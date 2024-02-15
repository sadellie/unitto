/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sadellie.unitto.feature.converter

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.addBracket
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.core.ui.common.textfield.getTextField
import com.sadellie.unitto.data.common.combine
import com.sadellie.unitto.data.common.isExpression
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.repository.UnitsRepository
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.DefaultUnit
import com.sadellie.unitto.data.model.unit.NumberBaseUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
internal class ConverterViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitsRepo: UnitsRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val converterInputKey1 = "CONVERTER_INPUT_1"
    private val converterInputKey2 = "CONVERTER_INPUT_2"
    private val input1 = MutableStateFlow(savedStateHandle.getTextField(converterInputKey1))
    private val input2 = MutableStateFlow(savedStateHandle.getTextField(converterInputKey2))
    private val focusedOnInput2 = MutableStateFlow(false)
    private val calculation = MutableStateFlow<BigDecimal?>(null)
    private val result = MutableStateFlow<ConverterResult>(ConverterResult.Loading)
    private val unitFrom = MutableStateFlow<AbstractUnit?>(null)
    private val unitTo = MutableStateFlow<AbstractUnit?>(null)

    private val currenciesState = MutableStateFlow<CurrencyRateUpdateState>(CurrencyRateUpdateState.Nothing)
    private var loadCurrenciesJob: Job? = null

    val converterUiState: StateFlow<UnitConverterUIState> = combine(
        input1,
        input2,
        calculation,
        result,
        unitFrom,
        unitTo,
        userPrefsRepository.converterPrefs,
        currenciesState,
    ) { input1, input2, calculation, result, unitFrom, unitTo, prefs, currenciesState ->
        return@combine when {
            (unitFrom is DefaultUnit) and (unitTo is DefaultUnit) -> {
                UnitConverterUIState.Default(
                    input1 = input1,
                    input2 = input2,
                    calculation = calculation,
                    result = result,
                    unitFrom = unitFrom as DefaultUnit,
                    unitTo = unitTo as DefaultUnit,
                    middleZero = prefs.middleZero,
                    formatterSymbols = prefs.formatterSymbols,
                    scale = prefs.precision,
                    outputFormat = prefs.outputFormat,
                    formatTime = prefs.unitConverterFormatTime,
                    currencyRateUpdateState = currenciesState,
                    acButton = prefs.acButton,
                )
            }
            (unitFrom is NumberBaseUnit) and (unitTo is NumberBaseUnit) -> {
                UnitConverterUIState.NumberBase(
                    input = input1,
                    result = result,
                    unitFrom = unitFrom as NumberBaseUnit,
                    unitTo = unitTo as NumberBaseUnit,
                )
            }
            else -> UnitConverterUIState.Loading
        }
    }
        .mapLatest { ui ->
            when (currenciesState.value) {
                is CurrencyRateUpdateState.Loading -> {
                    result.update { ConverterResult.Loading }
                    return@mapLatest ui
                }
                is CurrencyRateUpdateState.Error -> {
                    result.update { ConverterResult.Error }
                    return@mapLatest ui
                }
                is CurrencyRateUpdateState.Ready, is CurrencyRateUpdateState.Nothing -> {}
            }

            when (ui) {
                is UnitConverterUIState.Default -> {
                    convertDefault(
                        unitFrom = ui.unitFrom,
                        unitTo = ui.unitTo,
                        input1 = ui.input1,
                        input2 = ui.input2,
                        formatTime = ui.formatTime,
                    )
                }
                is UnitConverterUIState.NumberBase -> {
                    convertNumberBase(
                        unitFrom = ui.unitFrom,
                        unitTo = ui.unitTo,
                        input = ui.input,
                    )
                }
                is UnitConverterUIState.Loading -> {}
            }

            ui
        }
        .stateIn(viewModelScope, UnitConverterUIState.Loading)

    fun swapUnits() {
        unitFrom
            .getAndUpdate { unitTo.value }
            .also { oldUnitFrom -> unitTo.update { oldUnitFrom } }

        loadCurrenciesJob?.cancel()
        currenciesState.update { CurrencyRateUpdateState.Nothing }
        unitFrom.value?.let {
            if (it.group == UnitGroup.CURRENCY) updateCurrencyRates(it)
        }

        viewModelScope.launch {
            val unitTo = unitTo.value ?: return@launch
            val unitFrom = unitFrom.value ?: return@launch

            userPrefsRepository.updateLatestPairOfUnits(unitFrom = unitFrom, unitTo = unitTo)
        }
    }

    /**
     * Change currently focused text field. For feet and inches input
     *
     * @param focusOnInput2 `true` if focus is on inches input. `false`if focus on feet input.
     */
    fun updateFocused(focusOnInput2: Boolean) = focusedOnInput2.update { focusOnInput2 }

    fun addTokens(tokens: String) {
        if (focusedOnInput2.value) {
            input2.update {
                val newValue = it.addTokens(tokens)
                savedStateHandle[converterInputKey2] = newValue.text
                newValue
            }
        } else {
            input1.update {
                val newValue = it.addTokens(tokens)
                savedStateHandle[converterInputKey1] = newValue.text
                newValue
            }
        }
    }

    fun addBracket() {
        if (focusedOnInput2.value) {
            input2.update {
                val newValue = it.addBracket()
                savedStateHandle[converterInputKey2] = newValue.text
                newValue
            }
        } else {
            input1.update {
                val newValue = it.addBracket()
                savedStateHandle[converterInputKey1] = newValue.text
                newValue
            }
        }
    }

    fun deleteTokens() {
        if (focusedOnInput2.value) {
            input2.update {
                val newValue = it.deleteTokens()
                savedStateHandle[converterInputKey2] = newValue.text
                newValue
            }
        } else {
            input1.update {
                val newValue = it.deleteTokens()
                savedStateHandle[converterInputKey1] = newValue.text
                newValue
            }
        }
    }

    fun clearInput() {
        input1.update {
            savedStateHandle[converterInputKey1] = ""
            TextFieldValue()
        }
        input2.update {
            savedStateHandle[converterInputKey2] = ""
            TextFieldValue()
        }
    }

    fun updateInput(value: TextFieldValue) = input1.update { value }

    fun updateCurrencyRates(unit: AbstractUnit) {
        loadCurrenciesJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                currenciesState.update { CurrencyRateUpdateState.Loading }
                val updateDate = unitsRepo.updateRates(unit) ?: throw Exception("Empty cache")

                // Set to fresh objects with updated basic unit values
                unitFrom.update { unitFrom -> unitFrom?.id?.let { unitsRepo.getById(it) } }
                unitTo.update { unitTo -> unitTo?.id?.let { unitsRepo.getById(it) } }
                currenciesState.update { CurrencyRateUpdateState.Ready(updateDate) }
            } catch (e: Exception) {
                currenciesState.update { CurrencyRateUpdateState.Error }
            }
        }
    }

    fun updateUnitFrom(unit: AbstractUnit) = viewModelScope.launch {
        val pairId = unit.pairId
        val pair: AbstractUnit = if (pairId == null) {
            val collection = unitsRepo.getCollection(unit.group).sortedByDescending { it.counter }
            collection.firstOrNull { it.isFavorite } ?: collection.first()
        } else {
            unitsRepo.getById(pairId)
        }

        withContext(Dispatchers.Default) {
            unitsRepo.incrementCounter(unit)
            userPrefsRepository.updateLatestPairOfUnits(unitFrom = unit, unitTo = pair)
        }

        loadCurrenciesJob?.cancel()
        currenciesState.update { CurrencyRateUpdateState.Nothing }
        if (unit.group == UnitGroup.CURRENCY) updateCurrencyRates(unit)

        unitFrom.update {
            // We change from something to base converter or the other way around
            if ((it?.group == UnitGroup.NUMBER_BASE) xor (unit.group == UnitGroup.NUMBER_BASE)) {
                clearInput()
            }

            unit
        }
        unitTo.update { pair }
    }

    fun updateUnitTo(unit: AbstractUnit) {
        unitTo.update { unit }

        viewModelScope.launch {
            val unitTo = unitTo.value ?: return@launch
            val unitFrom = unitFrom.value ?: return@launch

            unitsRepo.incrementCounter(unitTo)

            unitsRepo.setPair(unitFrom, unitTo)
            userPrefsRepository.updateLatestPairOfUnits(unitFrom = unitFrom, unitTo = unitTo)
        }
    }

    private fun convertDefault(
        unitFrom: DefaultUnit,
        unitTo: DefaultUnit,
        input1: TextFieldValue,
        input2: TextFieldValue,
        formatTime: Boolean,
    ) = viewModelScope.launch(Dispatchers.Default) {
        val footInchInput = unitFrom.id == UnitID.foot

        if (footInchInput) {
            calculation.update { null }
        }

        // Calculate
        val calculated1 = try {
            Expression(input1.text.ifEmpty { Token.Digit._0 }).calculate()
        } catch (e: ExpressionException.DivideByZero) {
            calculation.update { null }
            return@launch
        } catch (e: Exception) {
            return@launch
        }

        // Update calculation
        calculation.update { if (input1.text.isExpression()) calculated1 else null }

        // Convert
        val result: ConverterResult = try {
            var conversion = unitFrom.convert(unitTo, calculated1)
            if (footInchInput) {
                // Converted from second text field too
                val inches = unitsRepo.getById(UnitID.inch) as DefaultUnit
                val calculated2 = try {
                    Expression(input2.text.ifEmpty { Token.Digit._0 }).calculate()
                } catch (e: ExpressionException.DivideByZero) {
                    calculation.update { null }
                    return@launch
                } catch (e: Exception) {
                    return@launch
                }
                conversion += inches.convert(unitTo, calculated2)
            }
            when {
                (unitFrom.group == UnitGroup.TIME) and (formatTime) -> formatTime(calculated1.multiply(unitFrom.basicUnit))
                unitTo.id == UnitID.foot -> formatFootInch(conversion, unitTo, unitsRepo.getById(UnitID.inch) as DefaultUnit)
                else -> ConverterResult.Default(conversion)
            }
        } catch (e: Exception) {
            ConverterResult.Default(BigDecimal.ZERO)
        }

        // Update result
        this@ConverterViewModel.result.update { result }
    }

    private fun convertNumberBase(
        unitFrom: NumberBaseUnit,
        unitTo: NumberBaseUnit,
        input: TextFieldValue,
    ) = viewModelScope.launch(Dispatchers.Default) {
        val conversion = try {
            unitFrom.convert(unitTo, input.text.ifEmpty { Token.Digit._0 })
        } catch (e: Exception) {
            ""
        }

        result.update { ConverterResult.NumberBase(conversion) }
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val userPrefs = userPrefsRepository.converterPrefs.first()
                val unitFrom = unitsRepo.getById(userPrefs.latestLeftSideUnit)
                val unitTo = unitsRepo.getById(userPrefs.latestRightSideUnit)

                this@ConverterViewModel.unitFrom.update { unitFrom }
                this@ConverterViewModel.unitTo.update { unitTo }
                if (unitFrom.group == UnitGroup.CURRENCY) updateCurrencyRates(unitFrom)
            } catch (e: NoSuchElementException) {
                val unitFrom = unitsRepo.getById(UnitID.kilometer)
                val unitTo = unitsRepo.getById(UnitID.mile)

                this@ConverterViewModel.unitFrom.update { unitFrom }
                this@ConverterViewModel.unitTo.update { unitTo }
            }
        }
    }
}

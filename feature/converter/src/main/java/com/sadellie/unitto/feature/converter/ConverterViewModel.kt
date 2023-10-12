/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.addBracket
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.data.common.combine
import com.sadellie.unitto.data.common.isExpression
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.DefaultUnit
import com.sadellie.unitto.data.model.unit.NumberBaseUnit
import com.sadellie.unitto.data.units.UnitsRepository
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
internal class ConverterViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitsRepo: UnitsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val converterInputKey = "CONVERTER_INPUT"
    private val _input = MutableStateFlow(
        with(savedStateHandle[converterInputKey] ?: "") {
            TextFieldValue(this, TextRange(this.length))
        }
    )
    private val _calculation = MutableStateFlow<BigDecimal?>(null)
    private val _result = MutableStateFlow<ConverterResult>(ConverterResult.Loading)
    private val _unitFrom = MutableStateFlow<AbstractUnit?>(null)
    private val _unitTo = MutableStateFlow<AbstractUnit?>(null)

    private val _leftQuery = MutableStateFlow(TextFieldValue())
    private val _leftUnits = MutableStateFlow<Map<UnitGroup, List<AbstractUnit>>>(emptyMap())
    private val _leftUnitGroup = MutableStateFlow<UnitGroup?>(null)

    private val _rightQuery = MutableStateFlow(TextFieldValue())
    private val _rightUnits = MutableStateFlow<Map<UnitGroup, List<AbstractUnit>>>(emptyMap())

    private val _currenciesState = MutableStateFlow<CurrencyRateUpdateState>(CurrencyRateUpdateState.Nothing)
    private var _loadCurrenciesJob: Job? = null

    val converterUiState: StateFlow<UnitConverterUIState> = combine(
        _input,
        _calculation,
        _result,
        _unitFrom,
        _unitTo,
        userPrefsRepository.converterPrefs,
        _currenciesState
    ) { input, calculation, result, unitFrom, unitTo, prefs, currenciesState ->
        return@combine when {
            (unitFrom is DefaultUnit) and (unitTo is DefaultUnit) -> {
                UnitConverterUIState.Default(
                    input = input,
                    calculation = calculation,
                    result = result,
                    unitFrom = unitFrom as DefaultUnit,
                    unitTo = unitTo as DefaultUnit,
                    enableHaptic = prefs.enableVibrations,
                    middleZero = prefs.middleZero,
                    formatterSymbols = AllFormatterSymbols.getById(prefs.separator),
                    scale = prefs.precision,
                    outputFormat = prefs.outputFormat,
                    formatTime = prefs.unitConverterFormatTime,
                    currencyRateUpdateState = currenciesState,
                    acButton = prefs.acButton,
                )
            }
            (unitFrom is NumberBaseUnit) and (unitTo is NumberBaseUnit) -> {
                UnitConverterUIState.NumberBase(
                    input = input,
                    result = result,
                    unitFrom = unitFrom as NumberBaseUnit,
                    unitTo = unitTo as NumberBaseUnit,
                    enableHaptic = prefs.enableVibrations,
                )
            }
            else -> UnitConverterUIState.Loading
        }
    }
        .mapLatest { ui ->
            when (_currenciesState.value) {
                is CurrencyRateUpdateState.Loading -> {
                    _result.update { ConverterResult.Loading }
                    return@mapLatest ui
                }
                is CurrencyRateUpdateState.Error -> {
                    _result.update { ConverterResult.Error }
                    return@mapLatest ui
                }
                is CurrencyRateUpdateState.Ready, is CurrencyRateUpdateState.Nothing -> {}
            }

            when (ui) {
                is UnitConverterUIState.Default -> {
                    convertDefault(
                        unitFrom = ui.unitFrom,
                        unitTo = ui.unitTo,
                        input = ui.input,
                        formatTime = ui.formatTime
                    )
                }
                is UnitConverterUIState.NumberBase -> {
                    convertNumberBase(
                        unitFrom = ui.unitFrom,
                        unitTo = ui.unitTo,
                        input = ui.input
                    )
                }
                is UnitConverterUIState.Loading -> {}
            }
            ui
        }
        .stateIn(viewModelScope, UnitConverterUIState.Loading)

    val leftSideUIState = combine(
        _unitFrom,
        _leftQuery,
        _leftUnits,
        _leftUnitGroup,
        userPrefsRepository.converterPrefs,
        unitsRepo.allUnits
    ) { unitFrom, query, units, unitGroup, prefs, _ ->
        unitFrom ?: return@combine LeftSideUIState.Loading

        return@combine LeftSideUIState.Ready(
            unitFrom = unitFrom,
            sorting = prefs.unitConverterSorting,
            shownUnitGroups = prefs.shownUnitGroups,
            favorites = prefs.unitConverterFavoritesOnly,
            verticalList = prefs.enableToolsExperiment,
            query = query,
            units = units,
            unitGroup = unitGroup
        )
    }
        .mapLatest {
            if (it !is LeftSideUIState.Ready) return@mapLatest it

            filterUnitsLeft(
                query = it.query,
                unitGroup = it.unitGroup,
                favoritesOnly = it.favorites,
                sorting = it.sorting,
                shownUnitGroups = it.shownUnitGroups,
            )
            it
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, LeftSideUIState.Loading)

    val rightSideUIState = combine(
        _unitFrom,
        _unitTo,
        _input,
        _calculation,
        _rightQuery,
        _rightUnits,
        userPrefsRepository.converterPrefs,
        _currenciesState,
        unitsRepo.allUnits,
    ) { unitFrom, unitTo, input, calculation, query, units, prefs, currenciesState, _ ->
        unitFrom ?: return@combine RightSideUIState.Loading
        unitTo ?: return@combine RightSideUIState.Loading

        return@combine RightSideUIState.Ready(
            unitFrom = unitFrom,
            unitTo = unitTo,
            sorting = prefs.unitConverterSorting,
            favorites = prefs.unitConverterFavoritesOnly,
            input = (calculation?.toPlainString() ?: input.text).replace(Token.Operator.minus, "-"),
            scale = prefs.precision,
            outputFormat = prefs.outputFormat,
            formatterSymbols = AllFormatterSymbols.getById(prefs.separator),
            currencyRateUpdateState = currenciesState,
            query = query,
            units = units,
        )
    }
        .mapLatest {
            if (it !is RightSideUIState.Ready) return@mapLatest it

            filterUnitsRight(
                query = it.query,
                unitGroup = it.unitTo.group,
                favoritesOnly = it.favorites,
                sorting = it.sorting,
            )
            it
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, RightSideUIState.Loading)

    fun swapUnits() {
        _unitFrom
            .getAndUpdate { _unitTo.value }
            .also { oldUnitFrom -> _unitTo.update { oldUnitFrom } }

        _loadCurrenciesJob?.cancel()
        _currenciesState.update { CurrencyRateUpdateState.Nothing }
        _unitFrom.value?.let {
            if (it.group == UnitGroup.CURRENCY) updateCurrencyRates(it)
        }

        viewModelScope.launch {
            val unitTo = _unitTo.value ?: return@launch
            val unitFrom = _unitFrom.value ?: return@launch

            userPrefsRepository.updateLatestPairOfUnits(unitFrom = unitFrom, unitTo = unitTo)
        }
    }

    fun addTokens(tokens: String) = _input.update {
        val newValue = it.addTokens(tokens)
        savedStateHandle[converterInputKey] = newValue.text
        newValue
    }

    fun addBracket() = _input.update {
        val newValue = it.addBracket()
        savedStateHandle[converterInputKey] = newValue.text
        newValue
    }

    fun deleteTokens() = _input.update {
        val newValue = it.deleteTokens()
        savedStateHandle[converterInputKey] = newValue.text
        newValue
    }

    fun clearInput() = _input.update {
        savedStateHandle[converterInputKey] = ""
        TextFieldValue()
    }

    fun onCursorChange(selection: TextRange) = _input.update { it.copy(selection = selection) }

    fun updateCurrencyRates(unit: AbstractUnit) {
        _loadCurrenciesJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                _currenciesState.update { CurrencyRateUpdateState.Loading }
                val updateDate = unitsRepo.updateRates(unit) ?: throw Exception("Empty cache")

                // Set to fresh objects with updated basic unit values
                _unitFrom.update { unitsRepo.getById(it!!.id) }
                _unitTo.update { unitsRepo.getById(it!!.id) }
                _currenciesState.update { CurrencyRateUpdateState.Ready(updateDate) }
            } catch (e: Exception) {
                _currenciesState.update { CurrencyRateUpdateState.Error }
            }
        }
    }

    fun updateUnitFrom(unit: AbstractUnit) = viewModelScope.launch {
        val pair = unitsRepo.getById(
            unit.pairId ?: unitsRepo.getCollection(unit.group).first().id
        )

        withContext(Dispatchers.Default) {
            unitsRepo.incrementCounter(unit)
            userPrefsRepository.updateLatestPairOfUnits(unitFrom = unit, unitTo = pair)
        }

        _loadCurrenciesJob?.cancel()
        _currenciesState.update { CurrencyRateUpdateState.Nothing }
        if (unit.group == UnitGroup.CURRENCY) updateCurrencyRates(unit)

        _unitFrom.update {
            // We change from something to base converter or the other way around
            if ((it?.group == UnitGroup.NUMBER_BASE) xor (unit.group == UnitGroup.NUMBER_BASE))
                clearInput()

            unit
        }
        _unitTo.update { pair }
    }

    fun updateUnitTo(unit: AbstractUnit) {
        _unitTo.update { unit }

        viewModelScope.launch {
            val unitTo = _unitTo.value ?: return@launch
            val unitFrom = _unitFrom.value ?: return@launch

            unitsRepo.incrementCounter(unitTo)

            unitsRepo.setPair(unitFrom, unitTo)
            userPrefsRepository.updateLatestPairOfUnits(unitFrom = unitFrom, unitTo = unitTo)
        }
    }

    fun queryChangeLeft(query: TextFieldValue) = _leftQuery.update { query }

    fun queryChangeRight(query: TextFieldValue) = _rightQuery.update { query }

    fun favoritesOnlyChange(enabled: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateUnitConverterFavoritesOnly(enabled)
    }

    fun updateUnitGroupLeft(unitGroup: UnitGroup?) = _leftUnitGroup.update { unitGroup }

    fun favoriteUnit(unit: AbstractUnit) = viewModelScope.launch {
        unitsRepo.favorite(unit)
    }

    private fun filterUnitsLeft(
        query: TextFieldValue,
        unitGroup: UnitGroup?,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
        shownUnitGroups: List<UnitGroup>,
    ) = viewModelScope.launch(Dispatchers.Default) {
        _leftUnits.update {
            unitsRepo.filterUnits(
                query = query.text,
                unitGroup = unitGroup,
                favoritesOnly = favoritesOnly,
                hideBrokenUnits = false,
                sorting = sorting,
                shownUnitGroups = shownUnitGroups
            )
        }
    }

    private fun filterUnitsRight(
        query: TextFieldValue,
        unitGroup: UnitGroup?,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
    ) = viewModelScope.launch(Dispatchers.Default) {
        _rightUnits.update {
            unitsRepo.filterUnits(
                query = query.text,
                unitGroup = unitGroup,
                favoritesOnly = favoritesOnly,
                hideBrokenUnits = true,
                sorting = sorting,
            )
        }
    }

    private fun convertDefault(
        unitFrom: DefaultUnit,
        unitTo: DefaultUnit,
        input: TextFieldValue,
        formatTime: Boolean,
    ) = viewModelScope.launch(Dispatchers.Default) {
        val calculated = try {
            Expression(input.text.ifEmpty { Token.Digit._0 }).calculate()
        } catch (e: ExpressionException.DivideByZero) {
            _calculation.update { null }
            return@launch
        } catch (e: Exception) {
            return@launch
        }
        _calculation.update { if (input.text.isExpression()) calculated else null }

        try {
            if ((unitFrom.group == UnitGroup.TIME) and (formatTime)) {
                _result.update { formatTime(calculated.multiply(unitFrom.basicUnit)) }

                return@launch
            }

            val conversion = unitFrom.convert(unitTo, calculated)

            _result.update { ConverterResult.Default(conversion) }
        } catch (e: Exception) {
            _result.update { ConverterResult.Default(BigDecimal.ZERO) }
        }
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

        _result.update { ConverterResult.NumberBase(conversion) }
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val userPrefs = userPrefsRepository.converterPrefs.first()
            val unitFrom = unitsRepo.getById(userPrefs.latestLeftSideUnit)
            val unitTo = unitsRepo.getById(userPrefs.latestRightSideUnit)

            _unitFrom.update { unitFrom }
            _unitTo.update { unitTo }
            if (unitFrom.group == UnitGroup.CURRENCY) updateCurrencyRates(unitFrom)
        }
    }
}

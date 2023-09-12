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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.data.common.isExpression
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.DefaultUnit
import com.sadellie.unitto.data.model.unit.NumberBaseUnit
import com.sadellie.unitto.data.units.UnitsRepository
import com.sadellie.unitto.data.units.combine
import com.sadellie.unitto.data.units.stateIn
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
internal class ConverterViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitsRepo: UnitsRepository,
) : ViewModel() {

    enum class CurrencyRateUpdateState { READY, LOADING, ERROR }

    private val _input = MutableStateFlow(TextFieldValue())
    private val _calculation = MutableStateFlow<BigDecimal?>(null)
    private val _result = MutableStateFlow<ConverterResult>(ConverterResult.Loading)
    private val _unitFrom = MutableStateFlow<AbstractUnit?>(null)
    private val _unitTo = MutableStateFlow<AbstractUnit?>(null)

    private val _leftSideUIState = MutableStateFlow(LeftSideUIState())
    private val _rightSideUIState = MutableStateFlow(RightSideUIState())
    private val _loadingCurrencies = MutableStateFlow(CurrencyRateUpdateState.READY)
    private var _loadCurrenciesJob: Job? = null

    val converterUiState: StateFlow<UnitConverterUIState> = combine(
        _input,
        _calculation,
        _result,
        _unitFrom,
        _unitTo,
        userPrefsRepository.mainPrefsFlow,
        _loadingCurrencies
    ) { input, calculation, result, unitFrom, unitTo, prefs, _ ->
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
                    scale = prefs.digitsPrecision,
                    outputFormat = prefs.outputFormat,
                    formatTime = prefs.unitConverterFormatTime,
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
        .onEach { ui ->
            when (_loadingCurrencies.value) {
                CurrencyRateUpdateState.LOADING -> {
                    _result.update { ConverterResult.Loading }
                    return@onEach
                }
                CurrencyRateUpdateState.ERROR -> {
                    _result.update { ConverterResult.Error }
                    return@onEach
                }
                CurrencyRateUpdateState.READY -> {}
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
        }
        .stateIn(viewModelScope, UnitConverterUIState.Loading)

    val leftSideUIState = combine(
        _unitFrom,
        _leftSideUIState,
        userPrefsRepository.mainPrefsFlow,
        unitsRepo.allUnits
    ) { unitFrom, ui, prefs, _ ->
        return@combine ui.copy(
            unitFrom = unitFrom,
            sorting = prefs.unitConverterSorting,
            shownUnitGroups = prefs.shownUnitGroups,
            favorites = prefs.unitConverterFavoritesOnly,
            verticalList = prefs.enableToolsExperiment,
        )
    }
        .onEach {
            filterUnitsLeft(
                query = it.query,
                unitGroup = it.unitGroup,
                favoritesOnly = it.favorites,
                sorting = it.sorting,
                shownUnitGroups = it.shownUnitGroups,
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, LeftSideUIState())

    val rightSideUIState = combine(
        _unitFrom,
        _unitTo,
        _input,
        _calculation,
        _rightSideUIState,
        userPrefsRepository.mainPrefsFlow,
        unitsRepo.allUnits
    ) { unitFrom, unitTo, input, calculation, ui, prefs, _ ->
        return@combine ui.copy(
            unitFrom = unitFrom,
            unitTo = unitTo,
            sorting = prefs.unitConverterSorting,
            favorites = prefs.unitConverterFavoritesOnly,
            input = calculation?.toPlainString() ?: input.text,
            scale = prefs.digitsPrecision,
            outputFormat = prefs.outputFormat,
            formatterSymbols = AllFormatterSymbols.getById(prefs.separator)
        )
    }
        .onEach {
            filterUnitsRight(
                query = it.query,
                unitGroup = it.unitTo?.group,
                favoritesOnly = it.favorites,
                sorting = it.sorting,
                shownUnitGroups = emptyList(),
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, RightSideUIState())

    fun swapUnits() {
        _unitFrom
            .getAndUpdate { _unitTo.value }
            .also { oldUnitFrom -> _unitTo.update { oldUnitFrom } }

        _loadCurrenciesJob?.cancel()
        _loadingCurrencies.update { CurrencyRateUpdateState.READY }
        _unitFrom.value?.let {
            if (it.group == UnitGroup.CURRENCY) updateCurrencyRates(it)
        }

        viewModelScope.launch {
            val unitTo = _unitTo.value ?: return@launch
            val unitFrom = _unitFrom.value ?: return@launch

            userPrefsRepository.updateLatestPairOfUnits(unitFrom = unitFrom, unitTo = unitTo)
        }
    }

    fun addTokens(tokens: String) = _input.update { it.addTokens(tokens) }

    fun deleteTokens() = _input.update { it.deleteTokens() }

    fun clearInput() = _input.update { TextFieldValue() }

    fun onCursorChange(selection: TextRange) = _input.update { it.copy(selection = selection) }

    fun updateCurrencyRates(unit: AbstractUnit) {
        _loadCurrenciesJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingCurrencies.update { CurrencyRateUpdateState.LOADING }
                unitsRepo.updateRates(unit)
                // Set to fresh objects with updated basic unit values
                _unitFrom.update { unitsRepo.getById(it!!.id) }
                _unitTo.update { unitsRepo.getById(it!!.id) }
                _loadingCurrencies.update { CurrencyRateUpdateState.READY }
            } catch (e: Exception) {
                _loadingCurrencies.update { CurrencyRateUpdateState.ERROR }
            }
        }
    }

    fun updateUnitFrom(unit: AbstractUnit) {
        val pair = unitsRepo.getById(
            unit.pairId ?: unitsRepo.getCollection(unit.group).first().id
        )

        viewModelScope.launch(Dispatchers.Default) {
            unitsRepo.incrementCounter(unit)
            userPrefsRepository.updateLatestPairOfUnits(unitFrom = unit, unitTo = pair)
        }

        _loadCurrenciesJob?.cancel()
        _loadingCurrencies.update { CurrencyRateUpdateState.READY }
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

    fun queryChangeLeft(query: TextFieldValue) = _leftSideUIState.update {
        it.copy(query = query)
    }

    fun queryChangeRight(query: TextFieldValue) = _rightSideUIState.update {
        it.copy(query = query)
    }

    fun favoritesOnlyChange(enabled: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateUnitConverterFavoritesOnly(enabled)
    }

    fun updateUnitGroupLeft(unitGroup: UnitGroup?) = _leftSideUIState.update {
        it.copy(unitGroup = unitGroup)
    }

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
        _leftSideUIState.update {
            it.copy(
                units = unitsRepo.filterUnits(
                    query = query.text,
                    unitGroup = unitGroup,
                    favoritesOnly = favoritesOnly,
                    hideBrokenUnits = false,
                    sorting = sorting,
                    shownUnitGroups = shownUnitGroups
                )
            )
        }
    }

    private fun filterUnitsRight(
        query: TextFieldValue,
        unitGroup: UnitGroup?,
        favoritesOnly: Boolean,
        sorting: UnitsListSorting,
        shownUnitGroups: List<UnitGroup>,
    ) = viewModelScope.launch(Dispatchers.Default) {
        _rightSideUIState.update {
            it.copy(
                units = unitsRepo.filterUnits(
                    query = query.text,
                    unitGroup = unitGroup,
                    favoritesOnly = favoritesOnly,
                    hideBrokenUnits = true,
                    sorting = sorting,
                    shownUnitGroups = shownUnitGroups
                )
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
            val userPrefs = userPrefsRepository.mainPrefsFlow.first()
            val unitFrom = unitsRepo.getById(userPrefs.latestLeftSideUnit)
            val unitTo = unitsRepo.getById(userPrefs.latestRightSideUnit)

            _unitFrom.update { unitFrom }
            _unitTo.update { unitTo }
            if (unitFrom.group == UnitGroup.CURRENCY) updateCurrencyRates(unitFrom)
        }
    }
}

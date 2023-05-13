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
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.data.common.isExpression
import com.sadellie.unitto.data.common.setMinimumRequiredScale
import com.sadellie.unitto.data.common.toStringWith
import com.sadellie.unitto.data.common.trimZeros
import com.sadellie.unitto.data.database.UnitsEntity
import com.sadellie.unitto.data.database.UnitsRepository
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.NumberBaseUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.units.AllUnitsRepository
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.combine
import com.sadellie.unitto.data.units.remote.CurrencyApi
import com.sadellie.unitto.data.units.remote.CurrencyUnitResponse
import com.sadellie.unitto.data.userprefs.UserPreferences
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.evaluatto.Expression
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitRepository: UnitsRepository,
    private val allUnitsRepository: AllUnitsRepository
) : ViewModel() {

    private val _userPrefs = userPrefsRepository.userPreferencesFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserPreferences()
    )

    /**
     * Unit on the left, the one we convert from. Initially null, meaning we didn't restore it yet.
     */
    private val _unitFrom: MutableStateFlow<AbstractUnit?> = MutableStateFlow(null)

    /**
     * Unit on the right, the one we convert to. Initially null, meaning we didn't restore it yet.
     */
    private val _unitTo: MutableStateFlow<AbstractUnit?> = MutableStateFlow(null)

    private val _input: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue())

    /**
     * Calculation result. Null when [_input] is not an expression.
     */
    private val _calculated: MutableStateFlow<String?> = MutableStateFlow(null)

    /**
     * Conversion result.
     */
    private val _result: MutableStateFlow<ConversionResult> = MutableStateFlow(ConversionResult.Loading)

    /**
     * True when loading something from network.
     */
    private val _showLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    /**
     * True if there was error while loading data.
     */
    private val _showError: MutableStateFlow<Boolean> = MutableStateFlow(false)

    /**
     * Current state of UI.
     */
    val uiState: StateFlow<ConverterUIState> = combine(
        _input,
        _unitFrom,
        _unitTo,
        _calculated,
        _result,
        _userPrefs,
        _showError,
        _showLoading
    ) { inputValue, unitFromValue, unitToValue, calculatedValue, resultValue, prefs, showError, showLoading ->
        return@combine ConverterUIState(
            inputValue = inputValue,
            calculatedValue = calculatedValue,
            resultValue = when {
                showError -> ConversionResult.Error
                showLoading -> ConversionResult.Loading
                else -> resultValue
            },
            unitFrom = unitFromValue,
            unitTo = unitToValue,
            mode = if (_unitFrom.value is NumberBaseUnit) ConverterMode.BASE else ConverterMode.DEFAULT,
            allowVibration = prefs.enableVibrations,
            formatterSymbols = AllFormatterSymbols.getById(prefs.separator)
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), ConverterUIState()
    )

    fun addTokens(tokens: String) = _input.update { it.addTokens(tokens) }
    fun deleteTokens() = _input.update { it.deleteTokens() }
    fun clearInput() = _input.update { TextFieldValue() }
    fun onCursorChange(selection: TextRange) = _input.update { it.copy(selection = selection) }

    /**
     * Update [_unitFrom] and set [_unitTo] from pair. Also updates stats for this [unit].
     */
    fun updateUnitFrom(unit: AbstractUnit) {
        // We change from something to base converter or the other way around
        if ((_unitFrom.value?.group == UnitGroup.NUMBER_BASE) xor (unit.group == UnitGroup.NUMBER_BASE)) {
            _calculated.update { null }
            clearInput()
        }

        _unitFrom.update { unit }

        /**
         * Update pair [_unitTo] if it exists
         */
        val pair = unit.pairedUnit
        if (pair != null) {
            _unitTo.update { allUnitsRepository.getById(pair) }
        } else {
            // No pair, get something from same group
            _unitTo.update { allUnitsRepository.getCollectionByGroup(unit.group).first() }
        }
        incrementCounter(unit)
        updateCurrenciesRatesIfNeeded()
        saveLatestPairOfUnits()
    }

    /**
     * Update [_unitTo] and update pair for [_unitFrom].
     */
    fun updateUnitTo(unit: AbstractUnit) {
        _unitTo.update { unit }
        _unitFrom.value?.pairedUnit = unit.unitId
        updatePairedUnit(_unitFrom.value ?: return)
        incrementCounter(unit)
        saveLatestPairOfUnits()
    }

    /**
     * Swap [_unitFrom] and [_unitTo].
     */
    fun swapUnits() {
        _unitFrom
            .getAndUpdate { _unitTo.value }
            .also { oldUnitFrom -> _unitTo.update { oldUnitFrom } }
        updateCurrenciesRatesIfNeeded()
    }

    private fun convertInput() {
        when (_unitFrom.value?.group) {
            UnitGroup.NUMBER_BASE -> convertAsNumberBase()
            else -> convertAsExpression()
        }
    }

    private fun convertAsNumberBase() = viewModelScope.launch(Dispatchers.Default) {
        // Units are still loading, don't convert anything yet
        val unitFrom = _unitFrom.value ?: return@launch
        val unitTo = _unitTo.value ?: return@launch

        val conversionResult = try {
            (unitFrom as NumberBaseUnit).convertToBase(
                input = _input.value.text.ifEmpty { "0" },
                toBase = (unitTo as NumberBaseUnit).base
            )
        } catch (e: Exception) {
            when (e) {
                is ClassCastException -> return@launch
                is NumberFormatException, is IllegalArgumentException -> ""
                else -> throw e
            }
        }
        _result.update { ConversionResult.NumberBase(conversionResult) }
    }

    private fun convertAsExpression() = viewModelScope.launch(Dispatchers.Default) {
        val unitFrom = _unitFrom.value ?: return@launch
        val unitTo = _unitTo.value ?: return@launch
        val input = _input.value.text.ifEmpty { "0" }

        if (input.isEmpty()) {
            _calculated.update { null }
            _result.update { ConversionResult.Default("") }
            return@launch
        }

        val evaluationResult = try {
            Expression(input)
                .calculate()
                .also {
                    if (it > BigDecimal.valueOf(Double.MAX_VALUE)) throw ExpressionException.TooBig()
                }
                .setMinimumRequiredScale(_userPrefs.value.digitsPrecision)
                .trimZeros()
        } catch (e: ExpressionException.DivideByZero) {
            _calculated.update { null }
            return@launch
        } catch (e: Exception) {
            return@launch
        }

        _calculated.update {
            if (input.isExpression()) evaluationResult.toStringWith(_userPrefs.value.outputFormat)
            else null
        }

        val conversionResult = unitFrom.convert(
            unitTo = unitTo,
            value = evaluationResult,
            scale = _userPrefs.value.digitsPrecision
        ).toStringWith(_userPrefs.value.outputFormat)

        _result.update {
            if ((unitFrom.group == UnitGroup.TIME) and (_userPrefs.value.unitConverterFormatTime))
                ConversionResult.Time(conversionResult)
            else
                ConversionResult.Default(conversionResult)
        }
    }

    private fun incrementCounter(unit: AbstractUnit) {
        viewModelScope.launch(Dispatchers.IO) {
            unitRepository.insertUnits(
                UnitsEntity(
                    unitId = unit.unitId,
                    isFavorite = unit.isFavorite,
                    pairedUnitId = unit.pairedUnit,
                    // This will increment counter on unit in list too
                    frequency = ++unit.counter
                )
            )
        }
    }

    private fun updatePairedUnit(unit: AbstractUnit) {
        viewModelScope.launch(Dispatchers.IO) {
            unitRepository.insertUnits(
                UnitsEntity(
                    unitId = unit.unitId,
                    isFavorite = unit.isFavorite,
                    pairedUnitId = unit.pairedUnit,
                    frequency = unit.counter
                )
            )
        }
    }

    private fun updateCurrenciesRatesIfNeeded() {
        viewModelScope.launch(Dispatchers.IO) {
            _showError.update { false }
            _showLoading.update { false }

            // Units are still loading, don't convert anything yet
            if (_unitFrom.value?.group != UnitGroup.CURRENCY) return@launch
            val unitFrom = _unitFrom.value ?: return@launch
            // Starting to load stuff
            _showLoading.update { true }

            try {
                val pairs: CurrencyUnitResponse =
                    CurrencyApi.retrofitService.getCurrencyPairs(unitFrom.unitId)
                allUnitsRepository.updateBasicUnitsForCurrencies(pairs.currency)
                convertAsExpression()
            } catch (e: Exception) {
                // Dangerous and stupid, but who cares
                _showError.update { true }
            } finally {
                /**
                 * Loaded, convert (this will trigger flow to call convertInput). Even if there was
                 * an error, it's OK and user will not see conversion result in that case.
                 */
                _showLoading.update { false }
            }
        }
    }

    private fun saveLatestPairOfUnits() {
        viewModelScope.launch(Dispatchers.IO) {
            // Units are still loading, don't convert anything yet
            val unitFrom = _unitFrom.value ?: return@launch
            val unitTo = _unitTo.value ?: return@launch
            userPrefsRepository.updateLatestPairOfUnits(unitFrom, unitTo)
        }
    }

    private fun startObserving() {
        viewModelScope.launch {
            merge(_input, _unitFrom, _unitTo, _showLoading, _userPrefs).collectLatest {
                convertInput()
            }
        }
    }

    private fun loadInitialUnitPair() {
        viewModelScope.launch(Dispatchers.IO) {
            val initialUserPrefs = userPrefsRepository.userPreferencesFlow.first()

            // First we load latest pair of units
            _unitFrom.update {
                try {
                    allUnitsRepository.getById(initialUserPrefs.latestLeftSideUnit)
                } catch (e: java.util.NoSuchElementException) {
                    allUnitsRepository.getById(MyUnitIDS.kilometer)
                }
            }

            _unitTo.update {
                try {
                    allUnitsRepository.getById(initialUserPrefs.latestRightSideUnit)
                } catch (e: java.util.NoSuchElementException) {
                    allUnitsRepository.getById(MyUnitIDS.mile)
                }
            }

            updateCurrenciesRatesIfNeeded()
        }
    }

    init {
        loadInitialUnitPair()
        startObserving()
    }
}

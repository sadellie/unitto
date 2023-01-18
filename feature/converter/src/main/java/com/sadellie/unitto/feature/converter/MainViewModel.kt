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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.keelar.exprk.ExpressionException
import com.github.keelar.exprk.Expressions
import com.sadellie.unitto.core.base.DIGITS
import com.sadellie.unitto.core.base.KEY_0
import com.sadellie.unitto.core.base.KEY_1
import com.sadellie.unitto.core.base.KEY_2
import com.sadellie.unitto.core.base.KEY_3
import com.sadellie.unitto.core.base.KEY_4
import com.sadellie.unitto.core.base.KEY_5
import com.sadellie.unitto.core.base.KEY_6
import com.sadellie.unitto.core.base.KEY_7
import com.sadellie.unitto.core.base.KEY_8
import com.sadellie.unitto.core.base.KEY_9
import com.sadellie.unitto.core.base.KEY_DIVIDE
import com.sadellie.unitto.core.base.KEY_DOT
import com.sadellie.unitto.core.base.KEY_EXPONENT
import com.sadellie.unitto.core.base.KEY_LEFT_BRACKET
import com.sadellie.unitto.core.base.KEY_MINUS
import com.sadellie.unitto.core.base.KEY_MULTIPLY
import com.sadellie.unitto.core.base.KEY_PLUS
import com.sadellie.unitto.core.base.KEY_RIGHT_BRACKET
import com.sadellie.unitto.core.base.KEY_SQRT
import com.sadellie.unitto.core.base.OPERATORS
import com.sadellie.unitto.data.combine
import com.sadellie.unitto.data.preferences.UserPreferences
import com.sadellie.unitto.data.preferences.UserPreferencesRepository
import com.sadellie.unitto.data.setMinimumRequiredScale
import com.sadellie.unitto.data.toStringWith
import com.sadellie.unitto.data.trimZeros
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.AllUnitsRepository
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.NumberBaseUnit
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.data.units.database.MyBasedUnit
import com.sadellie.unitto.data.units.database.MyBasedUnitsRepository
import com.sadellie.unitto.data.units.remote.CurrencyApi
import com.sadellie.unitto.data.units.remote.CurrencyUnitResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val basedUnitRepository: MyBasedUnitsRepository,
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

    /**
     * Current input. Used when converting units.
     */
    private val _input: MutableStateFlow<String> = MutableStateFlow(KEY_0)

    /**
     * Calculation result. Null when [_input] is not an expression.
     */
    private val _calculated: MutableStateFlow<String?> = MutableStateFlow(null)

    /**
     * List of latest symbols that were entered.
     */
    private val _latestInputStack: MutableList<String> = mutableListOf(_input.value)

    /**
     * Conversion result.
     */
    private val _result: MutableStateFlow<String> = MutableStateFlow(KEY_0)

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
    val uiStateFlow: StateFlow<MainScreenUIState> = combine(
        _input,
        _unitFrom,
        _unitTo,
        _calculated,
        _result,
        _showLoading,
        _showError
    ) { inputValue, unitFromValue, unitToValue, calculatedValue, resultValue, showLoadingValue, showErrorValue ->
        return@combine MainScreenUIState(
            inputValue = inputValue,
            calculatedValue = calculatedValue,
            resultValue = resultValue,
            showLoading = showLoadingValue,
            showError = showErrorValue,
            unitFrom = unitFromValue,
            unitTo = unitToValue,
            /**
             * If there will be more modes, this should be a separate value which we update when
             * changing units.
             */
            mode = if (_unitFrom.value is NumberBaseUnit) ConverterMode.BASE else ConverterMode.DEFAULT
        )
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MainScreenUIState()
        )

    /**
     * Process input with rules. Makes sure that user input is corrected when needed.
     *
     * @param symbolToAdd Use 'ugly' version of symbols.
     */
    fun processInput(symbolToAdd: String) {
        val lastTwoSymbols = _latestInputStack.takeLast(2)
        val lastSymbol: String = lastTwoSymbols.getOrNull(1) ?: lastTwoSymbols[0]
        val lastSecondSymbol: String? = lastTwoSymbols.getOrNull(0)

        when (symbolToAdd) {
            KEY_PLUS, KEY_DIVIDE, KEY_MULTIPLY, KEY_EXPONENT -> {
                when {
                    // Don't need expressions that start with zero
                    (_input.value == KEY_0) -> {}
                    (_input.value == KEY_MINUS) -> {}
                    (lastSymbol == KEY_LEFT_BRACKET) -> {}
                    (lastSymbol == KEY_SQRT) -> {}
                    /**
                     * For situations like "50+-", when user clicks "/" we delete "-" so it becomes
                     * "50+". We don't add "/' here. User will click "/" second time and the input
                     * will be "50/".
                     */
                    (lastSecondSymbol in OPERATORS) and (lastSymbol == KEY_MINUS) -> {
                        deleteDigit()
                    }
                    // Don't allow multiple operators near each other
                    (lastSymbol in OPERATORS) -> {
                        deleteDigit()
                        setInputSymbols(symbolToAdd)
                    }
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
            KEY_0 -> {
                when {
                    // Don't add zero if the input is already a zero
                    (_input.value == KEY_0) -> {}
                    (lastSymbol == KEY_RIGHT_BRACKET) -> {
                        processInput(KEY_MULTIPLY)
                        setInputSymbols(symbolToAdd)
                    }
                    // Prevents things like "-00" and "4+000"
                    ((lastSecondSymbol in OPERATORS + KEY_LEFT_BRACKET) and (lastSymbol == KEY_0)) -> {}
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
            KEY_1, KEY_2, KEY_3, KEY_4, KEY_5, KEY_6, KEY_7, KEY_8, KEY_9 -> {
                // Replace single zero (default input) if it's here
                when {
                    (_input.value == KEY_0) -> {
                        setInputSymbols(symbolToAdd, false)
                    }
                    (lastSymbol == KEY_RIGHT_BRACKET) -> {
                        processInput(KEY_MULTIPLY)
                        setInputSymbols(symbolToAdd)
                    }
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
            KEY_MINUS -> {
                when {
                    // Replace single zero with minus (to support negative numbers)
                    (_input.value == KEY_0) -> {
                        setInputSymbols(symbolToAdd, false)
                    }
                    // Don't allow multiple minuses near each other
                    (lastSymbol.compareTo(KEY_MINUS) == 0) -> {}
                    // Don't allow plus and minus be near each other
                    (lastSymbol == KEY_PLUS) -> {
                        deleteDigit()
                        setInputSymbols(symbolToAdd)
                    }
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
            KEY_DOT -> {
                if (!_input.value
                    .takeLastWhile { it.toString() !in OPERATORS.minus(KEY_DOT) }
                    .contains(KEY_DOT)
                ) {
                    setInputSymbols(symbolToAdd)
                }
            }
            KEY_LEFT_BRACKET -> {
                when {
                    // Replace single zero with minus (to support negative numbers)
                    (_input.value == KEY_0) -> {
                        setInputSymbols(symbolToAdd, false)
                    }
                    (lastSymbol == KEY_RIGHT_BRACKET) || (lastSymbol in DIGITS) || (lastSymbol == KEY_DOT) -> {
                        processInput(KEY_MULTIPLY)
                        setInputSymbols(symbolToAdd)
                    }
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
            KEY_RIGHT_BRACKET -> {
                when {
                    // Replace single zero with minus (to support negative numbers)
                    (_input.value == KEY_0) -> {}
                    (lastSymbol == KEY_LEFT_BRACKET) -> {}
                    (
                        _latestInputStack.filter { it == KEY_LEFT_BRACKET }.size ==
                            _latestInputStack.filter { it == KEY_RIGHT_BRACKET }.size
                        ) -> {
                    }
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
            KEY_SQRT -> {
                when {
                    // Replace single zero with minus (to support negative numbers)
                    (_input.value == KEY_0) -> {
                        setInputSymbols(symbolToAdd, false)
                    }
                    (lastSymbol == KEY_RIGHT_BRACKET) || (lastSymbol in DIGITS) || (lastSymbol == KEY_DOT) -> {
                        processInput(KEY_MULTIPLY)
                        setInputSymbols(symbolToAdd)
                    }
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
            else -> {
                when {
                    // Replace single zero with minus (to support negative numbers)
                    (_input.value == KEY_0) -> {
                        setInputSymbols(symbolToAdd, false)
                    }
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
        }
    }

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

    /**
     * Delete last symbol from [_input].
     */
    fun deleteDigit() {
        // Default input, don't delete
        if (_input.value == KEY_0) return

        val lastSymbol = _latestInputStack.removeLast()

        // If this value are same, it means that after deleting there will be no symbols left, set to default
        if (lastSymbol == _input.value) {
            setInputSymbols(KEY_0, false)
        } else {
            _input.update { it.removeSuffix(lastSymbol) }
        }
    }

    /**
     * Clear [_input].
     */
    fun clearInput() {
        setInputSymbols(KEY_0, false)
    }

    fun getInputValue(): String {
        return _calculated.value ?: _input.value
    }

    private suspend fun convertInput() {
        // Loading don't do anything
        if ((_unitFrom.value == null) or (_unitTo.value == null)) return
        withContext(Dispatchers.Default) {
            while (isActive) {
                when (_unitFrom.value?.group) {
                    UnitGroup.NUMBER_BASE -> convertAsNumberBase()
                    else -> convertAsExpression()
                }
                cancel()
            }
        }
    }

    private fun convertAsNumberBase() {
        val conversionResult: String = try {
            (_unitFrom.value as NumberBaseUnit).convertToBase(
                input = _input.value,
                toBase = (_unitTo.value as NumberBaseUnit).base
            )
        } catch (e: Exception) {
            when (e) {
                is ClassCastException -> return
                is NumberFormatException, is IllegalArgumentException -> ""
                else -> throw e
            }
        }
        _result.update { conversionResult }
    }

    private fun convertAsExpression() {
        // First we clean the input from garbage at the end
        var cleanInput = _input.value.dropLastWhile { !it.isDigit() }

        // Now we close open brackets that user didn't close
        // AUTO-CLOSE ALL BRACKETS
        val leftBrackets = _input.value.count { it.toString() == KEY_LEFT_BRACKET }
        val rightBrackets = _input.value.count { it.toString() == KEY_RIGHT_BRACKET }
        val neededBrackets = leftBrackets - rightBrackets
        if (neededBrackets > 0) cleanInput += KEY_RIGHT_BRACKET.repeat(neededBrackets)

        // Now we evaluate expression in input
        val evaluationResult: BigDecimal = try {
            Expressions().eval(cleanInput)
        } catch (e: Exception) {
            when (e) {
                // Invalid expression, don't do anything
                is ArrayIndexOutOfBoundsException,
                is IndexOutOfBoundsException,
                is ExpressionException -> return
                // Divide by zero or SQRT of negative
                is NumberFormatException, is ArithmeticException -> {
                    _calculated.update { null }
                    _result.update { "" }
                    return
                }
                else -> throw e
            }
        }

        // Now we just convert.
        // We can use evaluation result here, input is valid
        val conversionResult: BigDecimal = _unitFrom.value!!.convert(
            _unitTo.value!!,
            evaluationResult,
            _userPrefs.value.digitsPrecision
        )

        // Evaluated. Hide calculated result if no expression entered.
        // 123.456 will be true
        // -123.456 will be true
        // -123.456-123 will be false (first minus gets removed, ending with 123.456)
        _calculated.update {
            if (_input.value.removePrefix(KEY_MINUS).all { it.toString() !in OPERATORS }) {
                null
            } else {
                evaluationResult
                    .setMinimumRequiredScale(_userPrefs.value.digitsPrecision)
                    .trimZeros()
                    .toStringWith(_userPrefs.value.outputFormat)
            }
        }

        _result.update { conversionResult.toStringWith(_userPrefs.value.outputFormat) }
    }

    private fun setInputSymbols(symbol: String, add: Boolean = true) {
        if (add) {
            _input.update { it + symbol }
        } else {
            // We don't need previous input, clear entirely
            _latestInputStack.clear()
            _input.update { symbol }
        }
        _latestInputStack.add(symbol)
    }

    private fun incrementCounter(unit: AbstractUnit) {
        viewModelScope.launch(Dispatchers.IO) {
            basedUnitRepository.insertUnits(
                MyBasedUnit(
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
            basedUnitRepository.insertUnits(
                MyBasedUnit(
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
            val unitFrom = _unitFrom.value ?: return@launch
            if (_unitFrom.value?.group != UnitGroup.CURRENCY) return@launch
            // Starting to load stuff
            _showLoading.update { true }

            try {
                val pairs: CurrencyUnitResponse =
                    CurrencyApi.retrofitService.getCurrencyPairs(unitFrom.unitId)
                allUnitsRepository.updateBasicUnitsForCurrencies(pairs.currency)
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
        viewModelScope.launch(Dispatchers.Default) {
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

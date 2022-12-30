/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens.main

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.keelar.exprk.ExpressionException
import com.github.keelar.exprk.Expressions
import com.sadellie.unitto.FirebaseHelper
import com.sadellie.unitto.data.DIGITS
import com.sadellie.unitto.data.INTERNAL_DISPLAY
import com.sadellie.unitto.data.KEY_0
import com.sadellie.unitto.data.KEY_1
import com.sadellie.unitto.data.KEY_2
import com.sadellie.unitto.data.KEY_3
import com.sadellie.unitto.data.KEY_4
import com.sadellie.unitto.data.KEY_5
import com.sadellie.unitto.data.KEY_6
import com.sadellie.unitto.data.KEY_7
import com.sadellie.unitto.data.KEY_8
import com.sadellie.unitto.data.KEY_9
import com.sadellie.unitto.data.KEY_DIVIDE
import com.sadellie.unitto.data.KEY_DOT
import com.sadellie.unitto.data.KEY_EXPONENT
import com.sadellie.unitto.data.KEY_LEFT_BRACKET
import com.sadellie.unitto.data.KEY_MINUS
import com.sadellie.unitto.data.KEY_MULTIPLY
import com.sadellie.unitto.data.KEY_PLUS
import com.sadellie.unitto.data.KEY_RIGHT_BRACKET
import com.sadellie.unitto.data.KEY_SQRT
import com.sadellie.unitto.data.OPERATORS
import com.sadellie.unitto.data.preferences.UserPreferences
import com.sadellie.unitto.data.preferences.UserPreferencesRepository
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.AllUnitsRepository
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.data.units.database.MyBasedUnit
import com.sadellie.unitto.data.units.database.MyBasedUnitsRepository
import com.sadellie.unitto.data.units.remote.CurrencyApi
import com.sadellie.unitto.data.units.remote.CurrencyUnitResponse
import com.sadellie.unitto.data.combine
import com.sadellie.unitto.data.toStringWith
import com.sadellie.unitto.data.trimZeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val basedUnitRepository: MyBasedUnitsRepository,
    private val mContext: Application,
    private val allUnitsRepository: AllUnitsRepository
) : ViewModel() {

    val input: MutableStateFlow<String> = MutableStateFlow(KEY_0)
    private val _calculated: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _result: MutableStateFlow<String> = MutableStateFlow(KEY_0)
    private val _latestInputStack: MutableList<String> = mutableListOf(KEY_0)
    private val _inputDisplay: MutableStateFlow<String> = MutableStateFlow(KEY_0)
    private val _isLoadingDatabase: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _isLoadingNetwork: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _showError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _userPrefs = userPrefsRepository.userPreferencesFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), UserPreferences()
    )

    val mainFlow: StateFlow<MainScreenUIState> = combine(
        _inputDisplay,
        _calculated,
        _result,
        _isLoadingNetwork,
        _isLoadingDatabase,
        _showError,
    ) { inputValue: String,
        calculatedValue: String?,
        resultValue: String,
        showLoadingNetwork: Boolean,
        showLoadingDatabase: Boolean,
        showError: Boolean ->
        return@combine MainScreenUIState(
            inputValue = inputValue,
            calculatedValue = calculatedValue,
            resultValue = resultValue,
            isLoadingNetwork = showLoadingNetwork,
            isLoadingDatabase = showLoadingDatabase,
            showError = showError,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUIState())

    /**
     * Unit we converting from (left side)
     */
    var unitFrom: AbstractUnit by mutableStateOf(allUnitsRepository.getById(MyUnitIDS.kilometer))
        private set

    /**
     * Unit we are converting to (right side)
     */
    var unitTo: AbstractUnit by mutableStateOf(allUnitsRepository.getById(MyUnitIDS.mile))
        private set

    /**
     * This function takes local variables, converts values and then causes the UI to update
     */
    private suspend fun convertInput() {
        withContext(Dispatchers.Default) {
            while (isActive) {
                // First we clean the input from garbage at the end
                var cleanInput = input.value.dropLastWhile { !it.isDigit() }

                // Now we close open brackets that user didn't close
                // AUTOCLOSE ALL BRACKETS
                val leftBrackets = input.value.count { it.toString() == KEY_LEFT_BRACKET }
                val rightBrackets = input.value.count { it.toString() == KEY_RIGHT_BRACKET }
                val neededBrackets = leftBrackets - rightBrackets
                if (neededBrackets > 0) cleanInput += KEY_RIGHT_BRACKET.repeat(neededBrackets)

                // Now we evaluate expression in input
                val evaluationResult: BigDecimal = try {
                    Expressions().eval(cleanInput)
                        .setScale(_userPrefs.value.digitsPrecision, RoundingMode.HALF_EVEN)
                        .trimZeros()
                } catch (e: Exception) {
                    when (e) {
                        is ExpressionException,
                        is ArrayIndexOutOfBoundsException,
                        is IndexOutOfBoundsException,
                        is NumberFormatException,
                        is ArithmeticException -> {
                            // Invalid expression, can't do anything further
                            cancel()
                            return@withContext
                        }
                        else -> throw e
                    }
                }

                // Evaluated. Hide calculated result if no expression entered.
                // 123.456 will be true
                // -123.456 will be true
                // -123.456-123 will be false (first minus gets removed, ending with 123.456)
                if (input.value.removePrefix(KEY_MINUS).all { it.toString() !in OPERATORS }) {
                    // No operators
                    _calculated.update { null }
                } else {
                    _calculated.update { evaluationResult.toStringWith(_userPrefs.value.outputFormat) }
                }

                // Now we just convert.
                // We can use evaluation result here, input is valid
                val conversionResult: BigDecimal = unitFrom.convert(
                    unitTo, evaluationResult, _userPrefs.value.digitsPrecision
                )

                // Converted
                _result.update { conversionResult.toStringWith(_userPrefs.value.outputFormat) }

                cancel()
            }
        }
    }

    /**
     * Change left side unit. Unit to convert from
     *
     * @param clickedUnit Unit we need to change to
     */
    fun changeUnitFrom(clickedUnit: AbstractUnit) {
        // First we change unit
        unitFrom = clickedUnit

        // Now we change to positive if the group we switched to supports negate
        if (!clickedUnit.group.canNegate) {
            input.update { input.value.removePrefix(KEY_MINUS) }
        }

        // Now setting up right unit (pair for the left one)
        unitTo = if (unitFrom.pairedUnit == null) {
            allUnitsRepository.getCollectionByGroup(unitFrom.group).first()
        } else {
            allUnitsRepository.getById(unitFrom.pairedUnit!!)
        }

        viewModelScope.launch {
            // We need to increment counter for the clicked unit
            incrementCounter(clickedUnit)
            // Currencies require us to get data from the internet
            updateCurrenciesBasicUnits()
            // Saving latest pair
            saveLatestPairOfUnits()
        }
    }

    /**
     * Change right side unit. Unit to convert to
     *
     * @param clickedUnit Unit we need to change to
     */
    fun changeUnitTo(clickedUnit: AbstractUnit) {
        // First we change unit
        unitTo = clickedUnit
        // Updating paired unit for left side unit in memory (same thing for database below)
        unitFrom.pairedUnit = unitTo.unitId

        viewModelScope.launch {
            // Updating paired unit for left side unit in database
            basedUnitRepository.insertUnits(
                MyBasedUnit(
                    unitId = unitFrom.unitId,
                    isFavorite = unitFrom.isFavorite,
                    pairedUnitId = unitFrom.pairedUnit,
                    frequency = unitFrom.counter
                )
            )
            // We also need to increment counter for the selected unit
            incrementCounter(clickedUnit)
            // Saving latest pair
            saveLatestPairOfUnits()
        }
    }

    private suspend fun incrementCounter(unit: AbstractUnit) {
        basedUnitRepository.insertUnits(
            MyBasedUnit(
                unitId = unit.unitId, isFavorite = unit.isFavorite, pairedUnitId = unit.pairedUnit,
                // This will increment counter on unit in list too
                frequency = ++unit.counter
            )
        )
    }

    /**
     * Updates basic units properties for all currencies, BUT only when [unitFrom]'s group is set
     * to [UnitGroup.CURRENCY].
     */
    private suspend fun updateCurrenciesBasicUnits() {
        // Resetting error and network loading states in case we are not gonna do anything below
        _isLoadingNetwork.update { false }
        _showError.update { false }
        // We update currencies only when needed
        if (unitFrom.group != UnitGroup.CURRENCY) return

        // Starting to load stuff
        _isLoadingNetwork.update { true }

        try {
            val pairs: CurrencyUnitResponse =
                CurrencyApi.retrofitService.getCurrencyPairs(unitFrom.unitId)
            allUnitsRepository.updateBasicUnitsForCurrencies(pairs.currency)
        } catch (e: Exception) {
            when (e) {
                // 403, Network and Adapter exceptions can be ignored
                is retrofit2.HttpException, is java.net.UnknownHostException, is com.squareup.moshi.JsonDataException -> {}
                else -> {
                    // Unexpected exception, should report it
                    FirebaseHelper().recordException(e)
                }
            }
            _showError.update { true }
        } finally {
            // Loaded
            _isLoadingNetwork.update { false }
        }
    }

    /**
     * Swaps measurement, left to right and vice versa
     */
    fun swapUnits() {
        unitFrom = unitTo.also {
            unitTo = unitFrom
        }
        viewModelScope.launch {
            updateCurrenciesBasicUnits()
            saveLatestPairOfUnits()
        }
    }

    /**
     * Function to process input when we click keyboard. Make sure that digits/symbols will be
     * added properly
     * @param[symbolToAdd] Digit/Symbol we want to add, can be any digit 0..9 or a dot symbol
     */
    fun processInput(symbolToAdd: String) {
        val lastTwoSymbols = _latestInputStack.takeLast(2)
        val lastSymbol: String = lastTwoSymbols.getOrNull(1) ?: lastTwoSymbols[0]
        val lastSecondSymbol: String? = lastTwoSymbols.getOrNull(0)

        when (symbolToAdd) {
            KEY_PLUS, KEY_DIVIDE, KEY_MULTIPLY, KEY_EXPONENT -> {
                when {
                    // Don't need expressions that start with zero
                    (input.value == KEY_0) -> {}
                    (input.value == KEY_MINUS) -> {}
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
                    (input.value == KEY_0) -> {}
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
                    (input.value == KEY_0) -> {
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
                    (input.value == KEY_0) -> {
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
                if (canEnterDot()) {
                    setInputSymbols(symbolToAdd)
                }
            }
            KEY_LEFT_BRACKET -> {
                when {
                    // Replace single zero with minus (to support negative numbers)
                    (input.value == KEY_0) -> {
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
                    (input.value == KEY_0) -> {}
                    (lastSymbol == KEY_LEFT_BRACKET) -> {}
                    (_latestInputStack.filter { it == KEY_LEFT_BRACKET }.size ==
                            _latestInputStack.filter { it == KEY_RIGHT_BRACKET }.size) -> {
                    }
                    else -> {
                        setInputSymbols(symbolToAdd)
                    }
                }
            }
            KEY_SQRT -> {
                when {
                    // Replace single zero with minus (to support negative numbers)
                    (input.value == KEY_0) -> {
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
                    (input.value == KEY_0) -> {
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
     * Deletes last symbol from input and handles buttons state (enabled/disabled)
     */
    fun deleteDigit() {
        // Default input, don't delete
        if (input.value == KEY_0) return

        val lastSymbol = _latestInputStack.removeLast()

        // We will need to delete last symbol from both values
        val displayRepresentation: String = INTERNAL_DISPLAY[lastSymbol] ?: lastSymbol

        // If this value are same, it means that after deleting there will be no symbols left, set to default
        if (lastSymbol == input.value) {
            setInputSymbols(KEY_0, false)
        } else {
            input.update { it.removeSuffix(lastSymbol) }
            _inputDisplay.update { it.removeSuffix(displayRepresentation) }
        }
    }

    /**
     * Adds given [symbol] to [input] and [_inputDisplay] and updates [_latestInputStack].
     *
     * By default add symbol, but if [add] is False, will replace current input (when replacing
     * default [KEY_0] input).
     */
    private fun setInputSymbols(symbol: String, add: Boolean = true) {
        val displaySymbol: String = INTERNAL_DISPLAY[symbol] ?: symbol

        when {
            add -> {
                input.update { it + symbol }
                _inputDisplay.update { it + displaySymbol }
                _latestInputStack.add(symbol)
            }
            else -> {
                _latestInputStack.clear()
                input.update { symbol }
                _inputDisplay.update { displaySymbol }
                _latestInputStack.add(symbol)
            }
        }
    }

    /**
     * Clears input value and sets it to default (ZERO)
     */
    fun clearInput() {
        setInputSymbols(KEY_0, false)
    }

    /**
     * Returns value to be used when converting value on the right side screen (unit selection)
     */
    fun inputValue(): BigDecimal? {
        return try {
            (mainFlow.value.calculatedValue ?: mainFlow.value.inputValue).toBigDecimal()
        } catch (e: NumberFormatException) {
            null
        }
    }

    /**
     * Returns True if can be placed.
     */
    private fun canEnterDot(): Boolean = !input.value.takeLastWhile {
        it.toString() !in OPERATORS.minus(KEY_DOT)
    }.contains(KEY_DOT)

    /**
     * Saves latest pair of units into datastore
     */
    private suspend fun saveLatestPairOfUnits() {
        userPrefsRepository.updateLatestPairOfUnits(unitFrom, unitTo)
    }

    private fun startObserving() {
        viewModelScope.launch(Dispatchers.Default) {
            _userPrefs.collectLatest { convertInput() }
        }
        viewModelScope.launch(Dispatchers.Default) {
            input.collectLatest { convertInput() }
        }
    }

    init {
        viewModelScope.launch {
            val initialUserPrefs = userPrefsRepository.userPreferencesFlow.first()

            // First we load latest pair of units
            unitFrom = try {
                allUnitsRepository.getById(initialUserPrefs.latestLeftSideUnit)
            } catch (e: java.util.NoSuchElementException) {
                allUnitsRepository.getById(MyUnitIDS.kilometer)
            }

            unitTo = try {
                allUnitsRepository.getById(initialUserPrefs.latestRightSideUnit)
            } catch (e: java.util.NoSuchElementException) {
                allUnitsRepository.getById(MyUnitIDS.mile)
            }

            // Now we load units data from database
            val allBasedUnits = basedUnitRepository.getAll()
            allUnitsRepository.loadFromDatabase(mContext, allBasedUnits)

            // User is free to convert values and units on units screen can be sorted properly
            _isLoadingDatabase.update { false }
            updateCurrenciesBasicUnits()

            startObserving()
        }
    }
}

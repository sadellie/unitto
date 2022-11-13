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
import com.sadellie.unitto.data.*
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
import com.sadellie.unitto.screens.toStringWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val basedUnitRepository: MyBasedUnitsRepository,
    private val application: Application,
    private val allUnitsRepository: AllUnitsRepository
) : ViewModel() {

    private val _inputValue: MutableStateFlow<String> = MutableStateFlow(KEY_0)
    private val _deleteButtonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _negateButtonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isLoadingDatabase: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _isLoadingNetwork: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _showError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _calculatedValue: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _userPrefs = userPrefsRepository.userPreferencesFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserPreferences()
    )

    val mainFlow = combine(
        _inputValue, _isLoadingDatabase, _isLoadingNetwork, _showError, _userPrefs
    ) { inputValue, isLoadingDatabase, isLoadingNetwork, showError, _ ->
        return@combine MainScreenUIState(
            inputValue = inputValue,
            resultValue = convertValue(),
            deleteButtonEnabled = _deleteButtonEnabled.value,
            dotButtonEnabled = !_inputValue.value.takeLastWhile {
                it.toString() !in OPERATORS.minus(KEY_DOT)
            }.contains(KEY_DOT),
            negateButtonEnabled = _negateButtonEnabled.value,
            isLoadingDatabase = isLoadingDatabase,
            isLoadingNetwork = isLoadingNetwork,
            showError = showError,
            calculatedValue = _calculatedValue.value
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
    private fun convertValue(): String {

        val cleanInput = _inputValue.value.dropLastWhile { !it.isDigit() }

        // Kotlin doesn't have a multi catch
        val calculatedInput = try {
            Expressions()
                .setPrecision(_userPrefs.value.digitsPrecision)
                .eval(cleanInput)
        } catch (e: Exception) {
            // Kotlin doesn't have a multi catch
            when (e) {
                is ExpressionException, is ArrayIndexOutOfBoundsException, is NumberFormatException -> return mainFlow.value.resultValue
                else -> throw e
            }
        }

        // Ugly way of determining when to hide calculated value
        _calculatedValue.value = if (calculatedInput.toPlainString() != cleanInput) {
            // Expression is not same as the result, show result
            calculatedInput.toStringWith(_userPrefs.value.outputFormat)
        } else {
            // Expression is same as the result, don't show result, the input text is enough
            null
        }

        // Converting value using a specified precision
        val convertedValue: BigDecimal = unitFrom.convert(
            unitTo, calculatedInput, _userPrefs.value.digitsPrecision
        )

        /**
         * There is a very interesting bug when trailing zeros are not stripped when input
         * consists of ZEROS only (0.00000 as an example). This check is a workaround. If the result
         * is zero, than we make sure there are no trailing zeros.
         */
        val resultValue = if (convertedValue == BigDecimal.ZERO.setScale(
                _userPrefs.value.digitsPrecision, RoundingMode.HALF_EVEN
            )
        ) {
            KEY_0
        } else {
            convertedValue.toStringWith(_userPrefs.value.outputFormat)
        }

        return resultValue
    }

    /**
     * Change left side unit. Unit to convert from
     *
     * @param clickedUnit Unit we need to change to
     */
    fun changeUnitFrom(clickedUnit: AbstractUnit) {
        // First we change unit
        unitFrom = clickedUnit

        // Now we check for negate button
        _negateButtonEnabled.update { clickedUnit.group.canNegate }
        // Now we change to positive if the group we switched to supports negate
        if (!clickedUnit.group.canNegate) {
            _inputValue.update { _inputValue.value.removePrefix(KEY_MINUS) }
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
        val lastSymbol = _inputValue.value.last().toString()
        val lastSecondSymbol = _inputValue.value.takeLast(2).dropLast(1)
        _deleteButtonEnabled.update { true }

        when (symbolToAdd) {
            KEY_0 -> {
                // Don't add zero if the input is already a zero
                if (_inputValue.value == KEY_0) return
                // Don't add zero if there is a zero and operator in front
                if ((lastSecondSymbol in OPERATORS) and (lastSymbol == KEY_0)) return
                _inputValue.update { _inputValue.value + symbolToAdd }
            }
            KEY_1, KEY_2, KEY_3, KEY_4, KEY_5, KEY_6, KEY_7, KEY_8, KEY_9 -> {
                // Replace single zero (default input) if it's here
                if (_inputValue.value == KEY_0) {
                    _inputValue.update { symbolToAdd }
                } else {
                    _inputValue.update { _inputValue.value + symbolToAdd }
                }
            }
            KEY_DOT -> {
                _inputValue.update { _inputValue.value + symbolToAdd }
            }
            KEY_MINUS -> {
                when {
                    // Replace single zero with minus (to support negative numbers)
                    (_inputValue.value == KEY_0) -> {
                        if (symbolToAdd == KEY_MINUS) _inputValue.update { symbolToAdd }
                    }
                    // Don't allow multiple minuses near each other
                    (lastSymbol == KEY_MINUS) -> {}
                    // Don't allow plus and minus be near each other
                    (lastSymbol == KEY_PLUS) -> {
                        _inputValue.update { _inputValue.value.dropLast(1) + symbolToAdd }
                    }
                    else -> {
                        _inputValue.update { _inputValue.value + symbolToAdd }
                    }
                }
            }
            KEY_PLUS, KEY_DIVIDE, KEY_MULTIPLY -> {
                when {
                    // Don't need expressions that start with zero
                    (_inputValue.value == KEY_0) or (_inputValue.value == KEY_MINUS) -> {}
                    /**
                     * For situations like "50+-", when user clicks "/" we delete "-" so it becomes
                     * "50+". We don't add "/' here. User will click "/" second time and the input
                     * will be "50/".
                     */
                    (lastSecondSymbol in OPERATORS) and (lastSymbol == KEY_MINUS)-> {
                        deleteDigit()
                    }
                    // Don't allow multiple operators near each other
                    (lastSymbol in OPERATORS) -> {
                        _inputValue.update { _inputValue.value.dropLast(1) + symbolToAdd }
                    }
                    else -> {
                        _inputValue.update { _inputValue.value + symbolToAdd }
                    }
                }
            }
        }
    }

    /**
     * Deletes last symbol from input and handles buttons state (enabled/disabled)
     */
    fun deleteDigit() {
        // Deleting last symbol
        var inputToSet = _inputValue.value.dropLast(1)

        /*
        Now we check what we have left
        We deleted last symbol and we got Empty string, just minus symbol, or zero
        Do not allow deleting anything beyond this (disable button)
        Set input to default (zero)
        Skipping this block means that we are left we acceptable value, i.e. 123.03
        */
        if (inputToSet in listOf(String(), KEY_MINUS, KEY_0)) {
            _deleteButtonEnabled.update { false }
            inputToSet = KEY_0
        }

        _inputValue.update { inputToSet }
    }

    /**
     * Clears input value and sets it to default (ZERO)
     */
    fun clearInput() {
        _deleteButtonEnabled.update { false }
        _inputValue.update { KEY_0 }
    }

    /**
     * Saves latest pair of units into datastore
     */
    private suspend fun saveLatestPairOfUnits() {
        userPrefsRepository.updateLatestPairOfUnits(unitFrom, unitTo)
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
            allUnitsRepository.loadFromDatabase(application, allBasedUnits)

            // User is free to convert values and units on units screen can be sorted properly
            _negateButtonEnabled.update { unitFrom.group.canNegate }
            _isLoadingDatabase.update { false }
            updateCurrenciesBasicUnits()
        }
    }
}

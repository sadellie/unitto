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
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.FirebaseHelper
import com.sadellie.unitto.data.KEY_0
import com.sadellie.unitto.data.KEY_DOT
import com.sadellie.unitto.data.KEY_MINUS
import com.sadellie.unitto.data.preferences.OutputFormat
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
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
    private var userPrefs = UserPreferences()

    /**
     * UI state
     */
    private val _mainUIState = MutableStateFlow(MainScreenUIState())

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

    val mainFlow = combine(_mainUIState, userPrefsRepository.userPreferencesFlow) { UIState, prefs ->
        userPrefs = prefs
        convertValue()
        return@combine UIState
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainScreenUIState()
        )

    /**
     * This function takes local variables, converts values and then causes the UI to update
     */
    private fun convertValue() {
        // Converting value using a specified precision
        val convertedValue: BigDecimal =
            unitFrom.convert(
                unitTo,
                _mainUIState.value.inputValue.toBigDecimal(),
                userPrefs.digitsPrecision
            )

        /**
         * There is a very interesting bug when trailing zeros are not stripped when input
         * consists of ZEROS only (0.00000 as an example). This check is a workaround. If the result
         * is zero, than we make sure there are no trailing zeros.
         */
        val resultValue =
            if (convertedValue == BigDecimal.ZERO.setScale(
                    userPrefs.digitsPrecision,
                    RoundingMode.HALF_EVEN
                )
            ) {
                KEY_0
            } else {
                // Setting result value using a specified OutputFormat
                when (userPrefs.outputFormat) {
                    OutputFormat.ALLOW_ENGINEERING -> convertedValue.toString()
                    OutputFormat.FORCE_ENGINEERING -> convertedValue.toEngineeringString()
                    else -> convertedValue.toPlainString()
                }
            }
        _mainUIState.value = _mainUIState.value.copy(resultValue = resultValue)
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
        _mainUIState.value =
            _mainUIState.value.copy(negateButtonEnabled = clickedUnit.group.canNegate)
        // Now we change to positive if the group we switched to supports negate
        if (!clickedUnit.group.canNegate) {
            _mainUIState.value =
                _mainUIState.value.copy(
                    inputValue = _mainUIState.value.inputValue.removePrefix(
                        KEY_MINUS
                    )
                )
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
            // We can't call outside of this block. It will set precision to 0 in that case
            convertValue()
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

        // Changed units, now we can convert
        convertValue()
    }

    private suspend fun incrementCounter(unit: AbstractUnit) {
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

    /**
     * Updates basic units properties for all currencies, BUT only when [unitFrom]'s group is set
     * to [UnitGroup.CURRENCY].
     */
    private suspend fun updateCurrenciesBasicUnits() {
        // Resetting error and network loading states in case we are not gonna do anything below
        _mainUIState.value = _mainUIState.value.copy(isLoadingNetwork = false, showError = false)
        // We update currencies only when needed
        if (unitFrom.group != UnitGroup.CURRENCY) return

        // Starting to load stuff
        _mainUIState.value = _mainUIState.value.copy(isLoadingNetwork = true)

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
            _mainUIState.value = _mainUIState.value.copy(showError = true)
        } finally {
            // Loaded
            _mainUIState.value = _mainUIState.value.copy(isLoadingNetwork = false)
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
        // Swapped, can convert now
        convertValue()
    }

    /**
     * Function to process input when we click keyboard. Make sure that digits/symbols will be
     * added properly
     * @param[digitToAdd] Digit/Symbol we want to add, can be any digit 0..9 or a dot symbol
     */
    fun processInput(digitToAdd: String) {
        when (digitToAdd) {
            KEY_DOT -> {
                // Here we add a dot to input
                // Disabling dot button to avoid multiple dots in input value
                // Enabling delete button to so that we can delete this dot from input
                _mainUIState.value = _mainUIState.value.copy(
                    inputValue = _mainUIState.value.inputValue + digitToAdd,
                    dotButtonEnabled = false,
                    deleteButtonEnabled = true
                )
            }
            KEY_0 -> {
                // We shouldn't add zero to another zero in input, i.e. 00
                if (_mainUIState.value.inputValue != KEY_0) {
                    _mainUIState.value =
                        _mainUIState.value.copy(inputValue = _mainUIState.value.inputValue + digitToAdd)
                }
            }
            else -> {
                /*
                We want to add digit to input.
                When there is just a zero, we should replace it with the digit we want to add,
                avoids input to be like 03 (with this check it will be just 3)
                */
                _mainUIState.value = _mainUIState.value.copy(
                    inputValue = if (_mainUIState.value.inputValue == KEY_0) digitToAdd else _mainUIState.value.inputValue + digitToAdd,
                    deleteButtonEnabled = true
                )
            }
        }
        convertValue()
    }

    /**
     * Deletes last symbol from input and handles buttons state (enabled/disabled)
     */
    fun deleteDigit() {
        // Last symbol is a dot
        // We enable DOT button
        if (_mainUIState.value.inputValue.endsWith(KEY_DOT)) {
            _mainUIState.value = _mainUIState.value.copy(dotButtonEnabled = true)
        }

        // Deleting last symbol
        _mainUIState.value =
            _mainUIState.value.copy(inputValue = _mainUIState.value.inputValue.dropLast(1))

        /*
        Now we check what we have left
        We deleted last symbol and we got Empty string, just minus symbol, or zero
        Do not allow deleting anything beyond this (disable button)
        Set input to default (zero)
        Skipping this block means that we are left we acceptable value, i.e. 123.03
        */
        if (
            _mainUIState.value.inputValue in listOf(String(), KEY_MINUS, KEY_0)
        ) {
            _mainUIState.value =
                _mainUIState.value.copy(deleteButtonEnabled = false, inputValue = KEY_0)
        }

        // We are sure that input has acceptable value, so we convert it
        convertValue()
    }

    /**
     * Clears input value and sets it to default (ZERO)
     */
    fun clearInput() {
        _mainUIState.value = _mainUIState.value.copy(
            inputValue = KEY_0,
            deleteButtonEnabled = false,
            dotButtonEnabled = true
        )
        convertValue()
    }

    /**
     * Changes input from positive to negative and vice versa
     */
    fun negateInput() {
        _mainUIState.value = _mainUIState.value.copy(
            inputValue = if (_mainUIState.value.inputValue.getOrNull(0) != KEY_MINUS.single()) {
                // If input doesn't have minus at the beginning, we give it to it
                KEY_MINUS + _mainUIState.value.inputValue
            } else {
                // Input has minus, meaning we need to remove it
                _mainUIState.value.inputValue.removePrefix(KEY_MINUS)
            }
        )
        convertValue()
    }

    /**
     * Saves latest pair of units into datastore
     */
    private suspend fun saveLatestPairOfUnits() {
        userPrefsRepository.updateLatestPairOfUnits(unitFrom, unitTo)
    }

    init {
        viewModelScope.launch {
            userPrefs = userPrefsRepository.userPreferencesFlow.first()

            // First we load latest pair of units
            unitFrom = try {
                allUnitsRepository.getById(userPrefs.latestLeftSideUnit)
            } catch (e: java.util.NoSuchElementException) {
                Log.w("MainViewModel", "No unit with the given unitId")
                allUnitsRepository.getById(MyUnitIDS.kilometer)
            }

            unitTo = try {
                allUnitsRepository.getById(userPrefs.latestRightSideUnit)
            } catch (e: java.util.NoSuchElementException) {
                Log.w("MainViewModel", "No unit with the given unitId")
                allUnitsRepository.getById(MyUnitIDS.mile)
            }

            _mainUIState.value =
                _mainUIState.value.copy(negateButtonEnabled = unitFrom.group.canNegate)

            // Now we load units data from database
            val allBasedUnits = basedUnitRepository.getAll()
            allUnitsRepository.loadFromDatabase(application, allBasedUnits)

            // User is free to convert values and units on units screen can be sorted properly
            _mainUIState.value = _mainUIState.value.copy(isLoadingDatabase = false)
            updateCurrenciesBasicUnits()
            convertValue()
        }
    }
}

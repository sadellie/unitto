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
import com.sadellie.unitto.screens.combine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
    private val _dotButtonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _negateButtonEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _isLoadingDatabase: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _isLoadingNetwork: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _showError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _userPrefs = userPrefsRepository.userPreferencesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserPreferences())

    val mainFlow = combine(
        _inputValue,
        _deleteButtonEnabled,
        _dotButtonEnabled,
        _negateButtonEnabled,
        _isLoadingDatabase,
        _isLoadingNetwork,
        _showError,
        _userPrefs
    ) { inputValue, deleteButtonEnabled, dotButtonEnabled, negateButtonEnabled, isLoadingDatabase, isLoadingNetwork, showError, _ ->
        return@combine MainScreenUIState(
            inputValue = inputValue,
            resultValue = convertValue(),
            deleteButtonEnabled = deleteButtonEnabled,
            dotButtonEnabled = dotButtonEnabled,
            negateButtonEnabled = negateButtonEnabled,
            isLoadingDatabase = isLoadingDatabase,
            isLoadingNetwork = isLoadingNetwork,
            showError = showError
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUIState())

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
        // Converting value using a specified precision
        val convertedValue: BigDecimal =
            unitFrom.convert(
                unitTo,
                _inputValue.value.toBigDecimal(),
                _userPrefs.value.digitsPrecision
            )

        /**
         * There is a very interesting bug when trailing zeros are not stripped when input
         * consists of ZEROS only (0.00000 as an example). This check is a workaround. If the result
         * is zero, than we make sure there are no trailing zeros.
         */
        val resultValue =
            if (convertedValue == BigDecimal.ZERO.setScale(
                    _userPrefs.value.digitsPrecision,
                    RoundingMode.HALF_EVEN
                )
            ) {
                KEY_0
            } else {
                // Setting result value using a specified OutputFormat
                when (_userPrefs.value.outputFormat) {
                    OutputFormat.ALLOW_ENGINEERING -> convertedValue.toString()
                    OutputFormat.FORCE_ENGINEERING -> convertedValue.toEngineeringString()
                    else -> convertedValue.toPlainString()
                }
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
     * @param[digitToAdd] Digit/Symbol we want to add, can be any digit 0..9 or a dot symbol
     */
    fun processInput(digitToAdd: String) {
        when (digitToAdd) {
            KEY_DOT -> {
                // Here we add a dot to input
                // Disabling dot button to avoid multiple dots in input value
                // Enabling delete button to so that we can delete this dot from input
                _inputValue.update { _inputValue.value + digitToAdd }
                _dotButtonEnabled.update { false }
                _deleteButtonEnabled.update { true }
            }
            KEY_0 -> {
                // We shouldn't add zero to another zero in input, i.e. 00
                if (_inputValue.value != KEY_0) {
                    _inputValue.update { _inputValue.value + digitToAdd }
                }
            }
            else -> {
                /*
                We want to add digit to input.
                When there is just a zero, we should replace it with the digit we want to add,
                avoids input to be like 03 (with this check it will be just 3)
                */
                _inputValue.update {
                    if (_inputValue.value == KEY_0) digitToAdd else _inputValue.value + digitToAdd
                }
                _deleteButtonEnabled.update { true }
            }
        }
    }

    /**
     * Deletes last symbol from input and handles buttons state (enabled/disabled)
     */
    fun deleteDigit() {
        // Last symbol is a dot
        // We enable DOT button
        if (_inputValue.value.endsWith(KEY_DOT)) {
            _dotButtonEnabled.update { true }
        }

        // Deleting last symbol
        var inputToSet = _inputValue.value.dropLast(1)

        /*
        Now we check what we have left
        We deleted last symbol and we got Empty string, just minus symbol, or zero
        Do not allow deleting anything beyond this (disable button)
        Set input to default (zero)
        Skipping this block means that we are left we acceptable value, i.e. 123.03
        */
        if (
            inputToSet in listOf(String(), KEY_MINUS, KEY_0)
        ) {
            _deleteButtonEnabled.update { false }
            inputToSet = KEY_0
        }

        _inputValue.update { inputToSet }
    }

    /**
     * Clears input value and sets it to default (ZERO)
     */
    fun clearInput() {
        _inputValue.update { KEY_0 }
        _deleteButtonEnabled.update { false }
        _dotButtonEnabled.update { true }
    }

    /**
     * Changes input from positive to negative and vice versa
     */
    fun negateInput() {
        _inputValue.update {
            if (_inputValue.value.getOrNull(0) != KEY_MINUS.single()) {
                // If input doesn't have minus at the beginning, we give it to it
                KEY_MINUS + _inputValue.value
            } else {
                // Input has minus, meaning we need to remove it
                _inputValue.value.removePrefix(KEY_MINUS)
            }
        }
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

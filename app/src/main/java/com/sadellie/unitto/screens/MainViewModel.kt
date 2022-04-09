package com.sadellie.unitto.screens

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sadellie.unitto.data.KEY_0
import com.sadellie.unitto.data.KEY_DOT
import com.sadellie.unitto.data.KEY_MINUS
import com.sadellie.unitto.data.preferences.*
import com.sadellie.unitto.data.units.ALL_UNITS
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.data.units.collections.CURRENCY_COLLECTION
import com.sadellie.unitto.data.units.database.MyBasedUnit
import com.sadellie.unitto.data.units.database.MyBasedUnitDao
import com.sadellie.unitto.data.units.database.MyBasedUnitDatabase
import com.sadellie.unitto.data.units.remote.CurrencyApi
import com.sadellie.unitto.data.units.remote.CurrencyUnitResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mySettingsPrefs: UserPreferences,
    private val application: Application
) : ViewModel() {

    /**
     * App database
     */
    private val myBasedUnitDao: MyBasedUnitDao =
        MyBasedUnitDatabase.getDatabase(application).myBasedUnitDao()

    /**
     * APP THEME
     */
    val currentAppTheme =
        mySettingsPrefs.getItem(UserPreferenceKeys.CURRENT_APP_THEME, AppTheme.AUTO)

    fun saveCurrentAppTheme(value: Int) {
        viewModelScope.launch {
            mySettingsPrefs.saveInt(key = UserPreferenceKeys.CURRENT_APP_THEME, value)
        }
    }

    /**
     * CONVERSION PRECISION
     */
    var precision: Int by mutableStateOf(0)
        private set

    fun setPrecisionPref(value: Int) {
        viewModelScope.launch {
            precision = value
            mySettingsPrefs.saveInt(UserPreferenceKeys.DIGITS_PRECISION, value)
            convertValue()
        }
    }

    /**
     * SEPARATOR
     */
    var separator: Int by mutableStateOf(0)
        private set

    fun setSeparatorPref(value: Int) {
        separator = value
        viewModelScope.launch {
            Formatter.setSeparator(value)
            mySettingsPrefs.saveInt(UserPreferenceKeys.SEPARATOR, value)
            convertValue()
        }
    }

    /**
     * OUTPUT FORMAT
     */
    var outputFormat: Int by mutableStateOf(0)
        private set

    /**
     * Sets given output format and saves it in user preference store
     * @param value [OutputFormat] to set
     */
    fun setOutputFormatPref(value: Int) {
        // Updating value in memory
        outputFormat = value
        // Updating value on disk
        viewModelScope.launch {
            mySettingsPrefs.saveInt(UserPreferenceKeys.OUTPUT_FORMAT, value)
            convertValue()
        }
    }

    /**
     * Unit we converting from (left side)
     */
    var unitFrom: AbstractUnit by mutableStateOf(ALL_UNITS[0])
        private set

    /**
     * Unit we are converting to (right side)
     */
    var unitTo: AbstractUnit by mutableStateOf(ALL_UNITS[1])
        private set

    /**
     * UI state
     */
    var mainUIState: MainScreenUIState by mutableStateOf(MainScreenUIState())
        private set

    var favoritesOnly: Boolean by mutableStateOf(false)
        private set

    fun toggleFavoritesOnly() {
        favoritesOnly = !favoritesOnly
    }

    // This is a grouped list of units that is used for unit selection screen
    var unitsToShow: Map<UnitGroup, List<AbstractUnit>> by mutableStateOf(emptyMap())
        private set

    /**
     * This function takes local variables, converts values and then causes the UI to update
     */
    private fun convertValue() {
        // We cannot convert values, as we are still user prefs from datastore (precision)
        if (mainUIState.isLoadingDataStore) return
        // Converting value using a specified precision
        val convertedValue: BigDecimal =
            unitFrom.convert(unitTo, mainUIState.inputValue.toBigDecimal(), precision)
        // Setting result value using a specified OutputFormat
        mainUIState = mainUIState.copy(
            resultValue = when (outputFormat) {
                OutputFormat.ALLOW_ENGINEERING -> convertedValue.toString()
                OutputFormat.FORCE_ENGINEERING -> convertedValue.toEngineeringString()
                else -> convertedValue.toPlainString()
            }
        )
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
        mainUIState = mainUIState.copy(negateButtonEnabled = clickedUnit.group.canNegate)
        // Now we change to positive if the group we switched to supports negate
        if (!clickedUnit.group.canNegate) {
            mainUIState =
                mainUIState.copy(inputValue = mainUIState.inputValue.removePrefix(KEY_MINUS))
        }

        // Now setting up right unit (pair for the left one)
        unitTo = ALL_UNITS.first {
            if (unitFrom.pairedUnit.isNullOrEmpty()) {
                // No pair. Just getting unit from same group
                it.group == unitFrom.group
            } else {
                // There is a paired unit
                it.unitId == unitFrom.pairedUnit
            }
        }

        viewModelScope.launch {
            // We need to increment counter for the clicked unit
            incrementCounter(clickedUnit)
            // Currencies require us to get data from the internet
            updateCurrenciesBasicUnits()
            // We can't call outside of this block. It will set precision to 0 in that case
            convertValue()
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
            myBasedUnitDao.insertUnits(
                MyBasedUnit(
                    unitId = unitFrom.unitId,
                    isFavorite = unitFrom.isFavorite,
                    pairedUnitId = unitFrom.pairedUnit,
                    frequency = unitFrom.counter
                )
            )
            // We also need to increment counter for the selected unit
            incrementCounter(clickedUnit)
        }

        // Changed units, now we can convert
        convertValue()
    }

    private suspend fun incrementCounter(unit: AbstractUnit) {
        myBasedUnitDao.insertUnits(
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
     * Updates basic units properties for all currencies. Uses [unitFrom]
     */
    private suspend fun updateCurrenciesBasicUnits() {
        // Resetting error and network loading states in case we are not gonna do anything below
        mainUIState = mainUIState.copy(isLoadingNetwork = false, showError = false)
        // We update currencies only when needed
        if (unitFrom.group != UnitGroup.CURRENCY) return

        // Starting to load stuff
        mainUIState = mainUIState.copy(isLoadingNetwork = true)

        try {
            val pairs: CurrencyUnitResponse =
                CurrencyApi.retrofitService.getCurrencyPairs(unitFrom.unitId)
            CURRENCY_COLLECTION.forEach {
                // Getting rates from map. We set ZERO as default so that it can be skipped
                val rate = pairs.currency.getOrElse(it.unitId) { BigDecimal.ZERO }
                // We make sure that we don't divide by zero
                if (rate > BigDecimal.ZERO) {
                    it.isEnabled = true
                    it.basicUnit = BigDecimal.ONE.setScale(MAX_PRECISION).div(rate)
                } else {
                    // Hiding broken currencies
                    it.isEnabled = false
                }
            }
        } catch (e: Exception) {
            when (e) {
                // 403, Network and Adapter exceptions can be ignored
                is retrofit2.HttpException, is java.net.UnknownHostException, is com.squareup.moshi.JsonDataException -> {}
                else -> {
                    // Unexpected exception, should report it
                    FirebaseCrashlytics.getInstance().recordException(e)
                }
            }
            mainUIState = mainUIState.copy(showError = true)
        } finally {
            // Loaded
            mainUIState = mainUIState.copy(isLoadingNetwork = false)
        }
    }

    /**
     * Swaps measurement, left to right and vice versa
     */
    fun swapUnits() {
        unitFrom = unitTo.also {
            unitTo = unitFrom
        }
        viewModelScope.launch { updateCurrenciesBasicUnits() }
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
                mainUIState = mainUIState.copy(
                    inputValue = mainUIState.inputValue + digitToAdd,
                    dotButtonEnabled = false,
                    deleteButtonEnabled = true
                )
            }
            KEY_0 -> {
                // We shouldn't add zero to another zero in input, i.e. 00
                if (mainUIState.inputValue != KEY_0) {
                    mainUIState = mainUIState.copy(inputValue = mainUIState.inputValue + digitToAdd)
                }
            }
            else -> {
                /*
                We want to add digit to input.
                When there is just a zero, we should replace it with the digit we want to add,
                avoids input to be like 03 (with this check it will be just 3)
                */
                mainUIState = mainUIState.copy(
                    inputValue = if (mainUIState.inputValue == KEY_0) digitToAdd else mainUIState.inputValue + digitToAdd,
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
        if (mainUIState.inputValue.endsWith(KEY_DOT)) {
            mainUIState = mainUIState.copy(dotButtonEnabled = true)
        }

        // Deleting last symbol
        mainUIState = mainUIState.copy(inputValue = mainUIState.inputValue.dropLast(1))

        /*
        Now we check what we have left
        We deleted last symbol and we got Empty string, just minus symbol, or zero
        Do not allow deleting anything beyond this (disable button)
        Set input to default (zero)
        Skipping this block means that we are left we acceptable value, i.e. 123.03
        */
        if (
            mainUIState.inputValue in listOf(String(), KEY_MINUS, KEY_0)
        ) {
            mainUIState = mainUIState.copy(deleteButtonEnabled = false, inputValue = KEY_0)
        }

        // We are sure that input has acceptable value, so we convert it
        convertValue()
    }

    /**
     * Clears input value and sets it to default (ZERO)
     */
    fun clearInput() {
        mainUIState = mainUIState.copy(
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
        mainUIState = mainUIState.copy(
            inputValue = if (mainUIState.inputValue.getOrNull(0) != KEY_MINUS.single()) {
                // If input doesn't have minus at the beginning, we give it to it
                KEY_MINUS + mainUIState.inputValue
            } else {
                // Input has minus, meaning we need to remove it
                mainUIState.inputValue.removePrefix(KEY_MINUS)
            }
        )
        convertValue()
    }

    /**
     * Add or remove from favorites (changes to the opposite of current state)
     */
    fun favoriteUnit(unit: AbstractUnit) {
        viewModelScope.launch {
            // Changing unit in list to the opposite
            unit.isFavorite = !unit.isFavorite
            // Updating it in database
            myBasedUnitDao.insertUnits(
                MyBasedUnit(
                    unitId = unit.unitId,
                    isFavorite = unit.isFavorite,
                    pairedUnitId = unit.pairedUnit,
                    frequency = unit.counter
                )
            )
        }
    }

    /**
     * Saves latest pair of units into datastore
     */
    fun saveMe() {
        viewModelScope.launch {
            mySettingsPrefs.saveString(UserPreferenceKeys.LATEST_LEFT_SIDE, unitFrom.unitId)
            mySettingsPrefs.saveString(UserPreferenceKeys.LATEST_RIGHT_SIDE, unitTo.unitId)
        }
    }

    /**
     * Filters and groups [ALL_UNITS] in coroutine
     *
     * @param query String search query
     * @param chosenUnitGroup Currently selected [UnitGroup] (from chips list)
     * @param leftSide Decide whether or not we are on left side. Need it because right side requires
     * us to mark disabled currency units
     */
    suspend fun loadUnitToShow(
        query: String,
        chosenUnitGroup: UnitGroup?,
        leftSide: Boolean
    ) {
        val filterGroup: Boolean = chosenUnitGroup != null

        withContext(Dispatchers.Default) {
            // Basic filtering
            val basicFilteredUnits =
                ALL_UNITS.asSequence()
                    // Unit group and favorite
                    .filter {
                        // Decide which group of units to show
                        when {
                            // Both sides, Chip is selected, Only favorites
                            (filterGroup) and (favoritesOnly) -> {
                                (it.group == chosenUnitGroup) and it.isFavorite
                            }
                            // Both sides, Chip is selected, NOT Only favorites
                            (filterGroup) and (!favoritesOnly) -> it.group == chosenUnitGroup
                            // Chip is NOT selected, Only favorites
                            (!filterGroup) and (favoritesOnly) -> it.isFavorite
                            // Chip is NOT selected, NOT Only favorites
                            else -> true
                        }
                    }
                    // Hiding broken currency units
                    .filter { if (leftSide) true else it.isEnabled }

            unitsToShow = if (query.isEmpty()) {
                // Query is empty, i.e. we want to see all units and they need to be sorted by usage
                basicFilteredUnits
                    .sortedByDescending { it.counter }
            } else {
                // We are searching for a specific unit, we don't care about popularity
                // We need search accuracy
                basicFilteredUnits
                    .sortedBy {
                        it.renderedName
                            .substring(0, minOf(query.length, it.renderedName.length))
                            .lev(query)
                    }
            }
                // Group by unit group
                .groupBy { it.group }
        }
    }

    init {
        viewModelScope.launch {
            // First we load latest pair of units
            unitFrom = try {
                ALL_UNITS.first {
                    it.unitId == mySettingsPrefs.getItem(
                        UserPreferenceKeys.LATEST_LEFT_SIDE,
                        MyUnitIDS.kilometer
                    ).first()
                }
            } catch (e: java.util.NoSuchElementException) {
                Log.w("MainViewModel", "No unit with the given unitId")
                ALL_UNITS
                    .first { it.unitId == MyUnitIDS.kilometer }
            }

            unitTo = try {
                ALL_UNITS
                    .first {
                        it.unitId == mySettingsPrefs.getItem(
                            UserPreferenceKeys.LATEST_RIGHT_SIDE,
                            MyUnitIDS.mile
                        ).first()
                    }
            } catch (e: java.util.NoSuchElementException) {
                Log.w("MainViewModel", "No unit with the given unitId")
                ALL_UNITS
                    .first { it.unitId == MyUnitIDS.mile }
            }

            // Now we get the precision so we can convert values
            precision = mySettingsPrefs.getItem(UserPreferenceKeys.DIGITS_PRECISION, 3).first()
            // Getting separator and changing it in number formatter
            separator =
                mySettingsPrefs.getItem(UserPreferenceKeys.SEPARATOR, Separator.SPACES).first()
                    .also { Formatter.setSeparator(it) }
            // Getting output format
            outputFormat =
                mySettingsPrefs.getItem(UserPreferenceKeys.OUTPUT_FORMAT, OutputFormat.PLAIN)
                    .first()

            // Basic data is loaded, user is free to convert values
            // Set negate button state according to current group
            mainUIState = mainUIState.copy(
                isLoadingDataStore = false,
                negateButtonEnabled = unitFrom.group.canNegate
            )
            updateCurrenciesBasicUnits()
            convertValue()

            val allBasedUnits = myBasedUnitDao.getAll()

            ALL_UNITS.forEach {
                // Loading unit names so that we can search through them
                it.renderedName = application.getString(it.displayName)
                val based = allBasedUnits.firstOrNull { based -> based.unitId == it.unitId }
                // Loading paired units
                it.pairedUnit = based?.pairedUnitId
                // Loading favorite state
                it.isFavorite = based?.isFavorite ?: false
                it.counter = based?.frequency ?: 0
            }
        }
    }
}

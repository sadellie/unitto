/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.data.backup

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sadellie.unitto.data.userprefs.PrefsKeys
import com.sadellie.unitto.data.userprefs.getThemingMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BackupManagerTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var backupManager: BackupManager

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        dataStore = PreferenceDataStoreFactory.create(
            corruptionHandler = null,
            produceFile = { appContext.preferencesDataStoreFile("test") }
        )

        backupManager = BackupManager(
            mContext = appContext,
            dataStore = dataStore,
            unitsDao = FakeUnitsDao,
            timeZoneDao = FakeTimeZoneDao
        )

        runBlocking {
            dataStore.edit {
                it[PrefsKeys.THEMING_MODE] = FakeUsrPreferenceValues.themingMode
                it[PrefsKeys.ENABLE_DYNAMIC_THEME] = FakeUsrPreferenceValues.enableDynamicTheme
                it[PrefsKeys.ENABLE_AMOLED_THEME] = FakeUsrPreferenceValues.enableAmoledTheme
                it[PrefsKeys.CUSTOM_COLOR] = FakeUsrPreferenceValues.customColor
                it[PrefsKeys.MONET_MODE] = FakeUsrPreferenceValues.monetMode
                it[PrefsKeys.STARTING_SCREEN] = FakeUsrPreferenceValues.startingScreen
                it[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] = FakeUsrPreferenceValues.enableToolsExperiment
                it[PrefsKeys.ENABLE_VIBRATIONS] = FakeUsrPreferenceValues.enableVibrations
                it[PrefsKeys.MIDDLE_ZERO] = FakeUsrPreferenceValues.middleZero
                it[PrefsKeys.AC_BUTTON] = FakeUsrPreferenceValues.acButton
                it[PrefsKeys.RPN_MODE] = FakeUsrPreferenceValues.rpnMode

                // FORMATTER
                it[PrefsKeys.DIGITS_PRECISION] = FakeUsrPreferenceValues.precision
                it[PrefsKeys.SEPARATOR] = FakeUsrPreferenceValues.separator
                it[PrefsKeys.OUTPUT_FORMAT] = FakeUsrPreferenceValues.outputFormat

                // CALCULATOR
                it[PrefsKeys.RADIAN_MODE] = FakeUsrPreferenceValues.radianMode
                it[PrefsKeys.PARTIAL_HISTORY_VIEW] = FakeUsrPreferenceValues.partialHistoryView
                it[PrefsKeys.CLEAR_INPUT_AFTER_EQUALS] = FakeUsrPreferenceValues.clearInputAfterEquals

                // UNIT CONVERTER
                it[PrefsKeys.LATEST_LEFT_SIDE] = FakeUsrPreferenceValues.latestLeftSide
                it[PrefsKeys.LATEST_RIGHT_SIDE] = FakeUsrPreferenceValues.latestRightSide
                it[PrefsKeys.SHOWN_UNIT_GROUPS] = FakeUsrPreferenceValues.shownUnitGroups.joinToString(",")
                it[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY] = FakeUsrPreferenceValues.unitConverterFavoritesOnly
                it[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME] = FakeUsrPreferenceValues.unitConverterFormatTime
                it[PrefsKeys.UNIT_CONVERTER_SORTING] = FakeUsrPreferenceValues.unitConverterSorting.name
            }
        }
    }

    @Test
    fun getUserDataTest() = runBlocking{
        val actualUserData = backupManager.userDataFromApp()
        val expectedUserData = UserData(
            themingMode = FakeUsrPreferenceValues.themingMode,
            enableDynamicTheme = FakeUsrPreferenceValues.enableDynamicTheme,
            enableAmoledTheme = FakeUsrPreferenceValues.enableAmoledTheme,
            customColor = FakeUsrPreferenceValues.customColor,
            monetMode = FakeUsrPreferenceValues.monetMode,
            startingScreen = FakeUsrPreferenceValues.startingScreen,
            enableToolsExperiment = FakeUsrPreferenceValues.enableToolsExperiment,
            enableVibrations = FakeUsrPreferenceValues.enableVibrations,
            middleZero = FakeUsrPreferenceValues.middleZero,
            acButton = FakeUsrPreferenceValues.acButton,
            rpnMode = FakeUsrPreferenceValues.rpnMode,
            precision = FakeUsrPreferenceValues.precision,
            separator = FakeUsrPreferenceValues.separator,
            outputFormat = FakeUsrPreferenceValues.outputFormat,
            radianMode = FakeUsrPreferenceValues.radianMode,
            partialHistoryView = FakeUsrPreferenceValues.partialHistoryView,
            clearInputAfterEquals = FakeUsrPreferenceValues.clearInputAfterEquals,
            latestLeftSide = FakeUsrPreferenceValues.latestLeftSide,
            latestRightSide = FakeUsrPreferenceValues.latestRightSide,
            shownUnitGroups = FakeUsrPreferenceValues.shownUnitGroups,
            unitConverterFavoritesOnly = FakeUsrPreferenceValues.unitConverterFavoritesOnly,
            unitConverterFormatTime = FakeUsrPreferenceValues.unitConverterFormatTime,
            unitConverterSorting = FakeUsrPreferenceValues.unitConverterSorting,
            unitsTable = units,
            timeZoneTable = timeZones
        )

        assertEquals(expectedUserData, actualUserData)
    }

    @Test
    fun updateDatastoreTest() = runBlocking{
        backupManager.updateDatastore(
            UserData(
                themingMode = FakeUsrPreferenceValues.themingMode,
                enableDynamicTheme = FakeUsrPreferenceValues.enableDynamicTheme,
                enableAmoledTheme = FakeUsrPreferenceValues.enableAmoledTheme,
                customColor = FakeUsrPreferenceValues.customColor,
                monetMode = FakeUsrPreferenceValues.monetMode,
                startingScreen = FakeUsrPreferenceValues.startingScreen,
                enableToolsExperiment = FakeUsrPreferenceValues.enableToolsExperiment,
                enableVibrations = FakeUsrPreferenceValues.enableVibrations,
                middleZero = FakeUsrPreferenceValues.middleZero,
                acButton = FakeUsrPreferenceValues.acButton,
                rpnMode = FakeUsrPreferenceValues.rpnMode,
                precision = FakeUsrPreferenceValues.precision,
                separator = FakeUsrPreferenceValues.separator,
                outputFormat = FakeUsrPreferenceValues.outputFormat,
                radianMode = FakeUsrPreferenceValues.radianMode,
                partialHistoryView = FakeUsrPreferenceValues.partialHistoryView,
                clearInputAfterEquals = FakeUsrPreferenceValues.clearInputAfterEquals,
                latestLeftSide = FakeUsrPreferenceValues.latestLeftSide,
                latestRightSide = FakeUsrPreferenceValues.latestRightSide,
                shownUnitGroups = FakeUsrPreferenceValues.shownUnitGroups,
                unitConverterFavoritesOnly = FakeUsrPreferenceValues.unitConverterFavoritesOnly,
                unitConverterFormatTime = FakeUsrPreferenceValues.unitConverterFormatTime,
                unitConverterSorting = FakeUsrPreferenceValues.unitConverterSorting,
                unitsTable = units,
                timeZoneTable = timeZones
            )
        )

        val data = dataStore.data.first()
        // TODO Wrong implementation, should test all
        assertEquals(FakeUsrPreferenceValues.themingMode, data.getThemingMode())
    }

    @Test
    fun updateUnitsTableTest() = runBlocking {
        backupManager.updateUnitsTable(
            UserData(
                themingMode = FakeUsrPreferenceValues.themingMode,
                enableDynamicTheme = FakeUsrPreferenceValues.enableDynamicTheme,
                enableAmoledTheme = FakeUsrPreferenceValues.enableAmoledTheme,
                customColor = FakeUsrPreferenceValues.customColor,
                monetMode = FakeUsrPreferenceValues.monetMode,
                startingScreen = FakeUsrPreferenceValues.startingScreen,
                enableToolsExperiment = FakeUsrPreferenceValues.enableToolsExperiment,
                enableVibrations = FakeUsrPreferenceValues.enableVibrations,
                middleZero = FakeUsrPreferenceValues.middleZero,
                acButton = FakeUsrPreferenceValues.acButton,
                rpnMode = FakeUsrPreferenceValues.rpnMode,
                precision = FakeUsrPreferenceValues.precision,
                separator = FakeUsrPreferenceValues.separator,
                outputFormat = FakeUsrPreferenceValues.outputFormat,
                radianMode = FakeUsrPreferenceValues.radianMode,
                partialHistoryView = FakeUsrPreferenceValues.partialHistoryView,
                clearInputAfterEquals = FakeUsrPreferenceValues.clearInputAfterEquals,
                latestLeftSide = FakeUsrPreferenceValues.latestLeftSide,
                latestRightSide = FakeUsrPreferenceValues.latestRightSide,
                shownUnitGroups = FakeUsrPreferenceValues.shownUnitGroups,
                unitConverterFavoritesOnly = FakeUsrPreferenceValues.unitConverterFavoritesOnly,
                unitConverterFormatTime = FakeUsrPreferenceValues.unitConverterFormatTime,
                unitConverterSorting = FakeUsrPreferenceValues.unitConverterSorting,
                unitsTable = emptyList(),
                timeZoneTable = timeZones
            )
        )

        val data = FakeUnitsDao.getAllFlow().first()
        // TODO Wrong implementation
        assertEquals("$units | $data", units, data)
    }
}

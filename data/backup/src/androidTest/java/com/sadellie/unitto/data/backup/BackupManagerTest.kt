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
import com.sadellie.unitto.data.database.CalculatorHistoryEntity
import com.sadellie.unitto.data.database.TimeZoneEntity
import com.sadellie.unitto.data.database.UnitsEntity
import com.sadellie.unitto.data.userprefs.PrefsKeys
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
        // Inserting dummy data as app data (db and prefs)
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        dataStore = PreferenceDataStoreFactory.create(
            corruptionHandler = null,
            produceFile = { appContext.preferencesDataStoreFile("test") }
        )

        backupManager = BackupManager(
            mContext = appContext,
            dataStore = dataStore,
            unitsDao = FakeUnitsDao,
            timeZoneDao = FakeTimeZoneDao,
            calculatorHistoryDao = FakeCalculatorHistoryDao
        )

        runBlocking {
            dataStore.edit {
                it[PrefsKeys.THEMING_MODE] = fakeUserData.themingMode
                it[PrefsKeys.ENABLE_DYNAMIC_THEME] = fakeUserData.enableDynamicTheme
                it[PrefsKeys.ENABLE_AMOLED_THEME] = fakeUserData.enableAmoledTheme
                it[PrefsKeys.CUSTOM_COLOR] = fakeUserData.customColor
                it[PrefsKeys.MONET_MODE] = fakeUserData.monetMode
                it[PrefsKeys.STARTING_SCREEN] = fakeUserData.startingScreen
                it[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] = fakeUserData.enableToolsExperiment
                it[PrefsKeys.SYSTEM_FONT] = fakeUserData.systemFont
                it[PrefsKeys.ENABLE_VIBRATIONS] = fakeUserData.enableVibrations
                it[PrefsKeys.MIDDLE_ZERO] = fakeUserData.middleZero
                it[PrefsKeys.AC_BUTTON] = fakeUserData.acButton
                it[PrefsKeys.RPN_MODE] = fakeUserData.rpnMode
                it[PrefsKeys.DIGITS_PRECISION] = fakeUserData.precision
                it[PrefsKeys.SEPARATOR] = fakeUserData.separator
                it[PrefsKeys.OUTPUT_FORMAT] = fakeUserData.outputFormat
                it[PrefsKeys.RADIAN_MODE] = fakeUserData.radianMode
                it[PrefsKeys.PARTIAL_HISTORY_VIEW] = fakeUserData.partialHistoryView
                it[PrefsKeys.LATEST_LEFT_SIDE] = fakeUserData.latestLeftSide
                it[PrefsKeys.LATEST_RIGHT_SIDE] = fakeUserData.latestRightSide
                it[PrefsKeys.SHOWN_UNIT_GROUPS] = fakeUserData.shownUnitGroups
                it[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY] = fakeUserData.unitConverterFavoritesOnly
                it[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME] = fakeUserData.unitConverterFormatTime
                it[PrefsKeys.UNIT_CONVERTER_SORTING] = fakeUserData.unitConverterSorting
            }
        }
    }

    @Test
    fun testBackup() = runBlocking{
        // Backup manager also saves the file to disk, but it is not tested here.
        // There is probably no need to test if the data in app is valid since moshi will throw an
        // exceptions if the data in app is invalid. For example, if unitConverterSorting is set to
        // "Qwerty" (this sorting doesn't exist) there will be an exception.
        val actualUserData = backupManager.userDataFromApp()
        val expectedUserData = fakeUserData

        assertEquals(expectedUserData, actualUserData)
    }

    @Test
    fun testRestoreDatastore() = runBlocking{
        // Backup manager also saves the file to disk, but it is not tested here.
        // There is probably no need to test if the data in app is valid since moshi will throw an
        // exceptions if the data in app is invalid. For example, if unitConverterSorting is set to
        // "Qwerty" (this sorting doesn't exist) there will be an exception.
        backupManager.updateDatastore(fakeUserData)

        assertEquals(backupManager.userDataFromApp(), fakeUserData)
    }

    @Test
    fun testRestoreCalculatorHistoryTable() = runBlocking {
        // Data for import
        val fakeUserData2 = fakeUserData.copy(
            calculatorHistoryTable = listOf(
                CalculatorHistoryEntity(
                    timestamp = System.currentTimeMillis(),
                    expression = "69+420",
                    result = "444"
                )
            )
        )
        backupManager.updateCalculatorHistoryTable(fakeUserData2)

        assertEquals(FakeCalculatorHistoryDao.getAllDescending().first(), fakeUserData2.calculatorHistoryTable)
    }

    @Test
    fun testRestoreUnitsTable() = runBlocking {
        // Data for import
        val fakeUserData2 = fakeUserData.copy(
            unitsTable = listOf(
                UnitsEntity(
                    unitId = "UnitId3",
                    isFavorite = false,
                    pairedUnitId = "Pair4",
                    frequency = 123
                )
            )
        )
        backupManager.updateUnitsTable(fakeUserData2)

        assertEquals(FakeUnitsDao.getAllFlow().first(), fakeUserData2.unitsTable)
    }

    @Test
    fun testRestoreTimeZonesTable() = runBlocking {
        // Data for import
        val fakeUserData2 = fakeUserData.copy(
            timeZoneTable = listOf(
                TimeZoneEntity(
                    id = "id3",
                    position = 123,
                    label = "label456"
                )
            )
        )
        backupManager.updateTimeZonesTable(fakeUserData2)

        assertEquals(FakeTimeZoneDao.getFavorites().first(), fakeUserData2.timeZoneTable)
    }
}

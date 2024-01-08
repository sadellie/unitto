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

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.sadellie.unitto.data.database.CalculatorHistoryDao
import com.sadellie.unitto.data.database.TimeZoneDao
import com.sadellie.unitto.data.database.UnitsDao
import com.sadellie.unitto.data.userprefs.PrefsKeys
import com.sadellie.unitto.data.userprefs.getAcButton
import com.sadellie.unitto.data.userprefs.getCustomColor
import com.sadellie.unitto.data.userprefs.getDigitsPrecision
import com.sadellie.unitto.data.userprefs.getEnableAmoledTheme
import com.sadellie.unitto.data.userprefs.getEnableDynamicTheme
import com.sadellie.unitto.data.userprefs.getEnableToolsExperiment
import com.sadellie.unitto.data.userprefs.getEnableVibrations
import com.sadellie.unitto.data.userprefs.getLastReadChangelog
import com.sadellie.unitto.data.userprefs.getLatestLeftSide
import com.sadellie.unitto.data.userprefs.getLatestRightSide
import com.sadellie.unitto.data.userprefs.getMiddleZero
import com.sadellie.unitto.data.userprefs.getMonetMode
import com.sadellie.unitto.data.userprefs.getOutputFormat
import com.sadellie.unitto.data.userprefs.getPartialHistoryView
import com.sadellie.unitto.data.userprefs.getRadianMode
import com.sadellie.unitto.data.userprefs.getRpnMode
import com.sadellie.unitto.data.userprefs.getSeparator
import com.sadellie.unitto.data.userprefs.getShownUnitGroups
import com.sadellie.unitto.data.userprefs.getStartingScreen
import com.sadellie.unitto.data.userprefs.getSystemFont
import com.sadellie.unitto.data.userprefs.getThemingMode
import com.sadellie.unitto.data.userprefs.getUnitConverterFavoritesOnly
import com.sadellie.unitto.data.userprefs.getUnitConverterFormatTime
import com.sadellie.unitto.data.userprefs.getUnitConverterSorting
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.inject.Inject

@OptIn(ExperimentalStdlibApi::class)
class BackupManager @Inject constructor(
    @ApplicationContext private val mContext: Context,
    private val dataStore: DataStore<Preferences>,
    private val calculatorHistoryDao: CalculatorHistoryDao,
    private val unitsDao: UnitsDao,
    private val timeZoneDao: TimeZoneDao,
) {
    private val moshi: Moshi = Moshi.Builder()
        .add(UserDataTableAdapter())
        .build()
    private val jsonAdapter: JsonAdapter<UserData> = moshi.adapter<UserData>()
    private val auth = "com.sadellie.unitto.BackupManager"

    suspend fun backup(): Uri = withContext(Dispatchers.IO) {
        val userData = userDataFromApp()
        // save to disk
        val tempFile = File.createTempFile("backup", ".unitto", mContext.cacheDir)
        tempFile.writeText(jsonAdapter.toJson(userData))

        return@withContext FileProvider.getUriForFile(mContext, auth, tempFile)
    }

    suspend fun restore(uri: Uri) = withContext(Dispatchers.IO) {
        val jsonContent = StringBuilder()
        mContext.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    jsonContent.append(line)
                    line = reader.readLine()
                }
            }
        }

        // Return error
        val userData: UserData = jsonAdapter.fromJson(jsonContent.toString())
            ?: return@withContext IllegalArgumentException("Can't parse: $jsonContent")

        // Apply tables
        updateCalculatorHistoryTable(userData)
        updateUnitsTable(userData)
        updateTimeZonesTable(userData)

        // Apply datastore prefs
        // Datastore settings are restored at the end, because it will trigger recomposition of the
        // entire app composable and all jobs in ViewModels will be canceled
        updateDatastore(userData)
    }

    internal suspend fun userDataFromApp(): UserData {
        val data = dataStore.data.first()
        val calculatorHistoryTable = calculatorHistoryDao.getAllDescending().first()
        val unitsTableData = unitsDao.getAllFlow().first()
        val timeZoneTableData = timeZoneDao.getFavorites().first()

        return UserData(
            themingMode = data.getThemingMode(),
            enableDynamicTheme = data.getEnableDynamicTheme(),
            enableAmoledTheme = data.getEnableAmoledTheme(),
            customColor = data.getCustomColor(),
            monetMode = data.getMonetMode(),
            startingScreen = data.getStartingScreen(),
            enableToolsExperiment = data.getEnableToolsExperiment(),
            systemFont = data.getSystemFont(),
            lastReadChangelog = data.getLastReadChangelog(),
            enableVibrations = data.getEnableVibrations(),
            middleZero = data.getMiddleZero(),
            acButton = data.getAcButton(),
            rpnMode = data.getRpnMode(),
            precision = data.getDigitsPrecision(),
            separator = data.getSeparator(),
            outputFormat = data.getOutputFormat(),
            radianMode = data.getRadianMode(),
            partialHistoryView = data.getPartialHistoryView(),
            latestLeftSide = data.getLatestLeftSide(),
            latestRightSide = data.getLatestRightSide(),
            shownUnitGroups = data.getShownUnitGroups().joinToString(","),
            unitConverterFavoritesOnly = data.getUnitConverterFavoritesOnly(),
            unitConverterFormatTime = data.getUnitConverterFormatTime(),
            unitConverterSorting = data.getUnitConverterSorting().name,
            calculatorHistoryTable = calculatorHistoryTable,
            unitsTable = unitsTableData,
            timeZoneTable = timeZoneTableData,
        )
    }

    internal suspend fun updateDatastore(userData: UserData) {
        dataStore.edit { it.clear() }
        dataStore.edit {
            it[PrefsKeys.THEMING_MODE] = userData.themingMode
            it[PrefsKeys.ENABLE_DYNAMIC_THEME] = userData.enableDynamicTheme
            it[PrefsKeys.ENABLE_AMOLED_THEME] = userData.enableAmoledTheme
            it[PrefsKeys.CUSTOM_COLOR] = userData.customColor
            it[PrefsKeys.MONET_MODE] = userData.monetMode
            it[PrefsKeys.STARTING_SCREEN] = userData.startingScreen
            it[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] = userData.enableToolsExperiment
            it[PrefsKeys.SYSTEM_FONT] = userData.systemFont
            it[PrefsKeys.LAST_READ_CHANGELOG] = userData.lastReadChangelog
            it[PrefsKeys.ENABLE_VIBRATIONS] = userData.enableVibrations
            it[PrefsKeys.MIDDLE_ZERO] = userData.middleZero
            it[PrefsKeys.AC_BUTTON] = userData.acButton
            it[PrefsKeys.RPN_MODE] = userData.rpnMode

            // FORMATTER
            it[PrefsKeys.DIGITS_PRECISION] = userData.precision
            it[PrefsKeys.SEPARATOR] = userData.separator
            it[PrefsKeys.OUTPUT_FORMAT] = userData.outputFormat

            // CALCULATOR
            it[PrefsKeys.RADIAN_MODE] = userData.radianMode
            it[PrefsKeys.PARTIAL_HISTORY_VIEW] = userData.partialHistoryView

            // UNIT CONVERTER
            it[PrefsKeys.LATEST_LEFT_SIDE] = userData.latestLeftSide
            it[PrefsKeys.LATEST_RIGHT_SIDE] = userData.latestRightSide
            it[PrefsKeys.SHOWN_UNIT_GROUPS] = userData.shownUnitGroups
            it[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY] = userData.unitConverterFavoritesOnly
            it[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME] = userData.unitConverterFormatTime
            it[PrefsKeys.UNIT_CONVERTER_SORTING] = userData.unitConverterSorting
        }
    }

    internal suspend fun updateCalculatorHistoryTable(userData: UserData) {
        calculatorHistoryDao.clear()
        userData.calculatorHistoryTable.forEach { calculatorHistoryDao.insert(it) }
    }

    internal suspend fun updateUnitsTable(userData: UserData) {
        unitsDao.clear()
        userData.unitsTable.forEach { unitsDao.insertUnit(it) }
    }

    internal suspend fun updateTimeZonesTable(userData: UserData) {
        timeZoneDao.clear()
        userData.timeZoneTable.forEach { timeZoneDao.insert(it) }
    }
}

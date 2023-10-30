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

package com.sadellie.unitto.data.userprefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.Separator
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.userprefs.AboutPreferences
import com.sadellie.unitto.data.model.userprefs.AddSubtractPreferences
import com.sadellie.unitto.data.model.userprefs.AppPreferences
import com.sadellie.unitto.data.model.userprefs.CalculatorPreferences
import com.sadellie.unitto.data.model.userprefs.ConverterPreferences
import com.sadellie.unitto.data.model.userprefs.DisplayPreferences
import com.sadellie.unitto.data.model.userprefs.FormattingPreferences
import com.sadellie.unitto.data.model.userprefs.GeneralPreferences
import com.sadellie.unitto.data.model.userprefs.StartingScreenPreferences
import com.sadellie.unitto.data.model.userprefs.UnitGroupsPreferences
import com.sadellie.unitto.data.units.MyUnitIDS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : UserPreferencesRepository {
    private val data = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }

    override val appPrefs: Flow<AppPreferences> = data
        .map { preferences ->
            AppPreferencesImpl(
                themingMode = preferences.getThemingMode(),
                enableDynamicTheme = preferences.getEnableDynamicTheme(),
                enableAmoledTheme = preferences.getEnableAmoledTheme(),
                customColor = preferences.getCustomColor(),
                monetMode = preferences.getMonetMode(),
                startingScreen = preferences.getStartingScreen(),
                enableToolsExperiment = preferences.getEnableToolsExperiment(),
                systemFont = preferences.getSystemFont()
            )
        }

    override val generalPrefs: Flow<GeneralPreferences> = data
        .map { preferences ->
            GeneralPreferencesImpl(
                enableVibrations = preferences.getEnableVibrations(),
            )
        }

    override val calculatorPrefs: Flow<CalculatorPreferences> = data
        .map { preferences ->
            CalculatorPreferencesImpl(
                radianMode = preferences.getRadianMode(),
                enableVibrations = preferences.getEnableVibrations(),
                separator = preferences.getSeparator(),
                middleZero = preferences.getMiddleZero(),
                partialHistoryView = preferences.getPartialHistoryView(),
                precision = preferences.getDigitsPrecision(),
                outputFormat = preferences.getOutputFormat(),
                acButton = preferences.getAcButton(),
            )
        }

    override val converterPrefs: Flow<ConverterPreferences> = data
        .map { preferences ->
            ConverterPreferencesImpl(
                enableVibrations = preferences.getEnableVibrations(),
                separator = preferences.getSeparator(),
                middleZero = preferences.getMiddleZero(),
                precision = preferences.getDigitsPrecision(),
                outputFormat = preferences.getOutputFormat(),
                unitConverterFormatTime = preferences.getUnitConverterFormatTime(),
                unitConverterSorting = preferences.getUnitConverterSorting(),
                shownUnitGroups = preferences.getShownUnitGroups(),
                unitConverterFavoritesOnly = preferences.getUnitConverterFavoritesOnly(),
                enableToolsExperiment = preferences.getEnableToolsExperiment(),
                latestLeftSideUnit = preferences.getLatestLeftSide(),
                latestRightSideUnit = preferences.getLatestRightSide(),
                acButton = preferences.getAcButton(),
            )
        }

    override val displayPrefs: Flow<DisplayPreferences> = data
        .map { preferences ->
            DisplayPreferencesImpl(
                systemFont = preferences.getSystemFont(),
                middleZero = preferences.getMiddleZero(),
                acButton = preferences.getAcButton(),
            )
        }

    override val formattingPrefs: Flow<FormattingPreferences> = data
        .map { preferences ->
            FormattingPreferencesImpl(
                digitsPrecision = preferences.getDigitsPrecision(),
                separator = preferences.getSeparator(),
                outputFormat = preferences.getOutputFormat(),
            )
        }

    override val unitGroupsPrefs: Flow<UnitGroupsPreferences> = data
        .map { preferences ->
            UnitGroupsPreferencesImpl(
                shownUnitGroups = preferences.getShownUnitGroups(),
            )
        }

    override val addSubtractPrefs: Flow<AddSubtractPreferences> = data
        .map { preferences ->
            AddSubtractPreferencesImpl(
                separator = preferences.getSeparator(),
                enableVibrations = preferences.getEnableVibrations(),
            )
        }

    override val aboutPrefs: Flow<AboutPreferences> = data
        .map { preferences ->
            AboutPreferencesImpl(
                enableToolsExperiment = preferences.getEnableToolsExperiment()
            )
        }

    override val startingScreenPrefs: Flow<StartingScreenPreferences> = data
        .map { preferences ->
            StartingScreenPreferencesImpl(
                startingScreen = preferences.getStartingScreen(),
            )
        }

    override suspend fun updateDigitsPrecision(precision: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.DIGITS_PRECISION] = precision
        }
    }

    override suspend fun updateSeparator(separator: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SEPARATOR] = separator
        }
    }

    override suspend fun updateOutputFormat(outputFormat: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.OUTPUT_FORMAT] = outputFormat
        }
    }

    override suspend fun updateLatestPairOfUnits(unitFrom: AbstractUnit, unitTo: AbstractUnit) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.LATEST_LEFT_SIDE] = unitFrom.id
            preferences[PrefsKeys.LATEST_RIGHT_SIDE] = unitTo.id
        }
    }

    override suspend fun updateThemingMode(themingMode: String) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.THEMING_MODE] = themingMode
        }
    }

    override suspend fun updateDynamicTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_DYNAMIC_THEME] = enabled
        }
    }

    override suspend fun updateAmoledTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_AMOLED_THEME] = enabled
        }
    }

    override suspend fun updateCustomColor(color: Long) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.CUSTOM_COLOR] = color
        }
    }

    override suspend fun updateMonetMode(monetMode: String) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.MONET_MODE] = monetMode
        }
    }

    override suspend fun updateStartingScreen(startingScreen: String) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.STARTING_SCREEN] = startingScreen
        }
    }

    override suspend fun updateShownUnitGroups(shownUnitGroups: List<UnitGroup>) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SHOWN_UNIT_GROUPS] = shownUnitGroups.joinToString(",")
        }
    }

    override suspend fun updateVibrations(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_VIBRATIONS] = enabled
        }
    }

    override suspend fun updateMiddleZero(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.MIDDLE_ZERO] = enabled
        }
    }

    override suspend fun updateToolsExperiment(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] = enabled
        }
    }

    override suspend fun updateRadianMode(radianMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.RADIAN_MODE] = radianMode
        }
    }

    override suspend fun updateUnitConverterFavoritesOnly(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY] = enabled
        }
    }

    override suspend fun updateUnitConverterFormatTime(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME] = enabled
        }
    }

    override suspend fun updateUnitConverterSorting(sorting: UnitsListSorting) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.UNIT_CONVERTER_SORTING] = sorting.name
        }
    }

    override suspend fun updateSystemFont(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SYSTEM_FONT] = enabled
        }
    }

    override suspend fun updatePartialHistoryView(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.PARTIAL_HISTORY_VIEW] = enabled
        }
    }

    override suspend fun updateAcButton(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.AC_BUTTON] = enabled
        }
    }
}

private fun Preferences.getEnableDynamicTheme(): Boolean {
    return this[PrefsKeys.ENABLE_DYNAMIC_THEME] ?: true
}

private fun Preferences.getThemingMode(): String {
    return this[PrefsKeys.THEMING_MODE] ?: ""
}

private fun Preferences.getEnableAmoledTheme(): Boolean {
    return this[PrefsKeys.ENABLE_AMOLED_THEME] ?: false
}

private fun Preferences.getCustomColor(): Long {
    return this[PrefsKeys.CUSTOM_COLOR] ?: Long.MIN_VALUE
}

private fun Preferences.getMonetMode(): String {
    return this[PrefsKeys.MONET_MODE] ?: ""
}

private fun Preferences.getStartingScreen(): String {
    return this[PrefsKeys.STARTING_SCREEN]
        ?: TopLevelDestinations.Calculator.graph
}

private fun Preferences.getEnableToolsExperiment(): Boolean {
    return this[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] ?: false
}

private fun Preferences.getSystemFont(): Boolean {
    return this[PrefsKeys.SYSTEM_FONT] ?: false
}

private fun Preferences.getEnableVibrations(): Boolean {
    return this[PrefsKeys.ENABLE_VIBRATIONS] ?: true
}

private fun Preferences.getRadianMode(): Boolean {
    return this[PrefsKeys.RADIAN_MODE] ?: true
}

private fun Preferences.getSeparator(): Int {
    return this[PrefsKeys.SEPARATOR] ?: Separator.SPACE
}

private fun Preferences.getMiddleZero(): Boolean {
    return this[PrefsKeys.MIDDLE_ZERO] ?: false
}

private fun Preferences.getPartialHistoryView(): Boolean {
    return this[PrefsKeys.PARTIAL_HISTORY_VIEW] ?: true
}

private fun Preferences.getDigitsPrecision(): Int {
    return this[PrefsKeys.DIGITS_PRECISION] ?: 3
}

private fun Preferences.getOutputFormat(): Int {
    return this[PrefsKeys.OUTPUT_FORMAT] ?: OutputFormat.PLAIN
}

private fun Preferences.getUnitConverterFormatTime(): Boolean {
    return this[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME] ?: false
}

private fun Preferences.getUnitConverterSorting(): UnitsListSorting {
    return this[PrefsKeys.UNIT_CONVERTER_SORTING]
        ?.let { UnitsListSorting.valueOf(it) } ?: UnitsListSorting.USAGE
}

private fun Preferences.getShownUnitGroups(): List<UnitGroup> {
    return this[PrefsKeys.SHOWN_UNIT_GROUPS]?.letTryOrNull { list ->
        list.ifEmpty { return@letTryOrNull listOf() }.split(",")
            .map { UnitGroup.valueOf(it) }
    } ?: ALL_UNIT_GROUPS
}

private fun Preferences.getUnitConverterFavoritesOnly(): Boolean {
    return this[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY]
        ?: false
}

private fun Preferences.getLatestLeftSide(): String {
    return this[PrefsKeys.LATEST_LEFT_SIDE] ?: MyUnitIDS.kilometer
}

private fun Preferences.getLatestRightSide(): String {
    return this[PrefsKeys.LATEST_RIGHT_SIDE] ?: MyUnitIDS.mile
}

private fun Preferences.getAcButton(): Boolean {
    return this[PrefsKeys.AC_BUTTON] ?: false
}

private inline fun <T, R> T.letTryOrNull(block: (T) -> R): R? = try {
    this?.let(block)
} catch (e: Exception) {
    null
}

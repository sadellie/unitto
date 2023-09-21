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

import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.Separator
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import io.github.sadellie.themmo.MonetMode
import io.github.sadellie.themmo.ThemingMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private object PrefsKeys {
    val THEMING_MODE = stringPreferencesKey("THEMING_MODE_PREF_KEY")
    val ENABLE_DYNAMIC_THEME = booleanPreferencesKey("ENABLE_DYNAMIC_THEME_PREF_KEY")
    val ENABLE_AMOLED_THEME = booleanPreferencesKey("ENABLE_AMOLED_THEME_PREF_KEY")
    val CUSTOM_COLOR = longPreferencesKey("CUSTOM_COLOR_PREF_KEY")
    val MONET_MODE = stringPreferencesKey("MONET_MODE_PREF_KEY")
    val DIGITS_PRECISION = intPreferencesKey("DIGITS_PRECISION_PREF_KEY")
    val SEPARATOR = intPreferencesKey("SEPARATOR_PREF_KEY")
    val OUTPUT_FORMAT = intPreferencesKey("OUTPUT_FORMAT_PREF_KEY")
    val LATEST_LEFT_SIDE = stringPreferencesKey("LATEST_LEFT_SIDE_PREF_KEY")
    val LATEST_RIGHT_SIDE = stringPreferencesKey("LATEST_RIGHT_SIDE_PREF_KEY")
    val SHOWN_UNIT_GROUPS = stringPreferencesKey("SHOWN_UNIT_GROUPS_PREF_KEY")
    val ENABLE_VIBRATIONS = booleanPreferencesKey("ENABLE_VIBRATIONS_PREF_KEY")
    val ENABLE_TOOLS_EXPERIMENT = booleanPreferencesKey("ENABLE_TOOLS_EXPERIMENT_PREF_KEY")
    val STARTING_SCREEN = stringPreferencesKey("STARTING_SCREEN_PREF_KEY")
    val RADIAN_MODE = booleanPreferencesKey("RADIAN_MODE_PREF_KEY")
    val UNIT_CONVERTER_FAVORITES_ONLY =
        booleanPreferencesKey("UNIT_CONVERTER_FAVORITES_ONLY_PREF_KEY")
    val UNIT_CONVERTER_FORMAT_TIME = booleanPreferencesKey("UNIT_CONVERTER_FORMAT_TIME_PREF_KEY")
    val UNIT_CONVERTER_SORTING = stringPreferencesKey("UNIT_CONVERTER_SORTING_PREF_KEY")
    val MIDDLE_ZERO = booleanPreferencesKey("MIDDLE_ZERO_PREF_KEY")
    val SYSTEM_FONT = booleanPreferencesKey("SYSTEM_FONT_PREF_KEY")
    val PARTIAL_HISTORY_VIEW = booleanPreferencesKey("PARTIAL_HISTORY_VIEW_PREF_KEY")
}

data class AppPreferences(
    val themingMode: ThemingMode = ThemingMode.AUTO,
    val enableDynamicTheme: Boolean = true,
    val enableAmoledTheme: Boolean = false,
    val customColor: Color = Color.Unspecified,
    val monetMode: MonetMode = MonetMode.values().first(),
    val startingScreen: String = TopLevelDestinations.Calculator.graph,
    val enableToolsExperiment: Boolean = false,
    val systemFont: Boolean = false,
)

data class GeneralPreferences(
    val enableVibrations: Boolean = true,
    val middleZero: Boolean = false,
)

data class CalculatorPreferences(
    val radianMode: Boolean = true,
    val enableVibrations: Boolean = true,
    val separator: Int = Separator.SPACE,
    val middleZero: Boolean = false,
    val partialHistoryView: Boolean = true,
    val precision: Int = 3,
    val outputFormat: Int = OutputFormat.PLAIN,
)

data class ConverterPreferences(
    val enableVibrations: Boolean = true,
    val separator: Int = Separator.SPACE,
    val middleZero: Boolean = false,
    val precision: Int = 3,
    val outputFormat: Int = OutputFormat.PLAIN,
    val unitConverterFormatTime: Boolean = false,
    val unitConverterSorting: UnitsListSorting = UnitsListSorting.USAGE,
    val shownUnitGroups: List<UnitGroup> = ALL_UNIT_GROUPS,
    val unitConverterFavoritesOnly: Boolean = false,
    val enableToolsExperiment: Boolean = false,
    val latestLeftSideUnit: String = MyUnitIDS.kilometer,
    val latestRightSideUnit: String = MyUnitIDS.mile,
)

data class ThemePreferences(
    val systemFont: Boolean = false,
)

data class FormattingPreferences(
    val digitsPrecision: Int = 3,
    val separator: Int = Separator.SPACE,
    val outputFormat: Int = OutputFormat.PLAIN,
)

data class UnitGroupsPreferences(
    val shownUnitGroups: List<UnitGroup> = ALL_UNIT_GROUPS,
)

data class AddSubtractPreferences(
    val separator: Int = Separator.SPACE,
    val enableVibrations: Boolean = true,
)

data class AboutPreferences(
    val enableToolsExperiment: Boolean = false,
)

data class StartingScreenPreferences(
    val startingScreen: String = TopLevelDestinations.Calculator.graph,
)

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val data = dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }

    val appPrefs: Flow<AppPreferences> = data
        .map { preferences ->
            AppPreferences(
                themingMode = preferences[PrefsKeys.THEMING_MODE]?.letTryOrNull {
                    ThemingMode.valueOf(it)
                }
                    ?: ThemingMode.AUTO,
                enableDynamicTheme = preferences[PrefsKeys.ENABLE_DYNAMIC_THEME] ?: true,
                enableAmoledTheme = preferences[PrefsKeys.ENABLE_AMOLED_THEME] ?: false,
                customColor = preferences[PrefsKeys.CUSTOM_COLOR]?.letTryOrNull { Color(it.toULong()) }
                    ?: Color.Unspecified,
                monetMode = preferences[PrefsKeys.MONET_MODE]?.letTryOrNull { MonetMode.valueOf(it) }
                    ?: MonetMode.values().first(),
                startingScreen = preferences[PrefsKeys.STARTING_SCREEN]
                    ?: TopLevelDestinations.Calculator.graph,
                enableToolsExperiment = preferences[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] ?: false,
                systemFont = preferences[PrefsKeys.SYSTEM_FONT] ?: false
            )
        }

    val generalPrefs: Flow<GeneralPreferences> = data
        .map { preferences ->
            GeneralPreferences(
                enableVibrations = preferences[PrefsKeys.ENABLE_VIBRATIONS] ?: true,
                middleZero = preferences[PrefsKeys.MIDDLE_ZERO] ?: false,
            )
        }

    val calculatorPrefs: Flow<CalculatorPreferences> = data
        .map { preferences ->
            CalculatorPreferences(
                radianMode = preferences[PrefsKeys.RADIAN_MODE] ?: true,
                enableVibrations = preferences[PrefsKeys.ENABLE_VIBRATIONS] ?: true,
                separator = preferences[PrefsKeys.SEPARATOR] ?: Separator.SPACE,
                middleZero = preferences[PrefsKeys.MIDDLE_ZERO] ?: false,
                partialHistoryView = preferences[PrefsKeys.PARTIAL_HISTORY_VIEW] ?: true,
                precision = preferences[PrefsKeys.DIGITS_PRECISION] ?: 3,
                outputFormat = preferences[PrefsKeys.OUTPUT_FORMAT] ?: OutputFormat.PLAIN
            )
        }

    val converterPrefs: Flow<ConverterPreferences> = data
        .map { preferences ->
            ConverterPreferences(
                enableVibrations = preferences[PrefsKeys.ENABLE_VIBRATIONS] ?: true,
                separator = preferences[PrefsKeys.SEPARATOR] ?: Separator.SPACE,
                middleZero = preferences[PrefsKeys.MIDDLE_ZERO] ?: false,
                precision = preferences[PrefsKeys.DIGITS_PRECISION] ?: 3,
                outputFormat = preferences[PrefsKeys.OUTPUT_FORMAT] ?: OutputFormat.PLAIN,
                unitConverterFormatTime = preferences[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME]
                    ?: false,
                unitConverterSorting = preferences[PrefsKeys.UNIT_CONVERTER_SORTING]
                    ?.let { UnitsListSorting.valueOf(it) } ?: UnitsListSorting.USAGE,
                shownUnitGroups = preferences[PrefsKeys.SHOWN_UNIT_GROUPS]?.letTryOrNull { list ->
                    list.ifEmpty { return@letTryOrNull listOf() }.split(",")
                        .map { UnitGroup.valueOf(it) }
                } ?: ALL_UNIT_GROUPS,
                unitConverterFavoritesOnly = preferences[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY]
                    ?: false,
                enableToolsExperiment = preferences[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] ?: false,
                latestLeftSideUnit = preferences[PrefsKeys.LATEST_LEFT_SIDE] ?: MyUnitIDS.kilometer,
                latestRightSideUnit = preferences[PrefsKeys.LATEST_RIGHT_SIDE] ?: MyUnitIDS.mile,
            )
        }

    val themePrefs: Flow<ThemePreferences> = data
        .map { preferences ->
            ThemePreferences(
                systemFont = preferences[PrefsKeys.SYSTEM_FONT] ?: false
            )
        }

    val formattingPrefs: Flow<FormattingPreferences> = data
        .map { preferences ->
            FormattingPreferences(
                digitsPrecision = preferences[PrefsKeys.DIGITS_PRECISION] ?: 3,
                separator = preferences[PrefsKeys.SEPARATOR] ?: Separator.SPACE,
                outputFormat = preferences[PrefsKeys.OUTPUT_FORMAT] ?: OutputFormat.PLAIN,
            )
        }

    val unitGroupsPrefs: Flow<UnitGroupsPreferences> = data
        .map { preferences ->
            UnitGroupsPreferences(
                shownUnitGroups = preferences[PrefsKeys.SHOWN_UNIT_GROUPS]?.letTryOrNull { list ->
                    list.ifEmpty { return@letTryOrNull listOf() }.split(",")
                        .map { UnitGroup.valueOf(it) }
                } ?: ALL_UNIT_GROUPS,
            )
        }

    val addSubtractPrefs: Flow<AddSubtractPreferences> = data
        .map { preferences ->
            AddSubtractPreferences(
                separator = preferences[PrefsKeys.SEPARATOR] ?: Separator.SPACE,
                enableVibrations = preferences[PrefsKeys.ENABLE_VIBRATIONS] ?: true,
            )
        }

    val aboutPrefs: Flow<AboutPreferences> = data
        .map { preferences ->
            AboutPreferences(
                enableToolsExperiment = preferences[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] ?: false
            )
        }

    val startingScreenPrefs: Flow<StartingScreenPreferences> = data
        .map { preferences ->
            StartingScreenPreferences(
                startingScreen = preferences[PrefsKeys.STARTING_SCREEN]
                    ?: TopLevelDestinations.Calculator.graph,
            )
        }

    suspend fun updateDigitsPrecision(precision: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.DIGITS_PRECISION] = precision
        }
    }

    suspend fun updateSeparator(separator: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SEPARATOR] = separator
        }
    }

    suspend fun updateOutputFormat(outputFormat: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.OUTPUT_FORMAT] = outputFormat
        }
    }

    suspend fun updateLatestPairOfUnits(unitFrom: AbstractUnit, unitTo: AbstractUnit) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.LATEST_LEFT_SIDE] = unitFrom.id
            preferences[PrefsKeys.LATEST_RIGHT_SIDE] = unitTo.id
        }
    }

    suspend fun updateThemingMode(themingMode: ThemingMode) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.THEMING_MODE] = themingMode.name
        }
    }

    suspend fun updateDynamicTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_DYNAMIC_THEME] = enabled
        }
    }

    suspend fun updateAmoledTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_AMOLED_THEME] = enabled
        }
    }

    suspend fun updateCustomColor(color: Color) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.CUSTOM_COLOR] = color.value.toLong()
        }
    }

    suspend fun updateMonetMode(monetMode: MonetMode) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.MONET_MODE] = monetMode.name
        }
    }

    suspend fun updateStartingScreen(startingScreen: String) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.STARTING_SCREEN] = startingScreen
        }
    }

    suspend fun updateShownUnitGroups(shownUnitGroups: List<UnitGroup>) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SHOWN_UNIT_GROUPS] = shownUnitGroups.joinToString(",")
        }
    }

    suspend fun updateVibrations(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_VIBRATIONS] = enabled
        }
    }

    suspend fun updateMiddleZero(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.MIDDLE_ZERO] = enabled
        }
    }

    suspend fun updateToolsExperiment(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_TOOLS_EXPERIMENT] = enabled
        }
    }

    suspend fun updateRadianMode(radianMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.RADIAN_MODE] = radianMode
        }
    }

    suspend fun updateUnitConverterFavoritesOnly(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.UNIT_CONVERTER_FAVORITES_ONLY] = enabled
        }
    }

    suspend fun updateUnitConverterFormatTime(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.UNIT_CONVERTER_FORMAT_TIME] = enabled
        }
    }

    suspend fun updateUnitConverterSorting(sorting: UnitsListSorting) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.UNIT_CONVERTER_SORTING] = sorting.name
        }
    }

    suspend fun updateSystemFont(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SYSTEM_FONT] = enabled
        }
    }

    suspend fun updatePartialHistoryView(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.PARTIAL_HISTORY_VIEW] = enabled
        }
    }

    private inline fun <T, R> T.letTryOrNull(block: (T) -> R): R? = try {
        this?.let(block)
    } catch (e: Exception) {
        null
    }
}

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

package com.sadellie.unitto.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import io.github.sadellie.themmo.ThemingMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

/**
 * Represents user preferences that are user across the app
 *
 * @property themingMode [ThemingMode] from Themmo
 * @property enableDynamicTheme Use dynamic color scheme
 * @property enableAmoledTheme Use amoled color scheme
 * @property digitsPrecision Current [PRECISIONS]. Number of digits in fractional part
 * @property separator Current [Separator] that used to separate thousands
 * @property outputFormat Current [OutputFormat] that is applied to converted value (not input)
 * @property latestLeftSideUnit Latest [AbstractUnit] that was on the left side
 * @property latestRightSideUnit Latest [AbstractUnit] that was on the right side
 * @property enableAnalytics Whether or not user wants to share application usage data
 */
data class UserPreferences(
    val themingMode: ThemingMode,
    val enableDynamicTheme: Boolean,
    val enableAmoledTheme: Boolean,
    val digitsPrecision: Int,
    val separator: Int,
    val outputFormat: Int,
    val latestLeftSideUnit: String,
    val latestRightSideUnit: String,
    val enableAnalytics: Boolean
)

/**
 * Repository that works with DataStore
 */
class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    /**
     * Keys for DataStore
     */
    private object PrefsKeys {
        val THEMING_MODE = stringPreferencesKey("THEMING_MODE_PREF_KEY")
        val ENABLE_DYNAMIC_THEME = booleanPreferencesKey("ENABLE_DYNAMIC_THEME_PREF_KEY")
        val ENABLE_AMOLED_THEME = booleanPreferencesKey("ENABLE_AMOLED_THEME_PREF_KEY")
        val DIGITS_PRECISION = intPreferencesKey("DIGITS_PRECISION_PREF_KEY")
        val SEPARATOR = intPreferencesKey("SEPARATOR_PREF_KEY")
        val OUTPUT_FORMAT = intPreferencesKey("OUTPUT_FORMAT_PREF_KEY")
        val LATEST_LEFT_SIDE = stringPreferencesKey("LATEST_LEFT_SIDE_PREF_KEY")
        val LATEST_RIGHT_SIDE = stringPreferencesKey("LATEST_RIGHT_SIDE_PREF_KEY")
        val ENABLE_ANALYTICS = booleanPreferencesKey("ENABLE_ANALYTICS_PREF_KEY")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val themingMode: ThemingMode =
                preferences[PrefsKeys.THEMING_MODE]?.let { ThemingMode.valueOf(it) }
                    ?: ThemingMode.AUTO
            val enableDynamicTheme: Boolean =
                preferences[PrefsKeys.ENABLE_DYNAMIC_THEME] ?: false
            val enableAmoledTheme: Boolean =
                preferences[PrefsKeys.ENABLE_AMOLED_THEME] ?: false
            val digitsPrecision: Int =
                preferences[PrefsKeys.DIGITS_PRECISION] ?: 3
            val separator: Int =
                preferences[PrefsKeys.SEPARATOR] ?: Separator.SPACES
            val outputFormat: Int =
                preferences[PrefsKeys.OUTPUT_FORMAT] ?: OutputFormat.PLAIN
            val latestLeftSideUnit: String =
                preferences[PrefsKeys.LATEST_LEFT_SIDE] ?: MyUnitIDS.kilometer
            val latestRightSideUnit: String =
                preferences[PrefsKeys.LATEST_RIGHT_SIDE] ?: MyUnitIDS.mile
            val enableAnalytics: Boolean =
                preferences[PrefsKeys.ENABLE_ANALYTICS] ?: true

            UserPreferences(
                themingMode = themingMode,
                enableDynamicTheme = enableDynamicTheme,
                enableAmoledTheme = enableAmoledTheme,
                digitsPrecision = digitsPrecision,
                separator = separator,
                outputFormat = outputFormat,
                latestLeftSideUnit = latestLeftSideUnit,
                latestRightSideUnit = latestRightSideUnit,
                enableAnalytics = enableAnalytics
            )
        }

    /**
     * Update precision preference in DataStore
     *
     * @param precision One of the [PRECISIONS] to change to
     */
    suspend fun updateDigitsPrecision(precision: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.DIGITS_PRECISION] = precision
        }
    }

    /**
     * Update separator preference in DataStore
     *
     * @param separator One of the [Separator] to change to
     */
    suspend fun updateSeparator(separator: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.SEPARATOR] = separator
        }
    }

    /**
     * Update outputFormat preference in DataStore
     *
     * @param outputFormat One of the [OutputFormat] to change to
     */
    suspend fun updateOutputFormat(outputFormat: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.OUTPUT_FORMAT] = outputFormat
        }
    }

    /**
     * Update analytics preference in DataStore
     *
     * @param enableAnalytics True if user wants to share data, False if not
     */
    suspend fun updateEnableAnalytics(enableAnalytics: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_ANALYTICS] = enableAnalytics
        }
    }

    /**
     * Update latest used pair of [AbstractUnit] in DataStore. Need it so when user restarts the app,
     * this pair will be already set.
     *
     * @param leftSideUnit [AbstractUnit] on the left
     * @param rightSideUnit [AbstractUnit] on the right
     */
    suspend fun updateLatestPairOfUnits(leftSideUnit: AbstractUnit, rightSideUnit: AbstractUnit) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.LATEST_LEFT_SIDE] = leftSideUnit.unitId
            preferences[PrefsKeys.LATEST_RIGHT_SIDE] = rightSideUnit.unitId
        }
    }

    /**
     * Update [ThemingMode]. Saves value as a string.
     *
     * @param themingMode [ThemingMode] to save.
     */
    suspend fun updateThemingMode(themingMode: ThemingMode) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.THEMING_MODE] = themingMode.name
        }
    }

    /**
     * Update preference on whether or not generate color scheme from device wallpaper.
     *
     * @param enabled True if user wants to enable this feature.
     */
    suspend fun updateDynamicTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_DYNAMIC_THEME] = enabled
        }
    }

    /**
     * Update preference on whether or not use true black colors.
     *
     * @param enabled True if user wants to enable this feature.
     */
    suspend fun updateAmoledTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.ENABLE_AMOLED_THEME] = enabled
        }
    }
}

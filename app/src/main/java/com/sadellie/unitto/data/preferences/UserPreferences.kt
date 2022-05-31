package com.sadellie.unitto.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.MyUnitIDS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


// It's at the top level to make DataStore singleton
// DON'T TOUCH STRINGS!!!
private val Context.settingsDataStore by preferencesDataStore("settings")

/**
 * Represents user preferences that are user across the app
 *
 * @property currentAppTheme Current [AppTheme] to be used
 * @property digitsPrecision Current [PRECISIONS]. Number of digits in fractional part
 * @property separator Current [Separator] that used to separate thousands
 * @property outputFormat Current [OutputFormat] that is applied to converted value (not input)
 * @property latestLeftSideUnit Latest [AbstractUnit] that was on the left side
 * @property latestRightSideUnit Latest [AbstractUnit] that was on the right side
 * @property enableAnalytics Whether or not user wants to share application usage data
 */
data class UserPreferences(
    val currentAppTheme: Int,
    val digitsPrecision: Int,
    val separator: Int,
    val outputFormat: Int,
    val latestLeftSideUnit: String,
    val latestRightSideUnit: String,
    val enableAnalytics: Boolean
)

/**
 * Repository that works with DataStore
 *
 * @property context
 */
class UserPreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.settingsDataStore

    /**
     * Keys for DataStore
     */
    private object PrefsKeys {
        val CURRENT_APP_THEME = intPreferencesKey("CURRENT_APP_THEME")
        val DIGITS_PRECISION = intPreferencesKey("DIGITS_PRECISION_PREF_KEY")
        val SEPARATOR = intPreferencesKey("SEPARATOR_PREF_KEY")
        val OUTPUT_FORMAT = intPreferencesKey("OUTPUT_FORMAT_PREF_KEY")
        val LATEST_LEFT_SIDE = stringPreferencesKey("LATEST_LEFT_SIDE_PREF_KEY")
        val LATEST_RIGHT_SIDE = stringPreferencesKey("LATEST_RIGHT_SIDE_PREF_KEY")
        val ENABLE_ANALYTICS = booleanPreferencesKey("ENABLE_ANALYTICS_PREF_KEY")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .map { preferences ->
            val currentAppTheme: Int =
                preferences[PrefsKeys.CURRENT_APP_THEME] ?: AppTheme.AUTO
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
                currentAppTheme = currentAppTheme,
                digitsPrecision = digitsPrecision,
                separator = separator,
                outputFormat = outputFormat,
                latestLeftSideUnit = latestLeftSideUnit,
                latestRightSideUnit = latestRightSideUnit,
                enableAnalytics = enableAnalytics
            )
        }

    /**
     * Update current theme preference in DataStore
     *
     * @param appTheme [AppTheme] to change to
     */
    suspend fun updateCurrentAppTheme(appTheme: Int) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.CURRENT_APP_THEME] = appTheme
        }
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
}

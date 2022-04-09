package com.sadellie.unitto.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


// It's at the top level to make DataStore singleton
// DON'T TOUCH STRINGS!!!
private val Context.settingsDataStore by preferencesDataStore("settings")

/**
 * Keys for DataStore
 */
object UserPreferenceKeys {
    val CURRENT_APP_THEME = intPreferencesKey("CURRENT_APP_THEME")
    val DIGITS_PRECISION = intPreferencesKey("DIGITS_PRECISION_PREF_KEY")
    val SEPARATOR = intPreferencesKey("SEPARATOR_PREF_KEY")
    val OUTPUT_FORMAT = intPreferencesKey("OUTPUT_FORMAT_PREF_KEY")
    val LATEST_LEFT_SIDE = stringPreferencesKey("LATEST_LEFT_SIDE_PREF_KEY")
    val LATEST_RIGHT_SIDE = stringPreferencesKey("LATEST_RIGHT_SIDE_PREF_KEY")
}

/**
 * Repository that works with DataStore
 *
 * @property context
 */
class UserPreferences @Inject constructor(@ApplicationContext private val context: Context) {
    /**
     * Gets string from datastore
     *
     * @param[default] Value to return if didn't find anything on fresh install
     */
    fun getItem(key: Preferences.Key<String>, default: String): Flow<String> {
        return context.settingsDataStore.data.map {
            it[key] ?: default
        }
    }

    /**
     * Gets int from datastore
     *
     * @param[default] Value to return if didn't find anything. Used on fresh install
     */
    fun getItem(key: Preferences.Key<Int>, default: Int): Flow<Int> {
        return context.settingsDataStore.data.map {
            it[key] ?: default
        }
    }

    /**
     * Saves string value by key
     */
    suspend fun saveString(key: Preferences.Key<String>, value: String) {
        context.settingsDataStore.edit {
            it[key] = value
        }
    }

    /**
     * Saves int value by key
     */
    suspend fun saveInt(key: Preferences.Key<Int>, value: Int) {
        context.settingsDataStore.edit {
            it[key] = value
        }
    }
}

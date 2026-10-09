/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.core.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sadellie.unitto.core.navigation.CalculatorStartRoute
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.graphRoutes
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.map

@Inject
class AppPrefsRepository(private val source: UserPrefDataSource) {
  val prefs =
    source.rawData.map {
      AppPreferences(
        enableVibrations = it[enableVibrationsKey] ?: AppPreferences.DEFAULT_ENABLE_VIBRATIONS,
        enableKeepScreenOn = it[enableKeepScreenOn] ?: AppPreferences.DEFAULT_ENABLE_KEEP_SCREEN_ON,
        lastReadChangelog = it[lastReadChangelogKey] ?: AppPreferences.DEFAULT_LAST_READ_CHANGELOG,
        startingScreen = it.getStartingScreen(),
      )
    }

  suspend fun updateLastReadChangelog(value: String) = source.edit { preferences ->
    preferences[lastReadChangelogKey] = value
  }

  suspend fun updateVibrations(enabled: Boolean) = source.edit { preferences ->
    preferences[enableVibrationsKey] = enabled
  }

  suspend fun updateEnableKeepScreenOn(enabled: Boolean) = source.edit { preferences ->
    preferences[enableKeepScreenOn] = enabled
  }

  suspend fun updateStartingScreen(graphId: String) = source.edit { preferences ->
    preferences[startingScreenKey] = graphId
  }

  private fun Preferences.getStartingScreen() =
    graphRoutes.firstOrNull { it.routeId == this[startingScreenKey] }
      ?: AppPreferences.DEFAULT_STARTING_SCREEN

  private val startingScreenKey = stringPreferencesKey(PrefKeys.STARTING_SCREEN_PREF_KEY)
  private val lastReadChangelogKey = stringPreferencesKey(PrefKeys.LAST_READ_CHANGELOG_PREF_KEY)
  private val enableVibrationsKey = booleanPreferencesKey(PrefKeys.ENABLE_VIBRATIONS_PREF_KEY)
  private val enableKeepScreenOn = booleanPreferencesKey(PrefKeys.ENABLE_KEEP_SCREEN_ON_PREF_KEY)
}

data class AppPreferences(
  val enableVibrations: Boolean,
  val enableKeepScreenOn: Boolean,
  val lastReadChangelog: String,
  val startingScreen: Route,
) {
  internal companion object Defaults {
    const val DEFAULT_ENABLE_VIBRATIONS = true
    const val DEFAULT_ENABLE_KEEP_SCREEN_ON = false
    const val DEFAULT_LAST_READ_CHANGELOG = ""
    val DEFAULT_STARTING_SCREEN: Route = CalculatorStartRoute
  }
}

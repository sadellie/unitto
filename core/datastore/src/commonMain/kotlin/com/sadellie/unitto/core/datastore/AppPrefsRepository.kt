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
        enableVibrations =
          it[DatastorePrefKeys.ENABLE_VIBRATIONS] ?: AppPreferences.DEFAULT_ENABLE_VIBRATIONS,
        enableKeepScreenOn =
          it[DatastorePrefKeys.ENABLE_KEEP_SCREEN_ON]
            ?: AppPreferences.DEFAULT_ENABLE_KEEP_SCREEN_ON,
        lastReadChangelog =
          it[DatastorePrefKeys.LAST_READ_CHANGELOG] ?: AppPreferences.DEFAULT_LAST_READ_CHANGELOG,
        startingScreen = it.getStartingScreen(),
      )
    }

  suspend fun updateLastReadChangelog(value: String) = source.edit { preferences ->
    preferences[DatastorePrefKeys.LAST_READ_CHANGELOG] = value
  }

  suspend fun updateVibrations(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.ENABLE_VIBRATIONS] = enabled
  }

  suspend fun updateEnableKeepScreenOn(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.ENABLE_KEEP_SCREEN_ON] = enabled
  }

  suspend fun updateStartingScreen(graphId: String) = source.edit { preferences ->
    preferences[DatastorePrefKeys.STARTING_SCREEN] = graphId
  }

  private fun Preferences.getStartingScreen() =
    graphRoutes.firstOrNull { it.routeId == this[DatastorePrefKeys.STARTING_SCREEN] }
      ?: AppPreferences.DEFAULT_STARTING_SCREEN
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

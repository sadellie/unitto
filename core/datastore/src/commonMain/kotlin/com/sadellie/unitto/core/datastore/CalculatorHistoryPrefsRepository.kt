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

import androidx.datastore.preferences.core.booleanPreferencesKey
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.map

@Inject
class CalculatorHistoryPrefsRepository(private val source: UserPrefDataSource) {
  val prefs =
    source.rawData.map {
      CalculatorHistoryPreferences(
        partialHistoryView =
          it[partialHistoryViewKey] ?: CalculatorHistoryPreferences.DEFAULT_PARTIAL_HISTORY_VIEW,
        steppedPartialHistoryView =
          it[steppedPartialHistoryViewKey]
            ?: CalculatorHistoryPreferences.DEFAULT_STEPPED_PARTIAL_HISTORY_VIEW,
        openHistoryViewButton =
          it[openHistoryViewButtonKey]
            ?: CalculatorHistoryPreferences.DEFAULT_OPEN_HISTORY_VIEW_BUTTON,
      )
    }

  suspend fun updatePartialHistoryView(enabled: Boolean) = source.edit { preferences ->
    preferences[partialHistoryViewKey] = enabled
  }

  suspend fun updateSteppedPartialHistoryView(enabled: Boolean) = source.edit { preferences ->
    preferences[steppedPartialHistoryViewKey] = enabled
  }

  suspend fun updateOpenHistoryViewButton(enabled: Boolean) = source.edit { preferences ->
    preferences[openHistoryViewButtonKey] = enabled
  }

  private val partialHistoryViewKey = booleanPreferencesKey(PrefKeys.PARTIAL_HISTORY_VIEW_PREF_KEY)
  private val steppedPartialHistoryViewKey =
    booleanPreferencesKey(PrefKeys.STEPPED_PARTIAL_HISTORY_VIEW_PREF_KEY)
  private val openHistoryViewButtonKey =
    booleanPreferencesKey(PrefKeys.OPEN_HISTORY_VIEW_BUTTON_PREF_KEY)
}

data class CalculatorHistoryPreferences(
  val partialHistoryView: Boolean,
  val steppedPartialHistoryView: Boolean,
  val openHistoryViewButton: Boolean,
) {
  internal companion object Defaults {
    const val DEFAULT_PARTIAL_HISTORY_VIEW: Boolean = true
    const val DEFAULT_STEPPED_PARTIAL_HISTORY_VIEW: Boolean = true
    const val DEFAULT_OPEN_HISTORY_VIEW_BUTTON: Boolean = false
  }
}

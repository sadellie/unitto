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

import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.map

@Inject
class CalculatorHistoryPrefsRepository(private val source: UserPrefDataSource) {
  val prefs =
    source.rawData.map {
      CalculatorHistoryPreferences(
        partialHistoryView =
          it[DatastorePrefKeys.PARTIAL_HISTORY_VIEW]
            ?: CalculatorHistoryPreferences.DEFAULT_PARTIAL_HISTORY_VIEW,
        steppedPartialHistoryView =
          it[DatastorePrefKeys.STEPPED_PARTIAL_HISTORY_VIEW]
            ?: CalculatorHistoryPreferences.DEFAULT_STEPPED_PARTIAL_HISTORY_VIEW,
        openHistoryViewButton =
          it[DatastorePrefKeys.OPEN_HISTORY_VIEW_BUTTON]
            ?: CalculatorHistoryPreferences.DEFAULT_OPEN_HISTORY_VIEW_BUTTON,
      )
    }

  suspend fun updatePartialHistoryView(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.PARTIAL_HISTORY_VIEW] = enabled
  }

  suspend fun updateSteppedPartialHistoryView(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.STEPPED_PARTIAL_HISTORY_VIEW] = enabled
  }

  suspend fun updateOpenHistoryViewButton(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.OPEN_HISTORY_VIEW_BUTTON] = enabled
  }
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

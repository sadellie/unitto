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
class KeypadPrefsRepository(private val source: UserPrefDataSource) {
  val prefs =
    source.rawData.map {
      KeypadPreferences(
        acButton = it[DatastorePrefKeys.AC_BUTTON] ?: KeypadPreferences.DEFAULT_AC_BUTTON,
        middleZero = it[DatastorePrefKeys.MIDDLE_ZERO] ?: KeypadPreferences.DEFAULT_MIDDLE_ZERO,
      )
    }

  suspend fun updateAcButton(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.AC_BUTTON] = enabled
  }

  suspend fun updateMiddleZero(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.MIDDLE_ZERO] = enabled
  }
}

data class KeypadPreferences(val acButton: Boolean, val middleZero: Boolean) {
  internal companion object Defaults {
    const val DEFAULT_AC_BUTTON: Boolean = true

    const val DEFAULT_MIDDLE_ZERO: Boolean = true
  }
}

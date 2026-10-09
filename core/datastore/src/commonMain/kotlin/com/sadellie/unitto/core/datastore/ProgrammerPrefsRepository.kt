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
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sadellie.unitto.core.common.DataUnit
import com.sadellie.unitto.core.model.programmer.ShiftType
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
class ProgrammerPrefsRepository(private val source: UserPrefDataSource) {
  val prefs: Flow<ProgrammerPreferences> =
    source.rawData.map {
      ProgrammerPreferences(
        base = it[baseKey] ?: ProgrammerPreferences.DEFAULT_BASE,
        dataUnit = it.getDataUnit(),
        shiftType = it.getShiftType(),
        initialPartialHistoryView =
          it[initialPartialHistoryViewKey]
            ?: ProgrammerPreferences.DEFAULT_INITIAL_PARTIAL_HISTORY_VIEW,
      )
    }

  suspend fun updateBase(base: Int) = source.edit { preferences ->
    preferences[baseKey] = base
  }

  suspend fun updateDataUnit(dataUnit: DataUnit) = source.edit { preferences ->
    preferences[dataUnitKey] = dataUnit.name
  }

  suspend fun updateShiftType(shiftType: ShiftType) = source.edit { preferences ->
    preferences[shiftTypeKey] = shiftType.name
  }

  suspend fun updateInitialPartialHistoryView(initialPartialHistoryView: Boolean) = source.edit {
    it[initialPartialHistoryViewKey] = initialPartialHistoryView
  }

  private fun Preferences.getDataUnit(): DataUnit =
    this[dataUnitKey]?.letTryOrNull {
      DataUnit.valueOf(it)
    } ?: ProgrammerPreferences.DEFAULT_DATA_UNIT

  private fun Preferences.getShiftType(): ShiftType =
    this[shiftTypeKey]?.letTryOrNull {
      ShiftType.valueOf(it)
    } ?: ProgrammerPreferences.DEFAULT_SHIFT_TYPE

  private val baseKey = intPreferencesKey(PrefKeys.PROGRAMMER_BASE_PREF_KEY)
  private val dataUnitKey = stringPreferencesKey(PrefKeys.PROGRAMMER_DATA_UNIT_PREF_KEY)
  private val shiftTypeKey = stringPreferencesKey(PrefKeys.PROGRAMMER_SHIFT_TYPE_PREF_KEY)
  private val initialPartialHistoryViewKey =
    booleanPreferencesKey(PrefKeys.PROGRAMMER_INITIAL_PARTIAL_HISTORY_VIEW_PREF_KEY)
}

data class ProgrammerPreferences(
  val base: Int,
  val dataUnit: DataUnit,
  val shiftType: ShiftType,
  val initialPartialHistoryView: Boolean,
) {
  internal companion object Defaults {
    const val DEFAULT_BASE = 10
    val DEFAULT_DATA_UNIT = DataUnit.QWORD
    val DEFAULT_SHIFT_TYPE = ShiftType.SHIFT
    const val DEFAULT_INITIAL_PARTIAL_HISTORY_VIEW: Boolean = false
  }
}

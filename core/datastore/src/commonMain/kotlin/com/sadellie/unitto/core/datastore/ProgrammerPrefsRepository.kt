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
        base = it[DatastorePrefKeys.PROGRAMMER_BASE] ?: ProgrammerPreferences.DEFAULT_BASE,
        dataUnit = it.getDataUnit(),
        shiftType = it.getShiftType(),
        additionalButtons =
          it[DatastorePrefKeys.ADDITIONAL_BUTTONS]
            ?: ProgrammerPreferences.DEFAULT_ADDITIONAL_BUTTONS,
        initialPartialHistoryView =
          it[DatastorePrefKeys.INITIAL_PARTIAL_HISTORY_VIEW]
            ?: ProgrammerPreferences.DEFAULT_INITIAL_PARTIAL_HISTORY_VIEW,
      )
    }

  suspend fun updateBase(base: Int) = source.edit { preferences ->
    preferences[DatastorePrefKeys.PROGRAMMER_BASE] = base
  }

  suspend fun updateDataUnit(dataUnit: DataUnit) = source.edit { preferences ->
    preferences[DatastorePrefKeys.PROGRAMMER_DATA_UNIT] = dataUnit.name
  }

  suspend fun updateShiftType(shiftType: ShiftType) = source.edit { preferences ->
    preferences[DatastorePrefKeys.PROGRAMMER_SHIFT_TYPE] = shiftType.name
  }

  suspend fun updateAdditionalButtons(additionalButtons: Boolean) = source.edit {
    it[DatastorePrefKeys.ADDITIONAL_BUTTONS] = additionalButtons
  }

  suspend fun updateInitialPartialHistoryView(initialPartialHistoryView: Boolean) = source.edit {
    it[DatastorePrefKeys.INITIAL_PARTIAL_HISTORY_VIEW] = initialPartialHistoryView
  }

  private fun Preferences.getDataUnit(): DataUnit =
    this[DatastorePrefKeys.PROGRAMMER_DATA_UNIT]?.letTryOrNull {
      DataUnit.valueOf(it)
    } ?: ProgrammerPreferences.DEFAULT_DATA_UNIT

  private fun Preferences.getShiftType(): ShiftType =
    this[DatastorePrefKeys.PROGRAMMER_SHIFT_TYPE]?.letTryOrNull {
      ShiftType.valueOf(it)
    } ?: ProgrammerPreferences.DEFAULT_SHIFT_TYPE
}

data class ProgrammerPreferences(
  val base: Int,
  val dataUnit: DataUnit,
  val shiftType: ShiftType,
  val additionalButtons: Boolean,
  val initialPartialHistoryView: Boolean,
) {
  internal companion object Defaults {
    const val DEFAULT_BASE = 10
    val DEFAULT_DATA_UNIT = DataUnit.QWORD
    val DEFAULT_SHIFT_TYPE = ShiftType.SHIFT
    const val DEFAULT_ADDITIONAL_BUTTONS: Boolean = false
    const val DEFAULT_INITIAL_PARTIAL_HISTORY_VIEW: Boolean = false
  }
}

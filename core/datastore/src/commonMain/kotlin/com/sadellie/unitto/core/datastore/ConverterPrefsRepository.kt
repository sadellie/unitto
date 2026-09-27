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
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
class ConverterPrefsRepository(private val source: UserPrefDataSource) {
  val prefs: Flow<ConverterPreferences> =
    source.rawData.map {
      ConverterPreferences(
        customApiUrl =
          it[DatastorePrefKeys.UNIT_CONVERTER_CUSTOM_API_URL]
            ?: ConverterPreferences.DEFAULT_CUSTOM_API_URL,
        formatTime =
          it[DatastorePrefKeys.UNIT_CONVERTER_FORMAT_TIME]
            ?: ConverterPreferences.DEFAULT_UNIT_CONVERTER_FORMAT_TIME,
        latestLeftSideUnit =
          it[DatastorePrefKeys.LATEST_LEFT_SIDE] ?: ConverterPreferences.DEFAULT_LATEST_LEFT_SIDE,
        latestRightSideUnit =
          it[DatastorePrefKeys.LATEST_RIGHT_SIDE] ?: ConverterPreferences.DEFAULT_LATEST_RIGHT_SIDE,
        shownUnitGroups = it.getShownUnitGroups(),
        favoritesOnly =
          it[DatastorePrefKeys.UNIT_CONVERTER_FAVORITES_ONLY]
            ?: ConverterPreferences.DEFAULT_UNIT_CONVERTER_FAVORITES_ONLY,
        sorting = it.getUnitConverterSorting(),
        showIcons =
          it[DatastorePrefKeys.UNIT_CONVERTER_SHOW_ICONS]
            ?: ConverterPreferences.DEFAULT_UNIT_CONVERTER_SHOW_ICONS,
      )
    }

  suspend fun updateCustomApiUrl(apiUrl: String) = source.edit {
    it[DatastorePrefKeys.UNIT_CONVERTER_CUSTOM_API_URL] = apiUrl
  }

  suspend fun updateUnitConverterFavoritesOnly(value: Boolean) = source.edit {
    it[DatastorePrefKeys.UNIT_CONVERTER_FAVORITES_ONLY] = value
  }

  suspend fun updateLatestPairOfUnits(unitFrom: String, unitTo: String) = source.edit {
    it[DatastorePrefKeys.LATEST_LEFT_SIDE] = unitFrom
    it[DatastorePrefKeys.LATEST_RIGHT_SIDE] = unitTo
  }

  suspend fun updateUnitConverterFormatTime(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.UNIT_CONVERTER_FORMAT_TIME] = enabled
  }

  suspend fun updateUnitConverterSorting(sorting: UnitsListSorting) = source.edit { preferences ->
    preferences[DatastorePrefKeys.UNIT_CONVERTER_SORTING] = sorting.name
  }

  suspend fun updateUnitConverterShowIcons(enabled: Boolean) = source.edit { preferences ->
    preferences[DatastorePrefKeys.UNIT_CONVERTER_SHOW_ICONS] = enabled
  }

  suspend fun updateShownUnitGroups(shownUnitGroups: List<UnitGroup>) = source.edit { preferences ->
    preferences[DatastorePrefKeys.SHOWN_UNIT_GROUPS] = shownUnitGroups.packToString()
  }

  suspend fun addShownUnitGroup(unitGroup: UnitGroup) = source.edit { preferences ->
    preferences[DatastorePrefKeys.SHOWN_UNIT_GROUPS] =
      preferences.getShownUnitGroups().plus(unitGroup).packToString()
  }

  suspend fun removeShownUnitGroup(unitGroup: UnitGroup) = source.edit { preferences ->
    preferences[DatastorePrefKeys.SHOWN_UNIT_GROUPS] =
      preferences.getShownUnitGroups().minus(unitGroup).packToString()
  }

  private fun Preferences.getShownUnitGroups(): List<UnitGroup> =
    this[DatastorePrefKeys.SHOWN_UNIT_GROUPS]?.letTryOrNull { list ->
      list
        .ifEmpty {
          return@letTryOrNull listOf()
        }
        .split(",")
        .map { UnitGroup.valueOf(it) }
    } ?: ConverterPreferences.DEFAULT_SHOWN_UNIT_GROUPS

  private fun Preferences.getUnitConverterSorting() =
    this[DatastorePrefKeys.UNIT_CONVERTER_SORTING]?.let { UnitsListSorting.valueOf(it) }
      ?: ConverterPreferences.DEFAULT_UNIT_CONVERTER_SORTING

  private fun List<UnitGroup>.packToString(): String = this.joinToString(",")
}

data class ConverterPreferences(
  val customApiUrl: String,
  val formatTime: Boolean,
  val latestLeftSideUnit: String,
  val latestRightSideUnit: String,
  val shownUnitGroups: List<UnitGroup>,
  val favoritesOnly: Boolean,
  val sorting: UnitsListSorting,
  val showIcons: Boolean,
) {
  internal companion object Defaults {
    const val DEFAULT_UNIT_CONVERTER_FORMAT_TIME: Boolean = false

    val DEFAULT_UNIT_CONVERTER_SORTING: UnitsListSorting = UnitsListSorting.USAGE

    val DEFAULT_SHOWN_UNIT_GROUPS: List<UnitGroup> = UnitGroup.entries

    const val DEFAULT_UNIT_CONVERTER_FAVORITES_ONLY: Boolean = false

    const val DEFAULT_UNIT_CONVERTER_SHOW_ICONS: Boolean = true

    const val DEFAULT_LATEST_LEFT_SIDE: String = UnitID.kilometer

    const val DEFAULT_LATEST_RIGHT_SIDE: String = UnitID.mile

    const val DEFAULT_CUSTOM_API_URL: String = ""
  }
}

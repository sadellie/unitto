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
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitID
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
class ConverterPrefsRepository(private val source: UserPrefDataSource) {
  val prefs: Flow<ConverterPreferences> =
    source.rawData.map {
      ConverterPreferences(
        customApiUrl = it[customApiUrlKey] ?: ConverterPreferences.DEFAULT_CUSTOM_API_URL,
        formatTime = it[formatTimeKey] ?: ConverterPreferences.DEFAULT_UNIT_CONVERTER_FORMAT_TIME,
        latestLeftSideUnit = it[latestLeftSideKey] ?: ConverterPreferences.DEFAULT_LATEST_LEFT_SIDE,
        latestRightSideUnit =
          it[latestRightSideKey] ?: ConverterPreferences.DEFAULT_LATEST_RIGHT_SIDE,
        shownUnitGroups = it.getShownUnitGroups(),
        favoritesOnly =
          it[favoritesOnlyKey] ?: ConverterPreferences.DEFAULT_UNIT_CONVERTER_FAVORITES_ONLY,
        sorting = it.getUnitConverterSorting(),
        showIcons = it[showIconsKey] ?: ConverterPreferences.DEFAULT_UNIT_CONVERTER_SHOW_ICONS,
      )
    }

  suspend fun updateCustomApiUrl(apiUrl: String) = source.edit {
    it[customApiUrlKey] = apiUrl
  }

  suspend fun updateUnitConverterFavoritesOnly(value: Boolean) = source.edit {
    it[favoritesOnlyKey] = value
  }

  suspend fun updateLatestPairOfUnits(unitFrom: String, unitTo: String) = source.edit {
    it[latestLeftSideKey] = unitFrom
    it[latestRightSideKey] = unitTo
  }

  suspend fun updateUnitConverterFormatTime(enabled: Boolean) = source.edit { preferences ->
    preferences[formatTimeKey] = enabled
  }

  suspend fun updateUnitConverterSorting(sorting: UnitsListSorting) = source.edit { preferences ->
    preferences[sortingKey] = sorting.name
  }

  suspend fun updateUnitConverterShowIcons(enabled: Boolean) = source.edit { preferences ->
    preferences[showIconsKey] = enabled
  }

  suspend fun updateShownUnitGroups(shownUnitGroups: List<UnitGroup>) = source.edit { preferences ->
    preferences[shownUnitGroupsKey] = shownUnitGroups.packToString()
  }

  suspend fun addShownUnitGroup(unitGroup: UnitGroup) = source.edit { preferences ->
    preferences[shownUnitGroupsKey] =
      preferences.getShownUnitGroups().plus(unitGroup).packToString()
  }

  suspend fun removeShownUnitGroup(unitGroup: UnitGroup) = source.edit { preferences ->
    preferences[shownUnitGroupsKey] =
      preferences.getShownUnitGroups().minus(unitGroup).packToString()
  }

  private fun Preferences.getShownUnitGroups(): List<UnitGroup> =
    this[shownUnitGroupsKey]?.letTryOrNull { list ->
      list
        .ifEmpty {
          return@letTryOrNull listOf()
        }
        .split(",")
        .map { UnitGroup.valueOf(it) }
    } ?: ConverterPreferences.DEFAULT_SHOWN_UNIT_GROUPS

  private fun Preferences.getUnitConverterSorting() =
    this[sortingKey]?.letTryOrNull { UnitsListSorting.valueOf(it) }
      ?: ConverterPreferences.DEFAULT_UNIT_CONVERTER_SORTING

  private fun List<UnitGroup>.packToString(): String = this.joinToString(",")

  private val latestLeftSideKey = stringPreferencesKey(PrefKeys.LATEST_LEFT_SIDE_PREF_KEY)
  private val latestRightSideKey = stringPreferencesKey(PrefKeys.LATEST_RIGHT_SIDE_PREF_KEY)
  private val shownUnitGroupsKey = stringPreferencesKey(PrefKeys.SHOWN_UNIT_GROUPS_PREF_KEY)
  private val favoritesOnlyKey =
    booleanPreferencesKey(PrefKeys.UNIT_CONVERTER_FAVORITES_ONLY_PREF_KEY)
  private val formatTimeKey = booleanPreferencesKey(PrefKeys.UNIT_CONVERTER_FORMAT_TIME_PREF_KEY)
  private val sortingKey = stringPreferencesKey(PrefKeys.UNIT_CONVERTER_SORTING_PREF_KEY)
  private val customApiUrlKey =
    stringPreferencesKey(PrefKeys.UNIT_CONVERTER_CUSTOM_API_URL_PREF_KEY)
  private val showIconsKey = booleanPreferencesKey(PrefKeys.UNIT_CONVERTER_SHOW_ICONS_PREF_KEY)
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

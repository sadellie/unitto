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
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.zacsweers.metro.Inject
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.coroutines.flow.map

@Inject
class ThemePrefsRepository(private val source: UserPrefDataSource) {
  val prefs =
    source.rawData.map {
      ThemePreferences(
        themingMode = it.getThemingMode(),
        enableAmoledTheme =
          it[enableAmoledThemeKey] ?: ThemePreferences.DEFAULT_ENABLE_AMOLED_THEME,
        enableDynamicTheme =
          it[enableDynamicThemeKey] ?: ThemePreferences.DEFAULT_ENABLE_DYNAMIC_THEME,
        customColor = it[customColorKey] ?: ThemePreferences.DEFAULT_CUSTOM_COLOR,
        monetMode = it.getMonetMode(),
      )
    }

  private fun Preferences.getMonetMode() =
    this[monetModeKey]?.letTryOrNull { MonetMode.valueOf(it) }
      ?: ThemePreferences.DEFAULT_MONET_MODE

  private fun Preferences.getThemingMode() =
    this[themingModeKey]?.letTryOrNull { ThemingMode.valueOf(it) }
      ?: ThemePreferences.DEFAULT_THEMING_MODE

  suspend fun updateThemingMode(themingMode: ThemingMode) = source.edit { preferences ->
    preferences[themingModeKey] = themingMode.name
  }

  suspend fun updateDynamicTheme(enabled: Boolean) = source.edit { preferences ->
    preferences[enableDynamicThemeKey] = enabled
  }

  suspend fun updateAmoledTheme(enabled: Boolean) = source.edit { preferences ->
    preferences[enableAmoledThemeKey] = enabled
  }

  suspend fun updateCustomColor(color: Long) = source.edit { preferences ->
    preferences[customColorKey] = color
  }

  suspend fun updateMonetMode(monetMode: MonetMode) = source.edit { preferences ->
    preferences[monetModeKey] = monetMode.name
  }

  private val themingModeKey = stringPreferencesKey(PrefKeys.THEMING_MODE_PREF_KEY)
  private val enableDynamicThemeKey = booleanPreferencesKey(PrefKeys.ENABLE_DYNAMIC_THEME_PREF_KEY)
  private val enableAmoledThemeKey = booleanPreferencesKey(PrefKeys.ENABLE_AMOLED_THEME_PREF_KEY)
  private val customColorKey = longPreferencesKey(PrefKeys.CUSTOM_COLOR_PREF_KEY)
  private val monetModeKey = stringPreferencesKey(PrefKeys.MONET_MODE_PREF_KEY)
}

data class ThemePreferences(
  val themingMode: ThemingMode,
  val enableAmoledTheme: Boolean,
  val enableDynamicTheme: Boolean,
  val customColor: Long,
  val monetMode: MonetMode,
) {
  internal companion object Defaults {
    const val DEFAULT_ENABLE_DYNAMIC_THEME: Boolean = true
    val DEFAULT_THEMING_MODE: ThemingMode = ThemingMode.AUTO
    const val DEFAULT_ENABLE_AMOLED_THEME: Boolean = false
    const val DEFAULT_CUSTOM_COLOR: Long = 16L
    val DEFAULT_MONET_MODE: MonetMode = MonetMode.TonalSpot
  }
}

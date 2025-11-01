/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package com.sadellie.unitto

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.sadellie.unitto.core.datastore.AppPreferences
import com.sadellie.unitto.core.designsystem.theme.DarkThemeColors
import com.sadellie.unitto.core.designsystem.theme.LightThemeColors
import io.github.sadellie.themmo.ThemmoController

/** Remembers [ThemmoController] using [prefs] as a key. */
@Composable
internal fun rememberUnittoThemmoController(prefs: AppPreferences) =
  remember(
    prefs.themingMode,
    prefs.enableDynamicTheme,
    prefs.enableAmoledTheme,
    prefs.customColor,
    prefs.monetMode,
  ) {
    ThemmoController(
      lightColorScheme = LightThemeColors,
      darkColorScheme = DarkThemeColors,
      themingMode = prefs.themingMode,
      dynamicThemeEnabled = prefs.enableDynamicTheme,
      amoledThemeEnabled = prefs.enableAmoledTheme,
      customColor = prefs.customColor.toColorOrUnspecified(),
      monetMode = prefs.monetMode,
    )
  }

/**
 * Tries to convert [this] to [Color].
 *
 * @return Converted color or [Color.Unspecified] if failed to convert.
 */
private fun Long.toColorOrUnspecified(): Color =
  try {
    Color(this.toULong())
  } catch (_: Exception) {
    Color.Unspecified
  }

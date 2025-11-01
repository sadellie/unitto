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

package io.github.sadellie.themmo

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.expressiveLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

/**
 * Controller that holds current theming options and provides methods to manipulate them.
 *
 * @param lightColorScheme Scheme as default light color scheme.
 * @param darkColorScheme Scheme as default dark color scheme.
 * @param themingMode Current [io.github.sadellie.themmo.core.ThemingMode].
 * @param dynamicThemeEnabled When true will use dynamic theming (Monet).
 * @param amoledThemeEnabled When true will change background color to black. Only for dark theme.
 * @param customColor Color from which color scheme will be generated from.
 * @param monetMode Monet mode for custom color scheme generation
 */
class ThemmoController(
  val lightColorScheme: ColorScheme,
  val darkColorScheme: ColorScheme,
  themingMode: ThemingMode,
  dynamicThemeEnabled: Boolean,
  amoledThemeEnabled: Boolean,
  customColor: Color,
  monetMode: MonetMode,
) {
  var currentThemingMode: ThemingMode by mutableStateOf(themingMode)
    private set

  var isDynamicThemeEnabled: Boolean by mutableStateOf(dynamicThemeEnabled)
    private set

  var isAmoledThemeEnabled: Boolean by mutableStateOf(amoledThemeEnabled)
    private set

  var currentCustomColor: Color by mutableStateOf(customColor)
    private set

  var currentMonetMode: MonetMode by mutableStateOf(monetMode)
    private set

  fun setThemingMode(mode: ThemingMode) {
    currentThemingMode = mode
  }

  fun enableDynamicTheme(enable: Boolean) {
    isDynamicThemeEnabled = enable
  }

  fun enableAmoledTheme(enable: Boolean) {
    isAmoledThemeEnabled = enable
  }

  fun setCustomColor(color: Color) {
    currentCustomColor = color
  }

  fun setMonetMode(monetMode: MonetMode) {
    currentMonetMode = monetMode
  }

  @Composable
  internal fun provideColorScheme(isSystemDark: Boolean) =
    commonProvideColorScheme(
      isSystemDark = isSystemDark,
      currentThemingMode = currentThemingMode,
      isDynamicThemeEnabled = isDynamicThemeEnabled,
      currentCustomColor = currentCustomColor,
      currentMonetMode = currentMonetMode,
      lightColorScheme = lightColorScheme,
      darkColorScheme = darkColorScheme,
      isAmoledThemeEnabled = isAmoledThemeEnabled,
    )

  fun saveState(): Map<String, Any> {
    return mapOf(
      AMOLED_ENABLED to isAmoledThemeEnabled,
      THEMING_MODE to currentThemingMode,
      DYNAMIC_ENABLED to isDynamicThemeEnabled,
      // We can't save unsigned longs
      CUSTOM_COLOR to currentCustomColor.value.toLong(),
      MONET_MODE to currentMonetMode,
    )
  }
}

/**
 * Returns saveable [ThemmoController].
 *
 * @return [ThemmoController] with applied parameters.
 * @see [ThemmoController]
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun rememberThemmoController(
  lightColorScheme: ColorScheme = expressiveLightColorScheme(),
  darkColorScheme: ColorScheme = darkColorScheme(),
  amoledThemeEnabled: Boolean = false,
  themingMode: ThemingMode = ThemingMode.AUTO,
  dynamicThemeEnabled: Boolean = true,
  customColor: Color = Color.Unspecified,
  monetMode: MonetMode = MonetMode.FruitSalad,
): ThemmoController {
  return rememberSaveable(saver = themmoControllerSaver(lightColorScheme, darkColorScheme)) {
    ThemmoController(
      lightColorScheme = lightColorScheme,
      darkColorScheme = darkColorScheme,
      themingMode = themingMode,
      dynamicThemeEnabled = dynamicThemeEnabled,
      amoledThemeEnabled = amoledThemeEnabled,
      customColor = customColor,
      monetMode = monetMode,
    )
  }
}

/**
 * Saver that is used in rememberSaveable method. Handles save and restore logic.
 *
 * [lightColorScheme] and [darkColorScheme] are used as parameters here. There is probably no need
 * to save colorSchemes as they are less likely to change on runtime.
 *
 * @return Saver for [ThemmoController].
 */
internal fun themmoControllerSaver(
  lightColorScheme: ColorScheme,
  darkColorScheme: ColorScheme,
): Saver<ThemmoController, *> =
  mapSaver(
    save = { it.saveState() },
    restore = { restoreThemmoState(lightColorScheme, darkColorScheme, it) },
  )

/**
 * Restores [ThemmoController] for saver.
 *
 * @param lightColorScheme Light color scheme.
 * @param darkColorScheme Dark color scheme.
 * @param map Map of other options from [ThemmoController] that were saved.
 * @return Restored [ThemmoController].
 */
private fun restoreThemmoState(
  lightColorScheme: ColorScheme,
  darkColorScheme: ColorScheme,
  map: Map<String, Any?>,
): ThemmoController {
  return ThemmoController(
    lightColorScheme,
    darkColorScheme,
    themingMode = map[THEMING_MODE] as ThemingMode,
    dynamicThemeEnabled = map[DYNAMIC_ENABLED] as Boolean,
    amoledThemeEnabled = map[AMOLED_ENABLED] as Boolean,
    customColor = Color(value = (map[CUSTOM_COLOR] as Long).toULong()),
    monetMode = map[MONET_MODE] as MonetMode,
  )
}

// Constants for Saver
private const val AMOLED_ENABLED = "AMOLED_ENABLED"
private const val THEMING_MODE = "THEMING_MODE"
private const val DYNAMIC_ENABLED = "DYNAMIC_ENABLED"
private const val CUSTOM_COLOR = "CUSTOM_COLOR"
private const val MONET_MODE = "MONET_MODE"

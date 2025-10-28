/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.res.colorResource
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode

// Constants for Saver
private const val AMOLED_ENABLED = "AMOLED_ENABLED"
private const val THEMING_MODE = "THEMING_MODE"
private const val DYNAMIC_ENABLED = "DYNAMIC_ENABLED"
private const val CUSTOM_COLOR = "CUSTOM_COLOR"
private const val MONET_MODE = "MONET_MODE"

/**
 * Controller that holds current theming options and provides methods to manipulate them.
 *
 * @param lightColorScheme Scheme as default light color scheme.
 * @param darkColorScheme Scheme as default dark color scheme.
 * @param themingMode Current [ThemingMode].
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

  @RequiresApi(value = Build.VERSION_CODES.O_MR1)
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
  internal fun provideColorScheme(context: Context, isSystemDark: Boolean): ColorScheme {
    return when (currentThemingMode) {
      ThemingMode.AUTO -> {
        if (isSystemDark) {
          provideDarkColorScheme(context)
        } else {
          provideLightColorScheme(context)
        }
      }
      ThemingMode.FORCE_LIGHT -> provideLightColorScheme(context)
      ThemingMode.FORCE_DARK -> provideDarkColorScheme(context)
    }
  }

  @Composable
  private fun provideLightColorScheme(context: Context): ColorScheme {
    return when {
      isDynamicThemeEnabled -> {
        when {
          Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
            dynamicLightColorScheme(context)
          }
          Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val keyColor = colorResource(android.R.color.system_accent1_500)
            dynamicColorScheme(keyColor = keyColor, isDark = false, style = MonetMode.TonalSpot)
          }
          Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 -> {
            val keyColor = extractWallpaperPrimary(context)
            if (keyColor == null) {
              lightColorScheme
            } else {
              dynamicColorScheme(keyColor = keyColor, isDark = false, style = MonetMode.TonalSpot)
            }
          }
          else -> lightColorScheme
        }
      }
      !isDynamicThemeEnabled and currentCustomColor.isSpecified -> {
        dynamicColorScheme(keyColor = currentCustomColor, isDark = false, style = currentMonetMode)
      }
      else -> lightColorScheme
    }
  }

  @Composable
  private fun provideDarkColorScheme(context: Context): ColorScheme {
    val darkColorScheme: ColorScheme =
      when {
        isDynamicThemeEnabled -> {
          when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
              dynamicDarkColorScheme(context)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
              val keyColor = colorResource(android.R.color.system_accent1_500)
              dynamicColorScheme(keyColor = keyColor, isDark = true, style = MonetMode.TonalSpot)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 -> {
              val keyColor = extractWallpaperPrimary(context)
              if (keyColor == null) {
                darkColorScheme
              } else {
                dynamicColorScheme(keyColor = keyColor, isDark = true, style = MonetMode.TonalSpot)
              }
            }
            else -> darkColorScheme
          }
        }
        !isDynamicThemeEnabled and currentCustomColor.isSpecified -> {
          dynamicColorScheme(keyColor = currentCustomColor, isDark = true, style = currentMonetMode)
        }
        else -> darkColorScheme
      }

    // Turning into amoled if needed
    return if (isAmoledThemeEnabled) {
      ColorScheme(
        primary = darkColorScheme.primary.darken(AMOLED_MAIN_FACTOR),
        onPrimary = darkColorScheme.onPrimary.darken(AMOLED_TEXT_FACTOR),
        primaryContainer = darkColorScheme.primaryContainer.darken(AMOLED_MAIN_FACTOR),
        onPrimaryContainer = darkColorScheme.onPrimaryContainer.darken(AMOLED_TEXT_FACTOR),
        inversePrimary = darkColorScheme.inversePrimary.darken(AMOLED_MAIN_FACTOR),
        secondary = darkColorScheme.secondary.darken(AMOLED_MAIN_FACTOR),
        onSecondary = darkColorScheme.onSecondary.darken(AMOLED_TEXT_FACTOR),
        secondaryContainer = darkColorScheme.secondaryContainer.darken(AMOLED_MAIN_FACTOR),
        onSecondaryContainer = darkColorScheme.onSecondaryContainer.darken(AMOLED_TEXT_FACTOR),
        tertiary = darkColorScheme.tertiary.darken(AMOLED_MAIN_FACTOR),
        onTertiary = darkColorScheme.onTertiary.darken(AMOLED_TEXT_FACTOR),
        tertiaryContainer = darkColorScheme.tertiaryContainer.darken(AMOLED_MAIN_FACTOR),
        onTertiaryContainer = darkColorScheme.onTertiaryContainer.darken(AMOLED_TEXT_FACTOR),
        background = Color.Black,
        onBackground = darkColorScheme.onBackground.darken(AMOLED_TEXT_FACTOR),
        surface = Color.Black,
        onSurface = darkColorScheme.onSurface.darken(AMOLED_TEXT_FACTOR),
        surfaceVariant = darkColorScheme.surfaceVariant.darken(AMOLED_MAIN_FACTOR),
        onSurfaceVariant = darkColorScheme.onSurfaceVariant.darken(AMOLED_TEXT_FACTOR),
        surfaceTint = darkColorScheme.surfaceTint.darken(AMOLED_MAIN_FACTOR),
        inverseSurface = darkColorScheme.inverseSurface.darken(AMOLED_MAIN_FACTOR),
        inverseOnSurface = darkColorScheme.inverseOnSurface.darken(AMOLED_TEXT_FACTOR),
        error = darkColorScheme.error.darken(AMOLED_MAIN_FACTOR),
        onError = darkColorScheme.onError.darken(AMOLED_TEXT_FACTOR),
        errorContainer = darkColorScheme.errorContainer.darken(AMOLED_MAIN_FACTOR),
        onErrorContainer = darkColorScheme.onErrorContainer.darken(AMOLED_TEXT_FACTOR),
        outline = darkColorScheme.outline.darken(AMOLED_MAIN_FACTOR),
        outlineVariant = darkColorScheme.outlineVariant.darken(AMOLED_MAIN_FACTOR),
        scrim = darkColorScheme.scrim.darken(AMOLED_MAIN_FACTOR),
        surfaceBright = darkColorScheme.surfaceBright.darken(AMOLED_MAIN_FACTOR),
        surfaceDim = darkColorScheme.surfaceDim.darken(AMOLED_MAIN_FACTOR),
        surfaceContainer = darkColorScheme.surfaceContainer.darken(AMOLED_MAIN_FACTOR),
        surfaceContainerHigh = darkColorScheme.surfaceContainerHigh.darken(AMOLED_MAIN_FACTOR),
        surfaceContainerHighest =
          darkColorScheme.surfaceContainerHighest.darken(AMOLED_MAIN_FACTOR),
        surfaceContainerLow = darkColorScheme.surfaceContainerLow.darken(AMOLED_MAIN_FACTOR),
        surfaceContainerLowest = darkColorScheme.surfaceContainerLowest.darken(AMOLED_MAIN_FACTOR),
        primaryFixed = darkColorScheme.primaryFixed.darken(AMOLED_MAIN_FACTOR),
        primaryFixedDim = darkColorScheme.primaryFixedDim.darken(AMOLED_MAIN_FACTOR),
        onPrimaryFixed = darkColorScheme.onPrimaryFixed.darken(AMOLED_TEXT_FACTOR),
        onPrimaryFixedVariant = darkColorScheme.onPrimaryFixedVariant,
        secondaryFixed = darkColorScheme.secondaryFixed.darken(AMOLED_MAIN_FACTOR),
        secondaryFixedDim = darkColorScheme.secondaryFixedDim.darken(AMOLED_MAIN_FACTOR),
        onSecondaryFixed = darkColorScheme.onSecondaryFixed.darken(AMOLED_TEXT_FACTOR),
        onSecondaryFixedVariant = darkColorScheme.onSecondaryFixedVariant,
        tertiaryFixed = darkColorScheme.tertiaryFixed.darken(AMOLED_MAIN_FACTOR),
        tertiaryFixedDim = darkColorScheme.tertiaryFixedDim.darken(AMOLED_MAIN_FACTOR),
        onTertiaryFixed = darkColorScheme.onTertiaryFixed.darken(AMOLED_TEXT_FACTOR),
        onTertiaryFixedVariant = darkColorScheme.onTertiaryFixedVariant.darken(AMOLED_TEXT_FACTOR),
      )
    } else {
      darkColorScheme
    }
  }

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
 * Restores [ThemmoController] for saver.
 *
 * @param lightColorScheme Light color scheme.
 * @param darkColorScheme Dark color scheme.
 * @param map Map of other options from [ThemmoController] that were saved.
 * @return Restored [ThemmoController].
 */
internal fun restoreThemmoState(
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

/**
 * Returns saveable [ThemmoController].
 *
 * @return [ThemmoController] with applied parameters.
 * @see [ThemmoController]
 */
@Composable
fun rememberThemmoController(
  lightColorScheme: ColorScheme = lightColorScheme(),
  darkColorScheme: ColorScheme = darkColorScheme(),
  amoledThemeEnabled: Boolean = false,
  themingMode: ThemingMode = ThemingMode.AUTO,
  dynamicThemeEnabled: Boolean = false,
  customColor: Color = Color.Unspecified,
  monetMode: MonetMode = MonetMode.TonalSpot,
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

private fun Color.darken(alpha: Float) = this.copy(alpha = alpha).compositeOver(Color.Black)

private const val AMOLED_MAIN_FACTOR = 0.65f
private const val AMOLED_TEXT_FACTOR = 0.85f

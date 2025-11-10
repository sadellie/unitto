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

package com.sadellie.unitto.feature.glance.common

import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.glance.GlanceComposable
import androidx.glance.LocalContext
import androidx.glance.color.ColorProvider
import androidx.glance.unit.ColorProvider
import com.sadellie.unitto.core.designsystem.theme.DarkThemeColors
import com.sadellie.unitto.core.designsystem.theme.LightThemeColors
import io.github.sadellie.themmo.toAmoled

@Composable
internal fun WidgetTheme(isAmoledEnabled: Boolean, content: @Composable () -> Unit) {
  val colors = customColorProviders(LocalContext.current, isAmoledEnabled)
  CompositionLocalProvider(LocalUnittoGlanceColors provides colors, content = content)
}

object UnittoGlanceTheme {
  val colors: UnittoColorProviders
    @GlanceComposable @Composable @ReadOnlyComposable get() = LocalUnittoGlanceColors.current
}

internal val LocalUnittoGlanceColors: ProvidableCompositionLocal<UnittoColorProviders> =
  staticCompositionLocalOf {
    unittoColorProviders(LightThemeColors, DarkThemeColors)
  }

data class UnittoColorProviders(
  val primary: ColorProvider,
  val onPrimary: ColorProvider,
  val primaryContainer: ColorProvider,
  val onPrimaryContainer: ColorProvider,
  val secondaryContainer: ColorProvider,
  val onSecondaryContainer: ColorProvider,
  val inverseOnSurface: ColorProvider,
  val tertiaryContainer: ColorProvider,
  val onTertiaryContainer: ColorProvider,
  val onBackground: ColorProvider,
  val surfaceContainer: ColorProvider,
  val surfaceVariant: ColorProvider,
  val onSurfaceVariant: ColorProvider,
  val outline: ColorProvider,
)

private fun customColorProviders(context: Context, isAmoledEnabled: Boolean): UnittoColorProviders {
  val isApi34 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
  var dark = if (isApi34) dynamicDarkColorScheme(context) else DarkThemeColors
  if (isAmoledEnabled) dark = dark.toAmoled()
  val light = if (isApi34) dynamicLightColorScheme(context) else LightThemeColors
  return unittoColorProviders(light, dark)
}

private fun unittoColorProviders(light: ColorScheme, dark: ColorScheme) =
  UnittoColorProviders(
    primary = ColorProvider(light.primary, dark.primary),
    onPrimary = ColorProvider(light.onPrimary, dark.onPrimary),
    primaryContainer = ColorProvider(light.primaryContainer, dark.primaryContainer),
    onPrimaryContainer = ColorProvider(light.onPrimaryContainer, dark.onPrimaryContainer),
    surfaceContainer = ColorProvider(light.surfaceContainer, dark.surfaceContainer),
    surfaceVariant = ColorProvider(light.surfaceVariant, dark.surfaceVariant),
    onSurfaceVariant = ColorProvider(light.onSurfaceVariant, dark.onSurfaceVariant),
    secondaryContainer = ColorProvider(light.secondaryContainer, dark.secondaryContainer),
    onSecondaryContainer = ColorProvider(light.onSecondaryContainer, dark.onSecondaryContainer),
    inverseOnSurface = ColorProvider(light.inverseOnSurface, dark.inverseOnSurface),
    tertiaryContainer = ColorProvider(light.tertiaryContainer, dark.tertiaryContainer),
    onTertiaryContainer = ColorProvider(light.onTertiaryContainer, dark.onTertiaryContainer),
    onBackground = ColorProvider(light.onBackground, dark.onBackground),
    outline = ColorProvider(light.outline, dark.outline),
  )

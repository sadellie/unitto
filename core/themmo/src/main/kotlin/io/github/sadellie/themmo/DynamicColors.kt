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

import android.app.WallpaperColors
import android.app.WallpaperManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.kyant.m3color.dynamiccolor.MaterialDynamicColors
import com.kyant.m3color.hct.Hct
import com.kyant.m3color.scheme.SchemeContent
import com.kyant.m3color.scheme.SchemeExpressive
import com.kyant.m3color.scheme.SchemeFidelity
import com.kyant.m3color.scheme.SchemeFruitSalad
import com.kyant.m3color.scheme.SchemeMonochrome
import com.kyant.m3color.scheme.SchemeNeutral
import com.kyant.m3color.scheme.SchemeRainbow
import com.kyant.m3color.scheme.SchemeTonalSpot
import com.kyant.m3color.scheme.SchemeVibrant
import io.github.sadellie.themmo.core.MonetMode

/**
 * Extract primary color from device wallpaper.
 *
 * @param context Context
 * @return Primary color of current wallpaper image.
 */
@RequiresApi(Build.VERSION_CODES.O_MR1)
internal fun extractWallpaperPrimary(context: Context): Color? {
  val wallpaperColors: WallpaperColors =
    WallpaperManager.getInstance(context).getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
      ?: return null

  return Color(
    red = wallpaperColors.primaryColor.red(),
    green = wallpaperColors.primaryColor.green(),
    blue = wallpaperColors.primaryColor.blue(),
  )
}

internal fun dynamicColorScheme(
  keyColor: Color,
  isDark: Boolean,
  style: MonetMode = MonetMode.TonalSpot,
  contrastLevel: Double = 0.0,
): ColorScheme {
  val hct = Hct.fromInt(keyColor.toArgb())
  val colors = MaterialDynamicColors()
  val scheme =
    when (style) {
      MonetMode.TonalSpot -> SchemeTonalSpot(hct, isDark, contrastLevel)
      MonetMode.Neutral -> SchemeNeutral(hct, isDark, contrastLevel)
      MonetMode.Vibrant -> SchemeVibrant(hct, isDark, contrastLevel)
      MonetMode.Expressive -> SchemeExpressive(hct, isDark, contrastLevel)
      MonetMode.Rainbow -> SchemeRainbow(hct, isDark, contrastLevel)
      MonetMode.FruitSalad -> SchemeFruitSalad(hct, isDark, contrastLevel)
      MonetMode.Monochrome -> SchemeMonochrome(hct, isDark, contrastLevel)
      MonetMode.Fidelity -> SchemeFidelity(hct, isDark, contrastLevel)
      MonetMode.Content -> SchemeContent(hct, isDark, contrastLevel)
    }

  return ColorScheme(
    primary = Color(colors.primary().getArgb(scheme)),
    onPrimary = Color(colors.onPrimary().getArgb(scheme)),
    primaryContainer = Color(colors.primaryContainer().getArgb(scheme)),
    onPrimaryContainer = Color(colors.onPrimaryContainer().getArgb(scheme)),
    inversePrimary = Color(colors.inversePrimary().getArgb(scheme)),
    secondary = Color(colors.secondary().getArgb(scheme)),
    onSecondary = Color(colors.onSecondary().getArgb(scheme)),
    secondaryContainer = Color(colors.secondaryContainer().getArgb(scheme)),
    onSecondaryContainer = Color(colors.onSecondaryContainer().getArgb(scheme)),
    tertiary = Color(colors.tertiary().getArgb(scheme)),
    onTertiary = Color(colors.onTertiary().getArgb(scheme)),
    tertiaryContainer = Color(colors.tertiaryContainer().getArgb(scheme)),
    onTertiaryContainer = Color(colors.onTertiaryContainer().getArgb(scheme)),
    background = Color(colors.background().getArgb(scheme)),
    onBackground = Color(colors.onBackground().getArgb(scheme)),
    surface = Color(colors.surface().getArgb(scheme)),
    onSurface = Color(colors.onSurface().getArgb(scheme)),
    surfaceVariant = Color(colors.surfaceVariant().getArgb(scheme)),
    onSurfaceVariant = Color(colors.onSurfaceVariant().getArgb(scheme)),
    surfaceTint = Color(colors.surfaceTint().getArgb(scheme)),
    inverseSurface = Color(colors.inverseSurface().getArgb(scheme)),
    inverseOnSurface = Color(colors.inverseOnSurface().getArgb(scheme)),
    error = Color(colors.error().getArgb(scheme)),
    onError = Color(colors.onError().getArgb(scheme)),
    errorContainer = Color(colors.errorContainer().getArgb(scheme)),
    onErrorContainer = Color(colors.onErrorContainer().getArgb(scheme)),
    outline = Color(colors.outline().getArgb(scheme)),
    outlineVariant = Color(colors.outlineVariant().getArgb(scheme)),
    scrim = Color(colors.scrim().getArgb(scheme)),
    surfaceBright = Color(colors.surfaceBright().getArgb(scheme)),
    surfaceDim = Color(colors.surfaceDim().getArgb(scheme)),
    surfaceContainer = Color(colors.surfaceContainer().getArgb(scheme)),
    surfaceContainerHigh = Color(colors.surfaceContainerHigh().getArgb(scheme)),
    surfaceContainerHighest = Color(colors.surfaceContainerHighest().getArgb(scheme)),
    surfaceContainerLow = Color(colors.surfaceContainerLow().getArgb(scheme)),
    surfaceContainerLowest = Color(colors.surfaceContainerLowest().getArgb(scheme)),
    primaryFixed = Color(colors.primaryFixed().getArgb(scheme)),
    primaryFixedDim = Color(colors.primaryFixedDim().getArgb(scheme)),
    onPrimaryFixed = Color(colors.onPrimaryFixed().getArgb(scheme)),
    onPrimaryFixedVariant = Color(colors.onPrimaryFixedVariant().getArgb(scheme)),
    secondaryFixed = Color(colors.secondaryFixed().getArgb(scheme)),
    secondaryFixedDim = Color(colors.secondaryFixedDim().getArgb(scheme)),
    onSecondaryFixed = Color(colors.onSecondaryFixed().getArgb(scheme)),
    onSecondaryFixedVariant = Color(colors.onSecondaryFixedVariant().getArgb(scheme)),
    tertiaryFixed = Color(colors.tertiaryFixed().getArgb(scheme)),
    tertiaryFixedDim = Color(colors.tertiaryFixedDim().getArgb(scheme)),
    onTertiaryFixed = Color(colors.onTertiaryFixed().getArgb(scheme)),
    onTertiaryFixedVariant = Color(colors.onTertiaryFixedVariant().getArgb(scheme)),
  )
}

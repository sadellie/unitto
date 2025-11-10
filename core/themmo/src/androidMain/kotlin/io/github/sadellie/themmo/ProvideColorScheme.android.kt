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

import android.app.WallpaperColors
import android.app.WallpaperManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import io.github.sadellie.themmo.core.MonetMode

@Composable
internal actual fun provideDynamicColorScheme(
  isDark: Boolean,
  defaultColorScheme: ColorScheme,
): ColorScheme {
  return when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
      val context = LocalContext.current
      if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      generateColorScheme(
        keyColor = colorResource(android.R.color.system_accent1_500),
        isDark = isDark,
        style = MonetMode.TonalSpot,
      )
    }
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 -> {
      val context = LocalContext.current
      generateColorScheme(
        keyColor = extractWallpaperPrimary(context) ?: return defaultColorScheme,
        isDark = isDark,
        style = MonetMode.TonalSpot,
      )
    }
    else -> defaultColorScheme
  }
}

/**
 * Extract primary color from device wallpaper.
 *
 * @param context Context
 * @return Primary color of current wallpaper image.
 */
@RequiresApi(Build.VERSION_CODES.O_MR1)
private fun extractWallpaperPrimary(context: Context): Color? {
  val wallpaperColors: WallpaperColors =
    WallpaperManager.getInstance(context).getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
      ?: return null

  return Color(
    red = wallpaperColors.primaryColor.red(),
    green = wallpaperColors.primaryColor.green(),
    blue = wallpaperColors.primaryColor.blue(),
  )
}

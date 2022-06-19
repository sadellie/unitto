/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.ui.theme

import android.app.WallpaperManager
import android.content.Context
import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance


/**
 * Shifts colors to make them brighter
 */
@Stable
private fun Color.shiftTo255(ratio: Float): Color {
    return this
        .copy(
            alpha,
            red + (1.0f - red) * ratio,
            green + (1.0f - green) * ratio,
            blue + (1.0f - blue) * ratio,
        )
}

/**
 * Shifts colors to make them darker
 */
@Stable
private fun Color.shiftTo0(ratio: Float): Color {
    return this
        .copy(
            alpha,
            red * (1.0f - ratio),
            green * (1.0f - ratio),
            blue * (1.0f - ratio),
        )
}

/**
 * Decides which colors fits the best for the color that is used as a background
 */
@Stable
private fun Color.getAppropriateTextColor(): Color {
    return if (luminance() > 0.5) Color.Black else Color.White
}

/**
 * Function to return dynamic light theme. Determines which API to use based on Android version
 */
fun dynamicLightTheme(context: Context): ColorScheme {
    return when {
        // Android 12+ devices use new api
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> dynamicLightColorScheme(context)
        // Dynamic theming for devices with Android 8.1 up to Android 11
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 -> {
            // Wallpaper colors can be null. We return default theme for such cases
            val wallpaperColors = WallpaperManager.getInstance(context)
                .getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
                ?: return LightThemeColors

            val primary = Color(
                red = wallpaperColors.primaryColor.red(),
                green = wallpaperColors.primaryColor.green(),
                blue = wallpaperColors.primaryColor.blue()
            )
            // Secondary color can be null. For such cases we just generate it from primary
            val secondary: Color = primary.shiftTo255(0.5f)
            val background = primary.shiftTo255(0.9f)
            val onBackground = background.getAppropriateTextColor()

            return lightColorScheme(
                // Settings screen group text, units screen units group text
                primary = primary,
                // Switch thumb color
                onPrimary = primary.getAppropriateTextColor(),
                onPrimaryContainer = primary.shiftTo0(0.7f),
                // Selected unit, group, keyboard buttons
                secondaryContainer = secondary,
                onSecondaryContainer = secondary.getAppropriateTextColor(),
                // Background color for all screens
                background = background,
                // Main screen input/output text color
                onBackground = onBackground,
                // Collapsable top bar background
                surface = background,
                // Main screen Unitto text, disabled buttons
                // Settings screen title and back icon, dialog window title, not selected radio button color
                // Third party licenses screen title and back icon
                onSurface = onBackground,
                surfaceVariant = primary.shiftTo255(0.5f),
                // Main Screen settings icon, Not selected chips text, short unit names
                // Settings items secondary text
                onSurfaceVariant = primary.shiftTo0(0.80f),
                // Chips outline and cards outline on Third party licenses screen
                outline = primary.shiftTo0(0.8f),
            )
        }
        // Just in case
        else -> LightThemeColors
    }
}

/**
 * Function to return dynamic light theme. Determines which API to use based on Android version
 */
fun dynamicDarkTheme(context: Context): ColorScheme {
    return when {
        // Android 12+ devices use new api
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> dynamicDarkColorScheme(context)
        // Dynamic theming for devices with Android 8.1 up to Android 11
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 -> {
            // Wallpaper colors can be null. We return default theme for such cases
            val wallpaperColors = WallpaperManager.getInstance(context)
                .getWallpaperColors(WallpaperManager.FLAG_SYSTEM)
                ?: return DarkThemeColors

            val primary = Color(
                red = wallpaperColors.primaryColor.red(),
                green = wallpaperColors.primaryColor.green(),
                blue = wallpaperColors.primaryColor.blue()
            )
            // Secondary color can be null. For such cases we just generate it from primary
            val secondary: Color = primary.shiftTo0(0.7f)
            val background = primary.shiftTo0(0.9f)
            val onBackground = background.getAppropriateTextColor()

            return darkColorScheme(
                // Settings screen group text, units screen units group text
                primary = primary,
                // Switch thumb color
                onPrimary = primary.getAppropriateTextColor(),
                onPrimaryContainer = primary.shiftTo255(0.7f),
                // Selected unit, group, keyboard buttons
                secondaryContainer = secondary,
                onSecondaryContainer = secondary.getAppropriateTextColor(),
                // Background color for all screens
                background = background,
                // Main screen input/output text color
                onBackground = onBackground,
                // Collapsable top bar background
                surface = background,
                // Main screen Unitto text, disabled buttons
                // Settings screen title and back icon, dialog window title, not selected radio button color
                // Third party licenses screen title and back icon
                onSurface = onBackground,
                surfaceVariant = primary.shiftTo0(0.5f),
                // Main Screen settings icon, Not selected chips text, short unit names
                // Settings items secondary text
                onSurfaceVariant = primary.shiftTo255(0.80f),
                // Chips outline and cards outline on Third party licenses screen
                outline = primary.shiftTo255(0.8f),
            )
        }
        // Just in case
        else -> DarkThemeColors
    }
}

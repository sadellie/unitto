package com.sadellie.unitto.ui.theme

import android.app.WallpaperManager
import android.content.Context
import android.os.Build
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color


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

            return lightColorScheme(
                Color(
                    wallpaperColors.primaryColor.red(),
                    wallpaperColors.primaryColor.green(),
                    wallpaperColors.primaryColor.blue()
                )
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

            return darkColorScheme(
                Color(
                    wallpaperColors.primaryColor.red(),
                    wallpaperColors.primaryColor.green(),
                    wallpaperColors.primaryColor.blue()
                )
            )
        }
        // Just in case
        else -> DarkThemeColors
    }
}

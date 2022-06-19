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

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sadellie.unitto.data.preferences.AppTheme


@Composable
fun UnittoTheme(
    currentAppTheme: Int,
    content: @Composable () -> Unit
) {
    // Dynamic color is only for Android 12 and higher
    val dynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1

    val colors = when (currentAppTheme) {
        AppTheme.DARK -> DarkThemeColors
        AppTheme.LIGHT -> LightThemeColors
        AppTheme.AMOLED -> AmoledThemeColors
        AppTheme.LIGHT_DYNAMIC -> dynamicLightTheme(LocalContext.current)
        AppTheme.DARK_DYNAMIC -> dynamicDarkTheme(LocalContext.current)
        // When it is set to Auto
        else -> {
            when {
                dynamicColor and !isSystemInDarkTheme() -> dynamicLightTheme(LocalContext.current)
                dynamicColor and isSystemInDarkTheme() -> dynamicDarkTheme(LocalContext.current)
                !dynamicColor and !isSystemInDarkTheme() -> LightThemeColors
                !dynamicColor and isSystemInDarkTheme() -> DarkThemeColors
                // This case is here just in case, not actually used
                else -> LightThemeColors
            }
        }
    }

    rememberSystemUiController().setSystemBarsColor(
        color = colors.background
    )

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}

/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.unitslist.SecondViewModel
import com.sadellie.unitto.core.ui.theme.AppTypography
import com.sadellie.unitto.core.ui.theme.DarkThemeColors
import com.sadellie.unitto.core.ui.theme.LightThemeColors
import com.sadellie.unitto.feature.settings.SettingsViewModel
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.rememberThemmoController

@Composable
fun UnittoApp() {
    val converterViewModel: ConverterViewModel = hiltViewModel()
    val secondViewModel: SecondViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val userPrefs = settingsViewModel.userPrefs.collectAsStateWithLifecycle()

    val themmoController = rememberThemmoController(
        lightColorScheme = LightThemeColors,
        darkColorScheme = DarkThemeColors,
        // Anything below will not be called if theming mode is still loading from DataStore
        themingMode = userPrefs.value.themingMode ?: return,
        dynamicThemeEnabled = userPrefs.value.enableDynamicTheme,
        amoledThemeEnabled = userPrefs.value.enableAmoledTheme
    )
    val navController = rememberNavController()
    val sysUiController = rememberSystemUiController()

    Themmo(
        themmoController = themmoController,
        typography = AppTypography,
        animationSpec = tween(150)
    ) {
        val backgroundColor = MaterialTheme.colorScheme.background

        UnittoNavigation(
            navController = navController,
            converterViewModel = converterViewModel,
            secondViewModel = secondViewModel,
            settingsViewModel = settingsViewModel,
            themmoController = it,
            startDestination = userPrefs.value.startingScreen
        )

        SideEffect { sysUiController.setSystemBarsColor(backgroundColor) }
    }
}

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

package com.sadellie.unitto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sadellie.unitto.data.NavRoutes.ABOUT_SCREEN
import com.sadellie.unitto.data.NavRoutes.LEFT_LIST_SCREEN
import com.sadellie.unitto.data.NavRoutes.MAIN_SCREEN
import com.sadellie.unitto.data.NavRoutes.RIGHT_LIST_SCREEN
import com.sadellie.unitto.data.NavRoutes.SETTINGS_GRAPH
import com.sadellie.unitto.data.NavRoutes.SETTINGS_SCREEN
import com.sadellie.unitto.data.NavRoutes.THEMES_SCREEN
import com.sadellie.unitto.data.NavRoutes.UNIT_GROUPS_SCREEN
import com.sadellie.unitto.screens.main.MainViewModel
import com.sadellie.unitto.screens.main.MainScreen
import com.sadellie.unitto.screens.second.LeftSideScreen
import com.sadellie.unitto.screens.second.RightSideScreen
import com.sadellie.unitto.screens.second.SecondViewModel
import com.sadellie.unitto.screens.setttings.AboutScreen
import com.sadellie.unitto.screens.setttings.SettingsScreen
import com.sadellie.unitto.screens.setttings.SettingsViewModel
import com.sadellie.unitto.screens.setttings.ThemesScreen
import com.sadellie.unitto.screens.setttings.UnitGroupsScreen
import com.sadellie.unitto.ui.theme.AppTypography
import com.sadellie.unitto.ui.theme.DarkThemeColors
import com.sadellie.unitto.ui.theme.LightThemeColors
import dagger.hilt.android.AndroidEntryPoint
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController
import io.github.sadellie.themmo.rememberThemmoController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val userPrefs = settingsViewModel.userPrefs

            val themmoController = rememberThemmoController(
                lightColorScheme = LightThemeColors,
                darkColorScheme = DarkThemeColors,
                // Anything below will not called if theming mode is still loading from DataStore
                themingMode = userPrefs.themingMode ?: return@setContent,
                dynamicThemeEnabled = userPrefs.enableDynamicTheme,
                amoledThemeEnabled = userPrefs.enableAmoledTheme
            )
            val navController = rememberNavController()
            val sysUiController = rememberSystemUiController()

            Themmo(
                themmoController = themmoController,
                typography = AppTypography,
                animationSpec = tween(150)
            ) {
                val backgroundColor = MaterialTheme.colorScheme.background

                UnittoApp(
                    navController = navController,
                    settingsViewModel = settingsViewModel,
                    themmoController = it
                )

                SideEffect { sysUiController.setSystemBarsColor(backgroundColor) }
            }
        }
    }
}

@Composable
fun UnittoApp(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    themmoController: ThemmoController
) {
    val secondViewModel: SecondViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN
    ) {
        composable(MAIN_SCREEN) {
            MainScreen(
                navControllerAction = { route -> navController.navigate(route) },
                viewModel = mainViewModel
            )
        }

        composable(LEFT_LIST_SCREEN) {
            LeftSideScreen(
                viewModel = secondViewModel,
                currentUnit = mainViewModel.unitFrom,
                navigateUp = { navController.navigateUp() },
                navigateToSettingsActtion = { navController.navigate(UNIT_GROUPS_SCREEN) },
                selectAction = { mainViewModel.changeUnitFrom(it) }
            )
        }

        composable(RIGHT_LIST_SCREEN) {
            RightSideScreen(
                viewModel = secondViewModel,
                currentUnit = mainViewModel.unitTo,
                navigateUp = { navController.navigateUp() },
                navigateToSettingsActtion = { navController.navigate(UNIT_GROUPS_SCREEN) },
                selectAction = { mainViewModel.changeUnitTo(it) },
                inputValue = mainViewModel.mainFlow.value.inputValue.toBigDecimal(),
                unitFrom = mainViewModel.unitFrom
            )
        }

        settingGraph(
            settingsViewModel = settingsViewModel,
            themmoController = themmoController,
            navController = navController
        )
    }
}

private fun NavGraphBuilder.settingGraph(
    settingsViewModel: SettingsViewModel,
    themmoController: ThemmoController,
    navController: NavHostController
) {
    navigation(SETTINGS_SCREEN, SETTINGS_GRAPH) {
        composable(SETTINGS_SCREEN) {
            SettingsScreen(
                viewModel = settingsViewModel,
                navigateUpAction = { navController.navigateUp() }
            ) { route -> navController.navigate(route) }
        }

        composable(THEMES_SCREEN) {
            ThemesScreen(
                navigateUpAction = { navController.navigateUp() },
                themmoController = themmoController,
                viewModel = settingsViewModel
            )
        }

        composable(ABOUT_SCREEN) {
            AboutScreen(navigateUpAction = { navController.navigateUp() })
        }

        composable(UNIT_GROUPS_SCREEN) {
            UnitGroupsScreen(
                viewModel = settingsViewModel,
                navigateUpAction = { navController.navigateUp() }
            )
        }
    }
}

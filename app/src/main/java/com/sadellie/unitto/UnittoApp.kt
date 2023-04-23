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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.core.ui.model.DrawerItems
import com.sadellie.unitto.core.ui.common.UnittoDrawerSheet
import com.sadellie.unitto.core.ui.theme.AppTypography
import com.sadellie.unitto.core.ui.theme.DarkThemeColors
import com.sadellie.unitto.core.ui.theme.LightThemeColors
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.settings.SettingsViewModel
import com.sadellie.unitto.feature.unitslist.UnitsListViewModel
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.rememberThemmoController
import kotlinx.coroutines.launch

@Composable
internal fun UnittoApp() {
    val converterViewModel: ConverterViewModel = hiltViewModel()
    val unitsListViewModel: UnitsListViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val userPrefs = settingsViewModel.userPrefs.collectAsStateWithLifecycle()

    val themmoController = rememberThemmoController(
        lightColorScheme = LightThemeColors,
        darkColorScheme = DarkThemeColors,
        // Anything below will not be called if theming mode is still loading from DataStore
        themingMode = userPrefs.value.themingMode ?: return,
        dynamicThemeEnabled = userPrefs.value.enableDynamicTheme,
        amoledThemeEnabled = userPrefs.value.enableAmoledTheme,
        customColor = userPrefs.value.customColor,
        monetMode = userPrefs.value.monetMode
    )
    val navController = rememberNavController()
    val sysUiController = rememberSystemUiController()

    // Navigation drawer stuff
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val drawerScope = rememberCoroutineScope()
    val mainTabs = listOf(DrawerItems.Calculator, DrawerItems.Converter)
    val additionalTabs = listOf(DrawerItems.Settings)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: TopLevelDestinations? by remember(navBackStackEntry?.destination) {
        derivedStateOf {
            val hierarchyRoutes = navBackStackEntry?.destination?.hierarchy?.map { it.route }
                ?: emptySequence()

            (mainTabs + additionalTabs)
                .map { it.destination }
                .firstOrNull {
                    hierarchyRoutes.contains(it.route)
                }
        }
    }
    val gesturesEnabled: Boolean by remember(navBackStackEntry?.destination) {
        derivedStateOf {
            // Will be true for routes like
            // [null, calculator_route, settings_graph, settings_route, themes_route]
            // We disable drawer drag gesture when we are too deep
            navController.backQueue.size <= 4
        }
    }

    Themmo(
        themmoController = themmoController,
        typography = AppTypography,
        animationSpec = tween(250)
    ) {
        val statusBarColor = when (currentRoute) {
            // Match text field container color
            TopLevelDestinations.Calculator -> MaterialTheme.colorScheme.surfaceVariant
            else -> MaterialTheme.colorScheme.background
        }
        val navigationBarColor = MaterialTheme.colorScheme.background

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = gesturesEnabled,
            drawerContent = {
                UnittoDrawerSheet(
                    modifier = Modifier,
                    mainTabs = mainTabs,
                    additionalTabs = additionalTabs,
                    currentDestination = currentRoute
                ) {
                    drawerScope.launch { drawerState.close() }
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        ) {
            UnittoNavigation(
                navController = navController,
                converterViewModel = converterViewModel,
                unitsListViewModel = unitsListViewModel,
                settingsViewModel = settingsViewModel,
                themmoController = it,
                startDestination = userPrefs.value.startingScreen,
                openDrawer = { drawerScope.launch { drawerState.open() } }
            )
        }

        SideEffect {
            sysUiController.setNavigationBarColor(navigationBarColor)
            sysUiController.setStatusBarColor(statusBarColor)
        }
    }
}

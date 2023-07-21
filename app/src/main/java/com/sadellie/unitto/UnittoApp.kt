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

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.core.ui.common.UnittoDrawerSheet
import com.sadellie.unitto.core.ui.common.UnittoModalNavigationDrawer
import com.sadellie.unitto.core.ui.common.close
import com.sadellie.unitto.core.ui.common.isOpen
import com.sadellie.unitto.core.ui.common.open
import com.sadellie.unitto.core.ui.common.rememberUnittoDrawerState
import com.sadellie.unitto.core.ui.model.DrawerItems
import com.sadellie.unitto.core.ui.theme.AppTypography
import com.sadellie.unitto.core.ui.theme.DarkThemeColors
import com.sadellie.unitto.core.ui.theme.LightThemeColors
import com.sadellie.unitto.data.userprefs.UIPreferences
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.rememberThemmoController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun UnittoApp(uiPrefs: UIPreferences) {

    val themmoController = rememberThemmoController(
        lightColorScheme = LightThemeColors,
        darkColorScheme = DarkThemeColors,
        themingMode = uiPrefs.themingMode,
        dynamicThemeEnabled = uiPrefs.enableDynamicTheme,
        amoledThemeEnabled = uiPrefs.enableAmoledTheme,
        customColor = uiPrefs.customColor,
        monetMode = uiPrefs.monetMode
    )
    val navController = rememberNavController()
    val sysUiController = rememberSystemUiController()

    // Navigation drawer stuff
    val drawerState = rememberUnittoDrawerState()
    val drawerScope = rememberCoroutineScope()
    val mainTabs = listOf(
        DrawerItems.Calculator,
        DrawerItems.Converter,
        DrawerItems.DateDifference
    )
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

    Themmo(
        themmoController = themmoController,
        typography = AppTypography,
        animationSpec = tween(250)
    ) {
        val backgroundColor = MaterialTheme.colorScheme.background
        val useDarkIcons by remember(backgroundColor) {
            mutableStateOf(backgroundColor.luminance() > 0.5f)
        }

        UnittoModalNavigationDrawer(
            drawer = {
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
            },
            modifier = Modifier,
            state = drawerState,
            gesturesEnabled = true,
            scope = drawerScope,
            content = {
                UnittoNavigation(
                    navController = navController,
                    themmoController = it,
                    startDestination = uiPrefs.startingScreen,
                    openDrawer = { drawerScope.launch { drawerState.open() } }
                )
            }
        )

        BackHandler(drawerState.isOpen) {
            drawerScope.launch { drawerState.close() }
        }

        LaunchedEffect(useDarkIcons) {
            sysUiController.setNavigationBarColor(Color.Transparent, useDarkIcons)
            sysUiController.setStatusBarColor(Color.Transparent, useDarkIcons)
        }
    }
}

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sadellie.unitto.core.base.Shortcut
import com.sadellie.unitto.core.base.TOP_LEVEL_START_ROUTES
import com.sadellie.unitto.core.ui.common.UnittoDrawerSheet
import com.sadellie.unitto.core.ui.common.UnittoModalNavigationDrawer
import com.sadellie.unitto.core.ui.common.close
import com.sadellie.unitto.core.ui.common.isOpen
import com.sadellie.unitto.core.ui.common.open
import com.sadellie.unitto.core.ui.common.rememberUnittoDrawerState
import com.sadellie.unitto.core.ui.model.DrawerItems
import com.sadellie.unitto.core.ui.pushDynamicShortcut
import com.sadellie.unitto.core.ui.theme.DarkThemeColors
import com.sadellie.unitto.core.ui.theme.LightThemeColors
import com.sadellie.unitto.core.ui.theme.TypographySystem
import com.sadellie.unitto.data.userprefs.AppPreferences
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun UnittoApp(prefs: AppPreferences?) {

    val mContext = LocalContext.current
    val navController = rememberNavController()
    val sysUiController = rememberSystemUiController()

    // Navigation drawer stuff
    val drawerState = rememberUnittoDrawerState()
    val drawerScope = rememberCoroutineScope()

    val shortcutsScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val gesturesEnabled: Boolean by remember(navBackStackEntry?.destination) {
        derivedStateOf {
            TOP_LEVEL_START_ROUTES.contains(navBackStackEntry?.destination?.route)
        }
    }

    if (prefs != null) {
        val themmoController = remember(prefs) {
            ThemmoController(
                lightColorScheme = LightThemeColors,
                darkColorScheme = DarkThemeColors,
                themingMode = prefs.themingMode,
                dynamicThemeEnabled = prefs.enableDynamicTheme,
                amoledThemeEnabled = prefs.enableAmoledTheme,
                customColor = prefs.customColor,
                monetMode = prefs.monetMode
            )
        }

        Themmo(
            themmoController = themmoController,
            typography = TypographySystem,
            animationSpec = tween(250)
        ) {
            val backgroundColor = MaterialTheme.colorScheme.background
            val useDarkIcons = remember(backgroundColor) { backgroundColor.luminance() > 0.5f }

            UnittoModalNavigationDrawer(
                drawer = {
                    UnittoDrawerSheet(
                        modifier = Modifier,
                        tabs = DrawerItems.ALL,
                        currentDestination = navBackStackEntry?.destination?.route
                    ) { destination ->
                        drawerScope.launch { drawerState.close() }

                        navController.navigate(destination.graph) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                        shortcutsScope.launch {
                            destination.shortcut?.let { shortcut: Shortcut ->
                                mContext.pushDynamicShortcut(
                                    destination.graph,
                                    shortcut.shortcutShortLabel,
                                    shortcut.shortcutLongLabel,
                                    shortcut.shortcutDrawable
                                )
                            }
                        }
                    }
                },
                modifier = Modifier,
                state = drawerState,
                gesturesEnabled = gesturesEnabled,
                scope = drawerScope,
                content = {
                    UnittoNavigation(
                        navController = navController,
                        themmoController = it,
                        startDestination = prefs.startingScreen,
                        openDrawer = { drawerScope.launch { drawerState.open() } }
                    )
                }
            )

            LaunchedEffect(useDarkIcons) {
                sysUiController.setNavigationBarColor(Color.Transparent, useDarkIcons)
                sysUiController.setStatusBarColor(Color.Transparent, useDarkIcons)
            }
        }
    }

    BackHandler(drawerState.isOpen) {
        drawerScope.launch { drawerState.close() }
    }
}

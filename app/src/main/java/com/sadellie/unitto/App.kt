/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.sadellie.unitto.core.ui.common.NavigationDrawer
import com.sadellie.unitto.core.ui.common.rememberDrawerState
import com.sadellie.unitto.core.ui.model.DrawerItem
import com.sadellie.unitto.core.ui.pushDynamicShortcut
import com.sadellie.unitto.core.ui.theme.DarkThemeColors
import com.sadellie.unitto.core.ui.theme.LightThemeColors
import com.sadellie.unitto.core.ui.theme.TypographySystem
import com.sadellie.unitto.data.model.userprefs.AppPreferences
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController
import kotlinx.coroutines.launch

@Composable
internal fun ComponentActivity.App(prefs: AppPreferences?) {
    val mContext = LocalContext.current
    val navController = rememberNavController()

    val drawerScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()

    val shortcutsScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val gesturesEnabled: Boolean by remember(navBackStackEntry?.destination) {
        derivedStateOf {
            DrawerItem.startRoutes.contains(navBackStackEntry?.destination?.route)
        }
    }

    if (prefs == null) return

    val themmoController = remember(prefs) {
        ThemmoController(
            lightColorScheme = LightThemeColors,
            darkColorScheme = DarkThemeColors,
            themingMode = prefs.themingMode,
            dynamicThemeEnabled = prefs.enableDynamicTheme,
            amoledThemeEnabled = prefs.enableAmoledTheme,
            customColor = prefs.customColor.toColor(),
            monetMode = prefs.monetMode
        )
    }

    Themmo(
        themmoController = themmoController,
        typography = TypographySystem,
        animationSpec = tween(250)
    ) {
        val backgroundColor = MaterialTheme.colorScheme.background
        val isDarkThemeEnabled = remember(backgroundColor) { backgroundColor.luminance() < 0.5f }

        NavigationDrawer(
            modifier = Modifier,
            state = drawerState,
            gesturesEnabled = gesturesEnabled,
            tabs = DrawerItem.main,
            currentDestination = navBackStackEntry?.destination?.route,
            onItemClick = { destination ->
                drawerScope.launch { drawerState.close() }

                navController.navigate(destination.graph) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }

                shortcutsScope.launch { mContext.pushDynamicShortcut(destination) }
            },
            content = {
                UnittoNavigation(
                    navController = navController,
                    themmoController = it,
                    startDestination = prefs.startingScreen,
                    rpnMode = prefs.rpnMode,
                    openDrawer = { drawerScope.launch { drawerState.open() } }
                )
            }
        )

        DisposableEffect(isDarkThemeEnabled) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT,
                ) { isDarkThemeEnabled },
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim,
                    darkScrim,
                ) { isDarkThemeEnabled },
            )
            onDispose {}
        }
    }

    BackHandler(drawerState.isOpen) {
        drawerScope.launch { drawerState.close() }
    }
}

private fun Long.toColor(): Color = try {
    Color(this.toULong())
} catch (e: Exception) {
    Color.Unspecified
}

// The default scrims, as defined by androidx and the platform
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

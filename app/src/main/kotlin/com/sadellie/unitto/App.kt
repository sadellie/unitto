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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sadellie.unitto.core.datastore.AppPreferences
import com.sadellie.unitto.core.designsystem.theme.DarkThemeColors
import com.sadellie.unitto.core.designsystem.theme.LightThemeColors
import com.sadellie.unitto.core.designsystem.theme.isDark
import com.sadellie.unitto.core.navigation.DrawerItem
import com.sadellie.unitto.core.navigation.additionalDrawerItems
import com.sadellie.unitto.core.navigation.graphRoutes
import com.sadellie.unitto.core.navigation.mainDrawerItems
import com.sadellie.unitto.core.ui.NavigationDrawer
import com.sadellie.unitto.core.ui.UnittoDrawerValue
import com.sadellie.unitto.core.ui.rememberUnittoDrawerState
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.ThemmoController
import kotlinx.coroutines.launch

@Composable
internal fun ComponentActivity.App(prefs: AppPreferences?) {
  val navController = rememberNavController()
  val shortcutsScope = rememberCoroutineScope()
  val drawerScope = rememberCoroutineScope()
  val drawerState = rememberUnittoDrawerState(UnittoDrawerValue.Closed)
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentGraphRoute =
    remember(navBackStackEntry?.destination) {
      val currentGraph = navBackStackEntry?.destination?.parent ?: return@remember null
      (mainDrawerItems + additionalDrawerItems)
        .firstOrNull { drawerItem -> currentGraph.hasRoute(drawerItem.graphRoute::class) }
        ?.graphRoute
    }
  val gesturesEnabled: Boolean = remember(currentGraphRoute) { currentGraphRoute in graphRoutes }
  val themmoController = rememberUnittoThemmoController(prefs ?: return)

  BackHandler(drawerState.isOpen) { drawerScope.launch { drawerState.close() } }

  Themmo(themmoController = themmoController, animationSpec = tween(THEME_CHANGE_DURATION)) {
    val mContext = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background
    val isDarkThemeEnabled = remember(backgroundColor) { backgroundColor.isDark() }

    NavigationDrawer(
      modifier = Modifier,
      state = drawerState,
      gesturesEnabled = gesturesEnabled,
      mainTabs = mainDrawerItems,
      additionalTabs = additionalDrawerItems,
      currentDestination = currentGraphRoute,
      onItemClick = { destination ->
        drawerScope.launch { drawerState.close() }
        navigateToTab(destination, navController)
        shortcutsScope.launch { destination.pushDynamicShortcut(mContext) }
      },
    ) {
      UnittoNavigation(
        navController = navController,
        themmoController = it,
        startDestination = prefs.startingScreen,
        openDrawer = { drawerScope.launch { drawerState.open() } },
      )
    }

    DisposableEffect(isDarkThemeEnabled) {
      applyEdgeToEdgeTheming(isDarkThemeEnabled)
      onDispose {}
    }
  }
}

/** Remembers [ThemmoController] using [prefs] as a key. */
@Composable
private fun rememberUnittoThemmoController(prefs: AppPreferences) =
  remember(
    prefs.themingMode,
    prefs.enableDynamicTheme,
    prefs.enableAmoledTheme,
    prefs.customColor,
    prefs.monetMode,
  ) {
    ThemmoController(
      lightColorScheme = LightThemeColors,
      darkColorScheme = DarkThemeColors,
      themingMode = prefs.themingMode,
      dynamicThemeEnabled = prefs.enableDynamicTheme,
      amoledThemeEnabled = prefs.enableAmoledTheme,
      customColor = prefs.customColor.toColorOrUnspecified(),
      monetMode = prefs.monetMode,
    )
  }

/** Navigates to clicked [destination]. */
private fun navigateToTab(destination: DrawerItem, navController: NavHostController) {
  navController.navigate(destination.graphRoute) {
    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
    launchSingleTop = true
    restoreState = true
  }
}

/** Enables edge to edge and updates colors in status and navigation bar to match theming mode. */
private fun ComponentActivity.applyEdgeToEdgeTheming(isDarkThemeEnabled: Boolean) {
  enableEdgeToEdge(
    statusBarStyle =
    SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT) {
      isDarkThemeEnabled
    },
    navigationBarStyle = SystemBarStyle.auto(lightScrim, darkScrim) { isDarkThemeEnabled },
  )
}

/**
 * Tries to convert [this] to [Color].
 *
 * @return Converted color or [Color.Unspecified] if failed to convert.
 */
private fun Long.toColorOrUnspecified(): Color =
  try {
    Color(this.toULong())
  } catch (e: Exception) {
    Color.Unspecified
  }

// The default scrims, as defined by androidx and the platform
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
private const val THEME_CHANGE_DURATION = 250

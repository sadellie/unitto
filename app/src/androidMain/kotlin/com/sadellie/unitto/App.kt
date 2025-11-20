/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.rememberNavBackStack
import com.sadellie.unitto.core.datastore.AppPreferences
import com.sadellie.unitto.core.designsystem.theme.isDark
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.pushDynamicShortcut
import io.github.sadellie.themmo.Themmo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun ComponentActivity.App(deepLinkRoute: Route?, prefs: AppPreferences?) {
  val themmoController = rememberUnittoThemmoController(prefs ?: return)
  Themmo(themmoController = themmoController, motionScheme = MotionScheme.expressive()) {
    val mContext = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.surfaceContainer
    val isDarkThemeEnabled = remember(backgroundColor) { backgroundColor.isDark() }
    val shortcutsScope = rememberCoroutineScope()
    val backStack = rememberNavBackStack(deepLinkRoute ?: prefs.startingScreen)
    MainAppContent(
      backStack = backStack,
      onDrawerItemClick = { drawerItem ->
        shortcutsScope.launch { drawerItem.pushDynamicShortcut(mContext) }
      },
      themmoController = themmoController,
    )

    DisposableEffect(isDarkThemeEnabled) {
      applyEdgeToEdgeTheming(isDarkThemeEnabled)
      onDispose {}
    }
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

// The default scrims, as defined by androidx and the platform
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sadellie.unitto.core.data.dataModule
import com.sadellie.unitto.core.database.unittoDatabaseModule
import com.sadellie.unitto.core.datastore.AppPreferences
import com.sadellie.unitto.core.datastore.dataStoreModule
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.designsystem.theme.numberTypographyUnitto
import com.sadellie.unitto.core.navigation.CalculatorGraphRoute
import com.sadellie.unitto.core.navigation.additionalDrawerItems
import com.sadellie.unitto.core.navigation.graphRoutes
import com.sadellie.unitto.core.navigation.mainDrawerItems
import com.sadellie.unitto.core.ui.BackHandler
import com.sadellie.unitto.core.ui.NavigationDrawer
import com.sadellie.unitto.core.ui.UnittoDrawerValue
import com.sadellie.unitto.core.ui.rememberUnittoDrawerState
import com.sadellie.unitto.feature.calculator.calculatorModule
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin

@OptIn(
  ExperimentalComposeUiApi::class,
  ExperimentalMaterial3WindowSizeClassApi::class,
  ExperimentalMaterial3ExpressiveApi::class,
)
fun main() {
  initKoin()
  ComposeViewport(document.body!!) {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val windowSizeClass =
      remember(containerSize, density) {
        with(density) {
          WindowSizeClass.calculateFromSize(
            DpSize(containerSize.width.toDp(), containerSize.height.toDp())
          )
        }
      }
    CompositionLocalProvider(
      LocalWindowSize provides windowSizeClass,
      LocalNumberTypography provides numberTypographyUnitto(),
    ) {
      val appPreferences = remember {
        AppPreferences(
          themingMode = ThemingMode.FORCE_DARK,
          enableDynamicTheme = false,
          enableAmoledTheme = false,
          customColor = 16L,
          monetMode = MonetMode.TonalSpot,
          startingScreen = CalculatorGraphRoute,
          enableToolsExperiment = false,
          enableVibrations = false,
          enableKeepScreenOn = false,
        )
      }
      val themmoController = rememberUnittoThemmoController(appPreferences)

      Themmo(themmoController = themmoController) {
        val drawerScope = rememberCoroutineScope()
        val drawerState = rememberUnittoDrawerState(UnittoDrawerValue.Closed)
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentGraphRoute =
          remember(navBackStackEntry?.destination) {
            val currentGraph = navBackStackEntry?.destination?.parent ?: return@remember null
            (mainDrawerItems + additionalDrawerItems)
              .firstOrNull { drawerItem -> currentGraph.hasRoute(drawerItem.graphRoute::class) }
              ?.graphRoute
          }
        val gesturesEnabled: Boolean =
          remember(currentGraphRoute) { currentGraphRoute in graphRoutes }
        BackHandler(drawerState.isOpen) { drawerScope.launch { drawerState.close() } }
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
          },
        ) {
          UnittoNavigation(
            navController = navController,
            themmoController = it,
            startDestination = appPreferences.startingScreen,
            openDrawer = { drawerScope.launch { drawerState.open() } },
          )
        }
      }
    }
  }
}

fun initKoin() {
  startKoin { modules(unittoDatabaseModule, dataStoreModule, dataModule, calculatorModule) }
}

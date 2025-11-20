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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import com.sadellie.unitto.core.navigation.DrawerItem
import com.sadellie.unitto.core.navigation.TopLevelRoute
import com.sadellie.unitto.core.navigation.additionalDrawerItems
import com.sadellie.unitto.core.navigation.mainDrawerItems
import com.sadellie.unitto.core.ui.BackHandler
import com.sadellie.unitto.core.ui.NavigationDrawer
import com.sadellie.unitto.core.ui.UnittoDrawerState
import com.sadellie.unitto.core.ui.UnittoDrawerValue
import com.sadellie.unitto.core.ui.rememberUnittoDrawerState
import io.github.sadellie.themmo.ThemmoController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun MainAppContent(
  backStack: NavBackStack<NavKey>,
  onDrawerItemClick: (DrawerItem) -> Unit,
  themmoController: ThemmoController,
) {
  val drawerScope = rememberCoroutineScope()
  val drawerState = rememberUnittoDrawerState(UnittoDrawerValue.Closed)

  val currentRoute = backStack.lastOrNull()
  val currentTopLevelRoute = remember(currentRoute) { backStack.currentTopLevelRoute() }
  // allow opening drawer only when at top level route
  val gesturesEnabled = remember(currentRoute) { currentRoute is TopLevelRoute }

  NavigationDrawer(
    modifier = Modifier,
    state = drawerState,
    gesturesEnabled = gesturesEnabled,
    mainTabs = mainDrawerItems,
    additionalTabs = additionalDrawerItems,
    currentDestination = currentTopLevelRoute,
    onItemClick = { destination ->
      drawerScope.launch { drawerState.close() }
      navigateToTab(destination, backStack)
      onDrawerItemClick(destination)
    },
  ) {
    UnittoNavigation(
      backStack = backStack,
      themmoController = themmoController,
      drawerState = drawerState,
    )
  }
}

@Composable
internal expect fun UnittoNavigation(
  backStack: NavBackStack<NavKey>,
  themmoController: ThemmoController,
  drawerState: UnittoDrawerState,
)

internal fun NavBackStack<NavKey>.currentTopLevelRoute(): TopLevelRoute? =
  this.lastOrNull { it is TopLevelRoute } as? TopLevelRoute

@Composable
internal fun <T : Any> rememberDrawerCloseGestureNavEntryDecorator(
  drawerState: UnittoDrawerState,
  coroutineScope: CoroutineScope,
): DrawerCloseGestureNavEntryDecorator<T> =
  remember(drawerState, coroutineScope) {
    DrawerCloseGestureNavEntryDecorator(drawerState, coroutineScope)
  }

internal class DrawerCloseGestureNavEntryDecorator<T : Any>(
  drawerState: UnittoDrawerState,
  coroutineScope: CoroutineScope,
) :
  NavEntryDecorator<T>(
    decorate = { entry ->
      entry.Content()
      // must be composed after Content to override any BackHandler that was composed before it
      BackHandler(drawerState.isOpen) { coroutineScope.launch { drawerState.close() } }
    }
  )

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

import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sadellie.unitto.core.designsystem.unittoFadeIn
import com.sadellie.unitto.core.designsystem.unittoFadeOut
import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.core.navigation.Navigator
import com.sadellie.unitto.core.ui.UnittoDrawerState
import io.github.sadellie.themmo.ThemmoController
import kotlinx.coroutines.launch
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
actual fun UnittoNavigation(
  backStack: NavBackStack<NavKey>,
  themmoController: ThemmoController,
  drawerState: UnittoDrawerState,
) {
  val drawerScope = rememberCoroutineScope()
  val navigator =
    remember(backStack, drawerState) {
      Navigator(
        backStack = backStack,
        openDrawerAction = { drawerScope.launch { drawerState.open() } },
        navigateToUnitGroupsAction = { /*TODO*/ },
      )
    }
  CompositionLocalProvider(LocalNavigator provides navigator) {
    NavDisplay(
      modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
      backStack = backStack,
      popTransitionSpec = { unittoFadeIn() togetherWith unittoFadeOut() },
      transitionSpec = { unittoFadeIn() togetherWith unittoFadeOut() },
      predictivePopTransitionSpec = { unittoFadeIn() togetherWith unittoFadeOut() },
      entryDecorators =
        listOf(
          rememberSaveableStateHolderNavEntryDecorator(),
          rememberViewModelStoreNavEntryDecorator(),
          rememberDrawerCloseGestureNavEntryDecorator(drawerState, drawerScope),
        ),
      entryProvider = koinEntryProvider(),
    )
  }
}

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

package com.sadellie.unitto.feature.converter.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navDeepLink
import com.sadellie.unitto.core.designsystem.unittoComposable
import com.sadellie.unitto.core.designsystem.unittoNavigation
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.navigation.ConverterGraphRoute
import com.sadellie.unitto.core.navigation.ConverterStartRoute
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.deepLink
import com.sadellie.unitto.feature.converter.ConverterRoute
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.converter.UnitFromSelectorRoute
import com.sadellie.unitto.feature.converter.UnitToSelectorRoute
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.converterGraph(
  openDrawer: () -> Unit,
  navController: NavHostController,
  navigateToUnitGroups: () -> Unit,
) {
  unittoNavigation<ConverterGraphRoute>(
    startDestination = ConverterStartRoute::class,
    deepLinks = listOf(navDeepLink { uriPattern = deepLink(ConverterGraphRoute) }),
  ) {
    unittoComposable<ConverterStartRoute>(
      deepLinks = listOf(navDeepLink<ConverterStartRoute>(ConverterStartRoute.BASE_PATH))
    ) { backStackEntry ->
      val parentEntry =
        remember(backStackEntry) { navController.getBackStackEntry(ConverterGraphRoute) }
      val parentViewModel = koinViewModel<ConverterViewModel>(viewModelStoreOwner = parentEntry)

      ConverterRoute(
        viewModel = parentViewModel,
        navigateToLeftScreen = { unitFromId, group ->
          navController.navigate(UnitFromRoute(unitFromId, group))
        },
        navigateToRightScreen = {
          unitFromId: String,
          unitToId: String,
          group: UnitGroup,
          input1: String,
          input2: String ->
          navController.navigate(UnitToRoute(unitFromId, unitToId, group, input1, input2))
        },
        openDrawer = openDrawer,
      )
    }

    unittoComposable<UnitFromRoute> { backStackEntry ->
      val parentEntry =
        remember(backStackEntry) { navController.getBackStackEntry(ConverterGraphRoute) }
      val parentViewModel = koinViewModel<ConverterViewModel>(viewModelStoreOwner = parentEntry)

      UnitFromSelectorRoute(
        unitSelectorViewModel = koinViewModel(),
        converterViewModel = parentViewModel,
        navigateUp = navController::navigateUp,
        navigateToUnitGroups = navigateToUnitGroups,
      )
    }

    unittoComposable<UnitToRoute> { backStackEntry ->
      val parentEntry =
        remember(backStackEntry) { navController.getBackStackEntry(ConverterGraphRoute) }
      val parentViewModel = koinViewModel<ConverterViewModel>(viewModelStoreOwner = parentEntry)

      UnitToSelectorRoute(
        unitSelectorViewModel = koinViewModel(),
        converterViewModel = parentViewModel,
        navigateUp = navController::navigateUp,
        navigateToUnitGroups = navigateToUnitGroups,
      )
    }
  }
}

@Serializable
internal data class UnitFromRoute(val unitFromId: String, val unitGroup: UnitGroup) : Route {
  override val id = "unit_from"
}

@Serializable
internal data class UnitToRoute(
  val unitFromId: String,
  val unitToId: String,
  val unitGroup: UnitGroup,
  val input1: String,
  val input2: String,
) : Route {
  override val id = "unit_to"
}

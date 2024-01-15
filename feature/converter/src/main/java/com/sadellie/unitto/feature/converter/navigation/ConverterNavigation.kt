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

package com.sadellie.unitto.feature.converter.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navDeepLink
import com.sadellie.unitto.core.ui.model.DrawerItem
import com.sadellie.unitto.core.ui.unittoComposable
import com.sadellie.unitto.core.ui.unittoNavigation
import com.sadellie.unitto.feature.converter.ConverterRoute
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.converter.LeftSideRoute
import com.sadellie.unitto.feature.converter.RightSideRoute

private val graph = DrawerItem.Converter.graph
private val start = DrawerItem.Converter.start
private const val LEFT = "left"
private const val RIGHT = "right"

fun NavGraphBuilder.converterGraph(
    openDrawer: () -> Unit,
    navController: NavHostController,
    navigateToSettings: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    unittoNavigation(
        startDestination = start,
        route = graph,
        deepLinks = listOf(
            navDeepLink { uriPattern = "app://com.sadellie.unitto/$graph" }
        )
    ) {
        unittoComposable(start) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(graph)
            }

            val parentViewModel = hiltViewModel<ConverterViewModel>(parentEntry)

            ConverterRoute(
                viewModel = parentViewModel,
                navigateToLeftScreen = { navController.navigate(LEFT) },
                navigateToRightScreen = { navController.navigate(RIGHT) },
                navigateToSettings = navigateToSettings,
                navigateToMenu = openDrawer
            )
        }

        unittoComposable(LEFT) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(graph)
            }

            val parentViewModel = hiltViewModel<ConverterViewModel>(parentEntry)

            LeftSideRoute(
                viewModel = parentViewModel,
                navigateUp = navController::navigateUp,
                navigateToUnitGroups = navigateToUnitGroups
            )
        }

        unittoComposable(RIGHT) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(graph)
            }

            val parentViewModel = hiltViewModel<ConverterViewModel>(parentEntry)

            RightSideRoute(
                viewModel = parentViewModel,
                navigateUp = navController::navigateUp,
                navigateToUnitGroups = navigateToUnitGroups
            )
        }
    }
}

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

package com.sadellie.unitto.feature.converter.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.sadellie.unitto.core.ui.model.DrawerItem
import com.sadellie.unitto.core.ui.unittoComposable
import com.sadellie.unitto.core.ui.unittoNavigation
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.feature.converter.ConverterRoute
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.converter.UnitFromSelectorRoute
import com.sadellie.unitto.feature.converter.UnitToSelectorRoute

private val graph = DrawerItem.Converter.graph
private val start = DrawerItem.Converter.start

private const val UNIT_FROM = "unitFromSelector"
private const val UNIT_TO = "unitToSelector"
internal const val UNIT_GROUP_ARG = "unitGroupArg"
internal const val UNIT_FROM_ID_ARG = "unitFromIdArg"
internal const val UNIT_TO_ID_ARG = "unitToIdArg"
internal const val INPUT_ARG = "inputArg"

private const val UNIT_FROM_ROUTE = "$UNIT_FROM/{$UNIT_FROM_ID_ARG}/{$UNIT_GROUP_ARG}"
private const val UNIT_TO_ROUTE = "$UNIT_TO/{$UNIT_FROM_ID_ARG}/{$UNIT_TO_ID_ARG}/{$UNIT_GROUP_ARG}/{$INPUT_ARG}"
private fun NavHostController.navigateLeft(
    unitFromId: String,
    unitGroup: UnitGroup,
) = navigate("$UNIT_FROM/$unitFromId/$unitGroup")

private fun NavHostController.navigateRight(
    unitFromId: String,
    unitToId: String,
    unitGroup: UnitGroup,
    input: String,
) = navigate("$UNIT_TO/$unitFromId/$unitToId/$unitGroup/${input.ifEmpty { null }}")

fun NavGraphBuilder.converterGraph(
    openDrawer: () -> Unit,
    navController: NavHostController,
    navigateToUnitGroups: () -> Unit,
) {
    unittoNavigation(
        startDestination = start,
        route = graph,
        deepLinks = listOf(
            navDeepLink { uriPattern = "app://com.sadellie.unitto/$graph" },
        ),
    ) {
        unittoComposable(start) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(graph)
            }

            val parentViewModel = hiltViewModel<ConverterViewModel>(parentEntry)

            ConverterRoute(
                viewModel = parentViewModel,
                navigateToLeftScreen = navController::navigateLeft,
                navigateToRightScreen = navController::navigateRight,
                openDrawer = openDrawer,
            )
        }

        unittoComposable(
            route = UNIT_FROM_ROUTE,
            arguments = listOf(
                navArgument(UNIT_FROM_ID_ARG) {
                    type = NavType.StringType
                },
                navArgument(UNIT_GROUP_ARG) {
                    type = NavType.EnumType(UnitGroup::class.java)
                },
            ),
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(graph)
            }

            val parentViewModel = hiltViewModel<ConverterViewModel>(parentEntry)

            UnitFromSelectorRoute(
                unitSelectorViewModel = hiltViewModel(),
                converterViewModel = parentViewModel,
                navigateUp = navController::navigateUp,
                navigateToUnitGroups = navigateToUnitGroups,
            )
        }

        unittoComposable(
            route = UNIT_TO_ROUTE,
            arguments = listOf(
                navArgument(UNIT_FROM_ID_ARG) {
                    type = NavType.StringType
                },
                navArgument(UNIT_TO_ID_ARG) {
                    type = NavType.StringType
                },
                navArgument(UNIT_GROUP_ARG) {
                    type = NavType.EnumType(UnitGroup::class.java)
                },
                navArgument(INPUT_ARG) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
            ),
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(graph)
            }

            val parentViewModel = hiltViewModel<ConverterViewModel>(parentEntry)

            UnitToSelectorRoute(
                unitSelectorViewModel = hiltViewModel(),
                converterViewModel = parentViewModel,
                navigateUp = navController::navigateUp,
                navigateToUnitGroups = navigateToUnitGroups,
            )
        }
    }
}

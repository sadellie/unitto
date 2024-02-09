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
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.feature.converter.ConverterRoute
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.converter.CurrencyRateUpdateState
import com.sadellie.unitto.feature.converter.UnitConverterUIState
import com.sadellie.unitto.feature.converter.UnitFromSelectorRoute
import com.sadellie.unitto.feature.converter.UnitToSelectorRoute

private val graph = DrawerItem.Converter.graph
private val start = DrawerItem.Converter.start

private const val UNIT_FROM = "unitFromSelector"
private const val UNIT_TO = "unitToSelector"
internal const val unitGroupArg = "unitGroupArg"
internal const val unitFromIdArg = "unitFromId"
internal const val unitToIdArg = "unitToIdArg"
internal const val inputArg = "inputArg"

private const val UNIT_FROM_ROUTE = "$UNIT_FROM/{$unitFromIdArg}/{$unitGroupArg}"
private const val UNIT_TO_ROUTE = "$UNIT_TO/{$unitFromIdArg}/{$unitToIdArg}/{$inputArg}"
private fun NavHostController.navigateLeft(
    unitFromId: String,
    unitGroup: UnitGroup,
) = navigate("$UNIT_FROM/$unitFromId/$unitGroup")

private fun NavHostController.navigateRight(
    unitFromId: String,
    unitToId: String,
    input: String?,
) = navigate("$UNIT_TO/$unitFromId/$unitToId/$input")

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
                // Navigation logic is here, but should actually be in ConverterScreen
                navigateToLeftScreen = { uiState: UnitConverterUIState ->
                    when (uiState) {
                        is UnitConverterUIState.Default -> navController
                            .navigateLeft(uiState.unitFrom.id, uiState.unitFrom.group)

                        is UnitConverterUIState.NumberBase -> navController
                            .navigateLeft(uiState.unitFrom.id, uiState.unitFrom.group)

                        else -> Unit
                    }
                },
                navigateToRightScreen = { uiState: UnitConverterUIState ->
                    when (uiState) {
                        is UnitConverterUIState.Default -> {
                            // Don't allow converting if still loading currencies
                            val convertingCurrencies = uiState.unitFrom.group == UnitGroup.CURRENCY
                            val currenciesReady =
                                uiState.currencyRateUpdateState is CurrencyRateUpdateState.Ready

                            val input: String? = if (convertingCurrencies and !currenciesReady) {
                                null
                            } else {
                                (uiState.calculation?.toPlainString() ?: uiState.input1.text)
                                    .ifEmpty { null }
                            }

                            navController.navigateRight(
                                uiState.unitFrom.id,
                                uiState.unitTo.id,
                                input
                            )
                        }

                        is UnitConverterUIState.NumberBase -> {
                            val input = uiState.input.text.ifEmpty { null }
                            navController.navigateRight(
                                uiState.unitFrom.id,
                                uiState.unitTo.id,
                                input
                            )
                        }

                        UnitConverterUIState.Loading -> Unit
                    }
                },
                navigateToSettings = navigateToSettings,
                navigateToMenu = openDrawer
            )
        }

        unittoComposable(
            route = UNIT_FROM_ROUTE,
            arguments = listOf(
                navArgument(unitFromIdArg) {
                    type = NavType.StringType
                },
                navArgument(unitGroupArg) {
                    type = NavType.EnumType(UnitGroup::class.java)
                },
            )
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(graph)
            }

            val parentViewModel = hiltViewModel<ConverterViewModel>(parentEntry)

            UnitFromSelectorRoute(
                unitSelectorViewModel = hiltViewModel(),
                converterViewModel = parentViewModel,
                navigateUp = navController::navigateUp,
                navigateToUnitGroups = navigateToUnitGroups
            )
        }

        unittoComposable(
            route = UNIT_TO_ROUTE,
            arguments = listOf(
                navArgument(unitFromIdArg) {
                    type = NavType.StringType
                },
                navArgument(unitToIdArg) {
                    type = NavType.StringType
                },
                navArgument(inputArg) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
            )
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(graph)
            }

            val parentViewModel = hiltViewModel<ConverterViewModel>(parentEntry)

            UnitToSelectorRoute(
                unitSelectorViewModel = hiltViewModel(),
                converterViewModel = parentViewModel,
                navigateUp = navController::navigateUp,
                navigateToUnitGroups = navigateToUnitGroups
            )
        }
    }
}

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

package com.sadellie.unitto.feature.calculator.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import com.sadellie.unitto.core.ui.model.DrawerItem
import com.sadellie.unitto.core.ui.unittoComposable
import com.sadellie.unitto.core.ui.unittoNavigation
import com.sadellie.unitto.feature.calculator.CalculatorRoute
import com.sadellie.unitto.feature.calculator.RPNCalculatorRoute

private val graph = DrawerItem.Calculator.graph
private val start = DrawerItem.Calculator.start

fun NavGraphBuilder.calculatorGraph(
    rpnMode: Boolean,
    openDrawer: () -> Unit,
    navigateToSettings: () -> Unit
) {
    unittoNavigation(
        startDestination = start,
        route = graph,
        deepLinks = listOf(
            navDeepLink { uriPattern = "app://com.sadellie.unitto/$graph" }
        )
    ) {
        unittoComposable(start) {
            if (rpnMode) {
                RPNCalculatorRoute(
                    openDrawer = openDrawer,
                    navigateToSettings = navigateToSettings
                )
            } else {
                CalculatorRoute(
                    navigateToMenu = openDrawer,
                    navigateToSettings = navigateToSettings
                )
            }
        }
    }
}

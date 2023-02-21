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

package com.sadellie.unitto.feature.tools.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sadellie.unitto.feature.tools.ToolsScreen

private const val toolsRoute = "tools_route"

fun NavController.navigateToTools() {
    navigate(toolsRoute)
}

fun NavGraphBuilder.toolsScreen(
    navigateUpAction: () -> Unit,
    navigateToConverter: () -> Unit,
    navigateToCalculator: () -> Unit,
    navigateToEpoch: () -> Unit
) {
    composable(toolsRoute) {
        ToolsScreen(
            navigateUpAction = navigateUpAction,
            navigateToConverter = navigateToConverter,
            navigateToCalculator = navigateToCalculator,
            navigateToEpoch = navigateToEpoch
        )
    }
}
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

package com.sadellie.unitto.feature.unitslist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.units.AllUnitsRepository
import com.sadellie.unitto.feature.unitslist.LeftSideScreen
import com.sadellie.unitto.feature.unitslist.RightSideScreen
import com.sadellie.unitto.feature.unitslist.SecondViewModel

const val leftSideRoute = "left_side_route"
const val rightSideRoute = "right_side_route"
private const val unitFromIdArg = "unitFromId"
private const val unitToIdArg = "unitToId"
private const val inputArg = "input"

fun NavController.navigateToLeftSide(unitFromId: String) {
    navigate("$leftSideRoute/$unitFromId")
}

fun NavController.navigateToRightSide(unitFromId: String, unitToId: String, input: String) {
    navigate("$rightSideRoute/$unitFromId/$unitToId/$input")
}

fun NavGraphBuilder.leftScreen(
    viewModel: SecondViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
    onSelect: (AbstractUnit) -> Unit
) {
    composable(
        route = "$leftSideRoute/{$unitFromIdArg}"
    ) {
        val unitFromId = it.arguments?.getString(unitFromIdArg) ?: return@composable
        viewModel.setSelectedChip(unitFromId)
        LeftSideScreen(
            viewModel = viewModel,
            currentUnitId = unitFromId,
            navigateUp = navigateUp,
            navigateToSettingsAction = navigateToUnitGroups,
            selectAction = onSelect
        )
    }
}

fun NavGraphBuilder.rightScreen(
    viewModel: SecondViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
    onSelect: (AbstractUnit) -> Unit

) {
    composable(
        route = "$rightSideRoute/{$unitFromIdArg}/{$unitToIdArg}/{$inputArg}"
    ) {
        val unitFromId = it.arguments?.getString(unitFromIdArg) ?: return@composable
        val unitToId = it.arguments?.getString(unitToIdArg) ?: return@composable
        val input = it.arguments?.getString(inputArg) ?: return@composable
        viewModel.setSelectedChip(unitFromId, false)

        RightSideScreen(
            viewModel = viewModel,
            currentUnit = unitToId,
            navigateUp = navigateUp,
            navigateToSettingsAction = navigateToUnitGroups,
            selectAction = onSelect,
            inputValue = input,
            unitFrom = AllUnitsRepository().getById(unitFromId)
        )
    }
}

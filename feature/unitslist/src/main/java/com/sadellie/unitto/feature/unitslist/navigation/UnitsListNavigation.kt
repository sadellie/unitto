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
import com.sadellie.unitto.feature.unitslist.UnitsListViewModel

private const val LEFT_SIDE_ROUTE = "LEFT_SIDE_ROUTE"
private const val RIGHT_SIDE_ROUTE = "RIGHT_SIDE_ROUTE"
private const val UNIT_FROM_ARG = "UNIT_FROM_ARG"
private const val UNIT_TO_ARG = "UNIT_TO_ARG"
private const val INPUT_ARG = "INPUT_ARG"

fun NavController.navigateToLeftSide(unitFromId: String) {
    navigate("$LEFT_SIDE_ROUTE/$unitFromId")
}

fun NavController.navigateToRightSide(unitFromId: String, unitToId: String, input: String?) {
    navigate("$RIGHT_SIDE_ROUTE/$unitFromId/$unitToId/$input")
}

fun NavGraphBuilder.leftScreen(
    viewModel: UnitsListViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
    onSelect: (AbstractUnit) -> Unit
) {
    composable(
        route = "$LEFT_SIDE_ROUTE/{$UNIT_FROM_ARG}"
    ) {
        LeftSideScreen(
            viewModel = viewModel,
            currentUnitId = it.arguments?.getString(UNIT_FROM_ARG),
            navigateUp = navigateUp,
            navigateToSettingsAction = navigateToUnitGroups,
            selectAction = onSelect
        )
    }
}

fun NavGraphBuilder.rightScreen(
    viewModel: UnitsListViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
    onSelect: (AbstractUnit) -> Unit

) {
    composable(
        route = "$RIGHT_SIDE_ROUTE/{$UNIT_FROM_ARG}/{$UNIT_TO_ARG}/{$INPUT_ARG}"
    ) {
        val unitFromId = it.arguments?.getString(UNIT_FROM_ARG) ?: return@composable
        val unitToId = it.arguments?.getString(UNIT_TO_ARG) ?: return@composable
        val input = it.arguments?.getString(INPUT_ARG)
        viewModel.setSelectedChip(unitFromId, true)

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

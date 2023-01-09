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
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.feature.unitslist.LeftSideScreen
import com.sadellie.unitto.feature.unitslist.RightSideScreen
import com.sadellie.unitto.feature.unitslist.SecondViewModel

const val leftSideRoute = "left_side_route"
const val rightSideRoute = "right_side_route"

fun NavController.navigateToLeftSide() {
    navigate(leftSideRoute)
}

fun NavController.navigateToRightSide() {
    navigate(rightSideRoute)
}

fun NavGraphBuilder.leftSideScreen(
    secondViewModel: SecondViewModel,
    unitFrom: AbstractUnit,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
    onSelectAction: (AbstractUnit) -> Unit,
) {
    composable(leftSideRoute) {
        secondViewModel.setSelectedChip(unitFrom.group, true)

        LeftSideScreen(
            viewModel = secondViewModel,
            currentUnit = unitFrom,
            navigateUp = navigateUp,
            navigateToSettingsAction = navigateToUnitGroups,
            selectAction = onSelectAction
        )
    }
}

fun NavGraphBuilder.rightSideScreen(
    secondViewModel: SecondViewModel,
    unitFrom: AbstractUnit,
    unitTo: AbstractUnit,
    inputValue: String,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
    onSelectAction: (AbstractUnit) -> Unit
) {
    composable(rightSideRoute) {
        // Initial group
        secondViewModel.setSelectedChip(unitFrom.group, false)

        RightSideScreen(
            viewModel = secondViewModel,
            currentUnit = unitTo,
            navigateUp = navigateUp,
            navigateToSettingsAction = navigateToUnitGroups,
            selectAction = onSelectAction,
            inputValue = inputValue,
            unitFrom = unitFrom,
        )
    }
}
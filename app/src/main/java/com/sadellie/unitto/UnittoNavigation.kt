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

package com.sadellie.unitto

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sadellie.unitto.feature.converter.MainViewModel
import com.sadellie.unitto.feature.converter.navigation.converterRoute
import com.sadellie.unitto.feature.converter.navigation.converterScreen
import com.sadellie.unitto.feature.settings.SettingsViewModel
import com.sadellie.unitto.feature.settings.navigation.navigateToSettings
import com.sadellie.unitto.feature.settings.navigation.navigateToUnitGroups
import com.sadellie.unitto.feature.settings.navigation.settingGraph
import com.sadellie.unitto.feature.unitslist.LeftSideScreen
import com.sadellie.unitto.feature.unitslist.RightSideScreen
import com.sadellie.unitto.feature.unitslist.SecondViewModel
import com.sadellie.unitto.feature.unitslist.navigation.leftSideRoute
import com.sadellie.unitto.feature.unitslist.navigation.navigateToLeftSide
import com.sadellie.unitto.feature.unitslist.navigation.navigateToRightSide
import com.sadellie.unitto.feature.unitslist.navigation.rightSideRoute
import io.github.sadellie.themmo.ThemmoController

@Composable
fun UnittoNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    secondViewModel: SecondViewModel,
    settingsViewModel: SettingsViewModel,
    themmoController: ThemmoController
) {
    NavHost(
        navController = navController,
        startDestination = converterRoute
    ) {
        converterScreen(
            navigateToLeftScreen = { navController.navigateToLeftSide() },
            navigateToRightScreen = { navController.navigateToRightSide() },
            navigateToSettings = { navController.navigateToSettings() },
            viewModel = mainViewModel
        )

        composable(leftSideRoute) {
            // Don't do this in your app
            val mainUiState = mainViewModel.uiStateFlow.collectAsState()
            val unitFrom = mainUiState.value.unitFrom ?: return@composable
            // Initial group
            secondViewModel.setSelectedChip(unitFrom.group, true)

            LeftSideScreen(
                viewModel = secondViewModel,
                currentUnit = unitFrom,
                navigateUp = { navController.navigateUp() },
                navigateToSettingsAction = { navController.navigateToUnitGroups() },
                selectAction = { mainViewModel.updateUnitFrom(it) }
            )
        }

        composable(rightSideRoute) {
            // Don't do this in your app
            val mainUiState = mainViewModel.uiStateFlow.collectAsState()
            val unitFrom = mainUiState.value.unitFrom ?: return@composable
            val unitTo = mainUiState.value.unitTo ?: return@composable

            // Initial group
            secondViewModel.setSelectedChip(unitFrom.group, false)

            RightSideScreen(
                viewModel = secondViewModel,
                currentUnit = unitTo,
                navigateUp = { navController.navigateUp() },
                navigateToSettingsAction = { navController.navigateToUnitGroups() },
                selectAction = { mainViewModel.updateUnitTo(it) },
                inputValue = mainViewModel.getInputValue(),
                unitFrom = unitFrom
            )
        }

        settingGraph(
            settingsViewModel = settingsViewModel,
            themmoController = themmoController,
            navController = navController
        )
    }
}

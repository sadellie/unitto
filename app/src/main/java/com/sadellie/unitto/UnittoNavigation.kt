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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sadellie.unitto.feature.converter.MainViewModel
import com.sadellie.unitto.feature.converter.navigation.converterRoute
import com.sadellie.unitto.feature.converter.navigation.converterScreen
import com.sadellie.unitto.feature.settings.SettingsViewModel
import com.sadellie.unitto.feature.settings.navigation.navigateToSettings
import com.sadellie.unitto.feature.settings.navigation.navigateToUnitGroups
import com.sadellie.unitto.feature.settings.navigation.settingGraph
import com.sadellie.unitto.feature.unitslist.SecondViewModel
import com.sadellie.unitto.feature.unitslist.navigation.leftSideScreen
import com.sadellie.unitto.feature.unitslist.navigation.navigateToLeftSide
import com.sadellie.unitto.feature.unitslist.navigation.navigateToRightSide
import com.sadellie.unitto.feature.unitslist.navigation.rightSideScreen
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

        leftSideScreen(
            secondViewModel = secondViewModel,
            unitFrom = mainViewModel.uiStateFlow.value.unitFrom ?: return@NavHost,
            navigateUp = { navController.navigateUp() },
            navigateToUnitGroups = { navController.navigateToUnitGroups() },
            onSelectAction = { mainViewModel.updateUnitFrom(it) }
        )

        rightSideScreen(
            secondViewModel = secondViewModel,
            unitFrom = mainViewModel.uiStateFlow.value.unitFrom ?: return@NavHost,
            unitTo = mainViewModel.uiStateFlow.value.unitTo ?: return@NavHost,
            inputValue = mainViewModel.getInputValue(),
            navigateUp = { navController.navigateUp() },
            navigateToUnitGroups = { navController.navigateToUnitGroups() },
            onSelectAction = { mainViewModel.updateUnitTo(it) }
        )

        settingGraph(
            settingsViewModel = settingsViewModel,
            themmoController = themmoController,
            navController = navController
        )
    }
}

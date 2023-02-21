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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.sadellie.unitto.feature.calculator.navigation.calculatorScreen
import com.sadellie.unitto.feature.calculator.navigation.navigateToCalculator
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.converter.navigation.converterRoute
import com.sadellie.unitto.feature.converter.navigation.converterScreen
import com.sadellie.unitto.feature.converter.navigation.navigateToConverter
import com.sadellie.unitto.feature.epoch.navigation.epochScreen
import com.sadellie.unitto.feature.epoch.navigation.navigateToEpoch
import com.sadellie.unitto.feature.settings.SettingsViewModel
import com.sadellie.unitto.feature.settings.navigation.navigateToSettings
import com.sadellie.unitto.feature.settings.navigation.navigateToUnitGroups
import com.sadellie.unitto.feature.settings.navigation.settingGraph
import com.sadellie.unitto.feature.tools.navigation.navigateToTools
import com.sadellie.unitto.feature.tools.navigation.toolsScreen
import com.sadellie.unitto.feature.unitslist.SecondViewModel
import com.sadellie.unitto.feature.unitslist.navigation.leftScreen
import com.sadellie.unitto.feature.unitslist.navigation.navigateToLeftSide
import com.sadellie.unitto.feature.unitslist.navigation.navigateToRightSide
import com.sadellie.unitto.feature.unitslist.navigation.rightScreen
import io.github.sadellie.themmo.ThemmoController

@Composable
fun UnittoNavigation(
    navController: NavHostController,
    converterViewModel: ConverterViewModel,
    secondViewModel: SecondViewModel,
    settingsViewModel: SettingsViewModel,
    themmoController: ThemmoController
) {
    NavHost(
        navController = navController,
        startDestination = converterRoute
    ) {
        converterScreen(
            navigateToLeftScreen = navController::navigateToLeftSide,
            navigateToRightScreen = navController::navigateToRightSide,
            navigateToSettings = navController::navigateToSettings,
            navigateToMenu = navController::navigateToTools,
            viewModel = converterViewModel
        )

        leftScreen(
            viewModel = secondViewModel,
            navigateUp = navController::navigateUp,
            navigateToUnitGroups = navController::navigateToUnitGroups,
            onSelect = converterViewModel::updateUnitFrom
        )

        rightScreen(
            viewModel = secondViewModel,
            navigateUp = navController::navigateUp,
            navigateToUnitGroups = navController::navigateToUnitGroups,
            onSelect = converterViewModel::updateUnitTo
        )

        settingGraph(
            settingsViewModel = settingsViewModel,
            themmoController = themmoController,
            navController = navController
        )

        toolsScreen(
            navigateUpAction = navController::navigateUp,
            navigateToConverter = { navController.navigateToConverter(navController.clearStack) },
            navigateToCalculator = { navController.navigateToCalculator(navController.clearStack) },
            navigateToEpoch = { navController.navigateToEpoch(navController.clearStack) }
        )

        calculatorScreen(
            navigateToMenu = navController::navigateToTools,
            navigateToSettings = navController::navigateToSettings
        )

        epochScreen(navigateToMenu = navController::navigateToTools)
    }
}

val NavController.clearStack: NavOptions
    get() = NavOptions.Builder().setPopUpTo(this.graph.id, false).build()
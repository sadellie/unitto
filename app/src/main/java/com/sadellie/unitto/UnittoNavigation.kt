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

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sadellie.unitto.feature.calculator.navigation.calculatorGraph
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.converter.navigation.converterGraph
import com.sadellie.unitto.feature.datecalculator.navigation.dateCalculatorGraph
import com.sadellie.unitto.feature.settings.navigation.navigateToSettings
import com.sadellie.unitto.feature.settings.navigation.navigateToUnitGroups
import com.sadellie.unitto.feature.settings.navigation.settingGraph
import com.sadellie.unitto.feature.unitslist.UnitsListViewModel
import com.sadellie.unitto.feature.unitslist.navigation.leftScreen
import com.sadellie.unitto.feature.unitslist.navigation.navigateToLeftSide
import com.sadellie.unitto.feature.unitslist.navigation.navigateToRightSide
import com.sadellie.unitto.feature.unitslist.navigation.rightScreen
import com.sadellie.unitto.timezone.navigation.timeZoneGraph
import io.github.sadellie.themmo.ThemmoController

@Composable
internal fun UnittoNavigation(
    navController: NavHostController,
    themmoController: ThemmoController,
    startDestination: String,
    openDrawer: () -> Unit
) {
    val converterViewModel: ConverterViewModel = hiltViewModel()
    val unitsListViewModel: UnitsListViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        converterGraph(
            navigateToLeftScreen = navController::navigateToLeftSide,
            navigateToRightScreen = navController::navigateToRightSide,
            navigateToSettings = navController::navigateToSettings,
            navigateToMenu = openDrawer,
            viewModel = converterViewModel
        )

        leftScreen(
            viewModel = unitsListViewModel,
            navigateUp = navController::navigateUp,
            navigateToUnitGroups = navController::navigateToUnitGroups,
            onSelect = converterViewModel::updateUnitFrom
        )

        rightScreen(
            viewModel = unitsListViewModel,
            navigateUp = navController::navigateUp,
            navigateToUnitGroups = navController::navigateToUnitGroups,
            onSelect = converterViewModel::updateUnitTo
        )

        settingGraph(
            themmoController = themmoController,
            navController = navController,
            menuButtonClick = openDrawer
        )

        calculatorGraph(
            navigateToMenu = openDrawer,
            navigateToSettings = navController::navigateToSettings
        )

        dateCalculatorGraph(
            navigateToMenu = openDrawer,
            navigateToSettings = navController::navigateToSettings
        )

        timeZoneGraph(
            navigateToMenu = openDrawer,
            navigateToSettings = navController::navigateToSettings,
            navController = navController,
        )
    }
}

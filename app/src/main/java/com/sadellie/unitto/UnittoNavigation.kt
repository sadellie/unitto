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

package com.sadellie.unitto

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sadellie.unitto.feature.bodymass.navigation.bodyMassGraph
import com.sadellie.unitto.feature.calculator.navigation.calculatorGraph
import com.sadellie.unitto.feature.converter.navigation.converterGraph
import com.sadellie.unitto.feature.datecalculator.navigation.dateCalculatorGraph
import com.sadellie.unitto.feature.settings.navigation.navigateToUnitGroups
import com.sadellie.unitto.feature.settings.navigation.settingGraph
import com.sadellie.unitto.feature.timezone.navigation.timeZoneGraph
import io.github.sadellie.themmo.ThemmoController

@Composable
internal fun UnittoNavigation(
    navController: NavHostController,
    themmoController: ThemmoController,
    startDestination: String,
    openDrawer: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
    ) {
        calculatorGraph(
            openDrawer = openDrawer,
        )

        converterGraph(
            openDrawer = openDrawer,
            navController = navController,
            navigateToUnitGroups = navController::navigateToUnitGroups,
        )

        dateCalculatorGraph(
            openDrawer = openDrawer,
        )

        timeZoneGraph(
            openDrawer = openDrawer,
            navController = navController,
        )

        bodyMassGraph(
            openDrawer = openDrawer,
        )

        settingGraph(
            openDrawer = openDrawer,
            navController = navController,
            themmoController = themmoController,
        )
    }
}

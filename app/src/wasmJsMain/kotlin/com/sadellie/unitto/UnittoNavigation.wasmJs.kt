/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.feature.calculator.navigation.calculatorGraph
import io.github.sadellie.themmo.ThemmoController

@Composable
internal fun UnittoNavigation(
  navController: NavHostController,
  themmoController: ThemmoController,
  startDestination: Route,
  openDrawer: () -> Unit,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination::class,
    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
    enterTransition = { fadeIn() },
    exitTransition = { fadeOut() },
  ) {
    calculatorGraph(openDrawer = openDrawer)
  }
}

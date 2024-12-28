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

package com.sadellie.unitto.feature.datecalculator.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import com.sadellie.unitto.core.designsystem.unittoComposable
import com.sadellie.unitto.core.designsystem.unittoNavigation
import com.sadellie.unitto.core.navigation.DateCalculatorGraphRoute
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.deepLink
import com.sadellie.unitto.feature.datecalculator.DateCalculatorRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.dateCalculatorGraph(openDrawer: () -> Unit) {
  unittoNavigation<DateCalculatorGraphRoute>(
    startDestination = DateCalculatorStartRoute::class,
    deepLinks = listOf(navDeepLink { uriPattern = deepLink(DateCalculatorGraphRoute) }),
  ) {
    unittoComposable<DateCalculatorStartRoute> { DateCalculatorRoute(openDrawer = openDrawer) }
  }
}

@Serializable
private data object DateCalculatorStartRoute : Route {
  override val id = "date_calculator_start"
}
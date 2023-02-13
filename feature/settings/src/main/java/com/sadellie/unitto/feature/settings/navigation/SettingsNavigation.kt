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

package com.sadellie.unitto.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sadellie.unitto.feature.settings.AboutScreen
import com.sadellie.unitto.feature.settings.SettingsScreen
import com.sadellie.unitto.feature.settings.SettingsViewModel
import com.sadellie.unitto.feature.settings.ThemesScreen
import com.sadellie.unitto.feature.settings.ThirdPartyLicensesScreen
import com.sadellie.unitto.feature.settings.UnitGroupsScreen
import io.github.sadellie.themmo.ThemmoController

const val settingsGraph = "settings_graph"
private const val settingsRoute = "settings_route"
internal const val themesRoute = "themes_route"
internal const val unitsGroupRoute = "units_group_route"
internal const val thirdPartyRoute = "third_party_route"
internal const val aboutRoute = "about_route"

fun NavController.navigateToSettings() {
    navigate(settingsRoute)
}

fun NavController.navigateToUnitGroups() {
    navigate(unitsGroupRoute)
}

fun NavGraphBuilder.settingGraph(
    settingsViewModel: SettingsViewModel,
    themmoController: ThemmoController,
    navController: NavHostController
) {
    navigation(settingsRoute, settingsGraph) {
        composable(settingsRoute) {
            SettingsScreen(
                viewModel = settingsViewModel,
                navigateUpAction = { navController.navigateUp() }
            ) { route -> navController.navigate(route) }
        }

        composable(themesRoute) {
            ThemesScreen(
                navigateUpAction = { navController.navigateUp() },
                themmoController = themmoController,
                viewModel = settingsViewModel
            )
        }

        composable(thirdPartyRoute) {
            ThirdPartyLicensesScreen(
                navigateUpAction = { navController.navigateUp() }
            )
        }

        composable(aboutRoute) {
            AboutScreen(
                navigateUpAction = { navController.navigateUp() },
                navigateToThirdParty = { navController.navigate(thirdPartyRoute) },
                viewModel = settingsViewModel
            )
        }

        composable(unitsGroupRoute) {
            UnitGroupsScreen(
                viewModel = settingsViewModel,
                navigateUpAction = { navController.navigateUp() }
            )
        }
    }
}

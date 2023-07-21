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
import com.sadellie.unitto.core.base.TopLevelDestinations
import com.sadellie.unitto.feature.settings.AboutScreen
import com.sadellie.unitto.feature.settings.SettingsScreen
import com.sadellie.unitto.feature.settings.ThirdPartyLicensesScreen
import com.sadellie.unitto.feature.settings.formatting.FormattingRoute
import com.sadellie.unitto.feature.settings.themes.ThemesRoute
import com.sadellie.unitto.feature.settings.unitgroups.UnitGroupsScreen
import io.github.sadellie.themmo.ThemmoController

private val settingsGraph: String by lazy { TopLevelDestinations.Settings.route }
private const val settingsRoute = "settings_route"
internal const val themesRoute = "themes_route"
internal const val unitsGroupRoute = "units_group_route"
internal const val thirdPartyRoute = "third_party_route"
internal const val aboutRoute = "about_route"
internal const val formattingRoute = "formatting_route"

fun NavController.navigateToSettings() {
    navigate(settingsRoute)
}

fun NavController.navigateToUnitGroups() {
    navigate(unitsGroupRoute)
}

fun NavGraphBuilder.settingGraph(
    themmoController: ThemmoController,
    navController: NavHostController,
    menuButtonClick: () -> Unit
) {
    navigation(settingsRoute, settingsGraph) {
        composable(settingsRoute) {
            SettingsScreen(
                menuButtonClick = menuButtonClick,
                navControllerAction = navController::navigate
            )
        }

        composable(themesRoute) {
            ThemesRoute(
                navigateUpAction = navController::navigateUp,
                themmoController = themmoController,
            )
        }

        composable(thirdPartyRoute) {
            ThirdPartyLicensesScreen(
                navigateUpAction = navController::navigateUp,
            )
        }

        composable(aboutRoute) {
            AboutScreen(
                navigateUpAction = navController::navigateUp,
                navigateToThirdParty = { navController.navigate(thirdPartyRoute) },
            )
        }

        composable(unitsGroupRoute) {
            UnitGroupsScreen(
                navigateUpAction = navController::navigateUp,
            )
        }

        composable(formattingRoute) {
            FormattingRoute(
                navigateUpAction = navController::navigateUp
            )
        }
    }
}

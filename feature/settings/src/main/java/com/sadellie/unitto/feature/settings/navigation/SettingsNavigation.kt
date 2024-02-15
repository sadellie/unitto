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

package com.sadellie.unitto.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navDeepLink
import com.sadellie.unitto.core.ui.model.DrawerItem
import com.sadellie.unitto.core.ui.unittoComposable
import com.sadellie.unitto.core.ui.unittoNavigation
import com.sadellie.unitto.core.ui.unittoStackedComposable
import com.sadellie.unitto.feature.settings.SettingsRoute
import com.sadellie.unitto.feature.settings.about.AboutRoute
import com.sadellie.unitto.feature.settings.bouncingemoji.BouncingEmojiRoute
import com.sadellie.unitto.feature.settings.calculator.CalculatorSettingsRoute
import com.sadellie.unitto.feature.settings.converter.ConverterSettingsRoute
import com.sadellie.unitto.feature.settings.display.DisplayRoute
import com.sadellie.unitto.feature.settings.formatting.FormattingRoute
import com.sadellie.unitto.feature.settings.language.LanguageRoute
import com.sadellie.unitto.feature.settings.startingscreen.StartingScreenRoute
import com.sadellie.unitto.feature.settings.thirdparty.ThirdPartyLicensesScreen
import com.sadellie.unitto.feature.settings.unitgroups.UnitGroupsRoute
import io.github.sadellie.themmo.ThemmoController

private val graph = DrawerItem.Settings.graph
private val start = DrawerItem.Settings.start
internal const val DISPLAY_ROUTE = "display_route"
internal const val LANGUAGE_ROUTE = "language_route"
internal const val STARTING_SCREEN_ROUTE = "starting_screen_route"
internal const val UNITS_GROUP_ROUTE = "units_group_route"
internal const val THIRD_PARTY_ROUTE = "third_party_route"
internal const val ABOUT_ROUTE = "about_route"
internal const val FORMATTING_ROUTE = "formatting_route"
internal const val CALCULATOR_SETTINGS_ROUTE = "calculator_settings_route"
internal const val CONVERTER_SETTINGS_ROUTE = "converter_settings_route"
internal const val BOUNCING_EMOJI_ROUTE = "bouncing_emoji_route"

fun NavController.navigateToUnitGroups() {
    navigate(UNITS_GROUP_ROUTE)
}

fun NavGraphBuilder.settingGraph(
    openDrawer: () -> Unit,
    navController: NavHostController,
    themmoController: ThemmoController,
) {
    unittoNavigation(
        startDestination = start,
        route = graph,
        deepLinks = listOf(
            navDeepLink { uriPattern = "app://com.sadellie.unitto/$graph" },
        ),
    ) {
        unittoComposable(start) {
            SettingsRoute(
                openDrawer = openDrawer,
                navControllerAction = navController::navigate,
            )
        }

        unittoStackedComposable(DISPLAY_ROUTE) {
            DisplayRoute(
                navigateUp = navController::navigateUp,
                themmoController = themmoController,
                navigateToLanguages = { navController.navigate(LANGUAGE_ROUTE) },
            )
        }

        unittoStackedComposable(LANGUAGE_ROUTE) {
            LanguageRoute(
                navigateUp = navController::navigateUp,
            )
        }

        unittoStackedComposable(STARTING_SCREEN_ROUTE) {
            StartingScreenRoute(
                navigateUp = navController::navigateUp,
            )
        }

        unittoStackedComposable(FORMATTING_ROUTE) {
            FormattingRoute(
                navigateUpAction = navController::navigateUp,
            )
        }

        unittoStackedComposable(CALCULATOR_SETTINGS_ROUTE) {
            CalculatorSettingsRoute(
                navigateUpAction = navController::navigateUp,
            )
        }

        unittoStackedComposable(CONVERTER_SETTINGS_ROUTE) {
            ConverterSettingsRoute(
                navigateUpAction = navController::navigateUp,
                navigateToUnitsGroup = { navController.navigate(UNITS_GROUP_ROUTE) },
            )
        }

        unittoStackedComposable(UNITS_GROUP_ROUTE) {
            UnitGroupsRoute(
                navigateUpAction = navController::navigateUp,
            )
        }

        unittoStackedComposable(ABOUT_ROUTE) {
            AboutRoute(
                navigateUpAction = navController::navigateUp,
                navigateToThirdParty = { navController.navigate(THIRD_PARTY_ROUTE) },
                navigateToEasterEgg = { navController.navigate(BOUNCING_EMOJI_ROUTE) },
            )
        }

        unittoStackedComposable(THIRD_PARTY_ROUTE) {
            ThirdPartyLicensesScreen(
                navigateUpAction = navController::navigateUp,
            )
        }

        unittoStackedComposable(BOUNCING_EMOJI_ROUTE) {
            BouncingEmojiRoute(
                navigateUpAction = navController::navigateUp,
            )
        }
    }
}

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
import com.sadellie.unitto.core.designsystem.unittoComposable
import com.sadellie.unitto.core.designsystem.unittoNavigation
import com.sadellie.unitto.core.designsystem.unittoStackedComposable
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.SettingsGraphRoute
import com.sadellie.unitto.core.navigation.deepLink
import com.sadellie.unitto.feature.settings.SettingsRoute
import com.sadellie.unitto.feature.settings.about.AboutRoute
import com.sadellie.unitto.feature.settings.backup.BackupRoute
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
import kotlinx.serialization.Serializable

fun NavController.navigateToUnitGroups() = navigate(UnitGroupRoute)

fun NavGraphBuilder.settingGraph(
  openDrawer: () -> Unit,
  navController: NavHostController,
  themmoController: ThemmoController,
) {
  unittoNavigation<SettingsGraphRoute>(
    startDestination = SettingsStartRoute::class,
    deepLinks = listOf(navDeepLink { uriPattern = deepLink(SettingsGraphRoute) }),
  ) {
    unittoComposable<SettingsStartRoute> {
      SettingsRoute(openDrawer = openDrawer, navControllerAction = navController::navigate)
    }

    unittoStackedComposable<DisplayRoute> {
      DisplayRoute(
        navigateUp = navController::navigateUp,
        themmoController = themmoController,
        navigateToLanguages = { navController.navigate(LanguageRoute) },
      )
    }

    unittoStackedComposable<LanguageRoute> { LanguageRoute(navigateUp = navController::navigateUp) }

    unittoStackedComposable<StartingScreenRoute> {
      StartingScreenRoute(navigateUp = navController::navigateUp)
    }

    unittoStackedComposable<FormattingRoute> {
      FormattingRoute(navigateUpAction = navController::navigateUp)
    }

    unittoStackedComposable<CalculatorSettingsRoute> {
      CalculatorSettingsRoute(navigateUpAction = navController::navigateUp)
    }

    unittoStackedComposable<ConverterSettingsRoute> {
      ConverterSettingsRoute(
        navigateUpAction = navController::navigateUp,
        navigateToUnitsGroup = { navController.navigate(UnitGroupRoute) },
      )
    }

    unittoStackedComposable<UnitGroupRoute> {
      UnitGroupsRoute(navigateUpAction = navController::navigateUp)
    }

    unittoStackedComposable<BackupRoute> {
      BackupRoute(navigateUpAction = navController::navigateUp)
    }

    unittoStackedComposable<AboutRoute> {
      AboutRoute(
        navigateUpAction = navController::navigateUp,
        navigateToThirdParty = { navController.navigate(ThirdPartyRoute) },
        navigateToEasterEgg = { navController.navigate(EasterEggRoute) },
      )
    }

    unittoStackedComposable<ThirdPartyRoute> {
      ThirdPartyLicensesScreen(navigateUpAction = navController::navigateUp)
    }

    unittoStackedComposable<EasterEggRoute> {
      BouncingEmojiRoute(navigateUpAction = navController::navigateUp)
    }
  }
}

@Serializable
internal data object SettingsStartRoute : Route {
  override val id = "settings_start"
}

@Serializable
internal data object DisplayRoute : Route {
  override val id = "display_route"
}

@Serializable
internal data object LanguageRoute : Route {
  override val id = "language_route"
}

@Serializable
internal data object StartingScreenRoute : Route {
  override val id = "starting_screen_route"
}

@Serializable
internal data object FormattingRoute : Route {
  override val id = "formatting_route"
}

@Serializable
internal data object CalculatorSettingsRoute : Route {
  override val id = "calculator_settings_route"
}

@Serializable
internal data object ConverterSettingsRoute : Route {
  override val id = "converter_settings_route"
}

@Serializable
internal data object UnitGroupRoute : Route {
  override val id = "unit_group_route"
}

@Serializable
internal data object BackupRoute : Route {
  override val id = "backup_route"
}

@Serializable
internal data object AboutRoute : Route {
  override val id = "about_route"
}

@Serializable
internal data object ThirdPartyRoute : Route {
  override val id = "third_party_route"
}

@Serializable
internal data object EasterEggRoute : Route {
  override val id = "easter_egg_route"
}

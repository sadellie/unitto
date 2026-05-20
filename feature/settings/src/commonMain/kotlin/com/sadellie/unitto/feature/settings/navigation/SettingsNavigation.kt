/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.sadellie.unitto.core.designsystem.stackedTransition
import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.SettingsStartRoute
import com.sadellie.unitto.feature.settings.SettingsRoute
import com.sadellie.unitto.feature.settings.about.AboutRoute
import com.sadellie.unitto.feature.settings.advanced.AdvancedSettingsRoute
import com.sadellie.unitto.feature.settings.calculator.CalculatorSettingsRoute
import com.sadellie.unitto.feature.settings.converter.ConverterSettingsRoute
import com.sadellie.unitto.feature.settings.display.DisplayRoute
import com.sadellie.unitto.feature.settings.formatting.FormattingRoute
import com.sadellie.unitto.feature.settings.startingscreen.StartingScreenRoute
import com.sadellie.unitto.feature.settings.thirdparty.ThirdPartyLicensesScreen
import com.sadellie.unitto.feature.settings.unitgroups.UnitGroupsRoute
import io.github.sadellie.themmo.ThemmoController
import kotlinx.serialization.Serializable

fun NavBackStack<NavKey>.navigateToUnitGroups() = add(UnitGroupRoute)

val LocalThemmoController =
  staticCompositionLocalOf<ThemmoController> { error("LocalThemmoController not provided") }

fun EntryProviderScope<NavKey>.settingNavigation() {
  entry<SettingsStartRoute> {
    val navigator = LocalNavigator.current
    SettingsRoute(openDrawer = navigator::openDrawer, navControllerAction = navigator::goTo)
  }
  entry<DisplayRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    DisplayRoute(
      navigateUp = navigator::goBack,
      themmoController = LocalThemmoController.current,
      navigateToLanguages = { navigator.goTo(LanguageRoute) },
    )
  }
  languageNavigation()
  entry<StartingScreenRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    StartingScreenRoute(navigateUp = navigator::goBack)
  }
  entry<FormattingRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    FormattingRoute(navigateUpAction = navigator::goBack)
  }
  entry<CalculatorSettingsRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    CalculatorSettingsRoute(navigateUpAction = navigator::goBack)
  }
  entry<ConverterSettingsRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    ConverterSettingsRoute(
      navigateUpAction = navigator::goBack,
      navigateToUnitsGroup = { navigator.goTo(UnitGroupRoute) },
    )
  }
  entry<UnitGroupRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    UnitGroupsRoute(navigateUpAction = navigator::goBack)
  }
  backupNavigation()
  entry<AboutRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    AboutRoute(
      navigateUpAction = navigator::goBack,
      navigateToThirdParty = { navigator.goTo(ThirdPartyRoute) },
      navigateToAdvanced = { navigator.goTo(AdvancedSettingsRoute) },
    )
  }
  entry<ThirdPartyRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    ThirdPartyLicensesScreen(navigateUpAction = navigator::goBack)
  }
  entry<AdvancedSettingsRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    AdvancedSettingsRoute(navigateUpAction = navigator::goBack)
  }
}

internal expect fun EntryProviderScope<NavKey>.backupNavigation()

internal expect fun EntryProviderScope<NavKey>.languageNavigation()

@Serializable
internal data object DisplayRoute : Route {
  override val routeId = "display_route"
}

@Serializable
internal data object LanguageRoute : Route {
  override val routeId = "language_route"
}

@Serializable
internal data object StartingScreenRoute : Route {
  override val routeId = "starting_screen_route"
}

@Serializable
internal data object FormattingRoute : Route {
  override val routeId = "formatting_route"
}

@Serializable
internal data object CalculatorSettingsRoute : Route {
  override val routeId = "calculator_settings_route"
}

@Serializable
internal data object ConverterSettingsRoute : Route {
  override val routeId = "converter_settings_route"
}

@Serializable
internal data object UnitGroupRoute : Route {
  override val routeId = "unit_group_route"
}

@Serializable
internal data object BackupRoute : Route {
  override val routeId = "backup_route"
}

@Serializable
internal data object AboutRoute : Route {
  override val routeId = "about_route"
}

@Serializable
internal data object ThirdPartyRoute : Route {
  override val routeId = "third_party_route"
}

@Serializable
internal data object AdvancedSettingsRoute : Route {
  override val routeId = "advanced_settings_route"
}

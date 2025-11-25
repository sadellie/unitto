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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.sadellie.unitto.core.designsystem.stackedTransition
import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.core.navigation.SettingsStartRoute
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
import org.koin.compose.navigation3.EntryProviderInstaller
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.KoinDslMarker
import org.koin.core.module.Module
import org.koin.core.module._singleInstanceFactory
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.navigation3.navigation

fun NavBackStack<NavKey>.navigateToUnitGroups() = add(UnitGroupRoute)

val LocalThemmoController =
  staticCompositionLocalOf<ThemmoController> { error("LocalThemmoController not provided") }

@OptIn(KoinExperimentalAPI::class)
fun Module.settingNavigation() {
  navigation<SettingsStartRoute> {
    val navigator = LocalNavigator.current
    SettingsRoute(openDrawer = navigator::openDrawer, navControllerAction = navigator::goTo)
  }
  navigation<DisplayRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    DisplayRoute(
      navigateUp = navigator::goBack,
      themmoController = LocalThemmoController.current,
      navigateToLanguages = { navigator.goTo(LanguageRoute) },
    )
  }
  navigation<LanguageRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    LanguageRoute(navigateUp = navigator::goBack)
  }
  navigation<StartingScreenRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    StartingScreenRoute(navigateUp = navigator::goBack)
  }
  navigation<FormattingRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    FormattingRoute(navigateUpAction = navigator::goBack)
  }
  navigation<CalculatorSettingsRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    CalculatorSettingsRoute(navigateUpAction = navigator::goBack)
  }
  navigation<ConverterSettingsRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    ConverterSettingsRoute(
      navigateUpAction = navigator::goBack,
      navigateToUnitsGroup = { navigator.goTo(UnitGroupRoute) },
    )
  }
  navigation<UnitGroupRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    UnitGroupsRoute(navigateUpAction = navigator::goBack)
  }
  navigation<BackupRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    BackupRoute(navigateUpAction = navigator::goBack)
  }
  navigation<AboutRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    AboutRoute(
      navigateUpAction = navigator::goBack,
      navigateToThirdParty = { navigator.goTo(ThirdPartyRoute) },
      navigateToEasterEgg = { navigator.goTo(EasterEggRoute) },
    )
  }
  navigation<ThirdPartyRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    ThirdPartyLicensesScreen(navigateUpAction = navigator::goBack)
  }
  navigation<EasterEggRoute>(metadata = NavDisplay.stackedTransition()) {
    val navigator = LocalNavigator.current
    BouncingEmojiRoute(navigateUpAction = navigator::goBack)
  }
}

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
internal data object EasterEggRoute : Route {
  override val routeId = "easter_egg_route"
}

// remove after https://github.com/InsertKoinIO/koin/pull/2313
@KoinExperimentalAPI
@KoinDslMarker
@OptIn(KoinInternalApi::class)
private inline fun <reified T : Any> Module.navigation(
  metadata: Map<String, Any> = emptyMap(),
  noinline definition: @Composable Scope.(T) -> Unit,
): KoinDefinition<EntryProviderInstaller> {
  val def =
    _singleInstanceFactory<EntryProviderInstaller>(
      named<T>(),
      {
        val scope = this
        { entry<T>(content = { t -> definition(scope, t) }, metadata = metadata) }
      },
    )
  indexPrimaryType(def)
  return KoinDefinition(this, def)
}

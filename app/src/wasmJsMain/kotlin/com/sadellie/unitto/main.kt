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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.sadellie.unitto.core.data.dataModule
import com.sadellie.unitto.core.database.unittoDatabaseModule
import com.sadellie.unitto.core.datastore.AppPreferences
import com.sadellie.unitto.core.datastore.dataStoreModule
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.designsystem.theme.numberTypographyUnitto
import com.sadellie.unitto.core.navigation.CalculatorStartRoute
import com.sadellie.unitto.core.navigation.ConverterStartRoute
import com.sadellie.unitto.core.remote.currencyApiModule
import com.sadellie.unitto.feature.bodymass.bodyMassModule
import com.sadellie.unitto.feature.calculator.calculatorModule
import com.sadellie.unitto.feature.converter.converterModule
import com.sadellie.unitto.feature.converter.navigation.UnitFromRoute
import com.sadellie.unitto.feature.converter.navigation.UnitToRoute
import com.sadellie.unitto.feature.settings.settingsModule
import io.github.sadellie.themmo.Themmo
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.browser.document
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.context.startKoin

@OptIn(
  ExperimentalComposeUiApi::class,
  ExperimentalMaterial3WindowSizeClassApi::class,
  ExperimentalMaterial3ExpressiveApi::class,
)
fun main() {
  initKoin()
  ComposeViewport(document.body!!) {
    CompositionLocalProvider(
      LocalWindowSize provides rememberWindowSizeClass(),
      LocalNumberTypography provides numberTypographyUnitto(),
    ) {
      App()
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun App() {
  val appPreferences = remember {
    AppPreferences(
      themingMode = ThemingMode.FORCE_DARK,
      enableDynamicTheme = false,
      enableAmoledTheme = false,
      customColor = 16L,
      monetMode = MonetMode.TonalSpot,
      startingScreen = CalculatorStartRoute,
      enableToolsExperiment = false,
      enableVibrations = false,
      enableKeepScreenOn = false,
    )
  }
  val themmoController = rememberUnittoThemmoController(appPreferences)
  Themmo(themmoController = themmoController) {
    Column(Modifier.fillMaxSize()) {
      ExperimentalBar(modifier = Modifier.fillMaxWidth())
      HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.outlineVariant,
      )

      val backStack = rememberNavBackStack(navBackStackConfig, appPreferences.startingScreen)
      MainAppContent(
        backStack = backStack,
        onDrawerItemClick = {},
        themmoController = themmoController,
      )
    }
  }
}

private val navBackStackConfig = SavedStateConfiguration {
  this.serializersModule = SerializersModule {
    polymorphic(NavKey::class) {
      this.subclass(CalculatorStartRoute::class, CalculatorStartRoute.serializer())
      this.subclass(ConverterStartRoute::class, ConverterStartRoute.serializer())
      this.subclass(UnitFromRoute::class, UnitFromRoute.serializer())
      this.subclass(UnitToRoute::class, UnitToRoute.serializer())
    }
  }
}

private fun initKoin() {
  startKoin {
    modules(
      unittoDatabaseModule,
      dataStoreModule,
      dataModule,
      currencyApiModule,
      calculatorModule,
      converterModule,
      bodyMassModule,
      settingsModule,
    )
  }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun rememberWindowSizeClass(): WindowSizeClass {
  val containerSize = LocalWindowInfo.current.containerSize
  val density = LocalDensity.current
  return remember(containerSize, density) {
    with(density) {
      WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.toDp(), containerSize.height.toDp())
      )
    }
  }
}

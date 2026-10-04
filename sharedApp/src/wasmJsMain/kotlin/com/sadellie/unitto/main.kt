/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025-2026 Elshan Agaev
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
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.ComposeViewport
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.sadellie.unitto.core.data.calculator.CalculatorDataBindings
import com.sadellie.unitto.core.data.converter.ConverterDataBindings
import com.sadellie.unitto.core.data.programmer.ProgrammerDataBindings
import com.sadellie.unitto.core.datastore.AppPreferences
import com.sadellie.unitto.core.datastore.AppPrefsRepository
import com.sadellie.unitto.core.datastore.DataStoreBindings
import com.sadellie.unitto.core.datastore.ThemePreferences
import com.sadellie.unitto.core.datastore.ThemePrefsRepository
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.designsystem.theme.numberTypographyUnitto
import com.sadellie.unitto.core.navigation.CalculatorStartRoute
import com.sadellie.unitto.core.navigation.ConverterStartRoute
import com.sadellie.unitto.core.remote.RemoteBindings
import com.sadellie.unitto.core.ui.datetime.LocalPlatformDateFormatSettings
import com.sadellie.unitto.core.ui.datetime.PlatformDateFormatSettings
import com.sadellie.unitto.feature.converter.navigation.UnitFromRoute
import com.sadellie.unitto.feature.converter.navigation.UnitToRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory
import io.github.sadellie.themmo.Themmo
import kotlinx.browser.document
import kotlinx.datetime.TimeZone
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@OptIn(
  ExperimentalComposeUiApi::class,
  ExperimentalMaterial3WindowSizeClassApi::class,
  ExperimentalMaterial3ExpressiveApi::class,
)
fun main() {
  val appGraph = createGraphFactory<WasmAppGraph.Factory>().create()
  val platformDateFormatSettings =
    PlatformDateFormatSettings(
      is24Hour = true, // todo get preference from browser
      timeZone = TimeZone.currentSystemDefault(),
      locale = Locale.current,
    )
  ComposeViewport(document.body!!) {
    CompositionLocalProvider(
      LocalMetroViewModelFactory provides appGraph.metroViewModelFactory,
      LocalWindowSize provides rememberWindowSizeClass(),
      LocalNumberTypography provides numberTypographyUnitto(),
      LocalPlatformDateFormatSettings provides platformDateFormatSettings,
    ) {
      val appPrefs =
        appGraph.appPrefsRepository.prefs.collectAsStateWithLifecycle(null).value
          ?: return@CompositionLocalProvider
      val themePrefs =
        appGraph.themePrefsRepository.prefs.collectAsStateWithLifecycle(null).value
          ?: return@CompositionLocalProvider
      App(appPrefs, themePrefs)
    }
  }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun App(appPrefs: AppPreferences, themePrefs: ThemePreferences) {
  val themmoController = rememberUnittoThemmoController(themePrefs)
  Themmo(themmoController = themmoController) {
    Column(Modifier.fillMaxSize()) {
      ExperimentalBar(modifier = Modifier.fillMaxWidth())
      HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.outlineVariant,
      )

      val backStack = rememberNavBackStack(navBackStackConfig, appPrefs.startingScreen)
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

@DependencyGraph(
  AppScope::class,
  bindingContainers =
    [
      DataStoreBindings::class,
      CalculatorDataBindings::class,
      ProgrammerDataBindings::class,
      ConverterDataBindings::class,
      RemoteBindings::class,
    ],
)
interface WasmAppGraph : AppGraph {
  @DependencyGraph.Factory
  fun interface Factory {
    fun create(): WasmAppGraph
  }

  val appPrefsRepository: AppPrefsRepository
  val themePrefsRepository: ThemePrefsRepository
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

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

package com.sadellie.unitto.feature.glance.converter

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.sadellie.unitto.core.navigation.LocalEventBus
import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.core.navigation.Navigator
import com.sadellie.unitto.core.navigation.ResultEffect
import com.sadellie.unitto.core.navigation.Route
import kotlinx.serialization.Serializable
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun ConverterWidgetConfigureNavigation(
  backStack: NavBackStack<NavKey>,
  onDone: () -> Unit,
) {
  val navigator =
    remember(backStack, onDone) { Navigator(backStack = backStack, onDoneAction = onDone) }
  CompositionLocalProvider(LocalNavigator provides navigator) {
    NavDisplay(
      backStack = backStack,
      modifier = Modifier.background(MaterialTheme.colorScheme.background),
      entryDecorators =
        listOf(
          rememberSaveableStateHolderNavEntryDecorator(),
          rememberViewModelStoreNavEntryDecorator(),
        ),
      entryProvider = koinEntryProvider(),
    )
  }
}

@OptIn(KoinExperimentalAPI::class)
internal fun Module.converterWidgetNavigation() {
  navigation<ConverterWidgetConfigureStartRoute> {
    val viewModel =
      koinViewModel<ConverterWidgetConfigureViewModel> { parametersOf(it.appWidgetId) }

    val resultEventBus = LocalEventBus.current
    val navigator = LocalNavigator.current
    // listen for result when adding unit pair
    ResultEffect<AddUnitPairResult>(resultEventBus) {
      viewModel.addNewPair(it.unitFromId, it.unitToId)
    }

    val submitProgress = viewModel.submitProgress.collectAsStateWithLifecycle()
    LaunchedEffect(submitProgress.value) {
      if (submitProgress.value == ConverterWidgetConfigureSubmitProgress.FINISHED) {
        navigator.onDone()
      }
    }

    ConverterWidgetConfigureRoute(
      viewModel = viewModel,
      navigateToSelectFrom = { navigator.goTo(ConverterWidgetConfigureCreateNewRoute) },
      onDone = viewModel::submitNewPairs,
    )
  }

  navigation<ConverterWidgetConfigureCreateNewRoute> {
    val viewModel = koinViewModel<ConverterWidgetConfigureCreateNewViewModel>()
    val resultEventBus = LocalEventBus.current
    val navigator = LocalNavigator.current

    ResultEffect<String>(resultEventBus, SELECT_UNIT_FROM_RESULT) {
      viewModel.selectUnitFromById(it)
    }

    ResultEffect<String>(resultEventBus, SELECT_UNIT_TO_RESULT) { viewModel.selectUnitToById(it) }

    ConverterWidgetConfigureCreateNewRoute(
      viewModel = viewModel,
      navigateUp = navigator::goBack,
      navigateToSelectFrom = { navigator.goTo(ConverterWidgetConfigureSelectorRoute("")) },
      navigateToSelectTo = { unitFromId ->
        navigator.goTo(ConverterWidgetConfigureSelectorRoute(unitFromId))
      },
      onAdd = { unitFromId, unitToId ->
        val result = AddUnitPairResult(unitFromId, unitToId)
        resultEventBus.sendResult(result = result)
        navigator.goBack()
      },
    )
  }

  navigation<ConverterWidgetConfigureSelectorRoute> {
    val resultEventBus = LocalEventBus.current
    val navigator = LocalNavigator.current
    ConverterWidgetConfigureSelectorRoute(
      navigateUp = navigator::goBack,
      unitFromId = it.unitFromId,
      onClick = { unitId ->
        val key = if (it.unitFromId.isEmpty()) SELECT_UNIT_FROM_RESULT else SELECT_UNIT_TO_RESULT
        resultEventBus.sendResult(key, unitId)
        navigator.goBack()
      },
    )
  }
}

private const val SELECT_UNIT_FROM_RESULT = "SELECT_UNIT_FROM_RESULT"
private const val SELECT_UNIT_TO_RESULT = "SELECT_UNIT_TO_RESULT"

@Serializable internal data class AddUnitPairResult(val unitFromId: String, val unitToId: String)

@Serializable
internal data class ConverterWidgetConfigureStartRoute(val appWidgetId: Int) : Route {
  override val routeId = "converter_widget_configure_start_route"
}

@Serializable
private data object ConverterWidgetConfigureCreateNewRoute : Route {
  override val routeId = "converter_widget_configure_create_new_route"
}

@Serializable
private data class ConverterWidgetConfigureSelectorRoute(val unitFromId: String) : Route {
  override val routeId = "converter_widget_configure_selector_route"
}

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.converter.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.result.ResultEffect
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.navigation.ConverterStartRoute
import com.sadellie.unitto.core.navigation.LocalEventBus
import com.sadellie.unitto.core.navigation.LocalNavigator
import com.sadellie.unitto.core.navigation.Route
import com.sadellie.unitto.feature.converter.ConverterRoute
import com.sadellie.unitto.feature.converter.ConverterViewModel
import com.sadellie.unitto.feature.converter.UnitFromSelectorRoute
import com.sadellie.unitto.feature.converter.UnitFromSelectorViewModel
import com.sadellie.unitto.feature.converter.UnitToSelectorRoute
import com.sadellie.unitto.feature.converter.UnitToSelectorViewModel
import dev.zacsweers.metrox.viewmodel.assistedMetroViewModel
import kotlinx.serialization.Serializable

fun EntryProviderScope<NavKey>.converterNavigation() {
  entry<ConverterStartRoute> { route ->
    val viewModel =
      assistedMetroViewModel<ConverterViewModel, ConverterViewModel.Factory> { create(route) }
    val resultEventBus = LocalEventBus.current
    val navigator = LocalNavigator.current
    ResultEffect<String>(RESULT_TAG_FROM, resultEventBus) { unitFromId ->
      viewModel.updateUnitFromId(unitFromId)
    }
    ResultEffect<String>(RESULT_TAG_TO, resultEventBus) { unitToId ->
      viewModel.updateUnitToId(unitToId)
    }
    ConverterRoute(
      viewModel = viewModel,
      navigateToLeftScreen = { unitFromId, group ->
        navigator.goTo(UnitFromRoute(unitFromId, group))
      },
      navigateToRightScreen = { unitFromId, unitToId, group, input1, input2 ->
        navigator.goTo(UnitToRoute(unitFromId, unitToId, group, input1, input2))
      },
      openDrawer = navigator::openDrawer,
    )
  }
  entry<UnitFromRoute> { route ->
    val resultEventBus = LocalEventBus.current
    val navigator = LocalNavigator.current
    UnitFromSelectorRoute(
      unitSelectorViewModel =
        assistedMetroViewModel<UnitFromSelectorViewModel, UnitFromSelectorViewModel.Factory> {
          create(route)
        },
      updateUnitFrom = { unitFromId -> resultEventBus.sendResult(RESULT_TAG_FROM, unitFromId) },
      navigateUp = navigator::goBack,
      navigateToUnitGroups = navigator::navigateToUnitGroups,
    )
  }
  entry<UnitToRoute> { route ->
    val resultEventBus = LocalEventBus.current
    val navigator = LocalNavigator.current
    UnitToSelectorRoute(
      unitSelectorViewModel =
        assistedMetroViewModel<UnitToSelectorViewModel, UnitToSelectorViewModel.Factory> {
          create(route)
        },
      updateUnitTo = { unitToId -> resultEventBus.sendResult(RESULT_TAG_TO, unitToId) },
      navigateUp = navigator::goBack,
      navigateToUnitGroups = navigator::navigateToUnitGroups,
    )
  }
}

@Serializable
data class UnitFromRoute(val unitFromId: String, val unitGroup: UnitGroup) : Route {
  override val routeId = "unit_from"
}

@Serializable
data class UnitToRoute(
  val unitFromId: String,
  val unitToId: String,
  val unitGroup: UnitGroup,
  val input1: String,
  val input2: String,
) : Route {
  override val routeId = "unit_to"
}

private const val RESULT_TAG_FROM = "from"
private const val RESULT_TAG_TO = "to"

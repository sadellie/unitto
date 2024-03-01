/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

package com.sadellie.unitto.feature.converter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar

@Composable
internal fun ConverterRoute(
  viewModel: ConverterViewModel = hiltViewModel(),
  navigateToLeftScreen: (unitFromId: String, group: UnitGroup) -> Unit,
  navigateToRightScreen:
    (
      unitFromId: String, unitToId: String, group: UnitGroup, input1: String, input2: String,
    ) -> Unit,
  openDrawer: () -> Unit,
) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()

  LaunchedEffect(Unit) { viewModel.observeInput() }

  ConverterScreen(
    uiState = uiState.value,
    navigateToLeftScreen = navigateToLeftScreen,
    navigateToRightScreen = navigateToRightScreen,
    openDrawer = openDrawer,
    swapUnits = viewModel::swapUnits,
    convert = viewModel::retryConvert,
  )
}

@Composable
private fun ConverterScreen(
  uiState: ConverterUIState,
  navigateToLeftScreen: (unitFromId: String, group: UnitGroup) -> Unit,
  navigateToRightScreen:
    (
      unitFromId: String, unitToId: String, group: UnitGroup, input1: String, input2: String,
    ) -> Unit,
  openDrawer: () -> Unit,
  swapUnits: (String, String) -> Unit,
  convert: () -> Unit,
) {
  when (uiState) {
    ConverterUIState.Loading -> EmptyScreen()

    is ConverterUIState.NumberBase ->
      UnitConverterTopBar(openDrawer) {
        NumberBase(
          modifier = Modifier.padding(it),
          uiState = uiState,
          navigateToLeftScreen = navigateToLeftScreen,
          swapUnits = swapUnits,
          navigateToRightScreen = navigateToRightScreen,
        )
      }

    is ConverterUIState.Default ->
      UnitConverterTopBar(openDrawer) {
        ConverterDefault(
          modifier = Modifier.padding(it),
          uiState = uiState,
          navigateToLeftScreen = navigateToLeftScreen,
          swapUnits = swapUnits,
          navigateToRightScreen = navigateToRightScreen,
          convert = convert,
        )
      }
  }
}

@Composable
private fun UnitConverterTopBar(
  openDrawer: () -> Unit,
  content: @Composable (PaddingValues) -> Unit,
) {
  ScaffoldWithTopBar(
    title = {},
    navigationIcon = { DrawerButton(openDrawer) },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
    content = { content(it) },
  )
}

@Preview(widthDp = 432, heightDp = 1008, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(widthDp = 432, heightDp = 864, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(widthDp = 597, heightDp = 1393, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(heightDp = 432, widthDp = 1008, device = "spec:parent=pixel_5,orientation=landscape")
@Preview(heightDp = 432, widthDp = 864, device = "spec:parent=pixel_5,orientation=landscape")
@Preview(heightDp = 597, widthDp = 1393, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun PreviewConverterScreen() {
  ConverterScreen(
    uiState = ConverterUIState.Loading,
    navigateToLeftScreen = { _, _ -> },
    navigateToRightScreen = { _, _, _, _, _ -> },
    openDrawer = {},
    swapUnits = { _, _ -> },
    convert = {},
  )
}

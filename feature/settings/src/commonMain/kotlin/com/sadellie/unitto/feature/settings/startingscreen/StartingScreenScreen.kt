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

package com.sadellie.unitto.feature.settings.startingscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.navigation.CalculatorStartRoute
import com.sadellie.unitto.core.navigation.DrawerItem
import com.sadellie.unitto.core.navigation.mainDrawerItems
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.settings_starting_screen

@Composable
internal fun StartingScreenRoute(navigateUp: () -> Unit) {
  val viewModel: StartingScreenViewModel = koinViewModel()
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycleKMP().value) {
    null -> EmptyScreen()
    else -> {
      StartingScreenScreen(
        startingScreenGraphId = prefs.startingScreen.routeId,
        updateStartingScreen = viewModel::updateStartingScreen,
        navigateUp = navigateUp,
      )
    }
  }
}

@Composable
private fun StartingScreenScreen(
  startingScreenGraphId: String,
  updateStartingScreen: (String) -> Unit,
  navigateUp: () -> Unit,
) {
  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.settings_starting_screen),
    navigationIcon = { NavigateUpButton(navigateUp) },
  ) { padding ->
    LazyColumn(
      contentPadding = padding,
      modifier = Modifier.padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      itemsIndexed(mainDrawerItems, { _, destination -> destination.topLevelRoute.routeId }) {
        index,
        destination ->
        ListItemExpressive(
          onClick = { updateStartingScreen(destination.topLevelRoute.routeId) },
          shape = ListItemExpressiveDefaults.listedShaped(index, mainDrawerItems.size),
          headlineContent = { Text(stringResource(destination.name)) },
          leadingContent = {
            RadioButton(
              selected = destination.topLevelRoute.routeId == startingScreenGraphId,
              onClick = { updateStartingScreen(destination.topLevelRoute.routeId) },
            )
          },
          trailingContent = { AddShortcutButton(destination) },
        )
      }
    }
  }
}

@Composable internal expect fun AddShortcutButton(destination: DrawerItem)

@Preview
@Composable
private fun StartingScreenPreview() {
  StartingScreenScreen(
    startingScreenGraphId = CalculatorStartRoute.routeId,
    updateStartingScreen = {},
    navigateUp = {},
  )
}

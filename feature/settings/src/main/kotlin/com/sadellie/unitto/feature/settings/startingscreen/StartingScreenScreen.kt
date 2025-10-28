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

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.designsystem.icons.symbols.AppShortcut
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.navigation.ConverterGraphRoute
import com.sadellie.unitto.core.navigation.mainDrawerItems
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.listedShaped

@Composable
internal fun StartingScreenRoute(
  viewModel: StartingScreenViewModel = hiltViewModel(),
  navigateUp: () -> Unit,
) {
  when (val prefs = viewModel.prefs.collectAsStateWithLifecycle().value) {
    null -> EmptyScreen()
    else -> {
      StartingScreenScreen(
        startingScreenGraphId = prefs.startingScreen.id,
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
    title = stringResource(R.string.settings_starting_screen),
    navigationIcon = { NavigateUpButton(navigateUp) },
  ) { padding ->
    val mContext = LocalContext.current
    LazyColumn(
      contentPadding = padding,
      modifier = Modifier.padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListArrangement,
    ) {
      itemsIndexed(mainDrawerItems, { _, destination -> destination.graphRoute.id }) {
        index,
        destination ->
        ListItemExpressive(
          modifier = Modifier.clickable { updateStartingScreen(destination.graphRoute.id) },
          shape = ListItemDefaults.listedShaped(index, mainDrawerItems.size),
          headlineContent = { Text(stringResource(destination.name)) },
          leadingContent = {
            RadioButton(
              selected = destination.graphRoute.id == startingScreenGraphId,
              onClick = { updateStartingScreen(destination.graphRoute.id) },
            )
          },
          trailingContent = trail@{
              if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return@trail
              IconButton(onClick = { destination.addShortcut(mContext) }) {
                Icon(Symbols.AppShortcut, null)
              }
            },
        )
      }
    }
  }
}

@Preview
@Composable
private fun StartingScreenPreview() {
  StartingScreenScreen(
    startingScreenGraphId = ConverterGraphRoute.id,
    updateStartingScreen = {},
    navigateUp = {},
  )
}

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

package com.sadellie.unitto.feature.settings.startingscreen

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppShortcut
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.addShortcut
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.ListItem
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.model.DrawerItem

@Composable
internal fun StartingScreenRoute(
    viewModel: StartingScreenViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    when (val prefs = viewModel.prefs.collectAsStateWithLifecycle().value) {
        null -> EmptyScreen()
        else -> {
            StartingScreenScreen(
                startingScreen = prefs.startingScreen,
                updateStartingScreen = viewModel::updateStartingScreen,
                navigateUp = navigateUp,
            )
        }
    }
}

@Composable
private fun StartingScreenScreen(
    startingScreen: String,
    updateStartingScreen: (String) -> Unit,
    navigateUp: () -> Unit,
) {
    val mContext = LocalContext.current

    ScaffoldWithLargeTopBar(
        title = stringResource(R.string.settings_starting_screen),
        navigationIcon = { NavigateUpButton(navigateUp) },
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(DrawerItem.main, { it.graph }) { destination ->
                ListItem(
                    modifier = Modifier.clickable { updateStartingScreen(destination.graph) },
                    headlineContent = {
                        Text(stringResource(destination.name))
                    },
                    leadingContent = {
                        RadioButton(
                            selected = destination.graph == startingScreen,
                            onClick = { updateStartingScreen(destination.graph) },
                        )
                    },
                    trailingContent = trail@{
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return@trail

                        IconButton(
                            onClick = { mContext.addShortcut(destination) },
                        ) {
                            Icon(Icons.Default.AppShortcut, null)
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
        startingScreen = DrawerItem.Converter.graph,
        updateStartingScreen = {},
        navigateUp = {},
    )
}

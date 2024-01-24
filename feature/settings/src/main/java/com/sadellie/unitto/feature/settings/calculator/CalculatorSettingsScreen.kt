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

package com.sadellie.unitto.feature.settings.calculator

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.ListItem
import com.sadellie.unitto.core.ui.common.ScaffoldWithLargeTopBar

@Composable
internal fun CalculatorSettingsRoute(
    viewModel: CalculatorSettingsViewModel = hiltViewModel(),
    navigateUpAction: () -> Unit,
) {
    when (val prefs = viewModel.uiState.collectAsStateWithLifecycle().value) {
        CalculatorSettingsUIState.Loading -> EmptyScreen()
        else -> {
            CalculatorSettingsScreen(
                uiState = prefs,
                navigateUpAction = navigateUpAction,
                updatePartialHistoryView = viewModel::updatePartialHistoryView,
                updateRpnMode = viewModel::updateRpnMode,
            )
        }
    }
}

// TODO Translate
@Composable
private fun CalculatorSettingsScreen(
    uiState: CalculatorSettingsUIState,
    navigateUpAction: () -> Unit,
    updatePartialHistoryView: (Boolean) -> Unit,
    updateRpnMode: (Boolean) -> Unit,
) {
    ScaffoldWithLargeTopBar(
        title = stringResource(R.string.calculator_title),
        navigationIcon = { NavigateUpButton(navigateUpAction) }
    ) { padding ->
        Column(Modifier.padding(padding)) {
//            SingleChoiceSegmentedButtonRow(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                SegmentedButton(
//                    selected = uiState is CalculatorSettingsUIState.Standard,
//                    onClick = { updateRpnMode(false) },
//                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
//                ) {
//                    Text("Standard")
//                }
//                SegmentedButton(
//                    selected = uiState == CalculatorSettingsUIState.RPN,
//                    onClick = { updateRpnMode(true) },
//                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
//                ) {
//                    Text("RPN")
//                }
//            }

            Crossfade(
                targetState = uiState,
                label = "Mode switch"
            ) { state ->
                when (state) {
                    is CalculatorSettingsUIState.Standard -> {
                        Column {
                            ListItem(
                                headlineText = stringResource(R.string.settings_partial_history_view),
                                icon = Icons.Default.Timer,
                                supportingText = stringResource(R.string.settings_partial_history_view_support),
                                switchState = state.partialHistoryView,
                                onSwitchChange = updatePartialHistoryView
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCalculatorSettingsScreenStandard() {
    CalculatorSettingsScreen(
        uiState = CalculatorSettingsUIState.Standard(
            partialHistoryView = true,
        ),
        navigateUpAction = {},
        updatePartialHistoryView = {},
        updateRpnMode = {}
    )
}

@Preview
@Composable
private fun PreviewCalculatorSettingsScreenRPN() {
    CalculatorSettingsScreen(
        uiState = CalculatorSettingsUIState.RPN,
        navigateUpAction = {},
        updatePartialHistoryView = {},
        updateRpnMode = {}
    )
}

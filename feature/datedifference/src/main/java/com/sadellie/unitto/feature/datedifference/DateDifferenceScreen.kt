/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.feature.datedifference

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.DatePickerDialog
import com.sadellie.unitto.core.ui.common.MenuButton
import com.sadellie.unitto.core.ui.common.SettingsButton
import com.sadellie.unitto.core.ui.common.TimePickerDialog
import com.sadellie.unitto.core.ui.common.UnittoScreenWithTopBar
import com.sadellie.unitto.feature.datedifference.components.DateTimeResultBlock
import com.sadellie.unitto.feature.datedifference.components.DateTimeSelectorBlock
import java.time.LocalDateTime


@Composable
internal fun DateDifferenceRoute(
    viewModel: DateDifferenceViewModel = hiltViewModel(),
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    DateDifferenceScreen(
        navigateToMenu = navigateToMenu,
        navigateToSettings = navigateToSettings,
        uiState = uiState.value,
        updateStart = viewModel::setStartTime,
        updateEnd = viewModel::setEndTime
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun DateDifferenceScreen(
    navigateToMenu: () -> Unit,
    navigateToSettings: () -> Unit,
    updateStart: (LocalDateTime) -> Unit,
    updateEnd: (LocalDateTime) -> Unit,
    uiState: UIState
) {
    var dialogState by remember { mutableStateOf(DialogState.NONE) }

    UnittoScreenWithTopBar(
        title = { Text(stringResource(R.string.date_difference)) },
        navigationIcon = { MenuButton(navigateToMenu) },
        actions = {
            SettingsButton(navigateToSettings)
        }
    ) { paddingValues ->
        FlowRow(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            maxItemsInEachRow = 2,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DateTimeSelectorBlock(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                title = stringResource(R.string.date_difference_start),
                onClick = { dialogState = DialogState.FROM },
                onTimeClick = { dialogState = DialogState.FROM_TIME },
                onDateClick = { dialogState = DialogState.FROM_DATE },
                dateTime = uiState.start
            )

            DateTimeSelectorBlock(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                title = stringResource(R.string.date_difference_end),
                onClick = { dialogState = DialogState.TO },
                onTimeClick = { dialogState = DialogState.TO_TIME },
                onDateClick = { dialogState = DialogState.TO_DATE },
                dateTime = uiState.end
            )

            AnimatedVisibility(
                visible = uiState.result is DateDifference.Default,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                DateTimeResultBlock(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    dateDifference = uiState.result
                )
            }
        }
    }

    fun resetDialog() {
        dialogState = DialogState.NONE
    }

    when (dialogState) {
        DialogState.FROM -> {
            TimePickerDialog(
                localDateTime = uiState.start,
                onDismiss = ::resetDialog,
                onConfirm = {
                    updateStart(it)
                    dialogState = DialogState.FROM_DATE
                },
                confirmLabel = stringResource(R.string.next_label)
            )
        }

        DialogState.FROM_TIME -> {
            TimePickerDialog(
                localDateTime = uiState.start,
                onDismiss = ::resetDialog,
                onConfirm = {
                    updateStart(it)
                    resetDialog()
                }
            )
        }

        DialogState.FROM_DATE -> {
            DatePickerDialog(
                localDateTime = uiState.start,
                onDismiss = ::resetDialog,
                onConfirm = {
                    updateStart(it)
                    resetDialog()
                }
            )
        }

        DialogState.TO -> {
            TimePickerDialog(
                localDateTime = uiState.end,
                onDismiss = ::resetDialog,
                onConfirm = {
                    updateEnd(it)
                    dialogState = DialogState.TO_DATE
                },
                confirmLabel = stringResource(R.string.next_label)
            )
        }

        DialogState.TO_TIME -> {
            TimePickerDialog(
                localDateTime = uiState.end,
                onDismiss = ::resetDialog,
                onConfirm = {
                    updateEnd(it)
                    resetDialog()
                }
            )
        }

        DialogState.TO_DATE -> {
            DatePickerDialog(
                localDateTime = uiState.end,
                onDismiss = ::resetDialog,
                onConfirm = {
                    updateEnd(it)
                    resetDialog()
                }
            )
        }

        else -> {}
    }
}

private enum class DialogState {
    NONE, FROM, FROM_TIME, FROM_DATE, TO, TO_TIME, TO_DATE
}

@Preview
@Composable
private fun DateDifferenceScreenPreview() {
    DateDifferenceScreen(
        navigateToMenu = {},
        navigateToSettings = {},
        updateStart = {},
        updateEnd = {},
        uiState = UIState(
            result = DateDifference.Default(4, 1, 2, 3, 4)
        )
    )
}

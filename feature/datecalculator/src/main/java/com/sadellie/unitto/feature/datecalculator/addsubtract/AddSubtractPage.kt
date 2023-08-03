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

package com.sadellie.unitto.feature.datecalculator.addsubtract

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.feature.datecalculator.components.DateTimeDialogs
import com.sadellie.unitto.feature.datecalculator.components.DateTimeSelectorBlock
import com.sadellie.unitto.feature.datecalculator.components.DialogState
import com.sadellie.unitto.feature.datecalculator.components.TimeUnitTextField
import java.time.ZonedDateTime

@Composable
internal fun AddSubtractPage(
    viewModel: AddSubtractViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    AddSubtractView(
        uiState = uiState,
        updateStart = viewModel::updateStart,
        updateYears = viewModel::updateYears,
        updateMonths = viewModel::updateMonths,
        updateDays = viewModel::updateDays,
        updateHours = viewModel::updateHours,
        updateMinutes = viewModel::updateMinutes,
        updateAddition = viewModel::updateAddition
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddSubtractView(
    uiState: AddSubtractState,
    updateStart: (ZonedDateTime) -> Unit,
    updateYears: (String) -> Unit,
    updateMonths: (String) -> Unit,
    updateDays: (String) -> Unit,
    updateHours: (String) -> Unit,
    updateMinutes: (String) -> Unit,
    updateAddition: (Boolean) -> Unit,
) {
    var dialogState by remember { mutableStateOf(DialogState.NONE) }
    val mContext = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { mContext.addEvent(uiState.start, uiState.result) }) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            item("dates") {
                FlowRow(
                    modifier = Modifier,
                    maxItemsInEachRow = 2,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DateTimeSelectorBlock(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        title = stringResource(R.string.date_difference_start),
                        dateTime = uiState.start,
                        onLongClick = { updateStart(ZonedDateTime.now()) },
                        onClick = { dialogState = DialogState.FROM },
                        onTimeClick = { dialogState = DialogState.FROM_TIME },
                        onDateClick = { dialogState = DialogState.FROM_DATE },
                    )

                    DateTimeSelectorBlock(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        title = stringResource(R.string.date_difference_end),
                        dateTime = uiState.result,
                    )
                }
            }

            item("modes") {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    SegmentedButton(
                        selected = uiState.addition,
                        onClick = { updateAddition(true) },
                        shape = SegmentedButtonDefaults.shape(position = 0, count = 2),
                        icon = {}
                    ) {
                        Icon(Icons.Outlined.Add, null)
                    }
                    SegmentedButton(
                        selected = !uiState.addition,
                        onClick = { updateAddition(false) },
                        shape = SegmentedButtonDefaults.shape(position = 1, count = 2),
                        icon = {}
                    ) {
                        Icon(Icons.Outlined.Remove, null)
                    }
                }
            }

            item("textFields") {
                Column {
                    TimeUnitTextField(
                        value = uiState.years,
                        onValueChange = updateYears,
                        label = stringResource(R.string.date_difference_years),
                        formatterSymbols = uiState.formatterSymbols
                    )
                    TimeUnitTextField(
                        value = uiState.months,
                        onValueChange = updateMonths,
                        label = stringResource(R.string.date_difference_months),
                        formatterSymbols = uiState.formatterSymbols
                    )
                    TimeUnitTextField(
                        value = uiState.days,
                        onValueChange = updateDays,
                        label = stringResource(R.string.date_difference_days),
                        formatterSymbols = uiState.formatterSymbols
                    )
                    TimeUnitTextField(
                        value = uiState.hours,
                        onValueChange = updateHours,
                        label = stringResource(R.string.date_difference_hours),
                        formatterSymbols = uiState.formatterSymbols
                    )
                    TimeUnitTextField(
                        value = uiState.minutes,
                        onValueChange = updateMinutes,
                        label = stringResource(R.string.date_difference_minutes),
                        imeAction = ImeAction.Done,
                        formatterSymbols = uiState.formatterSymbols
                    )
                }
            }
        }
    }

    DateTimeDialogs(
        dialogState = dialogState,
        updateDialogState = { dialogState = it },
        date = uiState.start,
        updateDate = updateStart,
        bothState = DialogState.FROM,
        timeState = DialogState.FROM_TIME,
        dateState = DialogState.FROM_DATE,
    )
}

private fun Context.addEvent(start: ZonedDateTime, end: ZonedDateTime) {
    val startMillis: Long = start.toEpochSecond() * 1000
    val endMillis: Long = end.toEpochSecond() * 1000
    val intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, R.string.error_label, Toast.LENGTH_SHORT).show()
    }
}

@Preview
@Composable
fun AddSubtractViewPreview() {
    AddSubtractView(
        uiState = AddSubtractState(
            years = "12"
        ),
        updateStart = {},
        updateYears = {},
        updateMonths = {},
        updateDays = {},
        updateHours = {},
        updateMinutes = {},
        updateAddition = {}
    )
}

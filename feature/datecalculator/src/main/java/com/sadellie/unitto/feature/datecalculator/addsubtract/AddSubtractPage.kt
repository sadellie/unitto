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
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.textfield.addTokens
import com.sadellie.unitto.core.ui.common.textfield.deleteTokens
import com.sadellie.unitto.core.ui.isPortrait
import com.sadellie.unitto.core.ui.showToast
import com.sadellie.unitto.feature.datecalculator.components.AddSubtractKeyboard
import com.sadellie.unitto.feature.datecalculator.components.DateTimeDialogs
import com.sadellie.unitto.feature.datecalculator.components.DateTimeSelectorBlock
import com.sadellie.unitto.feature.datecalculator.components.DialogState
import com.sadellie.unitto.feature.datecalculator.components.TimeUnitTextField
import java.time.ZonedDateTime

@Composable
internal fun AddSubtractPage(
    viewModel: AddSubtractViewModel = hiltViewModel(),
    toggleTopBar: (Boolean) -> Unit,
    showKeyboard: Boolean,
    toggleKeyboard: (Boolean) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    AddSubtractView(
        uiState = uiState,
        toggleTopBar = toggleTopBar,
        showKeyboard = showKeyboard,
        toggleKeyboard = toggleKeyboard,
        updateStart = viewModel::updateStart,
        updateYears = viewModel::updateYears,
        updateMonths = viewModel::updateMonths,
        updateDays = viewModel::updateDays,
        updateHours = viewModel::updateHours,
        updateMinutes = viewModel::updateMinutes,
        updateAddition = viewModel::updateAddition,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddSubtractView(
    uiState: AddSubtractState,
    toggleTopBar: (Boolean) -> Unit,
    showKeyboard: Boolean,
    toggleKeyboard: (Boolean) -> Unit,
    updateStart: (ZonedDateTime) -> Unit,
    updateYears: (TextFieldValue) -> Unit,
    updateMonths: (TextFieldValue) -> Unit,
    updateDays: (TextFieldValue) -> Unit,
    updateHours: (TextFieldValue) -> Unit,
    updateMinutes: (TextFieldValue) -> Unit,
    updateAddition: (Boolean) -> Unit,
) {
    val mContext = LocalContext.current
    val focusManager = LocalFocusManager.current
    val landscape = !isPortrait()

    var dialogState by remember { mutableStateOf(DialogState.NONE) }
    var addSymbol: ((TextFieldValue) -> Unit)? by remember { mutableStateOf(null) }
    var focusedTextFieldValue: TextFieldValue? by remember { mutableStateOf(null) }

    LaunchedEffect(showKeyboard, landscape) {
        toggleTopBar(!(showKeyboard and landscape))
    }

    LaunchedEffect(addSymbol, focusedTextFieldValue) {
        toggleKeyboard((addSymbol != null) and (focusedTextFieldValue != null))
    }

    BackHandler(showKeyboard) {
        focusManager.clearFocus()
        addSymbol = null
        focusedTextFieldValue = null
    }

    val showResult = remember(uiState.start, uiState.result) { uiState.start != uiState.result }
    val inputWidth = animateFloatAsState(
        targetValue = if (showResult) 0.5f else 1f,
        label = "inputWidth"
    )
    val resultWidth = animateFloatAsState(
        targetValue = if (showResult) 1f else 0f,
        label = "resultWidth"
    )

    Column(Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        mContext.addEvent(uiState.start, uiState.result)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                    )
                }
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DateTimeSelectorBlock(
                        modifier = Modifier
                            .fillMaxWidth(inputWidth.value),
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        title = stringResource(R.string.date_calculator_start),
                        dateTime = uiState.start,
                        onLongClick = { updateStart(ZonedDateTime.now()) },
                        onClick = { dialogState = DialogState.FROM },
                        onTimeClick = { dialogState = DialogState.FROM_TIME },
                        onDateClick = { dialogState = DialogState.FROM_DATE },
                    )

                    DateTimeSelectorBlock(
                        modifier = Modifier
                            .fillMaxWidth(resultWidth.value),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        title = stringResource(R.string.date_calculator_end),
                        dateTime = uiState.result,
                    )
                }

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    SegmentedButton(
                        selected = uiState.addition,
                        onClick = { updateAddition(true) },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                        icon = {}
                    ) {
                        Icon(Icons.Outlined.Add, stringResource(R.string.date_calculator_add))
                    }
                    SegmentedButton(
                        selected = !uiState.addition,
                        onClick = { updateAddition(false) },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                        icon = {}
                    ) {
                        Icon(
                            Icons.Outlined.Remove,
                            stringResource(R.string.date_calculator_subtract)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(32.dp)
                        )
                        .padding(16.dp, 24.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TimeUnitTextField(
                        modifier = Modifier
                            .onFocusEvent {
                                if (it.hasFocus) {
                                    addSymbol = updateYears
                                    focusedTextFieldValue = uiState.years
                                }
                            }
                            .fillMaxWidth(),
                        value = uiState.years,
                        onValueChange = updateYears,
                        label = stringResource(R.string.date_calculator_years),
                        formatterSymbols = uiState.formatterSymbols
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TimeUnitTextField(
                            modifier = Modifier
                                .onFocusEvent {
                                    if (it.hasFocus) {
                                        addSymbol = updateMonths
                                        focusedTextFieldValue = uiState.months
                                    }
                                }
                                .weight(1f),
                            value = uiState.months,
                            onValueChange = updateMonths,
                            label = stringResource(R.string.date_calculator_months),
                            formatterSymbols = uiState.formatterSymbols
                        )

                        TimeUnitTextField(
                            modifier = Modifier
                                .onFocusEvent {
                                    if (it.hasFocus) {
                                        addSymbol = updateDays
                                        focusedTextFieldValue = uiState.days
                                    }
                                }
                                .weight(1f),
                            value = uiState.days,
                            onValueChange = updateDays,
                            label = stringResource(R.string.date_calculator_days),
                            formatterSymbols = uiState.formatterSymbols
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TimeUnitTextField(
                            modifier = Modifier
                                .onFocusEvent {
                                    if (it.hasFocus) {
                                        addSymbol = updateHours
                                        focusedTextFieldValue = uiState.hours
                                    }
                                }
                                .weight(1f),
                            value = uiState.hours,
                            onValueChange = updateHours,
                            label = stringResource(R.string.date_calculator_hours),
                            formatterSymbols = uiState.formatterSymbols
                        )

                        TimeUnitTextField(
                            modifier = Modifier
                                .onFocusEvent {
                                    if (it.hasFocus) {
                                        addSymbol = updateMinutes
                                        focusedTextFieldValue = uiState.minutes
                                    }
                                }
                                .weight(1f),
                            value = uiState.minutes,
                            onValueChange = updateMinutes,
                            label = stringResource(R.string.date_calculator_minutes),
                            formatterSymbols = uiState.formatterSymbols
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = showKeyboard,
            enter = slideInVertically { it / 2 } + fadeIn(),
            exit = slideOutVertically { it / 2 } + fadeOut()
        ) {
            HorizontalDivider()
            AddSubtractKeyboard(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight(if (isPortrait()) 0.4f else 0.6f)
                    .padding(2.dp, 4.dp),
                addSymbol = {
                    val newValue = focusedTextFieldValue?.addTokens(it)
                    if (newValue != null) {
                        addSymbol?.invoke(newValue)
                    }
                },
                deleteSymbol = {
                    val newValue = focusedTextFieldValue?.deleteTokens()
                    if (newValue != null) {
                        addSymbol?.invoke(newValue)
                    }
                },
                onConfirm = {
                    focusManager.clearFocus()
                    addSymbol = null
                    focusedTextFieldValue = null
                },
                allowVibration = uiState.allowVibration,
                imeAction = if (addSymbol == updateMinutes) ImeAction.Done else ImeAction.Next
            )
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
        showToast(this, this.getString(R.string.error_label))
    }
}

@Preview
@Composable
fun AddSubtractViewPreview() {
    AddSubtractView(
        uiState = AddSubtractState(
            years = TextFieldValue("12")
        ),
        toggleTopBar = {},
        showKeyboard = false,
        toggleKeyboard = {},
        updateStart = {},
        updateYears = {},
        updateMonths = {},
        updateDays = {},
        updateHours = {},
        updateMinutes = {},
        updateAddition = {},
    )
}

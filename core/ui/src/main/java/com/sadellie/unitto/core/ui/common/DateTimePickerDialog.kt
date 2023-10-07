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

package com.sadellie.unitto.core.ui.common

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.sadellie.unitto.core.base.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.max

// https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material3/samples/TimePickerSamples.kt
@Composable
fun TimePickerDialog(
    hour: Int,
    minute: Int,
    onCancel: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit,
    confirmLabel: String = stringResource(R.string.ok_label),
) {
    val pickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = DateFormat.is24HourFormat(LocalContext.current)
    )
    val configuration = LocalConfiguration.current
    val showingPicker = rememberSaveable { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            if (configuration.screenHeightDp > 400) {
                // Make this take the entire viewport. This will guarantee that Screen readers
                // focus the toggle first.
                Box(
                    Modifier
                        .fillMaxSize()
                        .semantics {
                            isTraversalGroup = true
                        }
                ) {
                    IconButton(
                        modifier = Modifier
                            // This is a workaround so that the Icon comes up first
                            // in the talkback traversal order. So that users of a11y
                            // services can use the text input. When talkback traversal
                            // order is customizable we can remove this.
                            .size(64.dp, 72.dp)
                            .align(Alignment.BottomStart)
                            .zIndex(5f),
                        onClick = { showingPicker.value = !showingPicker.value }) {
                        val icon = if (showingPicker.value) {
                            Icons.Outlined.Keyboard
                        } else {
                            Icons.Outlined.Schedule
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = stringResource(R.string.select_time_label)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = stringResource(R.string.select_time_label),
                    style = MaterialTheme.typography.labelMedium
                )
                if (showingPicker.value && configuration.screenHeightDp > 400) {
                    TimePicker(state = pickerState)
                } else {
                    TimeInput(state = pickerState)
                }
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text(stringResource(R.string.cancel_label)) }
                    TextButton(
                        onClick = { onConfirm(pickerState.hour, pickerState.minute) }
                    ) { Text(confirmLabel) }
                }
            }
        }
    }
}

@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    localDateTime: ZonedDateTime,
    confirmLabel: String = stringResource(R.string.ok_label),
    dismissLabel: String = stringResource(R.string.cancel_label),
    onDismiss: () -> Unit = {},
    onConfirm: (ZonedDateTime) -> Unit,
) {
    val pickerState = rememberDatePickerState(localDateTime.toEpochSecond() * 1000)

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier.wrapContentHeight(),
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .requiredWidth(360.dp)
                .heightIn(max = 568.dp),
            shape = DatePickerDefaults.shape,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                DatePicker(state = pickerState)

                Box(modifier = Modifier
                    .align(Alignment.End)
                    .padding(_dialogButtonsPadding)) {

                    AlertDialogFlowRow(
                        mainAxisSpacing = _dialogButtonsMainAxisSpacing,
                        crossAxisSpacing = _dialogButtonsCrossAxisSpacing
                    ) {
                        TextButton(
                            onClick = onDismiss
                        ) {
                            Text(text = dismissLabel)
                        }
                        TextButton(
                            onClick = {
                                val millis = pickerState.selectedDateMillis ?: return@TextButton

                                val date = LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(millis), ZoneId.systemDefault()
                                )

                                onConfirm(
                                    localDateTime
                                        .withYear(date.year)
                                        .withMonth(date.monthValue)
                                        .withDayOfMonth(date.dayOfMonth)
                                )
                            }
                        ) {
                            Text(text = confirmLabel)
                        }
                    }
                }
            }
        }
    }
}

// From androidx/compose/material3/AlertDialog.kt
@Composable
private fun AlertDialogFlowRow(
    mainAxisSpacing: Dp,
    crossAxisSpacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(content) { measurables, constraints ->
        val sequences = mutableListOf<List<Placeable>>()
        val crossAxisSizes = mutableListOf<Int>()
        val crossAxisPositions = mutableListOf<Int>()

        var mainAxisSpace = 0
        var crossAxisSpace = 0

        val currentSequence = mutableListOf<Placeable>()
        var currentMainAxisSize = 0
        var currentCrossAxisSize = 0

        // Return whether the placeable can be added to the current sequence.
        fun canAddToCurrentSequence(placeable: Placeable) =
            currentSequence.isEmpty() || currentMainAxisSize + mainAxisSpacing.roundToPx() +
                    placeable.width <= constraints.maxWidth

        // Store current sequence information and start a new sequence.
        fun startNewSequence() {
            if (sequences.isNotEmpty()) {
                crossAxisSpace += crossAxisSpacing.roundToPx()
            }
            sequences += currentSequence.toList()
            crossAxisSizes += currentCrossAxisSize
            crossAxisPositions += crossAxisSpace

            crossAxisSpace += currentCrossAxisSize
            mainAxisSpace = max(mainAxisSpace, currentMainAxisSize)

            currentSequence.clear()
            currentMainAxisSize = 0
            currentCrossAxisSize = 0
        }

        for (measurable in measurables) {
            // Ask the child for its preferred size.
            val placeable = measurable.measure(constraints)

            // Start a new sequence if there is not enough space.
            if (!canAddToCurrentSequence(placeable)) startNewSequence()

            // Add the child to the current sequence.
            if (currentSequence.isNotEmpty()) {
                currentMainAxisSize += mainAxisSpacing.roundToPx()
            }
            currentSequence.add(placeable)
            currentMainAxisSize += placeable.width
            currentCrossAxisSize = max(currentCrossAxisSize, placeable.height)
        }

        if (currentSequence.isNotEmpty()) startNewSequence()

        val mainAxisLayoutSize = max(mainAxisSpace, constraints.minWidth)

        val crossAxisLayoutSize = max(crossAxisSpace, constraints.minHeight)

        layout(mainAxisLayoutSize, crossAxisLayoutSize) {
            sequences.forEachIndexed { i, placeables ->
                val childrenMainAxisSizes = IntArray(placeables.size) { j ->
                    placeables[j].width +
                            if (j < placeables.lastIndex) mainAxisSpacing.roundToPx() else 0
                }
                val arrangement = Arrangement.End
                val mainAxisPositions = IntArray(childrenMainAxisSizes.size) { 0 }
                with(arrangement) {
                    arrange(
                        mainAxisLayoutSize, childrenMainAxisSizes,
                        layoutDirection, mainAxisPositions
                    )
                }
                placeables.forEachIndexed { j, placeable ->
                    placeable.place(
                        x = mainAxisPositions[j],
                        y = crossAxisPositions[i]
                    )
                }
            }
        }
    }
}

private val _dialogButtonsPadding by lazy { PaddingValues(bottom = 8.dp, end = 6.dp) }
private val _dialogButtonsMainAxisSpacing by lazy { 8.dp }
private val _dialogButtonsCrossAxisSpacing by lazy { 12.dp }

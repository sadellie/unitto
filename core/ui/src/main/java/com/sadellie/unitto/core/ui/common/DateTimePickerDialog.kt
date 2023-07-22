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

import android.content.res.Configuration
import android.text.format.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sadellie.unitto.core.base.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.math.max

@Composable
fun TimePickerDialog(
    modifier: Modifier = Modifier,
    hour: Int,
    minute: Int,
    confirmLabel: String = stringResource(R.string.ok_label),
    dismissLabel: String = stringResource(R.string.cancel_label),
    onDismiss: () -> Unit = {},
    onConfirm: (hour: Int, minute: Int) -> Unit,
) {
    val isVertical = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    val pickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = DateFormat.is24HourFormat(LocalContext.current)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier.wrapContentHeight(),
        properties = DialogProperties(usePlatformDefaultWidth = isVertical)
    ) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.select_time),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.align(Alignment.Start)
                )

                TimePicker(
                    state = pickerState,
                    modifier = Modifier.padding(top = 20.dp),
                    layoutType = if (isVertical) TimePickerLayoutType.Vertical else TimePickerLayoutType.Horizontal
                )

                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) {
                        Text(text = dismissLabel)
                    }
                    TextButton(
                        onClick = { onConfirm(pickerState.hour, pickerState.minute) }
                    ) {
                        Text(text = confirmLabel)
                    }
                }
            }
        }
    }
}

@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    localDateTime: LocalDateTime,
    confirmLabel: String = stringResource(R.string.ok_label),
    dismissLabel: String = stringResource(R.string.cancel_label),
    onDismiss: () -> Unit = {},
    onConfirm: (LocalDateTime) -> Unit,
) {
    val pickerState = rememberDatePickerState(localDateTime.toEpochSecond(ZoneOffset.UTC) * 1000)

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
                    .padding(DialogButtonsPadding)) {

                    AlertDialogFlowRow(
                        mainAxisSpacing = DialogButtonsMainAxisSpacing,
                        crossAxisSpacing = DialogButtonsCrossAxisSpacing
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

private val DialogButtonsPadding by lazy { PaddingValues(bottom = 8.dp, end = 6.dp) }
private val DialogButtonsMainAxisSpacing by lazy { 8.dp }
private val DialogButtonsCrossAxisSpacing by lazy { 12.dp }

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

package com.sadellie.unitto.feature.datedifference.components

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.common.squashable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun DateTimeSelectorBlock(
    modifier: Modifier,
    title: String,
    dateTime: LocalDateTime,
    onClick: () -> Unit,
    onTimeClick: () -> Unit,
    onDateClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .squashable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                cornerRadiusRange = 8.dp..32.dp
            )
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(title, style = MaterialTheme.typography.labelMedium)

        if (DateFormat.is24HourFormat(LocalContext.current)) {
            Text(
                modifier = Modifier.clickable(
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onTimeClick
                ),
                text = dateTime.format(time24Formatter),
                style = MaterialTheme.typography.displaySmall,
                maxLines = 1
            )
        } else {
            Column(
                modifier = Modifier.clickable(
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onTimeClick
                )
            ) {
                Text(
                    text = dateTime.format(time12Formatter),
                    style = MaterialTheme.typography.displaySmall,
                    maxLines = 1
                )
                Text(
                    text = dateTime.format(mTimeFormatter),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
            }
        }

        Text(
            modifier = Modifier.clickable(
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onDateClick
            ),
            text = dateTime.format(dateFormatter),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

private val time24Formatter by lazy { DateTimeFormatter.ofPattern("HH:mm") }
private val time12Formatter by lazy { DateTimeFormatter.ofPattern("hh:mm") }
private val dateFormatter by lazy { DateTimeFormatter.ofPattern("EEE, MMM d, y") }
private val mTimeFormatter by lazy { DateTimeFormatter.ofPattern("a") }

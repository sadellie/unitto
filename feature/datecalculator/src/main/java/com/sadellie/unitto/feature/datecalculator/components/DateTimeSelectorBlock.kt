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

package com.sadellie.unitto.feature.datecalculator.components

import android.text.format.DateFormat
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.LocalLocale
import com.sadellie.unitto.core.ui.common.squashable
import com.sadellie.unitto.core.ui.datetime.formatDateWeekDayMonthYear
import com.sadellie.unitto.core.ui.datetime.formatTime
import com.sadellie.unitto.core.ui.datetime.formatTime12Short
import com.sadellie.unitto.core.ui.datetime.formatTimeAmPm
import java.time.ZonedDateTime

@Composable
internal fun DateTimeSelectorBlock(
    modifier: Modifier = Modifier,
    title: String,
    dateTime: ZonedDateTime,
    onClick: () -> Unit = {},
    onTimeClick: () -> Unit = {},
    onDateClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    val locale = LocalLocale.current

    Column(
        modifier = modifier
            .squashable(
                onClick = onClick,
                onLongClick = onLongClick,
                interactionSource = remember { MutableInteractionSource() },
                cornerRadiusRange = 8.dp..32.dp
            )
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(title, style = MaterialTheme.typography.labelMedium)

        if (DateFormat.is24HourFormat(LocalContext.current)) {
            AnimatedContent(
                targetState = dateTime,
                transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() togetherWith
                            slideOutVertically { height -> -height } + fadeOut() using
                            SizeTransform()
                },
                label = "Animated 24 hour",
            ) { time ->
                Text(
                    modifier = Modifier.clickable(
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onTimeClick
                    ),
                    text = time.formatTime(locale, true),
                    style = MaterialTheme.typography.displaySmall,
                    maxLines = 1
                )
            }
        } else {
            Column(
                modifier = Modifier.clickable(
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onTimeClick
                )
            ) {
                AnimatedContent(
                    targetState = dateTime,
                    transitionSpec = {
                        slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut() using
                                SizeTransform()
                    },
                    label = "Animated 12 hour",
                ) { time ->
                    Text(
                        text = time.formatTime12Short(locale),
                        style = MaterialTheme.typography.displaySmall,
                        maxLines = 1
                    )
                }

                AnimatedContent(
                    targetState = dateTime,
                    transitionSpec = {
                        slideInVertically { height -> height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut() using
                                SizeTransform()
                    },
                    label = "Animated am/pm",
                ) { time ->
                    Text(
                        text = time.formatTimeAmPm(locale),
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1
                    )
                }
            }
        }

        AnimatedContent(
            targetState = dateTime,
            transitionSpec = {
                slideInVertically { height -> height } + fadeIn() togetherWith
                        slideOutVertically { height -> -height } + fadeOut() using
                        SizeTransform()
            },
            label = "Animated date",
        ) { date ->
            Text(
                modifier = Modifier.clickable(
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onDateClick
                ),
                text = date.formatDateWeekDayMonthYear(locale),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
fun DateTimeSelectorBlockPreview() {
    DateTimeSelectorBlock(
        title = "End",
        dateTime = ZonedDateTime.now(),
        modifier = Modifier.width(224.dp)
    )
}

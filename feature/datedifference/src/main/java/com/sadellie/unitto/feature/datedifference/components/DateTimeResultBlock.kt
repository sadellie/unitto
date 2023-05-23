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

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.squashable
import com.sadellie.unitto.feature.datedifference.DateDifference

@Composable
internal fun DateTimeResultBlock(
    modifier: Modifier = Modifier,
    dateDifference: DateDifference
) {
    val clipboardManager = LocalClipboardManager.current

    val years = dateDifference.years.formatDateTimeValue(R.string.date_difference_years)
    val months = dateDifference.months.formatDateTimeValue(R.string.date_difference_months)
    val days = dateDifference.days.formatDateTimeValue(R.string.date_difference_days)
    val hours = dateDifference.hours.formatDateTimeValue(R.string.date_difference_hours)
    val minutes = dateDifference.minutes.formatDateTimeValue(R.string.date_difference_minutes)

    val texts = listOf(years, months, days, hours, minutes)

    Column(
        modifier = modifier
            .squashable(
                onClick = {},
                interactionSource = remember { MutableInteractionSource() },
                cornerRadiusRange = 8.dp..32.dp,
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                stringResource(R.string.date_difference_result),
                style = MaterialTheme.typography.labelMedium
            )
            IconButton(
                onClick = {
                    clipboardManager.setText(
                        AnnotatedString(texts.filter { it.isNotEmpty() }.joinToString(" "))
                    )
                }
            ) {
                Icon(Icons.Default.ContentCopy, null)
            }
        }

        texts.forEach {
            AnimatedVisibility(
                visible = it.isNotEmpty(),
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Text(it, style = MaterialTheme.typography.displaySmall)
            }
        }
    }
}

@Composable
@ReadOnlyComposable
private fun Long.formatDateTimeValue(@StringRes id: Int): String {
    if (this <= 0) return ""

    return "${stringResource(id)}: $this"
}

@Preview
@Composable
private fun PreviewCard() {
    DateTimeResultBlock(
        modifier = Modifier,
        dateDifference = DateDifference.Default(
            months = 1,
            days = 2,
            hours = 3,
            minutes = 4
        )
    )
}

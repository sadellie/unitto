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

package com.sadellie.unitto.timezone.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.datetime.formatLocal
import com.sadellie.unitto.data.model.UnittoTimeZone
import java.time.ZonedDateTime

@Composable
fun SelectableTimeZone(
    modifier: Modifier = Modifier,
    timeZone: UnittoTimeZone,
    currentTime: ZonedDateTime?,
) {
    val offsetTime: ZonedDateTime? by remember(currentTime) {
        mutableStateOf(currentTime?.let { timeZone.offsetFrom(it) })
    }

    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(text = timeZone.nameRes)
        },
        supportingContent = {
            Text(text = timeZone.id)
        },
        trailingContent = {
            Text(text = offsetTime?.formatLocal() ?: "", style = MaterialTheme.typography.headlineSmall)
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewSelectableTimeZone() {
    SelectableTimeZone(
        timeZone = UnittoTimeZone(
            id = "text",
            nameRes = "Time zone"
        ),
        modifier = Modifier.width(440.dp),
        currentTime = ZonedDateTime.now(),
    )
}

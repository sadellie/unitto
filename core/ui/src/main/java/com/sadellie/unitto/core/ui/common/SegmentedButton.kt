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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

private val GroupRowContainerHeight = 40.dp
private val GroupRowItemMinWidth = 58.dp

@Composable
fun SegmentedButtonRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier
                .height(GroupRowContainerHeight)
                .widthIn(min = GroupRowItemMinWidth),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}

@Composable
fun RowScope.SegmentedButton(
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val containerColor =
        if (selected)
            MaterialTheme.colorScheme.secondaryContainer
        else
            MaterialTheme.colorScheme.surface
    OutlinedButton(
        modifier = modifier.weight(1f),
        onClick = onClick,
        shape = RectangleShape,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColorFor(containerColor)
        ),
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        AnimatedVisibility(visible = selected) {
            Row {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
            }
        }
        content()
    }
}

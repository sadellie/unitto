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

package com.sadellie.unitto.feature.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp

@Composable
internal fun ColorSelector(
    modifier: Modifier = Modifier,
    selected: Color,
    onItemClick: (Color) -> Unit,
    colorSchemes: List<Color>,
    defaultColor: Color
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Default, Unitto colors
        item(defaultColor.value.toLong()) {
            ColorCheckbox(
                color = defaultColor,
                selected = Color.Unspecified == selected,
                onClick = { onItemClick(Color.Unspecified) }
            )
        }

        colorSchemes.forEach {
            item(it.value.toLong()) {
                ColorCheckbox(
                    color = it,
                    selected = it == selected,
                    onClick = { onItemClick(it) }
                )
            }
        }
    }
}

@Composable
private fun ColorCheckbox(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(72.dp)
            .clip(RoundedCornerShape(25))
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(8.dp)
                .clip(CircleShape)
                .background(color)
                .border(1.dp, Color.Black.copy(0.5f), CircleShape),
        )
        AnimatedVisibility(
            visible = selected,
            enter = fadeIn(tween(250)) + scaleIn(tween(150)),
            exit = fadeOut(tween(250)) + scaleOut(tween(150)),
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (color.luminance() > 0.5) Color.Black else Color.White,
            )
        }
    }
}

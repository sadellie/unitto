/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2024 Elshan Agaev
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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val trackColor = animateColorAsState(targetValue = trackColor(enabled, checked, colors))
    val thumbColor = animateColorAsState(targetValue = thumbColor(enabled, checked, colors))
    val thumbSize = animateDpAsState(targetValue = if (checked) SelectedHandleSize else UnselectedHandleSize)
    val thumbOffset = animateDpAsState(targetValue = if (checked) ThumbPaddingEnd else ThumbPaddingStart)

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = true,
                onClickLabel = null,
                role = Role.Switch,
                onClick = { onCheckedChange(!checked) }
            )
            .background(trackColor.value, CircleShape)
            .size(TrackWidth, TrackHeight)
            .border(
                TrackOutlineWidth,
                borderColor(enabled, checked, colors),
                CircleShape
            )
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(x = thumbOffset.value.roundToPx(), y = 0) }
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(false, 16.dp),
                )
                .align(Alignment.CenterStart)
                .background(thumbColor.value, CircleShape)
                .size(thumbSize.value)
        )
    }
}

@Stable
private fun trackColor(
    enabled: Boolean,
    checked: Boolean,
    colors: SwitchColors
): Color =
    if (enabled) {
        if (checked) colors.checkedTrackColor else colors.uncheckedTrackColor
    } else {
        if (checked) colors.disabledCheckedTrackColor else colors.disabledUncheckedTrackColor
    }

@Stable
private fun thumbColor(
    enabled: Boolean,
    checked: Boolean,
    colors: SwitchColors
): Color =
    if (enabled) {
        if (checked) colors.checkedThumbColor else colors.uncheckedThumbColor
    } else {
        if (checked) colors.disabledCheckedThumbColor else colors.disabledUncheckedThumbColor
    }

@Stable
private fun borderColor(
    enabled: Boolean,
    checked: Boolean,
    colors: SwitchColors
): Color =
    if (enabled) {
        if (checked) colors.checkedBorderColor else colors.uncheckedBorderColor
    } else {
        if (checked) colors.disabledCheckedBorderColor else colors.disabledUncheckedBorderColor
    }

val TrackWidth = 56.0.dp
val TrackHeight = 28.0.dp
val TrackOutlineWidth = 1.8.dp
val SelectedHandleSize = 20.0.dp
val UnselectedHandleSize = 20.0.dp

val ThumbPaddingStart =  (TrackHeight - UnselectedHandleSize) / 2
val ThumbPaddingEnd = TrackWidth - SelectedHandleSize / 2 - TrackHeight / 2

@Preview
@Composable
fun PreviewPixelSwitch() {
    var checked by remember { mutableStateOf(false) }
    Switch(
        checked = checked,
        onCheckedChange = { checked = !checked }
    )
}

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

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp

fun Modifier.squashable(
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource,
    cornerRadiusRange: IntRange,
    role: Role = Role.Button,
) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val cornerRadius: Int by animateIntAsState(
        targetValue = if (isPressed) cornerRadiusRange.first else cornerRadiusRange.last,
        animationSpec = tween(easing = FastOutSlowInEasing),
        label = "Squashed animation"
    )

    Modifier
        .clip(RoundedCornerShape(cornerRadius))
        .combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick,
            interactionSource = interactionSource,
            indication = rememberRipple(),
            role = role,
            enabled = enabled
        )
}

fun Modifier.squashable(
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource,
    cornerRadiusRange: ClosedRange<Dp>,
    role: Role = Role.Button,
) = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val cornerRadius: Dp by animateDpAsState(
        targetValue = if (isPressed) cornerRadiusRange.start else cornerRadiusRange.endInclusive,
        animationSpec = tween(easing = FastOutSlowInEasing),
        label = "Squashed animation"
    )

    Modifier
        .clip(RoundedCornerShape(cornerRadius))
        .combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick,
            interactionSource = interactionSource,
            indication = rememberRipple(),
            role = role,
            enabled = enabled
        )
}

/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleTitleLarge

/**
 * Button for keyboard
 *
 * @param modifier Modifier that is applied to a [Button] component.
 * @param digit Symbol to show on button.
 * @param allowVibration When true will vibrate on button press.
 * @param isPrimary If true will use `inverseOnSurface` color, else `secondaryContainer`. Primary
 * buttons are digits.
 * @param onLongClick Action to perform when holding this button.
 * @param onClick Action to perform when clicking this button.
 */
@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    digit: String,
    allowVibration: Boolean,
    isPrimary: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: (String) -> Unit = {}
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cornerRadius: Int by animateIntAsState(
        targetValue = if (isPressed) 30 else 50,
        animationSpec = tween(easing = FastOutSlowInEasing),
    )

    UnittoButton(
        onClick = { onClick(digit) },
        onLongClick = onLongClick,
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        containerColor = if (isPrimary) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        contentPadding = PaddingValues(0.dp),
        interactionSource = interactionSource,
    ) {
        Text(
            text = digit,
            style = NumbersTextStyleTitleLarge,
            color = if (isPrimary) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

    LaunchedEffect(key1 = isPressed) {
        if (isPressed and allowVibration) view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }
}

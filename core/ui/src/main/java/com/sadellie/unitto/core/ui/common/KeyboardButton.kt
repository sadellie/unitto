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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleTitleLarge
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleTitleSmall

@Composable
fun BasicKeyboardButton(
    modifier: Modifier,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)?,
    containerColor: Color,
    contentColor: Color,
    text: String,
    textColor: Color,
    fontSize: TextUnit,
    allowVibration: Boolean
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cornerRadius: Int by animateIntAsState(
        targetValue = if (isPressed) 30 else 50,
        animationSpec = tween(easing = FastOutSlowInEasing),
    )

    UnittoButton(
        modifier = modifier,
        onClick = onClick,
        onLongClick = onLongClick,
        shape = RoundedCornerShape(cornerRadius),
        containerColor = containerColor,
        contentColor = contentColor,
        contentPadding = PaddingValues(0.dp),
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            style = NumbersTextStyleTitleLarge,
            color = textColor,
            fontSize = fontSize
        )
    }

    LaunchedEffect(key1 = isPressed) {
        if (isPressed and allowVibration) view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }
}

@Composable
fun KeyboardButtonLight(
    modifier: Modifier,
    symbol: String,
    onClick: (String) -> Unit,
    onLongClick: (() -> Unit)? = null,
    allowVibration: Boolean = false
) {
    BasicKeyboardButton(
        modifier = modifier,
        onClick = { onClick(symbol) },
        onLongClick = onLongClick,
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        text = symbol,
        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = TextUnit.Unspecified,
        allowVibration = allowVibration,
    )
}

@Composable
fun KeyboardButtonFilled(
    modifier: Modifier,
    symbol: String,
    onClick: (String) -> Unit,
    onLongClick: (() -> Unit)? = null,
    allowVibration: Boolean = false
) {
    BasicKeyboardButton(
        modifier = modifier,
        onClick = { onClick(symbol) },
        onLongClick = onLongClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        text = symbol,
        textColor = MaterialTheme.colorScheme.onSecondaryContainer,
        fontSize = TextUnit.Unspecified,
        allowVibration = allowVibration
    )
}

@Composable
fun KeyboardButtonAdditional(
    modifier: Modifier,
    symbol: String,
    onClick: (String) -> Unit
) {
    TextButton(
        onClick = { onClick(symbol) },
        modifier = modifier
    ) {
        Text(
            text = symbol,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = NumbersTextStyleTitleSmall
        )
    }
}

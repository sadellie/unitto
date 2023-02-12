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

package com.sadellie.unitto.feature.calculator.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.KEY_0
import com.sadellie.unitto.core.base.KEY_1
import com.sadellie.unitto.core.base.KEY_2
import com.sadellie.unitto.core.base.KEY_3
import com.sadellie.unitto.core.base.KEY_4
import com.sadellie.unitto.core.base.KEY_5
import com.sadellie.unitto.core.base.KEY_6
import com.sadellie.unitto.core.base.KEY_7
import com.sadellie.unitto.core.base.KEY_8
import com.sadellie.unitto.core.base.KEY_9
import com.sadellie.unitto.core.base.KEY_CLEAR
import com.sadellie.unitto.core.base.KEY_COS
import com.sadellie.unitto.core.base.KEY_DIVIDE_DISPLAY
import com.sadellie.unitto.core.base.KEY_DOT
import com.sadellie.unitto.core.base.KEY_EVALUATE
import com.sadellie.unitto.core.base.KEY_EXPONENT
import com.sadellie.unitto.core.base.KEY_E_SMALL
import com.sadellie.unitto.core.base.KEY_FACTORIAL
import com.sadellie.unitto.core.base.KEY_LEFT_BRACKET
import com.sadellie.unitto.core.base.KEY_LN
import com.sadellie.unitto.core.base.KEY_LOG
import com.sadellie.unitto.core.base.KEY_MINUS_DISPLAY
import com.sadellie.unitto.core.base.KEY_MODULO
import com.sadellie.unitto.core.base.KEY_MULTIPLY_DISPLAY
import com.sadellie.unitto.core.base.KEY_PERCENT
import com.sadellie.unitto.core.base.KEY_PI
import com.sadellie.unitto.core.base.KEY_PLUS
import com.sadellie.unitto.core.base.KEY_RIGHT_BRACKET
import com.sadellie.unitto.core.base.KEY_SIN
import com.sadellie.unitto.core.base.KEY_SQRT
import com.sadellie.unitto.core.base.KEY_TAN
import com.sadellie.unitto.core.ui.common.KeyboardButtonAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.feature.calculator.AngleMode

@Composable
internal fun CalculatorKeyboard(
    modifier: Modifier,
    addSymbol: (String) -> Unit,
    clearSymbols: () -> Unit,
    deleteSymbol: () -> Unit,
    toggleAngleMode: () -> Unit,
    angleMode: AngleMode,
    evaluate: () -> Unit
) {
    var showAdditional: Boolean by remember { mutableStateOf(false) }
    val expandRotation: Float by animateFloatAsState(
        targetValue = if (showAdditional) 0f else 180f,
        animationSpec = tween(easing = FastOutSlowInEasing)
    )

    Column(
        modifier = modifier
    ) {
        val weightModifier = Modifier.weight(1f)
        val mainButtonModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            // Additional buttons
            Column(modifier = weightModifier) {
                Row(Modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    KeyboardButtonAdditional(weightModifier, KEY_SQRT, onClick = addSymbol)
                    KeyboardButtonAdditional(weightModifier, KEY_PI, onClick = addSymbol)
                    KeyboardButtonAdditional(weightModifier, KEY_EXPONENT, onClick = addSymbol)
                    KeyboardButtonAdditional(weightModifier, KEY_FACTORIAL, onClick = addSymbol)
                }
                AnimatedVisibility(visible = showAdditional) {
                    Column {
                        Row(Modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            KeyboardButtonAdditional(weightModifier, angleMode.name, onClick = { toggleAngleMode() })
                            KeyboardButtonAdditional(weightModifier, KEY_SIN, onClick = addSymbol)
                            KeyboardButtonAdditional(weightModifier, KEY_COS, onClick = addSymbol)
                            KeyboardButtonAdditional(weightModifier, KEY_TAN, onClick = addSymbol)
                        }
                        Row(Modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            KeyboardButtonAdditional(weightModifier, KEY_MODULO, onClick = addSymbol)
                            KeyboardButtonAdditional(weightModifier, KEY_E_SMALL, onClick = addSymbol)
                            KeyboardButtonAdditional(weightModifier, KEY_LN, onClick = addSymbol)
                            KeyboardButtonAdditional(weightModifier, KEY_LOG, onClick = addSymbol)
                        }
                    }
                }
            }
            // Expand/Collapse
            IconButton(
                onClick = { showAdditional = !showAdditional },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface,)
            ) {
                Icon(Icons.Default.ExpandLess, null, Modifier.rotate(expandRotation))
            }
        }

        Row(weightModifier) {
            KeyboardButtonFilled(mainButtonModifier, KEY_LEFT_BRACKET, addSymbol)
            KeyboardButtonFilled(mainButtonModifier, KEY_RIGHT_BRACKET, addSymbol)
            KeyboardButtonFilled(mainButtonModifier, KEY_PERCENT, addSymbol)
            KeyboardButtonFilled(mainButtonModifier, KEY_DIVIDE_DISPLAY, addSymbol)
        }

        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, KEY_7, addSymbol)
            KeyboardButtonLight(mainButtonModifier, KEY_8, addSymbol)
            KeyboardButtonLight(mainButtonModifier, KEY_9, addSymbol)
            KeyboardButtonFilled(mainButtonModifier, KEY_MULTIPLY_DISPLAY, addSymbol)
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, KEY_4, addSymbol)
            KeyboardButtonLight(mainButtonModifier, KEY_5, addSymbol)
            KeyboardButtonLight(mainButtonModifier, KEY_6, addSymbol)
            KeyboardButtonFilled(mainButtonModifier, KEY_MINUS_DISPLAY, addSymbol)
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, KEY_1, addSymbol)
            KeyboardButtonLight(mainButtonModifier, KEY_2, addSymbol)
            KeyboardButtonLight(mainButtonModifier, KEY_3, addSymbol)
            KeyboardButtonFilled(mainButtonModifier, KEY_PLUS, addSymbol)
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, KEY_0, addSymbol)
            KeyboardButtonLight(mainButtonModifier, KEY_DOT, addSymbol)
            KeyboardButtonLight(mainButtonModifier, KEY_CLEAR, { deleteSymbol() }, onLongClick = clearSymbols)
            KeyboardButtonFilled(mainButtonModifier, KEY_EVALUATE, { evaluate() })
        }
    }
}

@Preview
@Composable
private fun PreviewCalculatorKeyboard() {
    CalculatorKeyboard(
        modifier = Modifier,
        addSymbol = {},
        clearSymbols = {},
        deleteSymbol = {},
        toggleAngleMode = {},
        angleMode = AngleMode.DEG,
        evaluate = {}
    )
}

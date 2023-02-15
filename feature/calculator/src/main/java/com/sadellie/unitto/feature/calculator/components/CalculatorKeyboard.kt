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
import com.sadellie.unitto.core.base.KEY_COS
import com.sadellie.unitto.core.base.KEY_COS_DISPLAY
import com.sadellie.unitto.core.base.KEY_DIVIDE_DISPLAY
import com.sadellie.unitto.core.base.KEY_DOT
import com.sadellie.unitto.core.base.KEY_EXPONENT
import com.sadellie.unitto.core.base.KEY_E_SMALL
import com.sadellie.unitto.core.base.KEY_FACTORIAL
import com.sadellie.unitto.core.base.KEY_LEFT_BRACKET
import com.sadellie.unitto.core.base.KEY_LN
import com.sadellie.unitto.core.base.KEY_LN_DISPLAY
import com.sadellie.unitto.core.base.KEY_LOG
import com.sadellie.unitto.core.base.KEY_LOG_DISPLAY
import com.sadellie.unitto.core.base.KEY_MINUS_DISPLAY
import com.sadellie.unitto.core.base.KEY_MODULO
import com.sadellie.unitto.core.base.KEY_MULTIPLY_DISPLAY
import com.sadellie.unitto.core.base.KEY_PERCENT
import com.sadellie.unitto.core.base.KEY_PI
import com.sadellie.unitto.core.base.KEY_PLUS
import com.sadellie.unitto.core.base.KEY_RIGHT_BRACKET
import com.sadellie.unitto.core.base.KEY_SIN
import com.sadellie.unitto.core.base.KEY_SIN_DISPLAY
import com.sadellie.unitto.core.base.KEY_SQRT
import com.sadellie.unitto.core.base.KEY_TAN
import com.sadellie.unitto.core.base.KEY_TAN_DISPLAY
import com.sadellie.unitto.core.ui.common.KeyboardButtonAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.Delete
import com.sadellie.unitto.core.ui.common.key.unittoicons.Divide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Dot
import com.sadellie.unitto.core.ui.common.key.unittoicons.Equal
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key0
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key1
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key2
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key3
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key4
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key5
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key6
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key7
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key8
import com.sadellie.unitto.core.ui.common.key.unittoicons.Key9
import com.sadellie.unitto.core.ui.common.key.unittoicons.LeftBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.Minus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Multiply
import com.sadellie.unitto.core.ui.common.key.unittoicons.Percent
import com.sadellie.unitto.core.ui.common.key.unittoicons.Plus
import com.sadellie.unitto.core.ui.common.key.unittoicons.RightBracket
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
                            KeyboardButtonAdditional(weightModifier, KEY_SIN_DISPLAY, onClick = { addSymbol(KEY_SIN) })
                            KeyboardButtonAdditional(weightModifier, KEY_COS_DISPLAY, onClick = { addSymbol(KEY_COS) })
                            KeyboardButtonAdditional(weightModifier, KEY_TAN_DISPLAY, onClick = { addSymbol(KEY_TAN) })
                        }
                        Row(Modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            KeyboardButtonAdditional(weightModifier, KEY_MODULO, onClick = addSymbol)
                            KeyboardButtonAdditional(weightModifier, KEY_E_SMALL, onClick = addSymbol)
                            KeyboardButtonAdditional(weightModifier, KEY_LN_DISPLAY, onClick = { addSymbol(KEY_LN) })
                            KeyboardButtonAdditional(weightModifier, KEY_LOG_DISPLAY, onClick = { addSymbol(KEY_LOG) })
                        }
                    }
                }
            }
            // Expand/Collapse
            IconButton(
                onClick = { showAdditional = !showAdditional },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Icon(Icons.Default.ExpandLess, null, Modifier.rotate(expandRotation))
            }
        }

        Row(weightModifier) {
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.LeftBracket, { addSymbol(KEY_LEFT_BRACKET) })
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.RightBracket, { addSymbol(KEY_RIGHT_BRACKET) })
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Percent, { addSymbol(KEY_PERCENT) })
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Divide, { addSymbol(KEY_DIVIDE_DISPLAY) })
        }

        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key7, { addSymbol(KEY_7) })
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key8, { addSymbol(KEY_8) })
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key9, { addSymbol(KEY_9) })
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Multiply, { addSymbol(KEY_MULTIPLY_DISPLAY) })
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key4, { addSymbol(KEY_4) })
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key5, { addSymbol(KEY_5) })
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key6, { addSymbol(KEY_6) })
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Minus, { addSymbol(KEY_MINUS_DISPLAY) })
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key1, { addSymbol(KEY_1) })
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key2, { addSymbol(KEY_2) })
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key3, { addSymbol(KEY_3) })
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Plus, { addSymbol(KEY_PLUS) })
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key0, { addSymbol(KEY_0) })
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Dot, { addSymbol(KEY_DOT) })
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Delete, { deleteSymbol() }, onLongClick = clearSymbols)
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Equal, { evaluate() })
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

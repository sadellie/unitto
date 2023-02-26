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

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
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
import com.sadellie.unitto.core.base.KEY_DIVIDE_DISPLAY
import com.sadellie.unitto.core.base.KEY_DOT
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
import com.sadellie.unitto.core.ui.Formatter
import com.sadellie.unitto.core.ui.common.KeyboardButtonAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.Comma
import com.sadellie.unitto.core.ui.common.key.unittoicons.Cos
import com.sadellie.unitto.core.ui.common.key.unittoicons.Deg
import com.sadellie.unitto.core.ui.common.key.unittoicons.Delete
import com.sadellie.unitto.core.ui.common.key.unittoicons.Divide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Dot
import com.sadellie.unitto.core.ui.common.key.unittoicons.E
import com.sadellie.unitto.core.ui.common.key.unittoicons.Equal
import com.sadellie.unitto.core.ui.common.key.unittoicons.Exponent
import com.sadellie.unitto.core.ui.common.key.unittoicons.Factorial
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
import com.sadellie.unitto.core.ui.common.key.unittoicons.Ln
import com.sadellie.unitto.core.ui.common.key.unittoicons.Log
import com.sadellie.unitto.core.ui.common.key.unittoicons.Minus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Modulo
import com.sadellie.unitto.core.ui.common.key.unittoicons.Multiply
import com.sadellie.unitto.core.ui.common.key.unittoicons.Percent
import com.sadellie.unitto.core.ui.common.key.unittoicons.Pi
import com.sadellie.unitto.core.ui.common.key.unittoicons.Plus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Rad
import com.sadellie.unitto.core.ui.common.key.unittoicons.RightBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.Sin
import com.sadellie.unitto.core.ui.common.key.unittoicons.SquareRoot
import com.sadellie.unitto.core.ui.common.key.unittoicons.Tan
import com.sadellie.unitto.feature.calculator.AngleMode

@Composable
internal fun CalculatorKeyboard(
    modifier: Modifier,
    angleMode: AngleMode,
    allowVibration: Boolean,
    addSymbol: (String) -> Unit,
    clearSymbols: () -> Unit,
    deleteSymbol: () -> Unit,
    toggleAngleMode: () -> Unit,
    evaluate: () -> Unit
) {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitKeyboard(
            modifier = modifier,
            angleMode = angleMode,
            allowVibration = allowVibration,
            addSymbol = addSymbol,
            toggleAngleMode = toggleAngleMode,
            deleteSymbol = deleteSymbol,
            clearSymbols = clearSymbols,
            evaluate = evaluate
        )
    } else {
        LandscapeKeyboard(
            modifier = modifier,
            angleMode = angleMode,
            allowVibration = allowVibration,
            addSymbol = addSymbol,
            toggleAngleMode = toggleAngleMode,
            deleteSymbol = deleteSymbol,
            clearSymbols = clearSymbols,
            evaluate = evaluate
        )
    }
}

@Composable
private fun PortraitKeyboard(
    modifier: Modifier,
    angleMode: AngleMode,
    allowVibration: Boolean,
    addSymbol: (String) -> Unit,
    toggleAngleMode: () -> Unit,
    deleteSymbol: () -> Unit,
    clearSymbols: () -> Unit,
    evaluate: () -> Unit
) {
    val fractionalIcon = remember { if (Formatter.fractional == KEY_DOT) UnittoIcons.Dot else UnittoIcons.Comma }
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
        val additionalButtonModifier = Modifier
            .minimumInteractiveComponentSize()
            .weight(1f)
            .heightIn(max = 48.dp)

        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Additional buttons
            Column(modifier = weightModifier) {
                Row(Modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.SquareRoot, allowVibration) { addSymbol(KEY_SQRT) }
                    KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Pi, allowVibration) { addSymbol(KEY_PI) }
                    KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Exponent, allowVibration) { addSymbol(KEY_EXPONENT) }
                    KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Factorial, allowVibration) { addSymbol(KEY_FACTORIAL) }
                }
                AnimatedVisibility(visible = showAdditional) {
                    Column {
                        Row(Modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            KeyboardButtonAdditional(additionalButtonModifier, if (angleMode == AngleMode.DEG) UnittoIcons.Deg else UnittoIcons.Rad, allowVibration) { toggleAngleMode() }
                            KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Sin, allowVibration) { addSymbol(KEY_SIN) }
                            KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Cos, allowVibration) { addSymbol(KEY_COS) }
                            KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Tan, allowVibration) { addSymbol(KEY_TAN) }
                        }
                        Row(Modifier, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                            KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Modulo, allowVibration) { addSymbol(KEY_MODULO) }
                            KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.E, allowVibration) { addSymbol(KEY_E_SMALL) }
                            KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Ln, allowVibration) { addSymbol(KEY_LN) }
                            KeyboardButtonAdditional(additionalButtonModifier, UnittoIcons.Log, allowVibration) { addSymbol(KEY_LOG) }
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
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.LeftBracket, allowVibration) { addSymbol(KEY_LEFT_BRACKET) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.RightBracket, allowVibration) { addSymbol(KEY_RIGHT_BRACKET) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Percent, allowVibration) { addSymbol(KEY_PERCENT) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Divide, allowVibration) { addSymbol(KEY_DIVIDE_DISPLAY) }
        }

        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key7, allowVibration) { addSymbol(KEY_7) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key8, allowVibration) { addSymbol(KEY_8) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key9, allowVibration) { addSymbol(KEY_9) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Multiply, allowVibration) { addSymbol(KEY_MULTIPLY_DISPLAY) }
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key4, allowVibration) { addSymbol(KEY_4) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key5, allowVibration) { addSymbol(KEY_5) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key6, allowVibration) { addSymbol(KEY_6) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Minus, allowVibration) { addSymbol(KEY_MINUS_DISPLAY) }
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key1, allowVibration) { addSymbol(KEY_1) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key2, allowVibration) { addSymbol(KEY_2) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key3, allowVibration) { addSymbol(KEY_3) }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Plus, allowVibration) { addSymbol(KEY_PLUS) }
        }
        Row(weightModifier) {
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Key0, allowVibration) { addSymbol(KEY_0) }
            KeyboardButtonLight(mainButtonModifier, fractionalIcon, allowVibration) { addSymbol(KEY_DOT) }
            KeyboardButtonLight(mainButtonModifier, UnittoIcons.Delete, allowVibration, clearSymbols) { deleteSymbol() }
            KeyboardButtonFilled(mainButtonModifier, UnittoIcons.Equal, allowVibration) { evaluate() }
        }
    }
}

@Composable
private fun LandscapeKeyboard(
    modifier: Modifier,
    angleMode: AngleMode,
    allowVibration: Boolean,
    addSymbol: (String) -> Unit,
    toggleAngleMode: () -> Unit,
    deleteSymbol: () -> Unit,
    clearSymbols: () -> Unit,
    evaluate: () -> Unit
) {
    val fractionalIcon = remember { if (Formatter.fractional == KEY_DOT) UnittoIcons.Dot else UnittoIcons.Comma }

    Column(modifier = modifier) {
        val buttonModifier = Modifier.weight(1f).padding(2.dp)

        Row(Modifier.weight(1f)) {
            KeyboardButtonAdditional(buttonModifier, if (angleMode == AngleMode.DEG) UnittoIcons.Deg else UnittoIcons.Rad, allowVibration) { toggleAngleMode() }
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.SquareRoot, allowVibration) { addSymbol(KEY_SQRT) }
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Pi, allowVibration) { addSymbol(KEY_PI) }

            KeyboardButtonLight(buttonModifier, UnittoIcons.Key7, allowVibration) { addSymbol(KEY_7) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key8, allowVibration) { addSymbol(KEY_8) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key9, allowVibration) { addSymbol(KEY_9) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.LeftBracket, allowVibration) { addSymbol(KEY_LEFT_BRACKET) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.RightBracket, allowVibration) { addSymbol(KEY_RIGHT_BRACKET) }
        }
        Row(Modifier.weight(1f)) {
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Modulo, allowVibration) { addSymbol(KEY_MODULO) }
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Exponent, allowVibration) { addSymbol(KEY_EXPONENT) }
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Factorial, allowVibration) { addSymbol(KEY_FACTORIAL) }

            KeyboardButtonLight(buttonModifier, UnittoIcons.Key4, allowVibration) { addSymbol(KEY_4) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key5, allowVibration) { addSymbol(KEY_5) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key6, allowVibration) { addSymbol(KEY_6) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Multiply, allowVibration) { addSymbol(KEY_MULTIPLY_DISPLAY) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Divide, allowVibration) { addSymbol(KEY_DIVIDE_DISPLAY) }
        }
        Row(Modifier.weight(1f)) {
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Sin, allowVibration) { addSymbol(KEY_SIN) }
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Cos, allowVibration) { addSymbol(KEY_COS) }
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Tan, allowVibration) { addSymbol(KEY_TAN) }

            KeyboardButtonLight(buttonModifier, UnittoIcons.Key1, allowVibration) { addSymbol(KEY_1) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key2, allowVibration) { addSymbol(KEY_2) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key3, allowVibration) { addSymbol(KEY_3) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Minus, allowVibration) { addSymbol(KEY_MINUS_DISPLAY) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Percent, allowVibration) { addSymbol(KEY_PERCENT) }
        }
        Row(Modifier.weight(1f)) {
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.E, allowVibration) { addSymbol(KEY_E_SMALL) }
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Ln, allowVibration) { addSymbol(KEY_LN) }
            KeyboardButtonAdditional(buttonModifier, UnittoIcons.Log, allowVibration) { addSymbol(KEY_LOG) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Key0, allowVibration) { addSymbol(KEY_0) }
            KeyboardButtonLight(buttonModifier, fractionalIcon, allowVibration) { addSymbol(KEY_DOT) }
            KeyboardButtonLight(buttonModifier, UnittoIcons.Delete, allowVibration, clearSymbols) { deleteSymbol() }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Plus, allowVibration) { addSymbol(KEY_PLUS) }
            KeyboardButtonFilled(buttonModifier, UnittoIcons.Equal, allowVibration) { evaluate() }
        }
    }
}

@Preview
@Composable
private fun PreviewCalculatorKeyboard() {
    CalculatorKeyboard(
        modifier = Modifier,
        angleMode = AngleMode.DEG,
        addSymbol = {},
        clearSymbols = {},
        deleteSymbol = {},
        toggleAngleMode = {},
        evaluate = {},
        allowVibration = false
    )
}

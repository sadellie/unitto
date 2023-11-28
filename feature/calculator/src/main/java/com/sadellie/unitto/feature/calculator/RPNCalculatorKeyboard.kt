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

package com.sadellie.unitto.feature.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import com.sadellie.unitto.core.ui.common.KeyboardButtonAdditional
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightShort
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightTall
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightWide
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.KeyboardButtonTertiary
import com.sadellie.unitto.core.ui.common.KeypadFlow
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.Backspace
import com.sadellie.unitto.core.ui.common.key.unittoicons.Clear
import com.sadellie.unitto.core.ui.common.key.unittoicons.Comma
import com.sadellie.unitto.core.ui.common.key.unittoicons.Divide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Dot
import com.sadellie.unitto.core.ui.common.key.unittoicons.Down
import com.sadellie.unitto.core.ui.common.key.unittoicons.Enter
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
import com.sadellie.unitto.core.ui.common.key.unittoicons.Minus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Multiply
import com.sadellie.unitto.core.ui.common.key.unittoicons.Percent
import com.sadellie.unitto.core.ui.common.key.unittoicons.Plus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Pop
import com.sadellie.unitto.core.ui.common.key.unittoicons.Swap
import com.sadellie.unitto.core.ui.common.key.unittoicons.Unary
import com.sadellie.unitto.core.ui.common.key.unittoicons.Up
import io.github.sadellie.evaluatto.RPNCalculation

@Composable
internal fun RPNCalculatorKeyboard(
    modifier: Modifier,
    fractional: String,
    middleZero: Boolean,
    allowVibration: Boolean,
    onCalculationClick: (RPNCalculation) -> Unit,
    onInputEditClick: (RPNInputEdit) -> Unit,
) {
    val fractionalIcon = remember(fractional) { if (fractional == Token.Digit.dot) UnittoIcons.Dot else UnittoIcons.Comma }

    if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) {
        RPNCalculatorKeyboardLandscape(
            modifier = modifier,
            fractionalIcon = fractionalIcon,
            middleZero = middleZero,
            allowVibration = allowVibration,
            onCalculationClick = onCalculationClick,
            onInputEditClick = onInputEditClick
        )
    } else {
        RPNCalculatorKeyboardPortrait(
            modifier = modifier,
            fractionalIcon = fractionalIcon,
            middleZero = middleZero,
            allowVibration = allowVibration,
            onCalculationClick = onCalculationClick,
            onInputEditClick = onInputEditClick
        )
    }
}

@Composable
private fun RPNCalculatorKeyboardPortrait(
    modifier: Modifier,
    fractionalIcon: ImageVector,
    middleZero: Boolean,
    allowVibration: Boolean,
    onCalculationClick: (RPNCalculation) -> Unit,
    onInputEditClick: (RPNInputEdit) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        KeypadFlow(
            modifier = Modifier.fillMaxHeight(0.1f).fillMaxWidth(),
            rows = 1,
            columns = 4
        ) { width, height ->
            val aModifier = Modifier
                .fillMaxWidth(width)
                .fillMaxHeight(height)

            KeyboardButtonAdditional(aModifier, UnittoIcons.Swap, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.Swap) }
            KeyboardButtonAdditional(aModifier, UnittoIcons.Up, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.RotateUp) }
            KeyboardButtonAdditional(aModifier, UnittoIcons.Down, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.RotateDown) }
            KeyboardButtonAdditional(aModifier, UnittoIcons.Pop, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.Pop) }
        }

        KeypadFlow(
            modifier = Modifier.weight(1f).fillMaxSize(),
            rows = 5,
            columns = 4
        ) { width, height ->
            val bModifier = Modifier
                .fillMaxWidth(width)
                .fillMaxHeight(height)

            KeyboardButtonTertiary(bModifier, UnittoIcons.Clear, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Clear) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Unary, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Negate) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Percent, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Percent) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Divide, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Divide) }

            KeyboardButtonLight(bModifier, UnittoIcons.Key7, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._7)) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key8, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._8)) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key9, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._9)) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Multiply, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Multiply) }

            KeyboardButtonLight(bModifier, UnittoIcons.Key4, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._4)) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key5, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._5)) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key6, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._6)) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Minus, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Minus) }

            KeyboardButtonLight(bModifier, UnittoIcons.Key1, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._1)) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key2, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._2)) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key3, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._3)) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Plus, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Plus) }

            if (middleZero) {
                KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Dot) }
                KeyboardButtonLight(bModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
            } else {
                KeyboardButtonLight(bModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
                KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Dot) }
            }
            KeyboardButtonLight(bModifier, UnittoIcons.Backspace, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Delete) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Enter, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Enter) }
        }
    }
}

@Composable
private fun RPNCalculatorKeyboardLandscape(
    modifier: Modifier,
    fractionalIcon: ImageVector,
    middleZero: Boolean,
    allowVibration: Boolean,
    onCalculationClick: (RPNCalculation) -> Unit,
    onInputEditClick: (RPNInputEdit) -> Unit,
) {
    KeypadFlow(
        modifier = modifier,
        rows = 4,
        columns = 6
    ) { width, height ->
        val bModifier = Modifier
            .fillMaxHeight(height)
            .fillMaxWidth(width)

        KeyboardButtonAdditional(bModifier, UnittoIcons.Swap, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.Swap) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key7, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._7)) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key8, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._8)) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key9, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._9)) }
        KeyboardButtonTertiary(bModifier, UnittoIcons.Clear, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Clear) }
        KeyboardButtonFilled(bModifier, UnittoIcons.Unary, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Negate) }

        KeyboardButtonAdditional(bModifier, UnittoIcons.Up, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.RotateUp) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key4, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._4)) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key5, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._5)) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key6, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._6)) }
        KeyboardButtonFilled(bModifier, UnittoIcons.Multiply, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Multiply) }
        KeyboardButtonFilled(bModifier, UnittoIcons.Divide, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Divide) }

        KeyboardButtonAdditional(bModifier, UnittoIcons.Down, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.RotateDown) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key1, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._1)) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key2, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._2)) }
        KeyboardButtonLight(bModifier, UnittoIcons.Key3, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._3)) }
        KeyboardButtonFilled(bModifier, UnittoIcons.Plus, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Plus) }
        KeyboardButtonFilled(bModifier, UnittoIcons.Minus, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Minus) }

        KeyboardButtonAdditional(bModifier, UnittoIcons.Pop, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.Pop) }
        if (middleZero) {
            KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Dot) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
        } else {
            KeyboardButtonLight(bModifier, UnittoIcons.Key0, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
            KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Dot) }
        }
        KeyboardButtonLight(bModifier, UnittoIcons.Backspace, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Delete) }
        KeyboardButtonFilled(bModifier, UnittoIcons.Percent, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Percent) }
        KeyboardButtonFilled(bModifier, UnittoIcons.Enter, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Enter) }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=portrait")
@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun PreviewKeyboard() {
    RPNCalculatorKeyboard(
        modifier = Modifier.fillMaxSize(),
        fractional = Token.Digit.dot,
        middleZero = false,
        allowVibration = false,
        onCalculationClick = {},
        onInputEditClick = {}
    )
}

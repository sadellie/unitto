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
import com.sadellie.unitto.core.ui.common.icons.IconPack
import com.sadellie.unitto.core.ui.common.icons.iconpack.Backspace
import com.sadellie.unitto.core.ui.common.icons.iconpack.Clear
import com.sadellie.unitto.core.ui.common.icons.iconpack.Comma
import com.sadellie.unitto.core.ui.common.icons.iconpack.Divide
import com.sadellie.unitto.core.ui.common.icons.iconpack.Dot
import com.sadellie.unitto.core.ui.common.icons.iconpack.Down
import com.sadellie.unitto.core.ui.common.icons.iconpack.Enter
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key0
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key1
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key2
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key3
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key4
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key5
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key6
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key7
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key8
import com.sadellie.unitto.core.ui.common.icons.iconpack.Key9
import com.sadellie.unitto.core.ui.common.icons.iconpack.Minus
import com.sadellie.unitto.core.ui.common.icons.iconpack.Multiply
import com.sadellie.unitto.core.ui.common.icons.iconpack.Percent
import com.sadellie.unitto.core.ui.common.icons.iconpack.Plus
import com.sadellie.unitto.core.ui.common.icons.iconpack.Pop
import com.sadellie.unitto.core.ui.common.icons.iconpack.Swap
import com.sadellie.unitto.core.ui.common.icons.iconpack.Unary
import com.sadellie.unitto.core.ui.common.icons.iconpack.Up
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
    val fractionalIcon = remember(fractional) { if (fractional == Token.Digit.dot) IconPack.Dot else IconPack.Comma }

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

            KeyboardButtonAdditional(aModifier, IconPack.Swap, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.Swap) }
            KeyboardButtonAdditional(aModifier, IconPack.Up, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.RotateUp) }
            KeyboardButtonAdditional(aModifier, IconPack.Down, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.RotateDown) }
            KeyboardButtonAdditional(aModifier, IconPack.Pop, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.Pop) }
        }

        KeypadFlow(
            modifier = Modifier.weight(1f).fillMaxSize(),
            rows = 5,
            columns = 4
        ) { width, height ->
            val bModifier = Modifier
                .fillMaxWidth(width)
                .fillMaxHeight(height)

            KeyboardButtonTertiary(bModifier, IconPack.Clear, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Clear) }
            KeyboardButtonFilled(bModifier, IconPack.Unary, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Negate) }
            KeyboardButtonFilled(bModifier, IconPack.Percent, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Percent) }
            KeyboardButtonFilled(bModifier, IconPack.Divide, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Divide) }

            KeyboardButtonLight(bModifier, IconPack.Key7, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._7)) }
            KeyboardButtonLight(bModifier, IconPack.Key8, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._8)) }
            KeyboardButtonLight(bModifier, IconPack.Key9, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._9)) }
            KeyboardButtonFilled(bModifier, IconPack.Multiply, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Multiply) }

            KeyboardButtonLight(bModifier, IconPack.Key4, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._4)) }
            KeyboardButtonLight(bModifier, IconPack.Key5, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._5)) }
            KeyboardButtonLight(bModifier, IconPack.Key6, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._6)) }
            KeyboardButtonFilled(bModifier, IconPack.Minus, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Minus) }

            KeyboardButtonLight(bModifier, IconPack.Key1, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._1)) }
            KeyboardButtonLight(bModifier, IconPack.Key2, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._2)) }
            KeyboardButtonLight(bModifier, IconPack.Key3, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._3)) }
            KeyboardButtonFilled(bModifier, IconPack.Plus, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Plus) }

            if (middleZero) {
                KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Dot) }
                KeyboardButtonLight(bModifier, IconPack.Key0, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
            } else {
                KeyboardButtonLight(bModifier, IconPack.Key0, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
                KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Dot) }
            }
            KeyboardButtonLight(bModifier, IconPack.Backspace, allowVibration, KeyboardButtonContentHeightTall) { onInputEditClick(RPNInputEdit.Delete) }
            KeyboardButtonFilled(bModifier, IconPack.Enter, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Enter) }
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

        KeyboardButtonAdditional(bModifier, IconPack.Swap, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.Swap) }
        KeyboardButtonLight(bModifier, IconPack.Key7, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._7)) }
        KeyboardButtonLight(bModifier, IconPack.Key8, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._8)) }
        KeyboardButtonLight(bModifier, IconPack.Key9, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._9)) }
        KeyboardButtonTertiary(bModifier, IconPack.Clear, allowVibration, KeyboardButtonContentHeightTall) { onCalculationClick(RPNCalculation.Clear) }
        KeyboardButtonFilled(bModifier, IconPack.Unary, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Negate) }

        KeyboardButtonAdditional(bModifier, IconPack.Up, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.RotateUp) }
        KeyboardButtonLight(bModifier, IconPack.Key4, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._4)) }
        KeyboardButtonLight(bModifier, IconPack.Key5, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._5)) }
        KeyboardButtonLight(bModifier, IconPack.Key6, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._6)) }
        KeyboardButtonFilled(bModifier, IconPack.Multiply, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Multiply) }
        KeyboardButtonFilled(bModifier, IconPack.Divide, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Divide) }

        KeyboardButtonAdditional(bModifier, IconPack.Down, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.RotateDown) }
        KeyboardButtonLight(bModifier, IconPack.Key1, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._1)) }
        KeyboardButtonLight(bModifier, IconPack.Key2, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._2)) }
        KeyboardButtonLight(bModifier, IconPack.Key3, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._3)) }
        KeyboardButtonFilled(bModifier, IconPack.Plus, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Plus) }
        KeyboardButtonFilled(bModifier, IconPack.Minus, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Minus) }

        KeyboardButtonAdditional(bModifier, IconPack.Pop, allowVibration, KeyboardButtonContentHeightWide) { onCalculationClick(RPNCalculation.Pop) }
        if (middleZero) {
            KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Dot) }
            KeyboardButtonLight(bModifier, IconPack.Key0, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
        } else {
            KeyboardButtonLight(bModifier, IconPack.Key0, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Digit(Token.Digit._0)) }
            KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Dot) }
        }
        KeyboardButtonLight(bModifier, IconPack.Backspace, allowVibration, KeyboardButtonContentHeightShort) { onInputEditClick(RPNInputEdit.Delete) }
        KeyboardButtonFilled(bModifier, IconPack.Percent, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Percent) }
        KeyboardButtonFilled(bModifier, IconPack.Enter, allowVibration, KeyboardButtonContentHeightShort) { onCalculationClick(RPNCalculation.Enter) }
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

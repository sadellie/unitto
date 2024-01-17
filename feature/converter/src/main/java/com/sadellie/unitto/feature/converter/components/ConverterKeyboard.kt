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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.LocalWindowSize
import com.sadellie.unitto.core.ui.WindowHeightSizeClass
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightShort
import com.sadellie.unitto.core.ui.common.KeyboardButtonContentHeightTall
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.KeyboardButtonTertiary
import com.sadellie.unitto.core.ui.common.KeypadFlow
import com.sadellie.unitto.core.ui.common.icons.IconPack
import com.sadellie.unitto.core.ui.common.icons.iconpack.Backspace
import com.sadellie.unitto.core.ui.common.icons.iconpack.Brackets
import com.sadellie.unitto.core.ui.common.icons.iconpack.Clear
import com.sadellie.unitto.core.ui.common.icons.iconpack.Comma
import com.sadellie.unitto.core.ui.common.icons.iconpack.Divide
import com.sadellie.unitto.core.ui.common.icons.iconpack.Dot
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
import com.sadellie.unitto.core.ui.common.icons.iconpack.KeyA
import com.sadellie.unitto.core.ui.common.icons.iconpack.KeyB
import com.sadellie.unitto.core.ui.common.icons.iconpack.KeyC
import com.sadellie.unitto.core.ui.common.icons.iconpack.KeyD
import com.sadellie.unitto.core.ui.common.icons.iconpack.KeyE
import com.sadellie.unitto.core.ui.common.icons.iconpack.KeyF
import com.sadellie.unitto.core.ui.common.icons.iconpack.LeftBracket
import com.sadellie.unitto.core.ui.common.icons.iconpack.Minus
import com.sadellie.unitto.core.ui.common.icons.iconpack.Multiply
import com.sadellie.unitto.core.ui.common.icons.iconpack.Plus
import com.sadellie.unitto.core.ui.common.icons.iconpack.Power
import com.sadellie.unitto.core.ui.common.icons.iconpack.RightBracket
import com.sadellie.unitto.core.ui.common.icons.iconpack.Root
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols

@Composable
internal fun DefaultKeyboard(
    modifier: Modifier,
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
    allowVibration: Boolean,
    fractional: String,
    middleZero: Boolean,
    acButton: Boolean,
    addBracket: () -> Unit,
) {
    val fractionalIcon = remember { if (fractional == Token.Digit.dot) IconPack.Dot else IconPack.Comma }
    val contentHeight: Float = if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) KeyboardButtonContentHeightShort else KeyboardButtonContentHeightTall

    KeypadFlow(
        modifier = modifier,
        rows = 5,
        columns = 4
    ) { width, height ->
        val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)

        if (acButton) {
            KeyboardButtonTertiary(bModifier, IconPack.Clear, allowVibration, contentHeight) { clearInput() }
            KeyboardButtonFilled(bModifier, IconPack.Brackets, allowVibration, contentHeight) { addBracket() }
        } else {
            KeyboardButtonFilled(bModifier, IconPack.LeftBracket, allowVibration, contentHeight) { addDigit(Token.Operator.leftBracket) }
            KeyboardButtonFilled(bModifier, IconPack.RightBracket, allowVibration, contentHeight) { addDigit(Token.Operator.rightBracket) }
        }
        KeyboardButtonFilled(bModifier, IconPack.Power, allowVibration, contentHeight) { addDigit(Token.Operator.power) }
        KeyboardButtonFilled(bModifier, IconPack.Root, allowVibration, contentHeight) { addDigit(Token.Operator.sqrt) }

        KeyboardButtonLight(bModifier, IconPack.Key7, allowVibration, contentHeight) { addDigit(Token.Digit._7) }
        KeyboardButtonLight(bModifier, IconPack.Key8, allowVibration, contentHeight) { addDigit(Token.Digit._8) }
        KeyboardButtonLight(bModifier, IconPack.Key9, allowVibration, contentHeight) { addDigit(Token.Digit._9) }
        KeyboardButtonFilled(bModifier, IconPack.Divide, allowVibration, contentHeight) { addDigit(Token.Operator.divide) }

        KeyboardButtonLight(bModifier, IconPack.Key4, allowVibration, contentHeight) { addDigit(Token.Digit._4) }
        KeyboardButtonLight(bModifier, IconPack.Key5, allowVibration, contentHeight) { addDigit(Token.Digit._5) }
        KeyboardButtonLight(bModifier, IconPack.Key6, allowVibration, contentHeight) { addDigit(Token.Digit._6) }
        KeyboardButtonFilled(bModifier, IconPack.Multiply, allowVibration, contentHeight) { addDigit(Token.Operator.multiply) }

        KeyboardButtonLight(bModifier, IconPack.Key1, allowVibration, contentHeight) { addDigit(Token.Digit._1) }
        KeyboardButtonLight(bModifier, IconPack.Key2, allowVibration, contentHeight) { addDigit(Token.Digit._2) }
        KeyboardButtonLight(bModifier, IconPack.Key3, allowVibration, contentHeight) { addDigit(Token.Digit._3) }
        KeyboardButtonFilled(bModifier, IconPack.Minus, allowVibration, contentHeight) { addDigit(Token.Operator.minus) }

        if (middleZero) {
            KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, contentHeight) { addDigit(Token.Digit.dot) }
            KeyboardButtonLight(bModifier, IconPack.Key0, allowVibration, contentHeight) { addDigit(Token.Digit._0) }
        } else {
            KeyboardButtonLight(bModifier, IconPack.Key0, allowVibration, contentHeight) { addDigit(Token.Digit._0) }
            KeyboardButtonLight(bModifier, fractionalIcon, allowVibration, contentHeight) { addDigit(Token.Digit.dot) }
        }
        KeyboardButtonLight(bModifier, IconPack.Backspace, allowVibration, contentHeight, onLongClick = clearInput) { deleteDigit() }
        KeyboardButtonFilled(bModifier, IconPack.Plus, allowVibration, contentHeight) { addDigit(Token.Operator.plus) }
    }
}

@Composable
internal fun NumberBaseKeyboard(
    modifier: Modifier,
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
    allowVibration: Boolean
) {
    val contentHeight: Float = if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) KeyboardButtonContentHeightShort else KeyboardButtonContentHeightTall

    KeypadFlow(
        modifier = modifier,
        rows = 6,
        columns = 3
    ) { width, height ->
        val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)

        KeyboardButtonFilled(bModifier, IconPack.KeyA, allowVibration, contentHeight) { addDigit(Token.Letter._A) }
        KeyboardButtonFilled(bModifier, IconPack.KeyB, allowVibration, contentHeight) { addDigit(Token.Letter._B) }
        KeyboardButtonFilled(bModifier, IconPack.KeyC, allowVibration, contentHeight) { addDigit(Token.Letter._C) }

        KeyboardButtonFilled(bModifier, IconPack.KeyD, allowVibration, contentHeight) { addDigit(Token.Letter._D) }
        KeyboardButtonFilled(bModifier, IconPack.KeyE, allowVibration, contentHeight) { addDigit(Token.Letter._E) }
        KeyboardButtonFilled(bModifier, IconPack.KeyF, allowVibration, contentHeight) { addDigit(Token.Letter._F) }

        KeyboardButtonLight(bModifier, IconPack.Key7, allowVibration, contentHeight) { addDigit(Token.Digit._7) }
        KeyboardButtonLight(bModifier, IconPack.Key8, allowVibration, contentHeight) { addDigit(Token.Digit._8) }
        KeyboardButtonLight(bModifier, IconPack.Key9, allowVibration, contentHeight) { addDigit(Token.Digit._9) }

        KeyboardButtonLight(bModifier, IconPack.Key4, allowVibration, contentHeight) { addDigit(Token.Digit._4) }
        KeyboardButtonLight(bModifier, IconPack.Key5, allowVibration, contentHeight) { addDigit(Token.Digit._5) }
        KeyboardButtonLight(bModifier, IconPack.Key6, allowVibration, contentHeight) { addDigit(Token.Digit._6) }

        KeyboardButtonLight(bModifier, IconPack.Key1, allowVibration, contentHeight) { addDigit(Token.Digit._1) }
        KeyboardButtonLight(bModifier, IconPack.Key2, allowVibration, contentHeight) { addDigit(Token.Digit._2) }
        KeyboardButtonLight(bModifier, IconPack.Key3, allowVibration, contentHeight) { addDigit(Token.Digit._3) }

        // TODO Should be a separate o use custom widthFillFactors and heightFillFactors
        KeyboardButtonLight(bModifier, IconPack.Key0, allowVibration, contentHeight) { addDigit(Token.Digit._0) }
        KeyboardButtonLight(Modifier.fillMaxHeight(height).fillMaxWidth(width * 2), IconPack.Backspace, allowVibration, contentHeight, clearInput) { deleteDigit() }
    }
}

@Preview
@Composable
private fun PreviewConverterKeyboard() {
    DefaultKeyboard(
        modifier = Modifier.fillMaxSize(),
        addDigit = {},
        clearInput = {},
        deleteDigit = {},
        allowVibration = false,
        fractional = FormatterSymbols.Spaces.fractional,
        middleZero = false,
        acButton = true,
        addBracket = {}
    )
}

@Preview
@Composable
private fun PreviewConverterKeyboardNumberBase() {
    NumberBaseKeyboard(
        modifier = Modifier.fillMaxSize(),
        addDigit = {},
        clearInput = {},
        deleteDigit = {},
        allowVibration = false,
    )
}

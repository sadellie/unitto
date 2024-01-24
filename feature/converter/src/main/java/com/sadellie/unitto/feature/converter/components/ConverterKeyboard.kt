/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.base.R
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
    fractional: String,
    middleZero: Boolean,
    acButton: Boolean,
    addBracket: () -> Unit,
) {
    val fractionalIcon = remember(fractional) { if (fractional == Token.Digit.dot) IconPack.Dot else IconPack.Comma }
    val fractionalIconDescription = remember(fractional) { if (fractional == Token.Digit.dot) R.string.keyboard_dot else R.string.comma }
    val contentHeight: Float = if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) KeyboardButtonContentHeightShort else KeyboardButtonContentHeightTall

    KeypadFlow(
        modifier = modifier,
        rows = 5,
        columns = 4
    ) { width, height ->
        val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)

        if (acButton) {
            KeyboardButtonTertiary(bModifier, IconPack.Clear, stringResource(R.string.delete_label), contentHeight) { clearInput() }
            KeyboardButtonFilled(bModifier, IconPack.Brackets, stringResource(R.string.keyboard_brackets), contentHeight) { addBracket() }
        } else {
            KeyboardButtonFilled(bModifier, IconPack.LeftBracket, stringResource(R.string.keyboard_left_bracket), contentHeight) { addDigit(Token.Operator.leftBracket) }
            KeyboardButtonFilled(bModifier, IconPack.RightBracket, stringResource(R.string.keyboard_right_bracket), contentHeight) { addDigit(Token.Operator.rightBracket) }
        }
        KeyboardButtonFilled(bModifier, IconPack.Power, stringResource(R.string.keyboard_power), contentHeight) { addDigit(Token.Operator.power) }
        KeyboardButtonFilled(bModifier, IconPack.Root, stringResource(R.string.keyboard_root), contentHeight) { addDigit(Token.Operator.sqrt) }

        KeyboardButtonLight(bModifier, IconPack.Key7, Token.Digit._7, contentHeight) { addDigit(Token.Digit._7) }
        KeyboardButtonLight(bModifier, IconPack.Key8, Token.Digit._8, contentHeight) { addDigit(Token.Digit._8) }
        KeyboardButtonLight(bModifier, IconPack.Key9, Token.Digit._9, contentHeight) { addDigit(Token.Digit._9) }
        KeyboardButtonFilled(bModifier, IconPack.Divide, stringResource(R.string.keyboard_divide), contentHeight) { addDigit(Token.Operator.divide) }

        KeyboardButtonLight(bModifier, IconPack.Key4, Token.Digit._4, contentHeight) { addDigit(Token.Digit._4) }
        KeyboardButtonLight(bModifier, IconPack.Key5, Token.Digit._5, contentHeight) { addDigit(Token.Digit._5) }
        KeyboardButtonLight(bModifier, IconPack.Key6, Token.Digit._6, contentHeight) { addDigit(Token.Digit._6) }
        KeyboardButtonFilled(bModifier, IconPack.Multiply, stringResource(R.string.keyboard_multiply), contentHeight) { addDigit(Token.Operator.multiply) }

        KeyboardButtonLight(bModifier, IconPack.Key1, Token.Digit._1, contentHeight) { addDigit(Token.Digit._1) }
        KeyboardButtonLight(bModifier, IconPack.Key2, Token.Digit._2, contentHeight) { addDigit(Token.Digit._2) }
        KeyboardButtonLight(bModifier, IconPack.Key3, Token.Digit._3, contentHeight) { addDigit(Token.Digit._3) }
        KeyboardButtonFilled(bModifier, IconPack.Minus, stringResource(R.string.keyboard_minus), contentHeight) { addDigit(Token.Operator.minus) }

        if (middleZero) {
            KeyboardButtonLight(bModifier, fractionalIcon, stringResource(fractionalIconDescription), contentHeight) { addDigit(Token.Digit.dot) }
            KeyboardButtonLight(bModifier, IconPack.Key0, Token.Digit._0, contentHeight) { addDigit(Token.Digit._0) }
        } else {
            KeyboardButtonLight(bModifier, IconPack.Key0, Token.Digit._0, contentHeight) { addDigit(Token.Digit._0) }
            KeyboardButtonLight(bModifier, fractionalIcon, stringResource(fractionalIconDescription), contentHeight) { addDigit(Token.Digit.dot) }
        }
        KeyboardButtonLight(bModifier, IconPack.Backspace, stringResource(R.string.delete_label), contentHeight, onLongClick = clearInput) { deleteDigit() }
        KeyboardButtonFilled(bModifier, IconPack.Plus, stringResource(R.string.keyboard_plus), contentHeight) { addDigit(Token.Operator.plus) }
    }
}

@Composable
internal fun NumberBaseKeyboard(
    modifier: Modifier,
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
) {
    val contentHeight: Float = if (LocalWindowSize.current.heightSizeClass < WindowHeightSizeClass.Medium) KeyboardButtonContentHeightShort else KeyboardButtonContentHeightTall

    KeypadFlow(
        modifier = modifier,
        rows = 6,
        columns = 3
    ) { width, height ->
        val bModifier = Modifier.fillMaxWidth(width).fillMaxHeight(height)
        val wideButtonModifier = Modifier.fillMaxHeight(height).fillMaxWidth(width * 2)

        KeyboardButtonFilled(bModifier, IconPack.KeyA, Token.Letter._A, contentHeight) { addDigit(Token.Letter._A) }
        KeyboardButtonFilled(bModifier, IconPack.KeyB, Token.Letter._B, contentHeight) { addDigit(Token.Letter._B) }
        KeyboardButtonFilled(bModifier, IconPack.KeyC, Token.Letter._C, contentHeight) { addDigit(Token.Letter._C) }

        KeyboardButtonFilled(bModifier, IconPack.KeyD, Token.Letter._D, contentHeight) { addDigit(Token.Letter._D) }
        KeyboardButtonFilled(bModifier, IconPack.KeyE, Token.Letter._E, contentHeight) { addDigit(Token.Letter._E) }
        KeyboardButtonFilled(bModifier, IconPack.KeyF, Token.Letter._F, contentHeight) { addDigit(Token.Letter._F) }

        KeyboardButtonLight(bModifier, IconPack.Key7, Token.Digit._7, contentHeight) { addDigit(Token.Digit._7) }
        KeyboardButtonLight(bModifier, IconPack.Key8, Token.Digit._8, contentHeight) { addDigit(Token.Digit._8) }
        KeyboardButtonLight(bModifier, IconPack.Key9, Token.Digit._9, contentHeight) { addDigit(Token.Digit._9) }

        KeyboardButtonLight(bModifier, IconPack.Key4, Token.Digit._4, contentHeight) { addDigit(Token.Digit._4) }
        KeyboardButtonLight(bModifier, IconPack.Key5, Token.Digit._5, contentHeight) { addDigit(Token.Digit._5) }
        KeyboardButtonLight(bModifier, IconPack.Key6, Token.Digit._6, contentHeight) { addDigit(Token.Digit._6) }

        KeyboardButtonLight(bModifier, IconPack.Key1, Token.Digit._1, contentHeight) { addDigit(Token.Digit._1) }
        KeyboardButtonLight(bModifier, IconPack.Key2, Token.Digit._2, contentHeight) { addDigit(Token.Digit._2) }
        KeyboardButtonLight(bModifier, IconPack.Key3, Token.Digit._3, contentHeight) { addDigit(Token.Digit._3) }

        // TODO Should be a separate o use custom widthFillFactors and heightFillFactors
        KeyboardButtonLight(bModifier, IconPack.Key0, Token.Digit._0, contentHeight) { addDigit(Token.Digit._0) }
        KeyboardButtonLight(wideButtonModifier, IconPack.Backspace, stringResource(R.string.delete_label), contentHeight, clearInput) { deleteDigit() }
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
    )
}

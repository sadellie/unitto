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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.sadellie.unitto.core.base.KEY_BASE_A
import com.sadellie.unitto.core.base.KEY_BASE_B
import com.sadellie.unitto.core.base.KEY_BASE_C
import com.sadellie.unitto.core.base.KEY_BASE_D
import com.sadellie.unitto.core.base.KEY_BASE_E
import com.sadellie.unitto.core.base.KEY_BASE_F
import com.sadellie.unitto.core.base.KEY_DIVIDE
import com.sadellie.unitto.core.base.KEY_DOT
import com.sadellie.unitto.core.base.KEY_EXPONENT
import com.sadellie.unitto.core.base.KEY_LEFT_BRACKET
import com.sadellie.unitto.core.base.KEY_MINUS
import com.sadellie.unitto.core.base.KEY_MULTIPLY
import com.sadellie.unitto.core.base.KEY_PLUS
import com.sadellie.unitto.core.base.KEY_RIGHT_BRACKET
import com.sadellie.unitto.core.base.KEY_SQRT
import com.sadellie.unitto.core.ui.Formatter
import com.sadellie.unitto.core.ui.common.KeyboardButtonFilled
import com.sadellie.unitto.core.ui.common.KeyboardButtonLight
import com.sadellie.unitto.core.ui.common.key.UnittoIcons
import com.sadellie.unitto.core.ui.common.key.unittoicons.Comma
import com.sadellie.unitto.core.ui.common.key.unittoicons.Delete
import com.sadellie.unitto.core.ui.common.key.unittoicons.Divide
import com.sadellie.unitto.core.ui.common.key.unittoicons.Dot
import com.sadellie.unitto.core.ui.common.key.unittoicons.Exponent
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
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyA
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyB
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyC
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyD
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyE
import com.sadellie.unitto.core.ui.common.key.unittoicons.KeyF
import com.sadellie.unitto.core.ui.common.key.unittoicons.LeftBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.Minus
import com.sadellie.unitto.core.ui.common.key.unittoicons.Multiply
import com.sadellie.unitto.core.ui.common.key.unittoicons.Plus
import com.sadellie.unitto.core.ui.common.key.unittoicons.RightBracket
import com.sadellie.unitto.core.ui.common.key.unittoicons.SquareRoot
import com.sadellie.unitto.feature.converter.ConverterMode

/**
 * Keyboard with button that looks like a calculator
 *
 * @param modifier Modifier that is applied to [Row]
 * @param addDigit Function that is called when clicking number and dot buttons
 * @param deleteDigit Function that is called when clicking delete "<" button
 * @param clearInput Function that is called when clicking clear "AC" button
 * @param converterMode
 */
@Composable
internal fun Keyboard(
    modifier: Modifier = Modifier,
    addDigit: (String) -> Unit = {},
    deleteDigit: () -> Unit = {},
    clearInput: () -> Unit = {},
    converterMode: ConverterMode,
    allowVibration: Boolean
) {
    Crossfade(converterMode, modifier = modifier) {
        when (it) {
            ConverterMode.DEFAULT -> DefaultKeyboard(addDigit, clearInput, deleteDigit, allowVibration)
            ConverterMode.BASE -> BaseKeyboard(addDigit, clearInput, deleteDigit, allowVibration)
        }
    }

}

@Composable
private fun DefaultKeyboard(
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
    allowVibration: Boolean
) {
    val fractionalIcon = remember { if (Formatter.fractional == KEY_DOT) UnittoIcons.Dot else UnittoIcons.Comma }
    Column {
        // Button modifier
        val bModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        // Column modifier
        val cModifier = Modifier.weight(1f)
        Row(cModifier) {
            KeyboardButtonFilled(bModifier, UnittoIcons.LeftBracket, allowVibration) { addDigit(KEY_LEFT_BRACKET) }
            KeyboardButtonFilled(bModifier, UnittoIcons.RightBracket, allowVibration) { addDigit(KEY_RIGHT_BRACKET) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Exponent, allowVibration) { addDigit(KEY_EXPONENT) }
            KeyboardButtonFilled(bModifier, UnittoIcons.SquareRoot, allowVibration) { addDigit(KEY_SQRT) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key7, allowVibration) { addDigit(KEY_7) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key8, allowVibration) { addDigit(KEY_8) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key9, allowVibration) { addDigit(KEY_9) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Divide, allowVibration) { addDigit(KEY_DIVIDE) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key4, allowVibration) { addDigit(KEY_4) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key5, allowVibration) { addDigit(KEY_5) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key6, allowVibration) { addDigit(KEY_6) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Multiply, allowVibration) { addDigit(KEY_MULTIPLY) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key1, allowVibration) { addDigit(KEY_1) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key2, allowVibration) { addDigit(KEY_2) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key3, allowVibration) { addDigit(KEY_3) }
            KeyboardButtonFilled(bModifier, UnittoIcons.Minus, allowVibration) { addDigit(KEY_MINUS) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key0, allowVibration) { addDigit(KEY_0) }
            KeyboardButtonLight(bModifier, fractionalIcon, allowVibration) { addDigit(KEY_DOT) }
            KeyboardButtonLight(bModifier, UnittoIcons.Delete, allowVibration, clearInput) { deleteDigit() }
            KeyboardButtonFilled(bModifier, UnittoIcons.Plus, allowVibration) { addDigit(KEY_PLUS) }
        }
    }
}

@Composable
private fun BaseKeyboard(
    addDigit: (String) -> Unit,
    clearInput: () -> Unit,
    deleteDigit: () -> Unit,
    allowVibration: Boolean
) {
    Column {
        // Button modifier
        val bModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        // Column modifier
        val cModifier = Modifier.weight(1f)

        Row(cModifier) {
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyA, allowVibration) { addDigit(KEY_BASE_A) }
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyB, allowVibration) { addDigit(KEY_BASE_B) }
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyC, allowVibration) { addDigit(KEY_BASE_C) }
        }
        Row(cModifier) {
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyD, allowVibration) { addDigit(KEY_BASE_D) }
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyE, allowVibration) { addDigit(KEY_BASE_E) }
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyF, allowVibration) { addDigit(KEY_BASE_F) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key7, allowVibration) { addDigit(KEY_7) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key8, allowVibration) { addDigit(KEY_8) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key9, allowVibration) { addDigit(KEY_9) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key4, allowVibration) { addDigit(KEY_4) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key5, allowVibration) { addDigit(KEY_5) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key6, allowVibration) { addDigit(KEY_6) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key1, allowVibration) { addDigit(KEY_1) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key2, allowVibration) { addDigit(KEY_2) }
            KeyboardButtonLight(bModifier, UnittoIcons.Key3, allowVibration) { addDigit(KEY_3) }
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key0, allowVibration) { addDigit(KEY_0) }
            KeyboardButtonLight(Modifier.fillMaxSize().weight(2f).padding(4.dp), UnittoIcons.Delete, allowVibration, clearInput) { deleteDigit() }
        }
    }
}

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
            KeyboardButtonFilled(bModifier, UnittoIcons.LeftBracket, { addDigit(KEY_LEFT_BRACKET) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.RightBracket, { addDigit(KEY_RIGHT_BRACKET) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.Exponent, { addDigit(KEY_EXPONENT) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.SquareRoot, { addDigit(KEY_SQRT) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key7, { addDigit(KEY_7) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key8, { addDigit(KEY_8) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key9, { addDigit(KEY_9) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.Divide, { addDigit(KEY_DIVIDE) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key4, { addDigit(KEY_4) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key5, { addDigit(KEY_5) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key6, { addDigit(KEY_6) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.Multiply, { addDigit(KEY_MULTIPLY) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key1, { addDigit(KEY_1) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key2, { addDigit(KEY_2) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key3, { addDigit(KEY_3) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.Minus, { addDigit(KEY_MINUS) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key0, { addDigit(KEY_0) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, fractionalIcon, { addDigit(KEY_DOT) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Delete, { deleteDigit() }, clearInput, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.Plus, { addDigit(KEY_PLUS) }, allowVibration = allowVibration)
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
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyA, { addDigit(KEY_BASE_A) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyB, { addDigit(KEY_BASE_B) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyC, { addDigit(KEY_BASE_C) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyD, { addDigit(KEY_BASE_D) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyE, { addDigit(KEY_BASE_E) }, allowVibration = allowVibration)
            KeyboardButtonFilled(bModifier, UnittoIcons.KeyF, { addDigit(KEY_BASE_F) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key7, { addDigit(KEY_7) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key8, { addDigit(KEY_8) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key9, { addDigit(KEY_9) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key4, { addDigit(KEY_4) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key5, { addDigit(KEY_5) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key6, { addDigit(KEY_6) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key1, { addDigit(KEY_1) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key2, { addDigit(KEY_2) }, allowVibration = allowVibration)
            KeyboardButtonLight(bModifier, UnittoIcons.Key3, { addDigit(KEY_3) }, allowVibration = allowVibration)
        }
        Row(cModifier) {
            KeyboardButtonLight(bModifier, UnittoIcons.Key0, { addDigit(KEY_0) }, allowVibration = allowVibration)
            KeyboardButtonLight(
                Modifier
                    .fillMaxSize()
                    .weight(2f)
                    .padding(4.dp),
                UnittoIcons.Delete,
                allowVibration = allowVibration,
                onClick = { deleteDigit() },
                onLongClick = clearInput
            )
        }
    }
}

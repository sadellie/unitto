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
import com.sadellie.unitto.core.base.KEY_CLEAR
import com.sadellie.unitto.core.base.KEY_DIVIDE
import com.sadellie.unitto.core.base.KEY_DIVIDE_DISPLAY
import com.sadellie.unitto.core.base.KEY_DOT
import com.sadellie.unitto.core.base.KEY_EXPONENT
import com.sadellie.unitto.core.base.KEY_LEFT_BRACKET
import com.sadellie.unitto.core.base.KEY_MINUS
import com.sadellie.unitto.core.base.KEY_MINUS_DISPLAY
import com.sadellie.unitto.core.base.KEY_MULTIPLY
import com.sadellie.unitto.core.base.KEY_MULTIPLY_DISPLAY
import com.sadellie.unitto.core.base.KEY_PLUS
import com.sadellie.unitto.core.base.KEY_RIGHT_BRACKET
import com.sadellie.unitto.core.base.KEY_SQRT
import com.sadellie.unitto.core.ui.Formatter
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
    Column {
        // Button modifier
        val bModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        // Column modifier
        val cModifier = Modifier.weight(1f)
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_LEFT_BRACKET, isPrimary = false, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_RIGHT_BRACKET, isPrimary = false, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_EXPONENT, isPrimary = false, allowVibration = allowVibration, onClick = { addDigit(KEY_EXPONENT) })
            KeyboardButton(bModifier, KEY_SQRT, isPrimary = false, allowVibration = allowVibration, onClick = { addDigit(KEY_SQRT) })
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_7, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_8, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_9, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_DIVIDE_DISPLAY, isPrimary = false, allowVibration = allowVibration) { addDigit(KEY_DIVIDE) }
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_4, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_5, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_6, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_MULTIPLY_DISPLAY, isPrimary = false, allowVibration = allowVibration, ) { addDigit(KEY_MULTIPLY) }
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_1, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_2, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_3, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_MINUS_DISPLAY, isPrimary = false, allowVibration = allowVibration) { addDigit(KEY_MINUS) }
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_0, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, Formatter.fractional, allowVibration = allowVibration, ) { addDigit(KEY_DOT) }
            KeyboardButton(bModifier, KEY_CLEAR, allowVibration = allowVibration, onLongClick = clearInput) { deleteDigit() }
            KeyboardButton(bModifier, KEY_PLUS, isPrimary = false, allowVibration = allowVibration, ) { addDigit(KEY_PLUS) }
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
            KeyboardButton(bModifier, KEY_BASE_A, isPrimary = false, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_BASE_B, isPrimary = false, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_BASE_C, isPrimary = false, allowVibration = allowVibration, onClick = addDigit)
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_BASE_D, isPrimary = false, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_BASE_E, isPrimary = false, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_BASE_F, isPrimary = false, allowVibration = allowVibration, onClick = addDigit)
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_7, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_8, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_9, allowVibration = allowVibration, onClick = addDigit)
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_4, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_5, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_6, allowVibration = allowVibration, onClick = addDigit)
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_1, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_2, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(bModifier, KEY_3, allowVibration = allowVibration, onClick = addDigit)
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_0, allowVibration = allowVibration, onClick = addDigit)
            KeyboardButton(
                Modifier
                    .fillMaxSize()
                    .weight(2f)
                    .padding(4.dp),
                KEY_CLEAR,
                allowVibration = allowVibration,
                onLongClick = clearInput
            ) { deleteDigit() }
        }
    }
}

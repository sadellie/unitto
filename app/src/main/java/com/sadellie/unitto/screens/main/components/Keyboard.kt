/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.data.KEY_0
import com.sadellie.unitto.data.KEY_1
import com.sadellie.unitto.data.KEY_2
import com.sadellie.unitto.data.KEY_3
import com.sadellie.unitto.data.KEY_4
import com.sadellie.unitto.data.KEY_5
import com.sadellie.unitto.data.KEY_6
import com.sadellie.unitto.data.KEY_7
import com.sadellie.unitto.data.KEY_8
import com.sadellie.unitto.data.KEY_9
import com.sadellie.unitto.data.KEY_CLEAR
import com.sadellie.unitto.data.KEY_DIVIDE
import com.sadellie.unitto.data.KEY_DIVIDE_DISPLAY
import com.sadellie.unitto.data.KEY_DOT
import com.sadellie.unitto.data.KEY_EXPONENT
import com.sadellie.unitto.data.KEY_LEFT_BRACKET
import com.sadellie.unitto.data.KEY_MINUS
import com.sadellie.unitto.data.KEY_MINUS_DISPLAY
import com.sadellie.unitto.data.KEY_MULTIPLY
import com.sadellie.unitto.data.KEY_MULTIPLY_DISPLAY
import com.sadellie.unitto.data.KEY_PLUS
import com.sadellie.unitto.data.KEY_RIGHT_BRACKET
import com.sadellie.unitto.data.KEY_SQRT
import com.sadellie.unitto.screens.Formatter

/**
 * Keyboard with button that looks like a calculator
 *
 * @param modifier Modifier that is applied to [Row]
 * @param addDigit Function that is called when clicking number and dot buttons
 * @param deleteDigit Function that is called when clicking delete "<" button
 * @param clearInput Function that is called when clicking clear "AC" button
 */
@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    addDigit: (String) -> Unit = {},
    deleteDigit: () -> Unit = {},
    clearInput: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Button modifier
        val bModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        // Column modifier
        val cModifier = Modifier.weight(1f)
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_LEFT_BRACKET, isPrimary = false, onClick = addDigit)
            KeyboardButton(bModifier, KEY_RIGHT_BRACKET, isPrimary = false, onClick = addDigit)
            KeyboardButton(bModifier, KEY_EXPONENT, isPrimary = false, onClick = { addDigit(KEY_EXPONENT) })
            KeyboardButton(bModifier, KEY_SQRT, isPrimary = false, onClick = { addDigit(KEY_SQRT) })
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_7, onClick = addDigit)
            KeyboardButton(bModifier, KEY_8, onClick = addDigit)
            KeyboardButton(bModifier, KEY_9, onClick = addDigit)
            KeyboardButton(bModifier, KEY_DIVIDE_DISPLAY, isPrimary = false) { addDigit(KEY_DIVIDE) }
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_4, onClick = addDigit)
            KeyboardButton(bModifier, KEY_5, onClick = addDigit)
            KeyboardButton(bModifier, KEY_6, onClick = addDigit)
            KeyboardButton(bModifier, KEY_MULTIPLY_DISPLAY, isPrimary = false) { addDigit(KEY_MULTIPLY) }
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_1, onClick = addDigit)
            KeyboardButton(bModifier, KEY_2, onClick = addDigit)
            KeyboardButton(bModifier, KEY_3, onClick = addDigit)
            KeyboardButton(bModifier, KEY_MINUS_DISPLAY, isPrimary = false) { addDigit(KEY_MINUS) }
        }
        Row(cModifier) {
            KeyboardButton(bModifier, KEY_0, onClick = addDigit)
            KeyboardButton(bModifier, Formatter.fractional) { addDigit(KEY_DOT) }
            KeyboardButton(Modifier.fillMaxSize().weight(2f).padding(4.dp), KEY_CLEAR, onLongClick = clearInput) { deleteDigit() }
            KeyboardButton(bModifier, KEY_PLUS, isPrimary = false) { addDigit(KEY_PLUS) }
        }
    }
}

@Preview
@Composable
fun PreviewKeyboard() {
    Keyboard()
}

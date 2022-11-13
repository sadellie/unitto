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
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.data.*
import com.sadellie.unitto.screens.Formatter

/**
 * Keyboard with button that looks like a calculator
 *
 * @param modifier Modifier that is applied to [Row]
 * @param addDigit Function that is called when clicking number and dot buttons
 * @param deleteDigit Function that is called when clicking delete "<" button
 * @param clearInput Function that is called when clicking clear "AC" button
 * @param deleteButtonEnabled Current state of delete "<" button
 * @param dotButtonEnabled Current state of clear "AC" button
 */
@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    addDigit: (String) -> Unit = {},
    deleteDigit: () -> Unit = {},
    clearInput: () -> Unit = {},
    deleteButtonEnabled: Boolean = false,
    dotButtonEnabled: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        // Button modifier
        val bModifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
        // Column modifier
        val cModifier = Modifier.weight(1f)
        Column(cModifier) {
            KeyboardButton(bModifier, KEY_7, onClick = addDigit)
            KeyboardButton(bModifier, KEY_4, onClick = addDigit)
            KeyboardButton(bModifier, KEY_1, onClick = addDigit)
            KeyboardButton(bModifier, KEY_0, onClick = addDigit)
        }
        Column(cModifier) {
            KeyboardButton(bModifier, KEY_8, onClick = addDigit)
            KeyboardButton(bModifier, KEY_5, onClick = addDigit)
            KeyboardButton(bModifier, KEY_2, onClick = addDigit)
            KeyboardButton(bModifier, Formatter.fractional, enabled = dotButtonEnabled) { addDigit(KEY_DOT) }
        }
        Column(cModifier) {
            KeyboardButton(bModifier, KEY_9, onClick = addDigit)
            KeyboardButton(bModifier, KEY_6, onClick = addDigit)
            KeyboardButton(bModifier, KEY_3, onClick = addDigit)
            KeyboardButton(bModifier, KEY_CLEAR, enabled = deleteButtonEnabled, onLongClick = clearInput) { deleteDigit() }
        }
        Column(cModifier) {
            KeyboardButton(bModifier, KEY_DIVIDE_DISPLAY, isPrimary = false) { addDigit(KEY_DIVIDE) }
            KeyboardButton(bModifier, KEY_MULTIPLY_DISPLAY, isPrimary = false) { addDigit(KEY_MULTIPLY) }
            KeyboardButton(bModifier, KEY_MINUS_DISPLAY, isPrimary = false) { addDigit(KEY_MINUS) }
            KeyboardButton(bModifier, KEY_PLUS, isPrimary = false) { addDigit(KEY_PLUS) }
        }
    }
}

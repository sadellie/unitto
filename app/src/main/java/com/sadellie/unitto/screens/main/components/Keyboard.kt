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
 * @param negateAction Function that is called when clicking negate "±" button
 * @param deleteButtonEnabled Current state of delete "<" button
 * @param dotButtonEnabled Current state of clear "AC" button
 * @param negateButtonEnabled Current state of negate "±" button
 */
@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    addDigit: (String) -> Unit = {},
    deleteDigit: () -> Unit = {},
    clearInput: () -> Unit = {},
    negateAction: () -> Unit = {},
    deleteButtonEnabled: Boolean = false,
    dotButtonEnabled: Boolean = true,
    negateButtonEnabled: Boolean = false,
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
            KeyboardButton(bModifier, Formatter.fractional, dotButtonEnabled) { addDigit(KEY_DOT) }
        }
        Column(cModifier) {
            KeyboardButton(bModifier, KEY_9, onClick = addDigit)
            KeyboardButton(bModifier, KEY_6, onClick = addDigit)
            KeyboardButton(bModifier, KEY_3, onClick = addDigit)
            KeyboardButton(bModifier, KEY_CLEAR, deleteButtonEnabled) { deleteDigit() }
        }
        Column(cModifier) {
            KeyboardButton(bModifier, KEY_AC, deleteButtonEnabled) { clearInput() }
            KeyboardButton(bModifier, KEY_NEGATE, negateButtonEnabled) { negateAction() }
        }
    }
}

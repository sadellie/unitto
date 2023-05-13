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

package com.sadellie.unitto.core.ui.common.textfield

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun TextFieldValue.addTokens(tokens: String): TextFieldValue {
    return this.copy(
        text = text.replaceRange(selection.start, selection.end, tokens),
        selection = TextRange(selection.start + tokens.length)
    )
}

fun TextFieldValue.deleteTokens(): TextFieldValue {
    val distanceFromEnd = text.length - selection.end

    val deleteRangeStart = when (selection.end) {
        // Don't delete if at the start of the text field
        0 -> return this
        // We don't have anything selected (cursor in one position)
        // like this 1234|56 => after deleting will be like this 123|56
        // Cursor moved one symbol left
        selection.start -> {
            // We default to 1 here. It means that cursor is not placed after illegal token
            // Just a number or a binary operator or something else, can delete by one symbol
            val symbolsToDelete = text.tokenLengthAhead(selection.end)

            selection.start - symbolsToDelete
        }
        // We have multiple symbols selected
        // like this 123[45]6 => after deleting will be like this 123|6
        // Cursor will be placed where selection start was
        else -> selection.start
    }

    val newText = text.removeRange(deleteRangeStart, selection.end)

    return this.copy(
        text = newText,
        selection = TextRange((newText.length - distanceFromEnd).coerceAtLeast(0))
    )
}

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import androidx.lifecycle.SavedStateHandle
import com.sadellie.unitto.core.base.Token

fun TextFieldValue.addTokens(tokens: String): TextFieldValue {
    val ahead by lazy { text.tokenAhead(selection.start) }

    when (tokens) {
        Token.Operator.plus,
        Token.Operator.multiply,
        Token.Operator.divide,
        Token.Operator.power -> {
            if (ahead == Token.Operator.plus) return deleteAheadAndAdd(tokens)
            if (ahead == Token.Operator.minus) return deleteAheadAndAdd(tokens)
            if (ahead == Token.Operator.multiply) return deleteAheadAndAdd(tokens)
            if (ahead == Token.Operator.divide) return deleteAheadAndAdd(tokens)
            if (ahead == Token.Operator.sqrt) return deleteAheadAndAdd(tokens)
            if (ahead == Token.Operator.power) return deleteAheadAndAdd(tokens)
            if (ahead == "") return deleteTokens()
        }
        Token.Operator.minus -> {
            if (ahead == Token.Operator.plus) return deleteAheadAndAdd(tokens)
            if (ahead == Token.Operator.minus) return deleteAheadAndAdd(tokens)
        }
        Token.Digit.dot -> {
            if (ahead == Token.Digit.dot) return deleteAheadAndAdd(tokens)
        }
    }

    return this.copy(
        text = text.replaceRange(selection.start, selection.end, tokens),
        selection = TextRange(selection.start + tokens.length)
    )
}

fun TextFieldValue.addBracket(): TextFieldValue {
    val subStringBeforeCursor = text.substring(0..<selection.start)

    // Always open when empty in front
    if (subStringBeforeCursor.isEmpty()) {
        return addTokens(Token.Operator.leftBracket)
    }

    // Always close before operator
    val operators = listOf(
        Token.Operator.multiply,
        Token.Operator.divide,
        Token.Operator.plus,
        Token.Operator.minus,
        Token.Operator.power,
    )
    if (text.tokenAfter(selection.start) in operators) {
        return addTokens(Token.Operator.rightBracket)
    }

    // Always open when balanced in front
    val leftBracketChar: Char = Token.Operator.leftBracket.first()
    val rightBracketChar: Char = Token.Operator.rightBracket.first()
    var balance = 0
    subStringBeforeCursor.forEach {
        if (it == leftBracketChar) balance += 1
        if (it == rightBracketChar) balance -= 1
    }
    if (balance == 0) {
        return addTokens(Token.Operator.leftBracket)
    }

    // Always open after operator
    val operators2 = listOf(
        Token.Operator.multiply,
        Token.Operator.divide,
        Token.Operator.plus,
        Token.Operator.minus,
        Token.Operator.power,
        Token.Operator.leftBracket
    )
    if (text.tokenAhead(selection.start) in operators2) {
        return addTokens(Token.Operator.leftBracket)
    }

    return addTokens(Token.Operator.rightBracket)
}

fun TextFieldValue.deleteTokens(): TextFieldValue {
    val distanceFromEnd = text.length - selection.end

    val deleteRangeStart = when (selection.end) {
        // Don't delete if at the start of the text field
        0 -> return this
        // We don't have anything selected (cursor in one position)
        // like this 1234|56 => after deleting will be like this 123|56
        // Cursor moved one symbol left
        selection.start -> selection.start - text.tokenLengthAhead(selection.end)
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

fun TextFieldValue.placeCursorAtTheEnd(): TextFieldValue = copy(selection = TextRange(text.length))

/**
 * Tries to get a [TextFieldValue]. Places cursor at the end.
 *
 * @receiver [SavedStateHandle] Where to look for.
 * @param key Key to find
 * @return [TextFieldValue] with cursor at the end.
 */
fun SavedStateHandle.getTextField(key: String): TextFieldValue =
    with(get(key) ?: "") {
        TextFieldValue(this, TextRange(this.length))
    }

/**
 * <b>!!! Recursive !!!</b> (one wrong step and you are dead 💀)
 */
private fun TextFieldValue.deleteAheadAndAdd(tokens: String): TextFieldValue {
    var newValue = this
    // For cases like: "12+[34]" and "*" symbol is being added. Will delete selected tokens
    if (!selection.collapsed) newValue = this.deleteTokens()
    return newValue
        .deleteTokens()
        .addTokens(tokens)
}

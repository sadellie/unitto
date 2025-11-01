/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui.textfield

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.TextRange
import androidx.lifecycle.SavedStateHandle
import com.sadellie.unitto.core.common.Token
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

fun TextFieldState.addTokens(tokens: String) {
  when (tokens) {
    Token.Operator.PLUS,
    Token.Operator.MULTIPLY,
    Token.Operator.DIVIDE,
    Token.Operator.POWER -> {
      val tokenAhead = text.toString().tokenAhead(selection.start)
      if (tokenAhead == Token.Operator.PLUS) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token.Operator.MINUS) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token.Operator.MULTIPLY) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token.Operator.DIVIDE) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token.Operator.SQRT) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token.Operator.POWER) return deleteAheadAndAdd(tokens)
      if (tokenAhead == "") return deleteTokens()
    }
    Token.Operator.MINUS -> {
      val tokenAhead = text.toString().tokenAhead(selection.start)
      if (tokenAhead == Token.Operator.PLUS) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token.Operator.MINUS) return deleteAheadAndAdd(tokens)
    }
    Token.Digit.DOT -> {
      val tokenAhead = text.toString().tokenAhead(selection.start)
      if (tokenAhead == Token.Digit.DOT) return deleteAheadAndAdd(tokens)
    }
  }

  this.edit {
    replace(selection.start, selection.end, tokens)
    selection = TextRange(selection.end)
  }
}

fun TextFieldState.addBracket() {
  val subStringBeforeCursor = text.substring(0..<selection.start)

  // Always open when empty in front
  if (subStringBeforeCursor.isEmpty()) {
    addTokens(Token.Operator.LEFT_BRACKET)
    return
  }

  // Always close before operator
  val openBeforeOperators =
    listOf(
      Token.Operator.MULTIPLY,
      Token.Operator.DIVIDE,
      Token.Operator.PLUS,
      Token.Operator.MINUS,
      Token.Operator.POWER,
    )
  if (text.toString().tokenAfter(selection.start) in openBeforeOperators) {
    addTokens(Token.Operator.RIGHT_BRACKET)
    return
  }

  // Always open when balanced in front
  val leftBracketChar: Char = Token.Operator.LEFT_BRACKET.first()
  val rightBracketChar: Char = Token.Operator.RIGHT_BRACKET.first()
  var balance = 0
  subStringBeforeCursor.forEach {
    if (it == leftBracketChar) balance += 1
    if (it == rightBracketChar) balance -= 1
  }
  if (balance == 0) {
    addTokens(Token.Operator.LEFT_BRACKET)
    return
  }

  // Always open after operator
  val openAfterOperators =
    listOf(
      Token.Operator.MULTIPLY,
      Token.Operator.DIVIDE,
      Token.Operator.PLUS,
      Token.Operator.MINUS,
      Token.Operator.POWER,
      Token.Operator.LEFT_BRACKET,
    )
  if (text.toString().tokenAhead(selection.start) in openAfterOperators) {
    addTokens(Token.Operator.LEFT_BRACKET)
    return
  }

  addTokens(Token.Operator.RIGHT_BRACKET)
}

fun TextFieldState.deleteTokens() {
  val deleteRangeStart =
    when (selection.end) {
      // Don't delete if at the start of the text field
      0 -> return
      // We don't have anything selected (cursor in one position)
      // like this 1234|56 => after deleting will be like this 123|56
      // Cursor moved one symbol left
      selection.start -> selection.start - text.toString().tokenLengthAhead(selection.end)
      // We have multiple symbols selected
      // like this 123[45]6 => after deleting will be like this 123|6
      // Cursor will be placed where selection start was
      else -> selection.start
    }

  this.edit { delete(deleteRangeStart, selection.end) }
}

fun TextFieldState.placeCursorAtTheEnd() = edit { placeCursorAtEnd() }

/**
 * Tries to get a [TextFieldState]. Places cursor at the end.
 *
 * @param key Key to find
 * @return [TextFieldState] with cursor at the end.
 * @receiver [SavedStateHandle] Where to look for.
 */
fun SavedStateHandle.getTextFieldState(key: String): TextFieldState =
  with(get(key) ?: "") { TextFieldState(this, TextRange(this.length)) }

@OptIn(FlowPreview::class)
fun TextFieldState.observe(debounceMs: Long = TEXT_FIELD_STATE_DEBOUNCE_MS) =
  snapshotFlow { this.text }.debounce(debounceMs)

/** <b>!!! Recursive !!!</b> (one wrong step and you are dead 💀) */
private fun TextFieldState.deleteAheadAndAdd(tokens: String) {
  // For cases like: "12+[34]" and "*" symbol is being added. Will delete selected tokens
  if (!selection.collapsed) {
    this.edit { delete(this.selection.start, this.selection.end) }
  }
  this.deleteTokens()
  this.addTokens(tokens)
}

private const val TEXT_FIELD_STATE_DEBOUNCE_MS = 50L

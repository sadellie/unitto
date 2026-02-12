/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
import com.sadellie.unitto.core.common.Token2
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

fun TextFieldState.addTokens(tokens: String) {
  when (tokens) {
    Token2.Plus.symbol,
    Token2.Multiply.symbol,
    Token2.Divide.symbol,
    Token2.Power.symbol -> {
      val tokenAhead = text.toString().tokenAhead(selection.min)
      if (tokenAhead == Token2.Plus.symbol) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token2.Minus.symbol) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token2.Multiply.symbol) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token2.Divide.symbol) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token2.Sqrt.symbol) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token2.Power.symbol) return deleteAheadAndAdd(tokens)
      if (tokenAhead == "") return deleteTokens()
    }
    Token2.Minus.symbol -> {
      val tokenAhead = text.toString().tokenAhead(selection.min)
      if (tokenAhead == Token2.Plus.symbol) return deleteAheadAndAdd(tokens)
      if (tokenAhead == Token2.Minus.symbol) return deleteAheadAndAdd(tokens)
    }
    Token2.Dot.symbol -> {
      val tokenAhead = text.toString().tokenAhead(selection.min)
      if (tokenAhead == Token2.Dot.symbol) return deleteAheadAndAdd(tokens)
    }
  }

  this.edit {
    replace(selection.min, selection.max, tokens)
    selection = TextRange(selection.max)
  }
}

fun TextFieldState.addBracket() {
  val subStringBeforeCursor = text.substring(0..<selection.min)

  // Always open when empty in front
  if (subStringBeforeCursor.isEmpty()) {
    addTokens(Token2.LeftBracket.symbol)
    return
  }

  // Always close before operator
  val openBeforeOperators =
    listOf(
      Token2.Multiply.symbol,
      Token2.Divide.symbol,
      Token2.Plus.symbol,
      Token2.Minus.symbol,
      Token2.Power.symbol,
    )
  if (text.toString().tokenAfter(selection.min) in openBeforeOperators) {
    addTokens(Token2.RightBracket.symbol)
    return
  }

  // Always open when balanced in front
  val leftBracketChar: Char = Token2.LeftBracket.symbol.first()
  val rightBracketChar: Char = Token2.RightBracket.symbol.first()
  var balance = 0
  subStringBeforeCursor.forEach {
    if (it == leftBracketChar) balance += 1
    if (it == rightBracketChar) balance -= 1
  }
  if (balance == 0) {
    addTokens(Token2.LeftBracket.symbol)
    return
  }

  // Always open after operator
  val openAfterOperators =
    listOf(
      Token2.Multiply.symbol,
      Token2.Divide.symbol,
      Token2.Plus.symbol,
      Token2.Minus.symbol,
      Token2.Power.symbol,
      Token2.LeftBracket.symbol,
    )
  if (text.toString().tokenAhead(selection.min) in openAfterOperators) {
    addTokens(Token2.LeftBracket.symbol)
    return
  }

  addTokens(Token2.RightBracket.symbol)
}

fun TextFieldState.deleteTokens() {
  val deleteRangeStart =
    when (selection.max) {
      // Don't delete if at the start of the text field
      0 -> return
      // We don't have anything selected (cursor in one position)
      // like this 1234|56 => after deleting will be like this 123|56
      // Cursor moved one symbol left
      selection.min -> selection.min - text.toString().tokenLengthAhead(selection.max)
      // We have multiple symbols selected
      // like this 123[45]6 => after deleting will be like this 123|6
      // Cursor will be placed where selection start was
      else -> selection.min
    }

  this.edit { delete(deleteRangeStart, selection.max) }
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
    this.edit { delete(this.selection.min, this.selection.max) }
  }
  this.deleteTokens()
  this.addTokens(tokens)
}

private const val TEXT_FIELD_STATE_DEBOUNCE_MS = 50L

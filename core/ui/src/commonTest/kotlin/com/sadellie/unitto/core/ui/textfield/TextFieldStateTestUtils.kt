/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.text.TextRange
import com.sadellie.unitto.core.common.Token
import kotlin.test.assertEquals

fun compareTextStates(expected: String, input: String, action: (TextFieldState) -> Unit = {}) {
  val expectedState = textState(expected)
  val actualState = textState(input).apply(action)
  assertEquals(expectedState.text, actualState.text)
  assertEquals(expectedState.selection, actualState.selection)
}

/** Use [] for selection */
internal fun textState(text: String): TextFieldState =
  TextFieldState(
    initialText = textStateInitialText(text),
    initialSelection = textStateInitialSelection(text),
  )

/** Use [] for selection */
internal fun textStateInitialText(text: String): String =
  text
    .replace("[", "")
    .replace("]", "")
    .replace("-", Token.Operator.MINUS)
    .replace("/", Token.Operator.DIVIDE)
    .replace("*", Token.Operator.MULTIPLY)

/** Use [] for selection */
internal fun textStateInitialSelection(text: String): TextRange {
  val selectionStart = text.indexOf("[")
  val selectionEnd = text.indexOf("]") - 1
  if (selectionStart < 0) throw Exception("forgot selectionStart")
  if (selectionEnd < 0) throw Exception("forgot selectionEnd")

  return TextRange(selectionStart, selectionEnd)
}

internal fun assertOutputTransformation(
  outputTransformation: OutputTransformation,
  expected: String,
  input: String,
) =
  with(outputTransformation) {
    val expectedTextState = textState(expected)
    // Start with clean state since all states are always originally empty
    // Transformation will skip processing tokens if it doesn't notice text changes
    val actualTextState = TextFieldState()
    actualTextState.edit {
      append(textStateInitialText(input))
      selection = textStateInitialSelection(input)
      this.transformOutput()
    }

    assertEquals(expectedTextState.text, actualTextState.text)
    assertEquals(expectedTextState.selection, actualTextState.selection)
  }

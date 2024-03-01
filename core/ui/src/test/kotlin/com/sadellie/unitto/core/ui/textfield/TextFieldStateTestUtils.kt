/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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
import androidx.compose.ui.text.TextRange
import com.sadellie.unitto.core.common.Token
import org.junit.Assert

fun compareTextStates(expected: String, input: String, action: (TextFieldState) -> Unit = {}) {
  val expectedState = textState(expected)
  val actualState = textState(input).apply(action)
  Assert.assertEquals(expectedState.text, actualState.text)
  Assert.assertEquals(expectedState.selection, actualState.selection)
}

// Use [] for selection
private fun textState(text: String = ""): TextFieldState {
  val selectionStart = text.indexOf("[")
  val selectionEnd = text.indexOf("]") - 1

  if (selectionStart < 0) throw Exception("forgot selectionStart")
  if (selectionEnd < 0) throw Exception("forgot selectionEnd")

  return TextFieldState(
    initialText =
      text
        .replace("[", "")
        .replace("]", "")
        .replace("-", Token.Operator.MINUS)
        .replace("/", Token.Operator.DIVIDE)
        .replace("*", Token.Operator.MULTIPLY),
    initialSelection = TextRange(selectionStart, selectionEnd),
  )
}

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.programmer

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.text.TextRange
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class ProgrammerInputTransformationTest {

  @Test
  fun programmerInputTransformation_test() {
    // Grouping symbol as space
    val fs = FormatterSymbols(Token.Space, Token.Period, false)
    val inputTransformation = ProgrammerInputTransformation(fs.grouping)

    fun transformAndCompare(expected: String, input: String) =
      assertInputTransformation(inputTransformation, expected, input)

    // do not break when empty
    transformAndCompare("[]", "[]")

    // Remove grouping symbols
    transformAndCompare("1234567[]", "1 234 567[]")

    // Replace ugly tokens with proper symbols
    transformAndCompare("1−2[]", "1-2[]")
    transformAndCompare("1−2[]", "1−2[]")
    transformAndCompare("1−2[]", "1−2[]")
    transformAndCompare("1÷2[]", "1÷2[]")
    transformAndCompare("1/2[]", "1÷2[]")
    transformAndCompare("1×2[]", "1*2[]")
    transformAndCompare("1×2[]", "1•2[]")
    transformAndCompare("1×2[]", "1×2[]")

    // Replace lowercase letters with uppercase for hex digits
    transformAndCompare("1A2B[]", "1a2b[]")
    transformAndCompare("ABCDEF[]", "abcDef[]")
    transformAndCompare("ABCD[]", "ABCD[]")

    // Do not touch input if everything is ok
    transformAndCompare("1234[]", "1234[]")
    transformAndCompare("1[]234", "1[]234")
    transformAndCompare("ABCDEF[]", "ABCDEF[]")

    // Allow legal tokens
    transformAndCompare("1+2−3×4÷5[]", "1+2−3×4÷5[]")
    transformAndCompare("(1+2)[]", "(1+2)[]")
    transformAndCompare("1or2[]", "1or2[]")
    transformAndCompare("1and2[]", "1and2[]")
    transformAndCompare("not1[]", "not1[]")
    transformAndCompare("1nand2[]", "1nand2[]")
    transformAndCompare("1nor2[]", "1nor2[]")
    transformAndCompare("1xor2[]", "1xor2[]")
    transformAndCompare("1lsh2[]", "1lsh2[]")
    transformAndCompare("1rsh2[]", "1rsh2[]")
    transformAndCompare("1mod2[]", "1mod2[]")

    // Do not allow illegal tokens
    transformAndCompare("123[]", "123g[]")
    transformAndCompare("123[]", "123G[]")
    transformAndCompare("123[]", "123<>[]")
    transformAndCompare("123E[]", "123text[]")
    transformAndCompare("[]", "g[]")
  }

  private fun assertInputTransformation(
    inputTransformation: InputTransformation,
    expected: String,
    input: String,
  ) =
    with(inputTransformation) {
      val expectedTextState = textState(expected)
      // Start with clean state since all states are always originally empty
      // Transformation will skip processing tokens if it doesn't notice text changes
      val actualTextState = TextFieldState()
      actualTextState.edit {
        append(textStateInitialText(input))
        selection = textStateInitialSelection(input)
        this.transformInput()
      }

      assertEquals(expectedTextState.text, actualTextState.text)
      assertEquals(expectedTextState.selection, actualTextState.selection)
    }
}

// duplicate from TextFieldStateTestUtils.kt
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
    .replace("-", Token.Minus.symbol)
    .replace("/", Token.Divide.symbol)
    .replace("*", Token.Multiply.symbol)

/** Use [] for selection */
internal fun textStateInitialSelection(text: String): TextRange {
  val selectionStart = text.indexOf("[")
  val selectionEnd = text.indexOf("]") - 1
  if (selectionStart < 0) throw Exception("forgot selectionStart")
  if (selectionEnd < 0) throw Exception("forgot selectionEnd")

  return TextRange(selectionStart, selectionEnd)
}

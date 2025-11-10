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

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import kotlin.test.Test
import kotlin.test.assertEquals

class TextFieldTransformationTest {

  @Test
  fun expressionInputTransformation_test() {
    // 123.456,789
    val fs = FormatterSymbols(Token.PERIOD, Token.COMMA)
    val inputTransformation = ExpressionInputTransformation(fs)

    fun transformAndCompare(expected: String, input: String) =
      assertInputTransformation(inputTransformation, expected, input)

    // do not break when empty
    transformAndCompare("[]", "[]")

    // Remove formatting symbols
    transformAndCompare("1234.567[]", "1.234,567[]")

    // replace ugly tokens
    transformAndCompare("1−2−3−4÷5×6×7[]", "1-2–3—4/5*6•7[]")
    transformAndCompare("123+345+[]sin⁻¹(678)", "123+345+[]arsin(678)")

    // Don't touch input if everything is ok
    transformAndCompare("1234[]", "1234[]")
    transformAndCompare("1[]234", "1[]234")

    // Fix cursor when inside function token
    transformAndCompare("123+345+[]cos(678)", "123+345+c[]os(678)")
    transformAndCompare("123+345+cos([]678)", "123+345+cos[](678)")

    // Do not allow illegal tokens
    transformAndCompare("123+345+[]", "123+345+c[]os")
    transformAndCompare("123+345+[]cos(678)", "123+345trashsymbols+c[]os(678)")
  }

  @Test
  fun expressionOutputTransformation_test() {
    val fs = FormatterSymbols(Token.PERIOD, Token.COMMA)
    val outputTransformation = ExpressionOutputTransformation(fs)
    fun transformAndCompare(expected: String, input: String) =
      assertOutputTransformation(outputTransformation, expected, input)

    transformAndCompare("[]", "[]")
    transformAndCompare("[]123", "[]123")
    transformAndCompare("12[]3", "12[]3")
    transformAndCompare("[]1,234", "[]1.234")
    transformAndCompare("1.234[]", "1234[]")
    transformAndCompare("[]cos(1)+1,234", "[]cos(1)+1.234")
    transformAndCompare("cos(1)+1,2[]34", "cos(1)+1.2[]34")
    transformAndCompare("cos(1)+123[].456,789", "cos(1)+123[]456.789")
  }

  @Test
  fun numberBaseInputTransformation_test() {
    val inputTransformation = NumberBaseInputTransformation
    fun transformAndCompare(expected: String, input: String) =
      assertInputTransformation(inputTransformation, expected, input)

    transformAndCompare("[]", "[]")
    transformAndCompare("1234567[]", "1234567[]")
    transformAndCompare("1234567[]89", "1234567.[]89")
    transformAndCompare("C123[]E", "cos(123)[]text")
  }

  @Test
  fun numberBaseOutputTransformation_test() {
    val outputTransformation = NumberBaseOutputTransformation
    fun transformAndCompare(expected: String, input: String) =
      assertOutputTransformation(outputTransformation, expected, input)

    transformAndCompare("[]", "[]")
    transformAndCompare("[]123", "[]123")
    transformAndCompare("12[]3", "12[]3")
    transformAndCompare("[]1234", "[]1234")
    transformAndCompare("1234[]", "1234[]")
    transformAndCompare("ABC[]123", "abc[]123")
  }

  @Test
  fun unexpectedDigitsInputTransformation_test() {
    val maxValue = 9999.0
    fun transformAndCompare(expected: String, input: String, allowFraction: Boolean) {
      val inputTransformation = UnexpectedDigitsInputTransformation(maxValue, allowFraction)
      assertInputTransformation(inputTransformation, expected, input)
    }

    transformAndCompare("[]", "[]", true)
    transformAndCompare("[]", "[]", false)
    transformAndCompare("123[]", "123[]", true)
    transformAndCompare("123[]", "123[]", false)
    transformAndCompare("9999[]", "1234567[]", true)
    transformAndCompare("9999[]", "1234567[]", false)
    transformAndCompare("123.4567[]", "123.4567[]", true)
    transformAndCompare("9999[]", "123.4567[]", false)
    transformAndCompare("123.4567[]", "123,4567[]", true)
    transformAndCompare("9999[]", "123,4567[]", false)
    transformAndCompare("123[]", "cos(123)[]text", true)
    transformAndCompare("123[]", "cos(123)[]text", false)
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

  private fun assertOutputTransformation(
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
}

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

import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Expressions are formatted using 2 different APIs: regular strings and OutputTransformation. This
 * does NOT test cursor shifts.
 *
 * @see [TextFieldTransformationTest]
 */
class FormatterExpressionTest {
  @Test
  fun formatExpression_testEng() {
    assertFormatExpression(
      unformatted = "123E+21",
      spaceAndPeriod = "123E+21",
      commaAndPeriod = "123E+21",
      periodAndComma = "123E+21",
    )
  }

  @Test
  fun formatExpression_testEngFractional() {
    assertFormatExpression(
      unformatted = "123.3E+21",
      spaceAndPeriod = "123.3E+21",
      commaAndPeriod = "123.3E+21",
      periodAndComma = "123,3E+21",
    )
  }

  @Test
  fun formatExpression_testEngValueExpression() {
    assertFormatExpression(
      unformatted = "123E+21+(123456.789)",
      spaceAndPeriod = "123E+21+(123 456.789)",
      commaAndPeriod = "123E+21+(123,456.789)",
      periodAndComma = "123E+21+(123.456,789)",
    )
  }

  @Test
  fun formatExpression_testEngFractionalExpression() {
    assertFormatExpression(
      unformatted = "123.3E+21+(123456.789)",
      spaceAndPeriod = "123.3E+21+(123 456.789)",
      commaAndPeriod = "123.3E+21+(123,456.789)",
      periodAndComma = "123,3E+21+(123.456,789)",
    )
  }

  @Test
  fun formatExpression_testComplete() {
    assertFormatExpression(
      unformatted = "123456.789",
      spaceAndPeriod = "123 456.789",
      commaAndPeriod = "123,456.789",
      periodAndComma = "123.456,789",
    )
  }

  @Test
  fun formatExpression_testIncomplete() {
    assertFormatExpression(
      unformatted = "123456.",
      spaceAndPeriod = "123 456.",
      commaAndPeriod = "123,456.",
      periodAndComma = "123.456,",
    )
  }

  @Test
  fun formatExpression_testInvalid() {
    // never format invalid numbers
    assertFormatExpression(
      unformatted = "123456..78",
      spaceAndPeriod = "123456..78",
      commaAndPeriod = "123456..78",
      periodAndComma = "123456..78",
    )
  }

  @Test
  fun formatExpression_testNoFractional() {
    assertFormatExpression(
      unformatted = "123456",
      spaceAndPeriod = "123 456",
      commaAndPeriod = "123,456",
      periodAndComma = "123.456",
    )
  }

  @Test
  fun formatExpression_testIncompleteExpression() {
    assertFormatExpression(
      unformatted = "50+123456÷8×0.8-12+",
      spaceAndPeriod = "50+123 456÷8×0.8−12+",
      commaAndPeriod = "50+123,456÷8×0.8−12+",
      periodAndComma = "50+123.456÷8×0,8−12+",
    )
  }

  @Test
  fun formatExpression_testCompleteExpression() {
    assertFormatExpression(
      unformatted = "50+123456÷8×0.8-12+0-√9×4^9+2×(9+8×7)",
      spaceAndPeriod = "50+123 456÷8×0.8−12+0−√9×4^9+2×(9+8×7)",
      commaAndPeriod = "50+123,456÷8×0.8−12+0−√9×4^9+2×(9+8×7)",
      periodAndComma = "50+123.456÷8×0,8−12+0−√9×4^9+2×(9+8×7)",
    )
  }

  @Test
  fun formatExpression_testLongHalfCompleteExpression() {
    assertFormatExpression(
      unformatted = "50+123456÷89078.9×0.8-12+0-√9×4^9+2×(9+8×7)×sin(13sin123cos",
      spaceAndPeriod = "50+123 456÷89 078.9×0.8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos",
      commaAndPeriod = "50+123,456÷89,078.9×0.8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos",
      periodAndComma = "50+123.456÷89.078,9×0,8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos",
    )
  }

  @Test
  fun formatExpression_testSomeBrackets() {
    assertFormatExpression(
      unformatted = "((((((((",
      spaceAndPeriod = "((((((((",
      commaAndPeriod = "((((((((",
      periodAndComma = "((((((((",
    )
  }

  @Test
  fun formatExpression_testFractional() {
    assertFormatExpression(
      unformatted = "1600 1234⁄56789",
      spaceAndPeriod = "1 600 1234⁄56789",
      commaAndPeriod = "1,600 1234⁄56789",
      periodAndComma = "1.600 1234⁄56789",
    )
  }

  private fun assertFormatExpression(
    unformatted: String,
    spaceAndPeriod: String,
    commaAndPeriod: String,
    periodAndComma: String,
  ) {
    assertEquals(
      spaceAndPeriod,
      unformatted.formatExpression(FormatterSymbols(Token.SPACE, Token.PERIOD)),
    )
    assertEquals(
      commaAndPeriod,
      unformatted.formatExpression(FormatterSymbols(Token.COMMA, Token.PERIOD)),
    )
    assertOutputTransformation(
      outputTransformation =
        ExpressionOutputTransformation(FormatterSymbols(Token.COMMA, Token.PERIOD)),
      expected = "[]$commaAndPeriod", // set fake cursor at start as it is not tested here
      input = "[]$unformatted",
    )
    assertEquals(
      periodAndComma,
      unformatted.formatExpression(FormatterSymbols(Token.PERIOD, Token.COMMA)),
    )
  }
}

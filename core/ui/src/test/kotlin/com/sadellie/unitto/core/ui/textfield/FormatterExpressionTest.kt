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

import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import org.junit.Assert
import org.junit.Test

private const val ENG_VALUE = "123E+21"
private const val ENG_VALUE_FRACTIONAL = "123.3E+21"
private const val ENG_VALUE_EXPRESSION = "123E+21+(123456.789)"
private const val ENG_VALUE_FRACTIONAL_EXPRESSION = "123.3E+21+(123456.789)"
private const val COMPLETE_VALUE = "123456.789"
private const val INCOMPLETE_VALUE = "123456."
private const val NO_FRACTIONAL_VALUE = "123456"
private const val INCOMPLETE_EXPR = "50+123456÷8×0.8-12+"
private const val COMPLETE_EXPR = "50+123456÷8×0.8-12+0-√9×4^9+2×(9+8×7)"
private const val LONG_HALF_COMPLETE_EXPR =
  "50+123456÷89078..9×0.8-12+0-√9×4^9+2×(9+8×7)×sin(13sin123cos"
private const val SOME_BRACKETS = "(((((((("
private const val FRACTION_VALUE = "1600 1234⁄56789"

class FormatterExpressionTest {

  @Test
  fun formatExpression_spaceAndPeriod() {
    fun String.format(): String = formatExpression(FormatterSymbols(Token.SPACE, Token.PERIOD))
    Assert.assertEquals("123E+21", ENG_VALUE.format())
    Assert.assertEquals("123.3E+21", ENG_VALUE_FRACTIONAL.format())
    Assert.assertEquals("123E+21+(123 456.789)", ENG_VALUE_EXPRESSION.format())
    Assert.assertEquals("123.3E+21+(123 456.789)", ENG_VALUE_FRACTIONAL_EXPRESSION.format())
    Assert.assertEquals("123 456.789", COMPLETE_VALUE.format())
    Assert.assertEquals("123 456.", INCOMPLETE_VALUE.format())
    Assert.assertEquals("123 456", NO_FRACTIONAL_VALUE.format())
    Assert.assertEquals("50+123 456÷8×0.8−12+", INCOMPLETE_EXPR.format())
    Assert.assertEquals("50+123 456÷8×0.8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
    Assert.assertEquals(
      "50+123 456÷89 078..9×0.8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos",
      LONG_HALF_COMPLETE_EXPR.format(),
    )
    Assert.assertEquals("((((((((", SOME_BRACKETS.format())
    Assert.assertEquals("1 600 1234⁄56789", FRACTION_VALUE.format())
  }

  @Test
  fun formatExpression_commaAndPeriod() {
    fun String.format(): String = formatExpression(FormatterSymbols(Token.COMMA, Token.PERIOD))
    Assert.assertEquals("123E+21", ENG_VALUE.format())
    Assert.assertEquals("123.3E+21", ENG_VALUE_FRACTIONAL.format())
    Assert.assertEquals("123E+21+(123,456.789)", ENG_VALUE_EXPRESSION.format())
    Assert.assertEquals("123.3E+21+(123,456.789)", ENG_VALUE_FRACTIONAL_EXPRESSION.format())
    Assert.assertEquals("123,456.789", COMPLETE_VALUE.format())
    Assert.assertEquals("123,456.", INCOMPLETE_VALUE.format())
    Assert.assertEquals("123,456", NO_FRACTIONAL_VALUE.format())
    Assert.assertEquals("50+123,456÷8×0.8−12+", INCOMPLETE_EXPR.format())
    Assert.assertEquals("50+123,456÷8×0.8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
    Assert.assertEquals(
      "50+123,456÷89,078..9×0.8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos",
      LONG_HALF_COMPLETE_EXPR.format(),
    )
    Assert.assertEquals("((((((((", SOME_BRACKETS.format())
    Assert.assertEquals("1,600 1234⁄56789", FRACTION_VALUE.format())
  }

  @Test
  fun formatExpression_periodAndComma() {
    fun String.format(): String = formatExpression(FormatterSymbols(Token.PERIOD, Token.COMMA))
    Assert.assertEquals("123E+21", ENG_VALUE.format())
    Assert.assertEquals("123,3E+21", ENG_VALUE_FRACTIONAL.format())
    Assert.assertEquals("123E+21+(123.456,789)", ENG_VALUE_EXPRESSION.format())
    Assert.assertEquals("123,3E+21+(123.456,789)", ENG_VALUE_FRACTIONAL_EXPRESSION.format())
    Assert.assertEquals("123.456,789", COMPLETE_VALUE.format())
    Assert.assertEquals("123.456,", INCOMPLETE_VALUE.format())
    Assert.assertEquals("123.456", NO_FRACTIONAL_VALUE.format())
    Assert.assertEquals("50+123.456÷8×0,8−12+", INCOMPLETE_EXPR.format())
    Assert.assertEquals("50+123.456÷8×0,8−12+0−√9×4^9+2×(9+8×7)", COMPLETE_EXPR.format())
    Assert.assertEquals(
      "50+123.456÷89.078,,9×0,8−12+0−√9×4^9+2×(9+8×7)×sin(13sin123cos",
      LONG_HALF_COMPLETE_EXPR.format(),
    )
    Assert.assertEquals("((((((((", SOME_BRACKETS.format())
    Assert.assertEquals("1.600 1234⁄56789", FRACTION_VALUE.format())
  }
}

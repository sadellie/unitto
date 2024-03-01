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

package com.sadellie.unitto.core.common

import org.junit.Assert
import org.junit.Test

/**
 * This tests for accidental changes. Tokens uses special characters and sometimes it's difficult
 * notice difference manually/visually.
 */
class TokenTest {

  @Test
  fun formatterSymbols_test() {
    Assert.assertEquals(" ", Token.SPACE)
    Assert.assertEquals(".", Token.PERIOD)
    Assert.assertEquals(",", Token.COMMA)
  }

  @Test
  fun digit_test() {
    Assert.assertEquals("1234567890", Token.Digit.all.joinToString(""))
    Assert.assertEquals("1234567890.", Token.Digit.allWithDot.joinToString(""))
  }

  @Test
  fun letter_test() {
    Assert.assertEquals("ABCDEF", Token.Letter.all.joinToString(""))
  }

  @Test
  fun operator_test() {
    listOf("+", "−", "×", "÷", "(", ")", "^", "!", "#", "%", "√").forEach {
      assert(it in Token.Operator.all)
    }
  }

  @Test
  fun func_test() {
    listOf("sin", "cos", "tan", "sin⁻¹", "cos⁻¹", "tan⁻¹", "ln", "log", "exp").forEach {
      assert(it in Token.Func.all)
    }

    listOf("sin(", "cos(", "tan(", "sin⁻¹(", "cos⁻¹(", "tan⁻¹(", "ln(", "log(", "exp(").forEach {
      assert(it in Token.Func.allWithOpeningBracket)
    }
  }

  @Test
  fun const_test() {
    listOf("π", "e").forEach { assert(it in Token.Const.all) }
  }

  @Test
  fun expressionTokens_test() {
    val operator = listOf("+", "−", "×", "÷", "(", ")", "^", "!", "#", "%", "√").joinToString("")

    val func =
      listOf("sin⁻¹", "cos⁻¹", "tan⁻¹", "sin", "cos", "tan", "log", "exp", "ln").joinToString("")

    val consts = listOf("π", "e").joinToString("")

    Assert.assertEquals(
      "1234567890.$operator$func${consts}E",
      Token.expressionTokens.joinToString(""),
    )
  }

  @Test
  fun numberBaseTokens_test() {
    Assert.assertEquals("1234567890ABCDEF", Token.numberBaseTokens.joinToString(""))
  }

  @Test
  fun displayOnlyObject_test() {
    Assert.assertNotNull(Token.DisplayOnly)
  }

  @Test
  fun sexyToUgly_test() {
    listOf("−", "÷", "×", "sin⁻¹", "cos⁻¹", "tan⁻¹").forEach { assert(it in Token.sexyToUgly.keys) }
  }
}

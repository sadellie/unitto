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

package com.sadellie.unitto.core.common

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * This tests for accidental changes. Tokens uses special characters and sometimes it's difficult
 * notice difference manually/visually.
 */
class TokenTest {

  @Test
  fun formatterSymbols_test() {
    assertEquals(" ", Token.SPACE)
    assertEquals(".", Token.PERIOD)
    assertEquals(",", Token.COMMA)
  }

  @Test
  fun digit_test() {
    assertEquals("1234567890", Token.Digit.all.joinToString(""))
    assertEquals("1234567890.", Token.Digit.allWithDot.joinToString(""))
  }

  @Test
  fun letter_test() {
    assertEquals("ABCDEF", Token.Letter.all.joinToString(""))
  }

  @Test
  fun operator_test() {
    listOf("+", "−", "×", "÷", "(", ")", "^", "!", "#", "%", "√").forEach {
      assertContains(Token.Operator.all, it)
    }
  }

  @Test
  fun func_test() {
    listOf("sin", "cos", "tan", "sin⁻¹", "cos⁻¹", "tan⁻¹", "ln", "log", "exp").forEach {
      assertContains(Token.Func.all, it)
    }

    listOf("sin(", "cos(", "tan(", "sin⁻¹(", "cos⁻¹(", "tan⁻¹(", "ln(", "log(", "exp(").forEach {
      assertContains(Token.Func.allWithOpeningBracket, it)
    }
  }

  @Test
  fun const_test() {
    listOf("π", "e").forEach { assertContains(Token.Const.all, it) }
  }

  @Test
  fun expressionTokens_test() {
    val operator = listOf("+", "−", "×", "÷", "(", ")", "^", "!", "#", "%", "√").joinToString("")

    val func =
      listOf("sin⁻¹", "cos⁻¹", "tan⁻¹", "sin", "cos", "tan", "log", "exp", "ln").joinToString("")

    val consts = listOf("π", "e").joinToString("")

    assertEquals("1234567890.$operator$func${consts}E", Token.expressionTokens.joinToString(""))
  }

  @Test
  fun numberBaseTokens_test() {
    assertEquals("1234567890ABCDEF", Token.numberBaseTokens.joinToString(""))
  }

  @Test
  fun displayOnlyObject_test() {
    assertNotNull(Token.DisplayOnly)
  }

  @Test
  fun sexyToUgly_test() {
    listOf("−", "÷", "×", "sin⁻¹", "cos⁻¹", "tan⁻¹").forEach {
      assertContains(Token.sexyToUgly.keys, it)
    }
  }
}

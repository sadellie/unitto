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

package com.sadellie.unitto.core.common

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

/**
 * This tests for accidental changes. Tokens uses special characters, and sometimes it's difficult
 * notice difference manually/visually.
 */
class TokenTest {
  @Test
  fun expressionTokens_test() {
    val operator = listOf("+", "−", "×", "÷", "^", "!", "#", "%", "√").joinToString("")
    val func =
      listOf("sin⁻¹", "cos⁻¹", "tan⁻¹", "sin", "cos", "tan", "log", "exp", "ln").joinToString("")
    val consts = listOf("π", "e").joinToString("")

    assertEquals(
      "$func$operator${consts}E()",
      Token.parseableMathTokens.joinToString("") { it.symbol },
    )
  }

  @Test
  fun sexyToUgly_test() {
    listOf("−", "÷", "×", "sin⁻¹", "cos⁻¹", "tan⁻¹").forEach {
      assertContains(Token.sexyToUgly.keys, it)
    }
  }
}

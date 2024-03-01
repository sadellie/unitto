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

package com.sadellie.unitto.feature.graphing

import org.junit.Assert.assertEquals
import org.junit.Test

class IsValidFunctionExpressionTest {
  @Test
  fun passValid() {
    val input = "cos(x)789+exp(1)"
    assertEquals(true, isValidFunctionExpression(input))
  }

  @Test
  fun failInvalid1() {
    val input = "cos()"
    assertEquals(false, isValidFunctionExpression(input))
  }

  @Test
  fun failInvalid2() {
    val input = "x÷0"
    assertEquals(true, isValidFunctionExpression(input))
  }
}

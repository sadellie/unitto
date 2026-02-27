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

package io.github.sadellie.evaluatto.math

import kotlin.test.Test
import kotlin.test.assertEquals

class OperationGenerateExpressionTest {
  @Test
  fun `both positive`() {
    val input = "1"
    val value2 = "2"
    assertEquals("1+2", Operation.Plus(value2).generateExpression(input))
    assertEquals("1−2", Operation.Minus(value2).generateExpression(input))
    assertEquals("1×2", Operation.Multiply(value2).generateExpression(input))
    assertEquals("1÷2", Operation.Divide(value2).generateExpression(input))
  }

  @Test
  fun `positive negative`() {
    val input = "1"
    val value2 = "−2"
    assertEquals("1×−2", Operation.Multiply(value2).generateExpression(input))
    assertEquals("1÷−2", Operation.Divide(value2).generateExpression(input))
  }
}

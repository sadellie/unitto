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

package io.github.sadellie.evaluatto.ast

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.Token2
import io.github.sadellie.evaluatto.tokenize
import kotlin.test.Test
import kotlin.test.assertEquals

class ASTBuilderTest {
  private val cx = ScriptContext(scale = 2)

  @Test fun build_empty() = assertTree("", null)

  @Test
  fun build_numberSingle() = assertTree("1", NumberNode(KBigDecimal("1.00"), Token2.Number("1")))

  @Test
  fun build_numberSingleWithDot() =
    assertTree("1.2", NumberNode(KBigDecimal("1.20"), Token2.Number("1.2")))

  @Test
  fun build_numberOnlyDot() = assertTree(".", NumberNode(KBigDecimal("0.00"), Token2.Number(".")))

  @Test
  fun build_numberStartWithDot() =
    assertTree(".2", NumberNode(KBigDecimal("0.20"), Token2.Number(".2")))

  @Test
  fun build_numberNegative() =
    assertTree(
      "−.2",
      UnaryOperatorNode(Token2.UnaryMinus, NumberNode(KBigDecimal("0.20"), Token2.Number(".2"))),
    )

  private fun assertTree(input: String, expected: ASTNode?) {
    val tokens = input.tokenize()
    val tree = ASTBuilder(tokens).build(cx).firstOrNull()
    assertEquals(expected, tree)
  }
}

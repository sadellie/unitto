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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.Token
import io.github.sadellie.evaluatto.ast.ASTNode
import io.github.sadellie.evaluatto.ast.MathNumberNode
import io.github.sadellie.evaluatto.ast.PlusNode
import io.github.sadellie.evaluatto.ast.ScriptContext
import io.github.sadellie.evaluatto.ast.UnaryMinusNode
import kotlin.test.Test
import kotlin.test.assertEquals

class MathASTBuilderTest {
  private val cx = ScriptContext.Math(scale = 2)

  @Test fun build_empty() = assertTree("", null)

  @Test
  fun build_numberSingle() = assertTree("1", MathNumberNode(KBigDecimal("1.00"), Token.Number("1")))

  @Test
  fun build_numberSingleWithDot() =
    assertTree("1.2", MathNumberNode(KBigDecimal("1.20"), Token.Number("1.2")))

  @Test
  fun build_numberOnlyDot() =
    assertTree(".", MathNumberNode(KBigDecimal("0.00"), Token.Number(".")))

  @Test
  fun build_numberStartWithDot() =
    assertTree(".2", MathNumberNode(KBigDecimal("0.20"), Token.Number(".2")))

  @Test
  fun build_numberNegative() =
    assertTree("−.2", UnaryMinusNode(MathNumberNode(KBigDecimal("0.20"), Token.Number(".2"))))

  @Test
  fun build_expressionWithUnary() =
    assertTree(
      "1+−2",
      PlusNode(
        MathNumberNode(KBigDecimal("1.00"), Token.Number("1")),
        UnaryMinusNode(MathNumberNode(KBigDecimal("2.00"), Token.Number("2"))),
      ),
    )

  private fun assertTree(input: String, expected: ASTNode?) {
    val tokens = input.tokenizeMath()
    println(tokens)
    val tree = ASTMathBuilder(tokens).build(cx).firstOrNull()
    assertEquals(expected, tree)
  }
}

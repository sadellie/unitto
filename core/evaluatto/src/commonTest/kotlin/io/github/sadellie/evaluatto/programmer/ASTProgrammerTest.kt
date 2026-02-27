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

package io.github.sadellie.evaluatto.programmer

import com.sadellie.unitto.core.common.Token
import io.github.sadellie.evaluatto.ast.ASTNode
import io.github.sadellie.evaluatto.ast.BracketsNode
import io.github.sadellie.evaluatto.ast.PlusNode
import io.github.sadellie.evaluatto.ast.ProgrammerNumberNode
import io.github.sadellie.evaluatto.ast.ProgrammerOperatorNode
import io.github.sadellie.evaluatto.ast.ScriptContext
import io.github.sadellie.evaluatto.ast.UnaryMinusNode
import io.github.sadellie.evaluatto.ast.UnaryNotNode
import kotlin.test.Test
import kotlin.test.assertEquals

class ProgrammerASTBuilderTest {
  @Test fun empty() = assertBuildTree("", null, cx(2))

  @Test
  fun `simple number base10`() {
    val context = cx(10)
    assertBuildTree("123", ProgrammerNumberNode(Token.Number("123"), context), context)
  }

  @Test
  fun `simple expression base16`() {
    val context = cx(16)
    assertBuildTree(
      "123+456ABC",
      PlusNode(
        ProgrammerNumberNode(Token.Number("123"), context),
        ProgrammerNumberNode(Token.Number("456ABC"), context),
      ),
      context,
    )
  }

  @Test
  fun `simple expression with minus`() {
    val context = cx(16)
    assertBuildTree(
      "123−456ABC",
      PlusNode(
        ProgrammerNumberNode(Token.Number("123"), context),
        UnaryMinusNode(ProgrammerNumberNode(Token.Number("456ABC"), context)),
      ),
      context,
    )
  }

  @Test
  fun `simple expression with unary not base16`() {
    val context = cx(16)
    assertBuildTree(
      "123+not456ABC",
      PlusNode(
        ProgrammerNumberNode(Token.Number("123"), context),
        UnaryNotNode(ProgrammerNumberNode(Token.Number("456ABC"), context)),
      ),
      context,
    )
  }

  @Test
  fun `complex expression with minus`() {
    val context = cx(16)
    assertBuildTree(
      "123−(456ABCxor789EDF)",
      PlusNode(
        ProgrammerNumberNode(Token.Number("123"), context),
        UnaryMinusNode(
          BracketsNode(
            ProgrammerOperatorNode(
              Token.Xor,
              ProgrammerNumberNode(Token.Number("456ABC"), context),
              ProgrammerNumberNode(Token.Number("789EDF"), context),
            )
          )
        ),
      ),
      context,
    )
  }

  @Test
  fun `collapse brackets`() {
    val context = cx(16)
    assertCollapseTree(
      PlusNode(
        ProgrammerNumberNode(Token.Number("123"), context),
        UnaryMinusNode(
          BracketsNode(
            ProgrammerOperatorNode(
              Token.Xor,
              ProgrammerNumberNode(Token.Number("456ABC"), context),
              ProgrammerNumberNode(Token.Number("789EDF"), context),
            )
          )
        ),
      ),
      PlusNode(
        ProgrammerNumberNode(Token.Number("123"), context),
        UnaryMinusNode(
          ProgrammerOperatorNode(
            Token.Xor,
            ProgrammerNumberNode(Token.Number("456ABC"), context),
            ProgrammerNumberNode(Token.Number("789EDF"), context),
          )
        ),
      ),
      context,
    )
  }

  private fun assertBuildTree(
    input: String,
    expected: ASTNode?,
    context: ScriptContext.Programmer,
  ) {
    val tokens = input.tokenizeProgrammer()
    val tree = ProgrammerASTBuilder(tokens).build(context).firstOrNull()
    assertEquals(expected, tree)
  }

  private fun assertCollapseTree(
    input: ASTNode,
    expected: ASTNode,
    context: ScriptContext.Programmer,
  ) {
    assertEquals(expected, input.collapse(context))
  }

  private fun cx(base: Int, dataUnit: DataUnit = DataUnit.QWORD) =
    ScriptContext.Programmer(base = base, dataUnit = dataUnit)
}

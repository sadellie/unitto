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

package io.github.sadellie.evaluatto.ast

import io.github.sadellie.evaluatto.ExpressionException
import io.github.sadellie.evaluatto.TokenizerException
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest

class ASTExpressionExceptionsTest {

  @Test fun `divide by zero`() = assertASTExprFail(ExpressionException.DivideByZero(), "2÷0")

  @Test
  fun `indeterminate 0 to the power of 0`() =
    assertASTExprFail(ExpressionException.BadExpression(), "0^0")

  @Test
  fun `factorial of float`() = assertASTExprFail(ExpressionException.FactorialCalculation(), "3.2!")

  @Test
  fun `factorial of negative`() =
    assertASTExprFail(ExpressionException.FactorialCalculation(), "−5!")

  @Test
  fun `factorial of negative2`() =
    assertASTExprFail(ExpressionException.FactorialCalculation(), "(−5)!")

  @Test
  fun `ugly ahh expression`() = assertASTExprFail(ExpressionException.BadExpression(), "100+cos()")

  @Test
  fun `ugly ahh expression2`() =
    assertASTExprFail(TokenizerException.TooManyFractionSymbols(), "...")

  @Test
  fun `ugly ahh expression3`() =
    assertASTExprFail(TokenizerException.BadScientificNotation(), "2.5E-")

  @Test
  fun `ugly ahh expression4`() =
    assertASTExprFail(TokenizerException.BadScientificNotation(), "2.5E")

  @Test
  fun `ugly ahh expression5`() =
    assertASTExprFail(TokenizerException.BadScientificNotation(), "2.5E÷")

  @Test fun `too big`() = assertASTExprFail(ExpressionException.TooBig(), "999999!")

  private fun <T : Throwable> assertASTExprFail(
    expectedThrowable: T,
    expr: String,
    radianMode: Boolean = true,
  ) = runTest {
    assertFailsWith(expectedThrowable::class) { calculateExpression(expr, radianMode = radianMode) }
  }
}

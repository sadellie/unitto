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

package io.github.sadellie.evaluatto

import org.junit.Test

class ExpressionExceptionsTest {

    @Test
    fun `divide by zero`() = assertExprFail(ExpressionException.DivideByZero::class.java, "2÷0")

    @Test
    fun `factorial of float`() = assertExprFail(ExpressionException.FactorialCalculation::class.java, "3.2!")

    @Test
    fun `factorial of negative`() = assertExprFail(ExpressionException.FactorialCalculation::class.java, "−5!")

    @Test
    fun `factorial of negative2`() = assertExprFail(ExpressionException.FactorialCalculation::class.java, "(−5)!")

    @Test
    fun `ugly ahh expression`() = assertExprFail(ExpressionException.BadExpression::class.java, "100+cos()")

    @Test
    fun `ugly ahh expression2`() = assertExprFail(TokenizerException.TooManyFractionSymbols::class.java, "...")

    @Test
    fun `ugly ahh expression3`() = assertExprFail(TokenizerException.BadScientificNotation::class.java, "2.5E-")

    @Test
    fun `ugly ahh expression4`() = assertExprFail(TokenizerException.BadScientificNotation::class.java, "2.5E")

    @Test
    fun `ugly ahh expression5`() = assertExprFail(TokenizerException.BadScientificNotation::class.java, "2.5E÷")

    @Test
    fun `too big`() = assertExprFail(ExpressionException.TooBig::class.java, "999999!")
}

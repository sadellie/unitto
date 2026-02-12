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

import kotlin.test.Test

class ASTExpressionComplexTest {
  @Test fun expression1() = assertASTExpr("94×π×89×cos(0.5)−3!÷9^(2)×√8", "23064.9104578494")

  @Test fun expression2() = assertASTExpr("√(25)×2+10÷2", "15")

  @Test fun expression3() = assertASTExpr("(3+4)×(5−2)", "21")

  @Test fun expression4() = assertASTExpr("8÷4+2×3", "8")

  @Test fun expression5() = assertASTExpr("2^3+4^2−5×6", "-6")

  @Test fun expression6() = assertASTExpr("(10−2)^2÷8+3×2", "14")

  @Test fun expression7() = assertASTExpr("7!÷3!−5!÷2!", "780")

  @Test fun expression8() = assertASTExpr("(2^2+3^3)÷5−√(16)×2", "-1.8")

  @Test fun expression9() = assertASTExpr("10×log(100)+2^4−3^2", "27")

  @Test fun expression10() = assertASTExpr("sin(π÷3)×cos(π÷6)+tan(π÷4)−√3", "0.0179491924")

  @Test fun expression11() = assertASTExpr("2^6−2^5+2^4−2^3+2^−2^1+2^0", "41.25")

  @Test fun expression12() = assertASTExpr("2×(3+4)×(5−2)÷6", "7")

  @Test fun expression13() = assertASTExpr("√64÷5", "1.6")

  @Test fun expression14() = assertASTExpr("4160×3.1%", "128.96")

  @Test fun expression15() = assertASTExpr("4160×3.13%", "130.208")

  @Test fun expression16() = assertASTExpr("10−2", "8")

  @Test fun expression17() = assertASTExpr("100+500", "600")

  @Test fun expression18() = assertASTExpr("2×5", "10")

  // TODO UNCOMMENT FOR BENCHMARK TESTS

  //  @Test
  //  fun bench() = runTest {
  //    val input = "100+500"
  //    val duration = measureTime { repeat(1_000_000) { Expression(input).calculate() } }
  //    println(duration.inWholeMilliseconds)
  //  }
  //
  //  @Test
  //  fun bench2() = runTest {
  //    val input = "100+500"
  //    val duration = measureTime { repeat(1_000_000) { calculateExpression(input) } }
  //    println(duration.inWholeMilliseconds)
  //  }
}

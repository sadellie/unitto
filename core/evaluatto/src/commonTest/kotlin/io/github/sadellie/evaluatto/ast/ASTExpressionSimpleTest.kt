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

class ASTExpressionSimpleTest {
  @Test fun expression0() = assertASTExpr("", "0")

  @Test fun expression1() = assertASTExpr("789", "789")

  @Test fun expression2() = assertASTExpr("0.1+0.2", "0.3")

  @Test fun expression3() = assertASTExpr(".1+.2", "0.3")

  @Test fun expression4() = assertASTExpr("789+200", "989")

  @Test fun expression5() = assertASTExpr("600×7.89", "4734")

  @Test fun expression6() = assertASTExpr("600÷7", "85.7142857143")

  @Test fun expression7() = assertASTExpr("(200+200)×200", "80000")

  @Test fun expression8() = assertASTExpr("99^5", "9509900499")

  @Test fun expression9() = assertASTExpr("12!", "479001600")

  @Test fun expression10() = assertASTExpr("12#5", "2")

  @Test fun `125 plus 9 percent`() = assertASTExpr("125+9%", "136.25")

  @Test fun expression11() = assertASTExpr("12×√5", "26.83281573")

  @Test fun expression12() = assertASTExpr("sin(42)", "-0.9165215479")

  @Test fun expression13() = assertASTExpr("sin(42)", "0.6691306064", radianMode = false)

  @Test fun expression14() = assertASTExpr("cos(42)", "-0.3999853150")

  @Test fun expression15() = assertASTExpr("cos(42)", "0.7431448255", radianMode = false)

  @Test fun expression16() = assertASTExpr("tan(42)", "2.2913879924")

  @Test fun expression17() = assertASTExpr("tan(42)", "0.9004040443", radianMode = false)

  @Test fun expression18() = assertASTExpr("sin⁻¹(.69)", "0.7614890527")

  @Test fun expression19() = assertASTExpr("sin⁻¹(.69)", "43.6301088679", radianMode = false)

  @Test fun expression20() = assertASTExpr("cos⁻¹(.69)", "0.8093072740")

  @Test fun expression21() = assertASTExpr("cos⁻¹(.69)", "46.3698911321", radianMode = false)

  @Test fun expression22() = assertASTExpr("tan⁻¹(.69)", "0.6039829783")

  @Test fun expression23() = assertASTExpr("tan⁻¹(.69)", "34.6056755516", radianMode = false)

  @Test fun expression24() = assertASTExpr("ln(.69)", "-0.3710636814")

  @Test fun expression25() = assertASTExpr("log(.69)", "-0.1611509093")

  @Test fun expression26() = assertASTExpr("exp(3)", "20.0855369232")

  @Test fun expression27() = assertASTExpr("π", "3.1415926536")

  @Test fun expression28() = assertASTExpr("e", "2.7182818285")

  @Test fun expression29() = assertASTExpr("0!", "1")

  @Test fun expression30() = assertASTExpr("cos(π)", "-1.0000000000")

  @Test fun expression31() = assertASTExpr("sin(2π)", "0.0000000000", radianMode = true)

  @Test fun expression32() = assertASTExpr("tan(π)", "0.0000000000", radianMode = true)
}

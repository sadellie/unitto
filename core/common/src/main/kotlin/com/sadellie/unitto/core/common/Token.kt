/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

object Token {
  const val SPACE = " "
  const val PERIOD = "."
  const val COMMA = ","

  object Digit {
    const val DIGIT_1 = "1"
    const val DIGIT_2 = "2"
    const val DIGIT_3 = "3"
    const val DIGIT_4 = "4"
    const val DIGIT_5 = "5"
    const val DIGIT_6 = "6"
    const val DIGIT_7 = "7"
    const val DIGIT_8 = "8"
    const val DIGIT_9 = "9"
    const val DIGIT_0 = "0"
    const val DOT = "."

    val all by lazy { listOf(DIGIT_1, DIGIT_2, DIGIT_3, DIGIT_4, DIGIT_5, DIGIT_6, DIGIT_7, DIGIT_8, DIGIT_9, DIGIT_0) }
    val allWithDot by lazy { all + DOT }
  }

  object Letter {
    const val LETTER_A = "A"
    const val LETTER_B = "B"
    const val LETTER_C = "C"
    const val LETTER_D = "D"
    const val LETTER_E = "E"
    const val LETTER_F = "F"

    val all by lazy { listOf(LETTER_A, LETTER_B, LETTER_C, LETTER_D, LETTER_E, LETTER_F) }
  }

  object Operator {
    const val PLUS = "+"
    const val MINUS = "−" // MINUS SIGN, not a regular minus (HYPHEN-MINUS)
    const val MULTIPLY = "×"
    const val DIVIDE = "÷"
    const val LEFT_BRACKET = "("
    const val RIGHT_BRACKET = ")"
    const val POWER = "^"
    const val FACTORIAL = "!"
    const val MODULO = "#"
    const val PERCENT = "%"
    const val SQRT = "√"

    val all by lazy {
      listOf(
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        POWER,
        FACTORIAL,
        MODULO,
        PERCENT,
        SQRT,
      )
    }
  }

  object Func {
    const val SIN = "sin"
    const val SIN_BRACKET = "$SIN("
    const val COS = "cos"
    const val COS_BRACKET = "$COS("
    const val TAN = "tan"
    const val TAN_BRACKET = "$TAN("
    const val ARSIN = "sin⁻¹"
    const val ARSIN_BRACKET = "$ARSIN("
    const val ARCOS = "cos⁻¹"
    const val ARCOS_BRACKET = "$ARCOS("
    const val ACTAN = "tan⁻¹"
    const val ACTAN_BRACKET = "$ACTAN("
    const val LN = "ln"
    const val LN_BRACKET = "$LN("
    const val LOG = "log"
    const val LOG_BRACKET = "$LOG("
    const val EXP = "exp"
    const val EXP_BRACKET = "$EXP("

    val all by lazy {
      listOf(ARSIN, ARCOS, ACTAN, SIN, COS, TAN, LOG, EXP, LN).sortedByDescending { it.length }
    }

    val allWithOpeningBracket by lazy {
      listOf(
        ARSIN_BRACKET,
        ARCOS_BRACKET,
        ACTAN_BRACKET,
        SIN_BRACKET,
        COS_BRACKET,
        TAN_BRACKET,
        LOG_BRACKET,
        EXP_BRACKET,
        LN_BRACKET,
      )
    }
  }

  object Const {
    const val PI = "π"
    const val E = "e"

    val all by lazy { listOf(PI, E) }
  }

  // Used only in formatter, don't use internally
  object DisplayOnly {
    const val ENGINEERING_E = "E"
    const val MINUS = "−"
    const val FRACTION = "⁄"
  }

  val expressionTokens by lazy {
    Digit.allWithDot + Operator.all + Func.all + Const.all + DisplayOnly.ENGINEERING_E
  }

  val numberBaseTokens by lazy { Digit.all + Letter.all }

  val sexyToUgly by lazy {
    mapOf(
      Operator.MINUS to listOf("-", "–", "—", "—"),
      Operator.DIVIDE to listOf("/"),
      Operator.MULTIPLY to listOf("*", "•"),
      Func.ARSIN to listOf("arsin"),
      Func.ARCOS to listOf("arcos"),
      Func.ACTAN to listOf("actan"),
    )
  }
}

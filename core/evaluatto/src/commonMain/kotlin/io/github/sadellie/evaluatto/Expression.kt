/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KBigDecimalMath
import com.sadellie.unitto.core.common.KMathContext
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.MAX_SCALE
import com.sadellie.unitto.core.common.Token2
import com.sadellie.unitto.core.common.isEqualTo

sealed class ExpressionException(override val message: String) : Exception(message) {
  class DivideByZero : ExpressionException("Can't divide by zero")

  class FactorialCalculation :
    ExpressionException("Can calculate factorial of non-negative real numbers only")

  class BadExpression :
    ExpressionException("Invalid expression. Operator lacks argument or missing closing bracket")

  class TooBig : ExpressionException("Value is too big")
}

class Expression(
  input: String,
  private val radianMode: Boolean = true,
  private val roundingMode: KRoundingMode = KRoundingMode.HALF_EVEN,
) {
  private val scale = MAX_SCALE
  private val mathContext = KMathContext(TRIG_SCALE, roundingMode)
  private val tokens = input.tokenize()
  private var cursorPosition = 0

  /**
   * Expression := [ "-" ] Term { ("+" | "-") Term }
   *
   * Term := Factor { ( "*" | "/" ) Factor }
   *
   * Factor := RealNumber | "(" Expression ")"
   *
   * RealNumber := Digit{Digit} | [ Digit ] "." {Digit}
   *
   * Digit := "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
   */
  fun calculate(): KBigDecimal {
    return parseExpression()
  }

  // Null when at the end of expression
  private fun peek() = tokens.getOrNull(cursorPosition)

  private fun moveIfMatched(token: Token2): Boolean {
    if (peek() == token) {
      // Move cursor
      cursorPosition++
      return true
    }
    return false
  }

  // Expression := [ "-" ] Term { ("+" | "-") Term }
  private fun parseExpression(): KBigDecimal {
    if (tokens.isEmpty()) return KBigDecimal.ZERO.setScale(scale)

    var expression = parseTerm()

    while (peek() in listOf(Token2.Plus, Token2.Minus)) {
      when {
        moveIfMatched(Token2.Plus) -> expression += parseTerm()
        moveIfMatched(Token2.Minus) -> expression -= parseTerm()
      }
    }
    return expression
  }

  // Term       := Factor { ( "*" | "/" ) Factor }
  private fun parseTerm(): KBigDecimal {
    var expression = parseFactor()

    while (peek() in listOf(Token2.Multiply, Token2.Divide)) {
      when {
        moveIfMatched(Token2.Multiply) -> {
          expression = expression.multiply(parseFactor())
        }

        moveIfMatched(Token2.Divide) -> {
          val divisor = parseFactor()
          if (divisor.isEqualTo(KBigDecimal.ZERO)) throw ExpressionException.DivideByZero()

          expression = expression.divide(divisor, scale, roundingMode)
        }
      }
    }
    return expression
  }

  // Factor     := RealNumber | "(" Expression ")"
  private fun parseFactor(negative: Boolean = false): KBigDecimal {
    // This will throw Exception if some function lacks argument, for example: "cos()" or "600^"
    var expr: KBigDecimal? = null

    fun parseFuncParentheses(): KBigDecimal {
      return if (moveIfMatched(Token2.LeftBracket)) {
        // Parse in parentheses
        val res = parseExpression()

        // Check if parentheses is closed
        if (!moveIfMatched(Token2.RightBracket)) throw ExpressionException.BadExpression()
        res
      } else {
        parseFactor()
      }
    }

    // Unary plus
    if (moveIfMatched(Token2.Plus)) {
      return parseFactor()
    }

    // Unary minus
    if (moveIfMatched(Token2.UnaryMinus)) {
      return -parseFactor(true)
    }

    // Parentheses
    if (moveIfMatched(Token2.LeftBracket)) {
      // Parse in parentheses
      expr = parseExpression()

      // Check if parentheses is closed
      if (!moveIfMatched(Token2.RightBracket)) throw ExpressionException.BadExpression()
    }

    // Numbers
    val possibleNumber = peek()
    if (possibleNumber is Token2.Number) {
      expr = KBigDecimal(possibleNumber.symbol).setScale(scale, roundingMode)
      cursorPosition++
    }

    // PI
    if (moveIfMatched(Token2.Pi)) {
      expr = KBigDecimalMath.pi(mathContext)
    }

    // e
    if (moveIfMatched(Token2.E)) {
      expr = KBigDecimalMath.e(mathContext)
    }

    // sqrt
    if (moveIfMatched(Token2.Sqrt)) {
      expr = KBigDecimalMath.sqrt(parseFuncParentheses(), mathContext)
    }

    // sin
    if (moveIfMatched(Token2.Sin)) {
      expr = parseFuncParentheses().sin(radianMode, mathContext).rescaleTrig()
    }

    // cos
    if (moveIfMatched(Token2.Cos)) {
      expr = parseFuncParentheses().cos(radianMode, mathContext).rescaleTrig()
    }

    // tan
    if (moveIfMatched(Token2.Tan)) {
      expr = parseFuncParentheses().tan(radianMode, mathContext).rescaleTrig()
    }

    // arsin
    if (moveIfMatched(Token2.ArSin)) {
      expr = parseFuncParentheses().arsin(radianMode, mathContext).rescaleTrig()
    }

    // arcos
    if (moveIfMatched(Token2.ArCos)) {
      expr = parseFuncParentheses().arcos(radianMode, mathContext).rescaleTrig()
    }

    // actan
    if (moveIfMatched(Token2.ArTan)) {
      expr = parseFuncParentheses().artan(radianMode, mathContext).rescaleTrig()
    }

    // ln
    if (moveIfMatched(Token2.Ln)) {
      expr = parseFuncParentheses().ln(mathContext)
    }

    // log
    if (moveIfMatched(Token2.Log)) {
      expr = parseFuncParentheses().log(mathContext)
    }

    // exp
    if (moveIfMatched(Token2.Exp)) {
      expr = parseFuncParentheses().exp(mathContext)
    }

    // Power
    if (moveIfMatched(Token2.Power)) {
      val factor = parseFactor()
      if (expr == null) throw ExpressionException.BadExpression()
      // mathematicians made up this controversy because reasons
      if (expr.isEqualTo(KBigDecimal.ZERO) && factor.isEqualTo(KBigDecimal.ZERO)) {
        throw ExpressionException.BadExpression()
      }

      expr = KBigDecimalMath.pow(expr, factor, mathContext)
    }

    // Modulo
    if (moveIfMatched(Token2.Modulo)) {
      if (expr == null) throw ExpressionException.BadExpression()
      expr = expr.remainder(parseFactor())
    }

    // Factorial
    if (moveIfMatched(Token2.Factorial)) {
      if (expr == null) throw ExpressionException.BadExpression()
      if (negative) throw ExpressionException.FactorialCalculation()
      expr = expr.factorial()
    }

    if (expr == null) throw ExpressionException.BadExpression()
    return expr
  }

  // rescale to avoid precision loss when evaluating special cases in trigonometry
  private fun KBigDecimal.rescaleTrig(): KBigDecimal =
    this.setScale(mathContext.precision, KRoundingMode.HALF_EVEN)
}

private const val TRIG_SCALE = 100

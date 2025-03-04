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

import ch.obermuhlner.math.big.BigDecimalMath
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.isEqualTo
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

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
  private val scale: Int,
  private val radianMode: Boolean = true,
  private val roundingMode: RoundingMode = RoundingMode.HALF_EVEN,
) {
  // double precision to have enough decimal points when formatting
  private val mathContext = MathContext(scale * 2, roundingMode)
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
  fun calculate(): BigDecimal {
    try {
      return parseExpression()
    } catch (e: UninitializedPropertyAccessException) {
      throw ExpressionException.BadExpression()
    }
  }

  // Null when at the end of expression
  private fun peek() = tokens.getOrNull(cursorPosition) ?: ""

  private fun moveIfMatched(token: String): Boolean {
    if (peek() == token) {
      // Move cursor
      cursorPosition++
      return true
    }
    return false
  }

  // Expression := [ "-" ] Term { ("+" | "-") Term }
  private fun parseExpression(): BigDecimal {
    if (tokens.isEmpty()) return BigDecimal.ZERO.setScale(scale)

    var expression = parseTerm()

    while (peek() in listOf(Token.Operator.PLUS, Token.Operator.MINUS)) {
      when {
        moveIfMatched(Token.Operator.PLUS) -> expression += parseTerm()
        moveIfMatched(Token.Operator.MINUS) -> expression -= parseTerm()
      }
    }
    return expression
  }

  // Term       := Factor { ( "*" | "/" ) Factor }
  private fun parseTerm(): BigDecimal {
    var expression = parseFactor()

    while (peek() in listOf(Token.Operator.MULTIPLY, Token.Operator.DIVIDE)) {
      when {
        moveIfMatched(Token.Operator.MULTIPLY) -> {
          expression = expression.multiply(parseFactor())
        }

        moveIfMatched(Token.Operator.DIVIDE) -> {
          val divisor = parseFactor()
          if (divisor.isEqualTo(BigDecimal.ZERO)) throw ExpressionException.DivideByZero()

          expression = expression.divide(divisor, scale, roundingMode)
        }
      }
    }
    return expression
  }

  // Factor     := RealNumber | "(" Expression ")"
  private fun parseFactor(negative: Boolean = false): BigDecimal {
    // This will throw Exception if some function lacks argument, for example: "cos()" or "600^"
    lateinit var expr: BigDecimal

    fun parseFuncParentheses(): BigDecimal {
      return if (moveIfMatched(Token.Operator.LEFT_BRACKET)) {
        // Parse in parentheses
        val res = parseExpression()

        // Check if parentheses is closed
        if (!moveIfMatched(Token.Operator.RIGHT_BRACKET)) throw ExpressionException.BadExpression()
        res
      } else {
        parseFactor()
      }
    }

    // Unary plus
    if (moveIfMatched(Token.Operator.PLUS)) {
      return parseFactor()
    }

    // Unary minus
    if (moveIfMatched(Token.Operator.MINUS)) {
      return -parseFactor(true)
    }

    // Parentheses
    if (moveIfMatched(Token.Operator.LEFT_BRACKET)) {
      // Parse in parentheses
      expr = parseExpression()

      // Check if parentheses is closed
      if (!moveIfMatched(Token.Operator.RIGHT_BRACKET)) throw ExpressionException.BadExpression()
    }

    // Numbers
    val possibleNumber = peek()
    // We know that if next token starts with a digit or dot, it can be converted into BigDecimal
    // Ugly
    if (possibleNumber.isNotEmpty()) {
      if (Token.Digit.allWithDot.contains(possibleNumber.first().toString())) {
        expr = BigDecimal(possibleNumber).setScale(scale, roundingMode)
        cursorPosition++
      }
    }

    // PI
    if (moveIfMatched(Token.Const.PI)) {
      expr = BigDecimalMath.pi(mathContext)
    }

    // e
    if (moveIfMatched(Token.Const.E)) {
      expr = BigDecimalMath.e(mathContext)
    }

    // sqrt
    if (moveIfMatched(Token.Operator.SQRT)) {
      expr = BigDecimalMath.sqrt(parseFuncParentheses(), mathContext)
    }

    // sin
    if (moveIfMatched(Token.Func.SIN)) {
      expr = parseFuncParentheses().sin(radianMode, mathContext).rescaleTrig()
    }

    // cos
    if (moveIfMatched(Token.Func.COS)) {
      expr = parseFuncParentheses().cos(radianMode, mathContext).rescaleTrig()
    }

    // tan
    if (moveIfMatched(Token.Func.TAN)) {
      expr = parseFuncParentheses().tan(radianMode, mathContext).rescaleTrig()
    }

    // arsin
    if (moveIfMatched(Token.Func.ARSIN)) {
      expr = parseFuncParentheses().arsin(radianMode, mathContext).rescaleTrig()
    }

    // arcos
    if (moveIfMatched(Token.Func.ARCOS)) {
      expr = parseFuncParentheses().arcos(radianMode, mathContext).rescaleTrig()
    }

    // actan
    if (moveIfMatched(Token.Func.ACTAN)) {
      expr = parseFuncParentheses().artan(radianMode, mathContext).rescaleTrig()
    }

    // ln
    if (moveIfMatched(Token.Func.LN)) {
      expr = parseFuncParentheses().ln(mathContext)
    }

    // log
    if (moveIfMatched(Token.Func.LOG)) {
      expr = parseFuncParentheses().log(mathContext)
    }

    // exp
    if (moveIfMatched(Token.Func.EXP)) {
      expr = parseFuncParentheses().exp(mathContext)
    }

    // Power
    if (moveIfMatched(Token.Operator.POWER)) {
      val factor = parseFactor()
      // mathematicians made up this controversy because reasons
      if (expr.isEqualTo(BigDecimal.ZERO) && factor.isEqualTo(BigDecimal.ZERO)) {
        throw ExpressionException.BadExpression()
      }

      expr = BigDecimalMath.pow(expr, factor, mathContext)
    }

    // Modulo
    if (moveIfMatched(Token.Operator.MODULO)) {
      expr = expr.remainder(parseFactor())
    }

    // Factorial
    if (moveIfMatched(Token.Operator.FACTORIAL)) {
      if (negative) throw ExpressionException.FactorialCalculation()
      expr = expr.factorial()
    }

    return expr
  }

  // rescale to avoid precision loss when evaluating special cases in trigonometry
  private fun BigDecimal.rescaleTrig(): BigDecimal = this.setScale(scale, RoundingMode.HALF_EVEN)
}

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

import com.sadellie.unitto.core.base.MAX_PRECISION
import com.sadellie.unitto.core.base.Token
import java.math.BigDecimal
import java.math.RoundingMode

sealed class ExpressionException(override val message: String): Exception(message) {
    class DivideByZero : ExpressionException("Can't divide by zero")
    class FactorialCalculation : ExpressionException("Can calculate factorial of non-negative real numbers only")
    class BadExpression : ExpressionException("Invalid expression. Probably some operator lacks argument")
    class TooBig : ExpressionException("Value is too big")
}

class Expression(
    input: String,
    private val radianMode: Boolean = true,
    private val roundingMode: RoundingMode = RoundingMode.HALF_EVEN
) {
    private val tokens = Tokenizer(input).tokenize()
    private var cursorPosition = 0

    /**
     * Expression := [ "-" ] Term { ("+" | "-") Term }
     *
     * Term       := Factor { ( "*" | "/" ) Factor }
     *
     * Factor     := RealNumber | "(" Expression ")"
     *
     * RealNumber := Digit{Digit} | [ Digit ] "." {Digit}
     *
     * Digit      := "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
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
        var expression = parseTerm()

        while (peek() in listOf(Token.Operator.plus, Token.Operator.minus)) {
            when {
                moveIfMatched(Token.Operator.plus) -> expression += parseTerm()
                moveIfMatched(Token.Operator.minus) -> expression -= parseTerm()
            }
        }
        return expression
    }

    // Term       := Factor { ( "*" | "/" ) Factor }
    private fun parseTerm(): BigDecimal {
        var expression = parseFactor()

        while (peek() in listOf(Token.Operator.multiply, Token.Operator.divide)) {
            when {
                moveIfMatched(Token.Operator.multiply) -> expression =
                    expression.multiply(parseFactor())

                moveIfMatched(Token.Operator.divide) -> {
                    val divisor = parseFactor()
                    if (divisor.compareTo(BigDecimal.ZERO) == 0) throw ExpressionException.DivideByZero()

                    expression = expression.divide(divisor, roundingMode)
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
            return if (moveIfMatched(Token.Operator.leftBracket)) {
                // Parse in parentheses
                val res = parseExpression()

                // Check if parentheses is closed
                if (!moveIfMatched(Token.Operator.rightBracket)) throw Exception("Closing bracket is missing")
                res
            } else {
                parseFactor()
            }
        }

        // Unary plus
        if (moveIfMatched(Token.Operator.plus)) return parseFactor()

        // Unary minus
        if (moveIfMatched(Token.Operator.minus)) {
            return -parseFactor(true)
        }

        // Parentheses
        if (moveIfMatched(Token.Operator.leftBracket)) {
            // Parse in parentheses
            expr = parseExpression()

            // Check if parentheses is closed
            if (!moveIfMatched(Token.Operator.rightBracket)) throw Exception("Closing bracket is missing")
        }

        // Numbers
        val possibleNumber = peek()
        // We know that if next token starts with a digit or dot, it can be converted into BigDecimal
        // Ugly
        if (possibleNumber.isNotEmpty()) {
            if (Token.Digit.allWithDot.contains(possibleNumber.first().toString())) {
                expr = BigDecimal(possibleNumber).setScale(MAX_PRECISION)
                cursorPosition++
            }
        }

        // PI
        if (moveIfMatched(Token.Const.pi)) {
            expr = BigDecimal.valueOf(Math.PI)
        }

        // e
        if (moveIfMatched(Token.Const.e)) {
            expr = BigDecimal.valueOf(Math.E)
        }

        // sqrt
        if (moveIfMatched(Token.Operator.sqrt)) {
            expr = parseFuncParentheses().pow(BigDecimal(0.5))
        }

        // sin
        if (moveIfMatched(Token.Func.sin)) {
            expr = parseFuncParentheses().sin(radianMode)
        }

        // cos
        if (moveIfMatched(Token.Func.cos)) {
            expr = parseFuncParentheses().cos(radianMode)
        }

        // tan
        if (moveIfMatched(Token.Func.tan)) {
            expr = parseFuncParentheses().tan(radianMode)
        }

        // arsin
        if (moveIfMatched(Token.Func.arsin)) {
            expr = parseFuncParentheses().arsin(radianMode)
        }

        // arcos
        if (moveIfMatched(Token.Func.arcos)) {
            expr = parseFuncParentheses().arcos(radianMode)
        }

        // actan
        if (moveIfMatched(Token.Func.actan)) {
            expr = parseFuncParentheses().artan(radianMode)
        }

        // ln
        if (moveIfMatched(Token.Func.ln)) {
            expr = parseFuncParentheses().ln()
        }

        // log
        if (moveIfMatched(Token.Func.log)) {
            expr = parseFuncParentheses().log()
        }

        // exp
        if (moveIfMatched(Token.Func.exp)) {
            expr = parseFuncParentheses().exp()
        }

        // Power
        if (moveIfMatched(Token.Operator.power)) {
            expr = expr.pow(parseFactor())
        }

        // Modulo
        if (moveIfMatched(Token.Operator.modulo)) {
            expr = expr.remainder(parseFactor())
        }

        // Factorial
        if (moveIfMatched(Token.Operator.factorial)) {
            if (negative) throw ExpressionException.FactorialCalculation()
            expr = expr.factorial()
        }

        return expr
    }
}

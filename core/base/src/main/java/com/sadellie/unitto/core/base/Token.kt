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

package com.sadellie.unitto.core.base

@Suppress("ObjectPropertyName")
object Token {
    object Digit {
        const val _1 = "1"
        const val _2 = "2"
        const val _3 = "3"
        const val _4 = "4"
        const val _5 = "5"
        const val _6 = "6"
        const val _7 = "7"
        const val _8 = "8"
        const val _9 = "9"
        const val _0 = "0"
        const val dot = "."

        val all by lazy {
            listOf(_1, _2, _3, _4, _5, _6, _7, _8, _9, _0)
        }
        val allWithDot by lazy { all + dot }
    }

    object Letter {
        const val _A = "A"
        const val _B = "B"
        const val _C = "C"
        const val _D = "D"
        const val _E = "E"
        const val _F = "F"

        val all by lazy {
            listOf(_A, _B, _C, _D, _E, _F)
        }
    }

    object Operator {
        const val plus = "+"
        const val minus = "−" // MINUS SIGN, not a regular minus (HYPHEN-MINUS)
        const val multiply = "×"
        const val divide = "÷"
        const val leftBracket = "("
        const val rightBracket = ")"
        const val power = "^"
        const val factorial = "!"
        const val modulo = "#"
        const val percent = "%"
        const val sqrt = "√"

        val all by lazy {
            listOf(
                plus, minus, multiply, divide,
                leftBracket, rightBracket,
                power, factorial, modulo, percent, sqrt,
            )
        }
    }

    object Func {
        const val sin = "sin"
        const val sinBracket = "$sin("
        const val cos = "cos"
        const val cosBracket = "$cos("
        const val tan = "tan"
        const val tanBracket = "$tan("
        const val arsin = "sin⁻¹"
        const val arsinBracket = "$arsin("
        const val arcos = "cos⁻¹"
        const val arcosBracket = "$arcos("
        const val actan = "tan⁻¹"
        const val actanBracket = "$actan("
        const val ln = "ln"
        const val lnBracket = "$ln("
        const val log = "log"
        const val logBracket = "$log("
        const val exp = "exp"
        const val expBracket = "$exp("

        val all by lazy {
            listOf(
                arsin, arcos, actan, sin, cos, tan, log, exp, ln
            ).sortedByDescending { it.length }
        }

        val allWithOpeningBracket by lazy {
            listOf(
                arsinBracket, arcosBracket, actanBracket, sinBracket, cosBracket, tanBracket,
                logBracket, expBracket, lnBracket
            )
        }
    }

    object Const {
        const val pi = "π"
        const val e = "e"

        val all by lazy {
            listOf(pi, e)
        }
    }

    // Used only in formatter, don't use internally
    object DisplayOnly {
        const val comma = ","
        const val engineeringE = "E"
        const val minus = "−"
        const val fraction = "⁄"
    }

    val expressionTokens by lazy {
        Digit.allWithDot + Operator.all + Func.all + Const.all + DisplayOnly.engineeringE
    }

    val numberBaseTokens by lazy {
        Digit.all + Letter.all
    }

    val sexyToUgly by lazy {
        mapOf(
            Operator.minus to listOf("-", "–", "—", "—"),
            Operator.divide to listOf("/"),
            Operator.multiply to listOf("*", "•"),
            Func.arsin to listOf("arsin"),
            Func.arcos to listOf("arcos"),
            Func.actan to listOf("actan")
        )
    }
}

/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

object Token {
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

    const val baseA = "A"
    const val baseB = "B"
    const val baseC = "C"
    const val baseD = "D"
    const val baseE = "E"
    const val baseF = "F"

    const val dot = "."
    const val comma = ","
    const val E = "E"

    const val plus = "+"
    const val minus = "-"
    const val minusDisplay = "–"

    const val divide = "/"
    const val divideDisplay = "÷"

    const val multiply = "*"
    const val multiplyDisplay = "×"

    const val leftBracket = "("
    const val rightBracket = ")"
    const val exponent = "^"
    const val sqrt = "√"
    const val pi = "π"
    const val factorial = "!"
    const val sin = "sin("
    const val arSin = "arsin("
    const val cos = "cos("
    const val arCos = "arcos("
    const val tan = "tan("
    const val acTan = "actan("
    const val e = "e"
    const val exp = "exp("
    const val modulo = "#"
    const val ln = "ln("
    const val log = "log("
    const val percent = "%"

    val operators by lazy {
        listOf(
            plus,
            minus,
            minusDisplay,
            multiply,
            multiplyDisplay,
            divide,
            divideDisplay,
            sqrt,
            exponent,
        )
    }

    val digits by lazy {
        listOf(
            _1,
            _2,
            _3,
            _4,
            _5,
            _6,
            _7,
            _8,
            _9,
            _0,
        )
    }

    val internalToDisplay: Map<String, String> = hashMapOf(
        minus to minusDisplay,
        multiply to multiplyDisplay,
        divide to divideDisplay
    )

    val knownSymbols: List<String> by lazy {
        listOf(
            arSin, arCos, acTan, exp,
            sin, cos, tan, ln, log,
            leftBracket, rightBracket,
            exponent, sqrt, factorial,
            modulo, e, percent, pi,
            multiply, multiplyDisplay,
            plus, minus, minusDisplay, divide, divideDisplay,
            baseA, baseB, baseC, baseD, baseE, baseF,
            _1, _2, _3, _4, _5, _6, _7, _8, _9, _0,
            dot
        ).sortedByDescending { it.length }
    }
}

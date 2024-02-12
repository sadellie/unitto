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

package com.sadellie.unitto.core.base

import org.junit.Assert
import org.junit.Test

class TokenTest {

    @Test
    fun testFormatterSymbols() {
        Assert.assertEquals(" ", Token.SPACE)
        Assert.assertEquals(".", Token.PERIOD)
        Assert.assertEquals(",", Token.COMMA)
    }

    @Test
    fun testDigit() {
        Assert.assertEquals("1234567890", Token.Digit.all.joinToString(""))
        Assert.assertEquals("1234567890.", Token.Digit.allWithDot.joinToString(""))
    }

    @Test
    fun testLetter() {
        Assert.assertEquals("ABCDEF", Token.Letter.all.joinToString(""))
    }

    @Test
    fun testOperator() {
        listOf(
            "+",
            "−",
            "×",
            "÷",
            "(",
            ")",
            "^",
            "!",
            "#",
            "%",
            "√",
        ).forEach {
            assert(it in Token.Operator.all)
        }
    }

    @Test
    fun testFunc() {
        listOf(
            "sin",
            "cos",
            "tan",
            "sin⁻¹",
            "cos⁻¹",
            "tan⁻¹",
            "ln",
            "log",
            "exp",
        ).forEach {
            assert(it in Token.Func.all)
        }

        listOf(
            "sin(",
            "cos(",
            "tan(",
            "sin⁻¹(",
            "cos⁻¹(",
            "tan⁻¹(",
            "ln(",
            "log(",
            "exp(",
        ).forEach {
            assert(it in Token.Func.allWithOpeningBracket)
        }
    }

    @Test
    fun testConst() {
        listOf(
            "π",
            "e",
        ).forEach {
            assert(it in Token.Const.all)
        }
    }

    @Test
    fun testExpressionTokens() {
        val operator = listOf(
            "+",
            "−",
            "×",
            "÷",
            "(",
            ")",
            "^",
            "!",
            "#",
            "%",
            "√",
        ).joinToString("")

        val func = listOf(
            "sin⁻¹",
            "cos⁻¹",
            "tan⁻¹",
            "sin",
            "cos",
            "tan",
            "log",
            "exp",
            "ln",
        ).joinToString("")

        val consts = listOf(
            "π",
            "e",
        ).joinToString("")

        Assert.assertEquals("1234567890.$operator$func${consts}E", Token.expressionTokens.joinToString(""))
    }

    @Test
    fun testNumberBaseTokens() {
        Assert.assertEquals("1234567890ABCDEF", Token.numberBaseTokens.joinToString(""))
    }

    @Test
    fun testDisplayOnlyObject() {
        Assert.assertNotNull(Token.DisplayOnly)
    }

    @Test
    fun testSexyToUgly() {
        listOf(
            "−", "÷", "×", "sin⁻¹", "cos⁻¹", "tan⁻¹"
        ).forEach {
            assert(it in Token.sexyToUgly.keys)
        }
    }
}
/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class RPNEngineKtTest {

    @Test
    fun testBadOperands() {
        // no funny business if input and/or stack is empty
        val actual = RPNCalculation.Divide.perform(
            input = "",
            stack = emptyList()
        )

        assertEquals(RPNResult.BadInput, actual)
    }

    @Test
    fun testDivide() {
        val actual = RPNCalculation.Divide.perform(
            input = "2",
            stack = listOf(BigDecimal("5"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(BigDecimal("2.5").setScale(MAX_PRECISION), actual.input)
        assertEquals(emptyList<BigDecimal>(), actual.stack)
    }

    @Test
    fun testDivideByZero() {
        val actual = RPNCalculation.Divide.perform(
            input = "0",
            stack = listOf(BigDecimal("5"))
        )

        assertEquals(RPNResult.DivideByZero, actual)
    }

    @Test
    fun testMinus() {
        val actual = RPNCalculation.Minus.perform(
            input = "2",
            stack = listOf(BigDecimal("5"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(BigDecimal("3"), actual.input)
        assertEquals(emptyList<BigDecimal>(), actual.stack)
    }

    @Test
    fun testMultiply() {
        val actual = RPNCalculation.Multiply.perform(
            input = "2",
            stack = listOf(BigDecimal("5"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(BigDecimal("10"), actual.input)
        assertEquals(emptyList<BigDecimal>(), actual.stack)
    }

    @Test
    fun testNegate() {
        val actual = RPNCalculation.Negate.perform(
            input = "2",
            stack = listOf(BigDecimal("5"))
        )

        if (actual !is RPNResult.NewInput) throw Exception("Wrong return")

        assertEquals(BigDecimal("-2"), actual.input)
    }

    @Test
    fun testPercent() {
        val actual = RPNCalculation.Percent.perform(
            input = "150",
            stack = listOf(BigDecimal("69"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(BigDecimal("103.5").setScale(MAX_PRECISION), actual.input)
        assertEquals(emptyList<BigDecimal>(), actual.stack)
    }

    @Test
    fun testPlus() {
        val actual = RPNCalculation.Plus.perform(
            input = "150",
            stack = listOf(BigDecimal("69"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(BigDecimal("219"), actual.input)
        assertEquals(emptyList<BigDecimal>(), actual.stack)
    }

    @Test
    fun testPower() {
        val actual = RPNCalculation.Power.perform(
            input = "3",
            stack = listOf(BigDecimal("2"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(BigDecimal("8"), actual.input)
        assertEquals(emptyList<BigDecimal>(), actual.stack)
    }

    @Test
    fun testRotateUp() {
        val actual = RPNCalculation.RotateUp.perform(
            input = "",
            stack = listOf(BigDecimal("1"), BigDecimal("2"), BigDecimal("3"))
        )

        if (actual !is RPNResult.NewStack) throw Exception("Wrong return")

        assertEquals(listOf(BigDecimal("2"), BigDecimal("3"), BigDecimal("1")), actual.stack)
    }

    @Test
    fun testRotateDown() {
        val actual = RPNCalculation.RotateDown.perform(
            input = "",
            stack = listOf(BigDecimal("1"), BigDecimal("2"), BigDecimal("3"))
        )

        if (actual !is RPNResult.NewStack) throw Exception("Wrong return")

        assertEquals(listOf(BigDecimal("3"), BigDecimal("1"), BigDecimal("2")), actual.stack)
    }

    @Test
    fun testPop() {
        val actual = RPNCalculation.Pop.perform(
            input = "",
            stack = listOf(BigDecimal("1"), BigDecimal("2"), BigDecimal("3"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(BigDecimal("3"), actual.input)
        assertEquals(listOf(BigDecimal("1"), BigDecimal("2")), actual.stack)
    }

    @Test
    fun testClear() {
        val actual = RPNCalculation.Clear.perform(
            input = "3",
            stack = listOf(BigDecimal("2"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(null, actual.input)
        assertEquals(emptyList<BigDecimal>(), actual.stack)
    }

    @Test
    fun testEnter() {
        val actual = RPNCalculation.Enter.perform(
            input = "3",
            stack = listOf(BigDecimal("2"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(null, actual.input)
        assertEquals(listOf(BigDecimal("2"), BigDecimal("3")), actual.stack)
    }

    @Test
    fun testSwap() {
        val actual = RPNCalculation.Swap.perform(
            input = "3",
            stack = listOf(BigDecimal("2"))
        )

        if (actual !is RPNResult.Result) throw Exception("Wrong return")

        assertEquals(BigDecimal("2"), actual.input)
        assertEquals(listOf(BigDecimal("3")), actual.stack)
    }

    @Test
    fun testSwapEmptyInput() {
        val actual = RPNCalculation.Swap.perform(
            input = "",
            stack = listOf(BigDecimal("1"), BigDecimal("2"))
        )

        if (actual !is RPNResult.NewStack) throw Exception("Wrong return")

        assertEquals(listOf(BigDecimal("2"), BigDecimal("1")), actual.stack)
    }

    @Test
    fun testSwapEmptyInputNotEnoughInStack() {
        val actual = RPNCalculation.Swap.perform(
            input = "",
            stack = listOf(BigDecimal("1"))
        )

        assertEquals(RPNResult.BadInput, actual)
    }

    @Test
    fun testSwapEmptyStack() {
        val actual = RPNCalculation.Swap.perform(
            input = "123",
            stack = emptyList()
        )

        assertEquals(RPNResult.BadInput, actual)
    }

    @Test
    fun testSwapEmptyBoth() {
        val actual = RPNCalculation.Swap.perform(
            input = "",
            stack = emptyList()
        )

        assertEquals(RPNResult.BadInput, actual)
    }
}

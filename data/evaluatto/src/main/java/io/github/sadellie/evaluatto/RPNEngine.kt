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
import java.math.BigDecimal
import java.math.RoundingMode

sealed class RPNResult {

    /**
     * Both input and stack were changed.
     *
     * @property input New input. Empty when `null`.
     * @property stack New stack.
     */
    data class Result(
        val input: BigDecimal?,
        val stack: List<BigDecimal>,
    ) : RPNResult()

    /**
     * Only input has been changed.
     *
     * @property input New input.
     */
    data class NewInput(
        val input: BigDecimal,
    ) : RPNResult()

    /**
     * Only stack has been changed.
     *
     * @property stack New stack.
     */
    data class NewStack(
        val stack: List<BigDecimal>,
    ) : RPNResult()

    /**
     * Something is wrong. Input/stack is empty or there ane not enough stack objects.
     */
    data object BadInput : RPNResult()

    /**
     * Dividing by zero, duh
     */
    data object DivideByZero : RPNResult()
}

// vroom vroom mfs
// overdose on early returns
fun RPNCalculation.perform(
    input: String,
    stack: List<BigDecimal>,
): RPNResult {
    when (this) {
        RPNCalculation.Clear -> {
            return RPNResult.Result(null, emptyList())
        }

        RPNCalculation.Enter -> {
            val inputBD = input.toBigDecimalOrNull() ?: return RPNResult.BadInput
            return RPNResult.Result(null, stack + inputBD)
        }

        RPNCalculation.Negate -> {
            val inputBD = input.toBigDecimalOrNull() ?: return RPNResult.BadInput
            val result = inputBD.negate()
            return RPNResult.NewInput(result)
        }

        RPNCalculation.RotateUp -> {
            if (stack.size < 2) return RPNResult.BadInput
            return RPNResult.NewStack(stack.rotateUp())
        }

        RPNCalculation.RotateDown -> {
            if (stack.size < 2) return RPNResult.BadInput
            return RPNResult.NewStack(stack.rotateDown())
        }

        RPNCalculation.Swap -> {
            if (stack.isEmpty()) return RPNResult.BadInput
            if (input.isEmpty()) {
                // Swap last 2 in stack
                if (stack.size < 2) return RPNResult.BadInput
                return RPNResult.NewStack(stack.swapLastTwo())
            }

            // Swap last and input
            val (lastFromStack, inputBD) = operands(stack, input) ?: return RPNResult.BadInput
            return RPNResult.Result(lastFromStack, stack.dropLast(1) + inputBD)
        }

        RPNCalculation.Pop -> {
            val lastStacked = stack.lastOrNull() ?: return RPNResult.BadInput
            return RPNResult.Result(lastStacked, stack.dropLast(1))
        }

        RPNCalculation.Plus -> {
            val (lastFromStack, inputBD) = operands(stack, input) ?: return RPNResult.BadInput
            val result = lastFromStack.plus(inputBD)
            return RPNResult.Result(result, stack.dropLast(1))
        }

        RPNCalculation.Minus -> {
            val (lastFromStack, inputBD) = operands(stack, input) ?: return RPNResult.BadInput
            val result = lastFromStack.minus(inputBD)
            return RPNResult.Result(result, stack.dropLast(1))
        }

        RPNCalculation.Multiply -> {
            val (lastFromStack, inputBD) = operands(stack, input) ?: return RPNResult.BadInput
            val result = lastFromStack.multiply(inputBD)
            return RPNResult.Result(result, stack.dropLast(1))
        }

        RPNCalculation.Divide -> {
            val (lastFromStack, inputBD) = operands(stack, input) ?: return RPNResult.BadInput
            if (inputBD.compareTo(BigDecimal.ZERO) == 0) return RPNResult.DivideByZero

            val result = lastFromStack.divide(inputBD, MAX_PRECISION, RoundingMode.HALF_EVEN)
            return RPNResult.Result(result, stack.dropLast(1))
        }

        RPNCalculation.Percent -> {
            val (lastFromStack, inputBD) = operands(stack, input) ?: return RPNResult.BadInput
            // 100 * 24 / 100 =
            val result = lastFromStack
                .multiply(inputBD)
                .divide(bigDecimalHundred, MAX_PRECISION, RoundingMode.HALF_EVEN)
            return RPNResult.Result(result, stack.dropLast(1))
        }

        RPNCalculation.Power -> {
            val (lastFromStack, inputBD) = operands(stack, input) ?: return RPNResult.BadInput
            val result = lastFromStack.pow(inputBD)
            return RPNResult.Result(result, stack.dropLast(1))
        }
    }
}

private val bigDecimalHundred by lazy { BigDecimal("100") }

private fun operands(
    stack: List<BigDecimal>,
    input: String,
): Pair<BigDecimal, BigDecimal>? {
    val first = stack.lastOrNull() ?: return null
    val second = input.toBigDecimalOrNull() ?: return null

    return first to second
}

private fun <T> List<T>.swapLastTwo(): List<T> {
    if (size < 2) return this
    return this
        .dropLast(2)
        .plus(get(lastIndex))
        .plus(get(lastIndex - 1))
}

private fun <T> List<T>.rotateUp(): List<T> {
    if (size < 2) return this
    return this
        .drop(1)
        .plus(first())
}

private fun <T> List<T>.rotateDown(): List<T> {
    if (size < 2) return this
    return listOf(last())
        .plus(this.dropLast(1))
}

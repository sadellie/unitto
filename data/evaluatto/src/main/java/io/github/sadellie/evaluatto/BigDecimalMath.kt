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

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.pow

internal fun BigDecimal.sin(radianMode: Boolean): BigDecimal {
    val angle: Double = if (radianMode) this.toDouble() else Math.toRadians(this.toDouble())
    return kotlin.math.sin(angle).toBigDecimal()
}

internal fun BigDecimal.arsin(radianMode: Boolean): BigDecimal {
    val angle: Double = asin(this.toDouble())
    return (if (radianMode) angle else Math.toDegrees(angle)).toBigDecimal()
}

internal fun BigDecimal.cos(radianMode: Boolean): BigDecimal {
    val angle: Double = if (radianMode) this.toDouble() else Math.toRadians(this.toDouble())
    return kotlin.math.cos(angle).toBigDecimal()
}

internal fun BigDecimal.arcos(radianMode: Boolean): BigDecimal {
    val angle: Double = acos(this.toDouble())
    return (if (radianMode) angle else Math.toDegrees(angle)).toBigDecimal()
}

internal fun BigDecimal.tan(radianMode: Boolean): BigDecimal {
    val angle: Double = if (radianMode) this.toDouble() else Math.toRadians(this.toDouble())
    return kotlin.math.tan(angle).toBigDecimal()
}

internal fun BigDecimal.artan(radianMode: Boolean): BigDecimal {
    val angle: Double = atan(this.toDouble())
    return (if (radianMode) angle else Math.toDegrees(angle)).toBigDecimal()
}

internal fun BigDecimal.ln(): BigDecimal {
    return kotlin.math.ln(this.toDouble()).toBigDecimal()
}

internal fun BigDecimal.log(): BigDecimal {
    return kotlin.math.log(this.toDouble(), 10.0).toBigDecimal()
}

internal fun BigDecimal.exp(): BigDecimal {
    return kotlin.math.exp(this.toDouble()).toBigDecimal()
}

internal fun BigDecimal.pow(n: BigDecimal): BigDecimal {
    val mathContext: MathContext = MathContext.DECIMAL64

    var right = n
    val signOfRight = right.signum()
    right = right.multiply(signOfRight.toBigDecimal())
    val remainderOfRight = right.remainder(BigDecimal.ONE)
    val n2IntPart = right.subtract(remainderOfRight)
    val intPow = pow(n2IntPart.intValueExact(), mathContext)
    val doublePow = BigDecimal(
        toDouble().pow(remainderOfRight.toDouble())
    )

    var result = intPow.multiply(doublePow, mathContext)
    if (signOfRight == -1) result =
        BigDecimal.ONE.divide(result, mathContext.precision, RoundingMode.HALF_UP)

    return result
}

internal fun BigDecimal.factorial(): BigDecimal {
    if (this.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0) throw ExpressionException.FactorialCalculation()
    if (this < BigDecimal.ZERO) throw ExpressionException.FactorialCalculation()
    if (this > maxFactorial) throw ExpressionException.TooBig()

    var expr = this
    for (i in 1 until this.toInt()) {
        expr *= BigDecimal(i)
    }
    return expr
}

private val maxFactorial by lazy { BigDecimal("9999") }

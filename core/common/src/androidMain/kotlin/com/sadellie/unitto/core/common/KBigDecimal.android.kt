/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

actual class KBigDecimal(internal val wrapped: BigDecimal) : Comparable<KBigDecimal> {
  actual override fun equals(other: Any?): Boolean {
    if (other !is KBigDecimal) return false
    return this.wrapped == other.wrapped
  }

  actual override fun toString(): String = this.wrapped.toString()

  actual override fun hashCode(): Int = this.wrapped.hashCode()

  actual constructor(string: String) : this(BigDecimal(string))

  actual constructor(double: Double) : this(BigDecimal(double))

  actual companion object {
    actual val ZERO: KBigDecimal = KBigDecimal(BigDecimal.ZERO)
    actual val ONE: KBigDecimal = KBigDecimal(BigDecimal.ONE)
    actual val TEN: KBigDecimal = KBigDecimal(BigDecimal.TEN)

    actual fun valueOf(double: Double): KBigDecimal = KBigDecimal(BigDecimal.valueOf(double))

    actual fun valueOf(long: Long): KBigDecimal = KBigDecimal(BigDecimal.valueOf(long))
  }

  actual override operator fun compareTo(other: KBigDecimal): Int =
    this.wrapped.compareTo(other.wrapped)

  actual fun stripTrailingZeros(): KBigDecimal = KBigDecimal(this.wrapped.stripTrailingZeros())

  actual fun setScale(scale: Int, roundingMode: KRoundingMode): KBigDecimal =
    KBigDecimal(this.wrapped.setScale(scale, roundingMode.wrapped))

  actual fun setScale(scale: Int): KBigDecimal = KBigDecimal(this.wrapped.setScale(scale))

  actual fun scale(): Int = this.wrapped.scale()

  actual fun abs(): KBigDecimal = KBigDecimal(this.wrapped.abs())

  actual fun remainder(divisor: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.remainder(divisor.wrapped))

  actual fun toPlainString(): String = this.wrapped.toPlainString()

  actual fun toEngineeringString(): String = this.wrapped.toEngineeringString()

  actual fun intValueExact(): Int = this.wrapped.intValueExact()

  actual fun multiply(multiplier: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.multiply(multiplier.wrapped))

  actual fun div(divisor: KBigDecimal): KBigDecimal = KBigDecimal(this.wrapped.div(divisor.wrapped))

  actual operator fun plus(addend: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.plus(addend.wrapped))

  actual operator fun minus(subtrahend: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.minus(subtrahend.wrapped))

  actual fun divide(divisor: KBigDecimal, scale: Int, roundingMode: KRoundingMode): KBigDecimal =
    KBigDecimal(this.wrapped.divide(divisor.wrapped, scale, roundingMode.wrapped))

  actual operator fun unaryMinus(): KBigDecimal = KBigDecimal(this.wrapped.unaryMinus())

  actual fun divide(divisor: KBigDecimal, mathContext: KMathContext): KBigDecimal =
    KBigDecimal(this.wrapped.divide(divisor.wrapped, mathContext.wrapped))

  actual fun add(addend: KBigDecimal): KBigDecimal = KBigDecimal(this.wrapped.add(addend.wrapped))

  actual constructor(
    string: String,
    mathContext: KMathContext,
  ) : this(BigDecimal(string, mathContext.wrapped))

  actual fun toBigInteger(): KBigInteger = KBigInteger(this.wrapped.toBigInteger())

  actual fun toInt(): Int = this.wrapped.toInt()

  actual constructor(bigInteger: KBigInteger) : this(BigDecimal(bigInteger.wrapped))

  constructor(int: Int) : this(BigDecimal(int))

  actual operator fun times(bigDecimal: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.times(bigDecimal.wrapped))

  actual fun divideAndRemainder(divisor: KBigDecimal): Array<KBigDecimal> {
    val res = this.wrapped.divideAndRemainder(divisor.wrapped)
    return arrayOf(KBigDecimal(res[0]), KBigDecimal(res[1]))
  }

  actual fun pow(n: Int): KBigDecimal = KBigDecimal(this.wrapped.pow(n))

  actual fun scaleByPowerOfTen(n: Int): KBigDecimal = KBigDecimal(this.wrapped.scaleByPowerOfTen(n))
}

actual class KRoundingMode internal constructor(val wrapped: RoundingMode) {

  actual override fun equals(other: Any?): Boolean {
    if (other !is KRoundingMode) return false
    return this.wrapped == other.wrapped
  }

  actual override fun toString(): String = this.wrapped.toString()

  actual override fun hashCode(): Int = this.wrapped.hashCode()

  actual companion object {
    actual val HALF_EVEN: KRoundingMode = KRoundingMode(RoundingMode.HALF_EVEN)
    actual val DOWN: KRoundingMode = KRoundingMode(RoundingMode.DOWN)
  }
}

actual class KMathContext private constructor(val wrapped: MathContext) {
  actual override fun equals(other: Any?): Boolean {
    if (other !is KMathContext) return false
    return this.wrapped == other.wrapped
  }

  actual override fun toString(): String = this.wrapped.toString()

  actual override fun hashCode(): Int = this.wrapped.hashCode()

  actual constructor(
    precision: Int,
    roundingMode: KRoundingMode,
  ) : this(MathContext(precision, roundingMode.wrapped))

  actual val precision: Int = this.wrapped.precision
}

actual class KBigInteger internal constructor(internal val wrapped: BigInteger) :
  Comparable<KBigInteger> {
  actual override fun equals(other: Any?): Boolean {
    if (other !is KBigInteger) return false
    return this.wrapped == other.wrapped
  }

  actual override fun toString(): String = this.wrapped.toString()

  actual fun toString(radix: Int): String = this.wrapped.toString(radix)

  actual override fun hashCode(): Int = this.wrapped.hashCode()

  actual fun gcd(d: KBigInteger): KBigInteger = KBigInteger(this.wrapped.gcd(d.wrapped))

  actual fun divide(divisor: KBigInteger): KBigInteger =
    KBigInteger(this.wrapped.divide(divisor.wrapped))

  actual companion object {
    actual val ONE: KBigInteger = KBigInteger(BigInteger.ONE)
    actual val ZERO: KBigInteger = KBigInteger(BigInteger.ZERO)
    actual val TEN: KBigInteger = KBigInteger(BigInteger.TEN)
  }

  actual fun pow(n: Int): KBigInteger = KBigInteger(this.wrapped.pow(n))

  actual fun toKBigDecimal(): KBigDecimal = KBigDecimal(this.wrapped.toBigDecimal())

  actual operator fun div(other: KBigInteger): KBigInteger =
    KBigInteger(this.wrapped.div(other.wrapped))

  actual operator fun minus(other: KBigInteger): KBigInteger =
    KBigInteger(this.wrapped.minus(other.wrapped))

  actual fun multiply(other: KBigInteger): KBigInteger =
    KBigInteger(this.wrapped.multiply(other.wrapped))

  actual constructor(value: String) : this(BigInteger(value))

  actual constructor(value: String, radix: Int) : this(BigInteger(value, radix))

  actual override fun compareTo(other: KBigInteger): Int = this.wrapped.compareTo(other.wrapped)
}

actual class KBigDecimalMath {
  actual companion object {
    actual fun toRadians(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.toRadians(bigDecimal.wrapped, mathContext.wrapped))

    actual fun sin(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.sin(bigDecimal.wrapped, mathContext.wrapped))

    actual fun asin(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.asin(bigDecimal.wrapped, mathContext.wrapped))

    actual fun toDegrees(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.toDegrees(bigDecimal.wrapped, mathContext.wrapped))

    actual fun cos(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.cos(bigDecimal.wrapped, mathContext.wrapped))

    actual fun acos(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.acos(bigDecimal.wrapped, mathContext.wrapped))

    actual fun tan(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.tan(bigDecimal.wrapped, mathContext.wrapped))

    actual fun atan(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.atan(bigDecimal.wrapped, mathContext.wrapped))

    actual fun log(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.log(bigDecimal.wrapped, mathContext.wrapped))

    actual fun log10(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.log10(bigDecimal.wrapped, mathContext.wrapped))

    actual fun exp(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.exp(bigDecimal.wrapped, mathContext.wrapped))

    actual fun pi(mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.pi(mathContext.wrapped))

    actual fun e(mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.e(mathContext.wrapped))

    actual fun sqrt(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.sqrt(bigDecimal.wrapped, mathContext.wrapped))

    actual fun pow(expr: KBigDecimal, factor: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      KBigDecimal(BigDecimalMath.pow(expr.wrapped, factor.wrapped, mathContext.wrapped))
  }
}

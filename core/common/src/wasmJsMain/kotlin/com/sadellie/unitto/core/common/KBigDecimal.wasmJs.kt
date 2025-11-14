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
import org.gciatto.kt.math.BigDecimal
import org.gciatto.kt.math.BigInteger
import org.gciatto.kt.math.MathContext
import org.gciatto.kt.math.RoundingMode

actual class KBigDecimal(internal val wrapped: BigDecimal) : Comparable<KBigDecimal> {
  actual override fun equals(other: Any?): Boolean {
    if (other !is KBigDecimal) return false
    return this.wrapped == other.wrapped
  }

  actual override fun toString(): String = this.wrapped.toString()

  actual override fun hashCode(): Int = this.wrapped.hashCode()

  actual constructor(string: String) : this(BigDecimal.of(string))

  actual constructor(double: Double) : this(BigDecimal.of(double))

  actual companion object {
    actual val ZERO: KBigDecimal = KBigDecimal(BigDecimal.ZERO)
    actual val ONE: KBigDecimal = KBigDecimal(BigDecimal.ONE)
    actual val TEN: KBigDecimal = KBigDecimal(BigDecimal.TEN)

    actual fun valueOf(double: Double): KBigDecimal = KBigDecimal(BigDecimal.of(double))

    actual fun valueOf(long: Long): KBigDecimal = KBigDecimal(BigDecimal.of(long))

    internal const val ROUND_DOWN: Int = 1
  }

  actual override operator fun compareTo(other: KBigDecimal): Int =
    this.wrapped.compareTo(other.wrapped)

  actual fun stripTrailingZeros(): KBigDecimal = KBigDecimal(this.wrapped.stripTrailingZeros())

  actual fun setScale(scale: Int, roundingMode: KRoundingMode): KBigDecimal =
    KBigDecimal(this.wrapped.setScale(scale, roundingMode.wrapped))

  actual fun setScale(scale: Int): KBigDecimal = KBigDecimal(this.wrapped.setScale(scale))

  actual fun scale(): Int = this.wrapped.scale

  actual fun abs(): KBigDecimal = KBigDecimal(this.wrapped.absoluteValue)

  actual fun remainder(divisor: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.divideAndRemainder(divisor.wrapped).second)

  actual fun toPlainString(): String = this.wrapped.toPlainString()

  actual fun toEngineeringString(): String = this.wrapped.toEngineeringString()

  actual fun intValueExact(): Int = this.wrapped.toIntExact()

  actual fun multiply(multiplier: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.times(multiplier.wrapped))

  actual fun div(divisor: KBigDecimal): KBigDecimal = KBigDecimal(this.wrapped.div(divisor.wrapped))

  actual operator fun plus(addend: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.plus(addend.wrapped))

  actual operator fun minus(subtrahend: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.minus(subtrahend.wrapped))

  actual fun divide(divisor: KBigDecimal, scale: Int, roundingMode: KRoundingMode): KBigDecimal =
    KBigDecimal(this.wrapped.divide(divisor.wrapped, scale, roundingMode.wrapped))

  actual operator fun unaryMinus(): KBigDecimal = KBigDecimal(this.wrapped.unaryMinus())

  actual fun divide(divisor: KBigDecimal, mathContext: KMathContext): KBigDecimal =
    KBigDecimal(this.wrapped.div(divisor.wrapped, mathContext.wrapped) ?: error("Failed to divide"))

  actual fun add(addend: KBigDecimal): KBigDecimal = KBigDecimal(this.wrapped.plus(addend.wrapped))

  actual constructor(
    string: String,
    mathContext: KMathContext,
  ) : this(BigDecimal.of(string, mathContext.wrapped))

  actual fun toBigInteger(): KBigInteger = KBigInteger(this.wrapped.toBigInteger())

  actual fun toInt(): Int = this.wrapped.toInt()

  actual constructor(bigInteger: KBigInteger) : this(BigDecimal.of(bigInteger.wrapped))

  constructor(int: Int) : this(BigDecimal.of(int))

  actual operator fun times(bigDecimal: KBigDecimal): KBigDecimal =
    KBigDecimal(this.wrapped.times(bigDecimal.wrapped))

  actual fun divideAndRemainder(divisor: KBigDecimal): Array<KBigDecimal> {
    val res = this.wrapped.divideAndRemainder(divisor.wrapped)
    return arrayOf(KBigDecimal(res.first), KBigDecimal(res.second))
  }

  actual fun pow(n: Int): KBigDecimal = KBigDecimal(this.wrapped.pow(n))

  actual fun scaleByPowerOfTen(n: Int): KBigDecimal = KBigDecimal(this.wrapped.scaleByPowerOfTen(n))

  internal fun negate(): KBigDecimal =
    KBigDecimal(this.wrapped.times(KBigDecimal("-1").wrapped)) // TODO real negate

  internal val signum: Int = this.wrapped.signum

  internal fun toDouble(): Double = this.wrapped.toDouble()

  internal fun toFloat(): Float = this.wrapped.toFloat()

  internal fun toLong(): Long = this.wrapped.toLong()

  internal fun movePointLeft(n: Int): KBigDecimal = KBigDecimal(this.wrapped.movePointLeft(n))

  internal fun movePointRight(n: Int): KBigDecimal = KBigDecimal(this.wrapped.movePointRight(n))

  internal fun round(mc: KMathContext): KBigDecimal =
    KBigDecimal(this.wrapped.round(mc.wrapped)!!) // TODO remove nullability

  internal fun multiply(bigDecimal: KBigDecimal, mc: KMathContext): KBigDecimal =
    KBigDecimal(this.wrapped.times(bigDecimal.wrapped, mc.wrapped))

  internal fun precision(): Int = this.wrapped.precision

  internal constructor(
    `in`: CharArray,
    offset: Int,
    len: Int,
  ) : this(BigDecimal.of(`in`, offset, len))

  @Suppress("DEPRECATION")
  internal fun setScale(newScale: Int, roundingMode: Int): KBigDecimal =
    KBigDecimal(this.wrapped.setScale(newScale, roundingMode))

  internal fun add(bigDecimal: KBigDecimal, mc: KMathContext): KBigDecimal =
    KBigDecimal(this.wrapped.plus(bigDecimal.wrapped, mc.wrapped))

  internal fun longValueExact(): Long = this.wrapped.toLongExact()

  internal fun remainder(divisor: KBigDecimal, mc: KMathContext): KBigDecimal =
    KBigDecimal(this.wrapped.divideAndRemainder(divisor.wrapped, mc.wrapped).second)

  internal fun minus(bigDecimal: KBigDecimal, mc: KMathContext): KBigDecimal =
    KBigDecimal(this.wrapped.minus(bigDecimal.wrapped, mc.wrapped))
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
    internal val HALF_UP: KRoundingMode = KRoundingMode(RoundingMode.HALF_UP)
    actual val DOWN: KRoundingMode = KRoundingMode(RoundingMode.DOWN)
  }
}

actual class KMathContext private constructor(val wrapped: MathContext) {
  actual override fun equals(other: Any?): Boolean {
    if (other !is KMathContext) return false
    return this.wrapped == other.wrapped
  }

  internal companion object {
    val DECIMAL128 = KMathContext(MathContext.DECIMAL128)
    val UNLIMITED = KMathContext(MathContext.UNLIMITED)
  }

  actual override fun toString(): String = this.wrapped.toString()

  actual override fun hashCode(): Int = this.wrapped.hashCode()

  actual constructor(
    precision: Int,
    roundingMode: KRoundingMode,
  ) : this(MathContext(precision, roundingMode.wrapped))

  internal constructor(precision: Int) : this(MathContext(precision))

  actual val precision: Int = this.wrapped.precision

  internal val roundingMode: KRoundingMode = KRoundingMode(this.wrapped.roundingMode)
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
    KBigInteger(this.wrapped.div(divisor.wrapped))

  actual companion object {
    actual val ONE: KBigInteger = KBigInteger(BigInteger.ONE)
    actual val ZERO: KBigInteger = KBigInteger(BigInteger.ZERO)
    actual val TEN: KBigInteger = KBigInteger(BigInteger.TEN)

    internal fun valueOf(value: Long) = KBigInteger(BigInteger.of(value))
  }

  actual fun pow(n: Int): KBigInteger = KBigInteger(this.wrapped.pow(n))

  actual fun toKBigDecimal(): KBigDecimal = KBigDecimal(this)

  actual operator fun div(other: KBigInteger): KBigInteger =
    KBigInteger(this.wrapped.div(other.wrapped))

  actual operator fun minus(other: KBigInteger): KBigInteger =
    KBigInteger(this.wrapped.minus(other.wrapped))

  actual fun multiply(other: KBigInteger): KBigInteger =
    KBigInteger(this.wrapped.times(other.wrapped))

  actual constructor(value: String) : this(BigInteger.of(value))

  actual constructor(value: String, radix: Int) : this(BigInteger.of(value, radix))

  actual override fun compareTo(other: KBigInteger): Int = this.wrapped.compareTo(other.wrapped)

  internal val signum: Int = this.wrapped.signum

  internal fun bitLength(): Int = this.wrapped.bitLength
}

actual class KBigDecimalMath {
  actual companion object {
    actual fun toRadians(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.toRadians(bigDecimal, mathContext)

    actual fun sin(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.sin(bigDecimal, mathContext)

    actual fun asin(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.asin(bigDecimal, mathContext)

    actual fun toDegrees(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.toDegrees(bigDecimal, mathContext)

    actual fun cos(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.cos(bigDecimal, mathContext)

    actual fun acos(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.acos(bigDecimal, mathContext)

    actual fun tan(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.tan(bigDecimal, mathContext)

    actual fun atan(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.atan(bigDecimal, mathContext)

    actual fun log(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.log(bigDecimal, mathContext)

    actual fun log10(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.log10(bigDecimal, mathContext)

    actual fun exp(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.exp(bigDecimal, mathContext)

    actual fun pi(mathContext: KMathContext): KBigDecimal = BigDecimalMath.pi(mathContext)

    actual fun e(mathContext: KMathContext): KBigDecimal = BigDecimalMath.e(mathContext)

    actual fun sqrt(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.sqrt(bigDecimal, mathContext)

    actual fun pow(expr: KBigDecimal, factor: KBigDecimal, mathContext: KMathContext): KBigDecimal =
      BigDecimalMath.pow(expr, factor, mathContext)
  }
}

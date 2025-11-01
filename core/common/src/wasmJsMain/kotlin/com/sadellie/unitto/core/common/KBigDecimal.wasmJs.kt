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
    TODO("Not ported from java yet")

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
    KBigInteger(this.wrapped.div(divisor.wrapped))

  actual companion object {
    actual val ONE: KBigInteger = KBigInteger(BigInteger.ONE)
    actual val ZERO: KBigInteger = KBigInteger(BigInteger.ZERO)
    actual val TEN: KBigInteger = KBigInteger(BigInteger.TEN)
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
}

actual class KBigDecimalMath {
  actual override operator fun equals(other: Any?): Boolean {
    TODO("Not yet implemented")
  }

  actual override fun toString(): String {
    TODO("Not yet implemented")
  }

  actual override fun hashCode(): Int {
    TODO("Not yet implemented")
  }

  actual companion object {
    actual fun toRadians(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun sin(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun asin(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun toDegrees(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun cos(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun acos(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun tan(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun atan(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun log(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun log10(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun exp(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun pi(mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun e(mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun sqrt(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }

    actual fun pow(expr: KBigDecimal, factor: KBigDecimal, mathContext: KMathContext): KBigDecimal {
      TODO("Not yet implemented")
    }
  }
}

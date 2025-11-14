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

expect class KBigDecimal : Comparable<KBigDecimal> {

  constructor(string: String)

  constructor(double: Double)

  constructor(string: String, mathContext: KMathContext)

  constructor(bigInteger: KBigInteger)

  companion object {
    val ZERO: KBigDecimal
    val ONE: KBigDecimal
    val TEN: KBigDecimal

    fun valueOf(double: Double): KBigDecimal

    fun valueOf(long: Long): KBigDecimal
  }

  override operator fun compareTo(other: KBigDecimal): Int

  fun stripTrailingZeros(): KBigDecimal

  fun setScale(scale: Int, roundingMode: KRoundingMode): KBigDecimal

  fun scale(): Int

  fun abs(): KBigDecimal

  fun remainder(divisor: KBigDecimal): KBigDecimal

  fun toPlainString(): String

  override fun equals(other: Any?): Boolean

  override fun toString(): String

  fun toEngineeringString(): String

  fun intValueExact(): Int

  fun multiply(multiplier: KBigDecimal): KBigDecimal

  fun div(divisor: KBigDecimal): KBigDecimal

  fun setScale(scale: Int): KBigDecimal

  operator fun plus(addend: KBigDecimal): KBigDecimal

  operator fun minus(subtrahend: KBigDecimal): KBigDecimal

  fun divide(divisor: KBigDecimal, scale: Int, roundingMode: KRoundingMode): KBigDecimal

  operator fun unaryMinus(): KBigDecimal

  fun add(addend: KBigDecimal): KBigDecimal

  fun divide(divisor: KBigDecimal, mathContext: KMathContext): KBigDecimal

  fun toBigInteger(): KBigInteger

  fun toInt(): Int

  operator fun times(bigDecimal: KBigDecimal): KBigDecimal

  fun divideAndRemainder(divisor: KBigDecimal): Array<KBigDecimal>

  fun pow(n: Int): KBigDecimal

  override fun hashCode(): Int

  fun scaleByPowerOfTen(n: Int): KBigDecimal
}

expect class KBigInteger : Comparable<KBigInteger> {
  constructor(value: String)

  constructor(value: String, radix: Int)

  override operator fun compareTo(other: KBigInteger): Int

  override fun equals(other: Any?): Boolean

  override fun toString(): String

  fun toString(radix: Int): String

  fun gcd(d: KBigInteger): KBigInteger

  fun divide(divisor: KBigInteger): KBigInteger

  fun pow(n: Int): KBigInteger

  fun toKBigDecimal(): KBigDecimal

  override fun hashCode(): Int

  operator fun div(other: KBigInteger): KBigInteger

  operator fun minus(other: KBigInteger): KBigInteger

  fun multiply(other: KBigInteger): KBigInteger

  companion object {
    val ONE: KBigInteger
    val ZERO: KBigInteger
    val TEN: KBigInteger
  }
}

expect class KRoundingMode {
  override fun equals(other: Any?): Boolean

  override fun toString(): String

  override fun hashCode(): Int

  companion object {
    val HALF_EVEN: KRoundingMode
    val DOWN: KRoundingMode
  }
}

expect class KMathContext {
  override fun equals(other: Any?): Boolean

  override fun toString(): String

  constructor(precision: Int, roundingMode: KRoundingMode)

  val precision: Int

  override fun hashCode(): Int
}

expect class KBigDecimalMath {
  companion object {
    fun toRadians(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun sin(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun asin(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun toDegrees(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun cos(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun acos(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun tan(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun atan(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun log(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun log10(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun exp(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun pi(mathContext: KMathContext): KBigDecimal

    fun e(mathContext: KMathContext): KBigDecimal

    fun sqrt(bigDecimal: KBigDecimal, mathContext: KMathContext): KBigDecimal

    fun pow(expr: KBigDecimal, factor: KBigDecimal, mathContext: KMathContext): KBigDecimal
  }
}

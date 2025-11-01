/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

import kotlin.math.max

/** Current maximum scale that will be used in app. Used in various places in code. */
const val MAX_SCALE: Int = 1_000

fun KBigDecimal.toFormattedString(scale: Int, outputFormat: Int): String =
  this.setMinimumRequiredScale(scale).trimZeros().toStringWith(outputFormat)

/** Removes all trailing zeroes. */
fun KBigDecimal.trimZeros(): KBigDecimal =
  if (this.isEqualTo(KBigDecimal.ZERO)) KBigDecimal.ZERO else this.stripTrailingZeros()

/**
 * Uses [compareTo], ignores scale differences.
 *
 * @param bd BigDecimal to which this BigDecimal is to be compared.
 * @return `true` if [compareTo] returned 0
 */
fun KBigDecimal.isEqualTo(bd: KBigDecimal): Boolean = compareTo(bd) == 0

/**
 * Uses [compareTo], ignores scale differences.
 *
 * @param bd BigDecimal to which this BigDecimal is to be compared.
 * @return `true` if [compareTo] returned 1
 */
fun KBigDecimal.isGreaterThan(bd: KBigDecimal): Boolean = compareTo(bd) == 1

/**
 * Uses [compareTo], ignores scale differences.
 *
 * @param bd BigDecimal to which this BigDecimal is to be compared.
 * @return `true` if [compareTo] returned -1
 */
fun KBigDecimal.isLessThan(bd: KBigDecimal): Boolean = compareTo(bd) == -1

/** Sets scale to [MAX_SCALE] and round using [KRoundingMode.HALF_EVEN] */
fun KBigDecimal.setMaxScale(): KBigDecimal = setScale(MAX_SCALE, KRoundingMode.HALF_EVEN)

/**
 * Sets the minimum scale that is required to get first non-zero value in fractional part
 *
 * @param[prefScale] Is the preferred scale, the one which will be compared against
 */
internal fun KBigDecimal.setMinimumRequiredScale(prefScale: Int): KBigDecimal {
  // values without scale do not need rescaling. 123 => 123 (will not add trailing zeroes)
  if (this.scale() == 0) return this
  val absValue = this.abs()
  if (absValue.isLessThan(KBigDecimal.ONE)) {
    // remainder returns 0.01234. Need to use substring to skip "0." part
    println("---")
    println(absValue.remainder(KBigDecimal.ONE).toPlainString())
    println(absValue.divideAndRemainder(KBigDecimal.ONE)[0].toPlainString())
    println(absValue.divideAndRemainder(KBigDecimal.ONE)[1].toPlainString())
    println("---")
    val fractionalPart = absValue.remainder(KBigDecimal.ONE).toPlainString().substring(2)
    println(fractionalPart)
    // indexOfFirst can give -1, but it's okay
    // +1 to show first non-zero digit in fractional part
    val scaleToShowFirstNonZeroDigit = fractionalPart.indexOfFirst { char -> char != '0' } + 1
    println(scaleToShowFirstNonZeroDigit)
    val scaleToSet = max(prefScale, scaleToShowFirstNonZeroDigit)
    println(scaleToSet)

    return setScale(scaleToSet, KRoundingMode.HALF_EVEN)
  } else {
    return setScale(prefScale, KRoundingMode.HALF_EVEN)
  }
}

/** Shorthand function to use correct `toString` method according to [outputFormat]. */
private fun KBigDecimal.toStringWith(outputFormat: Int): String =
  // Setting result value using a specified OutputFormat
  when (outputFormat) {
    OutputFormat.ALLOW_ENGINEERING -> this.toString()
    OutputFormat.FORCE_ENGINEERING -> this.toEngineeringString()
    else -> this.toPlainString()
  }

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.max

/** Current maximum scale that will be used in app. Used in various places in code. */
const val MAX_SCALE: Int = 1_000

fun BigDecimal.toFormattedString(scale: Int, outputFormat: Int): String =
  this.setMinimumRequiredScale(scale).trimZeros().toStringWith(outputFormat)

/** Removes all trailing zeroes. */
fun BigDecimal.trimZeros(): BigDecimal =
  if (this.isEqualTo(BigDecimal.ZERO)) BigDecimal.ZERO else this.stripTrailingZeros()

/**
 * Uses [compareTo], ignores scale differences.
 *
 * @param bd BigDecimal to which this BigDecimal is to be compared.
 * @return `true` if [compareTo] returned 0
 */
fun BigDecimal.isEqualTo(bd: BigDecimal): Boolean = compareTo(bd) == 0

/**
 * Uses [compareTo], ignores scale differences.
 *
 * @param bd BigDecimal to which this BigDecimal is to be compared.
 * @return `true` if [compareTo] returned 1
 */
fun BigDecimal.isGreaterThan(bd: BigDecimal): Boolean = compareTo(bd) == 1

/**
 * Uses [compareTo], ignores scale differences.
 *
 * @param bd BigDecimal to which this BigDecimal is to be compared.
 * @return `true` if [compareTo] returned -1
 */
fun BigDecimal.isLessThan(bd: BigDecimal): Boolean = compareTo(bd) == -1

/** Sets scale to [MAX_SCALE] and round using [RoundingMode.HALF_EVEN] */
fun BigDecimal.setMaxScale(): BigDecimal = setScale(MAX_SCALE, RoundingMode.HALF_EVEN)

/**
 * Sets the minimum scale that is required to get first non zero value in fractional part
 *
 * @param[prefScale] Is the preferred scale, the one which will be compared against
 */
internal fun BigDecimal.setMinimumRequiredScale(prefScale: Int): BigDecimal {
  // values without scale do not need rescaling. 123 => 123 (will not add trailing zeroes)
  if (this.scale() == 0) return this
  val absValue = this.abs()
  if (absValue.isLessThan(BigDecimal.ONE)) {
    // remainder returns 0.01234. Need to use substring to skip "0." part
    val fractionalPart = absValue.remainder(BigDecimal.ONE).toPlainString().substring(2)
    // indexOfFirst can give -1, but it's okay
    // +1 to show first non zero digit in fractional part
    val scaleToShowFirstNonZeroDigit = fractionalPart.indexOfFirst { char -> char != '0' } + 1
    val scaleToSet = max(prefScale, scaleToShowFirstNonZeroDigit)

    return setScale(scaleToSet, RoundingMode.HALF_EVEN)
  } else {
    return setScale(prefScale, RoundingMode.HALF_EVEN)
  }
}

/** Shorthand function to use correct `toString` method according to [outputFormat]. */
private fun BigDecimal.toStringWith(outputFormat: Int): String =
  // Setting result value using a specified OutputFormat
  when (outputFormat) {
    OutputFormat.ALLOW_ENGINEERING -> this.toString()
    OutputFormat.FORCE_ENGINEERING -> this.toEngineeringString()
    else -> this.toPlainString()
  }

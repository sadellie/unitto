/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

package com.sadellie.unitto.feature.calculator

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KBigInteger
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.isEqualTo

/**
 * Tries to convert [KBigDecimal] into fractional string.
 * - 0.5 -> `1⁄2`
 * - 123.5 -> `123 1⁄2`
 * - 123 -> `Empty string`
 *
 * @return String with fractional or empty string if fractional output is impossible for [this].
 * @receiver [KBigDecimal]. Scale doesn't matter, will be rescaled to [FRACTIONAL_ACCURACY] for
 *   performance.
 */
fun KBigDecimal.toFractionalString(): String {
  val truncated =
    if (scale() > FRACTIONAL_ACCURACY) {
      setScale(FRACTIONAL_ACCURACY, KRoundingMode.DOWN)
    } else {
      this
    }

  val (integral, fractional) = truncated.divideAndRemainder(KBigDecimal.ONE)
  if (fractional.isEqualTo(KBigDecimal.ZERO)) return ""

  val integralPart = integral.toBigInteger()
  val prefix = if (integral.isEqualTo(KBigDecimal.ZERO)) "" else "$integralPart "

  // Represent the fractional part as targetNumerator / targetDenominator.
  // For example, 0.375 -> 375 / 1000.
  val fractionalScale = fractional.scale()
  val targetDenominator = KBigInteger.TEN.pow(fractionalScale)
  val targetNumerator = fractional.scaleByPowerOfTen(fractionalScale).toBigInteger()

  val fraction =
    approximateFraction(targetNumerator, targetDenominator, maxDenominator) ?: return ""

  return "$prefix${fraction.first}⁄${fraction.second}"
}

/**
 * Finds a fraction numerator/denominator whose denominator does not exceed [maxDenominator], such
 * that:
 *
 *     targetNumerator / targetDenominator  <=  numerator / denominator  <  (targetNumerator + 1) / targetDenominator
 *
 * The left bound corresponds to DOWN truncation; the right bound is strict inequality because
 * (targetNumerator + 1)/targetDenominator is already the next number.
 *
 * Uses continued fraction expansion: each iteration yields increasingly accurate convergents. If
 * the next convergent falls into the interval, it is the answer. If its denominator exceeds the
 * limit, there is no answer.
 */
private fun approximateFraction(
  targetNumerator: KBigInteger,
  targetDenominator: KBigInteger,
  maxDenominator: KBigInteger,
): Pair<KBigInteger, KBigInteger>? {
  if (targetNumerator == KBigInteger.ZERO) return null

  var remainder = targetNumerator
  var divisor = targetDenominator
  var prevNumerator = KBigInteger.ZERO
  var prevDenominator = KBigInteger.ONE
  var currNumerator = KBigInteger.ONE
  var currDenominator = KBigInteger.ZERO
  while (divisor != KBigInteger.ZERO) {
    val quotient = remainder / divisor
    val nextNumerator = prevNumerator + quotient.multiply(currNumerator)
    val nextDenominator = prevDenominator + quotient.multiply(currDenominator)
    if (nextDenominator > maxDenominator) break

    // Check whether nextNumerator/nextDenominator falls into the interval
    // [targetNumerator/targetDenominator, (targetNumerator+1)/targetDenominator).
    // Multiply by the denominators to avoid division.
    val intervalOffset =
      nextNumerator.multiply(targetDenominator) - targetNumerator.multiply(nextDenominator)
    if (intervalOffset >= KBigInteger.ZERO && intervalOffset < nextDenominator) {
      return nextNumerator to nextDenominator
    }

    prevNumerator = currNumerator
    prevDenominator = currDenominator
    currNumerator = nextNumerator
    currDenominator = nextDenominator
    val nextRemainder = remainder.remainder(divisor)
    remainder = divisor
    divisor = nextRemainder
  }

  return null
}

private val maxDenominator by lazy { KBigInteger("1000000000") }
private const val FRACTIONAL_ACCURACY = 30

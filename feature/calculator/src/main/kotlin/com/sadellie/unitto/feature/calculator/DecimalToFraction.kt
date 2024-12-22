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

package com.sadellie.unitto.feature.calculator

import com.sadellie.unitto.core.common.isEqualTo
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Tries to convert [BigDecimal] into fractional string.
 *
 * 0.5 -> `1⁄2`
 *
 * 123.5 -> `123 1⁄2`
 *
 * 123 -> `Empty string`
 *
 * @return
 * @receiver [BigDecimal]. Scale doesn't matter, but should be `MAX_SCALE`
 */
suspend fun BigDecimal.toFractionalString(): String =
  withContext(Dispatchers.Default) {
    // https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-numbers-operations/cc-8th-repeating-decimals/v/coverting-repeating-decimals-to-fractions-1
    // https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-numbers-operations/cc-8th-repeating-decimals/v/coverting-repeating-decimals-to-fractions-2
    val (integral, fractional) = this@toFractionalString.divideAndRemainder(BigDecimal.ONE)
    val integralBI = integral.toBigInteger()

    if (fractional.isEqualTo(BigDecimal.ZERO)) return@withContext ""

    val res: String = if (integral.isEqualTo(BigDecimal.ZERO)) "" else "$integralBI "

    val repeatingDecimals = fractional.repeatingDecimals()

    val (finalNumerator, finalDenominator) =
      if (repeatingDecimals == null) {
        fractional.notRepeatingFractional()
      } else {
        fractional.repeatingFractional(repeatingDecimals.length)
      }

    if (finalDenominator > maxDenominator) return@withContext ""

    return@withContext "$res$finalNumerator⁄$finalDenominator"
  }

private fun BigDecimal.notRepeatingFractional(): Pair<BigInteger, BigInteger> {
  val fractionalPrecision = BigInteger.TEN.pow(scale())

  // 0.000123456 -> 123456
  val fractionalBI: BigInteger = (this * fractionalPrecision.toBigDecimal()).toBigInteger()

  val gcdVal = fractionalBI.gcd(fractionalPrecision)
  val numerator = fractionalBI / gcdVal
  val denominator = fractionalPrecision / gcdVal

  return numerator to denominator
}

private fun BigDecimal.repeatingFractional(repeatingLength: Int): Pair<BigInteger, BigInteger> {
  val multiplier = BigInteger.TEN.pow(repeatingLength)

  val multiplied = (this * multiplier.toBigDecimal()).stripTrailingZeros()

  val numerator =
    (multiplied - this.setScale(multiplied.scale(), RoundingMode.DOWN)).stripTrailingZeros()
  val denominator = multiplier - BigInteger.ONE

  // get rid of decimal in numerator
  val bigIntegerMultiplies = BigDecimal.TEN.pow(scale())
  var finalNumerator = numerator.multiply(bigIntegerMultiplies).toBigInteger()
  var finalDenominator = denominator.multiply(bigIntegerMultiplies.toBigInteger())

  val gcd = finalNumerator.gcd(finalDenominator)
  finalNumerator /= gcd
  finalDenominator /= gcd

  return finalNumerator to finalDenominator
}

fun BigDecimal.repeatingDecimals(): String? {
  // turn 0.123454545 into 123454545
  val inputString = scaleByPowerOfTen(scale()).toBigInteger().toString()

  repeat(inputString.length) { index ->
    // check string in front and drop first char each time repeating decimal is not found
    val stringInFront = inputString.substring(index)
    // it is a pattern only if it repeats at least 2 times
    val maxPatternLength = stringInFront.length / 2
    // start at 1
    for (patternLength in 1..maxPatternLength) {
      // build a pattern by taking a few characters
      // increase until repeating decimal is not found
      val pattern = stringInFront.take(patternLength)
      // zeroes can't be repeating decimals, always trimmed
      if (pattern.all { it == '0' }) continue
      // how many characters to take to avoid incomplete chunks
      val maxCheckRangeBound = stringInFront.length - stringInFront.length % pattern.length
      val stringToCheck = stringInFront.substring(0, maxCheckRangeBound)
      // split the string to check
      val checkChunks = stringToCheck.chunked(patternLength)
      val isRepeating = checkChunks.all { it == pattern }
      if (isRepeating) return pattern
    }
  }

  return null
}

private val maxDenominator by lazy { BigInteger("1000000000") }

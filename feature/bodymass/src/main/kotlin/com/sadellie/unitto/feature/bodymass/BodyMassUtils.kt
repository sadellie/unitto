/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.bodymass

import com.sadellie.unitto.core.common.MAX_SCALE
import com.sadellie.unitto.core.common.isEqualTo
import com.sadellie.unitto.core.common.isLessThan
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Calculates BMI value for metric system.
 *
 * @param heightCm Height in centimeters.
 * @param weightKg Weight in kilograms.
 * @return BMI Value with [MAX_SCALE].
 */
internal fun calculateMetric(heightCm: BigDecimal, weightKg: BigDecimal): BigDecimal {
  if (heightCm.isLessThan(BigDecimal.ZERO)) return BigDecimal.ZERO
  if (weightKg.isLessThan(BigDecimal.ZERO)) return BigDecimal.ZERO
  if (heightCm.isEqualTo(BigDecimal.ZERO)) return BigDecimal.ZERO
  if (weightKg.isEqualTo(BigDecimal.ZERO)) return BigDecimal.ZERO

  val heightMeters = heightCm.divide(cmToMFactor, MAX_SCALE, RoundingMode.HALF_EVEN)

  return weightKg.divide(heightMeters.pow(2), MAX_SCALE, RoundingMode.HALF_EVEN)
}

/**
 * Calculates BMI value for imperial system.
 *
 * @param heightFt Height in feet.
 * @param heightIn Height in inches.
 * @param weightLbs Weight in pounds.
 * @return BMI Value with [MAX_SCALE] or [BigDecimal.ZERO] if input is negative or zero.
 */
internal fun calculateImperial(
  heightFt: BigDecimal,
  heightIn: BigDecimal,
  weightLbs: BigDecimal,
): BigDecimal {
  if (heightFt.isLessThan(BigDecimal.ZERO)) return BigDecimal.ZERO
  if (heightIn.isLessThan(BigDecimal.ZERO)) return BigDecimal.ZERO
  if (weightLbs.isLessThan(BigDecimal.ZERO)) return BigDecimal.ZERO
  if (heightFt.isEqualTo(BigDecimal.ZERO) && heightIn.isEqualTo(BigDecimal.ZERO))
    return BigDecimal.ZERO
  if (weightLbs.isEqualTo(BigDecimal.ZERO)) return BigDecimal.ZERO

  val heightInches = heightFt.multiply(footToInchFactor).plus(heightIn)

  return weightLbs
    .divide(heightInches.pow(2), MAX_SCALE, RoundingMode.HALF_EVEN)
    .multiply(metricToImperialFactor) // Approximate lbs/inch^2 to kg/m^2
}

/**
 * Calculates weight (kilograms) range for normal BMI values.
 *
 * @param heightCm Height in centimeters.
 * @return [Pair] of [BigDecimal]. First value is lowest weight. Second value is highest value. If
 *   input is negative or zero, will return range of zeros.
 */
internal fun calculateNormalWeightMetric(heightCm: BigDecimal): Pair<BigDecimal, BigDecimal> {
  if (heightCm.isLessThan(BigDecimal.ZERO) || heightCm.isEqualTo(BigDecimal.ZERO))
    return BigDecimal.ZERO to BigDecimal.ZERO

  val heightMetres2 = heightCm.divide(cmToMFactor, MAX_SCALE, RoundingMode.HALF_EVEN).pow(2)

  val lowest = lowestNormalIndex.multiply(heightMetres2)
  val highest = highestNormalIndex.multiply(heightMetres2)

  return lowest to highest
}

/**
 * Calculates weight (pounds) range for normal BMI values.
 *
 * @param heightFt Height in feet.
 * @param heightIn Height in inches.
 * @return [Pair] of [BigDecimal]. First value is lowest weight. Second value is highest value. If
 *   input is negative or zero, will return range of zeros.
 */
internal fun calculateNormalWeightImperial(
  heightFt: BigDecimal,
  heightIn: BigDecimal,
): Pair<BigDecimal, BigDecimal> {
  if (heightFt.isLessThan(BigDecimal.ZERO)) return BigDecimal.ZERO to BigDecimal.ZERO
  if (heightIn.isLessThan(BigDecimal.ZERO)) return BigDecimal.ZERO to BigDecimal.ZERO
  if (heightFt.isEqualTo(BigDecimal.ZERO) && heightIn.isEqualTo(BigDecimal.ZERO))
    return BigDecimal.ZERO to BigDecimal.ZERO

  val heightInches2 =
    heightFt
      .multiply(footToInchFactor)
      .plus(heightIn)
      .pow(2)
      .divide(metricToImperialFactor, MAX_SCALE, RoundingMode.HALF_EVEN)

  val lowest = lowestNormalIndex.multiply(heightInches2)
  val highest = highestNormalIndex.multiply(heightInches2)

  return lowest to highest
}

private val cmToMFactor = BigDecimal("100")
private val footToInchFactor = BigDecimal("12")
private val metricToImperialFactor = BigDecimal("703")

private val lowestNormalIndex = BigDecimal("18.5")
private val highestNormalIndex = BigDecimal("25")

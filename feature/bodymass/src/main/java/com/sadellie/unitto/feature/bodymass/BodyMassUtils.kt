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

import com.sadellie.unitto.core.base.MAX_PRECISION
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Calculates BMI value for metric system.
 *
 * @param heightCm Height in centimeters.
 * @param weightKg Weight in kilograms.
 * @return BMI Value with [MAX_PRECISION].
 */
internal fun calculateMetric(
    heightCm: BigDecimal,
    weightKg: BigDecimal,
): BigDecimal {
    val heightMeters = heightCm
        .divide(cmToMFactor, MAX_PRECISION, RoundingMode.HALF_EVEN)

    return weightKg
        .divide(heightMeters.pow(2), MAX_PRECISION, RoundingMode.HALF_EVEN)
}

/**
 * Calculates BMI value for imperial system.
 *
 * @param heightFt Height in feet.
 * @param heightIn Height in inches.
 * @param weightLbs Weight in pounds.
 * @return BMI Value with [MAX_PRECISION].
 */
internal fun calculateImperial(
    heightFt: BigDecimal,
    heightIn: BigDecimal,
    weightLbs: BigDecimal,
): BigDecimal {
    val heightInches = heightFt
        .multiply(footToInchFactor)
        .plus(heightIn)

    return weightLbs
        .divide(heightInches.pow(2), MAX_PRECISION, RoundingMode.HALF_EVEN)
        .multiply(metricToImperialFactor) // Approximate lbs/inch^2 to kg/m^2
}

/**
 * Calculates weight (kilograms) range for normal BMI values.
 *
 * @param heightCm Height in centimeters.
 * @return [Pair] of [BigDecimal]. First value is lowest weight. Second value is highest value.
 */
internal fun calculateNormalWeightMetric(
    heightCm: BigDecimal,
): Pair<BigDecimal, BigDecimal> {
    val heightMetres2 = heightCm
        .divide(cmToMFactor, MAX_PRECISION, RoundingMode.HALF_EVEN)
        .pow(2)

    val lowest = lowestNormalIndex.multiply(heightMetres2)
    val highest = highestNormalIndex.multiply(heightMetres2)

    return lowest to highest
}

/**
 * Calculates weight (pounds) range for normal BMI values.
 *
 * @param heightFt Height in feet.
 * @param heightIn Height in inches.
 * @return [Pair] of [BigDecimal]. First value is lowest weight. Second value is highest value.
 */
internal fun calculateNormalWeightImperial(
    heightFt: BigDecimal,
    heightIn: BigDecimal,
): Pair<BigDecimal, BigDecimal> {
    val heightInches2 = heightFt
        .multiply(footToInchFactor)
        .plus(heightIn)
        .pow(2)
        .divide(metricToImperialFactor, MAX_PRECISION, RoundingMode.HALF_EVEN)

    val lowest = lowestNormalIndex.multiply(heightInches2)
    val highest = highestNormalIndex.multiply(heightInches2)

    return lowest to highest
}

private val cmToMFactor = BigDecimal("100")
private val footToInchFactor = BigDecimal("12")
private val metricToImperialFactor = BigDecimal("703")

private val lowestNormalIndex = BigDecimal("18.5")
private val highestNormalIndex = BigDecimal("25")

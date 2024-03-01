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

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

class BodyMassUtilsTest {
  @Test
  fun calculateMetric_positiveNumbers() {
    val heightCm = BigDecimal("190")
    val weightKg = BigDecimal("80")
    val expected = BigDecimal("22.161").scaleDown()
    val actual = calculateMetric(heightCm, weightKg).scaleDown()

    assertEquals(expected, actual)
  }

  @Test
  fun calculateMetric_negativeNumbers() {
    val heightCm = BigDecimal("-190")
    val weightKg = BigDecimal("80")
    val expected = BigDecimal.ZERO
    val actual = calculateMetric(heightCm, weightKg)

    assertEquals(expected, actual)
  }

  @Test
  fun calculateMetric_zero() {
    val heightCm = BigDecimal.ZERO
    val weightKg = BigDecimal.ZERO
    val expected = BigDecimal.ZERO
    val actual = calculateMetric(heightCm, weightKg)

    assertEquals(expected, actual)
  }

  @Test
  fun calculateImperial_positiveNumbers() {
    val heightFt = BigDecimal("6")
    val heightIn = BigDecimal("5")
    val weightLbs = BigDecimal("150")
    val expected = BigDecimal("17.785")
    val actual = calculateImperial(heightFt, heightIn, weightLbs).scaleDown()

    assertEquals(expected, actual)
  }

  @Test
  fun calculateImperial_negativeNumbers() {
    val heightFt = BigDecimal("-6")
    val heightIn = BigDecimal("5")
    val weightLbs = BigDecimal.ZERO
    val expected = BigDecimal.ZERO
    val actual = calculateImperial(heightFt, heightIn, weightLbs)

    assertEquals(expected, actual)
  }

  @Test
  fun calculateImperial_zero() {
    val heightFt = BigDecimal.ZERO
    val heightIn = BigDecimal.ZERO
    val weightLbs = BigDecimal.ZERO
    val expected = BigDecimal.ZERO
    val actual = calculateImperial(heightFt, heightIn, weightLbs)

    assertEquals(expected, actual)
  }

  @Test
  fun calculateNormalWeightMetric_positiveNumber() {
    val height = BigDecimal("190")
    val expectedWeight1 = BigDecimal("66.785")
    val expectedWeight2 = BigDecimal("90.250")
    val actualWeight = calculateNormalWeightMetric(height)

    assertEquals(expectedWeight1, actualWeight.first.scaleDown())
    assertEquals(expectedWeight2, actualWeight.second.scaleDown())
  }

  @Test
  fun calculateNormalWeightMetric_negativeNumber() {
    val height = BigDecimal("-190")
    val expectedWeight1 = BigDecimal.ZERO
    val expectedWeight2 = BigDecimal.ZERO
    val actualWeight = calculateNormalWeightMetric(height)

    assertEquals(expectedWeight1, actualWeight.first)
    assertEquals(expectedWeight2, actualWeight.second)
  }

  @Test
  fun calculateNormalWeightMetric_zero() {
    val height = BigDecimal.ZERO
    val expectedWeight1 = BigDecimal.ZERO
    val expectedWeight2 = BigDecimal.ZERO
    val actualWeight = calculateNormalWeightMetric(height)

    assertEquals(expectedWeight1, actualWeight.first)
    assertEquals(expectedWeight2, actualWeight.second)
  }

  @Test
  fun calculateNormalWeightImperial_positiveNumbers() {
    val heightFt = BigDecimal("6")
    val heightIn = BigDecimal("5")
    val expectedWeight1 = BigDecimal("156.026")
    val expectedWeight2 = BigDecimal("210.846")
    val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

    assertEquals(expectedWeight1, actualWeight.first.scaleDown())
    assertEquals(expectedWeight2, actualWeight.second.scaleDown())
  }

  @Test
  fun calculateNormalWeightImperial_negativeNumbers() {
    val heightFt = BigDecimal("-6")
    val heightIn = BigDecimal("5")
    val expectedWeight1 = BigDecimal.ZERO
    val expectedWeight2 = BigDecimal.ZERO
    val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

    assertEquals(expectedWeight1, actualWeight.first)
    assertEquals(expectedWeight2, actualWeight.second)
  }

  @Test
  fun calculateNormalWeightImperial_zero() {
    val heightFt = BigDecimal("0")
    val heightIn = BigDecimal("5")
    val expectedWeight1 = BigDecimal.ZERO
    val expectedWeight2 = BigDecimal.ZERO
    val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

    assertEquals(expectedWeight1, actualWeight.first)
    assertEquals(expectedWeight2, actualWeight.second)
  }

  private fun BigDecimal.scaleDown(): BigDecimal = setScale(3, RoundingMode.HALF_EVEN)
}

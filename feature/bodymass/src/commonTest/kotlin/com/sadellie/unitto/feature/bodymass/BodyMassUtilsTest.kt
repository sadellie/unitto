/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KRoundingMode
import org.junit.Assert.assertEquals
import org.junit.Test

class BodyMassUtilsTest {
  @Test
  fun calculateMetric_positiveNumbers() {
    val heightCm = KBigDecimal("190")
    val weightKg = KBigDecimal("80")
    val expected = KBigDecimal("22.161").scaleDown()
    val actual = calculateMetric(heightCm, weightKg).scaleDown()

    assertEquals(expected, actual)
  }

  @Test
  fun calculateMetric_negativeNumbers() {
    val heightCm = KBigDecimal("-190")
    val weightKg = KBigDecimal("80")
    val expected = KBigDecimal.ZERO
    val actual = calculateMetric(heightCm, weightKg)

    assertEquals(expected, actual)
  }

  @Test
  fun calculateMetric_zero() {
    val heightCm = KBigDecimal.ZERO
    val weightKg = KBigDecimal.ZERO
    val expected = KBigDecimal.ZERO
    val actual = calculateMetric(heightCm, weightKg)

    assertEquals(expected, actual)
  }

  @Test
  fun calculateImperial_positiveNumbers() {
    val heightFt = KBigDecimal("6")
    val heightIn = KBigDecimal("5")
    val weightLbs = KBigDecimal("150")
    val expected = KBigDecimal("17.785")
    val actual = calculateImperial(heightFt, heightIn, weightLbs).scaleDown()

    assertEquals(expected, actual)
  }

  @Test
  fun calculateImperial_negativeNumbers() {
    val heightFt = KBigDecimal("-6")
    val heightIn = KBigDecimal("5")
    val weightLbs = KBigDecimal.ZERO
    val expected = KBigDecimal.ZERO
    val actual = calculateImperial(heightFt, heightIn, weightLbs)

    assertEquals(expected, actual)
  }

  @Test
  fun calculateImperial_zeroAll() {
    val heightFt = KBigDecimal.ZERO
    val heightIn = KBigDecimal.ZERO
    val weightLbs = KBigDecimal.ZERO
    val expected = KBigDecimal.ZERO
    val actual = calculateImperial(heightFt, heightIn, weightLbs)

    assertEquals(expected, actual)
  }

  @Test
  fun calculateImperial_zeroFeet() {
    val heightFt = KBigDecimal.ZERO
    val heightIn = KBigDecimal("12")
    val weightLbs = KBigDecimal("150")
    val expectedWeight1 = KBigDecimal("732.292")
    val actual = calculateImperial(heightFt, heightIn, weightLbs).scaleDown()

    assertEquals(expectedWeight1, actual)
  }

  @Test
  fun calculateImperial_zeroInch() {
    val heightFt = KBigDecimal("1")
    val heightIn = KBigDecimal.ZERO
    val weightLbs = KBigDecimal("150")
    val expectedWeight1 = KBigDecimal("732.292")
    val actual = calculateImperial(heightFt, heightIn, weightLbs).scaleDown()

    assertEquals(expectedWeight1, actual)
  }

  @Test
  fun calculateImperial_zeroWeightLbs() {
    val heightFt = KBigDecimal("1")
    val heightIn = KBigDecimal("12")
    val weightLbs = KBigDecimal.ZERO
    val expectedWeight1 = KBigDecimal.ZERO
    val actual = calculateImperial(heightFt, heightIn, weightLbs)

    assertEquals(expectedWeight1, actual)
  }

  @Test
  fun calculateNormalWeightMetric_positiveNumber() {
    val height = KBigDecimal("190")
    val expectedWeight1 = KBigDecimal("66.785")
    val expectedWeight2 = KBigDecimal("90.250")
    val actualWeight = calculateNormalWeightMetric(height)

    assertEquals(expectedWeight1, actualWeight.first.scaleDown())
    assertEquals(expectedWeight2, actualWeight.second.scaleDown())
  }

  @Test
  fun calculateNormalWeightMetric_negativeNumber() {
    val height = KBigDecimal("-190")
    val expectedWeight1 = KBigDecimal.ZERO
    val expectedWeight2 = KBigDecimal.ZERO
    val actualWeight = calculateNormalWeightMetric(height)

    assertEquals(expectedWeight1, actualWeight.first)
    assertEquals(expectedWeight2, actualWeight.second)
  }

  @Test
  fun calculateNormalWeightMetric_zero() {
    val height = KBigDecimal.ZERO
    val expectedWeight1 = KBigDecimal.ZERO
    val expectedWeight2 = KBigDecimal.ZERO
    val actualWeight = calculateNormalWeightMetric(height)

    assertEquals(expectedWeight1, actualWeight.first)
    assertEquals(expectedWeight2, actualWeight.second)
  }

  @Test
  fun calculateNormalWeightImperial_positiveNumbers() {
    val heightFt = KBigDecimal("6")
    val heightIn = KBigDecimal("5")
    val expectedWeight1 = KBigDecimal("156.026")
    val expectedWeight2 = KBigDecimal("210.846")
    val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

    assertEquals(expectedWeight1, actualWeight.first.scaleDown())
    assertEquals(expectedWeight2, actualWeight.second.scaleDown())
  }

  @Test
  fun calculateNormalWeightImperial_negativeNumbers() {
    val heightFt = KBigDecimal("-6")
    val heightIn = KBigDecimal("5")
    val expectedWeight1 = KBigDecimal.ZERO
    val expectedWeight2 = KBigDecimal.ZERO
    val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

    assertEquals(expectedWeight1, actualWeight.first)
    assertEquals(expectedWeight2, actualWeight.second)
  }

  @Test
  fun calculateNormalWeightImperial_zeroFeet() {
    val heightFt = KBigDecimal.ZERO
    val heightIn = KBigDecimal("12")
    val expectedWeight1 = KBigDecimal("3.789")
    val expectedWeight2 = KBigDecimal("5.121")
    val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

    assertEquals(expectedWeight1, actualWeight.first.scaleDown())
    assertEquals(expectedWeight2, actualWeight.second.scaleDown())
  }

  @Test
  fun calculateNormalWeightImperial_zeroInch() {
    val heightFt = KBigDecimal("1")
    val heightIn = KBigDecimal.ZERO
    val expectedWeight1 = KBigDecimal("3.789")
    val expectedWeight2 = KBigDecimal("5.121")
    val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

    assertEquals(expectedWeight1, actualWeight.first.scaleDown())
    assertEquals(expectedWeight2, actualWeight.second.scaleDown())
  }

  @Test
  fun calculateNormalWeightImperial_zeroAll() {
    val heightFt = KBigDecimal.ZERO
    val heightIn = KBigDecimal.ZERO
    val expectedWeight1 = KBigDecimal.ZERO
    val expectedWeight2 = KBigDecimal.ZERO
    val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

    assertEquals(expectedWeight1, actualWeight.first)
    assertEquals(expectedWeight2, actualWeight.second)
  }

  private fun KBigDecimal.scaleDown(): KBigDecimal = setScale(3, KRoundingMode.HALF_EVEN)
}

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

class BodyMassTest {
    @Test
    fun testCalculateMetric() {
        val height = BigDecimal("190")
        val weight = BigDecimal("80")
        val expected = BigDecimal("22.161").scaleDown()
        val actual = calculateMetric(height, weight).scaleDown()

        assertEquals(expected, actual)
    }

    @Test
    fun testCalculateImperial() {
        val height1 = BigDecimal("6")
        val height2 = BigDecimal("5")
        val weight = BigDecimal("150")
        val expected = BigDecimal("17.785")
        val actual = calculateImperial(height1, height2, weight).scaleDown()

        assertEquals(expected, actual)
    }

    @Test
    fun testCalculateNormalWeightMetric() {
        val height = BigDecimal("190")
        val expectedWeight1 = BigDecimal("66.785")
        val expectedWeight2 = BigDecimal("90.250")
        val actualWeight = calculateNormalWeightMetric(height)

        assertEquals(expectedWeight1, actualWeight.first.scaleDown())
        assertEquals(expectedWeight2, actualWeight.second.scaleDown())
    }

    @Test
    fun testCalculateNormalWeightImperial() {
        val heightFt = BigDecimal("6")
        val heightIn = BigDecimal("5")
        val expectedWeight1 = BigDecimal("156.026")
        val expectedWeight2 = BigDecimal("210.846")
        val actualWeight = calculateNormalWeightImperial(heightFt, heightIn)

        assertEquals(expectedWeight1, actualWeight.first.scaleDown())
        assertEquals(expectedWeight2, actualWeight.second.scaleDown())
    }

    private fun BigDecimal.scaleDown(): BigDecimal = setScale(3, RoundingMode.HALF_EVEN)
}

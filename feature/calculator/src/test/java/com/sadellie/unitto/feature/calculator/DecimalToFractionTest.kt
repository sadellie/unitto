/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class DecimalToFractionTest {
    @Test
    fun testNoDecimal1() {
        val bd = BigDecimal("100")
        assertFractional("", bd.toFractionalString())
    }

    @Test
    fun testNoDecimal2() {
        val bd = BigDecimal("100.000000000")
        assertFractional("", bd.toFractionalString())
    }

    @Test
    fun testSimpleDecimal1() {
        val bd = BigDecimal("0.25")
        assertFractional("1/4", bd.toFractionalString())
    }

    @Test
    fun testSimpleDecimal2() {
        val bd = BigDecimal("100.25")
        assertFractional("100 1/4", bd.toFractionalString())
    }

    @Test
    fun testRepeating1() {
        val bd = BigDecimal("0.666666666")
        assertFractional("2/3", bd.toFractionalString())
    }

    @Test
    fun testRepeating2() {
        val bd = BigDecimal("4.666666666")
        assertFractional("4 2/3", bd.toFractionalString())
    }

    @Test
    fun testRepeating3() {
        val bd = BigDecimal("0.78571428571428571428")
        assertFractional("11/14", bd.toFractionalString())
    }

    @Test
    fun testRepeating4() {
        val bd = BigDecimal("66.78571428571428571428")
        assertFractional("66 11/14", bd.toFractionalString())
    }

    @Test
    fun testRepeating5() {
        val bd = BigDecimal("0.666000")
        assertFractional("333/500", bd.toFractionalString())
    }

    private fun assertFractional(expected: String, actual: String) = assertEquals(
        expected,
        actual.replace("⁄", "/")
    )
}

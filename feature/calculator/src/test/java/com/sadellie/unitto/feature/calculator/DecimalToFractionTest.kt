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

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class DecimalToFractionTest {
    @Test
    fun testNoDecimal1() {
        assertFractional("", "100")
    }

    @Test
    fun testNoDecimal2() {
        assertFractional("", "100.000000000")
    }

    @Test
    fun testSimpleDecimal1() {
        assertFractional("1/4", "0.25")
    }

    @Test
    fun testSimpleDecimal2() {
        assertFractional("100 1/4", "100.25")
    }

    @Test
    fun testRepeating1() {
        assertFractional("2/3", "0.666666666")
    }

    @Test
    fun testRepeating2() {
        assertFractional("4 2/3", "4.666666666")
    }

    @Test
    fun testRepeating3() {
        assertFractional("11/14", "0.78571428571428571428")
    }

    @Test
    fun testRepeating4() {
        assertFractional("66 11/14", "66.78571428571428571428")
    }

    @Test
    fun testRepeating5() {
        assertFractional("333/500", "0.666000")
    }

    private fun assertFractional(expected: String, actual: String) = assertEquals(
        expected,
        runBlocking { BigDecimal(actual).toFractionalString() }
            .replace("⁄", "/"),
    )
}

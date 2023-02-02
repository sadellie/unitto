/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.data.units

import com.sadellie.unitto.data.setMinimumRequiredScale
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class MinimumRequiredScaleTest {

    @Test
    fun setMinimumRequiredScalePrefTooLow() {
        // We prefer scale of 2, which is too low
        val bd = BigDecimal("0.000000123456").setMinimumRequiredScale(2)
        assertEquals("0.0000001", bd.toPlainString())
    }

    @Test
    fun setMinimumRequiredScalePrefTooHigh() {
        // We prefer scale of 15, which is too high
        val bd = BigDecimal("0.000000123456").setMinimumRequiredScale(15)
        assertEquals("0.000000123456000", bd.toPlainString())
    }

    @Test
    fun setMinimumRequiredScalePrefBetween() {
        // We prefer scale of 9, which will cut this value
        val bd = BigDecimal("0.000000123456").setMinimumRequiredScale(9)
        assertEquals("0.000000123", bd.toPlainString())
    }
}
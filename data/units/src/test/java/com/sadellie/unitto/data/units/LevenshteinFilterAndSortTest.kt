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

import android.content.Context
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.model.unit.filterByLev
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.math.BigDecimal

val baseList = listOf(
    NormalUnit("", BigDecimal.ONE, UnitGroup.ANGLE, R.string.attometer, R.string.attometer_short),
    NormalUnit("", BigDecimal.ONE, UnitGroup.ANGLE, R.string.nanometer, R.string.nanometer_short),
    NormalUnit("", BigDecimal.ONE, UnitGroup.ANGLE, R.string.millimeter, R.string.millimeter_short),
    NormalUnit("", BigDecimal.ONE, UnitGroup.ANGLE, R.string.meter, R.string.meter_short),
    NormalUnit("", BigDecimal.ONE, UnitGroup.ANGLE, R.string.kilometer, R.string.kilometer_short),
    NormalUnit("", BigDecimal.ONE, UnitGroup.ANGLE, R.string.mile, R.string.mile_short),
    NormalUnit("", BigDecimal.ONE, UnitGroup.ANGLE, R.string.pound, R.string.pound_short),
    NormalUnit("", BigDecimal.ONE, UnitGroup.ANGLE, R.string.kilometer_per_square_second, R.string.kilometer_per_square_second_short),
)

@RunWith(RobolectricTestRunner::class)
class LevenshteinFilterAndSortTest {

    private val mContext: Context = RuntimeEnvironment.getApplication().applicationContext

    @Test
    fun testOneEdit() {
        val searchQuery = "Kelometer"
        val result = baseList.asSequence().filterByLev(searchQuery, mContext).map { mContext.getString(it.displayName) }.toList()
        println(result)
        assertEquals(
            listOf("Kilometer", "Kilometer per square", "Attometer", "Nanometer"),
            result
        )
    }

    @Test
    fun testLongQuery() {
        val searchQuery = "Kelometers per"
        val result = baseList.asSequence().filterByLev(searchQuery, mContext).map { mContext.getString(it.displayName) }.toList()
        println(result)
        assertEquals(
            listOf("Kilometer per square", "Kilometer"),
            result
        )
    }

    @Test
    fun testMultipleMatches() {
        val searchQuery = "meter"
        val result = baseList.asSequence().filterByLev(searchQuery, mContext).map { mContext.getString(it.displayName) }.toList()
        println(result)
        assertEquals(
            listOf("Meter", "Attometer", "Nanometer", "Millimeter", "Kilometer","Kilometer per square"),
            result
        )
    }

    @Test
    fun testNone() {
        val searchQuery = "Very long unit name that doesn't exist"
        val result = baseList.asSequence().filterByLev(searchQuery, mContext).map { mContext.getString(it.displayName) }.toList()
        println(result)
        assertEquals(
            listOf<String>(),
            result
        )
    }

    @Test
    fun testLowerAndUpperCases() {
        val searchQuery = "T"
        val result = baseList.asSequence().filterByLev(searchQuery, mContext).map { mContext.getString(it.displayName) }.toList()
        println(result)
        assertEquals(
            listOf("Attometer", "Nanometer", "Millimeter", "Meter", "Kilometer", "Kilometer per square"),
            result
        )
    }
}
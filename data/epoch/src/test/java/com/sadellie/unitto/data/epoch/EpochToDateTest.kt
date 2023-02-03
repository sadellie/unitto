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

package com.sadellie.unitto.data.epoch

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EpochToDateTest {

    @Test
    fun `0`() {
        convertUnixToDate("0", "00:00:00 01.01.1970")
    }

    @Test
    fun `00000000000000 max input length`() {
        convertUnixToDate("00000000000000", "00:00:00 01.01.1970")
    }

    @Test
    fun `253402300559 max possible date`() {
        convertUnixToDate("253402300559", "23:55:59 31.12.9999")
    }

    @Test
    fun `2534023005599999 input longe than allowed`() {
        convertUnixToDate("2534023005599999", "23:55:59 31.12.9999", IllegalArgumentException())
    }

    @Test
    fun `99999999999999 max possible unix with max length`() {
        convertUnixToDate("99999999999999", "23:55:59 31.12.9999", IllegalArgumentException())
    }

    private fun convertUnixToDate(inputUnix: String, expectedDate: String, throwable: Throwable? = null) {
        // Date input comes "fancy"
        val cleanExpectedDate = expectedDate.filter{ it.isDigit() }

        // Will throw
        if (throwable != null) {
            assertThrows(
                "Failed to throw ${throwable.javaClass} when converting $inputUnix into $expectedDate ($cleanExpectedDate)",
                throwable.javaClass
            ) { EpochDateConverter.convertUnixToDate(inputUnix) }
            return
        }

        // Should actually convert
        assertEquals(
            "Couldn't convert $inputUnix into $expectedDate ($cleanExpectedDate)",
            cleanExpectedDate,
            EpochDateConverter.convertUnixToDate(inputUnix)
        )
    }
}

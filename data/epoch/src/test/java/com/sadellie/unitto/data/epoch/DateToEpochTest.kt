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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DateToEpochTest {

    @Test
    fun `00h0m00s 00 00 0000`() {
        convertDateToUnixTest("00:00:00 00.00.0000", "0")
    }

    @Test
    fun `00h00m00s 01 01 0001`() {
        convertDateToUnixTest("00:00:00 01.01.0001", "-62135596800")
    }

    @Test
    fun `00h00m00s 01 01 1970`() {
        convertDateToUnixTest("00:00:00 01.01.1970", "0")
    }

    @Test
    fun `23h55m59s 31 12 9999`() {
        convertDateToUnixTest("23:55:59 31.12.9999", "253402300559")
    }

    @Test
    fun `99h99m99s 99 99 9999`() {
        convertDateToUnixTest("99:99:99 99.99.9999", "0")
    }

    private fun convertDateToUnixTest(inputDate: String, expectedUnix: String) {
        // Date input comes "fancy"
        val cleanInputDate = inputDate.filter{ it.isDigit() }
        assertEquals(
            "Couldn't convert $inputDate ($cleanInputDate) into unix",
            expectedUnix,
            EpochDateConverter.convertDateToUnix(cleanInputDate)
        )
    }
}

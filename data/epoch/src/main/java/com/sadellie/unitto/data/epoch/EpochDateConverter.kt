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

import java.math.BigDecimal
import java.util.*

object EpochDateConverter {
    fun convertDateToUnix(date: String): String {
        // Here we add some zeros, so that input is 14 symbols long
        val inputWithPadding = date.padEnd(14, '0')

        // Now we break input that is 14 symbols into pieces
        val hour = inputWithPadding.substring(0, 2)
        val minute = inputWithPadding.substring(2, 4)
        val second = inputWithPadding.substring(4, 6)
        val day = inputWithPadding.substring(6, 8)
        val month = inputWithPadding.substring(8, 10)
        val year = inputWithPadding.substring(10, 14)

        val cal = Calendar.getInstance()
        cal.set(
            year.toIntOrNull() ?: 1970,
            (month.toIntOrNull() ?: 1) - 1,
            day.toIntOrNull() ?: 0,
            hour.toIntOrNull() ?: 0,
            minute.toIntOrNull() ?: 0,
            second.toIntOrNull() ?: 0,
        )
        return (cal.timeInMillis / 1000).toString()
    }

    fun convertUnixToDate(unix: String): String {
        var date = ""
        val cal2 = Calendar.getInstance()
        cal2.clear()
        cal2.isLenient = true

        // This lets us bypass calendars limits (it uses Int, we want BigDecimal)
        try {
            val unixBg = BigDecimal(unix.ifEmpty { "0" })
            val division = unixBg.divideAndRemainder(BigDecimal(Int.MAX_VALUE))
            val intTimes = division.component1()
            val rem = division.component2()
            repeat(intTimes.intValueExact()) {
                cal2.add(Calendar.SECOND, Int.MAX_VALUE)
            }
            cal2.add(Calendar.SECOND, rem.intValueExact())
        } catch (e: NumberFormatException) {
            return ""
        }
        date += cal2.get(Calendar.HOUR_OF_DAY).toString().padStart(2, '0')
        date += cal2.get(Calendar.MINUTE).toString().padStart(2, '0')
        date += cal2.get(Calendar.SECOND).toString().padStart(2, '0')
        date += cal2.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
        date += (cal2.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        // Year is 4 symbols long
        date += cal2.get(Calendar.YEAR).toString().padStart(4, '0')
        return date
    }
}
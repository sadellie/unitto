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

package com.sadellie.unitto.feature.datecalculator.difference

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeDifferenceKtTest {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

    @Test
    fun `same dates`() {
        val date1: ZonedDateTime = ZonedDateTime.parse("2023-05-01T12:00+01:00[Europe/Paris]", formatter)

        assertEquals(ZonedDateTimeDifference.Zero, date1 - date1)
    }

    @Test
    fun `positive difference one day`() {
        val date1: ZonedDateTime = ZonedDateTime.parse("2023-05-01T12:00+01:00[Europe/Paris]", formatter)
        val date2: ZonedDateTime = ZonedDateTime.parse("2023-05-02T12:00+01:00[Europe/Paris]", formatter)

        assertEquals(
            ZonedDateTimeDifference.
            Default(
                years = 0,
                months = 0,
                days = 1,
                hours = 0,
                minutes = 0,
                sumYears = BigDecimal("0.003"),
                sumMonths = BigDecimal("0.033"),
                sumDays = BigDecimal("1.000"),
                sumHours = BigDecimal("24.000"),
                sumMinutes = BigDecimal("1440.000"),
            ),
            date1.minus(date2, 3)
        )
    }

    @Test
    fun `positive difference one month`() {
        val date1: ZonedDateTime = ZonedDateTime.parse("2023-05-01T12:00+01:00[Europe/Paris]", formatter)
        val date2: ZonedDateTime = ZonedDateTime.parse("2023-06-01T12:00+01:00[Europe/Paris]", formatter)

        assertEquals(
            ZonedDateTimeDifference.Default(
                years = 0,
                months = 1,
                days = 0,
                hours = 0,
                minutes = 0,
                sumYears = BigDecimal("0.086"),
                sumMonths = BigDecimal("1.033"),
                sumDays = BigDecimal("31.000"),
                sumHours = BigDecimal("744.000"),
                sumMinutes = BigDecimal("44640.000"),
            ),
            date1.minus(date2, 3)
        )
    }

    @Test
    fun `negative difference one day`() {
        val date1: ZonedDateTime = ZonedDateTime.parse("2023-05-02T12:00+01:00[Europe/Paris]", formatter)
        val date2: ZonedDateTime = ZonedDateTime.parse("2023-05-01T12:00+01:00[Europe/Paris]", formatter)

        assertEquals(
            ZonedDateTimeDifference.Default(
                years = 0,
                months = 0,
                days = 1,
                hours = 0,
                minutes = 0,
                sumYears = BigDecimal("0.003"),
                sumMonths = BigDecimal("0.033"),
                sumDays = BigDecimal("1.000"),
                sumHours = BigDecimal("24.000"),
                sumMinutes = BigDecimal("1440.000"),
            ),
            date1.minus(date2, 3)
        )
    }

    @Test
    fun `positive big difference`() {
        val date1: ZonedDateTime = ZonedDateTime.parse("2023-10-25T12:00+01:00[Europe/Paris]", formatter)
        val date2: ZonedDateTime = ZonedDateTime.parse("2023-11-25T12:00+01:00[Europe/Paris]", formatter)

        assertEquals(
            ZonedDateTimeDifference.Default(
                years = 0,
                months = 0,
                days = 30,
                hours = 23,
                minutes = 0,
                sumYears = BigDecimal("0.086"),
                sumMonths = BigDecimal("1.033"),
                sumDays = BigDecimal("31.000"),
                sumHours = BigDecimal("744.000"),
                sumMinutes = BigDecimal("44640.000"),
            ),
            date1.minus(date2, 3)
        )
    }

    @Test
    fun `positive big difference 2`() {
        val date1: ZonedDateTime = ZonedDateTime.parse("2023-11-25T12:00+01:00[Europe/Paris]", formatter)
        val date2: ZonedDateTime = ZonedDateTime.parse("2023-12-25T12:00+01:00[Europe/Paris]", formatter)

        assertEquals(
            ZonedDateTimeDifference.Default(
                years = 0,
                months = 1,
                days = 0,
                hours = 0,
                minutes = 0,
                sumYears = BigDecimal("0.083"),
                sumMonths = BigDecimal("1.000"),
                sumDays = BigDecimal("30.000"),
                sumHours = BigDecimal("720.000"),
                sumMinutes = BigDecimal("43200.000"),
            ),
            date1.minus(date2, 3)
        )
    }
}

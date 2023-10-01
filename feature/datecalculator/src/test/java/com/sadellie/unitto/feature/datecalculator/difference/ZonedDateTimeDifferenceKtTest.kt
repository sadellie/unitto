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

package com.sadellie.unitto.feature.datecalculator.difference

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeDifferenceKtTest {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    private val `may 1 2023`: ZonedDateTime = ZonedDateTime.parse("2023-05-01T12:00+01:00[Europe/Paris]", formatter)
    private val `may 2 2023`: ZonedDateTime = ZonedDateTime.parse("2023-05-02T12:00+01:00[Europe/Paris]", formatter)
    private val `june 1 2023`: ZonedDateTime = ZonedDateTime.parse("2023-06-01T12:00+01:00[Europe/Paris]", formatter)

    @Test
    fun `same dates`() {
        assertEquals(ZonedDateTimeDifference.Zero, `may 1 2023` - `may 1 2023`)
    }

    @Test
    fun `positive difference dates one day`() {
        assertEquals(ZonedDateTimeDifference.Default(days = 1), `may 1 2023` - `may 2 2023`)
    }

    @Test
    fun `positive difference dates one minth`() {
        assertEquals(ZonedDateTimeDifference.Default(months = 1), `may 1 2023` - `june 1 2023`)
    }

    @Test
    fun `negative difference dates one day`() {
        assertEquals(ZonedDateTimeDifference.Default(days = 1), `may 2 2023` - `may 1 2023`)
    }
}

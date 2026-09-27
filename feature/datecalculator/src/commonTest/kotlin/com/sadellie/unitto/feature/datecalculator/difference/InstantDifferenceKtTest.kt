/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.MAX_SCALE
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant
import kotlinx.datetime.TimeZone

class InstantDifferenceKtTest {
  private val timeZone = TimeZone.of("Europe/Paris")

  @Test
  fun `same dates`() {
    val date1 = Instant.parse("2023-05-01T12:00:00+01:00")
    val date2 = Instant.parse("2023-05-01T12:00:00+01:00")
    assertEquals(InstantDifference.Zero, date1.difference(date2, MAX_SCALE, timeZone))
  }

  @Test
  fun `positive difference one day`() {
    val date1 = Instant.parse("2023-05-01T12:00:00+01:00")
    val date2 = Instant.parse("2023-05-02T12:00:00+01:00")

    assertEquals(
      InstantDifference.Default(
        years = 0,
        months = 0,
        days = 1,
        hours = 0,
        minutes = 0,
        sumYears = KBigDecimal("0.003"),
        sumMonths = KBigDecimal("0.033"),
        sumDays = KBigDecimal("1.000"),
        sumHours = KBigDecimal("24.000"),
        sumMinutes = KBigDecimal("1440.000"),
      ),
      date1.difference(otherInstant = date2, scale = 3, timeZone = timeZone),
    )
  }

  @Test
  fun `positive difference one month`() {
    val date1 = Instant.parse("2023-05-01T12:00:00+01:00")
    val date2 = Instant.parse("2023-06-01T12:00:00+01:00")

    assertEquals(
      InstantDifference.Default(
        years = 0,
        months = 1,
        days = 0,
        hours = 0,
        minutes = 0,
        sumYears = KBigDecimal("0.085"),
        sumMonths = KBigDecimal("1.019"),
        sumDays = KBigDecimal("31.000"),
        sumHours = KBigDecimal("744.000"),
        sumMinutes = KBigDecimal("44640.000"),
      ),
      date1.difference(otherInstant = date2, scale = 3, timeZone = timeZone),
    )
  }

  @Test
  fun `negative difference one day`() {
    val date1 = Instant.parse("2023-05-02T12:00:00+01:00")
    val date2 = Instant.parse("2023-05-01T12:00:00+01:00")

    assertEquals(
      InstantDifference.Default(
        years = 0,
        months = 0,
        days = 1,
        hours = 0,
        minutes = 0,
        sumYears = KBigDecimal("0.003"),
        sumMonths = KBigDecimal("0.033"),
        sumDays = KBigDecimal("1.000"),
        sumHours = KBigDecimal("24.000"),
        sumMinutes = KBigDecimal("1440.000"),
      ),
      date1.difference(otherInstant = date2, scale = 3, timeZone = timeZone),
    )
  }

  @Test
  fun `positive big difference`() {
    val date1 = Instant.parse("2023-10-25T12:00:00+01:00")
    val date2 = Instant.parse("2023-11-25T12:00:00+01:00")

    assertEquals(
      InstantDifference.Default(
        years = 0,
        months = 0,
        days = 30,
        hours = 23,
        minutes = 0,
        sumYears = KBigDecimal("0.085"),
        sumMonths = KBigDecimal("1.019"),
        sumDays = KBigDecimal("31.000"),
        sumHours = KBigDecimal("744.000"),
        sumMinutes = KBigDecimal("44640.000"),
      ),
      date1.difference(otherInstant = date2, scale = 3, timeZone = timeZone),
    )
  }

  @Test
  fun `positive big difference 2`() {
    val date1 = Instant.parse("2023-11-25T12:00:00+01:00")
    val date2 = Instant.parse("2023-12-25T12:00:00+01:00")

    assertEquals(
      InstantDifference.Default(
        years = 0,
        months = 1,
        days = 0,
        hours = 0,
        minutes = 0,
        sumYears = KBigDecimal("0.082"),
        sumMonths = KBigDecimal("0.986"),
        sumDays = KBigDecimal("30.000"),
        sumHours = KBigDecimal("720.000"),
        sumMinutes = KBigDecimal("43200.000"),
      ),
      date1.difference(otherInstant = date2, scale = 3, timeZone = timeZone),
    )
  }

  @Test
  fun `common rounding test`() {
    val date1 = Instant.parse("2024-11-01T12:00:00+01:00")
    val date2 = Instant.parse("2027-11-01T12:00:00+01:00")

    assertEquals(
      InstantDifference.Default(
        years = 3,
        months = 0,
        days = 0,
        hours = 0,
        minutes = 0,
        sumYears = KBigDecimal("3.000"),
        sumMonths = KBigDecimal("36.000"),
        sumDays = KBigDecimal("1095.000"),
        sumHours = KBigDecimal("26280.000"),
        sumMinutes = KBigDecimal("1576800.000"),
      ),
      date1.difference(otherInstant = date2, scale = 3, timeZone = timeZone),
    )
  }
}

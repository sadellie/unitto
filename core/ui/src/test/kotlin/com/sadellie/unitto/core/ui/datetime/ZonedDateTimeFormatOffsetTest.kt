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

package com.sadellie.unitto.core.ui.datetime

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.time.ZonedDateTime

@RunWith(RobolectricTestRunner::class)
class ZonedDateTimeFormatOffsetTest {
  private val context = RuntimeEnvironment.getApplication().applicationContext

  @Test
  fun formatOffset_noDifference() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)
    val formatted = currentTime.formatOffset(context, currentTime)

    assertEquals(null, formatted)
  }

  @Test
  fun formatOffset_showPositiveHour() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)
    val offset =
      currentTime
        // + 2h = 14:00
        .plusSeconds(7200)
        .formatOffset(context, currentTime)

    assertEquals("+2h", offset)
  }

  @Test
  fun formatOffset_showPositiveHourMinute() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)
    val offset =
      currentTime
        // + 2h 30m = 14:30
        .plusSeconds(9000)
        .formatOffset(context, currentTime)

    assertEquals("+2h 30m", offset)
  }

  @Test
  fun formatOffset_showPositiveHourMinuteDay() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)
    val offset =
      currentTime
        // + 14h = 02:00 tomorrow
        .plusSeconds(50400)
        .formatOffset(context, currentTime)

    assertEquals("+14h, tomorrow", offset)
  }

  @Test
  fun formatOffset_showPositiveMinute() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)
    val offset =
      currentTime
        // + 30m = 12:30
        .plusSeconds(1800)
        .formatOffset(context, currentTime)

    assertEquals("+30m", offset)
  }

  @Test
  fun formatOffset_showPositiveMinuteDay() {
    val currentTime = ZonedDateTime.now().withHour(23).withMinute(45)

    val offset =
      currentTime
        // + 30m = 00:15 tomorrow
        .plusSeconds(1800)
        .formatOffset(context, currentTime)

    assertEquals("+30m, tomorrow", offset)
  }

  @Test
  fun formatOffset_showNegativeHour() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)

    val offset =
      currentTime
        // - 2h = 10:00
        .minusSeconds(7200)
        .formatOffset(context, currentTime)

    assertEquals("-2h", offset)
  }

  @Test
  fun formatOffset_showNegativeHourMinute() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)

    val offset =
      currentTime
        // - 2h 30m = 09:30 tomorrow
        .minusSeconds(9000)
        .formatOffset(context, currentTime)

    assertEquals("-2h 30m", offset)
  }

  @Test
  fun formatOffset_showNegativeHourMinuteDay() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)

    val offset =
      currentTime
        // - 14h = 22:00 yesterday
        .minusSeconds(50400)
        .formatOffset(context, currentTime)

    assertEquals("-14h, yesterday", offset)
  }

  @Test
  fun formatOffset_showNegativeMinute() {
    val currentTime = ZonedDateTime.now().withHour(12).withMinute(0)
    val offset =
      currentTime
        // - 30m = 11:30
        .minusSeconds(1800)
        .formatOffset(context, currentTime)

    assertEquals("-30m", offset)
  }

  @Test
  fun formatOffset_showNegativeMinuteDay() {
    val currentTime = ZonedDateTime.now().withHour(0).withMinute(15)
    val offset =
      currentTime
        // - 30m = 23:45 yesterday
        .minusSeconds(1800)
        .formatOffset(context, currentTime)

    assertEquals("-30m, yesterday", offset)
  }
}

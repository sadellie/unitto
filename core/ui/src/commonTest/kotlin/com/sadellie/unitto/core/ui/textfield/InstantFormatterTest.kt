/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.core.ui.textfield

import androidx.compose.ui.text.intl.Locale
import com.sadellie.unitto.core.ui.datetime.PlatformDateFormatSettings
import com.sadellie.unitto.core.ui.datetime.formatDateWeekDayMonthYear
import com.sadellie.unitto.core.ui.datetime.formatTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant
import kotlinx.datetime.TimeZone

class InstantFormatterTest {
  @Test
  fun formatTime_test() {
    val platformDateFormatSettings24 =
      PlatformDateFormatSettings(
        is24Hour = true,
        timeZone = TimeZone.of("UTC+1"),
        locale = Locale("en-US"),
      )
    val platformDateFormatSettings12 = platformDateFormatSettings24.copy(is24Hour = false)
    val instant = Instant.parse("2020-08-30T18:43:00Z")
    assertEquals("19:43", instant.formatTime(platformDateFormatSettings24))
    assertEquals("07:43 PM", instant.formatTime(platformDateFormatSettings12))
  }

  @Test
  fun formatDateWeekDayMonthYear_test() {
    val platformDateFormatSettings =
      PlatformDateFormatSettings(
        is24Hour = true,
        timeZone = TimeZone.of("UTC+1"),
        locale = Locale("en-US"),
      )
    val instant = Instant.parse("2020-08-30T18:43:00Z")
    assertEquals(
      "Sun, Aug 30, 2020",
      instant.formatDateWeekDayMonthYear(platformDateFormatSettings),
    )
  }
}

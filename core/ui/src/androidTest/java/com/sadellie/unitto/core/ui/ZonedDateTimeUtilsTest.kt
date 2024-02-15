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

package com.sadellie.unitto.core.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.sadellie.unitto.core.ui.datetime.formatOffset
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime

class ZonedDateTimeUtilsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun noDifference() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val formatted = currentTime.formatOffset(currentTime)

        assertEquals(null, formatted)
    }

    @Test
    fun showPositiveHour() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .plusSeconds(7200) // + 2h = 14:00
            .formatOffset(currentTime)

        assertEquals("+2h", offset)
    }

    @Test
    fun showPositiveHourMinute() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .plusSeconds(9000) // + 2h 30m = 14:30
            .formatOffset(currentTime)

        assertEquals("+2h 30m", offset)
    }

    @Test
    fun showPositiveHourMinuteDay() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .plusSeconds(50400) // + 14h = 02:00 tomorrow
            .formatOffset(currentTime)

        assertEquals("+14h, tomorrow", offset)
    }

    @Test
    fun showPositiveMinute() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .plusSeconds(1800) // + 30m = 12:30
            .formatOffset(currentTime)

        assertEquals("+30m", offset)
    }

    @Test
    fun showPositiveMinuteDay() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(23)
            .withMinute(45)

        val offset = currentTime
            .plusSeconds(1800) // + 30m = 00:15 tomorrow
            .formatOffset(currentTime)

        assertEquals("+30m, tomorrow", offset)
    }

    @Test
    fun showNegativeHour() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .minusSeconds(7200) // - 2h = 10:00
            .formatOffset(currentTime)

        assertEquals("-2h", offset)
    }

    @Test
    fun showNegativeHourMinute() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .minusSeconds(9000) // - 2h 30m = 09:30 tomorrow
            .formatOffset(currentTime)

        assertEquals("-2h 30m", offset)
    }

    @Test
    fun showNegativeHourMinuteDay() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .minusSeconds(50400) // - 14h = 22:00 yesterday
            .formatOffset(currentTime)

        assertEquals("-14h, yesterday", offset)
    }

    @Test
    fun showNegativeMinute() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .minusSeconds(1800) // - 30m = 11:30
            .formatOffset(currentTime)

        assertEquals("-30m", offset)
    }

    @Test
    fun showNegativeMinuteDay() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(0)
            .withMinute(15)

        val offset = currentTime
            .minusSeconds(1800) // - 30m = 23:45 yesterday
            .formatOffset(currentTime)

        assertEquals("-30m, yesterday", offset)
    }
}

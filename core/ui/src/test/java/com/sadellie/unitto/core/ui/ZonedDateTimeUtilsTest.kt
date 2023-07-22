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

package com.sadellie.unitto.core.ui

import androidx.compose.ui.test.junit4.createComposeRule
import com.sadellie.unitto.core.ui.datetime.formatOffset
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.robolectric.RobolectricTestRunner
import java.time.ZonedDateTime

@RunWith(RobolectricTestRunner::class)
class ZonedDateTimeUtilsTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `no difference`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val formatted = currentTime.formatOffset(currentTime)

        assertEquals(null, formatted)
    }

    @Test
    fun `show positive hour`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .plusSeconds(7200) // + 2h = 14:00
            .formatOffset(currentTime)

        assertEquals("+2h", offset)
    }

    @Test
    fun `show positive hour minute`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .plusSeconds(9000) // + 2h 30m = 14:30
            .formatOffset(currentTime)

        assertEquals("+2h 30m", offset)
    }

    @Test
    fun `show positive hour minute day`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .plusSeconds(50400) // + 14h = 02:00 tomorrow
            .formatOffset(currentTime)

        assertEquals("+14h, tomorrow", offset)
    }

    @Test
    fun `show positive minute`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .plusSeconds(1800) // + 30m = 12:30
            .formatOffset(currentTime)

        assertEquals("+30m", offset)
    }

    @Test
    fun `show positive minute day`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(23)
            .withMinute(45)

        val offset = currentTime
            .plusSeconds(1800) // + 30m = 00:15 tomorrow
            .formatOffset(currentTime)

        assertEquals("+30m, tomorrow", offset)
    }

    @Test
    fun `show negative hour`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .minusSeconds(7200) // - 2h = 10:00
            .formatOffset(currentTime)

        assertEquals("-2h", offset)
    }

    @Test
    fun `show negative hour minute`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .minusSeconds(9000) // - 2h 30m = 09:30 tomorrow
            .formatOffset(currentTime)

        assertEquals("-2h 30m", offset)
    }

    @Test
    fun `show negative hour minute day`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .minusSeconds(50400) // - 14h = 22:00 yesterday
            .formatOffset(currentTime)

        assertEquals("-14h, yesterday", offset)
    }

    @Test
    fun `show negative minute`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(12)
            .withMinute(0)

        val offset = currentTime
            .minusSeconds(1800) // - 30m = 11:30
            .formatOffset(currentTime)

        assertEquals("-30m", offset)
    }

    @Test
    fun `show negative minute day`() = composeTestRule.setContent {
        val currentTime = ZonedDateTime.now()
            .withHour(0)
            .withMinute(15)

        val offset = currentTime
            .minusSeconds(1800) // - 30m = 23:45 yesterday
            .formatOffset(currentTime)

        assertEquals("-30m, yesterday", offset)
    }
}

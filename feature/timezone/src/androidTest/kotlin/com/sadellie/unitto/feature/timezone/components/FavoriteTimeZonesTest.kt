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

package com.sadellie.unitto.feature.timezone.components

import android.icu.text.LocaleDisplayNames
import android.icu.text.TimeZoneNames
import android.icu.util.TimeZone
import android.icu.util.ULocale
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FavoriteTimeZonesTest {
  @get:Rule val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun convertTime(): Unit =
    with(composeTestRule) {
      setContent {
        val locale = ULocale.getDefault()
        val timeZoneNames = remember(locale) { TimeZoneNames.getInstance(locale) }
        val localeDisplayNames = remember(locale) { LocaleDisplayNames.getInstance(locale) }

        FavoriteTimeZoneItem(
          modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer),
          item =
            FavoriteZone(
              timeZone = TimeZone.getTimeZone("Africa/Addis_Ababa"),
              position = -1,
              label = "label text",
            ),
          fromTime =
            ZonedDateTime.parse(
              "2023-05-01T14:00+03:00[Africa/Addis_Ababa]",
              DateTimeFormatter.ISO_ZONED_DATE_TIME,
            ),
          expanded = true,
          onClick = {},
          onDelete = {},
          onPrimaryClick = {},
          onLabelClick = {},
          isDragging = false,
          timeZoneNames = timeZoneNames,
          localeDisplayNames = localeDisplayNames,
        )
      }

      onNodeWithText("11:00").assertExists()
    }
}

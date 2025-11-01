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

package com.sadellie.unitto.core.common

import android.icu.text.LocaleDisplayNames
import android.icu.text.TimeZoneNames
import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.util.Date

@RequiresApi(Build.VERSION_CODES.N)
fun TimeZone.offset(currentTime: ZonedDateTime): ZonedDateTime {
  val offsetSeconds = currentTime.offset.totalSeconds.toLong()
  val currentTimeWithoutOffset = currentTime.minusSeconds(offsetSeconds)
  val currentDate = Date.from(currentTime.toInstant())
  val isInDaylightTime = inDaylightTime(currentDate)
  // day light saving offset
  val dstOffsetSeconds: Long = if (isInDaylightTime) (dstSavings / MS_IN_S) else 0L

  return currentTimeWithoutOffset.plusSeconds(this.rawOffset / MS_IN_S + dstOffsetSeconds)
}

@RequiresApi(Build.VERSION_CODES.N)
fun TimeZone.regionName(
  timeZoneNames: TimeZoneNames,
  localeDisplayNames: LocaleDisplayNames,
): String {
  val location = timeZoneNames.getExemplarLocationName(this.id) ?: return fallbackRegionName()
  val region =
    localeDisplayNames.regionDisplayName(TimeZone.getRegion(id)) ?: return fallbackRegionName()

  return "$location, $region"
}

@RequiresApi(Build.VERSION_CODES.N)
fun TimeZone.displayName(locale: ULocale): String {
  return this.getDisplayName(locale) ?: id
}

@RequiresApi(Build.VERSION_CODES.N)
private fun TimeZone.fallbackRegionName(): String =
  id.replace("_", " ").split("/").reversed().joinToString()

// How many milliseconds are in 1 second
private const val MS_IN_S = 1_000L

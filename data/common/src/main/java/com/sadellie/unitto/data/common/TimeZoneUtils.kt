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

package com.sadellie.unitto.data.common

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
    val dstOffset: Long = if (inDaylightTime(Date.from(currentTime.toInstant()))) (dstSavings / 1000L) else 0L

    return currentTimeWithoutOffset.plusSeconds(this.rawOffset / 1000L).plusSeconds(dstOffset)
}

@RequiresApi(Build.VERSION_CODES.N)
fun TimeZone.regionName(
    timeZoneNames: TimeZoneNames,
    localeDisplayNames: LocaleDisplayNames,
): String {
    val location = timeZoneNames.getExemplarLocationName(this.id) ?: return fallbackRegion
    val region = localeDisplayNames.regionDisplayName(TimeZone.getRegion(id)) ?: return fallbackRegion

    return "$location, $region"
}

@RequiresApi(Build.VERSION_CODES.N)
fun TimeZone.displayName(
    locale: ULocale,
): String {
    return this.getDisplayName(locale) ?: id
}

private val TimeZone.fallbackRegion: String
    @RequiresApi(Build.VERSION_CODES.N)
    get() = id
        .replace("_", " ")
        .split("/")
        .reversed()
        .joinToString()

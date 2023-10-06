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

package com.sadellie.unitto.data.common

import android.icu.util.TimeZone
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.N)
fun TimeZone.offset(currentTime: ZonedDateTime): ZonedDateTime {
    val offsetSeconds = currentTime.offset.totalSeconds.toLong()
    val currentTimeWithoutOffset = currentTime.minusSeconds(offsetSeconds)

    return currentTimeWithoutOffset.plusSeconds(this.rawOffset / 1000L)
}

val TimeZone.region: String
    @RequiresApi(Build.VERSION_CODES.N)
    get() = id
        .replace("_", " ")
        .split("/")
        .reversed()
        .joinToString()

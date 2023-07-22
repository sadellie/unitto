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

package com.sadellie.unitto.core.ui.datetime

import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.core.base.R
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

@Composable
fun ZonedDateTime.formatLocal(): String {
    return if (DateFormat.is24HourFormat(LocalContext.current)) format24()
    else format12()
}

/**
 * Formats [ZonedDateTime] into string that looks like
 *
 * 23:58
 *
 * @return Formatted string.
 */
fun ZonedDateTime.format24(): String = this.format(time24Formatter)

/**
 * Formats [ZonedDateTime] into string that looks like
 *
 * 11:58 am
 *
 * @return Formatted string.
 */
fun ZonedDateTime.format12(): String = this.format(time12Formatter)

/**
 * Formats [ZonedDateTime] into string that looks like
 *
 * 21 Jul 2023
 *
 * @return Formatted string.
 */
fun ZonedDateTime.formatDayMonthYear(): String = this.format(dayMonthYear)

fun ZonedDateTime.formatTimeZoneOffset(): String = this.format(zoneFormatPattern)

/**
 * Format offset string. Examples:
 *
 * 0
 *
 * +8
 *
 * +8, tomorrow
 *
 * -8, yesterday
 *
 * @receiver [ZonedDateTime] Time with offset.
 * @param currentTime Time without offset.
 * @return Formatted string.
 */
@Composable
fun ZonedDateTime.formatOffset(
    currentTime: ZonedDateTime
): String? {

    val offsetFixed = ChronoUnit.SECONDS.between(currentTime, this)

    if (offsetFixed == 0L) return null

    var resultBuffer = ""
    val absoluteOffset = offsetFixed.absoluteValue

    // Add a positive/negative prefix symbol
    when {
        offsetFixed > 0 -> resultBuffer += "+"
        offsetFixed < 0 -> resultBuffer += "-"
    }

    // Formatted hours and minutes
    val hour = absoluteOffset / 3600
    val minute = absoluteOffset % 3600 / 60

    if (hour != 0L) {
        resultBuffer += "${hour}${stringResource(R.string.hour_short)}"
    }

    // TODO Very ugly
    if (minute != 0L) {
        if (hour != 0L) resultBuffer += " "
        resultBuffer += "${minute}${stringResource(R.string.minute_short)}"
    }

    // Day after time string
    val diff = this.dayOfYear - currentTime.dayOfYear
    when {
        diff > 0 -> resultBuffer += ", ${stringResource(R.string.tomorrow).lowercase()}"
        diff < 0 -> resultBuffer += ", ${stringResource(R.string.yesterday).lowercase()}"
    }

    return resultBuffer
}

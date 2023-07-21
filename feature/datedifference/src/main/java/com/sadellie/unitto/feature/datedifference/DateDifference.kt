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

package com.sadellie.unitto.feature.datedifference

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

internal sealed class DateDifference(
    open val years: Long = 0,
    open val months: Long = 0,
    open val days: Long = 0,
    open val hours: Long = 0,
    open val minutes: Long = 0,
) {
    data class Default(
        override val years: Long = 0,
        override val months: Long = 0,
        override val days: Long = 0,
        override val hours: Long = 0,
        override val minutes: Long = 0,
    ) : DateDifference(
        years = years,
        months = months,
        days = days,
        hours = hours,
        minutes = minutes,
    )

    object Zero : DateDifference()
}

// https://stackoverflow.com/a/25760725
internal infix operator fun LocalDateTime.minus(localDateTime: LocalDateTime): DateDifference {
    if (this == localDateTime) return DateDifference.Zero

    var fromDateTime: LocalDateTime = this
    var toDateTime: LocalDateTime = localDateTime

    // Swap to avoid negative
    if (this > localDateTime) {
        fromDateTime = localDateTime
        toDateTime = this
    }

    var tempDateTime = LocalDateTime.from(fromDateTime)

    val years = tempDateTime.until(toDateTime, ChronoUnit.YEARS)

    tempDateTime = tempDateTime.plusYears(years)
    val months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS)

    tempDateTime = tempDateTime.plusMonths(months)
    val days = tempDateTime.until(toDateTime, ChronoUnit.DAYS)

    tempDateTime = tempDateTime.plusDays(days)
    val hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS)

    tempDateTime = tempDateTime.plusHours(hours)
    val minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES)

    if (listOf(years, months, days, hours, minutes).sum() == 0L) return DateDifference.Zero

    return DateDifference.Default(
        years = years, months = months, days = days, hours = hours, minutes = minutes
    )
}

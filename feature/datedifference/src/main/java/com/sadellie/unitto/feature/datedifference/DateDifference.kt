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

import java.time.Duration
import java.time.LocalDateTime

internal sealed class DateDifference(
    open val months: Long = 0,
    open val days: Long = 0,
    open val hours: Long = 0,
    open val minutes: Long = 0,
) {
    data class Default(
        override val months: Long = 0,
        override val days: Long = 0,
        override val hours: Long = 0,
        override val minutes: Long = 0,
    ) : DateDifference(
        months = months,
        days = days,
        hours = hours,
        minutes = minutes,
    )

    object Zero : DateDifference()
}

internal infix operator fun LocalDateTime.minus(localDateTime: LocalDateTime): DateDifference {
    val duration = Duration.between(this, localDateTime).abs()

    if (duration.isZero) return DateDifference.Zero

    val durationDays = duration.toDays()
    val months = durationDays / 30
    val days = durationDays % 30
    val hours = duration.toHoursPart().toLong()
    val minutes = duration.toMinutesPart().toLong()

    if (listOf(months, days, hours, minutes).all { it == 0L }) return DateDifference.Zero

    return DateDifference.Default(
        months = months,
        days = days,
        hours = hours,
        minutes = minutes
    )
}

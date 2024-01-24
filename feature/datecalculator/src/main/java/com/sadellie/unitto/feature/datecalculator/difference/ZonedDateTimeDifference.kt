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

package com.sadellie.unitto.feature.datecalculator.difference

import com.sadellie.unitto.core.base.MAX_PRECISION
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

internal sealed class ZonedDateTimeDifference {
    data class Default(
        val years: Long,
        val months: Long,
        val days: Long,
        val hours: Long,
        val minutes: Long,
        val sumYears: BigDecimal,
        val sumMonths: BigDecimal,
        val sumDays: BigDecimal,
        val sumHours: BigDecimal,
        val sumMinutes: BigDecimal,
    ) : ZonedDateTimeDifference()

    data object Zero : ZonedDateTimeDifference()
}

/**
 * Same as other [ZonedDateTime.minus] but `MAX_PRECISION` passed as scale.
 *
 * @receiver First [ZonedDateTime].
 * @param zonedDateTime Second [ZonedDateTime].
 * @return [ZonedDateTimeDifference.Default] (_always positive_) or [ZonedDateTimeDifference.Zero]
 */
internal infix operator fun ZonedDateTime.minus(
    zonedDateTime: ZonedDateTime
): ZonedDateTimeDifference = this.minus(zonedDateTime = zonedDateTime, scale = MAX_PRECISION)

/**
 * Calculate difference between [this] and [zonedDateTime]. Return absolute value, order of operands
 * doesn't matter.
 *
 * @receiver First [ZonedDateTime].
 * @param zonedDateTime Second [ZonedDateTime].
 * @param scale Scale that will be used to calculate [ZonedDateTimeDifference.Default.sumDays] and
 * others summed values.
 * @return [ZonedDateTimeDifference.Default] (_always positive_) or [ZonedDateTimeDifference.Zero]
 */
internal fun ZonedDateTime.minus(
    zonedDateTime: ZonedDateTime,
    scale: Int
): ZonedDateTimeDifference {
    // https://stackoverflow.com/a/25760725

    if (this == zonedDateTime) return ZonedDateTimeDifference.Zero

    var fromDateTime: ZonedDateTime = this
    var toDateTime: ZonedDateTime = zonedDateTime
    val epSeconds: BigDecimal = (this.toEpochSecond() - zonedDateTime.toEpochSecond()).toBigDecimal().abs()

    // Swap to avoid negative
    if (this > zonedDateTime) {
        fromDateTime = zonedDateTime
        toDateTime = this
    }

    var tempDateTime = ZonedDateTime.from(fromDateTime)

    val years = tempDateTime.until(toDateTime, ChronoUnit.YEARS)

    tempDateTime = tempDateTime.plusYears(years)
    val months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS)

    tempDateTime = tempDateTime.plusMonths(months)
    val days = tempDateTime.until(toDateTime, ChronoUnit.DAYS)

    tempDateTime = tempDateTime.plusDays(days)
    val hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS)

    tempDateTime = tempDateTime.plusHours(hours)
    val minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES)

    if (listOf(years, months, days, hours, minutes).sum() == 0L) return ZonedDateTimeDifference.Zero

    return ZonedDateTimeDifference.Default(
        years = years,
        months = months,
        days = days,
        hours = hours,
        minutes = minutes,
        sumYears = epSeconds.divide(yearInSeconds, scale, RoundingMode.HALF_EVEN),
        sumMonths = epSeconds.divide(monthsInSeconds, scale, RoundingMode.HALF_EVEN),
        sumDays = epSeconds.divide(dayInSeconds, scale, RoundingMode.HALF_EVEN),
        sumHours = epSeconds.divide(hourInSeconds, scale, RoundingMode.HALF_EVEN),
        sumMinutes = epSeconds.divide(minuteInSeconds, scale, RoundingMode.HALF_EVEN),
    )
}

private val yearInSeconds by lazy { BigDecimal("31104000") }
private val monthsInSeconds by lazy { BigDecimal("2592000") }
private val dayInSeconds by lazy { BigDecimal("86400") }
private val hourInSeconds by lazy { BigDecimal("3600") }
private val minuteInSeconds by lazy { BigDecimal("60") }

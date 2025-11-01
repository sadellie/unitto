/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.KRoundingMode
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

internal sealed class ZonedDateTimeDifference {
  // TODO long to KBigDecimal
  data class Default(
    val years: Long,
    val months: Long,
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val sumYears: KBigDecimal,
    val sumMonths: KBigDecimal,
    val sumDays: KBigDecimal,
    val sumHours: KBigDecimal,
    val sumMinutes: KBigDecimal,
  ) : ZonedDateTimeDifference()

  data object Zero : ZonedDateTimeDifference()
}

/**
 * Calculate difference between [this] and [zonedDateTime]. Return absolute value, order of operands
 * doesn't matter.
 *
 * @param zonedDateTime Second [ZonedDateTime].
 * @param scale Scale that will be used to calculate [ZonedDateTimeDifference.Default.sumDays] and
 *   others summed values.
 * @return [ZonedDateTimeDifference.Default] (_always positive_) or [ZonedDateTimeDifference.Zero]
 * @receiver First [ZonedDateTime].
 */
internal fun ZonedDateTime.minus(
  zonedDateTime: ZonedDateTime,
  scale: Int,
): ZonedDateTimeDifference {
  // https://stackoverflow.com/a/25760725

  if (this == zonedDateTime) return ZonedDateTimeDifference.Zero

  var fromDateTime: ZonedDateTime = this
  var toDateTime: ZonedDateTime = zonedDateTime
  val epSeconds: KBigDecimal =
    KBigDecimal.valueOf(this.toEpochSecond() - zonedDateTime.toEpochSecond()).abs()

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
    sumYears = epSeconds.divide(secondsInYear, scale, KRoundingMode.HALF_EVEN),
    sumMonths = epSeconds.divide(secondsInMonth, scale, KRoundingMode.HALF_EVEN),
    sumDays = epSeconds.divide(secondsInDay, scale, KRoundingMode.HALF_EVEN),
    sumHours = epSeconds.divide(secondsInHour, scale, KRoundingMode.HALF_EVEN),
    sumMinutes = epSeconds.divide(secondsInMinute, scale, KRoundingMode.HALF_EVEN),
  )
}

private val secondsInYear by lazy { KBigDecimal("31536000") }
private val secondsInMonth by lazy { KBigDecimal("2628000") }
private val secondsInDay by lazy { KBigDecimal("86400") }
private val secondsInHour by lazy { KBigDecimal("3600") }
private val secondsInMinute by lazy { KBigDecimal("60") }

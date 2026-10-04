/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus

internal sealed interface InstantDifference {
  data class Default(
    val years: Long,
    val months: Long,
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val sumYears: KBigDecimal,
    val sumMonths: KBigDecimal,
    val sumWeeks: KBigDecimal,
    val sumDays: KBigDecimal,
    val sumHours: KBigDecimal,
    val sumMinutes: KBigDecimal,
  ) : InstantDifference

  data object Zero : InstantDifference
}

internal fun Instant.difference(
  otherInstant: Instant,
  scale: Int,
  timeZone: TimeZone = TimeZone.currentSystemDefault(),
): InstantDifference {
  if (this == otherInstant) return InstantDifference.Zero

  var fromDateTime = this
  var toDateTime = otherInstant
  if (this > otherInstant) {
    fromDateTime = otherInstant
    toDateTime = this
  }
  val period = toDateTime.minus(fromDateTime, timeZone)

  if (period.years + period.months + period.days + period.hours + period.minutes == 0) {
    return InstantDifference.Zero
  }

  val epSeconds = KBigDecimal.valueOf(this.epochSeconds - otherInstant.epochSeconds).abs()
  return InstantDifference.Default(
    years = period.years.toLong(),
    months = period.months.toLong(),
    days = period.days.toLong(),
    hours = period.hours.toLong(),
    minutes = period.minutes.toLong(),
    sumYears = epSeconds.divide(secondsInYear, scale, KRoundingMode.HALF_EVEN),
    sumMonths = epSeconds.divide(secondsInMonth, scale, KRoundingMode.HALF_EVEN),
    sumWeeks = epSeconds.divide(secondsInWeek, scale, KRoundingMode.HALF_EVEN),
    sumDays = epSeconds.divide(secondsInDay, scale, KRoundingMode.HALF_EVEN),
    sumHours = epSeconds.divide(secondsInHour, scale, KRoundingMode.HALF_EVEN),
    sumMinutes = epSeconds.divide(secondsInMinute, scale, KRoundingMode.HALF_EVEN),
  )
}

private val secondsInYear by lazy { KBigDecimal("31536000") }
private val secondsInMonth by lazy { KBigDecimal("2628000") }
private val secondsInWeek by lazy { KBigDecimal("604800") }
private val secondsInDay by lazy { KBigDecimal("86400") }
private val secondsInHour by lazy { KBigDecimal("3600") }
private val secondsInMinute by lazy { KBigDecimal("60") }

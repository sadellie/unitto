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

package com.sadellie.unitto.core.ui.datetime

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.absoluteValue

/**
 * Formats date time into something like:
 *
 * 23:59 or 11:59 AM
 *
 * Depends on system preferences
 *
 * @return Formatted string
 * @see UnittoDateTimeFormatter.time24
 * @see UnittoDateTimeFormatter.time12Full
 */
fun ZonedDateTime.formatTime(locale: Locale, is24Hour: Boolean): String =
  if (is24Hour) {
    format(UnittoDateTimeFormatter.time24(locale))
  } else {
    format(UnittoDateTimeFormatter.time12Full(locale))
  }

/**
 * Formats date time into something like:
 *
 * 23:59 or 11:59
 *
 * Depends on system preferences
 *
 * @see UnittoDateTimeFormatter.time24
 * @see UnittoDateTimeFormatter.time12Short
 */
fun ZonedDateTime.formatTimeShort(locale: Locale, is24Hour: Boolean): String =
  if (is24Hour) {
    format(UnittoDateTimeFormatter.time24(locale))
  } else {
    format(UnittoDateTimeFormatter.time12Short(locale))
  }

/**
 * Formats date time into something like:
 *
 * 23 or 11
 *
 * Depends on system preferences
 *
 * @return Formatted string
 * @see UnittoDateTimeFormatter.time24Hours
 * @see UnittoDateTimeFormatter.time12Hours
 */
fun ZonedDateTime.formatTimeHours(locale: Locale, is24Hour: Boolean): String =
  if (is24Hour) {
    format(UnittoDateTimeFormatter.time24Hours(locale))
  } else {
    format(UnittoDateTimeFormatter.time12Hours(locale))
  }

/** @see UnittoDateTimeFormatter.timeMinutes */
fun ZonedDateTime.formatTimeMinutes(locale: Locale): String =
  format(UnittoDateTimeFormatter.timeMinutes(locale))

/** @see UnittoDateTimeFormatter.time12AmPm */
fun ZonedDateTime.formatTimeAmPm(locale: Locale): String =
  format(UnittoDateTimeFormatter.time12AmPm(locale))

/** @see UnittoDateTimeFormatter.dateWeekDayMonthYear */
fun ZonedDateTime.formatDateWeekDayMonthYear(locale: Locale): String =
  format(UnittoDateTimeFormatter.dateWeekDayMonthYear(locale))

/** @see UnittoDateTimeFormatter.zone */
fun ZonedDateTime.formatZone(locale: Locale): String = format(UnittoDateTimeFormatter.zone(locale))

/** @see UnittoDateTimeFormatter.dateDayMonthYear */
fun ZonedDateTime.formatDateDayMonthYear(locale: Locale): String =
  format(UnittoDateTimeFormatter.dateDayMonthYear(locale))

/** @see UnittoDateTimeFormatter.dateWeekDayMonthYear */
fun LocalDate.formatDateWeekDayMonthYear(locale: Locale): String =
  format(UnittoDateTimeFormatter.dateWeekDayMonthYear(locale))

fun ZonedDateTime.formatOffset(
  currentTime: ZonedDateTime,
  hourShort: String,
  minuteLabel: String,
  tomorrowLabel: String,
  yesterdayLabel: String,
): String? {
  val offsetFixed = ChronoUnit.SECONDS.between(currentTime, this)

  var resultBuffer = ""

  // Add a positive/negative prefix symbol
  resultBuffer +=
    when {
      offsetFixed > 0 -> "+"
      offsetFixed < 0 -> "-"
      else -> return null
    }

  val absoluteOffset = offsetFixed.absoluteValue

  // Formatted hours and minutes
  val hour = absoluteOffset / 3600
  val minute = absoluteOffset % 3600 / 60

  if (hour != 0L) {
    resultBuffer += "${hour}${hourShort}"
  }

  if (minute != 0L) {
    if (hour != 0L) resultBuffer += " "
    resultBuffer += "${minute}${minuteLabel}"
  }

  // Day after time string
  val diff = this.dayOfYear - currentTime.dayOfYear
  when {
    diff > 0 -> resultBuffer += ", ${tomorrowLabel.lowercase()}"
    diff < 0 -> resultBuffer += ", ${yesterdayLabel.lowercase()}"
  }

  return resultBuffer
}

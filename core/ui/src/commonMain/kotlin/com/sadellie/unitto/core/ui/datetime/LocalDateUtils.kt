/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025-2026 Elshan Agaev
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

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.intl.Locale
import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

val LocalPlatformDateFormatSettings =
  staticCompositionLocalOf<PlatformDateFormatSettings> { error("No PlatformDateFormatSettings") }

data class PlatformDateFormatSettings(
  val is24Hour: Boolean,
  val timeZone: TimeZone,
  val locale: Locale,
)

/** Thu, Dec 31, 2077 */
fun Instant.formatDateWeekDayMonthYear(
  platformDateFormatSettings: PlatformDateFormatSettings
): String {
  val weekNames = dayOfWeekNamesAbbreviated(platformDateFormatSettings.locale)
  val monthNames = monthNamesAbbreviated(platformDateFormatSettings.locale)

  val formatter = LocalDateTime.Format {
    this.dayOfWeek(weekNames)
    this.char(',')
    this.char(' ')
    this.monthName(monthNames)
    this.char(' ')
    this.day()
    this.char(',')
    this.char(' ')
    this.year()
  }
  return this.toLocalDateTime(platformDateFormatSettings.timeZone).format(formatter)
}

fun Instant.formatTime(platformDateFormatSettings: PlatformDateFormatSettings): String {
  val formatter =
    if (platformDateFormatSettings.is24Hour) formatTime24Formatter
    else formatTime12Formatter(amPm(platformDateFormatSettings.locale))

  return this.toLocalDateTime(platformDateFormatSettings.timeZone).format(formatter)
}

expect fun dayOfWeekNamesAbbreviated(locale: Locale): DayOfWeekNames

expect fun monthNamesAbbreviated(locale: Locale): MonthNames

expect fun amPm(locale: Locale): Pair<String, String>

private val formatTime24Formatter = LocalDateTime.Format {
  this.hour()
  this.char(':')
  this.minute()
}

private fun formatTime12Formatter(amPm: Pair<String, String>) = LocalDateTime.Format {
  this.amPmHour()
  this.char(':')
  this.minute()
  this.char(' ')
  this.amPmMarker(amPm.first, amPm.second)
}

internal const val TAG = "LocalDateUtils"

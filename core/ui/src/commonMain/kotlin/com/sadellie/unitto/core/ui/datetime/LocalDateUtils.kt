/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

import androidx.compose.ui.text.intl.PlatformLocale
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

fun LocalDate.formatDateWeekDayMonthYear(locale: PlatformLocale): String {
  // TODO catch exceptions for names
  val formatter =
    LocalDate.Format {
      this.dayOfWeek(commonDayOfWeekNames(locale))
      this.chars(", ")
      this.monthName(commonMonthNames(locale))
      this.char(' ')
      this.day()
      this.chars(", ")
      this.year()
    }

  return this.format(formatter)
}

expect fun commonDayOfWeekNames(locale: PlatformLocale): DayOfWeekNames

expect fun commonMonthNames(locale: PlatformLocale): MonthNames

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
import java.text.DateFormatSymbols
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames

actual fun dayOfWeekNamesAbbreviated(locale: PlatformLocale): DayOfWeekNames {
  val weekDaysFromSunday =
    DateFormatSymbols.getInstance(locale).shortWeekdays.filterNot(String::isEmpty)
  return DayOfWeekNames(
    monday = weekDaysFromSunday[1],
    tuesday = weekDaysFromSunday[2],
    wednesday = weekDaysFromSunday[3],
    thursday = weekDaysFromSunday[4],
    friday = weekDaysFromSunday[5],
    saturday = weekDaysFromSunday[6],
    sunday = weekDaysFromSunday[0],
  )
}

actual fun monthNamesAbbreviated(locale: PlatformLocale) =
  MonthNames(DateFormatSymbols.getInstance(locale).shortMonths.filterNot(String::isEmpty).toList())

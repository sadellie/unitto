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

package com.sadellie.unitto.core.ui.datetime

import java.time.format.DateTimeFormatter
import java.util.Locale

internal data object UnittoDateTimeFormatter {
  /** 23:59 */
  fun time24(locale: Locale): DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", locale)

  /** 11:59 AM */
  fun time12Full(locale: Locale): DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a", locale)

  /** 11:59 (no AM/PM) */
  fun time12Short(locale: Locale): DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm", locale)

  /** 23 */
  fun time24Hours(locale: Locale): DateTimeFormatter = DateTimeFormatter.ofPattern("HH", locale)

  /** 23 */
  fun time12Hours(locale: Locale): DateTimeFormatter = DateTimeFormatter.ofPattern("hh", locale)

  /** 59 */
  fun timeMinutes(locale: Locale): DateTimeFormatter = DateTimeFormatter.ofPattern("mm", locale)

  /** AM */
  fun time12AmPm(locale: Locale): DateTimeFormatter = DateTimeFormatter.ofPattern("a", locale)

  /** 31 Dec 2077 */
  fun dateDayMonthYear(locale: Locale): DateTimeFormatter =
    DateTimeFormatter.ofPattern("d MMM y", locale)

  /** Mon, 31 Dec, 2077 */
  fun dateWeekDayMonthYear(locale: Locale): DateTimeFormatter =
    DateTimeFormatter.ofPattern("EEE, MMM d, y", locale)

  /** GMT+3 */
  fun zone(locale: Locale): DateTimeFormatter = DateTimeFormatter.ofPattern("O", locale)
}

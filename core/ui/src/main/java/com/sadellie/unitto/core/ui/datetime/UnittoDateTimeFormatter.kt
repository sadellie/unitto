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

package com.sadellie.unitto.core.ui.datetime

import java.time.format.DateTimeFormatter

data object UnittoDateTimeFormatter {
    /**
     * 23:59
     */
    val time24Formatter: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("HH:mm") }

    /**
     * 11:59 AM
     */
    val time12FormatterFull: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("hh:mm a") }

    /**
     * 11:59
     */
    val time12Formatter1: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("hh:mm") }

    /**
     * 23
     */
    val time24OnlyHoursFormatter: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("HH") }

    /**
     * 23
     */
    val time12OnlyHoursFormatter: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("hh") }

    /**
     * 59
     */
    val timeOnlyMinutesFormatter: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("mm") }

    /**
     * 59
     */
    val timeOnlySecondsFormatter: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("ss") }

    /**
     * AM
     */
    val time12Formatter2: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("a") }

    /**
     * 31 Dec 2077
     */
    val dayMonthYear: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("d MMM y") }

    /**
     * Mon, 31 Dec, 2077
     */
    val weekDayMonthYear: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("EEE, MMM d, y") }

    /**
     * GMT+3
     */
    val zoneFormatPattern: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("O") }
}

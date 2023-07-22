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

// FIXME Duplicate from date difference
internal val time24Formatter by lazy { DateTimeFormatter.ofPattern("HH:mm") }
internal val time12Formatter by lazy { DateTimeFormatter.ofPattern("hh:mm a") }
internal val dayMonthYear by lazy { DateTimeFormatter.ofPattern("d MMM y") }
internal val zoneFormatPattern by lazy { DateTimeFormatter.ofPattern("O") }

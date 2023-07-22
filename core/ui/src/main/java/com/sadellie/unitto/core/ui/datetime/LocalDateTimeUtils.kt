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

import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDateTime

@Composable
fun LocalDateTime.formatLocal(): String {
    return if (DateFormat.is24HourFormat(LocalContext.current)) format24()
    else format12()
}

/**
 * Formats [LocalDateTime] into string that looks like
 *
 * 23:58
 *
 * @return Formatted string.
 */
fun LocalDateTime.format24(): String = this.format(time24Formatter)

/**
 * Formats [LocalDateTime] into string that looks like
 *
 * 11:58 am
 *
 * @return Formatted string.
 */
fun LocalDateTime.format12(): String = this.format(time12Formatter)

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

package com.sadellie.unitto.data.epoch

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object EpochDateConverter {
    private val pattern by lazy {
        DateTimeFormatter.ofPattern("HHmmssddMMyyyy")
    }

    fun convertDateToUnix(date: String): String {
        return try {
            // Here we add some zeros, so that input is 14 symbols long
            LocalDateTime
                .parse(date.padEnd(14, '0'), pattern)
                .toEpochSecond(ZoneOffset.UTC)
        } catch (e: DateTimeParseException) {
            0
        }.toString()
    }

    fun convertUnixToDate(unix: String): String {
        val unixLong = unix.toLong().takeIf { it <= 253402300559L }
            ?: throw IllegalArgumentException("Max unix is 253402300559")

        return LocalDateTime
            .ofEpochSecond(unixLong, 0, ZoneOffset.UTC)
            .format(pattern)
    }
}
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

package com.sadellie.unitto.data.model

import java.time.ZonedDateTime

data class UnittoTimeZone(
    val id: String,
    // For beta only, will change to StringRes later
    val nameRes: String,
    val position: Int = 0,
    val offsetSeconds: Long = 0,
    val code: String = "CODE",
) {
    fun offsetFrom(currentTime: ZonedDateTime): ZonedDateTime {
        val offsetSeconds = currentTime.offset.totalSeconds.toLong()
        val currentTimeWithoutOffset = currentTime.minusSeconds(offsetSeconds)

        return currentTimeWithoutOffset.plusSeconds(this.offsetSeconds)
    }
}

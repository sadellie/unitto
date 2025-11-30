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

package com.sadellie.unitto.feature.timezone

import android.icu.util.TimeZone
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import java.time.ZonedDateTime

internal sealed class TimeZoneUIState {
  data object Loading : TimeZoneUIState()

  data class Ready(
    val favorites: List<FavoriteZone>,
    val customUserTime: ZonedDateTime?,
    val userTimeZone: TimeZone,
    val selectedTimeZone: FavoriteZone?,
    val dialogState: TimeZoneDialogState,
  ) : TimeZoneUIState()
}

internal sealed class TimeZoneDialogState {
  data object Nothing : TimeZoneDialogState()

  data class UserTimePicker(val time: ZonedDateTime) : TimeZoneDialogState()

  data class FavoriteTimePicker(val timeZone: FavoriteZone, val time: ZonedDateTime) :
    TimeZoneDialogState()

  data class LabelEditPicker(val timeZone: FavoriteZone) : TimeZoneDialogState()
}

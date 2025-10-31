/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.timezone.TimeZonesRepository
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import java.time.ZonedDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
internal class TimeZoneViewModel(private val timezonesRepository: TimeZonesRepository) :
  ViewModel() {
  private val userTimeZone = MutableStateFlow(TimeZone.getDefault())
  private val customUserTime = MutableStateFlow<ZonedDateTime?>(null)
  private val selectedTimeZone = MutableStateFlow<FavoriteZone?>(null)
  private val dialogState = MutableStateFlow<TimeZoneDialogState>(TimeZoneDialogState.Nothing)

  val uiState =
    combine(
        customUserTime,
        userTimeZone,
        selectedTimeZone,
        dialogState,
        timezonesRepository.favoriteTimeZones,
      ) { customUserTime, userTimeZone, selectedTimeZone, dialogState, favoriteTimeZones ->
        return@combine TimeZoneUIState.Ready(
          favorites = favoriteTimeZones,
          customUserTime = customUserTime,
          userTimeZone = userTimeZone,
          selectedTimeZone = selectedTimeZone,
          dialogState = dialogState,
        )
      }
      .stateIn(viewModelScope, TimeZoneUIState.Loading)

  fun setCurrentTime() = customUserTime.update { null }

  fun setSelectedTime(time: ZonedDateTime) = customUserTime.update { time }

  fun setDialogState(state: TimeZoneDialogState) = dialogState.update { state }

  fun updatePosition(updatedList: List<FavoriteZone>, tz: FavoriteZone) {
    val originalList = updatedList.sortedBy { it.position }
    val targetIndex = updatedList.indexOfFirst { it.position == tz.position }
    val targetPosition = originalList[targetIndex].position

    if (tz.position == targetPosition) return
    viewModelScope.launch { timezonesRepository.updatePosition(tz, targetPosition) }
  }

  fun delete(timeZone: FavoriteZone) =
    viewModelScope.launch { timezonesRepository.removeFromFavorites(timeZone) }

  fun selectTimeZone(timeZone: FavoriteZone?) = selectedTimeZone.update { timeZone }

  fun updateLabel(timeZone: FavoriteZone, label: String) =
    viewModelScope.launch { timezonesRepository.updateLabel(timeZone = timeZone, label = label) }
}

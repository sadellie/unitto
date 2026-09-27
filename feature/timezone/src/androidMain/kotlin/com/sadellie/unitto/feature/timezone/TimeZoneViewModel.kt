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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.timezone.TimeZonesRepository
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import java.time.ZonedDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class TimeZoneViewModel(private val timezonesRepository: TimeZonesRepository) : ViewModel() {
  private val userTimeZone = MutableStateFlow(TimeZone.getDefault())
  private val customUserTime = MutableStateFlow<ZonedDateTime?>(null)
  private val selectedTimeZone = MutableStateFlow<FavoriteZone?>(null)
  private val dialogState = MutableStateFlow<TimeZoneDialogState>(TimeZoneDialogState.Nothing)

  internal val uiState =
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

  internal fun setCurrentTime() = customUserTime.update { null }

  internal fun setSelectedTime(time: ZonedDateTime) = customUserTime.update { time }

  internal fun setDialogState(state: TimeZoneDialogState) = dialogState.update { state }

  internal fun updatePosition(updatedList: List<FavoriteZone>, tz: FavoriteZone) {
    val originalList = updatedList.sortedBy { it.position }
    val targetIndex = updatedList.indexOfFirst { it.position == tz.position }
    val targetPosition = originalList[targetIndex].position

    if (tz.position == targetPosition) return
    viewModelScope.launch { timezonesRepository.updatePosition(tz, targetPosition) }
  }

  internal fun delete(timeZone: FavoriteZone) = viewModelScope.launch {
    timezonesRepository.removeFromFavorites(timeZone)
  }

  internal fun selectTimeZone(timeZone: FavoriteZone?) = selectedTimeZone.update { timeZone }

  internal fun updateLabel(timeZone: FavoriteZone, label: String) = viewModelScope.launch {
    timezonesRepository.updateLabel(timeZone = timeZone, label = label)
  }
}

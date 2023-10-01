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

package com.sadellie.unitto.feature.timezone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.model.UnittoTimeZone
import com.sadellie.unitto.data.timezone.TimeZonesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class TimeZoneViewModel @Inject constructor(
    private val timezonesRepository: TimeZonesRepository
): ViewModel() {

    private val _userTime = MutableStateFlow(ZonedDateTime.now())
    private val _updateTime = MutableStateFlow(true)

    val timeZoneUIState = combine(
        _userTime,
        _updateTime,
        timezonesRepository.favoriteTimeZones
    ) { userTime, updateTime, favorites ->
        return@combine TimeZoneUIState(
            list = favorites,
            userTime = userTime,
            updateTime = updateTime
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), TimeZoneUIState()
    )

    fun onDragEnd(from: String, to: String) = viewModelScope.launch {
        timezonesRepository.swapTimeZones(from, to)
    }

    fun onDelete(timeZone: UnittoTimeZone) = viewModelScope.launch {
        timezonesRepository.delete(timeZone)
    }

    fun setCustomTime(time: ZonedDateTime) = viewModelScope.launch(Dispatchers.Default) {
        _updateTime.update { false }
        _userTime.update { time }
    }

    fun resetTime() = viewModelScope.launch(Dispatchers.Default) {
        _updateTime.update { true }
    }

    fun setCurrentTime() = viewModelScope.launch(Dispatchers.Default) {
        _userTime.update { ZonedDateTime.now() }
    }
}

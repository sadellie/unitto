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

package com.sadellie.unitto.timezone

import androidx.compose.ui.text.input.TextFieldValue
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
class AddTimeZoneViewModel @Inject constructor(
    private val timezonesRepository: TimeZonesRepository,
) : ViewModel() {

    private val _userTime = MutableStateFlow<ZonedDateTime?>(ZonedDateTime.now())
    private val _query = MutableStateFlow(TextFieldValue())

    private val _filteredTimeZones = MutableStateFlow(emptyList<UnittoTimeZone>())
    val addTimeZoneUIState = combine(
        _query,
        _filteredTimeZones,
        _userTime,
    ) { query, filteredTimeZone, userTime ->
        return@combine AddTimeZoneUIState(
            query = query,
            list = filteredTimeZone,
            userTime = userTime,
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), AddTimeZoneUIState()
    )

    fun onQueryChange(query: TextFieldValue) {
        _query.update { query }
        filterTimeZones(query.text)
    }

    private fun filterTimeZones(query: String = "") = viewModelScope.launch {
        _filteredTimeZones.update {
            timezonesRepository.filterAllTimeZones(query)
        }
    }

    fun addToFavorites(timeZone: UnittoTimeZone) = viewModelScope.launch(Dispatchers.IO) {
        timezonesRepository.addToFavorites(timeZone)
    }

    fun setTime(time: ZonedDateTime) = viewModelScope.launch(Dispatchers.Default) {
            _userTime.update { time }
        }

    init {
        // TODO Maybe only when actually needed?
        filterTimeZones()
    }
}

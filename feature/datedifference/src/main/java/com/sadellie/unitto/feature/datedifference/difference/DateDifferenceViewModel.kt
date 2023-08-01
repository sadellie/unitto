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

package com.sadellie.unitto.feature.datedifference.difference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.feature.datedifference.ZonedDateTimeDifference
import com.sadellie.unitto.feature.datedifference.minus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
internal class DateDifferenceViewModel @Inject constructor() : ViewModel() {
    private val _start = MutableStateFlow(ZonedDateTime.now())
    private val _end = MutableStateFlow(ZonedDateTime.now())
    private val _result = MutableStateFlow<ZonedDateTimeDifference>(ZonedDateTimeDifference.Zero)

    val uiState = combine(_start, _end, _result) { start, end, result ->
        return@combine DifferenceUIState(start, end, result)
    }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L), DifferenceUIState()
        )

    fun setStartDate(dateTime: ZonedDateTime) = _start.update { dateTime }

    fun setEndDate(dateTime: ZonedDateTime) = _end.update { dateTime }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            merge(_start, _end).collectLatest {
                val difference = _start.value - _end.value
                _result.update { difference }
            }
        }
    }
}

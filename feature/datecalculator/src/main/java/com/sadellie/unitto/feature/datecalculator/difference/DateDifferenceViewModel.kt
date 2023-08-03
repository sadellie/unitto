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

package com.sadellie.unitto.feature.datecalculator.difference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
internal class DateDifferenceViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(DifferenceUIState())

    val uiState = _uiState
        .onEach { updateResult() }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L), DifferenceUIState()
        )

    fun setStartDate(newValue: ZonedDateTime) = _uiState.update { it.copy(start = newValue) }

    fun setEndDate(newValue: ZonedDateTime) = _uiState.update { it.copy(end = newValue) }

    private fun updateResult() = viewModelScope.launch(Dispatchers.Default) {
        _uiState.update { ui -> ui.copy(result = ui.start - ui.end) }
    }
}

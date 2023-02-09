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

package com.sadellie.unitto.feature.epoch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.epoch.EpochDateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class EpochUIState(
    val dateField: String = "",
    val unixField: String = "",
    val dateToUnix: Boolean = true,
    val selection: IntRange = 0..0
)

@HiltViewModel
class EpochViewModel @Inject constructor() : ViewModel() {

    private val _input: MutableStateFlow<String> = MutableStateFlow("")
    private val _fromDateToUnix: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val _selection: MutableStateFlow<IntRange> = MutableStateFlow(IntRange(0, 0))

    val uiState: StateFlow<EpochUIState> = combine(
        _input, _fromDateToUnix, _selection
    ) { input, fromDateToUnix, selection ->
        if (fromDateToUnix) {
            EpochUIState(
                dateField = input,
                unixField = EpochDateConverter.convertDateToUnix(input),
                dateToUnix = fromDateToUnix,
                selection = selection
            )
        } else {
            val date = try {
                EpochDateConverter.convertUnixToDate(input)
            } catch (e: IllegalArgumentException) {
                ""
            }

            EpochUIState(
                unixField = input,
                dateField = date,
                dateToUnix = fromDateToUnix,
                selection = selection
            )
        }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), EpochUIState()
    )

    fun addSymbol(symbol: String) {
        val selection = _selection.value
        val maxSymbols = if (_fromDateToUnix.value) 14 else 10
        if (_input.value.length >= maxSymbols) return

        _input.update { 
            if (it.isEmpty()) symbol else it.replaceRange(selection.first, selection.last, symbol)
        }
        _selection.update { it.first + 1..it.first + 1 }
    }

    fun deleteSymbol() {
        val selection = _selection.value
        val newSelectionStart = when (selection.last) {
            0 -> return
            selection.first -> _selection.value.first - 1
            else -> _selection.value.first
        }

        _selection.update { newSelectionStart..newSelectionStart }
        _input.update { it.removeRange(newSelectionStart, selection.last) }
    }

    fun clearSymbols() {
        _selection.update { 0..0 }
        _input.update { "" }
    }

    fun swap() {
        clearSymbols()
        _fromDateToUnix.update { !it }
    }

    fun onCursorChange(selectionRange: IntRange) {
        _selection.update { selectionRange }
    }
}

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
import java.lang.Integer.min
import javax.inject.Inject

data class EpochUIState(
    val dateField: String = "",
    val unixField: String = "",
    val dateToUnix: Boolean = true
)

@HiltViewModel
class EpochViewModel @Inject constructor() : ViewModel() {

    private val _input: MutableStateFlow<String> = MutableStateFlow("")
    private val _fromDateToUnix: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val uiState: StateFlow<EpochUIState> = combine(
        _input, _fromDateToUnix
    ) { input, fromDateToUnix ->
        if (fromDateToUnix) {
            EpochUIState(
                dateField = input,
                unixField = EpochDateConverter.convertDateToUnix(input),
                dateToUnix = fromDateToUnix
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
                dateToUnix = fromDateToUnix
            )
        }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), EpochUIState()
    )

    fun addSymbol(symbol: String, position: Int) {
        val maxSymbols = if (_fromDateToUnix.value) 14 else 10
        if (_input.value.length >= maxSymbols) return
        _input.update { it.replaceRange(position, position, symbol) }
    }

    fun deleteSymbol(position: Int) {
        _input.update {
            it.removeRange(position, min(position + 1, _input.value.length))
        }
    }

    fun clearSymbols() = _input.update { "" }

    fun swap() = _fromDateToUnix.update {
        clearSymbols()
        !it
    }
}

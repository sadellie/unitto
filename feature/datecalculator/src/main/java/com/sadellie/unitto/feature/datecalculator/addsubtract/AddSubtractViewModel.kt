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

package com.sadellie.unitto.feature.datecalculator.addsubtract

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.ui.common.textfield.AllFormatterSymbols
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
internal class AddSubtractViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddSubtractState())

    val uiState: StateFlow<AddSubtractState> = _uiState
        .combine(userPreferencesRepository.addSubtractPrefs) { uiState, userPrefs ->
            return@combine uiState.copy(
                formatterSymbols = AllFormatterSymbols.getById(userPrefs.separator),
                allowVibration = userPrefs.enableVibrations,
            )
        }
        .onEach { updateResult() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AddSubtractState())

    fun updateStart(newValue: ZonedDateTime) = _uiState.update { it.copy(start = newValue) }

    fun updateYears(value: TextFieldValue) = _uiState.update {
        it.copy(years = checkWithMax(value, 9_999L))
    }

    fun updateMonths(value: TextFieldValue) = _uiState.update {
        it.copy(months = checkWithMax(value, 9_999L))
    }

    fun updateDays(value: TextFieldValue) = _uiState.update {
        it.copy(days = checkWithMax(value, 99_999L))
    }

    fun updateHours(value: TextFieldValue) = _uiState.update {
        it.copy(hours = checkWithMax(value, 9_999_999L))
    }

    fun updateMinutes(value: TextFieldValue) = _uiState.update {
        it.copy(minutes = checkWithMax(value, 99_999_999L))
    }

    // BCE is not handled properly because who gives a shit...
    fun updateAddition(newValue: Boolean) = _uiState.update {
        it.copy(addition = newValue)
    }

    private fun updateResult() = viewModelScope.launch(Dispatchers.Default) {
        // Gets canceled, works with latest _uiState only
        _uiState.update { ui ->
            val newResult = if (ui.addition) {
                ui.start
                    .plusYears(ui.years.text.ifEmpty { "0" }.toLong())
                    .plusMonths(ui.months.text.ifEmpty { "0" }.toLong())
                    .plusDays(ui.days.text.ifEmpty { "0" }.toLong())
                    .plusHours(ui.hours.text.ifEmpty { "0" }.toLong())
                    .plusMinutes(ui.minutes.text.ifEmpty { "0" }.toLong())
            } else {
                ui.start
                    .minusYears(ui.years.text.ifEmpty { "0" }.toLong())
                    .minusMonths(ui.months.text.ifEmpty { "0" }.toLong())
                    .minusDays(ui.days.text.ifEmpty { "0" }.toLong())
                    .minusHours(ui.hours.text.ifEmpty { "0" }.toLong())
                    .minusMinutes(ui.minutes.text.ifEmpty { "0" }.toLong())
            }
            ui.copy(result = newResult)
        }
    }

    private fun checkWithMax(value: TextFieldValue, maxValue: Long): TextFieldValue {
        if (value.text.isEmpty()) return value
        if (value.text.toLong() <= maxValue) return value
        val maxValueText = maxValue.toString()
        return TextFieldValue(maxValueText, TextRange(maxValueText.length))
    }
}

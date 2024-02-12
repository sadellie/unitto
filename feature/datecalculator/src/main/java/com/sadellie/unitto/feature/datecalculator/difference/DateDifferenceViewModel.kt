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

package com.sadellie.unitto.feature.datecalculator.difference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import com.sadellie.unitto.feature.datecalculator.ZonedDateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
internal class DateDifferenceViewModel @Inject constructor(
    userPrefsRepository: UserPreferencesRepository,
) : ViewModel() {
    private val _start = MutableStateFlow(ZonedDateTimeUtils.nowWithMinutes())
    private val _end = MutableStateFlow(ZonedDateTimeUtils.nowWithMinutes())
    private val _result = MutableStateFlow<ZonedDateTimeDifference>(ZonedDateTimeDifference.Zero)

    val uiState: StateFlow<DifferenceUIState> = combine(
        userPrefsRepository.formattingPrefs,
        _start,
        _end,
        _result
    ) { prefs, start, end, result ->
            return@combine DifferenceUIState.Ready(
                start = start,
                end = end,
                result = result,
                precision = prefs.digitsPrecision,
                outputFormat = prefs.outputFormat,
                formatterSymbols = prefs.formatterSymbols
            )
        }
        .mapLatest { ui ->
            updateResult(
                start = ui.start,
                end = ui.end
            )

            ui
        }
        .stateIn(
            viewModelScope, DifferenceUIState.Loading
        )

    fun setStartDate(newValue: ZonedDateTime) = _start.update { newValue }

    fun setEndDate(newValue: ZonedDateTime) = _end.update { newValue }

    private fun updateResult(
        start: ZonedDateTime,
        end: ZonedDateTime
    ) = viewModelScope.launch(Dispatchers.Default) {
        _result.update {
            try {
                start - end
            } catch (e: Exception) {
                ZonedDateTimeDifference.Zero
            }
        }
    }
}

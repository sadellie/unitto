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

package com.sadellie.unitto.feature.datecalculator.addsubtract

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.combineBig
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.ui.textfield.observe
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddSubtractViewModel
@Inject
constructor(userPreferencesRepository: UserPreferencesRepository) : ViewModel() {
  private val _initialDateTime = ZonedDateTime.now()
  private var _calculateJob: Job? = null
  private val _result = MutableStateFlow(_initialDateTime)
  private val _start = MutableStateFlow(_initialDateTime)
  private val _addition = MutableStateFlow(true)
  private val _years = TextFieldState()
  private val _months = TextFieldState()
  private val _days = TextFieldState()
  private val _hours = TextFieldState()
  private val _minutes = TextFieldState()

  val uiState: StateFlow<AddSubtractUIState> =
    combine(_result, _start, _addition, userPreferencesRepository.addSubtractPrefs) {
        resultValue,
        startValue,
        additionValue,
        userPrefs ->
        return@combine AddSubtractUIState.Ready(
          addition = additionValue,
          start = startValue,
          result = resultValue,
          years = _years,
          months = _months,
          days = _days,
          hours = _hours,
          minutes = _minutes,
          formatterSymbols = userPrefs.formatterSymbols,
        )
      }
      .stateIn(viewModelScope, AddSubtractUIState.Loading)

  suspend fun observeInput() {
    val yearsFlow = _years.observe()
    val monthsFlow = _months.observe()
    val daysFlow = _days.observe()
    val hoursFlow = _hours.observe()
    val minutesFlow = _minutes.observe()

    combineBig(_addition, _start, yearsFlow, monthsFlow, daysFlow, hoursFlow, minutesFlow) {
        additionValue,
        startValue,
        yearsValue,
        monthsValue,
        daysValue,
        hoursValue,
        minutesValue ->
        calculate(
          additionValue,
          startValue,
          yearsValue.toString().ifEmpty { "0" }.toLong(),
          monthsValue.toString().ifEmpty { "0" }.toLong(),
          daysValue.toString().ifEmpty { "0" }.toLong(),
          hoursValue.toString().ifEmpty { "0" }.toLong(),
          minutesValue.toString().ifEmpty { "0" }.toLong(),
        )
      }
      .collectLatest {}
  }

  fun updateStart(newValue: ZonedDateTime) = _start.update { newValue }

  // BCE is not handled properly because who gives a shit...
  fun updateAddition(newValue: Boolean) = _addition.update { newValue }

  private fun calculate(
    addition: Boolean,
    start: ZonedDateTime,
    years: Long,
    months: Long,
    days: Long,
    hours: Long,
    minutes: Long,
  ) {
    _calculateJob?.cancel()
    _calculateJob =
      viewModelScope.launch(Dispatchers.Default) {
        val newResult =
          if (addition) {
            start
              .plusYears(years)
              .plusMonths(months)
              .plusDays(days)
              .plusHours(hours)
              .plusMinutes(minutes)
          } else {
            start
              .minusYears(years)
              .minusMonths(months)
              .minusDays(days)
              .minusHours(hours)
              .minusMinutes(minutes)
          }

        _result.update { newResult }
      }
  }
}

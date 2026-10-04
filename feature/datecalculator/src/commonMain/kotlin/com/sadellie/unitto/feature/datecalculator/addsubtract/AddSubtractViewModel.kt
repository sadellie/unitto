/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
import com.sadellie.unitto.core.datastore.FormatterPrefsRepository
import com.sadellie.unitto.core.ui.textfield.observe
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class AddSubtractViewModel(formatterPrefsRepository: FormatterPrefsRepository) : ViewModel() {
  private val _initialDateTime = Clock.System.now()
  private var _calculateJob: Job? = null
  private val _result = MutableStateFlow(_initialDateTime)
  private val _start = MutableStateFlow(_initialDateTime)
  private val _addition = MutableStateFlow(true)
  private val _years = TextFieldState()
  private val _months = TextFieldState()
  private val _weeks = TextFieldState()
  private val _days = TextFieldState()
  private val _hours = TextFieldState()
  private val _minutes = TextFieldState()

  internal val uiState: StateFlow<AddSubtractUIState> =
    combine(_result, _start, _addition, formatterPrefsRepository.prefs) {
        resultValue,
        startValue,
        additionValue,
        formatterPrefs ->
        return@combine AddSubtractUIState.Ready(
          addition = additionValue,
          start = startValue,
          result = resultValue,
          years = _years,
          months = _months,
          weeks = _weeks,
          days = _days,
          hours = _hours,
          minutes = _minutes,
          formatterSymbols = formatterPrefs.formatterSymbols,
        )
      }
      .stateIn(viewModelScope, AddSubtractUIState.Loading)

  internal suspend fun observeInput() {
    val yearsFlow = _years.observe()
    val monthsFlow = _months.observe()
    val weeksFlow = _weeks.observe()
    val daysFlow = _days.observe()
    val hoursFlow = _hours.observe()
    val minutesFlow = _minutes.observe()

    combineBig(
        _addition,
        _start,
        yearsFlow,
        monthsFlow,
        weeksFlow,
        daysFlow,
        hoursFlow,
        minutesFlow,
      ) {
        additionValue,
        startValue,
        yearsValue,
        monthsValue,
        weeksValue,
        daysValue,
        hoursValue,
        minutesValue ->
        calculate(
          additionValue,
          startValue,
          yearsValue.toString().ifEmpty { "0" }.toInt(),
          monthsValue.toString().ifEmpty { "0" }.toInt(),
          weeksValue.toString().ifEmpty { "0" }.toInt(),
          daysValue.toString().ifEmpty { "0" }.toInt(),
          hoursValue.toString().ifEmpty { "0" }.toInt(),
          minutesValue.toString().ifEmpty { "0" }.toInt(),
        )
      }
      .collectLatest {}
  }

  internal fun updateStart(newValue: Instant) = _start.update { newValue }

  internal fun updateAddition(newValue: Boolean) = _addition.update { newValue }

  private fun calculate(
    addition: Boolean,
    start: Instant,
    years: Int,
    months: Int,
    weeks: Int,
    days: Int,
    hours: Int,
    minutes: Int,
  ) {
    _calculateJob?.cancel()
    _calculateJob =
      viewModelScope.launch(Dispatchers.Default) {
        val daysInWeeks = weeks * 7
        val totalDays = days + daysInWeeks
        val period =
          DateTimePeriod(
            years = if (addition) years else -years,
            months = if (addition) months else -months,
            days = if (addition) totalDays else -totalDays,
            hours = if (addition) hours else -hours,
            minutes = if (addition) minutes else -minutes,
          )
        val timeZone = TimeZone.currentSystemDefault()
        val newResult = start.plus(period, timeZone)

        _result.update { newResult }
      }
  }
}

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

package com.sadellie.unitto.feature.datecalculator.difference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.sadellie.unitto.core.common.MAX_SCALE
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.FormatterPrefsRepository
import com.sadellie.unitto.feature.datecalculator.InstantUtils
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlin.time.Instant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class DateDifferenceViewModel(formatterPrefsRepository: FormatterPrefsRepository) : ViewModel() {
  private val start = MutableStateFlow(InstantUtils.nowWithMinutes())
  private val end = MutableStateFlow(InstantUtils.nowWithMinutes())
  private val result = MutableStateFlow<InstantDifference>(InstantDifference.Zero)

  internal val uiState: StateFlow<DifferenceUIState> =
    combine(formatterPrefsRepository.prefs, start, end, result) { prefs, start, end, result ->
        return@combine DifferenceUIState.Ready(
          start = start,
          end = end,
          result = result,
          precision = prefs.digitsPrecision,
          outputFormat = prefs.outputFormat,
          formatterSymbols = prefs.formatterSymbols,
        )
      }
      .mapLatest { ui ->
        updateResult(start = ui.start, end = ui.end)
        ui
      }
      .stateIn(viewModelScope, DifferenceUIState.Loading)

  internal fun setStartDate(newValue: Instant) = start.update { newValue }

  internal fun setEndDate(newValue: Instant) = end.update { newValue }

  private fun updateResult(start: Instant, end: Instant) =
    viewModelScope.launch(Dispatchers.Default) {
      result.update {
        try {
          start.difference(end, MAX_SCALE)
        } catch (e: Exception) {
          Logger.e(e, TAG) { "Failed to update result" }
          InstantDifference.Zero
        }
      }
    }
}

private const val TAG = "DateDifferenceViewModel"

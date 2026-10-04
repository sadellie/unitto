/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.programmer

import androidx.compose.foundation.text.input.TextFieldState
import androidx.paging.PagingData
import com.sadellie.unitto.core.common.DataUnit
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.model.programmer.ShiftType
import com.sadellie.unitto.core.ui.calculators.CalculationResult
import com.sadellie.unitto.core.ui.calculators.CalculatorHistoryListItem
import kotlinx.coroutines.flow.Flow

internal sealed interface ProgrammerScreenUIState {
  data class Ready(
    val input: TextFieldState,
    val output: CalculationResult,
    val showAcButton: Boolean,
    val formatterSymbols: FormatterSymbols,
    val middleZero: Boolean,
    val dataUnit: DataUnit,
    val base: Int,
    val shiftType: ShiftType,
    val partialHistoryView: Boolean,
    val steppedPartialHistoryView: Boolean,
    val initialPartialHistoryView: Boolean,
    val openHistoryViewButton: Boolean,
    val history: Flow<PagingData<CalculatorHistoryListItem>>,
  ) : ProgrammerScreenUIState

  data object Loading : ProgrammerScreenUIState
}

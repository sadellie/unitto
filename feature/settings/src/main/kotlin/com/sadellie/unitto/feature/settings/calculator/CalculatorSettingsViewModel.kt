/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import kotlinx.coroutines.launch

internal class CalculatorSettingsViewModel(
  private val userPrefsRepository: UserPreferencesRepository
) : ViewModel() {
  val prefs = userPrefsRepository.calculatorPrefs.stateIn(viewModelScope, null)

  fun updatePartialHistoryView(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updatePartialHistoryView(enabled) }

  fun updateSteppedPartialHistoryView(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateSteppedPartialHistoryView(enabled) }

  fun updateOpenHistoryViewButton(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateOpenHistoryViewButton(enabled) }

  fun updateFractionalOutput(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateFractionalOutput(enabled) }
}

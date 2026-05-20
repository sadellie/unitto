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

package com.sadellie.unitto.feature.settings.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.launch

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class CalculatorSettingsViewModel(private val userPrefsRepository: UserPreferencesRepository) :
  ViewModel() {
  internal val prefs = userPrefsRepository.calculatorPrefs.stateIn(viewModelScope, null)

  internal fun updatePartialHistoryView(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updatePartialHistoryView(enabled) }

  internal fun updateSteppedPartialHistoryView(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateSteppedPartialHistoryView(enabled) }

  internal fun updateOpenHistoryViewButton(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateOpenHistoryViewButton(enabled) }

  internal fun updateFractionalOutput(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateFractionalOutput(enabled) }

  internal fun updateConstantCalculation(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateConstantCalculation(enabled) }
}

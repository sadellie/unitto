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

package com.sadellie.unitto.feature.settings.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CalculatorSettingsViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
) : ViewModel() {
    val uiState = combine(
        userPrefsRepository.appPrefs,
        userPrefsRepository.calculatorPrefs,
    ) { app, calc ->
        if (app.rpnMode) {
            CalculatorSettingsUIState.RPN
        } else {
            CalculatorSettingsUIState.Standard(
                partialHistoryView = calc.partialHistoryView,
                clearInputAfterEquals = calc.clearInputAfterEquals
            )
        }
    }
        .stateIn(viewModelScope, CalculatorSettingsUIState.Loading)

    fun updatePartialHistoryView(enabled: Boolean) = viewModelScope.launch {
        userPrefsRepository.updatePartialHistoryView(enabled)
    }

    fun updateClearInputAfterEquals(enabled: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateClearInputAfterEquals(enabled)
    }

    fun updateRpnMode(enabled: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateRpnMode(enabled)
    }
}

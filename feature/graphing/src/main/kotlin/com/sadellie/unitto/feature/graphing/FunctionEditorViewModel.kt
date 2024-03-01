/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.graphing

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.feature.graphing.navigation.FunctionEditorRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@HiltViewModel
internal class FunctionEditorViewModel
@Inject
constructor(
  private val userPreferencesRepository: UserPreferencesRepository,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val args = savedStateHandle.toRoute<FunctionEditorRoute>()
  private val function =
    GraphFunction(
      id = args.functionId,
      expression = args.functionExpression,
      color = Color(args.functionColor.toULong()),
    )

  val uiState =
    userPreferencesRepository.calculatorPrefs
      .mapLatest { prefs ->
        FunctionUIState.Editor(
          middleZero = prefs.middleZero,
          showAcButton = prefs.acButton,
          inverseMode = prefs.inverseMode,
          formatterSymbols = prefs.formatterSymbols,
          function = function,
        )
      }
      .stateIn(viewModelScope, null)

  fun onInverseModeClick(enabled: Boolean) =
    viewModelScope.launch { userPreferencesRepository.updateInverseMode(enabled) }
}

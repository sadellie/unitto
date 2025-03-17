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

package com.sadellie.unitto.feature.settings.display

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayViewModel
@Inject
constructor(private val userPrefsRepository: UserPreferencesRepository) : ViewModel() {

  val prefs = userPrefsRepository.displayPrefs.stateIn(viewModelScope, null)

  fun updateThemingMode(themingMode: ThemingMode) {
    viewModelScope.launch { userPrefsRepository.updateThemingMode(themingMode) }
  }

  fun updateDynamicTheme(enabled: Boolean) {
    viewModelScope.launch { userPrefsRepository.updateDynamicTheme(enabled) }
  }

  fun updateAmoledTheme(enabled: Boolean) {
    viewModelScope.launch { userPrefsRepository.updateAmoledTheme(enabled) }
  }

  fun updateCustomColor(color: Color) {
    viewModelScope.launch { userPrefsRepository.updateCustomColor(color.value.toLong()) }
  }

  fun updateMonetMode(monetMode: MonetMode) {
    viewModelScope.launch { userPrefsRepository.updateMonetMode(monetMode) }
  }

  fun updateAcButton(enabled: Boolean) {
    viewModelScope.launch { userPrefsRepository.updateAcButton(enabled) }
  }

  fun updateMiddleZero(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateMiddleZero(enabled) }
}

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

package com.sadellie.unitto.feature.settings.display

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.CalculatorHistoryPrefsRepository
import com.sadellie.unitto.core.datastore.KeypadPrefsRepository
import com.sadellie.unitto.core.datastore.ThemePrefsRepository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import io.github.sadellie.themmo.core.MonetMode
import io.github.sadellie.themmo.core.ThemingMode
import kotlinx.coroutines.launch

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class DisplayViewModel(
  private val keypadPrefsRepository: KeypadPrefsRepository,
  private val themePrefsRepository: ThemePrefsRepository,
  private val calculatorHistoryPrefsRepository: CalculatorHistoryPrefsRepository,
) : ViewModel() {
  internal val calculatorHistoryPrefs =
    calculatorHistoryPrefsRepository.prefs.stateIn(viewModelScope, null)
  internal val keypadPrefs = keypadPrefsRepository.prefs.stateIn(viewModelScope, null)

  internal fun updateThemingMode(themingMode: ThemingMode) {
    viewModelScope.launch { themePrefsRepository.updateThemingMode(themingMode) }
  }

  internal fun updateDynamicTheme(enabled: Boolean) {
    viewModelScope.launch { themePrefsRepository.updateDynamicTheme(enabled) }
  }

  internal fun updateAmoledTheme(enabled: Boolean) {
    viewModelScope.launch { themePrefsRepository.updateAmoledTheme(enabled) }
  }

  internal fun updateCustomColor(color: Color) {
    viewModelScope.launch { themePrefsRepository.updateCustomColor(color.value.toLong()) }
  }

  internal fun updateMonetMode(monetMode: MonetMode) {
    viewModelScope.launch { themePrefsRepository.updateMonetMode(monetMode) }
  }

  internal fun updatePartialHistoryView(enabled: Boolean) = viewModelScope.launch {
    calculatorHistoryPrefsRepository.updatePartialHistoryView(enabled)
  }

  internal fun updateSteppedPartialHistoryView(enabled: Boolean) = viewModelScope.launch {
    calculatorHistoryPrefsRepository.updateSteppedPartialHistoryView(enabled)
  }

  internal fun updateOpenHistoryViewButton(enabled: Boolean) = viewModelScope.launch {
    calculatorHistoryPrefsRepository.updateOpenHistoryViewButton(enabled)
  }

  internal fun updateAcButton(enabled: Boolean) {
    viewModelScope.launch { keypadPrefsRepository.updateAcButton(enabled) }
  }

  internal fun updateMiddleZero(enabled: Boolean) = viewModelScope.launch {
    keypadPrefsRepository.updateMiddleZero(enabled)
  }
}

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

package com.sadellie.unitto.feature.settings.display

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
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
class DisplayViewModel(private val userPrefsRepository: UserPreferencesRepository) : ViewModel() {

  internal val prefs = userPrefsRepository.displayPrefs.stateIn(viewModelScope, null)

  internal fun updateThemingMode(themingMode: ThemingMode) {
    viewModelScope.launch { userPrefsRepository.updateThemingMode(themingMode) }
  }

  internal fun updateDynamicTheme(enabled: Boolean) {
    viewModelScope.launch { userPrefsRepository.updateDynamicTheme(enabled) }
  }

  internal fun updateAmoledTheme(enabled: Boolean) {
    viewModelScope.launch { userPrefsRepository.updateAmoledTheme(enabled) }
  }

  internal fun updateCustomColor(color: Color) {
    viewModelScope.launch { userPrefsRepository.updateCustomColor(color.value.toLong()) }
  }

  internal fun updateMonetMode(monetMode: MonetMode) {
    viewModelScope.launch { userPrefsRepository.updateMonetMode(monetMode) }
  }

  internal fun updateAcButton(enabled: Boolean) {
    viewModelScope.launch { userPrefsRepository.updateAcButton(enabled) }
  }

  internal fun updateMiddleZero(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateMiddleZero(enabled) }
}

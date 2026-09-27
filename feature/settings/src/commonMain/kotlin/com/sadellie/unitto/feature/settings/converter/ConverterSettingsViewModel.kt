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

package com.sadellie.unitto.feature.settings.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.datastore.ConverterPrefsRepository
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.launch

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class ConverterSettingsViewModel(private val converterPrefsRepository: ConverterPrefsRepository) :
  ViewModel() {
  internal val prefs = converterPrefsRepository.prefs.stateIn(viewModelScope, null)

  internal fun updateUnitConverterFormatTime(enabled: Boolean) = viewModelScope.launch {
    converterPrefsRepository.updateUnitConverterFormatTime(enabled)
  }

  internal fun updateUnitConverterSorting(sorting: UnitsListSorting) = viewModelScope.launch {
    converterPrefsRepository.updateUnitConverterSorting(sorting)
  }

  internal fun updateUnitConverterShowIcons(enabled: Boolean) = viewModelScope.launch {
    converterPrefsRepository.updateUnitConverterShowIcons(enabled)
  }
}

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.database.CurrencyRatesDao
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

internal class SettingsViewModel(
  private val userPrefsRepository: UserPreferencesRepository,
  private val currencyRatesDao: CurrencyRatesDao,
) : ViewModel() {
  private val _showErrorToast = MutableSharedFlow<Boolean>()
  val showErrorToast = _showErrorToast.asSharedFlow()

  val uiState =
    combine(userPrefsRepository.generalPrefs, currencyRatesDao.size()) { prefs, cacheSize ->
        SettingsUIState.Ready(
          enableVibrations = prefs.enableVibrations,
          enableKeepScreenOn = prefs.enableKeepScreenOn,
          cacheSize = cacheSize,
          showUpdateChangelog = prefs.lastReadChangelog != BuildConfig.VERSION_CODE,
        )
      }
      .stateIn(viewModelScope, SettingsUIState.Loading)

  /** @see UserPreferencesRepository.updateLastReadChangelog */
  fun updateLastReadChangelog(value: String) =
    viewModelScope.launch { userPrefsRepository.updateLastReadChangelog(value) }

  /** @see UserPreferencesRepository.updateVibrations */
  fun updateVibrations(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateVibrations(enabled) }

  /** @see UserPreferencesRepository.updateEnableKeepScreenOn */
  fun updateEnableKeepScreenOn(enabled: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateEnableKeepScreenOn(enabled) }

  fun clearCache() = viewModelScope.launch(Dispatchers.IO) { currencyRatesDao.clear() }
}

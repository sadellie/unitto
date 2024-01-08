/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.base.BuildConfig
import com.sadellie.unitto.data.backup.BackupManager
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.database.CurrencyRatesDao
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val currencyRatesDao: CurrencyRatesDao,
    private val backupManager: BackupManager
) : ViewModel() {
    private val _backupFileUri = MutableSharedFlow<Uri?>()
    val backupFileUri = _backupFileUri.asSharedFlow()

    private val _showErrorToast = MutableSharedFlow<Boolean>()
    val showErrorToast = _showErrorToast.asSharedFlow()

    private val _backupInProgress = MutableStateFlow(false)
    private var backupJob: Job? = null

    val uiState = combine(
        userPrefsRepository.generalPrefs,
        currencyRatesDao.size(),
        _backupInProgress,
    ) { prefs, cacheSize, backupInProgress ->
        SettingsUIState.Ready(
            enableVibrations = prefs.enableVibrations,
            cacheSize = cacheSize,
            backupInProgress = backupInProgress,
            showUpdateChangelog = prefs.lastReadChangelog != BuildConfig.VERSION_CODE
        )
    }
        .stateIn(viewModelScope, SettingsUIState.Loading)

    fun backup() {
        backupJob?.cancel()
        backupJob = viewModelScope.launch(Dispatchers.IO) {
            _backupInProgress.update { true }
            try {
                val backupFileUri = backupManager.backup()
                _backupFileUri.emit(backupFileUri) // Emit to trigger file share intent
                _showErrorToast.emit(false)
            } catch (e: Exception) {
                _showErrorToast.emit(true)
                Log.e(TAG, "$e")
            }
            _backupInProgress.update { false }
        }
    }

    fun restore(uri: Uri) {
        backupJob?.cancel()
        backupJob = viewModelScope.launch(Dispatchers.IO) {
            _backupInProgress.update { true }
            try {
                backupManager.restore(uri)
                _showErrorToast.emit(false)
            } catch (e: Exception) {
                _showErrorToast.emit(true)
                Log.e(TAG, "$e")
            }
            _backupInProgress.update { false }
        }
    }

    /**
     * @see UserPreferencesRepository.updateLastReadChangelog
     */
    fun updateLastReadChangelog(value: String) = viewModelScope.launch {
        userPrefsRepository.updateLastReadChangelog(value)
    }

    /**
     * @see UserPreferencesRepository.updateVibrations
     */
    fun updateVibrations(enabled: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateVibrations(enabled)
    }

    fun clearCache() = viewModelScope.launch(Dispatchers.IO) {
        currencyRatesDao.clear()
    }
}

private const val TAG = "SettingsViewModel"
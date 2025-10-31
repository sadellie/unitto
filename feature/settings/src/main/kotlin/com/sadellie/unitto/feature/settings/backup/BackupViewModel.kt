/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.backup

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.backup.BackupManager
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.database.AppStatsDao
import com.sadellie.unitto.core.database.UnittoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class BackupViewModel(appStatsDao: AppStatsDao, private val database: UnittoDatabase) :
  ViewModel() {
  private val _showErrorToast = MutableSharedFlow<Boolean>()
  val showErrorToast = _showErrorToast.asSharedFlow()
  private val _favoriteUnits = appStatsDao.favoriteUnitsSize()
  private val _usedUnits = appStatsDao.usedUnitsCount()
  private val _savedExpressions = appStatsDao.savedExpressionCount()
  private val _favoriteTimeZones = appStatsDao.favoriteTimeZones()
  private val _isInProgress = MutableStateFlow(false)
  private var backupJob: Job? = null

  val uiState =
    combine(_favoriteUnits, _usedUnits, _savedExpressions, _favoriteTimeZones, _isInProgress) {
        favoriteUnits,
        usedUnits,
        savedExpressions,
        favoriteTimeZones,
        isInProgress ->
        if (isInProgress) return@combine BackupUIState.InProgress

        return@combine BackupUIState.Ready(
          favoriteUnits = favoriteUnits,
          usedUnits = usedUnits,
          savedExpressions = savedExpressions,
          favoriteTimeZones = favoriteTimeZones,
        )
      }
      .stateIn(viewModelScope, BackupUIState.Loading)

  fun backup(context: Context, uri: Uri) {
    backupJob?.cancel()
    backupJob =
      viewModelScope.launch(Dispatchers.IO) {
        _isInProgress.update { true }
        try {
          BackupManager().backup(context, uri, database)
        } catch (e: Exception) {
          _showErrorToast.emit(true)
          Log.e(TAG, "$e")
        }
        _isInProgress.update { false }
      }
  }

  fun restore(context: Context, uri: Uri) {
    backupJob?.cancel()
    backupJob =
      viewModelScope.launch(Dispatchers.IO) {
        _isInProgress.update { true }
        try {
          BackupManager().restore(context, uri, database)
        } catch (e: Exception) {
          _showErrorToast.emit(true)
          Log.e(TAG, "$e")
        }
        _isInProgress.update { false }
      }
  }
}

private const val TAG = "BackupViewModel"

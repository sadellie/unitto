/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022 Elshan Agaev
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

package com.sadellie.unitto.screens.setttings

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.sadellie.unitto.data.preferences.UserPreferences
import com.sadellie.unitto.data.preferences.UserPreferencesRepository
import com.sadellie.unitto.screens.Formatter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.themmo.ThemingMode
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val application: Application,
) : ViewModel() {
    var userPrefs: UserPreferences by mutableStateOf(UserPreferences())

    /**
     * @see [UserPreferencesRepository.updateThemingMode]
     */
    fun updateThemingMode(themingMode: ThemingMode) {
        viewModelScope.launch {
            userPrefsRepository.updateThemingMode(themingMode)
        }
    }

    /**
     * @see [UserPreferencesRepository.updateDynamicTheme]
     */
    fun updateDynamicTheme(enabled: Boolean) {
        viewModelScope.launch {
            userPrefsRepository.updateDynamicTheme(enabled)
        }
    }

    /**
     * @see [UserPreferencesRepository.updateAmoledTheme]
     */
    fun updateAmoledTheme(enabled: Boolean) {
        viewModelScope.launch {
            userPrefsRepository.updateAmoledTheme(enabled)
        }
    }

    /**
     * See [UserPreferencesRepository.updateDigitsPrecision]
     */
    fun updatePrecision(precision: Int) {
        viewModelScope.launch {
            userPrefsRepository.updateDigitsPrecision(precision)
        }
    }

    /**
     * See [UserPreferencesRepository.updateSeparator]
     */
    fun updateSeparator(separator: Int) {
        viewModelScope.launch {
            userPrefsRepository.updateSeparator(separator)
        }
    }

    /**
     * See [UserPreferencesRepository.updateOutputFormat]
     */
    fun updateOutputFormat(outputFormat: Int) {
        viewModelScope.launch {
            userPrefsRepository.updateOutputFormat(outputFormat)
        }
    }

    /**
     * See [UserPreferencesRepository.updateEnableAnalytics]
     */
    fun updateEnableAnalytics(enableAnalytics: Boolean) {
        viewModelScope.launch {
            userPrefsRepository.updateEnableAnalytics(enableAnalytics)
            FirebaseAnalytics.getInstance(application)
                .setAnalyticsCollectionEnabled(enableAnalytics)
        }
    }

    init {
        viewModelScope.launch {
            userPrefsRepository.userPreferencesFlow.collect {
                userPrefs = it
                Formatter.setSeparator(it.separator)
            }
        }
    }
}

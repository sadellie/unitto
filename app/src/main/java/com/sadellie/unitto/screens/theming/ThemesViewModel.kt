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

package com.sadellie.unitto.screens.theming

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.themmo.ThemingMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemesViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository
) : ViewModel() {
    var themingMode: ThemingMode? by mutableStateOf(null)
    var enableDynamic by mutableStateOf(false)
    var enableAmoled by mutableStateOf(false)

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
     * Collect saved theming options. Used on app launch.
     */
    private fun collectThemeOptions() {
        viewModelScope.launch {
            val userPref = userPrefsRepository.userPreferencesFlow.first()
            themingMode = userPref.themingMode
            enableDynamic = userPref.enableDynamicTheme
            enableAmoled = userPref.enableAmoledTheme
        }
    }

    init {
        collectThemeOptions()
    }
}

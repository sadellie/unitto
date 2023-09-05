/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.themes

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.themmo.MonetMode
import io.github.sadellie.themmo.ThemingMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemesViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository
) : ViewModel() {

    val systemFont = userPrefsRepository.uiPreferencesFlow
        .map { it.systemFont }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    /**
     * @see UserPreferencesRepository.updateThemingMode
     */
    fun updateThemingMode(themingMode: ThemingMode) {
        viewModelScope.launch {
            userPrefsRepository.updateThemingMode(themingMode)
        }
    }

    /**
     * @see UserPreferencesRepository.updateDynamicTheme
     */
    fun updateDynamicTheme(enabled: Boolean) {
        viewModelScope.launch {
            userPrefsRepository.updateDynamicTheme(enabled)
        }
    }

    /**
     * @see UserPreferencesRepository.updateAmoledTheme
     */
    fun updateAmoledTheme(enabled: Boolean) {
        viewModelScope.launch {
            userPrefsRepository.updateAmoledTheme(enabled)
        }
    }

    /**
     * @see UserPreferencesRepository.updateCustomColor
     */
    fun updateCustomColor(color: Color) {
        viewModelScope.launch {
            userPrefsRepository.updateCustomColor(color)
        }
    }

    /**
     * @see UserPreferencesRepository.updateMonetMode
     */
    fun updateMonetMode(monetMode: MonetMode) {
        viewModelScope.launch {
            userPrefsRepository.updateMonetMode(monetMode)
        }
    }

    /**
     * @see UserPreferencesRepository.updateSystemFont
     */
    fun updateSystemFont(enabled: Boolean) {
        viewModelScope.launch {
            userPrefsRepository.updateSystemFont(enabled)
        }
    }
}

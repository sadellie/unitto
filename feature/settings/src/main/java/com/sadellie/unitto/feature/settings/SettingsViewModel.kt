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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.ui.Formatter
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.unitgroups.UnitGroupsRepository
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sadellie.themmo.ThemingMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitGroupsRepository: UnitGroupsRepository,
) : ViewModel() {
    var userPrefs = userPrefsRepository.userPreferencesFlow
        .onEach { Formatter.setSeparator(it.separator) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
            com.sadellie.unitto.data.userprefs.UserPreferences()
        )
    val shownUnitGroups = unitGroupsRepository.shownUnitGroups
    val hiddenUnitGroups = unitGroupsRepository.hiddenUnitGroups

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
     * @see UserPreferencesRepository.updateDigitsPrecision
     */
    fun updatePrecision(precision: Int) {
        viewModelScope.launch {
            userPrefsRepository.updateDigitsPrecision(precision)
        }
    }

    /**
     * @see UserPreferencesRepository.updateSeparator
     */
    fun updateSeparator(separator: Int) {
        viewModelScope.launch {
            userPrefsRepository.updateSeparator(separator)
        }
    }

    /**
     * @see UserPreferencesRepository.updateOutputFormat
     */
    fun updateOutputFormat(outputFormat: Int) {
        viewModelScope.launch {
            userPrefsRepository.updateOutputFormat(outputFormat)
        }
    }

    /**
     * @see UserPreferencesRepository.updateVibrations
     */
    fun updateVibrations(enabled: Boolean) {
        viewModelScope.launch {
            userPrefsRepository.updateVibrations(enabled)
        }
    }

    /**
     * @see UserPreferencesRepository.updateStartingScreen
     */
    fun updateStartingScreen(startingScreen: String) {
        viewModelScope.launch {
            userPrefsRepository.updateStartingScreen(startingScreen)
        }
    }

    /**
     * @see UnitGroupsRepository.markUnitGroupAsHidden
     * @see UserPreferencesRepository.updateShownUnitGroups
     */
    fun hideUnitGroup(unitGroup: UnitGroup) {
        viewModelScope.launch {
            unitGroupsRepository.markUnitGroupAsHidden(unitGroup)
            userPrefsRepository.updateShownUnitGroups(unitGroupsRepository.shownUnitGroups.value)
        }
    }

    /**
     * @see UnitGroupsRepository.markUnitGroupAsShown
     * @see UserPreferencesRepository.updateShownUnitGroups
     */
    fun returnUnitGroup(unitGroup: UnitGroup) {
        viewModelScope.launch {
            unitGroupsRepository.markUnitGroupAsShown(unitGroup)
            userPrefsRepository.updateShownUnitGroups(unitGroupsRepository.shownUnitGroups.value)
        }
    }

    /**
     * @see UnitGroupsRepository.moveShownUnitGroups
     */
    fun onMove(from: ItemPosition, to: ItemPosition) {
        viewModelScope.launch {
            unitGroupsRepository.moveShownUnitGroups(from, to)
        }
    }

    /**
     * @see UserPreferencesRepository.updateShownUnitGroups
     */
    fun onDragEnd() {
        viewModelScope.launch {
            userPrefsRepository.updateShownUnitGroups(unitGroupsRepository.shownUnitGroups.value)
        }
    }

    /**
     * @see UserPreferencesRepository.updateToolsExperiment
     */
    fun enableToolsExperiment() {
        viewModelScope.launch {
            userPrefsRepository.updateToolsExperiment(true)
        }
    }

    /**
     * @see UserPreferencesRepository.updateUnitConverterFormatTime
     */
    fun updateUnitConverterFormatTime(enabled: Boolean) {
        viewModelScope.launch {
            userPrefsRepository.updateUnitConverterFormatTime(enabled)
        }
    }

    /**
     * Prevent from dragging over non-draggable items (headers and hidden)
     *
     * @param pos Position we are dragging over.
     * @return True if can drag over given item.
     */
    fun canDragOver(pos: ItemPosition) = shownUnitGroups.value.any { it == pos.key }

    init {
        viewModelScope.launch {
            unitGroupsRepository.updateShownGroups(
                userPrefsRepository.userPreferencesFlow.first().shownUnitGroups
            )
        }
    }
}

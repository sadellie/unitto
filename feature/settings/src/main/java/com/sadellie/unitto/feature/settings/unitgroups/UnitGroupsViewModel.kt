/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

package com.sadellie.unitto.feature.settings.unitgroups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UnitGroupsViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
) : ViewModel() {

    val uiState = userPrefsRepository.unitGroupsPrefs
        .map {
            UnitGroupsUIState.Ready(
                shownUnitGroups = it.shownUnitGroups,
                hiddenUnitGroups = UnitGroup.entries - it.shownUnitGroups.toSet(),
            )
        }
        .stateIn(viewModelScope, UnitGroupsUIState.Loading)

    /**
     * @see UserPreferencesRepository.removeShownUnitGroup
     */
    fun removeShownUnitGroup(unitGroup: UnitGroup) {
        viewModelScope.launch {
            userPrefsRepository.removeShownUnitGroup(unitGroup)
        }
    }

    /**
     * @see UserPreferencesRepository.addShownUnitGroup
     */
    fun addShownUnitGroup(unitGroup: UnitGroup) {
        viewModelScope.launch {
            userPrefsRepository.addShownUnitGroup(unitGroup)
        }
    }

    /**
     * @see UserPreferencesRepository.updateShownUnitGroups
     */
    fun updateShownUnitGroups(unitGroups: List<UnitGroup>) {
        viewModelScope.launch {
            userPrefsRepository.updateShownUnitGroups(unitGroups)
        }
    }
}

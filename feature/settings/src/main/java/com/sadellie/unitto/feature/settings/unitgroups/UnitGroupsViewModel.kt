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

package com.sadellie.unitto.feature.settings.unitgroups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject

@HiltViewModel
class UnitGroupsViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitGroupsRepository: UnitGroupsRepository,
) : ViewModel() {
    val shownUnitGroups = unitGroupsRepository.shownUnitGroups
    val hiddenUnitGroups = unitGroupsRepository.hiddenUnitGroups

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
     * Prevent from dragging over non-draggable items (headers and hidden)
     *
     * @param pos Position we are dragging over.
     * @return True if can drag over given item.
     */
    fun canDragOver(pos: ItemPosition) = shownUnitGroups.value.any { it == pos.key }

    init {
        viewModelScope.launch {
            unitGroupsRepository.updateShownGroups(
                userPrefsRepository.unitGroupsPrefs.first().shownUnitGroups
            )
        }
    }
}

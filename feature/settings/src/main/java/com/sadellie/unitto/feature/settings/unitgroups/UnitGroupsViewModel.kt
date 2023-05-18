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
import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.userprefs.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.burnoutcrew.reorderable.ItemPosition
import javax.inject.Inject

@HiltViewModel
class UnitGroupsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private var mutex: Mutex = Mutex()

    /**
     * Currently shown [UnitGroup]s.
     */
    private val _shownUnitGroups = MutableStateFlow(listOf<UnitGroup>())

    /**
     * Currently hidden [UnitGroup]s.
     */
    private val _hiddenUnitGroups = MutableStateFlow(listOf<UnitGroup>())

    init {
        viewModelScope.launch {
            val shown = userPreferencesRepository.mainPreferencesFlow.first().shownUnitGroups
            mutex.withLock {
                _shownUnitGroups.update { shown }
                _hiddenUnitGroups.update { ALL_UNIT_GROUPS - shown.toSet() }
            }
        }
    }

    val uiState = combine(_shownUnitGroups, _hiddenUnitGroups) { shown, hidden ->
        return@combine UnitGroupsUIState(
            shownGroups = shown,
            hiddenGroups = hidden
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        UnitGroupsUIState(emptyList(), emptyList())
    )

    /**
     * Moves [UnitGroup] from [_shownUnitGroups] to [_hiddenUnitGroups]
     *
     * @param unitGroup [UnitGroup] to hide.
     */
    fun markUnitGroupAsHidden(unitGroup: UnitGroup) = viewModelScope.launch {
        mutex.withLock {
            _shownUnitGroups.update { it - unitGroup }
            // Newly hidden unit will appear at the top of the list
            _hiddenUnitGroups.update { listOf(unitGroup) + it }
        }
    }

    /**
     * Moves [UnitGroup] from [_hiddenUnitGroups] to [_shownUnitGroups]
     *
     * @param unitGroup [UnitGroup] to show.
     */
    fun markUnitGroupAsShown(unitGroup: UnitGroup) = viewModelScope.launch {
        mutex.withLock {
            _hiddenUnitGroups.update { it - unitGroup }
            _shownUnitGroups.update { it + unitGroup }
        }
    }

    /**
     * Moves [UnitGroup] in [_shownUnitGroups] from one index to another (reorder).
     *
     * @param from Position from which we need to move from
     * @param to Position where to put [UnitGroup]
     */
    fun moveShownUnitGroups(from: ItemPosition, to: ItemPosition) = viewModelScope.launch {
        mutex.withLock {
            _shownUnitGroups.update { shown ->
                shown.toMutableList().apply {
                    val initialIndex = shown.indexOfFirst { it == from.key }
                    /**
                     * No such item. Happens when dragging item and clicking "remove" while item is
                     * still being dragged.
                     */
                    if (initialIndex == -1) return@launch

                    add(
                        shown.indexOfFirst { it == to.key },
                        removeAt(initialIndex)
                    )
                }
            }
        }
    }

    fun canDragOver(pos: ItemPosition) = uiState.value.shownGroups.any { it == pos.key }

    fun saveShownUnitGroups() = viewModelScope.launch {
        userPreferencesRepository.updateShownUnitGroups(
            uiState.value.shownGroups
        )
    }
}

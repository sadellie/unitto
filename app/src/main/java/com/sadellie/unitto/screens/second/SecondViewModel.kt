/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.AllUnitsRepository
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.data.units.UnitGroupsRepository
import com.sadellie.unitto.data.units.database.MyBasedUnit
import com.sadellie.unitto.data.units.database.MyBasedUnitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private val basedUnitRepository: MyBasedUnitsRepository,
    private val allUnitsRepository: AllUnitsRepository,
    unitGroupsRepository: UnitGroupsRepository
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(SecondScreenUIState())

    val mainFlow = combine(_uiStateFlow, unitGroupsRepository.shownUnitGroups) { uiState, shown ->
        return@combine uiState.copy(shownUnitGroups = shown)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SecondScreenUIState()
        )

    fun toggleFavoritesOnly() {
        _uiStateFlow.value = _uiStateFlow.value.copy(favoritesOnly = !_uiStateFlow.value.favoritesOnly)
    }

    fun onSearchQueryChange(newValue: String) {
        _uiStateFlow.value = _uiStateFlow.value.copy(searchQuery = newValue)
    }

    /**
     * Changes currently selected chip.
     *
     * @param unitGroup Chip to change to.
     */
    fun setSelectedChip(unitGroup: UnitGroup) {
        _uiStateFlow.value = _uiStateFlow.value.copy(chosenUnitGroup = unitGroup)
    }

    /**
     * Changes currently selected chip, but acts as toggle when the given [UnitGroup] is same as
     * already set [UnitGroup]. For example, if currently selected [UnitGroup] is [UnitGroup.TIME]
     * and the provided [UnitGroup] is also [UnitGroup.TIME], currently selected unit will be set
     * to null (toggle).
     *
     * @param unitGroup [UnitGroup], currently selected chip.
     */
    fun toggleSelectedChip(unitGroup: UnitGroup) {
        val newUnitGroup = if (_uiStateFlow.value.chosenUnitGroup == unitGroup) null else unitGroup
        _uiStateFlow.value = _uiStateFlow.value.copy(chosenUnitGroup = newUnitGroup)
    }

    /**
     * Filters and groups [AllUnitsRepository.allUnits] in coroutine
     *
     * @param hideBrokenCurrencies Decide whether or not we are on left side. Need it because right side requires
     * us to mark disabled currency units
     */
    fun loadUnitsToShow(
        hideBrokenCurrencies: Boolean
    ) {
        viewModelScope.launch {
            // This is mostly not UI related stuff and viewModelScope.launch uses Dispatchers.Main
            // So we switch to Default
            withContext(Dispatchers.Default) {
                val unitsToShow = allUnitsRepository.filterUnits(
                    hideBrokenCurrencies = hideBrokenCurrencies,
                    chosenUnitGroup = _uiStateFlow.value.chosenUnitGroup,
                    favoritesOnly = _uiStateFlow.value.favoritesOnly,
                    searchQuery = _uiStateFlow.value.searchQuery,
                    allUnitsGroups = _uiStateFlow.value.shownUnitGroups
                )

                _uiStateFlow.value = _uiStateFlow.value.copy(unitsToShow = unitsToShow)
            }
        }
    }

    /**
     * Add or remove from favorites (changes to the opposite of current state, toggle)
     */
    fun favoriteUnit(unit: AbstractUnit) {
        viewModelScope.launch {
            // Changing unit in list to the opposite
            unit.isFavorite = !unit.isFavorite
            // Updating it in database
            basedUnitRepository.insertUnits(
                MyBasedUnit(
                    unitId = unit.unitId,
                    isFavorite = unit.isFavorite,
                    pairedUnitId = unit.pairedUnit,
                    frequency = unit.counter
                )
            )
        }
    }
}

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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private val basedUnitRepository: MyBasedUnitsRepository,
    private val allUnitsRepository: AllUnitsRepository,
    private val unitGroupsRepository: UnitGroupsRepository
) : ViewModel() {

    var uiState: SecondScreenUIState by mutableStateOf(SecondScreenUIState())
        private set

    fun toggleFavoritesOnly() {
        uiState = uiState.copy(favoritesOnly = !uiState.favoritesOnly)
    }

    fun onSearchQueryChange(newValue: String) {
        uiState = uiState.copy(searchQuery = newValue)
    }

    /**
     * Changes currently selected chip.
     *
     * @param unitGroup Chip to change to.
     */
    fun setSelectedChip(unitGroup: UnitGroup) {
        uiState = uiState.copy(chosenUnitGroup = unitGroup)
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
        val newUnitGroup = if (uiState.chosenUnitGroup == unitGroup) null else unitGroup
        uiState = uiState.copy(chosenUnitGroup = newUnitGroup)
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
            // Prevent user from seeing invalid list
            uiState = uiState.copy(unitsToShow = emptyMap())

            // This is mostly not UI related stuff and viewModelScope.launch uses Dispatchers.Main
            // So we switch to Default
            withContext(Dispatchers.Default) {
                val unitsToShow = allUnitsRepository.filterUnits(
                    hideBrokenCurrencies = hideBrokenCurrencies,
                    chosenUnitGroup = uiState.chosenUnitGroup,
                    favoritesOnly = uiState.favoritesOnly,
                    searchQuery = uiState.searchQuery,
                    allUnitsGroups = uiState.shownUnitGroups
                )

                uiState = uiState.copy(unitsToShow = unitsToShow)
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

    init {
        viewModelScope.launch {
            unitGroupsRepository.shownUnitGroups.collect {
                uiState = uiState.copy(shownUnitGroups = it)
            }
        }
    }
}

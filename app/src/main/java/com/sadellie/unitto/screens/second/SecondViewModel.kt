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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private val basedUnitRepository: MyBasedUnitsRepository,
    private val allUnitsRepository: AllUnitsRepository,
    unitGroupsRepository: UnitGroupsRepository
) : ViewModel() {

    private val _favoritesOnly = MutableStateFlow(false)
    private val _unitsToShow = MutableStateFlow(emptyMap<UnitGroup, List<AbstractUnit>>())
    private val _searchQuery = MutableStateFlow("")
    private val _chosenUnitGroup: MutableStateFlow<UnitGroup?> = MutableStateFlow(null)
    private val _shownUnitGroups = unitGroupsRepository.shownUnitGroups

    val mainFlow = combine(
        _favoritesOnly,
        _unitsToShow,
        _searchQuery,
        _chosenUnitGroup,
        _shownUnitGroups
    ) { favoritesOnly, unitsToShow, searchQuery, chosenUnitGroup, shownUnitGroups ->
        return@combine SecondScreenUIState(
            favoritesOnly = favoritesOnly,
            unitsToShow = unitsToShow,
            searchQuery = searchQuery,
            chosenUnitGroup = chosenUnitGroup,
            shownUnitGroups = shownUnitGroups
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SecondScreenUIState())

    fun toggleFavoritesOnly(hideBrokenCurrencies: Boolean = true) {
        _favoritesOnly.update { !_favoritesOnly.value }
        loadUnitsToShow(hideBrokenCurrencies)
    }

    fun onSearchQueryChange(newValue: String, hideBrokenCurrencies: Boolean = true) {
        _searchQuery.update { newValue }
        loadUnitsToShow(hideBrokenCurrencies)
    }

    /**
     * Changes currently selected chip.
     *
     * @param unitGroup Chip to change to.
     */
    fun setSelectedChip(unitGroup: UnitGroup, hideBrokenCurrencies: Boolean = true) {
        _chosenUnitGroup.update { unitGroup }
        loadUnitsToShow(hideBrokenCurrencies)
    }

    /**
     * Changes currently selected chip, but acts as toggle when the given [UnitGroup] is same as
     * already set [UnitGroup]. For example, if currently selected [UnitGroup] is [UnitGroup.TIME]
     * and the provided [UnitGroup] is also [UnitGroup.TIME], currently selected unit will be set
     * to null (toggle).
     *
     * @param unitGroup [UnitGroup], currently selected chip.
     */
    fun toggleSelectedChip(unitGroup: UnitGroup, hideBrokenCurrencies: Boolean = true) {
        val newUnitGroup = if (_chosenUnitGroup.value == unitGroup) null else unitGroup
        _chosenUnitGroup.update { newUnitGroup }
        loadUnitsToShow(hideBrokenCurrencies)
    }

    /**
     * Filters and groups [AllUnitsRepository.allUnits] in coroutine
     *
     * @param hideBrokenCurrencies Decide whether or not we are on left side. Need it because right side requires
     * us to mark disabled currency units
     */
    private fun loadUnitsToShow(
        hideBrokenCurrencies: Boolean
    ) {
        viewModelScope.launch {
            // This is mostly not UI related stuff and viewModelScope.launch uses Dispatchers.Main
            // So we switch to Default
            withContext(Dispatchers.Default) {
                val unitsToShow = allUnitsRepository.filterUnits(
                    hideBrokenCurrencies = hideBrokenCurrencies,
                    chosenUnitGroup = _chosenUnitGroup.value,
                    favoritesOnly = _favoritesOnly.value,
                    searchQuery = _searchQuery.value,
                    allUnitsGroups = _shownUnitGroups.value
                )

                _unitsToShow.update { unitsToShow }
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

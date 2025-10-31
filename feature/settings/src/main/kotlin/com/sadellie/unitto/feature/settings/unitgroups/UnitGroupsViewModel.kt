/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class UnitGroupsViewModel(
  private val userPrefsRepository: UserPreferencesRepository,
  private val unitsRepository: UnitsRepository,
) : ViewModel() {
  private var _autoSortJob: Job? = null
  private val _autoSortDialogState = MutableStateFlow(AutoSortDialogState.NONE)
  private val _unitGroupBeforeAutoSorting = MutableStateFlow(emptyList<UnitGroup>())
  private val _shownUnitGroups =
    userPrefsRepository.unitGroupsPrefs.map { it.shownUnitGroups }.stateIn(viewModelScope, null)

  val uiState =
    combine(_shownUnitGroups, _autoSortDialogState, _unitGroupBeforeAutoSorting) {
        shownUnitGroups,
        autoSortDialogState,
        unitGroupBeforeAutoSorting ->
        UnitGroupsUIState.Ready(
          shownUnitGroups = shownUnitGroups ?: return@combine UnitGroupsUIState.Loading,
          hiddenUnitGroups = UnitGroup.entries - shownUnitGroups.toSet(),
          isAutoSortEnabled = unitGroupBeforeAutoSorting.isEmpty(),
          autoSortDialogState = autoSortDialogState,
        )
      }
      .stateIn(viewModelScope, UnitGroupsUIState.Loading)

  /** @see UserPreferencesRepository.removeShownUnitGroup */
  fun removeShownUnitGroup(unitGroup: UnitGroup) {
    viewModelScope.launch {
      userPrefsRepository.removeShownUnitGroup(unitGroup)
      _unitGroupBeforeAutoSorting.update { emptyList() }
    }
  }

  /** @see UserPreferencesRepository.addShownUnitGroup */
  fun addShownUnitGroup(unitGroup: UnitGroup) {
    viewModelScope.launch {
      userPrefsRepository.addShownUnitGroup(unitGroup)
      _unitGroupBeforeAutoSorting.update { emptyList() }
    }
  }

  /** @see UserPreferencesRepository.updateShownUnitGroups */
  fun updateShownUnitGroups(unitGroups: List<UnitGroup>) {
    viewModelScope.launch {
      userPrefsRepository.updateShownUnitGroups(unitGroups)
      _unitGroupBeforeAutoSorting.update { emptyList() }
    }
  }

  fun autoSortUnitGroups() {
    _autoSortJob?.cancel()
    _autoSortJob =
      viewModelScope.launch(Dispatchers.Default) {
        _autoSortDialogState.update { AutoSortDialogState.IN_PROGRESS }
        _unitGroupBeforeAutoSorting.update { _shownUnitGroups.value ?: emptyList() }

        val unitGroups = _shownUnitGroups.value ?: emptyList()
        val units =
          unitsRepository.filter(
            query = "",
            unitGroups = unitGroups,
            favoritesOnly = false,
            sorting = UnitsListSorting.USAGE,
          )
        val sortedUnitGroups = sortUnitGroupsByUsage(units)
        userPrefsRepository.updateShownUnitGroups(sortedUnitGroups)

        _autoSortDialogState.update { AutoSortDialogState.NONE }
      }
  }

  fun undoAutoSortUnitGroups() {
    viewModelScope.launch {
      userPrefsRepository.updateShownUnitGroups(_unitGroupBeforeAutoSorting.value)
      _unitGroupBeforeAutoSorting.update { emptyList() }
    }
  }

  fun updateAutoSortDialogState(value: AutoSortDialogState) {
    if (_autoSortJob?.isActive == true) return
    _autoSortDialogState.update { value }
  }
}

suspend fun sortUnitGroupsByUsage(units: Sequence<UnitSearchResultItem>): List<UnitGroup> =
  withContext(Dispatchers.Default) {
    units
      .groupBy { it.basicUnit.group }
      .mapValues { (_, units) -> units.sumOf { it.stats.frequency } }
      .toList()
      .sortedByDescending { it.second }
      .map { it.first }
  }

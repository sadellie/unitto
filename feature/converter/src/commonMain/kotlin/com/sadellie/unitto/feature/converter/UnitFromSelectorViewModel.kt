/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package com.sadellie.unitto.feature.converter

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.converter.UnitConverterRepository
import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.datastore.ConverterPreferences
import com.sadellie.unitto.core.datastore.UserPreferencesRepository
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.ui.textfield.observe
import com.sadellie.unitto.feature.converter.navigation.UnitFromRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class UnitFromSelectorViewModel(
  private val userPrefsRepository: UserPreferencesRepository,
  private val unitsRepo: UnitConverterRepository,
  private val args: UnitFromRoute,
) : ViewModel() {
  private var _searchJob: Job? = null
  private val _query = TextFieldState()
  private val _searchResults = MutableStateFlow<Map<UnitGroup, List<UnitSearchResultItem>>?>(null)
  private val _selectedUnitGroup = MutableStateFlow<UnitGroup?>(args.unitGroup)

  val unitFromUIState: StateFlow<UnitSelectorUIState> =
    combine(_searchResults, _selectedUnitGroup, userPrefsRepository.converterPrefs) {
        searchResults,
        selectedUnitGroup,
        prefs ->
        return@combine UnitSelectorUIState.UnitFrom(
          query = _query,
          unitFromId = args.unitFromId,
          shownUnitGroups = prefs.shownUnitGroups,
          showFavoritesOnly = prefs.unitConverterFavoritesOnly,
          units = searchResults,
          selectedUnitGroup = selectedUnitGroup,
          sorting = prefs.unitConverterSorting,
        )
      }
      .stateIn(viewModelScope, UnitSelectorUIState.Loading)

  suspend fun observeSearchFilters() {
    val queryFlow = _query.observe()

    combine(queryFlow, _selectedUnitGroup, userPrefsRepository.converterPrefs) {
        queryFlowValue,
        selectedUnitGroupValue,
        converterPrefsValue ->
        onSearch(converterPrefsValue, queryFlowValue.toString(), selectedUnitGroupValue)
      }
      .collectLatest {}
  }

  fun updateShowFavoritesOnly(value: Boolean) =
    viewModelScope.launch { userPrefsRepository.updateUnitConverterFavoritesOnly(value) }

  fun updateSelectedUnitGroup(value: UnitGroup?) {
    _selectedUnitGroup.update { value }
  }

  fun favoriteUnit(unit: UnitSearchResultItem) =
    viewModelScope.launch {
      unitsRepo.favorite(unit.basicUnit.id)
      onSearch(
        userPrefsRepository.converterPrefs.first(),
        _query.text.toString(),
        _selectedUnitGroup.value,
      )
    }

  private fun onSearch(prefs: ConverterPreferences, query: String, selectedGroupValue: UnitGroup?) {
    _searchJob?.cancel()
    _searchJob =
      viewModelScope.launch {
        val result =
          unitsRepo.filterUnits(
            query = query,
            favoritesOnly = prefs.unitConverterFavoritesOnly,
            sorting = prefs.unitConverterSorting,
            unitGroups =
              if (selectedGroupValue == null) {
                prefs.shownUnitGroups
              } else {
                listOf(selectedGroupValue)
              },
          )

        _searchResults.update { result }
      }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}

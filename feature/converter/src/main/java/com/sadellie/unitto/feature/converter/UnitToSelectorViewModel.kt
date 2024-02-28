/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.data.common.stateIn
import com.sadellie.unitto.data.converter.UnitSearchResultItem
import com.sadellie.unitto.data.converter.UnitsRepositoryImpl
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import com.sadellie.unitto.feature.converter.navigation.INPUT_ARG
import com.sadellie.unitto.feature.converter.navigation.UNIT_FROM_ID_ARG
import com.sadellie.unitto.feature.converter.navigation.UNIT_GROUP_ARG
import com.sadellie.unitto.feature.converter.navigation.UNIT_TO_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UnitToSelectorViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitsRepo: UnitsRepositoryImpl,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private var searchJob: Job? = null

    private val query = MutableStateFlow(TextFieldValue())
    private val searchResults = MutableStateFlow<Map<UnitGroup, List<UnitSearchResultItem>>?>(null)
    private val selectedUnitGroup = MutableStateFlow(savedStateHandle.get<UnitGroup>(UNIT_GROUP_ARG))
    private val unitFromId = savedStateHandle.get<String>(UNIT_FROM_ID_ARG)
    private val unitToId = savedStateHandle.get<String>(UNIT_TO_ID_ARG)
    private val input = savedStateHandle.get<String>(INPUT_ARG)

    val unitToUIState: StateFlow<UnitSelectorUIState> = combine(
        query,
        searchResults,
        userPrefsRepository.converterPrefs,
    ) { query, searchResults, prefs ->
        if (unitFromId.isNullOrEmpty()) return@combine UnitSelectorUIState.Loading
        if (unitToId.isNullOrEmpty()) return@combine UnitSelectorUIState.Loading
        if (searchResults == null) return@combine UnitSelectorUIState.Loading

        UnitSelectorUIState.UnitTo(
            query = query,
            unitFrom = unitsRepo.getById(unitFromId),
            unitTo = unitsRepo.getById(unitToId),
            showFavoritesOnly = prefs.unitConverterFavoritesOnly,
            units = searchResults,
            input = input,
            sorting = prefs.unitConverterSorting,
            scale = prefs.precision,
            outputFormat = prefs.outputFormat,
            formatterSymbols = prefs.formatterSymbols,
        )
    }
        .stateIn(viewModelScope, UnitSelectorUIState.Loading)

    fun updateSelectorQuery(value: TextFieldValue) {
        query.update { value }
        onSearch()
    }

    fun updateShowFavoritesOnly(value: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateUnitConverterFavoritesOnly(value)
        onSearch()
    }

    fun favoriteUnit(unit: UnitSearchResultItem) = viewModelScope.launch {
        unitsRepo.favorite(unit.basicUnit.id)
        onSearch()
    }

    private fun onSearch() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val prefs = userPrefsRepository.converterPrefs.first()
            val result = unitsRepo.filterUnitsAndBatchConvert(
                query = query.value.text,
                unitGroup = selectedUnitGroup.value ?: return@launch,
                favoritesOnly = prefs.unitConverterFavoritesOnly,
                sorting = prefs.unitConverterSorting,
                unitFromId = unitFromId ?: return@launch,
                input = input,
            )

            searchResults.update { result }
        }
    }

    init {
        onSearch()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

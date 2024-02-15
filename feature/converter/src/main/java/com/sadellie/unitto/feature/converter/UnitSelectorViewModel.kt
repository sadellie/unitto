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
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.repository.UnitsRepository
import com.sadellie.unitto.data.model.repository.UserPreferencesRepository
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.feature.converter.navigation.INPUT_ARG
import com.sadellie.unitto.feature.converter.navigation.UNIT_FROM_ID_ARG
import com.sadellie.unitto.feature.converter.navigation.UNIT_GROUP_ARG
import com.sadellie.unitto.feature.converter.navigation.UNIT_TO_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class UnitSelectorViewModel @Inject constructor(
    private val userPrefsRepository: UserPreferencesRepository,
    private val unitsRepo: UnitsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val query = MutableStateFlow(TextFieldValue())
    private val searchResults = MutableStateFlow<UnitSearchResult>(UnitSearchResult.Loading)
    private val selectedUnitGroup = MutableStateFlow(savedStateHandle.get<UnitGroup>(UNIT_GROUP_ARG))
    private val unitFromId = savedStateHandle.get<String>(UNIT_FROM_ID_ARG)
    private val unitToId = savedStateHandle.get<String>(UNIT_TO_ID_ARG)
    private val input = savedStateHandle.get<String>(INPUT_ARG)

    val unitFromUIState: StateFlow<UnitSelectorUIState> = combine(
        query,
        searchResults,
        selectedUnitGroup,
        userPrefsRepository.converterPrefs,
    ) { query, searchResults, selectedUnitGroup, prefs ->
        if (unitFromId.isNullOrEmpty()) return@combine UnitSelectorUIState.Loading

        return@combine UnitSelectorUIState.UnitFrom(
            query = query,
            unitFrom = unitsRepo.getById(unitFromId),
            shownUnitGroups = prefs.shownUnitGroups,
            showFavoritesOnly = prefs.unitConverterFavoritesOnly,
            units = searchResults,
            selectedUnitGroup = selectedUnitGroup,
            sorting = prefs.unitConverterSorting,
        )
    }
        .mapLatest { ui ->
            if (ui is UnitSelectorUIState.UnitFrom) {
                searchResults.update {
                    val result = unitsRepo.filterUnits(
                        query = ui.query.text,
                        unitGroup = ui.selectedUnitGroup,
                        favoritesOnly = ui.showFavoritesOnly,
                        hideBrokenUnits = false,
                        sorting = ui.sorting,
                        shownUnitGroups = ui.shownUnitGroups,
                    )

                    if (result.isEmpty()) UnitSearchResult.Empty else UnitSearchResult.Success(result)
                }
            }

            ui
        }
        .stateIn(viewModelScope, UnitSelectorUIState.Loading)

    val unitToUIState: StateFlow<UnitSelectorUIState> = combine(
        query,
        searchResults,
        userPrefsRepository.converterPrefs,
        unitsRepo.units,
    ) { query, searchResults, prefs, _ ->
        if (unitFromId.isNullOrEmpty()) return@combine UnitSelectorUIState.Loading
        if (unitToId.isNullOrEmpty()) return@combine UnitSelectorUIState.Loading

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
        .mapLatest { ui ->
            if (ui is UnitSelectorUIState.UnitTo) {
                searchResults.update {
                    if (ui.unitFrom.group == UnitGroup.CURRENCY) unitsRepo.updateRates(ui.unitFrom)

                    val result = unitsRepo.filterUnits(
                        query = ui.query.text,
                        unitGroup = ui.unitFrom.group,
                        favoritesOnly = ui.showFavoritesOnly,
                        hideBrokenUnits = true,
                        sorting = ui.sorting,
                    )

                    if (result.isEmpty()) UnitSearchResult.Empty else UnitSearchResult.Success(result)
                }
            }
            ui
        }
        .stateIn(viewModelScope, UnitSelectorUIState.Loading)

    fun updateSelectorQuery(value: TextFieldValue) = query.update { value }

    fun updateShowFavoritesOnly(value: Boolean) = viewModelScope.launch {
        userPrefsRepository.updateUnitConverterFavoritesOnly(value)
    }

    fun updateSelectedUnitGroup(value: UnitGroup?) = selectedUnitGroup.update { value }

    fun favoriteUnit(unit: AbstractUnit) = viewModelScope.launch(Dispatchers.IO) {
        unitsRepo.favorite(unit)
    }
}

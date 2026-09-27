/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2026 Elshan Agaev
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
import com.sadellie.unitto.core.datastore.ConverterPrefsRepository
import com.sadellie.unitto.core.datastore.FormatterPrefsRepository
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.ui.textfield.observe
import com.sadellie.unitto.feature.converter.navigation.UnitToRoute
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactoryKey
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@AssistedInject
class UnitToSelectorViewModel(
  @Assisted private val args: UnitToRoute,
  private val converterPrefsRepository: ConverterPrefsRepository,
  private val unitsRepo: UnitConverterRepository,
  formatterPrefsRepository: FormatterPrefsRepository,
) : ViewModel() {
  @AssistedFactory
  @ManualViewModelAssistedFactoryKey
  @ContributesIntoMap(AppScope::class)
  fun interface Factory : ManualViewModelAssistedFactory {
    fun create(args: UnitToRoute): UnitToSelectorViewModel
  }

  private var _searchJob: Job? = null
  private val _query = TextFieldState()
  private val _searchResults = MutableStateFlow<Map<UnitGroup, List<UnitSearchResultItem>>?>(null)
  private val _selectedUnitGroup = MutableStateFlow(args.unitGroup)

  internal val unitToUIState: StateFlow<UnitSelectorUIState> =
    combine(_searchResults, converterPrefsRepository.prefs, formatterPrefsRepository.prefs) {
        searchResults,
        converterPrefs,
        formatterPrefs ->
        UnitSelectorUIState.UnitTo(
          query = _query,
          unitFrom = unitsRepo.getById(args.unitFromId),
          unitTo = unitsRepo.getById(args.unitToId),
          showFavoritesOnly = converterPrefs.favoritesOnly,
          units = searchResults,
          sorting = converterPrefs.sorting,
          scale = formatterPrefs.digitsPrecision,
          outputFormat = formatterPrefs.outputFormat,
          formatterSymbols = formatterPrefs.formatterSymbols,
        )
      }
      .stateIn(viewModelScope, UnitSelectorUIState.Loading)

  internal suspend fun observeSearchFilters() {
    val queryFlow = _query.observe()

    combine(queryFlow, _selectedUnitGroup, converterPrefsRepository.prefs) {
        queryFlowValue,
        selectedUnitGroupValue,
        converterPrefsValue ->
        onSearch(converterPrefsValue, queryFlowValue.toString(), selectedUnitGroupValue)
      }
      .collectLatest {}
  }

  internal fun updateShowFavoritesOnly(value: Boolean) = viewModelScope.launch {
    converterPrefsRepository.updateUnitConverterFavoritesOnly(value)
  }

  internal fun favoriteUnit(unit: UnitSearchResultItem) = viewModelScope.launch {
    unitsRepo.favorite(unit.basicUnit.id)
    onSearch(
      converterPrefsRepository.prefs.first(),
      _query.text.toString(),
      _selectedUnitGroup.value,
    )
  }

  private fun onSearch(prefs: ConverterPreferences, query: String, selectedGroupValue: UnitGroup) {
    _searchJob?.cancel()
    _searchJob = viewModelScope.launch {
      val result =
        unitsRepo.filterUnitsAndBatchConvert(
          query = query,
          unitGroup = selectedGroupValue,
          favoritesOnly = prefs.favoritesOnly,
          sorting = prefs.sorting,
          unitFromId = args.unitFromId,
          input1 = args.input1,
          input2 = args.input2,
          apiUrl = prefs.customApiUrl,
        )

      _searchResults.update { result }
    }
  }

  override fun onCleared() {
    super.onCleared()
    viewModelScope.cancel()
  }
}

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

package com.sadellie.unitto.feature.timezone

import android.icu.util.TimeZone
import android.icu.util.ULocale
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.timezone.TimeZonesRepository
import com.sadellie.unitto.core.model.timezone.SearchResultZone
import com.sadellie.unitto.core.ui.textfield.observe
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class)
class AddTimeZoneViewModel(private val timezonesRepository: TimeZonesRepository) : ViewModel() {
  private var _searchJob: Job? = null
  private val _locale =
    ULocale.forLanguageTag(AppCompatDelegate.getApplicationLocales().toLanguageTags())
  private val _query = TextFieldState()
  private val _result = MutableStateFlow(emptyList<SearchResultZone>())

  internal val uiState =
    _result
      .mapLatest { AddTimeZoneUIState.Ready(_query, it) }
      .stateIn(viewModelScope, AddTimeZoneUIState.Loading)

  internal suspend fun observeSearchFilters() {
    val queryFlow = _query.observe()

    combine(queryFlow, timezonesRepository.favoriteTimeZones) { queryFlowValue, _ ->
        search(queryFlowValue.toString())
      }
      .collectLatest {}
  }

  internal fun addToFavorites(timeZone: TimeZone) = viewModelScope.launch {
    timezonesRepository.addToFavorites(timeZone)
  }

  private fun search(query: String) {
    _searchJob?.cancel()
    _searchJob = viewModelScope.launch {
      val timezones = timezonesRepository.filter(query, _locale)
      _result.update { timezones }
    }
  }
}

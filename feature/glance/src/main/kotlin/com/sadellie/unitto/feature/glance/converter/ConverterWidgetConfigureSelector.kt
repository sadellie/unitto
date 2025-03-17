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

package com.sadellie.unitto.feature.glance.converter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.data.converter.UnitStats
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.SearchBar
import com.sadellie.unitto.core.ui.textfield.observe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
internal fun ConverterWidgetConfigureSelectorRoute(
  navigateUp: () -> Unit,
  onClick: (unitId: String) -> Unit,
) {
  val viewModel = hiltViewModel<ConverterWidgetConfigureSelectorViewModel>()
  LaunchedEffect(Unit) { viewModel.observeFilter() }

  val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
  if (uiState == null) {
    EmptyScreen()
  } else {
    ConverterWidgetConfigureSelectorScreen(
      uiState = uiState,
      navigateUp = navigateUp,
      onClick = onClick,
    )
  }
}

@Composable
private fun ConverterWidgetConfigureSelectorScreen(
  uiState: ConverterWidgetConfigureSelectorUIState,
  navigateUp: () -> Unit,
  onClick: (unitId: String) -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      SearchBar(state = uiState.query, navigateUp = navigateUp, scrollBehavior = scrollBehavior)
    },
  ) { paddingValues ->
    if (uiState.result == null) {
      EmptyScreen()
    } else {
      ConverterWidgetUnitList(
        modifier = Modifier.padding(paddingValues),
        units = uiState.result,
        onClick = onClick,
      )
    }
  }
}

@Composable
private fun ConverterWidgetUnitList(
  modifier: Modifier,
  units: Map<UnitGroup, List<UnitSearchResultItem>>,
  onClick: (unitId: String) -> Unit,
) {
  LazyColumn(modifier = modifier) {
    units.forEach { (group, units) ->
      item(group.name, ContentType.HEADER) {
        Text(
          text = stringResource(group.res),
          color = MaterialTheme.colorScheme.primary,
          style = MaterialTheme.typography.titleSmall,
          modifier = Modifier.fillMaxWidth().padding(Sizes.small),
        )
      }

      items(items = units, key = { it.basicUnit.id }, contentType = { ContentType.ITEM }) { unit ->
        Text(
          text = stringResource(unit.basicUnit.displayName),
          style = MaterialTheme.typography.bodyMedium,
          modifier =
            Modifier.clickable { onClick(unit.basicUnit.id) }.fillMaxWidth().padding(Sizes.medium),
        )
      }
    }
  }
}

private enum class ContentType {
  HEADER,
  ITEM,
}

internal data class ConverterWidgetConfigureSelectorUIState(
  val query: TextFieldState,
  val result: Map<UnitGroup, List<UnitSearchResultItem>>?,
)

@HiltViewModel
internal class ConverterWidgetConfigureSelectorViewModel
@Inject
constructor(private val unitsRepo: UnitsRepository, savedStateHandle: SavedStateHandle) :
  ViewModel() {
  private val args = savedStateHandle.toRoute<ConverterWidgetConfigureSelectorRoute>()
  private var job: Job? = null
  private val _query = TextFieldState()
  private val _searchResult = MutableStateFlow<Map<UnitGroup, List<UnitSearchResultItem>>?>(null)
  val uiState =
    _searchResult
      .mapLatest { searchResult ->
        if (searchResult == null) return@mapLatest null
        ConverterWidgetConfigureSelectorUIState(query = _query, result = searchResult)
      }
      .stateIn(viewModelScope, null)

  suspend fun observeFilter() {
    _query.observe().collectLatest { query ->
      job?.cancel()
      job =
        viewModelScope.launch(Dispatchers.Default) {
          val groups =
            if (args.unitFromId == null) {
              UnitGroup.entries.toList()
            } else {
              val unitFrom = unitsRepo.getById(args.unitFromId)
              listOf(unitFrom.group)
            }

          val result =
            unitsRepo
              .filter(
                query = query.toString(),
                unitGroups = groups,
                favoritesOnly = false,
                sorting = UnitsListSorting.USAGE,
              )
              .groupBy { it.basicUnit.group }

          _searchResult.update { result }
        }
    }
  }
}

@Preview
@Composable
private fun PreviewConverterWidgetSelectFrom() {
  ConverterWidgetConfigureSelectorScreen(
    uiState =
      ConverterWidgetConfigureSelectorUIState(
        query = rememberTextFieldState(),
        result =
          listOf(
              NormalUnit(
                UnitID.meter,
                BigDecimal("2"),
                UnitGroup.LENGTH,
                R.string.unit_meter,
                R.string.unit_meter_short,
              ),
              NormalUnit(
                UnitID.kilometer,
                BigDecimal("2"),
                UnitGroup.LENGTH,
                R.string.unit_kilometer,
                R.string.unit_kilometer_short,
              ),
            )
            .groupBy { it.group }
            .mapValues { (_, units) ->
              units.map { unit ->
                UnitSearchResultItem(
                  basicUnit = unit,
                  stats = UnitStats(unit.id),
                  conversion = null,
                )
              }
            },
      ),
    navigateUp = {},
    onClick = {},
  )
}

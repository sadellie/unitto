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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.database.ConverterWidgetUnitPairDao
import com.sadellie.unitto.core.database.ConverterWidgetUnitPairEntity
import com.sadellie.unitto.core.designsystem.icons.symbols.Add
import com.sadellie.unitto.core.designsystem.icons.symbols.Check
import com.sadellie.unitto.core.designsystem.icons.symbols.ChevronRight
import com.sadellie.unitto.core.designsystem.icons.symbols.Close
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal

@Composable
internal fun ConverterWidgetConfigureRoute(
  viewModel: ConverterWidgetConfigureViewModel,
  navigateToSelectFrom: () -> Unit,
  onDone: () -> Unit,
) {
  val uiState = viewModel.mainUiState.collectAsStateWithLifecycle().value

  if (uiState == null) {
    EmptyScreen()
  } else {
    MainScreen(
      uiState = uiState,
      navigateToSelectFrom = navigateToSelectFrom,
      onDone = onDone,
      onRemove = viewModel::removePair,
    )
  }
}

@Composable
private fun MainScreen(
  uiState: ConverterWidgetConfigureMainUIState,
  navigateToSelectFrom: () -> Unit,
  onDone: () -> Unit,
  onRemove: (index: Int) -> Unit,
) {
  val isNotSubmitting =
    remember(uiState.submitProgress) {
      uiState.submitProgress == ConverterWidgetConfigureSubmitProgress.NONE
    }

  val enableSubmitButton =
    remember(uiState, isNotSubmitting) {
      val isListNotEmpty = uiState.selectedUnits.isNotEmpty()
      isListNotEmpty && isNotSubmitting
    }

  ScaffoldWithTopBar(
    title = { Text(stringResource(R.string.converter_widget_configure_select_pairs)) },
    navigationIcon = {},
    actions = {
      IconButton(onDone, enabled = enableSubmitButton) {
        Icon(Symbols.Check, stringResource(R.string.common_confirm))
      }
    },
    floatingActionButton = {
      FloatingActionButton(onClick = navigateToSelectFrom) {
        Icon(imageVector = Symbols.Add, contentDescription = stringResource(R.string.common_add))
      }
    },
  ) { paddingValues ->
    if (uiState.selectedUnits.isEmpty()) {
      Box(
        modifier = Modifier.padding(paddingValues).fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        Text(text = stringResource(R.string.converter_widget_configure_list_placeholder))
      }

      return@ScaffoldWithTopBar
    }

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
      itemsIndexed(uiState.selectedUnits) { index, selectedUnit ->
        ListItem(
          headlineContent = {
            Row(modifier = Modifier.height(IntrinsicSize.Max)) {
              Text(text = stringResource(selectedUnit.from.displayName))
              Icon(Symbols.ChevronRight, null)
              Text(text = stringResource(selectedUnit.to.displayName))
            }
          },
          trailingContent = {
            IconButton(onClick = { onRemove(index) }, enabled = isNotSubmitting) {
              Icon(
                imageVector = Symbols.Close,
                contentDescription = stringResource(R.string.common_delete),
              )
            }
          },
        )
      }
    }
  }
}

internal data class ConverterWidgetConfigureMainUIState(
  val selectedUnits: List<SelectedUnitPair>,
  val submitProgress: ConverterWidgetConfigureSubmitProgress,
)

@HiltViewModel(assistedFactory = ConverterWidgetConfigureViewModel.Factory::class)
internal class ConverterWidgetConfigureViewModel
@AssistedInject
constructor(
  private val converterWidgetUnitPairDao: ConverterWidgetUnitPairDao,
  private val unitsRepo: UnitsRepository,
  @Assisted private val appWidgetId: Int,
) : ViewModel() {
  @AssistedFactory
  interface Factory {
    fun create(appWidgetId: Int): ConverterWidgetConfigureViewModel
  }

  private val _inMemoryPairs = MutableStateFlow<List<SelectedUnitPair>?>(null)
  val submitProgress = MutableStateFlow(ConverterWidgetConfigureSubmitProgress.NONE)

  init {
    viewModelScope.launch(Dispatchers.IO) {
      val entities = converterWidgetUnitPairDao.getByAppWidgetId(appWidgetId).first()
      val domain = entityListToDomainList(unitsRepo, entities)
      _inMemoryPairs.update { domain }
    }
  }

  val mainUiState =
    combine(_inMemoryPairs, submitProgress) { selectedUnitPairs, submitProgressValue ->
        if (selectedUnitPairs == null) return@combine null
        ConverterWidgetConfigureMainUIState(selectedUnitPairs, submitProgressValue)
      }
      .stateIn(viewModelScope, null)

  fun addNewPair(unitFromId: String, unitToId: String) {
    viewModelScope.launch {
      val unitFrom = unitsRepo.getById(unitFromId)
      val unitTo = unitsRepo.getById(unitToId)
      _inMemoryPairs.update { unitPairs ->
        if (unitPairs == null) return@update null
        val maxPosition = unitPairs.maxOfOrNull(SelectedUnitPair::order) ?: 0
        unitPairs + SelectedUnitPair(maxPosition + 1, unitFrom, unitTo)
      }
    }
  }

  fun removePair(index: Int) {
    _inMemoryPairs.update { unitPairs ->
      if (unitPairs == null) return@update null
      val mutableList = unitPairs.toMutableList()
      mutableList.removeAt(index)
      mutableList
    }
  }

  fun submitNewPairs() {
    // Already submitting
    if (submitProgress.value != ConverterWidgetConfigureSubmitProgress.NONE) return
    viewModelScope.launch(Dispatchers.IO) {
      submitProgress.update { ConverterWidgetConfigureSubmitProgress.ACTIVE }
      val entities =
        _inMemoryPairs.value?.mapIndexed { index, pair ->
          ConverterWidgetUnitPairEntity(
            entityId = 0,
            appWidgetId = appWidgetId,
            unitFromId = pair.from.id,
            unitToId = pair.to.id,
            position = index, // index is more reliable than position (pair property)
          )
        }

      if (entities == null) {
        submitProgress.update { ConverterWidgetConfigureSubmitProgress.NONE }
      } else {
        converterWidgetUnitPairDao.deleteByAppWidgetId(appWidgetId)
        converterWidgetUnitPairDao.insert(entities)
        submitProgress.update { ConverterWidgetConfigureSubmitProgress.FINISHED }
      }
    }
  }
}

internal enum class ConverterWidgetConfigureSubmitProgress {
  NONE,
  ACTIVE,
  FINISHED,
}

@Preview
@Composable
private fun PreviewMainScreen() {
  MainScreen(
    uiState =
      ConverterWidgetConfigureMainUIState(
        selectedUnits =
          listOf(
            SelectedUnitPair(
              order = 0,
              from =
                NormalUnit(
                  UnitID.meter,
                  BigDecimal("2"),
                  UnitGroup.LENGTH,
                  R.string.unit_meter,
                  R.string.unit_meter_short,
                ),
              to =
                NormalUnit(
                  UnitID.kilometer,
                  BigDecimal("2"),
                  UnitGroup.LENGTH,
                  R.string.unit_kilometer,
                  R.string.unit_kilometer_short,
                ),
            )
          ),
        submitProgress = ConverterWidgetConfigureSubmitProgress.NONE,
      ),
    navigateToSelectFrom = {},
    onDone = {},
    onRemove = {},
  )
}

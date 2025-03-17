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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.designsystem.icons.symbols.Add
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItem
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.feature.glance.R
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
internal fun ConverterWidgetConfigureCreateNewRoute(
  viewModel: ConverterWidgetConfigureCreateNewViewModel,
  navigateUp: () -> Unit,
  navigateToSelectFrom: () -> Unit,
  navigateToSelectTo: (unitFromId: String) -> Unit,
  onAdd: (unitFromId: String, unitToId: String) -> Unit,
) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
  if (uiState == null) {
    EmptyScreen()
  } else {
    ConverterWidgetConfigureCreateNewScreen(
      uiState = uiState,
      navigateUp = navigateUp,
      navigateToSelectFrom = navigateToSelectFrom,
      navigateToSelectTo = navigateToSelectTo,
      onAdd = onAdd,
    )
  }
}

@Composable
private fun ConverterWidgetConfigureCreateNewScreen(
  uiState: ConverterWidgetConfigureCreateNewUIState,
  navigateUp: () -> Unit,
  navigateToSelectFrom: () -> Unit,
  navigateToSelectTo: (unitFromId: String) -> Unit,
  onAdd: (unitFromId: String, unitToId: String) -> Unit,
) {
  val isAddEnabled =
    remember(uiState.unitFrom, uiState.unitTo) {
      uiState.unitFrom != null && uiState.unitTo != null
    }

  val isUnitFromSelected = remember(uiState.unitFrom) { uiState.unitFrom != null }

  ScaffoldWithLargeTopBar(
    title = stringResource(R.string.converter_widget_configure_create_pair),
    navigationIcon = { NavigateUpButton(navigateUp) },
    actions = {
      IconButton(
        onClick = {
          if (uiState.unitFrom == null) return@IconButton
          if (uiState.unitTo == null) return@IconButton
          onAdd(uiState.unitFrom.id, uiState.unitTo.id)
        },
        enabled = isAddEnabled,
      ) {
        Icon(imageVector = Symbols.Add, contentDescription = stringResource(R.string.common_add))
      }
    },
  ) { paddingValues ->
    Column(modifier = Modifier.padding(paddingValues)) {
      ListItem(
        modifier = Modifier.clickable { navigateToSelectFrom() },
        headlineContent = { Text(stringResource(R.string.converter_widget_configure_left_unit)) },
        supportingContent = {
          Text(
            stringResource(
              uiState.unitFrom?.displayName ?: R.string.converter_widget_configure_not_selected
            )
          )
        },
      )

      AnimatedVisibility(
        visible = isUnitFromSelected,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        ListItem(
          modifier =
            Modifier.clickable(enabled = isUnitFromSelected) {
              if (uiState.unitFrom != null) navigateToSelectTo(uiState.unitFrom.id)
            },
          headlineContent = {
            Text(stringResource(R.string.converter_widget_configure_right_unit))
          },
          supportingContent = {
            Text(
              stringResource(
                uiState.unitTo?.displayName ?: R.string.converter_widget_configure_not_selected
              )
            )
          },
        )
      }
    }
  }
}

internal data class ConverterWidgetConfigureCreateNewUIState(
  val unitFrom: BasicUnit?,
  val unitTo: BasicUnit?,
)

@HiltViewModel
internal class ConverterWidgetConfigureCreateNewViewModel
@Inject
constructor(private val unitsRepo: UnitsRepository) : ViewModel() {
  private val _unitFrom = MutableStateFlow<BasicUnit?>(null)
  private val _unitTo = MutableStateFlow<BasicUnit?>(null)

  val uiState =
    combine(_unitFrom, _unitTo) { unitFrom, unitTo ->
        ConverterWidgetConfigureCreateNewUIState(unitFrom = unitFrom, unitTo = unitTo)
      }
      .stateIn(viewModelScope, null)

  fun selectUnitFromById(id: String) {
    viewModelScope.launch {
      val unitFrom = unitsRepo.getById(id)
      if (unitFrom.group != _unitTo.value?.group) {
        _unitTo.update { null }
      }
      _unitFrom.update { unitFrom }
    }
  }

  fun selectUnitToById(id: String) {
    viewModelScope.launch {
      val unitTo = unitsRepo.getById(id)
      if (unitTo.group != _unitFrom.value?.group) return@launch
      _unitTo.update { unitTo }
    }
  }
}

@Preview
@Composable
private fun PreviewConverterWidgetConfigureCreateNewScreen() {
  ConverterWidgetConfigureCreateNewScreen(
    uiState =
      ConverterWidgetConfigureCreateNewUIState(
        unitFrom =
          NormalUnit(
            UnitID.meter,
            BigDecimal("2"),
            UnitGroup.LENGTH,
            com.sadellie.unitto.core.common.R.string.unit_meter,
            com.sadellie.unitto.core.common.R.string.unit_meter_short,
          ),
        unitTo =
          NormalUnit(
            UnitID.kilometer,
            BigDecimal("2"),
            UnitGroup.LENGTH,
            com.sadellie.unitto.core.common.R.string.unit_kilometer,
            com.sadellie.unitto.core.common.R.string.unit_kilometer_short,
          ),
      ),
    navigateUp = {},
    navigateToSelectFrom = {},
    navigateToSelectTo = {},
    onAdd = { _, _ -> },
  )
}

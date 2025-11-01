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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.stateIn
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.designsystem.icons.symbols.Add
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_add
import unitto.core.common.generated.resources.converter_widget_configure_create_pair
import unitto.core.common.generated.resources.converter_widget_configure_left_unit
import unitto.core.common.generated.resources.converter_widget_configure_not_selected
import unitto.core.common.generated.resources.converter_widget_configure_right_unit
import unitto.core.common.generated.resources.unit_kilometer
import unitto.core.common.generated.resources.unit_kilometer_short
import unitto.core.common.generated.resources.unit_meter
import unitto.core.common.generated.resources.unit_meter_short

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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
    title = stringResource(Res.string.converter_widget_configure_create_pair),
    navigationIcon = { NavigateUpButton(navigateUp) },
    actions = {
      FilledIconButton(
        onClick = onClick@{
            if (uiState.unitFrom == null || uiState.unitTo == null) return@onClick
            onAdd(uiState.unitFrom.id, uiState.unitTo.id)
          },
        enabled = isAddEnabled,
        shapes = IconButtonDefaults.shapes(),
        modifier =
          Modifier.size(
            IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Wide)
          ),
      ) {
        Icon(
          imageVector = Symbols.Add,
          contentDescription = stringResource(Res.string.common_add),
          modifier = Modifier.size(IconButtonDefaults.smallIconSize),
        )
      }
    },
  ) { paddingValues ->
    Column(
      modifier =
        Modifier.verticalScroll(rememberScrollState())
          .padding(paddingValues)
          .padding(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
    ) {
      ListItemExpressive(
        onClick = { navigateToSelectFrom() },
        shape =
          if (isUnitFromSelected) ListItemExpressiveDefaults.firstShape
          else ListItemExpressiveDefaults.singleShape,
        headlineContent = { Text(stringResource(Res.string.converter_widget_configure_left_unit)) },
        supportingContent = {
          Text(
            stringResource(
              uiState.unitFrom?.displayName ?: Res.string.converter_widget_configure_not_selected
            )
          )
        },
      )

      AnimatedVisibility(
        visible = isUnitFromSelected,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        ListItemExpressive(
          onClick = { if (uiState.unitFrom != null) navigateToSelectTo(uiState.unitFrom.id) },
          shape = ListItemExpressiveDefaults.lastShape,
          headlineContent = {
            Text(stringResource(Res.string.converter_widget_configure_right_unit))
          },
          supportingContent = {
            Text(
              stringResource(
                uiState.unitTo?.displayName ?: Res.string.converter_widget_configure_not_selected
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

internal class ConverterWidgetConfigureCreateNewViewModel(private val unitsRepo: UnitsRepository) :
  ViewModel() {
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
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_meter,
            Res.string.unit_meter_short,
          ),
        unitTo =
          NormalUnit(
            UnitID.kilometer,
            KBigDecimal("2"),
            UnitGroup.LENGTH,
            Res.string.unit_kilometer,
            Res.string.unit_kilometer_short,
          ),
      ),
    navigateUp = {},
    navigateToSelectFrom = {},
    navigateToSelectTo = {},
    onAdd = { _, _ -> },
  )
}

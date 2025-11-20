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

package com.sadellie.unitto.feature.converter

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.common.toFormattedString
import com.sadellie.unitto.core.data.converter.ConverterResult
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.data.converter.UnitStats
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.SearchBar
import com.sadellie.unitto.core.ui.textfield.formatExpression
import com.sadellie.unitto.feature.converter.components.FavoritesButton
import com.sadellie.unitto.feature.converter.components.UnitsList
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_foot
import unitto.core.common.generated.resources.unit_foot_short
import unitto.core.common.generated.resources.unit_inch
import unitto.core.common.generated.resources.unit_inch_short
import unitto.core.common.generated.resources.unit_kilometer
import unitto.core.common.generated.resources.unit_kilometer_short
import unitto.core.common.generated.resources.unit_meter
import unitto.core.common.generated.resources.unit_meter_short
import unitto.core.common.generated.resources.unit_mile
import unitto.core.common.generated.resources.unit_mile_short
import unitto.core.common.generated.resources.unit_nautical_mile
import unitto.core.common.generated.resources.unit_nautical_mile_short
import unitto.core.common.generated.resources.unit_yard
import unitto.core.common.generated.resources.unit_yard_short

@Composable
internal fun UnitToSelectorRoute(
  unitSelectorViewModel: UnitToSelectorViewModel,
  updateUnitTo: (String) -> Unit,
  navigateUp: () -> Unit,
  navigateToUnitGroups: () -> Unit,
) {
  LaunchedEffect(Unit) { unitSelectorViewModel.observeSearchFilters() }

  when (val uiState = unitSelectorViewModel.unitToUIState.collectAsStateWithLifecycleKMP().value) {
    is UnitSelectorUIState.UnitTo ->
      UnitToSelectorScreen(
        uiState = uiState,
        toggleFavoritesOnly = unitSelectorViewModel::updateShowFavoritesOnly,
        updateUnitTo = updateUnitTo,
        favoriteUnit = unitSelectorViewModel::favoriteUnit,
        navigateUp = navigateUp,
        navigateToUnitGroups = navigateToUnitGroups,
      )
    else -> EmptyScreen()
  }
}

@Composable
private fun UnitToSelectorScreen(
  uiState: UnitSelectorUIState.UnitTo,
  toggleFavoritesOnly: (Boolean) -> Unit,
  updateUnitTo: (String) -> Unit,
  favoriteUnit: (UnitSearchResultItem) -> Unit,
  navigateUp: () -> Unit,
  navigateToUnitGroups: () -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    containerColor = MaterialTheme.colorScheme.surfaceContainer,
    topBar = {
      SearchBar(
        state = uiState.query,
        navigateUp = navigateUp,
        trailingIcon = {
          FavoritesButton(uiState.showFavoritesOnly) {
            toggleFavoritesOnly(!uiState.showFavoritesOnly)
          }
        },
        scrollBehavior = scrollBehavior,
      )
    },
  ) { paddingValues ->
    if (uiState.units != null) {
      UnitsList(
        modifier = Modifier,
        searchResult = uiState.units,
        navigateToUnitGroups = navigateToUnitGroups,
        selectedUnitId = uiState.unitTo.id,
        supportLabel = {
          val shortName = stringResource(it.basicUnit.shortName)
          remember(uiState.scale, uiState.outputFormat, uiState.formatterSymbols) {
            unitToSupportLabel(
              shortName = shortName,
              unitSearchResultItem = it,
              scale = uiState.scale,
              outputFormat = uiState.outputFormat,
              formatterSymbols = uiState.formatterSymbols,
            )
          }
        },
        onClick = {
          uiState.query.clearText()
          updateUnitTo(it.basicUnit.id)
          navigateUp()
        },
        favoriteUnit = { favoriteUnit(it) },
        contentPadding = paddingValues,
      )
    }
  }
}

private fun unitToSupportLabel(
  shortName: String,
  unitSearchResultItem: UnitSearchResultItem,
  scale: Int,
  outputFormat: Int,
  formatterSymbols: FormatterSymbols,
): String {
  return when (val conversion = unitSearchResultItem.conversion) {
    is ConverterResult.Default -> {
      val formattedConversion =
        conversion.value.toFormattedString(scale, outputFormat).formatExpression(formatterSymbols)

      "$formattedConversion $shortName"
    }
    is ConverterResult.NumberBase -> "${conversion.value} $shortName"
    else -> shortName
  }
}

@Preview
@Composable
private fun UnitToSelectorPreview() {
  val units: Map<UnitGroup, List<UnitSearchResultItem>> =
    mapOf(
      UnitGroup.LENGTH to
        listOf(
            NormalUnit(
              UnitID.meter,
              KBigDecimal("1000000000000000000"),
              UnitGroup.LENGTH,
              Res.string.unit_meter,
              Res.string.unit_meter_short,
            ),
            NormalUnit(
              UnitID.kilometer,
              KBigDecimal("1000000000000000000000"),
              UnitGroup.LENGTH,
              Res.string.unit_kilometer,
              Res.string.unit_kilometer_short,
            ),
            NormalUnit(
              UnitID.nautical_mile,
              KBigDecimal("1852000000000000000000"),
              UnitGroup.LENGTH,
              Res.string.unit_nautical_mile,
              Res.string.unit_nautical_mile_short,
            ),
            NormalUnit(
              UnitID.inch,
              KBigDecimal("25400000000000000"),
              UnitGroup.LENGTH,
              Res.string.unit_inch,
              Res.string.unit_inch_short,
            ),
            NormalUnit(
              UnitID.foot,
              KBigDecimal("304800000000000000"),
              UnitGroup.LENGTH,
              Res.string.unit_foot,
              Res.string.unit_foot_short,
            ),
            NormalUnit(
              UnitID.yard,
              KBigDecimal("914400000000000000"),
              UnitGroup.LENGTH,
              Res.string.unit_yard,
              Res.string.unit_yard_short,
            ),
            NormalUnit(
              UnitID.mile,
              KBigDecimal("1609344000000000000000"),
              UnitGroup.LENGTH,
              Res.string.unit_mile,
              Res.string.unit_mile_short,
            ),
          )
          .map { UnitSearchResultItem(it, UnitStats(it.id), null) }
    )

  UnitToSelectorScreen(
    uiState =
      UnitSelectorUIState.UnitTo(
        unitFrom = units.values.first().first().basicUnit,
        unitTo = units.values.first().first().basicUnit,
        query = TextFieldState("test"),
        units = units,
        showFavoritesOnly = false,
        sorting = UnitsListSorting.USAGE,
        scale = 3,
        outputFormat = OutputFormat.PLAIN,
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
      ),
    toggleFavoritesOnly = {},
    updateUnitTo = {},
    favoriteUnit = {},
    navigateUp = {},
    navigateToUnitGroups = {},
  )
}

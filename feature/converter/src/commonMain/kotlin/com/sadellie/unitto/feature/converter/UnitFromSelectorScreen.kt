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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.data.converter.UnitID
import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.data.converter.UnitStats
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.SearchBar
import com.sadellie.unitto.feature.converter.components.ChipsRow
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
internal fun UnitFromSelectorRoute(
  unitSelectorViewModel: UnitFromSelectorViewModel,
  updateUnitFrom: (String) -> Unit,
  navigateUp: () -> Unit,
  navigateToUnitGroups: () -> Unit,
) {
  LaunchedEffect(Unit) { unitSelectorViewModel.observeSearchFilters() }

  when (
    val uiState = unitSelectorViewModel.unitFromUIState.collectAsStateWithLifecycleKMP().value
  ) {
    is UnitSelectorUIState.UnitFrom ->
      UnitFromSelectorScreen(
        uiState = uiState,
        toggleFavoritesOnly = unitSelectorViewModel::updateShowFavoritesOnly,
        updateUnitFrom = updateUnitFrom,
        updateUnitGroup = unitSelectorViewModel::updateSelectedUnitGroup,
        favoriteUnit = unitSelectorViewModel::favoriteUnit,
        navigateUp = navigateUp,
        navigateToUnitGroups = navigateToUnitGroups,
      )
    else -> EmptyScreen()
  }
}

@Composable
private fun UnitFromSelectorScreen(
  uiState: UnitSelectorUIState.UnitFrom,
  toggleFavoritesOnly: (Boolean) -> Unit,
  updateUnitFrom: (String) -> Unit,
  updateUnitGroup: (UnitGroup?) -> Unit,
  favoriteUnit: (UnitSearchResultItem) -> Unit,
  navigateUp: () -> Unit,
  navigateToUnitGroups: () -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    containerColor = MaterialTheme.colorScheme.surfaceContainer,
    topBar = {
      Column(Modifier.background(MaterialTheme.colorScheme.surfaceContainer)) {
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

        ChipsRow(
          modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp).fillMaxWidth(),
          chosenUnitGroup = uiState.selectedUnitGroup,
          items = uiState.shownUnitGroups,
          selectAction = updateUnitGroup,
          navigateToSettingsAction = navigateToUnitGroups,
        )
      }
    },
  ) { paddingValues ->
    if (uiState.units != null) {
      UnitsList(
        modifier = Modifier,
        searchResult = uiState.units,
        navigateToUnitGroups = navigateToUnitGroups,
        selectedUnitId = uiState.unitFromId,
        supportLabel = { stringResource(it.basicUnit.shortName) },
        onClick = {
          uiState.query.clearText()
          updateUnitFrom(it.basicUnit.id)
          navigateUp()
        },
        favoriteUnit = { favoriteUnit(it) },
        contentPadding = paddingValues,
      )
    }
  }
}

@Preview
@Composable
private fun UnitFromSelectorScreenPreview() {
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

  UnitFromSelectorScreen(
    uiState =
      UnitSelectorUIState.UnitFrom(
        unitFromId = UnitID.kilometer,
        query = TextFieldState("test"),
        units = units,
        selectedUnitGroup = UnitGroup.SPEED,
        shownUnitGroups = UnitGroup.entries,
        showFavoritesOnly = false,
        sorting = UnitsListSorting.USAGE,
      ),
    toggleFavoritesOnly = {},
    updateUnitFrom = {},
    updateUnitGroup = {},
    favoriteUnit = {},
    navigateUp = {},
    navigateToUnitGroups = {},
  )
}

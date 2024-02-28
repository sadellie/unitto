/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.SearchBar
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.converter.UnitSearchResultItem
import com.sadellie.unitto.data.database.UnitsEntity
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.converter.UnitsListSorting
import com.sadellie.unitto.data.model.converter.unit.NormalUnit
import com.sadellie.unitto.feature.converter.components.ChipsRow
import com.sadellie.unitto.feature.converter.components.FavoritesButton
import com.sadellie.unitto.feature.converter.components.UnitsList
import java.math.BigDecimal

@Composable
internal fun UnitFromSelectorRoute(
    unitSelectorViewModel: UnitFromSelectorViewModel,
    converterViewModel: ConverterViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    when (
        val uiState = unitSelectorViewModel.unitFromUIState.collectAsStateWithLifecycle().value
    ) {
        is UnitSelectorUIState.UnitFrom -> UnitFromSelectorScreen(
            uiState = uiState,
            onQueryChange = unitSelectorViewModel::updateSelectorQuery,
            toggleFavoritesOnly = unitSelectorViewModel::updateShowFavoritesOnly,
            updateUnitFrom = converterViewModel::updateUnitFromId,
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
    onQueryChange: (TextFieldValue) -> Unit,
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
        topBar = {
            Column(
                Modifier.background(MaterialTheme.colorScheme.surface),
            ) {
                SearchBar(
                    query = uiState.query,
                    onQueryChange = onQueryChange,
                    navigateUp = navigateUp,
                    trailingIcon = {
                        FavoritesButton(uiState.showFavoritesOnly) {
                            toggleFavoritesOnly(!uiState.showFavoritesOnly)
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )

                ChipsRow(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                        .fillMaxWidth(),
                    chosenUnitGroup = uiState.selectedUnitGroup,
                    items = uiState.shownUnitGroups,
                    selectAction = updateUnitGroup,
                    navigateToSettingsAction = navigateToUnitGroups,
                )
            }
        },
    ) { paddingValues ->
        val resources = LocalContext.current.resources

        UnitsList(
            modifier = Modifier.padding(paddingValues),
            searchResult = uiState.units,
            navigateToUnitGroups = navigateToUnitGroups,
            currentUnitId = uiState.unitFromId,
            supportLabel = { resources.getString(it.basicUnit.shortName) },
            onClick = {
                onQueryChange(TextFieldValue())
                updateUnitFrom(it.basicUnit.id)
                navigateUp()
            },
            favoriteUnit = { favoriteUnit(it) },
        )
    }
}

@Preview
@Composable
private fun UnitFromSelectorScreenPreview() {
    val units: Map<UnitGroup, List<UnitSearchResultItem>> = mapOf(
        UnitGroup.LENGTH to listOf(
            NormalUnit(UnitID.meter, BigDecimal("1000000000000000000"), UnitGroup.LENGTH, R.string.unit_meter, R.string.unit_meter_short),
            NormalUnit(UnitID.kilometer, BigDecimal("1000000000000000000000"), UnitGroup.LENGTH, R.string.unit_kilometer, R.string.unit_kilometer_short),
            NormalUnit(UnitID.nautical_mile, BigDecimal("1852000000000000000000"), UnitGroup.LENGTH, R.string.unit_nautical_mile, R.string.unit_nautical_mile_short),
            NormalUnit(UnitID.inch, BigDecimal("25400000000000000"), UnitGroup.LENGTH, R.string.unit_inch, R.string.unit_inch_short),
            NormalUnit(UnitID.foot, BigDecimal("304800000000000000"), UnitGroup.LENGTH, R.string.unit_foot, R.string.unit_foot_short),
            NormalUnit(UnitID.yard, BigDecimal("914400000000000000"), UnitGroup.LENGTH, R.string.unit_yard, R.string.unit_yard_short),
            NormalUnit(UnitID.mile, BigDecimal("1609344000000000000000"), UnitGroup.LENGTH, R.string.unit_mile, R.string.unit_mile_short),
        )
            .map { UnitSearchResultItem(it, UnitsEntity(unitId = it.id), null) },
    )

    UnitFromSelectorScreen(
        uiState = UnitSelectorUIState.UnitFrom(
            unitFromId = UnitID.kilometer,
            query = TextFieldValue("test"),
            units = units,
            selectedUnitGroup = UnitGroup.SPEED,
            shownUnitGroups = UnitGroup.entries,
            showFavoritesOnly = false,
            sorting = UnitsListSorting.USAGE,
        ),
        onQueryChange = {},
        toggleFavoritesOnly = {},
        updateUnitFrom = {},
        updateUnitGroup = {},
        favoriteUnit = {},
        navigateUp = {},
        navigateToUnitGroups = {},
    )
}

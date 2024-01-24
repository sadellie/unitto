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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.SearchPlaceholder
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.SearchBar
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.feature.converter.components.BasicUnitListItem
import com.sadellie.unitto.feature.converter.components.ChipsRow
import com.sadellie.unitto.feature.converter.components.FavoritesButton
import com.sadellie.unitto.feature.converter.components.UnitGroupHeader
import java.math.BigDecimal

@Composable
internal fun LeftSideRoute(
    viewModel: ConverterViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    when (
        val uiState = viewModel.leftSideUIState.collectAsStateWithLifecycle().value
    ) {
        is LeftSideUIState.Loading -> EmptyScreen()
        is LeftSideUIState.Ready -> LeftSideScreen(
            uiState = uiState,
            onQueryChange = viewModel::queryChangeLeft,
            toggleFavoritesOnly = viewModel::favoritesOnlyChange,
            updateUnitFrom = viewModel::updateUnitFrom,
            updateUnitGroup = viewModel::updateUnitGroupLeft,
            favoriteUnit = viewModel::favoriteUnit,
            navigateUp = navigateUp,
            navigateToUnitGroups = navigateToUnitGroups,
        )
    }
}

@Composable
private fun LeftSideScreen(
    uiState: LeftSideUIState.Ready,
    onQueryChange: (TextFieldValue) -> Unit,
    toggleFavoritesOnly: (Boolean) -> Unit,
    updateUnitFrom: (AbstractUnit) -> Unit,
    updateUnitGroup: (UnitGroup?) -> Unit,
    favoriteUnit: (AbstractUnit) -> Unit,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val chipsRowLazyListState = rememberLazyListState()

    LaunchedEffect(uiState.unitFrom, uiState.shownUnitGroups) {
        updateUnitGroup(uiState.unitFrom.group)

        kotlin.runCatching {
            val groupToSelect = uiState.shownUnitGroups.indexOf(uiState.unitFrom.group)
            if (groupToSelect > -1) {
                chipsRowLazyListState.scrollToItem(groupToSelect)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                SearchBar(
                    query = uiState.query,
                    onQueryChange = onQueryChange,
                    navigateUp = navigateUp,
                    trailingIcon = {
                        FavoritesButton(uiState.favorites) {
                            toggleFavoritesOnly(!uiState.favorites)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )

                ChipsRow(
                    chosenUnitGroup = uiState.unitGroup,
                    items = uiState.shownUnitGroups,
                    selectAction = updateUnitGroup,
                    lazyListState = chipsRowLazyListState,
                    navigateToSettingsAction = navigateToUnitGroups
                )
            }
        }
    ) { paddingValues ->
        Crossfade(
            targetState = uiState.units.isNotEmpty(),
            modifier = Modifier.padding(paddingValues),
            label = "Units list"
        ) { hasUnits ->
            when (hasUnits) {
                true -> LazyColumn(Modifier.fillMaxSize()) {
                    uiState.units.forEach { (unitGroup, units) ->
                        item(unitGroup.name) {
                            UnitGroupHeader(Modifier.animateItemPlacement(), unitGroup)
                        }

                        items(units, { it.id }) {
                            BasicUnitListItem(
                                modifier = Modifier.animateItemPlacement(),
                                name = stringResource(it.displayName),
                                supportLabel = stringResource(it.shortName),
                                isFavorite = it.isFavorite,
                                isSelected = it.id == uiState.unitFrom.id,
                                onClick = {
                                    onQueryChange(TextFieldValue())
                                    updateUnitFrom(it)
                                    navigateUp()
                                },
                                favoriteUnit = { favoriteUnit(it) }
                            )
                        }
                    }
                }

                false -> SearchPlaceholder(
                    onButtonClick = navigateToUnitGroups,
                    supportText = stringResource(R.string.converter_no_results_support),
                    buttonLabel = stringResource(R.string.open_settings_label)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LeftSideScreenPreview() {
    val units: Map<UnitGroup, List<AbstractUnit>> = mapOf(
        UnitGroup.LENGTH to listOf(
            NormalUnit(UnitID.meter, BigDecimal.valueOf(1.0E+18), UnitGroup.LENGTH, R.string.unit_meter, R.string.unit_meter_short),
            NormalUnit(UnitID.kilometer, BigDecimal.valueOf(1.0E+21), UnitGroup.LENGTH, R.string.unit_kilometer, R.string.unit_kilometer_short),
            NormalUnit(UnitID.nautical_mile, BigDecimal.valueOf(1.852E+21), UnitGroup.LENGTH, R.string.unit_nautical_mile, R.string.unit_nautical_mile_short),
            NormalUnit(UnitID.inch, BigDecimal.valueOf(25_400_000_000_000_000), UnitGroup.LENGTH, R.string.unit_inch, R.string.unit_inch_short),
            NormalUnit(UnitID.foot, BigDecimal.valueOf(304_800_000_000_002_200), UnitGroup.LENGTH, R.string.unit_foot, R.string.unit_foot_short),
            NormalUnit(UnitID.yard, BigDecimal.valueOf(914_400_000_000_006_400), UnitGroup.LENGTH, R.string.unit_yard, R.string.unit_yard_short),
            NormalUnit(UnitID.mile, BigDecimal.valueOf(1_609_344_000_000_010_500_000.0), UnitGroup.LENGTH, R.string.unit_mile, R.string.unit_mile_short),
        )
    )

    LeftSideScreen(
        uiState = LeftSideUIState.Ready(
            unitFrom = units.values.first().first(),
            units = units,
            query = TextFieldValue("test"),
            favorites = false,
            shownUnitGroups = listOf(UnitGroup.LENGTH, UnitGroup.TEMPERATURE, UnitGroup.CURRENCY),
            unitGroup = units.keys.toList().first(),
            sorting = UnitsListSorting.USAGE,
        ),
        onQueryChange = {},
        toggleFavoritesOnly = {},
        updateUnitFrom = {},
        updateUnitGroup = {},
        favoriteUnit = {},
        navigateUp = {},
        navigateToUnitGroups = {}
    )
}

/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.UnittoSearchBar
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.feature.converter.components.BasicUnitListItem
import com.sadellie.unitto.feature.converter.components.ChipsFlexRow
import com.sadellie.unitto.feature.converter.components.ChipsRow
import com.sadellie.unitto.feature.converter.components.FavoritesButton
import com.sadellie.unitto.feature.converter.components.SearchPlaceholder
import com.sadellie.unitto.feature.converter.components.UnitGroupHeader

@Composable
internal fun LeftSideRoute(
    viewModel: ConverterViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit
) {
    val uiState = viewModel.leftSideUIState.collectAsStateWithLifecycle()

    LeftSideScreen(
        uiState = uiState.value,
        onQueryChange = viewModel::queryChangeLeft,
        toggleFavoritesOnly = viewModel::favoritesOnlyChange,
        updateUnitFrom = viewModel::updateUnitFrom,
        updateUnitGroup = viewModel::updateUnitGroupLeft,
        favoriteUnit = viewModel::favoriteUnit,
        navigateUp = navigateUp,
        navigateToUnitGroups = navigateToUnitGroups,
    )
}

@Composable
private fun LeftSideScreen(
    uiState: LeftSideUIState,
    onQueryChange: (TextFieldValue) -> Unit,
    toggleFavoritesOnly: (Boolean) -> Unit,
    updateUnitFrom: (AbstractUnit) -> Unit,
    updateUnitGroup: (UnitGroup?) -> Unit,
    favoriteUnit: (AbstractUnit) -> Unit,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val elevatedColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    val needToTint by remember {
        derivedStateOf { scrollBehavior.state.overlappedFraction > 0.01f }
    }
    val chipsBackground = animateColorAsState(
        targetValue =  if (needToTint) elevatedColor else MaterialTheme.colorScheme.surface,
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "Chips background",
    )

    val chipsRowLazyListState = rememberLazyListState()

    LaunchedEffect(uiState.unitFrom, uiState.shownUnitGroups) {
        // TODO Scroll to initially selected unit group
        if (uiState.unitFrom == null) return@LaunchedEffect
        updateUnitGroup(uiState.unitFrom.group)

        val groupToSelect = uiState.shownUnitGroups.indexOf(uiState.unitFrom.group)
        if (groupToSelect > -1) {
            chipsRowLazyListState.animateScrollToItem(groupToSelect)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                Modifier.background(chipsBackground.value)
            ) {
                UnittoSearchBar(
                    query = uiState.query,
                    onQueryChange = onQueryChange,
                    navigateUp = navigateUp,
                    title = stringResource(R.string.units_screen_from),
                    placeholder = stringResource(R.string.search_bar_placeholder),
                    noSearchActions = {
                        FavoritesButton(uiState.favorites) {
                            toggleFavoritesOnly(!uiState.favorites)
                        }
                    }
                )

                if (uiState.verticalList) {
                    ChipsFlexRow(
                        chosenUnitGroup = uiState.unitGroup,
                        items = uiState.shownUnitGroups,
                        selectAction = updateUnitGroup,
                        lazyListState = chipsRowLazyListState,
                        navigateToSettingsAction = navigateToUnitGroups
                    )
                } else {
                    ChipsRow(
                        chosenUnitGroup = uiState.unitGroup,
                        items = uiState.shownUnitGroups,
                        selectAction = updateUnitGroup,
                        lazyListState = chipsRowLazyListState,
                        navigateToSettingsAction = navigateToUnitGroups
                    )
                }
            }
        }
    ) { paddingValues ->
        Crossfade(
            targetState = uiState.units?.isNotEmpty(),
            modifier = Modifier.padding(paddingValues),
            label = "Units list"
        ) { hasUnits ->
            when (hasUnits) {
                true -> LazyColumn(Modifier.fillMaxSize()) {
                    uiState.units?.forEach { (unitGroup, units) ->
                        item(unitGroup.name) {
                            UnitGroupHeader(Modifier.animateItemPlacement(), unitGroup)
                        }

                        items(units, { it.id }) {
                            BasicUnitListItem(
                                modifier = Modifier.animateItemPlacement(),
                                name = stringResource(it.displayName),
                                supportLabel = stringResource(it.shortName),
                                isFavorite = it.isFavorite,
                                isSelected = it.id == uiState.unitFrom?.id,
                                onClick = { updateUnitFrom(it); navigateUp() },
                                favoriteUnit = { favoriteUnit(it) }
                            )
                        }
                    }
                }
                false -> SearchPlaceholder(navigateToSettingsAction = navigateToUnitGroups)
                null -> {}
            }
        }
    }
}

@Preview
@Composable
private fun LeftSideScreenPreview() {
    LeftSideScreen(
        uiState = LeftSideUIState(),
        onQueryChange = {},
        toggleFavoritesOnly = {},
        updateUnitFrom = {},
        updateUnitGroup = {},
        favoriteUnit = {},
        navigateUp = {},
        navigateToUnitGroups = {}
    )
}

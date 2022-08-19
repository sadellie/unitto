/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.screens.second

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.screens.Formatter
import com.sadellie.unitto.screens.common.Header
import com.sadellie.unitto.screens.second.components.ChipsRow
import com.sadellie.unitto.screens.second.components.SearchBar
import com.sadellie.unitto.screens.second.components.SearchPlaceholder
import com.sadellie.unitto.screens.second.components.UnitListItem
import java.math.BigDecimal

/**
 * Left side screen. Unit to convert from.
 *
 * @param viewModel [SecondViewModel].
 * @param currentUnit Currently selected [AbstractUnit].
 * @param navigateUp Action to navigate up. Called when user click back button.
 * @param navigateToSettingsActtion Action to perform when clicking settings chip at the end.
 * @param selectAction Action to perform when user clicks on [UnitListItem].
 */
@Composable
fun LeftSideScreen(
    viewModel: SecondViewModel,
    currentUnit: AbstractUnit,
    navigateUp: () -> Unit,
    navigateToSettingsActtion: () -> Unit,
    selectAction: (AbstractUnit) -> Unit
) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val chipsRowLazyListState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    val elevatedColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    val needToTint by remember {
        derivedStateOf { scrollBehavior.state.overlappedFraction > 0.01f }
    }

    val chipsBackground = animateColorAsState(
        if (needToTint) elevatedColor else MaterialTheme.colorScheme.surface,
        tween(durationMillis = 500, easing = LinearOutSlowInEasing)
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column(
                Modifier.background(chipsBackground.value)
            ) {
                SearchBar(
                    title = stringResource(R.string.units_screen_from),
                    value = uiState.searchQuery,
                    onValueChange = {
                        viewModel.onSearchQueryChange(it)
                        viewModel.loadUnitsToShow(true)
                    },
                    favoritesOnly = uiState.favoritesOnly,
                    favoriteAction = {
                        viewModel.toggleFavoritesOnly()
                        viewModel.loadUnitsToShow(true)
                    },
                    navigateUpAction = navigateUp,
                    focusManager = focusManager,
                    scrollBehavior = scrollBehavior
                )
                ChipsRow(
                    chosenUnitGroup = viewModel.uiState.chosenUnitGroup,
                    items = uiState.shownUnitGroups,
                    selectAction = {
                        viewModel.toggleSelectedChip(it)
                        viewModel.loadUnitsToShow(true)
                    },
                    lazyListState = chipsRowLazyListState,
                    navigateToSettingsActtion = navigateToSettingsActtion
                )
            }
        }
    ) { paddingValues ->
        Crossfade(
            targetState = uiState.unitsToShow.isEmpty(),
            modifier = Modifier.padding(paddingValues)
        ) { noUnits ->
            if (noUnits) {
                SearchPlaceholder(navigateToSettingsActtion = navigateToSettingsActtion)
            } else {
                LazyColumn(Modifier.fillMaxSize()) {
                    uiState.unitsToShow.forEach { (unitGroup, listOfUnits) ->
                        item(unitGroup.name) {
                            UnitGroupHeader(Modifier.animateItemPlacement(), unitGroup)
                        }
                        items(listOfUnits, { it.unitId }) { unit ->
                            UnitListItem(
                                modifier = Modifier.animateItemPlacement(),
                                unit = unit,
                                isSelected = currentUnit == unit,
                                selectAction = {
                                    selectAction(it)
                                    viewModel.onSearchQueryChange("")
                                    focusManager.clearFocus(true)
                                    navigateUp()
                                },
                                favoriteAction = { viewModel.favoriteUnit(it) },
                            )
                        }
                    }
                }
            }
        }
    }

    // This block is called only once on initial composition
    LaunchedEffect(Unit) {
        /**
         * Telling viewModel that it needs to update the list
         */
        viewModel.setSelectedChip(currentUnit.group)
        viewModel.loadUnitsToShow(true)

        val groupToSelect = uiState.shownUnitGroups.indexOf(currentUnit.group)
        if (groupToSelect > -1) {
            chipsRowLazyListState.animateScrollToItem(groupToSelect)
        }
    }
}

/**
 * Right side screen. Unit to convert to.
 *
 * @param viewModel [SecondViewModel].
 * @param currentUnit Currently selected [AbstractUnit].
 * @param navigateUp Action to navigate up. Called when user click back button.
 * @param navigateToSettingsActtion Action to perform when clicking settings chip at the end.
 * @param selectAction Action to perform when user clicks on [UnitListItem].
 * @param inputValue Current input value (upper text field on MainScreen)
 * @param unitFrom Unit we are converting from. Need it for conversion.
 */
@Composable
fun RightSideScreen(
    viewModel: SecondViewModel,
    currentUnit: AbstractUnit,
    navigateUp: () -> Unit,
    navigateToSettingsActtion: () -> Unit,
    selectAction: (AbstractUnit) -> Unit,
    inputValue: BigDecimal,
    unitFrom: AbstractUnit
) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchBar(
                title = stringResource(R.string.units_screen_from),
                value = uiState.searchQuery,
                onValueChange = {
                    viewModel.onSearchQueryChange(it)
                    viewModel.loadUnitsToShow(false)
                },
                favoritesOnly = uiState.favoritesOnly,
                favoriteAction = {
                    viewModel.toggleFavoritesOnly()
                    viewModel.loadUnitsToShow(false)
                },
                navigateUpAction = navigateUp,
                focusManager = focusManager,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Crossfade(
            targetState = uiState.unitsToShow.isEmpty(),
            modifier = Modifier.padding(paddingValues)
        ) { noUnits ->
            if (noUnits) {
                SearchPlaceholder(navigateToSettingsActtion = navigateToSettingsActtion)
            } else {
                LazyColumn(Modifier.fillMaxSize()) {
                    uiState.unitsToShow.forEach { (unitGroup, listOfUnits) ->
                        item(unitGroup.name) {
                            UnitGroupHeader(Modifier.animateItemPlacement(), unitGroup)
                        }
                        items(listOfUnits, { it.unitId }) { unit ->
                            UnitListItem(
                                modifier = Modifier.animateItemPlacement(),
                                unit = unit,
                                isSelected = currentUnit == unit,
                                selectAction = {
                                    selectAction(it)
                                    viewModel.onSearchQueryChange("")
                                    focusManager.clearFocus(true)
                                    navigateUp()
                                },
                                favoriteAction = { viewModel.favoriteUnit(it) },
                                convertValue = {
                                    Formatter.format(
                                        unitFrom.convert(unit, inputValue, 3).toPlainString()
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // This block is called only once on initial composition
    LaunchedEffect(Unit) {
        /**
         * Telling viewModel that it needs to update the list
         */
        viewModel.setSelectedChip(currentUnit.group)
        viewModel.loadUnitsToShow(false)
    }
}

@Composable
private fun UnitGroupHeader(modifier: Modifier, unitGroup: UnitGroup) {
    Header(
        text = stringResource(unitGroup.res),
        modifier = modifier,
        paddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp)
    )
}

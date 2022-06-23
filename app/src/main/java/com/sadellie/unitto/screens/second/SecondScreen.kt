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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.screens.Formatter
import com.sadellie.unitto.screens.second.components.ChipsRow
import com.sadellie.unitto.screens.second.components.Header
import com.sadellie.unitto.screens.second.components.SearchBar
import com.sadellie.unitto.screens.second.components.SearchPlaceholder
import com.sadellie.unitto.screens.second.components.UnitListItem
import java.math.BigDecimal

/**
 * Basic Unit list screen for left and right sides screens.
 *
 * @param currentUnit Currently selected [AbstractUnit].
 * @param navigateUp Action to navigate up. Called when user click back button.
 * @param selectAction Action to perform when user clicks on [UnitListItem].
 * @param viewModel [SecondViewModel].
 * @param chipsRow Composable that is placed under TopAppBar. See [ChipsRow]
 * @param unitsListItem Composable that holds all units. See [UnitListItem].
 * @param noBrokenCurrencies When True will hide [AbstractUnit] with [AbstractUnit.isEnabled] set
 * to False.
 * @param title TopAppBar text.
 */
@Composable
private fun BasicUnitListScreen(
    currentUnit: AbstractUnit,
    navigateUp: () -> Unit,
    selectAction: (AbstractUnit) -> Unit,
    viewModel: SecondViewModel,
    chipsRow: @Composable (UnitGroup?, LazyListState) -> Unit = {_, _->},
    unitsListItem: @Composable (AbstractUnit, (AbstractUnit) -> Unit) -> Unit,
    noBrokenCurrencies: Boolean,
    title: String,
) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarScrollState())
    val focusManager = LocalFocusManager.current
    val chipsRowLazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchBar(
                title = title,
                value = uiState.searchQuery,
                onValueChange = {
                    viewModel.onSearchQueryChange(it)
                    viewModel.loadUnitsToShow(noBrokenCurrencies)
                },
                favoritesOnly = uiState.favoritesOnly,
                favoriteAction = {
                    viewModel.toggleFavoritesOnly()
                    viewModel.loadUnitsToShow(noBrokenCurrencies)
                },
                navigateUpAction = navigateUp,
                focusManager = focusManager,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(Modifier.padding(paddingValues)) {
            stickyHeader {
                chipsRow(
                    viewModel.uiState.chosenUnitGroup,
                    chipsRowLazyListState
                )
            }
            if (uiState.unitsToShow.isEmpty()) {
                item { SearchPlaceholder() }
                return@LazyColumn
            }
            uiState.unitsToShow.forEach { (unitGroup, listOfUnits) ->
                item { Header(text = stringResource(id = unitGroup.res)) }
                items(items = listOfUnits, key = { it.unitId }) { unit ->
                    unitsListItem(unit) {
                        selectAction(it)
                        viewModel.onSearchQueryChange("")
                        focusManager.clearFocus(true)
                        navigateUp()
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
        viewModel.loadUnitsToShow(noBrokenCurrencies)
        chipsRowLazyListState.animateScrollToItem(ALL_UNIT_GROUPS.indexOf(currentUnit.group))
    }
}

/**
 * Left side screen. Unit to convert from.
 *
 * @param currentUnit Currently selected [AbstractUnit].
 * @param navigateUp Action to navigate up. Called when user click back button.
 * @param selectAction Action to perform when user clicks on [UnitListItem].
 * @param viewModel [SecondViewModel].
 */
@Composable
fun LeftSideScreen(
    currentUnit: AbstractUnit,
    navigateUp: () -> Unit,
    selectAction: (AbstractUnit) -> Unit,
    viewModel: SecondViewModel
) = BasicUnitListScreen(
    currentUnit = currentUnit,
    navigateUp = navigateUp,
    selectAction = selectAction,
    viewModel = viewModel,
    chipsRow = { unitGroup, lazyListState ->
        ChipsRow(
            chosenUnitGroup = unitGroup,
            selectAction = {
                viewModel.toggleSelectedChip(it)
                viewModel.loadUnitsToShow(true)
            },
            lazyListState = lazyListState
        )
    },
    unitsListItem = { unit, selectUnitAction ->
        UnitListItem(
            unit = unit,
            isSelected = currentUnit == unit,
            selectAction = selectUnitAction,
            favoriteAction = { viewModel.favoriteUnit(it) },
        )
    },
    noBrokenCurrencies = true,
    title = stringResource(R.string.units_screen_from)
)

/**
 * Right side screen. Unit to convert from.
 *
 * @param currentUnit Currently selected [AbstractUnit].
 * @param navigateUp Action to navigate up. Called when user click back button.
 * @param selectAction Action to perform when user clicks on [UnitListItem].
 * @param viewModel [SecondViewModel].
 * @param inputValue Current input value (upper text field on MainScreen)
 * @param unitFrom Unit we are converting from. Need it for conversion.
 */
@Composable
fun RightSideScreen(
    currentUnit: AbstractUnit,
    navigateUp: () -> Unit,
    selectAction: (AbstractUnit) -> Unit,
    viewModel: SecondViewModel,
    inputValue: BigDecimal,
    unitFrom: AbstractUnit
) = BasicUnitListScreen(
    currentUnit = currentUnit,
    navigateUp = navigateUp,
    selectAction = selectAction,
    viewModel = viewModel,
    unitsListItem = { unit, selectUnitAction ->
        UnitListItem(
            unit = unit,
            isSelected = currentUnit == unit,
            selectAction = selectUnitAction,
            favoriteAction = { viewModel.favoriteUnit(it) },
            convertValue = { Formatter.format(unitFrom.convert(unit, inputValue, 3).toPlainString()) }
        )
    },
    noBrokenCurrencies = false,
    title = stringResource(R.string.units_screen_to)
)

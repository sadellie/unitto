package com.sadellie.unitto.screens.second

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.screens.MainViewModel
import com.sadellie.unitto.screens.second.components.ChipsRow
import com.sadellie.unitto.screens.second.components.SearchBar
import com.sadellie.unitto.screens.second.components.UnitsList

/** Second screen which contains a list of measurements
 * @param leftSide True if we have navigated to this screen by pressing the left button
 * @param navigateUp Function to navigate to the previous screen from NavHost
 * @param viewModel MainViewModel. Need it to access current state of the app
 */
@Composable
fun SecondScreen(
    leftSide: Boolean = true,
    navigateUp: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    var searchQuery: String by rememberSaveable { mutableStateOf(String()) }
    val favoritesOnly: Boolean = viewModel.favoritesOnly
    val focusManager = LocalFocusManager.current
    val unitsList: Map<UnitGroup, List<AbstractUnit>> = viewModel.unitsToShow
    val chipsRowLazyListState = rememberLazyListState()
    val currentUnit = if (leftSide) viewModel.unitFrom else viewModel.unitTo
    var chosenUnitGroup: UnitGroup? by rememberSaveable { mutableStateOf(currentUnit.group) }
    val scrollBehavior: TopAppBarScrollBehavior = remember {
        TopAppBarDefaults.enterAlwaysScrollBehavior()
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchBar(
                title = stringResource(id = if (leftSide) R.string.units_screen_from else R.string.units_screen_to),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.loadUnitsToShow(searchQuery, chosenUnitGroup, leftSide)
                },
                favoritesOnly = favoritesOnly,
                favoriteAction = {
                    viewModel.toggleFavoritesOnly()
                    viewModel.loadUnitsToShow(searchQuery, chosenUnitGroup, leftSide)
                },
                navigateUpAction = navigateUp,
                focusManager = focusManager,
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (leftSide) {
                ChipsRow(
                    lazyListState = chipsRowLazyListState,
                    items = ALL_UNIT_GROUPS,
                    chosenUnitGroup = chosenUnitGroup,
                    selectAction = {
                        chosenUnitGroup = if (it == chosenUnitGroup) null else it
                        viewModel.loadUnitsToShow(searchQuery, chosenUnitGroup, leftSide)
                    }
                )
                UnitsList(
                    groupedUnits = unitsList,
                    changeAction = { viewModel.changeUnitFrom(it); focusManager.clearFocus(true); navigateUp() },
                    favoriteAction = { viewModel.favoriteUnit(it) },
                    currentUnit = viewModel.unitFrom,
                )
            } else {
                UnitsList(
                    groupedUnits = unitsList,
                    changeAction = { viewModel.changeUnitTo(it); focusManager.clearFocus(true); navigateUp() },
                    favoriteAction = { viewModel.favoriteUnit(it) },
                    currentUnit = viewModel.unitTo,
                    inputValue = viewModel.mainUIState.inputValue.toBigDecimal(),
                    unitFrom = viewModel.unitFrom
                )
            }
        }
    }

    // This block is called only once on initial composition
    LaunchedEffect(Unit) {
        /**
         * Telling viewModel that it needs to update the list
         * Also while the below is being computed user will composable will use cached list from viewModel
         *
         * User actually doesn't see cached list. Computation takes few milliseconds since we don't
         * compute any Levenshtein distance when the screen is launched and the list is limited
         * to a specific [UnitGroup]
         *
         * Adding animation/spinners will cause flickering and confuse user
         */
        viewModel.loadUnitsToShow(searchQuery, chosenUnitGroup, leftSide)
        // Scrolling chips to current group
        chosenUnitGroup?.let {
            chipsRowLazyListState.animateScrollToItem(ALL_UNIT_GROUPS.indexOf(chosenUnitGroup))
        }
    }
}

/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.feature.unitslist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.UnittoSearchBar
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.NumberBaseUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.feature.unitslist.components.FavoritesButton
import com.sadellie.unitto.feature.unitslist.components.SearchPlaceholder
import com.sadellie.unitto.feature.unitslist.components.UnitGroupHeader
import com.sadellie.unitto.feature.unitslist.components.UnitListItem
import java.math.BigDecimal

/**
 * Right side screen. Unit to convert to.
 *
 * @param viewModel [UnitsListViewModel].
 * @param currentUnit Currently selected [AbstractUnit].
 * @param navigateUp Action to navigate up. Called when user click back button.
 * @param navigateToSettingsAction Action to perform when clicking settings chip at the end.
 * @param selectAction Action to perform when user clicks on [UnitListItem].
 * @param inputValue Current input value (upper text field on MainScreen)
 * @param unitFrom Unit we are converting from. Need it for conversion.
 */
@Composable
internal fun RightSideScreen(
    viewModel: UnitsListViewModel,
    currentUnit: String,
    navigateUp: () -> Unit,
    navigateToSettingsAction: () -> Unit,
    selectAction: (AbstractUnit) -> Unit,
    inputValue: String?,
    unitFrom: AbstractUnit
) {
    val uiState = viewModel.mainFlow.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val focusManager = LocalFocusManager.current

    val convertMethod: (AbstractUnit) -> String = try {
        val inputAsBigDecimal = BigDecimal(inputValue)

        when {
            inputValue.isNullOrEmpty() -> { { "" } }

            unitFrom.group == UnitGroup.NUMBER_BASE -> {
                { (convertForSecondaryNumberBase(inputValue, unitFrom, it)) }
            }

            else -> {
                {
                    convertForSecondary(inputAsBigDecimal, unitFrom, it, uiState.value.formatterSymbols)
                }
            }
        }
    } catch (e: Exception) {
        { "" }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UnittoSearchBar(
                query = uiState.value.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChange(it, true) },
                navigateUp = navigateUp,
                title = stringResource(R.string.units_screen_to),
                placeholder = stringResource(R.string.search_bar_placeholder),
                noSearchActions = {
                    FavoritesButton(uiState.value.favoritesOnly) {
                        viewModel.toggleFavoritesOnly(true)
                    }
                }
            )
        }
    ) { paddingValues ->
        Crossfade(
            targetState = uiState.value.unitsToShow.isEmpty(),
            modifier = Modifier.padding(paddingValues)
        ) { noUnits ->
            if (noUnits) {
                SearchPlaceholder(navigateToSettingsAction = navigateToSettingsAction)
            } else {
                LazyColumn(Modifier.fillMaxSize()) {
                    uiState.value.unitsToShow.forEach { (unitGroup, listOfUnits) ->
                        item(unitGroup.name) {
                            UnitGroupHeader(Modifier.animateItemPlacement(), unitGroup)
                        }
                        items(listOfUnits, { it.unitId }) { unit ->
                            UnitListItem(
                                modifier = Modifier.animateItemPlacement(),
                                unit = unit,
                                isSelected = currentUnit == unit.unitId,
                                selectAction = {
                                    selectAction(it)
                                    viewModel.onSearchQueryChange("", true)
                                    focusManager.clearFocus(true)
                                    navigateUp()
                                },
                                favoriteAction = { viewModel.favoriteUnit(it) },
                                convertValue = convertMethod
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun convertForSecondary(
    inputValue: BigDecimal,
    unitFrom: AbstractUnit,
    unitTo: AbstractUnit,
    formatterSymbols: FormatterSymbols
): String {
    return unitFrom.convert(unitTo, inputValue, 3).toPlainString()
        .formatExpression(formatterSymbols) + " "
}

private fun convertForSecondaryNumberBase(
    inputValue: String,
    unitFrom: AbstractUnit,
    unitTo: AbstractUnit
): String {
    return try {
        (unitFrom as NumberBaseUnit).convertToBase(
            inputValue,
            (unitTo as NumberBaseUnit).base
        ) + " "
    } catch (e: NumberFormatException) {
        ""
    } catch (e: ClassCastException) {
        ""
    }
}

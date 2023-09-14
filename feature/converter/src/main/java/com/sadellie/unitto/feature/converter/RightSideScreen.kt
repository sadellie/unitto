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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.UnittoSearchBar
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.DefaultUnit
import com.sadellie.unitto.data.model.unit.NumberBaseUnit
import com.sadellie.unitto.feature.converter.components.BasicUnitListItem
import com.sadellie.unitto.feature.converter.components.FavoritesButton
import com.sadellie.unitto.feature.converter.components.SearchPlaceholder
import com.sadellie.unitto.feature.converter.components.UnitGroupHeader
import java.math.BigDecimal

@Composable
internal fun RightSideRoute(
    viewModel: ConverterViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    val uiState = viewModel.rightSideUIState.collectAsStateWithLifecycle()

    RightSideScreen(
        uiState = uiState.value,
        onQueryChange = viewModel::queryChangeRight,
        toggleFavoritesOnly = viewModel::favoritesOnlyChange,
        updateUnitTo = viewModel::updateUnitTo,
        favoriteUnit = viewModel::favoriteUnit,
        navigateUp = navigateUp,
        navigateToUnitGroups = navigateToUnitGroups,
    )
}

@Composable
private fun RightSideScreen(
    uiState: RightSideUIState,
    onQueryChange: (TextFieldValue) -> Unit,
    toggleFavoritesOnly: (Boolean) -> Unit,
    updateUnitTo: (AbstractUnit) -> Unit,
    favoriteUnit: (AbstractUnit) -> Unit,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            UnittoSearchBar(
                query = uiState.query,
                onQueryChange = onQueryChange,
                navigateUp = navigateUp,
                title = stringResource(R.string.units_screen_to),
                placeholder = stringResource(R.string.search_bar_placeholder),
                noSearchActions = {
                    FavoritesButton(uiState.favorites) {
                        toggleFavoritesOnly(!uiState.favorites)
                    }
                },
                scrollBehavior = scrollBehavior
            )
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
                                supportLabel = formatUnitToSupportLabel(
                                    unitFrom = uiState.unitFrom,
                                    unitTo = it,
                                    input = uiState.input,
                                    shortName = stringResource(it.shortName),
                                    scale = uiState.scale,
                                    outputFormat = uiState.outputFormat,
                                    formatterSymbols = uiState.formatterSymbols,
                                    readyCurrencies = uiState.currencyRateUpdateState is CurrencyRateUpdateState.Ready,
                                ),
                                isFavorite = it.isFavorite,
                                isSelected = it.id == uiState.unitTo?.id,
                                onClick = {
                                    onQueryChange(TextFieldValue())
                                    updateUnitTo(it)
                                    navigateUp()
                                },
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

private fun formatUnitToSupportLabel(
    unitFrom: AbstractUnit?,
    unitTo: AbstractUnit?,
    input: String,
    shortName: String,
    scale: Int,
    outputFormat: Int,
    formatterSymbols: FormatterSymbols,
    readyCurrencies: Boolean,
): String {
    try {
        if ((unitFrom?.group == UnitGroup.CURRENCY) and !readyCurrencies) {
            return shortName
        }

        if ((unitFrom is DefaultUnit) and (unitTo is DefaultUnit)) {
            unitFrom as DefaultUnit
            unitTo as DefaultUnit

            val conversion = unitFrom
                .convert(unitTo, BigDecimal(input))
                .format(scale, outputFormat)
                .formatExpression(formatterSymbols)

            return "$conversion $shortName"
        }

        if ((unitFrom is NumberBaseUnit) and (unitTo is NumberBaseUnit)) {
            unitFrom as NumberBaseUnit
            unitTo as NumberBaseUnit

            val conversion = unitFrom.convert(unitTo, input).uppercase()

            return "$conversion $shortName"
        }
    } catch (e: Exception) {
        return shortName
    }

    return shortName
}

@Preview
@Composable
private fun RightSideScreenPreview() {
    RightSideScreen(
        uiState = RightSideUIState(),
        onQueryChange = {},
        toggleFavoritesOnly = {},
        updateUnitTo = {},
        favoriteUnit = {},
        navigateUp = {},
        navigateToUnitGroups = {}
    )
}


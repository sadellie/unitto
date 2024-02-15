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

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.FormatterSymbols
import com.sadellie.unitto.core.base.OutputFormat
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.SearchBar
import com.sadellie.unitto.core.ui.common.textfield.formatExpression
import com.sadellie.unitto.data.common.format
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit
import com.sadellie.unitto.data.model.unit.DefaultUnit
import com.sadellie.unitto.data.model.unit.NormalUnit
import com.sadellie.unitto.data.model.unit.NumberBaseUnit
import com.sadellie.unitto.feature.converter.components.FavoritesButton
import com.sadellie.unitto.feature.converter.components.UnitsList
import java.math.BigDecimal

@Composable
internal fun UnitToSelectorRoute(
    unitSelectorViewModel: UnitSelectorViewModel,
    converterViewModel: ConverterViewModel,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    when (
        val uiState = unitSelectorViewModel.unitToUIState.collectAsStateWithLifecycle().value
    ) {
        is UnitSelectorUIState.UnitTo -> UnitToSelectorScreen(
            uiState = uiState,
            onQueryChange = unitSelectorViewModel::updateSelectorQuery,
            toggleFavoritesOnly = unitSelectorViewModel::updateShowFavoritesOnly,
            updateUnitTo = converterViewModel::updateUnitTo,
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
    onQueryChange: (TextFieldValue) -> Unit,
    toggleFavoritesOnly: (Boolean) -> Unit,
    updateUnitTo: (AbstractUnit) -> Unit,
    favoriteUnit: (AbstractUnit) -> Unit,
    navigateUp: () -> Unit,
    navigateToUnitGroups: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
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
        },
    ) { paddingValues ->
        val resources = LocalContext.current.resources
        UnitsList(
            modifier = Modifier.padding(paddingValues),
            searchResult = uiState.units,
            navigateToUnitGroups = navigateToUnitGroups,
            currentUnitId = uiState.unitTo.id,
            supportLabel = {
                formatUnitToSupportLabel(
                    unitFrom = uiState.unitFrom,
                    unitTo = it,
                    input = uiState.input,
                    shortName = resources.getString(it.shortName),
                    scale = uiState.scale,
                    outputFormat = uiState.outputFormat,
                    formatterSymbols = uiState.formatterSymbols,
                )
            },
            onClick = {
                onQueryChange(TextFieldValue())
                updateUnitTo(it)
                navigateUp()
            },
            favoriteUnit = { favoriteUnit(it) },
        )
    }
}

private fun formatUnitToSupportLabel(
    unitFrom: AbstractUnit?,
    unitTo: AbstractUnit?,
    input: String?,
    shortName: String,
    scale: Int,
    outputFormat: Int,
    formatterSymbols: FormatterSymbols,
): String {
    if (input.isNullOrEmpty()) return shortName

    try {
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
private fun UnitToSelectorPreview() {
    val units: Map<UnitGroup, List<AbstractUnit>> = mapOf(
        UnitGroup.LENGTH to listOf(
            NormalUnit(UnitID.meter, BigDecimal("1000000000000000000"), UnitGroup.LENGTH, R.string.unit_meter, R.string.unit_meter_short),
            NormalUnit(UnitID.kilometer, BigDecimal("1000000000000000000000"), UnitGroup.LENGTH, R.string.unit_kilometer, R.string.unit_kilometer_short),
            NormalUnit(UnitID.nautical_mile, BigDecimal("1852000000000000000000"), UnitGroup.LENGTH, R.string.unit_nautical_mile, R.string.unit_nautical_mile_short),
            NormalUnit(UnitID.inch, BigDecimal("25400000000000000"), UnitGroup.LENGTH, R.string.unit_inch, R.string.unit_inch_short),
            NormalUnit(UnitID.foot, BigDecimal("304800000000000000"), UnitGroup.LENGTH, R.string.unit_foot, R.string.unit_foot_short),
            NormalUnit(UnitID.yard, BigDecimal("914400000000000000"), UnitGroup.LENGTH, R.string.unit_yard, R.string.unit_yard_short),
            NormalUnit(UnitID.mile, BigDecimal("1609344000000000000000"), UnitGroup.LENGTH, R.string.unit_mile, R.string.unit_mile_short),
        ),
    )

    UnitToSelectorScreen(
        uiState = UnitSelectorUIState.UnitTo(
            unitFrom = units.values.first().first(),
            unitTo = units.values.first().first(),
            query = TextFieldValue("test"),
            units = UnitSearchResult.Success(units),
            showFavoritesOnly = false,
            sorting = UnitsListSorting.USAGE,
            input = "100",
            scale = 3,
            outputFormat = OutputFormat.PLAIN,
            formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
        ),
        onQueryChange = {},
        toggleFavoritesOnly = {},
        updateUnitTo = {},
        favoriteUnit = {},
        navigateUp = {},
        navigateToUnitGroups = {},
    )
}

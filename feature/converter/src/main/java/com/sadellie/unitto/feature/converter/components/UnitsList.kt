/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.SearchPlaceholder
import com.sadellie.unitto.data.converter.UnitID
import com.sadellie.unitto.data.converter.UnitSearchResultItem
import com.sadellie.unitto.data.database.UnitsEntity
import com.sadellie.unitto.data.model.converter.UnitGroup
import com.sadellie.unitto.data.model.converter.unit.NormalUnit
import java.math.BigDecimal

@Composable
internal fun UnitsList(
    modifier: Modifier,
    searchResult: Map<UnitGroup, List<UnitSearchResultItem>>,
    navigateToUnitGroups: () -> Unit,
    currentUnitId: String,
    supportLabel: (UnitSearchResultItem) -> String,
    onClick: (UnitSearchResultItem) -> Unit,
    favoriteUnit: (UnitSearchResultItem) -> Unit,
) {
    Crossfade(
        modifier = modifier,
        targetState = searchResult,
        label = "Units list",
        animationSpec = tween(200),
    ) { result ->
        when {
            result.isEmpty() -> SearchPlaceholder(
                onButtonClick = navigateToUnitGroups,
                supportText = stringResource(R.string.converter_no_results_support),
                buttonLabel = stringResource(R.string.open_settings_label),
            )
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                result.forEach { (group, units) ->
                    item(group.name) {
                        UnitGroupHeader(Modifier.animateItemPlacement(), group)
                    }

                    items(units, { it.basicUnit.id }) {
                        BasicUnitListItem(
                            modifier = Modifier.animateItemPlacement(),
                            name = stringResource(it.basicUnit.displayName),
                            supportLabel = supportLabel(it),
                            isFavorite = it.stats.isFavorite,
                            isSelected = it.basicUnit.id == currentUnitId,
                            onClick = { onClick(it) },
                            favoriteUnit = { favoriteUnit(it) },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewUnitsList() {
    val resources = LocalContext.current.resources
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

    UnitsList(
        modifier = Modifier.fillMaxSize(),
        searchResult = units,
        navigateToUnitGroups = {},
        currentUnitId = UnitID.mile,
        supportLabel = { resources.getString(it.basicUnit.shortName) },
        onClick = {},
        favoriteUnit = {},
    )
}

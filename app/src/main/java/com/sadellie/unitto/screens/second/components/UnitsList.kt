package com.sadellie.unitto.screens.second.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.data.units.AbstractUnit
import com.sadellie.unitto.data.units.UnitGroup
import com.sadellie.unitto.screens.Formatter
import java.math.BigDecimal


/**
 * Component with grouped units.
 *
 * @param groupedUnits Grouped [AbstractUnit]s to be listed.
 * @param selectAction Action to perform when clicking on a list item.
 * @param favoriteAction Action to perform when clicking "favorite" button on list item.
 * @param currentUnit Currently selected unit. Will be visually distinguishable.
 */
@Composable
fun UnitsList(
    groupedUnits: Map<UnitGroup, List<AbstractUnit>>,
    selectAction: (AbstractUnit) -> Unit,
    favoriteAction: (AbstractUnit) -> Unit,
    currentUnit: AbstractUnit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            if (groupedUnits.isEmpty()) {
                item { SearchPlaceholder() }
                return@LazyColumn
            }
            groupedUnits.forEach { (unitGroup, listOfUnits) ->
                stickyHeader { Header(text = stringResource(id = unitGroup.res)) }
                items(items = listOfUnits, key = { it.unitId }) { unit ->
                    UnitListItem(
                        unit = unit,
                        isSelected = currentUnit == unit,
                        selectAction = selectAction,
                        favoriteAction = favoriteAction
                    )
                }
            }
        }
    )
}


/**
 * Component with grouped units/
 *
 * @param groupedUnits Grouped [AbstractUnit]s to be listed.
 * @param selectAction Action to perform when clicking on a list item.
 * @param favoriteAction Action to perform when clicking "favorite" button on list item.
 * @param currentUnit Currently selected unit. Will be visually distinguishable.
 * @param unitFrom Unit to convert from. Used for conversion in short name field.
 * @param input Current input value. Used for conversion in short name field.
 */
@Composable
fun UnitsList(
    groupedUnits: Map<UnitGroup, List<AbstractUnit>>,
    selectAction: (AbstractUnit) -> Unit,
    favoriteAction: (AbstractUnit) -> Unit,
    currentUnit: AbstractUnit,
    unitFrom: AbstractUnit,
    input: BigDecimal
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            if (groupedUnits.isEmpty()) {
                item { SearchPlaceholder() }
                return@LazyColumn
            }
            groupedUnits.forEach { (unitGroup, listOfUnits) ->
                stickyHeader { Header(text = stringResource(id = unitGroup.res)) }
                items(items = listOfUnits, key = { it.unitId }) { unit ->
                    UnitListItem(
                        unit = unit,
                        isSelected = currentUnit == unit,
                        selectAction = selectAction,
                        favoriteAction = favoriteAction,
                        convertValue = {
                            Formatter.format(unitFrom.convert(unit, input, 3).toPlainString())
                        }
                    )
                }
            }
        }
    )
}

/**
 * Unit group header.
 *
 * @param text Unit group name.
 */
@Composable
private fun Header(text: String) {
    Text(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}
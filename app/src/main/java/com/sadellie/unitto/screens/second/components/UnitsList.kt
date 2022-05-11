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
 * Component with grouped units
 *
 * @param groupedUnits Grouped [AbstractUnit]s to be listed
 * @param changeAction Action to perform when clicking on a list item
 * @param favoriteAction Action to perform when clicking "favorite" button on list item
 * @param inputValue Current input value. Used for right side screen
 * @param unitFrom Current unit on the left. Used for right side screen
 * @param currentUnit Currently selected unit. Will be visually distinguishable
 */
@Composable
fun UnitsList(
    groupedUnits: Map<UnitGroup, List<AbstractUnit>>,
    changeAction: (AbstractUnit) -> Unit,
    favoriteAction: (AbstractUnit) -> Unit,
    inputValue: BigDecimal = BigDecimal.ONE,
    unitFrom: AbstractUnit? = null,
    currentUnit: AbstractUnit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            if (groupedUnits.isEmpty()) {
                item { SearchPlaceholder() }
            } else {
                groupedUnits.forEach { (unitGroup, listOfUnits) ->
                    stickyHeader {
                        Text(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 8.dp),
                            text = stringResource(id = unitGroup.res),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    items(items = listOfUnits, key = { it.unitId }) { unit ->
                        UnitListItem(
                            modifier = Modifier,
                            changeAction = changeAction,
                            favoriteAction = favoriteAction,
                            item = unit,
                            isSelected = currentUnit == unit,
                            convertValue = {
                                if (unitFrom != null) {
                                    Formatter.format(
                                        unitFrom
                                            .convert(it, inputValue, 3)
                                            .toPlainString()
                                            .plus(" ")
                                    )
                                } else {
                                    ""
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

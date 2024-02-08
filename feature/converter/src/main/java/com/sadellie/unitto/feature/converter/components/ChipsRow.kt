/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.AssistChip
import com.sadellie.unitto.core.ui.common.FilterChip
import com.sadellie.unitto.data.model.UnitGroup

/**
 * Row of chips with [UnitGroup]s. Temporary solution
 *
 * @param items All [UnitGroup]s
 * @param chosenUnitGroup Currently selected [UnitGroup]
 * @param selectAction Action to perform when a chip is clicked
 * @param navigateToSettingsAction Action to perform when clicking settings chip at the end
 */
@Composable
internal fun ChipsRow(
    modifier: Modifier,
    items: List<UnitGroup>,
    chosenUnitGroup: UnitGroup?,
    selectAction: (UnitGroup?) -> Unit,
    navigateToSettingsAction: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val chipModifier = Modifier.padding(horizontal = 4.dp)

    AnimatedContent(
        targetState = expanded,
        transitionSpec = {
            expandVertically(expandFrom = Alignment.Top) { it } + fadeIn() togetherWith
                    shrinkVertically(shrinkTowards = Alignment.Top) { it } + fadeOut()
        }
    ) { isExpanded ->
        FlexRow(
            modifier = modifier,
            maxRows = if (isExpanded) Int.MAX_VALUE else 2,
            mainContent = {
                items.forEach { item ->
                    val selected: Boolean = item == chosenUnitGroup
                    FilterChip(
                        modifier = chipModifier,
                        isSelected = selected,
                        onClick = { 
                            selectAction(if (selected) null else item)
                            expanded = false 
                        },
                        label = stringResource(item.res),
                    )
                }

                AssistChip(
                    modifier = chipModifier,
                    onClick = navigateToSettingsAction,
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.open_settings_label)
                )

                if (expanded) {
                    AssistChip(
                        modifier = chipModifier,
                        onClick = { expanded = false },
                        imageVector = Icons.Default.ExpandLess,
                        contentDescription = "" // TODO
                    )
                }
            },
            expandContent = {
                AssistChip(
                    modifier = chipModifier,
                    onClick = { expanded = true },
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "" // TODO
                )
            },
        )
    }
}

/**
 * Foldable row that places a specified element if overflown.
 *
 * @param modifier [Modifier] to be applied to this layout.
 * @param maxRows Max amount of rows including with [expandContent].
 * @param mainContent Main content (list of items) that will be folded.
 * @param expandContent Item that will be placed at the end if given [maxRows] wasn't high enough to
 * place all [mainContent] items.
 */
@Composable
private fun FlexRow(
    modifier: Modifier = Modifier,
    maxRows: Int = Int.MAX_VALUE,
    mainContent: @Composable () -> Unit,
    expandContent: @Composable () -> Unit,
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->
        val localConstraints = constraints.copy(minWidth = 0, minHeight = 0)
        val layoutWidth = localConstraints.maxWidth

        val mainMeasurables = subcompose(FlexRowSlots.Main, mainContent)
        val expandMeasurables = subcompose(
            slotId = FlexRowSlots.Expand,
            content = { expandContent() }
        ).map {
            it.measure(localConstraints)
        }
        val expandContentWidth = expandMeasurables.sumOf { it.measuredWidth }

        val placeables = mutableListOf<MutableList<Placeable>>(mutableListOf())

        var widthLeft = layoutWidth
        var index = 0
        for (measurable in mainMeasurables) {
            val mainPlaceable = measurable.measure(localConstraints)

            val lastAvailableRow = placeables.size >= maxRows
            val notLastItem = index < mainMeasurables.lastIndex

			// count expandContent width only for last row and not last main placeable
            val measuredWidth = if (lastAvailableRow and notLastItem) {
                mainPlaceable.measuredWidth + expandContentWidth
            } else {
                mainPlaceable.measuredWidth
            }

            // need new row
            if (widthLeft <= measuredWidth) {
                // Can't add more rows, add expandContent
                if (lastAvailableRow) {
                    expandMeasurables.forEach {
                        placeables.last().add(it)
                    }
                    break
                }

                placeables.add(mutableListOf())
                widthLeft = layoutWidth
            }
            placeables.last().add(mainPlaceable)
            index++
            widthLeft -= mainPlaceable.measuredWidth
        }

        val flattenPlaceables = placeables.flatten()
        val layoutHeight = placeables.size * (flattenPlaceables.maxByOrNull { it.height }?.height ?: 0)

        layout(layoutWidth, layoutHeight) {
            var yPos = 0
            placeables.forEach { row ->
                var xPos = 0
                row.forEach { placeable ->
                    placeable.place(x = xPos, y = yPos)
                    xPos += placeable.width
                }
                yPos += row.maxByOrNull { it.height }?.height ?: 0
            }
        }
    }
}

private enum class FlexRowSlots { Main, Expand }

@Preview(device = "spec:width=380dp,height=850.9dp,dpi=440")
@Composable
fun PreviewUnittoChips() {
    var selected by remember { mutableStateOf<UnitGroup?>(UnitGroup.LENGTH) }

    fun selectAction(unitGroup: UnitGroup?) {
        selected = unitGroup
    }

    Column {
        ChipsRow(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            items = UnitGroup.entries.take(7),
            chosenUnitGroup = selected,
            selectAction = { selectAction(it) },
            navigateToSettingsAction = {},
        )

        ChipsRow(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            items = UnitGroup.entries.take(10),
            chosenUnitGroup = selected,
            selectAction = { selectAction(it) },
            navigateToSettingsAction = {},
        )
    }
}

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

package com.sadellie.unitto.screens.second.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.R
import com.sadellie.unitto.data.units.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.units.UnitGroup

/**
 * Row of chips with [UnitGroup]s. Temporary solution
 *
 * @param items All [UnitGroup]s
 * @param chosenUnitGroup Currently selected [UnitGroup]
 * @param selectAction Action to perform when a chip is clicked
 * @param navigateToSettingsAction Action to perform when clicking settings chip at the end
 * @param lazyListState Used for animated scroll when entering unit selection screen
 */
@Composable
fun ChipsRow(
    items: List<UnitGroup> = ALL_UNIT_GROUPS,
    chosenUnitGroup: UnitGroup?,
    selectAction: (UnitGroup) -> Unit,
    navigateToSettingsAction: () -> Unit,
    lazyListState: LazyListState
) {
    LazyRow(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth(),
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { it.name }) { item ->
            val isSelected: Boolean = item == chosenUnitGroup
            UnittoFilterChip(
                isSelected = isSelected,
                selectAction = { selectAction(item) }
            ) {
                AnimatedVisibility(visible = isSelected) {
                    Icon(
                        modifier = Modifier.height(18.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.checked_filter_description)
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(item.res),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        /**
         * Usually this chip is placed at the start, but since we scroll to currently selected
         * chip, user may never find it. There is higher chance that user will notice this chip when
         * scrolling right (to the last one).
         */
        item("settings") {
            UnittoFilterChip(
                isSelected = false,
                selectAction = navigateToSettingsAction,
                paddingValues = PaddingValues(horizontal = 8.dp)
            ) {
                Icon(
                    modifier = Modifier.height(18.dp),
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.open_settings_description)
                )
            }
        }
    }
}

/**
 * Basic chip implementation
 *
 * @param isSelected Chip state.
 * @param selectAction Action to perform when clicking chip.
 * @param paddingValues Padding values applied to Row.
 * @param content Content of the list. Icon and/or label.
 */
@Composable
private fun UnittoFilterChip(
    isSelected: Boolean,
    selectAction: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(start = 8.dp, end = 16.dp),
    content: @Composable RowScope.() -> Unit
) {
    val chipShape = RoundedCornerShape(8.dp)
    Row(
        modifier = Modifier
            .height(32.dp)
            .clip(chipShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
            )
            // Remove border when selected
            .border(
                1.dp,
                if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline,
                chipShape
            )
            .clickable { selectAction() }
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

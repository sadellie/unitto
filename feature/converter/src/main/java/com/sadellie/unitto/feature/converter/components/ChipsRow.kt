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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.AssistChip
import com.sadellie.unitto.core.ui.common.FilterChip
import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.UnitGroup

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
internal fun ChipsRow(
    items: List<UnitGroup> = ALL_UNIT_GROUPS,
    chosenUnitGroup: UnitGroup?,
    selectAction: (UnitGroup?) -> Unit,
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
            val selected: Boolean = item == chosenUnitGroup
            FilterChip(
                selected = selected,
                onClick = { selectAction(if (selected) null else item) },
                label = stringResource(item.res),
                imageVector = Icons.Default.Check,
                contentDescription = stringResource(R.string.checked_filter_description)
            )
        }

        /**
         * Usually this chip is placed at the start, but since we scroll to currently selected
         * chip, user may never find it. There is higher chance that user will notice this chip when
         * scrolling right (to the last one).
         */
        item("settings") {
            AssistChip(
                onClick = navigateToSettingsAction,
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.open_settings_description)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsFlexRow(
    items: List<UnitGroup> = ALL_UNIT_GROUPS,
    chosenUnitGroup: UnitGroup?,
    selectAction: (UnitGroup?) -> Unit,
    navigateToSettingsAction: () -> Unit,
    lazyListState: LazyListState
) {
    var expanded by remember { mutableStateOf(false) }
    val transition = updateTransition(expanded, label = "Expanded transition")

    val rowHeight by transition.animateDp({ tween() }, "Row height") {
        if (it) 392.dp else 32.dp
    }

    val expandRotation by transition.animateFloat({ tween() }, "Expand rotation") {
        if (it) 180f else 0f
    }

    Row(
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .heightIn(max = rowHeight)
            .clipToBounds()
    ) {
        FlowRow(
            modifier = Modifier
                .verticalScroll(rememberScrollState(), expanded)
                .fillMaxHeight()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                val selected: Boolean = item == chosenUnitGroup
                FilterChip(
                    selected = selected,
                    onClick = { selectAction(if (selected) null else item) },
                    label = stringResource(item.res),
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.checked_filter_description)
                )
            }
            AssistChip(
                onClick = navigateToSettingsAction,
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.open_settings_description)
            )
        }

        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExpandMore,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .rotate(expandRotation)
            )
        }
    }
}

@Preview
@Composable
fun PreviewUnittoChips() {
    var selected by remember { mutableStateOf<UnitGroup?>(UnitGroup.LENGTH) }

    fun selectAction(unitGroup: UnitGroup?) {
        selected = unitGroup
    }

    ChipsRow(
        items = ALL_UNIT_GROUPS,
        chosenUnitGroup = selected,
        selectAction = { selectAction(it) },
        navigateToSettingsAction = {},
        lazyListState = rememberLazyListState()
    )
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFE0E0E0)
@Composable
fun PreviewChipsFlowRow() {
    var selected by remember { mutableStateOf<UnitGroup?>(UnitGroup.LENGTH) }

    fun selectAction(unitGroup: UnitGroup?) {
        selected = unitGroup
    }

    ChipsFlexRow(
        items = ALL_UNIT_GROUPS,
        chosenUnitGroup = selected,
        selectAction = { selectAction(it) },
        navigateToSettingsAction = {},
        lazyListState = rememberLazyListState()
    )
}

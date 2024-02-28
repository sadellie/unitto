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

package com.sadellie.unitto.feature.settings.unitgroups

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.ui.common.EmptyScreen
import com.sadellie.unitto.core.ui.common.Header
import com.sadellie.unitto.core.ui.common.NavigateUpButton
import com.sadellie.unitto.core.ui.common.ScaffoldWithLargeTopBar
import com.sadellie.unitto.data.model.converter.UnitGroup
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
internal fun UnitGroupsRoute(
    viewModel: UnitGroupsViewModel = hiltViewModel(),
    navigateUpAction: () -> Unit,
) {
    when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
        UnitGroupsUIState.Loading -> EmptyScreen()
        is UnitGroupsUIState.Ready -> UnitGroupsScreen(
            uiState = uiState,
            navigateUpAction = navigateUpAction,
            updateShownUnitGroups = viewModel::updateShownUnitGroups,
            addShownUnitGroup = viewModel::addShownUnitGroup,
            removeShownUnitGroup = viewModel::removeShownUnitGroup,
        )
    }
}

@Composable
private fun UnitGroupsScreen(
    uiState: UnitGroupsUIState.Ready,
    navigateUpAction: () -> Unit,
    updateShownUnitGroups: (List<UnitGroup>) -> Unit,
    addShownUnitGroup: (UnitGroup) -> Unit,
    removeShownUnitGroup: (UnitGroup) -> Unit,
) {
    ScaffoldWithLargeTopBar(
        title = stringResource(R.string.settings_unit_groups_title),
        navigationIcon = { NavigateUpButton(navigateUpAction) },
    ) { paddingValues ->
        val copiedShownList = rememberUpdatedState(uiState.shownUnitGroups) as MutableState
        val state = rememberReorderableLazyListState(
            onMove = { from, to ->
                copiedShownList.value = copiedShownList.value
                    .toMutableList()
                    .apply {
                        // -1 for list header
                        add(to.index - 1, removeAt(from.index - 1))
                    }
            },
            canDragOver = { draggedOver, _ ->
                // offset by 1 for list header
                draggedOver.index in 1..(copiedShownList.value.lastIndex + 1)
            },
            onDragEnd = onDragEnd@{ from, to ->
                if (from == to) return@onDragEnd
                updateShownUnitGroups(copiedShownList.value)
            },
        )

        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .padding(paddingValues)
                .reorderable(state),
        ) {
            item(key = "enabled") {
                Header(
                    text = stringResource(R.string.enabled_label),
                    paddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                )
            }

            items(copiedShownList.value, { it }) { item ->
                ReorderableItem(state, key = item) { isDragging ->
                    val transition = updateTransition(isDragging, label = "draggedTransition")
                    val background by transition.animateColor(label = "background") {
                        if (it) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    }
                    val textColor by transition.animateColor(label = "background") {
                        if (it) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                    }
                    val iconColor by transition.animateColor(label = "background") {
                        if (it) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.outline
                    }
                    val itemPadding by transition.animateDp(label = "itemPadding") {
                        if (it) 16.dp else 0.dp
                    }

                    ListItem(
                        headlineContent = { Text(stringResource(item.res)) },
                        modifier = Modifier
                            .padding(horizontal = itemPadding)
                            .clip(CircleShape)
                            .clickable { removeShownUnitGroup(item) }
                            .detectReorderAfterLongPress(state),
                        colors = ListItemDefaults.colors(
                            containerColor = background,
                            headlineColor = textColor,
                            leadingIconColor = iconColor,
                            trailingIconColor = iconColor,
                        ),
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.RemoveCircle,
                                contentDescription = stringResource(R.string.settings_disable_unit_group_description),
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(false),
                                    onClick = { removeShownUnitGroup(item) },
                                ),
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Default.DragHandle,
                                contentDescription = stringResource(R.string.settings_reorder_unit_group_description),
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple(false),
                                        onClick = {},
                                    )
                                    .detectReorder(state),
                            )
                        },
                    )
                }
            }

            item(key = "disabled") {
                Header(
                    text = stringResource(R.string.disabled_label),
                    modifier = Modifier.animateItemPlacement(),
                    paddingValues = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                )
            }

            items(uiState.hiddenUnitGroups, { it }) {
                ListItem(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { addShownUnitGroup(it) }
                        .animateItemPlacement(),
                    headlineContent = { Text(stringResource(it.res)) },
                    trailingContent = {
                        Icon(
                            Icons.Default.AddCircleOutline,
                            stringResource(R.string.settings_enable_unit_group_description),
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(false),
                                onClick = { addShownUnitGroup(it) },
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewUnitGroupsScreen() {
    val shownUnitGroups = UnitGroup.entries.take(4)

    UnitGroupsScreen(
        uiState = UnitGroupsUIState.Ready(
            shownUnitGroups = shownUnitGroups,
            hiddenUnitGroups = UnitGroup.entries - shownUnitGroups.toSet(),
        ),
        navigateUpAction = {},
        updateShownUnitGroups = {},
        addShownUnitGroup = {},
        removeShownUnitGroup = {},
    )
}

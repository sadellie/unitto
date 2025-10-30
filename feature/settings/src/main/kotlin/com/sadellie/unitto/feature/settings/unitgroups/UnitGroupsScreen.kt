/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.designsystem.icons.symbols.AddCircle
import com.sadellie.unitto.core.designsystem.icons.symbols.Cancel
import com.sadellie.unitto.core.designsystem.icons.symbols.DragHandle
import com.sadellie.unitto.core.designsystem.icons.symbols.SwapVert
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols.Undo
import com.sadellie.unitto.core.designsystem.plus
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListHeader
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.ListItemExpressiveDefaults
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
internal fun UnitGroupsRoute(
  viewModel: UnitGroupsViewModel = hiltViewModel(),
  navigateUpAction: () -> Unit,
) {
  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    UnitGroupsUIState.Loading -> EmptyScreen()
    is UnitGroupsUIState.Ready ->
      UnitGroupsScreen(
        uiState = uiState,
        navigateUpAction = navigateUpAction,
        updateShownUnitGroups = viewModel::updateShownUnitGroups,
        addShownUnitGroup = viewModel::addShownUnitGroup,
        removeShownUnitGroup = viewModel::removeShownUnitGroup,
        updateAutoSortDialogState = viewModel::updateAutoSortDialogState,
        autoSortUnitGroups = viewModel::autoSortUnitGroups,
        undoAutoSortUnitGroups = viewModel::undoAutoSortUnitGroups,
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
  autoSortUnitGroups: () -> Unit,
  undoAutoSortUnitGroups: () -> Unit,
  updateAutoSortDialogState: (AutoSortDialogState) -> Unit,
) {
  val showAutoSortDialog =
    remember(uiState.autoSortDialogState) {
      uiState.autoSortDialogState != AutoSortDialogState.NONE
    }
  val isSorting =
    remember(uiState.autoSortDialogState) {
      uiState.autoSortDialogState == AutoSortDialogState.IN_PROGRESS
    }

  ScaffoldWithLargeTopBar(
    title = stringResource(R.string.settings_unit_groups_title),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
    actions = {
      AnimatedVisibility(
        visible = !uiState.isAutoSortEnabled,
        label = "Undo button",
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally(),
      ) {
        IconButton(onClick = undoAutoSortUnitGroups, enabled = !uiState.isAutoSortEnabled) {
          Icon(Symbols.Undo, stringResource(R.string.settings_unit_groups_undo))
        }
      }

      IconButton(
        onClick = { updateAutoSortDialogState(AutoSortDialogState.SHOW) },
        enabled = uiState.isAutoSortEnabled,
      ) {
        Icon(Symbols.SwapVert, stringResource(R.string.settings_unit_groups_auto_sort))
      }
    },
  ) { paddingValues ->
    val copiedShownList = rememberUpdatedState(uiState.shownUnitGroups) as MutableState
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState =
      rememberReorderableLazyListState(
        lazyListState = lazyListState,
        onMove = { from, to ->
          copiedShownList.value =
            copiedShownList.value.toMutableList().apply {
              // -1 for list header
              add(to.index - 1, removeAt(from.index - 1))
            }
        },
      )

    val headerModifier =
      Modifier.padding(
        start = Sizes.small,
        end = Sizes.small,
        top = Sizes.large,
        bottom = Sizes.small,
      )

    LazyColumn(
      state = lazyListState,
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
      contentPadding =
        paddingValues + PaddingValues(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
    ) {
      item(key = "enabled", contentType = ContentType.HEADER) {
        ListHeader(
          modifier = Modifier.animateItem().then(headerModifier),
          text = stringResource(R.string.common_enabled),
        )
      }

      itemsIndexed(
        items = copiedShownList.value,
        key = { _, item -> item },
        contentType = { index, item -> ContentType.ENABLED_ITEM },
      ) { index, unitGroup ->
        EnabledUnitGroupItem(
          reorderableLazyListState = reorderableLazyListState,
          unitGroup = unitGroup,
          removeShownUnitGroup = removeShownUnitGroup,
          onDragStopped = { updateShownUnitGroups(copiedShownList.value) },
          shape = ListItemExpressiveDefaults.listedShaped(index, copiedShownList.value.size),
        )
      }

      item(key = "disabled", contentType = ContentType.HEADER) {
        ListHeader(
          text = stringResource(R.string.common_disabled),
          modifier = Modifier.animateItem().then(headerModifier),
        )
      }

      itemsIndexed(
        items = uiState.hiddenUnitGroups,
        key = { index, item -> item },
        contentType = { index, item -> ContentType.DISABLED_ITEM },
      ) { index, unitGroup ->
        DisabledUnitGroupItem(
          onClick = { addShownUnitGroup(unitGroup) },
          unitGroup = unitGroup,
          shape = ListItemExpressiveDefaults.listedShaped(index, uiState.hiddenUnitGroups.size),
        )
      }
    }
  }

  if (showAutoSortDialog) {
    AlertDialog(
      icon = { Icon(Symbols.SwapVert, stringResource(R.string.settings_unit_groups_auto_sort)) },
      onDismissRequest = { updateAutoSortDialogState(AutoSortDialogState.NONE) },
      confirmButton = {
        TextButton(onClick = autoSortUnitGroups, enabled = !isSorting) {
          Text(stringResource(R.string.common_confirm))
        }
      },
      dismissButton = {
        TextButton(
          onClick = { updateAutoSortDialogState(AutoSortDialogState.NONE) },
          enabled = !isSorting,
        ) {
          Text(stringResource(R.string.common_cancel))
        }
      },
      title = { Text(stringResource(R.string.settings_unit_groups_auto_sort)) },
      text = { Text(stringResource(R.string.settings_unit_groups_auto_sort_support)) },
    )
  }
}

@Composable
private fun LazyItemScope.EnabledUnitGroupItem(
  reorderableLazyListState: ReorderableLazyListState,
  unitGroup: UnitGroup,
  removeShownUnitGroup: (UnitGroup) -> Unit,
  onDragStopped: () -> Unit,
  shape: Shape,
) {
  ReorderableItem(reorderableLazyListState, unitGroup) { isDragging ->
    val transition = updateTransition(isDragging, label = "draggedTransition")
    val background by
      transition.animateColor(label = "background") {
        if (it) MaterialTheme.colorScheme.surfaceContainerHighest
        else MaterialTheme.colorScheme.surfaceBright
      }
    val itemPadding by transition.animateDp(label = "itemPadding") { if (it) Sizes.large else 0.dp }
    val cornerRadius by
      transition.animateDp(label = "cornerRadius") { if (it) Sizes.large else 0.dp }

    ListItemExpressive(
      headlineContent = { Text(stringResource(unitGroup.res)) },
      onClick = { removeShownUnitGroup(unitGroup) },
      modifier =
        Modifier.padding(horizontal = itemPadding)
          .clip(RoundedCornerShape(cornerRadius.value))
          .longPressDraggableHandle(onDragStopped = onDragStopped),
      colors = ListItemDefaults.colors(containerColor = background),
      shape = shape,
      leadingContent = {
        Icon(
          imageVector = Symbols.Cancel,
          contentDescription = stringResource(R.string.settings_disable_unit_group_description),
          modifier =
            Modifier.clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple(false),
              onClick = { removeShownUnitGroup(unitGroup) },
            ),
        )
      },
      trailingContent = {
        Icon(
          imageVector = Symbols.DragHandle,
          contentDescription = stringResource(R.string.settings_reorder_unit_group_description),
          modifier =
            Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(false),
                onClick = {},
              )
              .draggableHandle(onDragStopped = onDragStopped),
        )
      },
    )
  }
}

@Composable
private fun LazyItemScope.DisabledUnitGroupItem(
  onClick: () -> Unit,
  unitGroup: UnitGroup,
  shape: Shape,
) {
  ListItemExpressive(
    modifier = Modifier.animateItem(),
    onClick = onClick,
    shape = shape,
    headlineContent = { Text(stringResource(unitGroup.res)) },
    trailingContent = {
      Icon(
        imageVector = Symbols.AddCircle,
        contentDescription = stringResource(R.string.settings_enable_unit_group_description),
        modifier =
          Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(false),
            onClick = onClick,
          ),
      )
    },
  )
}

private enum class ContentType {
  HEADER,
  ENABLED_ITEM,
  DISABLED_ITEM,
}

@Preview
@Composable
private fun PreviewUnitGroupsScreen() {
  val shownUnitGroups = UnitGroup.entries.take(4)

  UnitGroupsScreen(
    uiState =
      UnitGroupsUIState.Ready(
        shownUnitGroups = shownUnitGroups,
        hiddenUnitGroups = UnitGroup.entries - shownUnitGroups.toSet(),
        isAutoSortEnabled = true,
        autoSortDialogState = AutoSortDialogState.NONE,
      ),
    navigateUpAction = {},
    updateShownUnitGroups = {},
    addShownUnitGroup = {},
    removeShownUnitGroup = {},
    updateAutoSortDialogState = {},
    autoSortUnitGroups = {},
    undoAutoSortUnitGroups = {},
  )
}

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2026 Elshan Agaev
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
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ListItemShapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.designsystem.icons.symbols.AddCircle
import com.sadellie.unitto.core.designsystem.icons.symbols.Cancel
import com.sadellie.unitto.core.designsystem.icons.symbols.DragHandle
import com.sadellie.unitto.core.designsystem.icons.symbols.SwapVert
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.icons.symbols.Undo
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ListArrangement
import com.sadellie.unitto.core.ui.ListHeader
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.NavigateUpButton
import com.sadellie.unitto.core.ui.ScaffoldWithLargeTopBar
import com.sadellie.unitto.core.ui.animations.animateItemDefault
import com.sadellie.unitto.core.ui.listedShapes
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.ReorderableLazyListState
import sh.calvin.reorderable.rememberReorderableLazyListState
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_confirm
import unitto.core.common.generated.resources.common_disabled
import unitto.core.common.generated.resources.common_enabled
import unitto.core.common.generated.resources.settings_disable_unit_group_description
import unitto.core.common.generated.resources.settings_enable_unit_group_description
import unitto.core.common.generated.resources.settings_reorder_unit_group_description
import unitto.core.common.generated.resources.settings_unit_groups_auto_sort
import unitto.core.common.generated.resources.settings_unit_groups_auto_sort_support
import unitto.core.common.generated.resources.settings_unit_groups_title
import unitto.core.common.generated.resources.settings_unit_groups_undo

@Composable
internal fun UnitGroupsRoute(navigateUpAction: () -> Unit) {
  val viewModel: UnitGroupsViewModel = metroViewModel()
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
    title = stringResource(Res.string.settings_unit_groups_title),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
    actions = {
      AnimatedVisibility(
        visible = !uiState.isAutoSortEnabled,
        label = "Undo button",
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally(),
      ) {
        IconButton(
          onClick = undoAutoSortUnitGroups,
          enabled = !uiState.isAutoSortEnabled,
          shapes = IconButtonDefaults.shapes(),
        ) {
          Icon(Symbols.Undo, stringResource(Res.string.settings_unit_groups_undo))
        }
      }

      IconButton(
        onClick = { updateAutoSortDialogState(AutoSortDialogState.SHOW) },
        enabled = uiState.isAutoSortEnabled,
        shapes = IconButtonDefaults.shapes(),
      ) {
        Icon(Symbols.SwapVert, stringResource(Res.string.settings_unit_groups_auto_sort))
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
      verticalArrangement = ListItemDefaults.ListArrangement,
      contentPadding =
        paddingValues + PaddingValues(start = Sizes.large, end = Sizes.large, bottom = Sizes.large),
    ) {
      item(key = "enabled", contentType = ContentType.HEADER) {
        ListHeader(
          modifier = Modifier.animateItemDefault().then(headerModifier),
          text = stringResource(Res.string.common_enabled),
        )
      }

      itemsIndexed(
        items = copiedShownList.value,
        key = { _, item -> item },
        contentType = { _, _ -> ContentType.ENABLED_ITEM },
      ) { index, unitGroup ->
        EnabledUnitGroupItem(
          reorderableLazyListState = reorderableLazyListState,
          unitGroup = unitGroup,
          removeShownUnitGroup = removeShownUnitGroup,
          onDragStopped = { updateShownUnitGroups(copiedShownList.value) },
          shapes = ListItemDefaults.listedShapes(index, copiedShownList.value.size),
        )
      }

      item(key = "disabled", contentType = ContentType.HEADER) {
        ListHeader(
          text = stringResource(Res.string.common_disabled),
          modifier = Modifier.animateItemDefault().then(headerModifier),
        )
      }

      itemsIndexed(
        items = uiState.hiddenUnitGroups,
        key = { _, item -> item },
        contentType = { _, _ -> ContentType.DISABLED_ITEM },
      ) { index, unitGroup ->
        DisabledUnitGroupItem(
          onClick = { addShownUnitGroup(unitGroup) },
          unitGroup = unitGroup,
          shapes = ListItemDefaults.listedShapes(index, uiState.hiddenUnitGroups.size),
        )
      }
    }
  }

  if (showAutoSortDialog) {
    AlertDialog(
      icon = { Icon(Symbols.SwapVert, stringResource(Res.string.settings_unit_groups_auto_sort)) },
      onDismissRequest = { updateAutoSortDialogState(AutoSortDialogState.NONE) },
      confirmButton = {
        TextButton(
          onClick = autoSortUnitGroups,
          enabled = !isSorting,
          shapes = ButtonDefaults.shapes(),
        ) {
          Text(stringResource(Res.string.common_confirm))
        }
      },
      dismissButton = {
        TextButton(
          onClick = { updateAutoSortDialogState(AutoSortDialogState.NONE) },
          enabled = !isSorting,
          shapes = ButtonDefaults.shapes(),
        ) {
          Text(stringResource(Res.string.common_cancel))
        }
      },
      title = { Text(stringResource(Res.string.settings_unit_groups_auto_sort)) },
      text = { Text(stringResource(Res.string.settings_unit_groups_auto_sort_support)) },
    )
  }
}

@Composable
private fun LazyItemScope.EnabledUnitGroupItem(
  reorderableLazyListState: ReorderableLazyListState,
  unitGroup: UnitGroup,
  removeShownUnitGroup: (UnitGroup) -> Unit,
  onDragStopped: () -> Unit,
  shapes: ListItemShapes,
) {
  ReorderableItem(reorderableLazyListState, unitGroup) { _ ->
    val interactionSource = remember { MutableInteractionSource() }
    ListItemExpressive(
      interactionSource = interactionSource,
      content = { Text(stringResource(unitGroup.res)) },
      onClick = { removeShownUnitGroup(unitGroup) },
      modifier =
        Modifier.longPressDraggableHandle(
          onDragStopped = onDragStopped,
          interactionSource = interactionSource,
        ),
      shapes = shapes,
      leadingContent = {
        Icon(
          imageVector = Symbols.Cancel,
          contentDescription = stringResource(Res.string.settings_disable_unit_group_description),
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
          contentDescription = stringResource(Res.string.settings_reorder_unit_group_description),
          modifier =
            Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(false),
                onClick = {},
              )
              .draggableHandle(
                onDragStopped = onDragStopped,
                interactionSource = interactionSource,
              ),
        )
      },
    )
  }
}

@Composable
private fun LazyItemScope.DisabledUnitGroupItem(
  onClick: () -> Unit,
  unitGroup: UnitGroup,
  shapes: ListItemShapes,
) {
  ListItemExpressive(
    modifier = Modifier.animateItemDefault(),
    onClick = onClick,
    shapes = shapes,
    content = { Text(stringResource(unitGroup.res)) },
    trailingContent = {
      Icon(
        imageVector = Symbols.AddCircle,
        contentDescription = stringResource(Res.string.settings_enable_unit_group_description),
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

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

package com.sadellie.unitto.feature.calculator.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.LocalHapticFeedbackManager
import com.sadellie.unitto.core.designsystem.icons.symbols.Delete
import com.sadellie.unitto.core.designsystem.icons.symbols.Edit
import com.sadellie.unitto.core.designsystem.icons.symbols.History
import com.sadellie.unitto.core.designsystem.icons.symbols.MoreHoriz
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryModel
import com.sadellie.unitto.core.ui.ProvideColor
import com.sadellie.unitto.core.ui.datetime.LocalPlatformDateFormatSettings
import com.sadellie.unitto.core.ui.datetime.formatDateWeekDayMonthYear
import com.sadellie.unitto.core.ui.textfield.FixedExpressionInputTextField
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.calculator_delete_history_entry
import unitto.core.common.generated.resources.calculator_delete_history_entry_support
import unitto.core.common.generated.resources.calculator_no_history
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_delete
import unitto.core.common.generated.resources.common_label
import unitto.core.common.generated.resources.common_ok

@Composable
internal fun CalculatorHistoryList(
  modifier: Modifier,
  itemsFlow: Flow<PagingData<CalculatorHistoryModel>>,
  formatterSymbols: FormatterSymbols,
  addTokens: (String) -> Unit,
  onDelete: (CalculatorHistoryModel.Item) -> Unit,
  onUpdateLabel: (CalculatorHistoryModel.Item, String) -> Unit,
  showMenuButton: Boolean,
) {
  val pagingItems = itemsFlow.collectAsLazyPagingItems()
  val showPlaceHolder =
    remember(pagingItems) {
      derivedStateOf {
        val refreshState = pagingItems.loadState.refresh
        refreshState is LoadState.NotLoading && pagingItems.itemCount == 0
      }
    }

  Crossfade(
    targetState = showPlaceHolder.value,
    label = "History list",
    modifier = modifier,
  ) { emptyList ->
    if (emptyList) {
      HistoryListPlaceholder(modifier = Modifier.fillMaxSize())
    } else {
      HistoryListContent(
        modifier = Modifier.fillMaxSize(),
        pagingItems = pagingItems,
        formatterSymbols = formatterSymbols,
        addTokens = addTokens,
        onDelete = onDelete,
        onUpdateLabel = onUpdateLabel,
        showMenuButton = showMenuButton,
      )
    }
  }
}

@Composable
private fun HistoryListPlaceholder(modifier: Modifier) {
  Column(
    modifier = modifier.wrapContentHeight(unbounded = true),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Column(
      modifier = Modifier.height(HistoryItemHeight),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      ProvideColor(MaterialTheme.colorScheme.onSurfaceVariant) {
        Icon(
          imageVector = Symbols.History,
          contentDescription = stringResource(Res.string.calculator_no_history),
        )
        Text(stringResource(Res.string.calculator_no_history))
      }
    }
  }
}

@Composable
private fun HistoryListContent(
  modifier: Modifier,
  pagingItems: LazyPagingItems<CalculatorHistoryModel>,
  formatterSymbols: FormatterSymbols,
  addTokens: (String) -> Unit,
  onDelete: (CalculatorHistoryModel.Item) -> Unit,
  onUpdateLabel: (CalculatorHistoryModel.Item, String) -> Unit,
  showMenuButton: Boolean,
) {
  val listState = rememberLazyListState()
  val focusManager = LocalFocusManager.current
  var openedItemId by remember { mutableStateOf<Int?>(null) }

  LaunchedEffect(showMenuButton) {
    // force close all
    if (!showMenuButton) openedItemId = null
  }

  LaunchedEffect(listState.isScrollInProgress) {
    if (listState.isScrollInProgress) {
      openedItemId = null
      // Selection handles cause lag
      focusManager.clearFocus(true)
    }
  }

  LaunchedEffect(pagingItems.itemCount) {
    // scroll only when list is not expanded. This fixes items placement animation
    if (!showMenuButton) listState.animateScrollToItem(0)
  }

  LazyColumn(modifier = modifier, state = listState, reverseLayout = true) {
    items(
      count = pagingItems.itemCount,
      key =
        pagingItems.itemKey {
          when (it) {
            is CalculatorHistoryModel.Header -> "header_$it"
            is CalculatorHistoryModel.Item -> "item_${it.id}"
          }
        },
    ) { index ->
      val item = pagingItems[index]
      if (item == null) {
        Spacer(Modifier.fillMaxWidth().height(HistoryItemHeight))
      } else {
        when (item) {
          is CalculatorHistoryModel.Header ->
            HistoryListHeader(modifier = Modifier.fillMaxWidth(), header = item)
          is CalculatorHistoryModel.Item ->
            HistoryListItem(
              modifier = Modifier.animateItem().fillMaxWidth().heightIn(min = HistoryItemHeight),
              item = item,
              formatterSymbols = formatterSymbols,
              addTokens = addTokens,
              onDelete = { onDelete(item) },
              showMenuButton = showMenuButton,
              onUpdateLabel = { label -> onUpdateLabel(item, label) },
              isOpened = openedItemId == item.id,
              onOpened = { openedItemId = item.id },
              onClosed = { if (openedItemId == item.id) openedItemId = null },
            )
        }
      }
    }
  }
}

@Composable
private fun HistoryListHeader(
  modifier: Modifier,
  header: CalculatorHistoryModel.Header,
) {
  val platformDateFormatSettings = LocalPlatformDateFormatSettings.current
  val formattedDate =
    remember(header.instant, platformDateFormatSettings) {
      header.instant.formatDateWeekDayMonthYear(platformDateFormatSettings)
    }
  Text(
    modifier = modifier.padding(start = Sizes.small, end = Sizes.small, top = Sizes.small),
    text = formattedDate,
    style = MaterialTheme.typography.labelMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant,
  )
}

private enum class HistoryListItemDragState {
  Closed,
  Opened,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HistoryListItem(
  modifier: Modifier,
  item: CalculatorHistoryModel.Item,
  formatterSymbols: FormatterSymbols,
  addTokens: (String) -> Unit,
  onDelete: () -> Unit,
  onUpdateLabel: (String) -> Unit,
  showMenuButton: Boolean,
  isOpened: Boolean,
  onOpened: () -> Unit,
  onClosed: () -> Unit,
) {
  Box(
    modifier =
      modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh).height(IntrinsicSize.Min)
  ) {
    val density = LocalDensity.current
    val maxDragPx = remember(density) { with(density) { HistoryItemButtonRowWidth.toPx() } }
    val dragState = remember {
      AnchoredDraggableState(
        initialValue = HistoryListItemDragState.Closed,
        anchors =
          DraggableAnchors {
            HistoryListItemDragState.Closed at 0f
            HistoryListItemDragState.Opened at -maxDragPx
          },
      )
    }

    LaunchedEffect(isOpened) {
      // react to parent
      if (isOpened) {
        dragState.animateTo(HistoryListItemDragState.Opened)
      } else {
        dragState.animateTo(HistoryListItemDragState.Closed)
      }
    }

    LaunchedEffect(dragState.settledValue) {
      // react to gesture
      when (dragState.settledValue) {
        HistoryListItemDragState.Opened -> onOpened()
        HistoryListItemDragState.Closed -> onClosed()
      }
    }
    val hapticFeedbackManager = LocalHapticFeedbackManager.current
    val vibrationScope = rememberCoroutineScope()
    LaunchedEffect(dragState.targetValue, dragState.settledValue) {
      if (dragState.targetValue == dragState.settledValue) return@LaunchedEffect
      hapticFeedbackManager.vibrateGestureThresholdActivate(vibrationScope)
    }

    HistoryListItemBackgroundContent(
      modifier =
        Modifier.graphicsLayer {
            alpha =
              dragState.progress(
                HistoryListItemDragState.Closed,
                HistoryListItemDragState.Opened,
              )
          }
          .align(Alignment.CenterEnd)
          .fillMaxHeight()
          .clipToBounds()
          .layout { measurable, constraints ->
            val revealedPx =
              (-dragState.requireOffset())
                .roundToInt()
                .coerceIn(0, maxDragPx.roundToInt().coerceAtMost(constraints.maxWidth))
            val placeable =
              measurable.measure(constraints.copy(minWidth = revealedPx, maxWidth = revealedPx))
            layout(revealedPx, placeable.height) { placeable.place(0, 0) }
          }
          .padding(end = Sizes.small),
      onDelete = onDelete,
      onUpdateLabel = onUpdateLabel,
      item = item,
    )

    HistoryListItemContent(
      modifier =
        Modifier.fillMaxWidth()
          .offset { IntOffset(x = dragState.requireOffset().roundToInt(), y = 0) }
          .background(MaterialTheme.colorScheme.surfaceContainerHigh),
      item = item,
      formatterSymbols = formatterSymbols,
      addTokens = addTokens,
      onMenuClick = {
        if (isOpened) onClosed() else onOpened()
      },
      showMenuButton = showMenuButton,
      draggableState = dragState,
    )
  }
}

@Composable
private fun HistoryListItemBackgroundContent(
  modifier: Modifier,
  onDelete: () -> Unit,
  onUpdateLabel: (String) -> Unit,
  item: CalculatorHistoryModel.Item,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(Sizes.extraSmall, Alignment.End),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    var showDeleteConfirmation by rememberSaveable { mutableStateOf(false) }
    FilledTonalIconButton(
      onClick = {
        if (item.isFavorite) {
          showDeleteConfirmation = true
        } else {
          onDelete()
        }
      },
      modifier = Modifier.fillMaxHeight().weight(1f),
      shapes = IconButtonDefaults.shapes(),
    ) {
      Icon(Symbols.Delete, null, modifier = Modifier.requiredWidthIn(min = 48.dp))
    }
    if (showDeleteConfirmation) {
      HistoryListItemDeleteDialog(
        onConfirm = onDelete,
        onDismiss = { showDeleteConfirmation = false },
      )
    }
    var showLabelEditDialog by rememberSaveable { mutableStateOf(false) }
    FilledIconButton(
      onClick = { showLabelEditDialog = true },
      modifier = Modifier.fillMaxHeight().weight(1f),
      shapes = IconButtonDefaults.shapes(),
    ) {
      Icon(Symbols.Edit, null, modifier = Modifier.requiredWidthIn(min = 48.dp))
    }
    if (showLabelEditDialog) {
      HistoryListItemLabelDialog(
        onConfirm = onUpdateLabel,
        onDismiss = { showLabelEditDialog = false },
        label = item.label,
      )
    }
  }
}

@Composable
private fun HistoryListItemDeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
  AlertDialog(
    icon = { Icon(Symbols.Delete, stringResource(Res.string.calculator_delete_history_entry)) },
    title = { Text(stringResource(Res.string.calculator_delete_history_entry)) },
    text = { Text(stringResource(Res.string.calculator_delete_history_entry_support)) },
    confirmButton = {
      TextButton(onClick = onConfirm, shapes = ButtonDefaults.shapes()) {
        Text(stringResource(Res.string.common_delete))
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss, shapes = ButtonDefaults.shapes()) {
        Text(stringResource(Res.string.common_cancel))
      }
    },
    onDismissRequest = onDismiss,
  )
}

@Composable
private fun HistoryListItemLabelDialog(
  onConfirm: (String) -> Unit,
  onDismiss: () -> Unit,
  label: String?,
) {
  val focusRequester = remember { FocusRequester() }
  val textFieldState = rememberTextFieldState(label ?: "")
  AlertDialog(
    title = { Text(text = stringResource(Res.string.common_label)) },
    text = {
      OutlinedTextField(
        state = textFieldState,
        modifier = Modifier.focusRequester(focusRequester),
        shape = MaterialTheme.shapes.large,
        lineLimits = TextFieldLineLimits.SingleLine,
      )
      LaunchedEffect(Unit) {
        focusRequester.requestFocus()
      }
    },
    confirmButton = {
      TextButton(
        onClick = {
          onConfirm(textFieldState.text.toString())
          onDismiss()
        },
        content = { Text(text = stringResource(Res.string.common_ok)) },
        shapes = ButtonDefaults.shapes(),
      )
    },
    dismissButton = {
      TextButton(
        onClick = onDismiss,
        content = { Text(text = stringResource(Res.string.common_cancel)) },
        shapes = ButtonDefaults.shapes(),
      )
    },
    onDismissRequest = onDismiss,
  )
}

@Composable
private fun HistoryListItemContent(
  modifier: Modifier,
  item: CalculatorHistoryModel.Item,
  formatterSymbols: FormatterSymbols,
  addTokens: (String) -> Unit,
  onMenuClick: () -> Unit,
  showMenuButton: Boolean,
  draggableState: AnchoredDraggableState<HistoryListItemDragState>,
) {
  Row(
    modifier = modifier.heightIn(min = HistoryItemHeight),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    val mainColor = MaterialTheme.colorScheme.onSurfaceVariant
    Column(
      modifier = Modifier.weight(1f).fillMaxHeight().padding(horizontal = Sizes.small),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.End,
    ) {
      val secondaryColor = mainColor.copy(alpha = 0.65f)
      AnimatedContent(targetState = item.label, label = "Label reveal") { label ->
        if (!label.isNullOrEmpty())
          Text(
            text = label,
            color = secondaryColor,
          )
      }

      FixedExpressionInputTextField(
        modifier = Modifier.fillMaxWidth(),
        value = item.expression,
        formatterSymbols = formatterSymbols,
        textStyle =
          LocalNumberTypography.current.titleLarge.copy(
            color = secondaryColor,
            textAlign = TextAlign.End,
          ),
        onClick = { addTokens(item.expression) },
      )

      FixedExpressionInputTextField(
        modifier = Modifier.fillMaxWidth(),
        value = item.result,
        formatterSymbols = formatterSymbols,
        textStyle =
          LocalNumberTypography.current.displaySmall.copy(
            color = mainColor,
            textAlign = TextAlign.End,
          ),
        onClick = { addTokens(item.result) },
      )
    }

    AnimatedVisibility(
      visible = showMenuButton,
      modifier = Modifier.fillMaxHeight().anchoredDraggable(draggableState, Orientation.Horizontal),
    ) {
      IconButton(
        onClick = onMenuClick,
        shapes = IconButtonDefaults.shapes(),
      ) {
        Icon(
          imageVector = Symbols.MoreHoriz,
          contentDescription = stringResource(Res.string.common_delete),
          tint = mainColor,
        )
      }
    }
  }
}

internal val HistoryItemHeight = 108.dp
internal val HistoryItemButtonRowWidth = 112.dp

@Preview(showBackground = true)
@Composable
private fun PreviewHistoryListPlaceholder() {
  HistoryListPlaceholder(modifier = Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
private fun PreviewCalculatorHistoryList() {
  val calculatorHistoryItems = remember {
    val instant = Clock.System.now()
    val timestamp = instant.epochSeconds
    flowOf(
      PagingData.from(
        listOf(
          CalculatorHistoryModel.Item(
            id = 0,
            timestamp = timestamp,
            expression = "123",
            result = "45678",
            isFavorite = true,
            label = "Label content",
          ),
          CalculatorHistoryModel.Item(
            id = 1,
            timestamp = timestamp,
            expression = "123456789",
            result = "45678.123",
            isFavorite = true,
            label = null,
          ),
          CalculatorHistoryModel.Item(
            id = 3,
            timestamp = timestamp,
            expression = "123",
            result = "45678",
            isFavorite = true,
            label = "Label content",
          ),
          CalculatorHistoryModel.Header(instant),
        )
      )
    )
  }

  CalculatorHistoryList(
    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh).fillMaxSize(),
    itemsFlow = calculatorHistoryItems,
    formatterSymbols = FormatterSymbols(Token.Space, Token.Period, false),
    addTokens = {},
    onDelete = {},
    onUpdateLabel = { _, _ -> },
    showMenuButton = true,
  )
}

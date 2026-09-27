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

package com.sadellie.unitto.feature.calculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.collectAsStateWithLifecycleKMP
import com.sadellie.unitto.core.designsystem.LocalHapticFeedbackManager
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.icons.symbols.Delete
import com.sadellie.unitto.core.designsystem.icons.symbols.History
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.designsystem.theme.numberTypographyUnitto
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryModel
import com.sadellie.unitto.core.ui.BackHandler
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.feature.calculator.components.CalculatorHistoryList
import com.sadellie.unitto.feature.calculator.components.CalculatorKeyboard
import com.sadellie.unitto.feature.calculator.components.HistoryItemHeight
import com.sadellie.unitto.feature.calculator.components.TextBox
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlin.time.Clock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.calculator_clear_history
import unitto.core.common.generated.resources.calculator_clear_history_support
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_clear
import unitto.core.common.generated.resources.settings_history_view_button

@Composable
internal fun CalculatorRoute(openDrawer: () -> Unit) {
  val viewModel: CalculatorViewModel = metroViewModel()
  LaunchedEffect(Unit) { viewModel.observeInput() }

  when (val uiState = viewModel.uiState.collectAsStateWithLifecycleKMP().value) {
    CalculatorUIState.Loading -> EmptyScreen()
    is CalculatorUIState.Ready ->
      Ready(
        uiState = uiState,
        openDrawer = openDrawer,
        onAddTokenClick = viewModel::addTokens,
        onBracketsClick = viewModel::addBracket,
        onDeleteClick = viewModel::deleteTokens,
        onClearClick = viewModel::clearInput,
        onEqualClick = viewModel::onEqualClick,
        onRadianModeClick = viewModel::updateRadianMode,
        onAdditionalButtonsClick = viewModel::updateAdditionalButtons,
        onInverseModeClick = viewModel::updateInverseMode,
        onClearHistoryClick = viewModel::clearHistory,
        onDeleteHistoryItemClick = viewModel::deleteHistoryItem,
        onUpdateHistoryItemLabel = viewModel::updateHistoryItemLabel,
        updateInitialPartialHistoryView = viewModel::updateInitialPartialHistoryView,
        onHardwareInput = viewModel::onHardwareInput,
      )
  }
}

@Composable
internal fun Ready(
  uiState: CalculatorUIState.Ready,
  openDrawer: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onBracketsClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onClearClick: () -> Unit,
  onEqualClick: () -> Unit,
  onRadianModeClick: (Boolean) -> Unit,
  onAdditionalButtonsClick: (Boolean) -> Unit,
  onInverseModeClick: (Boolean) -> Unit,
  onClearHistoryClick: () -> Unit,
  onDeleteHistoryItemClick: (CalculatorHistoryModel.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryModel.Item, String) -> Unit,
  updateInitialPartialHistoryView: (Boolean) -> Unit,
  onHardwareInput: () -> Unit,
) {
  val windowSizeClass = LocalWindowSize.current
  if (
    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
      windowSizeClass.heightSizeClass >= WindowHeightSizeClass.Medium
  ) {
    ReadyExpanded(
      uiState = uiState,
      onAddTokenClick = onAddTokenClick,
      onBracketsClick = onBracketsClick,
      onDeleteClick = onDeleteClick,
      onClearClick = onClearClick,
      onEqualClick = onEqualClick,
      onRadianModeClick = onRadianModeClick,
      onAdditionalButtonsClick = onAdditionalButtonsClick,
      onInverseModeClick = onInverseModeClick,
      onClearHistoryClick = onClearHistoryClick,
      onDeleteHistoryItemClick = onDeleteHistoryItemClick,
      onUpdateHistoryItemLabel = onUpdateHistoryItemLabel,
      onHardwareInput = onHardwareInput,
    )
  } else {
    ReadyCompact(
      uiState = uiState,
      openDrawer = openDrawer,
      onAddTokenClick = onAddTokenClick,
      onBracketsClick = onBracketsClick,
      onDeleteClick = onDeleteClick,
      onClearClick = onClearClick,
      onEqualClick = onEqualClick,
      onRadianModeClick = onRadianModeClick,
      onAdditionalButtonsClick = onAdditionalButtonsClick,
      onInverseModeClick = onInverseModeClick,
      onClearHistoryClick = onClearHistoryClick,
      onDeleteHistoryItemClick = onDeleteHistoryItemClick,
      updateInitialPartialHistoryView = updateInitialPartialHistoryView,
      onUpdateHistoryItemLabel = onUpdateHistoryItemLabel,
      onHardwareInput = onHardwareInput,
    )
  }
}

@Composable
private fun ReadyCompact(
  uiState: CalculatorUIState.Ready,
  openDrawer: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onBracketsClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onClearClick: () -> Unit,
  onEqualClick: () -> Unit,
  onRadianModeClick: (Boolean) -> Unit,
  onAdditionalButtonsClick: (Boolean) -> Unit,
  onInverseModeClick: (Boolean) -> Unit,
  onClearHistoryClick: () -> Unit,
  onDeleteHistoryItemClick: (CalculatorHistoryModel.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryModel.Item, String) -> Unit,
  updateInitialPartialHistoryView: (Boolean) -> Unit,
  onHardwareInput: () -> Unit,
) {
  val focusManager = LocalFocusManager.current
  val dragState = remember {
    val initialValue =
      if (uiState.partialHistoryView && uiState.initialPartialHistoryView) DragState.PARTIAL
      else DragState.CLOSED
    AnchoredDraggableState(initialValue)
  }
  val isOpen = remember(dragState.currentValue) { dragState.currentValue == DragState.OPEN }
  val draggableScope = rememberCoroutineScope()
  val dragAnimationSpec = MaterialTheme.motionScheme.defaultSpatialSpec<Float>()
  // true if it goes from CLOSED to OPEN
  var isExpanding by rememberSaveable { mutableStateOf(true) }
  LaunchedEffect(dragState.currentValue) {
    focusManager.clearFocus()
    when (dragState.currentValue) {
      DragState.CLOSED -> isExpanding = true
      DragState.OPEN -> isExpanding = false
      DragState.PARTIAL -> Unit
    }
  }
  BackHandler(dragState.currentValue != DragState.CLOSED) {
    draggableScope.launch {
      dragState.toggleDragState(isExpanding = false, dragAnimationSpec = dragAnimationSpec)
    }
  }

  ScaffoldWithTopBar(
    title = {},
    navigationIcon = { DrawerButton(onClick = openDrawer) },
    colors =
      TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    actions = {
      ReadyCompactActions(
        isOpen = isOpen,
        onClearHistoryClick = onClearHistoryClick,
        onOpenHistoryView = {
          draggableScope.launch {
            dragState.toggleDragState(
              isExpanding = isExpanding,
              dragAnimationSpec = dragAnimationSpec,
            )
          }
        },
        openHistoryViewButton = uiState.openHistoryViewButton,
      )
    },
  ) { paddingValues ->
    LiquidCalculatorView(
      modifier = Modifier.padding(paddingValues),
      calculatorHistory = { height ->
        CalculatorHistoryList(
          modifier =
            Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
              .fillMaxWidth()
              .deferredHeight(height),
          itemsFlow = uiState.history,
          formatterSymbols = uiState.formatterSymbols,
          addTokens = onAddTokenClick,
          onDelete = onDeleteHistoryItemClick,
          onUpdateLabel = onUpdateHistoryItemLabel,
          showMenuButton = isOpen,
        )
      },
      textBox = { offset, height ->
        TextBox(
          modifier =
            Modifier.offset(offset)
              .height(height)
              .fillMaxWidth()
              .anchoredDraggable(
                state = dragState,
                orientation = Orientation.Vertical,
                flingBehavior = liquidFlingBehaviour(dragState),
              ),
          formatterSymbols = uiState.formatterSymbols,
          state = uiState.input,
          output = uiState.output,
          onEnter = onEqualClick,
          showHandle = true,
          onHardwareInput = onHardwareInput,
        )
      },
      keyboard = { offset, height ->
        CalculatorKeyboard(
          modifier =
            Modifier.semantics { testTag = "ready" }
              .offset(offset)
              .deferredHeight(height)
              .fillMaxWidth()
              .padding(horizontal = Sizes.small, vertical = Sizes.extraSmall),
          onAddTokenClick = onAddTokenClick,
          onBracketsClick = onBracketsClick,
          onDeleteClick = onDeleteClick,
          onClearClick = onClearClick,
          onEqualClick = {
            focusManager.clearFocus()
            onEqualClick()
          },
          radianMode = uiState.radianMode,
          onRadianModeClick = onRadianModeClick,
          additionalButtons = uiState.additionalButtons,
          onAdditionalButtonsClick = onAdditionalButtonsClick,
          inverseMode = uiState.inverseMode,
          onInverseModeClick = onInverseModeClick,
          showAcButton = uiState.acButton,
          middleZero = uiState.middleZero,
          fractional = uiState.formatterSymbols.fractional,
        )
      },
      partialHistoryView = uiState.partialHistoryView,
      steppedPartialHistoryView = uiState.steppedPartialHistoryView,
      updateInitialPartialHistoryView = updateInitialPartialHistoryView,
      dragState = dragState,
    )
  }
}

@Composable
private fun ReadyExpanded(
  uiState: CalculatorUIState.Ready,
  onAddTokenClick: (String) -> Unit,
  onBracketsClick: () -> Unit,
  onDeleteClick: () -> Unit,
  onClearClick: () -> Unit,
  onEqualClick: () -> Unit,
  onRadianModeClick: (Boolean) -> Unit,
  onAdditionalButtonsClick: (Boolean) -> Unit,
  onInverseModeClick: (Boolean) -> Unit,
  onClearHistoryClick: () -> Unit,
  onDeleteHistoryItemClick: (CalculatorHistoryModel.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryModel.Item, String) -> Unit,
  onHardwareInput: () -> Unit,
) {

  Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainer) { paddingValues ->
    Row(
      modifier = Modifier.padding(paddingValues).consumeWindowInsets(paddingValues).fillMaxSize()
    ) {
      CalculatorHistoryList(
        modifier =
          Modifier.weight(2f)
            .fillMaxHeight()
            .padding(Sizes.small)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        itemsFlow = uiState.history,
        formatterSymbols = uiState.formatterSymbols,
        addTokens = onAddTokenClick,
        onDelete = onDeleteHistoryItemClick,
        onUpdateLabel = onUpdateHistoryItemLabel,
        showMenuButton = true,
      )
      ScaffoldWithTopBar(
        modifier = Modifier.weight(3f),
        title = {},
        navigationIcon = {},
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
          ),
        actions = {
          ReadyExpandedActions(onClearHistoryClick = onClearHistoryClick)
        },
      ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxHeight()) {
          TextBox(
            modifier = Modifier.fillMaxHeight(TEXT_BOX_HEIGHT_FACTOR_COMPACT).fillMaxWidth(),
            formatterSymbols = uiState.formatterSymbols,
            state = uiState.input,
            output = uiState.output,
            onEnter = onEqualClick,
            showHandle = false,
            onHardwareInput = onHardwareInput,
          )
          val focusManager = LocalFocusManager.current
          CalculatorKeyboard(
            modifier = Modifier.padding(Sizes.large).weight(1f).fillMaxWidth(),
            onAddTokenClick = onAddTokenClick,
            onBracketsClick = onBracketsClick,
            onDeleteClick = onDeleteClick,
            onClearClick = onClearClick,
            onEqualClick = {
              focusManager.clearFocus()
              onEqualClick()
            },
            radianMode = uiState.radianMode,
            onRadianModeClick = onRadianModeClick,
            additionalButtons = uiState.additionalButtons,
            onAdditionalButtonsClick = onAdditionalButtonsClick,
            inverseMode = uiState.inverseMode,
            onInverseModeClick = onInverseModeClick,
            showAcButton = uiState.acButton,
            middleZero = uiState.middleZero,
            fractional = uiState.formatterSymbols.fractional,
          )
        }
      }
    }
  }
}

@Composable
private fun LiquidCalculatorView(
  modifier: Modifier,
  calculatorHistory: @Composable (height: () -> Dp) -> Unit,
  textBox: @Composable (offset: Density.() -> IntOffset, height: Dp) -> Unit,
  keyboard: @Composable (offset: Density.() -> IntOffset, height: () -> Dp) -> Unit,
  partialHistoryView: Boolean,
  steppedPartialHistoryView: Boolean,
  updateInitialPartialHistoryView: (Boolean) -> Unit,
  dragState: AnchoredDraggableState<DragState>,
) =
  BoxWithConstraints(modifier) {
    val density = LocalDensity.current
    val isCompact = LocalWindowSize.current.heightSizeClass == WindowHeightSizeClass.Compact
    val textBoxHeight =
      maxHeight * if (isCompact) TEXT_BOX_HEIGHT_FACTOR_COMPACT else TEXT_BOX_HEIGHT_FACTOR_EXPANDED

    LaunchedEffect(
      partialHistoryView,
      steppedPartialHistoryView,
      textBoxHeight,
      maxHeight,
      dragState.settledValue,
    ) {
      dragState.updateAnchors(
        updateDraggableAnchors(
          density = density,
          partialHistoryView = partialHistoryView,
          steppedPartialHistoryView = steppedPartialHistoryView,
          textBoxHeight = textBoxHeight,
          maxHeight = maxHeight,
          settledValue = dragState.settledValue,
        )
      )
      dragState.settle(snap())
    }

    // deferred calculations
    val historyHeight: () -> Dp =
      remember(dragState, density) {
        {
          val offset = dragState.offset
          if (offset.isNaN()) 0.dp else with(density) { offset.toDp() }
        }
      }
    val keyboardHeight: () -> Dp =
      remember(dragState, density, textBoxHeight, maxHeight) {
        {
          val offset = dragState.offset
          val h = if (offset.isNaN()) 0.dp else with(density) { offset.toDp() }
          maxHeight - textBoxHeight - minOf(h, HistoryItemHeight)
        }
      }
    val textBoxOffset: Density.() -> IntOffset =
      remember(historyHeight) {
        { IntOffset(0, historyHeight().roundToPx()) }
      }
    val keyboardOffset: Density.() -> IntOffset =
      remember(historyHeight, textBoxHeight) {
        { IntOffset(0, (historyHeight() + textBoxHeight).roundToPx()) }
      }

    val hapticFeedbackManager = LocalHapticFeedbackManager.current
    val vibrationScope = rememberCoroutineScope()
    LaunchedEffect(dragState.targetValue, dragState.settledValue) {
      if (dragState.targetValue == dragState.settledValue) return@LaunchedEffect
      hapticFeedbackManager.vibrateGestureThresholdActivate(vibrationScope)
    }
    LaunchedEffect(dragState.settledValue) {
      delay(REMEMBER_PARTIAL_HISTORY_VIEW_STATE_DELAY_MS)
      updateInitialPartialHistoryView(dragState.settledValue == DragState.PARTIAL)
    }

    calculatorHistory(historyHeight)
    textBox(textBoxOffset, textBoxHeight)
    keyboard(keyboardOffset, keyboardHeight)
  }

private fun Modifier.deferredHeight(height: () -> Dp): Modifier =
  layout { measurable, constraints ->
    val h = height().roundToPx().coerceAtLeast(0)
    val placeable = measurable.measure(constraints.copy(minHeight = h, maxHeight = h))
    layout(placeable.width, h) {
      placeable.place(0, 0)
    }
  }

@Composable
private fun ClearHistoryDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
  AlertDialog(
    icon = { Icon(Symbols.Delete, stringResource(Res.string.calculator_clear_history)) },
    title = { Text(stringResource(Res.string.calculator_clear_history)) },
    text = { Text(stringResource(Res.string.calculator_clear_history_support)) },
    confirmButton = {
      TextButton(onClick = onConfirm, shapes = ButtonDefaults.shapes()) {
        Text(stringResource(Res.string.common_clear))
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

private fun updateDraggableAnchors(
  density: Density,
  partialHistoryView: Boolean,
  steppedPartialHistoryView: Boolean,
  textBoxHeight: Dp,
  maxHeight: Dp,
  settledValue: DragState,
): DraggableAnchors<DragState> =
  with(density) {
    DraggableAnchors {
      when {
        partialHistoryView && steppedPartialHistoryView ->
          when (settledValue) {
            DragState.CLOSED -> {
              DragState.CLOSED at 0f
              DragState.PARTIAL at HistoryItemHeight.toPx()
            }

            DragState.PARTIAL -> {
              DragState.CLOSED at 0f
              DragState.PARTIAL at HistoryItemHeight.toPx()
              DragState.OPEN at (maxHeight - textBoxHeight).toPx()
            }

            DragState.OPEN -> {
              DragState.PARTIAL at HistoryItemHeight.toPx()
              DragState.OPEN at (maxHeight - textBoxHeight).toPx()
            }
          }

        partialHistoryView -> {
          DragState.CLOSED at 0f
          DragState.PARTIAL at HistoryItemHeight.toPx()
          DragState.OPEN at (maxHeight - textBoxHeight).toPx()
        }

        else -> {
          DragState.CLOSED at 0f
          DragState.OPEN at (maxHeight - textBoxHeight).toPx()
        }
      }
    }
  }

@Composable
private fun liquidFlingBehaviour(dragState: AnchoredDraggableState<DragState>) =
  AnchoredDraggableDefaults.flingBehavior(
    state = dragState,
    animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
  )

@Composable
private fun ClearHistoryButton(onClick: () -> Unit) {
  IconButton(
    onClick = onClick,
    shapes = IconButtonDefaults.shapes(),
    content = {
      Icon(
        imageVector = Symbols.Delete,
        contentDescription = stringResource(Res.string.calculator_clear_history),
        modifier = Modifier.size(IconButtonDefaults.mediumIconSize),
      )
    },
    modifier =
      Modifier.semantics { testTag = "historyButton" }
        .size(
          IconButtonDefaults.smallContainerSize(IconButtonDefaults.IconButtonWidthOption.Uniform)
        ),
  )
}

@Composable
private fun OpenHistoryViewButton(onClick: () -> Unit, isOpen: Boolean) {
  IconButton(onClick = onClick, shapes = IconButtonDefaults.shapes()) {
    val rotation =
      animateFloatAsState(
        targetValue = if (isOpen) 360f else 0f,
        animationSpec = MaterialTheme.motionScheme.defaultEffectsSpec(),
        label = "Open history view",
      )
    Icon(
      imageVector = Symbols.History,
      contentDescription = stringResource(Res.string.settings_history_view_button),
      modifier = Modifier.rotate(rotation.value),
    )
  }
}

@Composable
private fun ReadyCompactActions(
  isOpen: Boolean,
  onOpenHistoryView: () -> Unit,
  onClearHistoryClick: () -> Unit,
  openHistoryViewButton: Boolean,
) {
  var showClearHistoryDialog by rememberSaveable { mutableStateOf(false) }
  val transitionSpec = MaterialTheme.motionScheme.defaultEffectsSpec<Float>()
  AnimatedVisibility(
    visible = isOpen,
    label = "History buttons reveal",
    enter = fadeIn(transitionSpec) + scaleIn(transitionSpec, 0.5f),
    exit = fadeOut(transitionSpec) + scaleOut(transitionSpec, 0.5f),
  ) {
    Row {
      ClearHistoryButton(onClick = { showClearHistoryDialog = true })
    }
  }

  if (showClearHistoryDialog) {
    ClearHistoryDialog(
      onConfirm = {
        onClearHistoryClick()
        showClearHistoryDialog = false
      },
      onDismiss = { showClearHistoryDialog = false },
    )
  }

  if (openHistoryViewButton) {
    OpenHistoryViewButton(
      onClick = onOpenHistoryView,
      isOpen = isOpen,
    )
  }
}

@Composable
private fun ReadyExpandedActions(onClearHistoryClick: () -> Unit) {
  var showClearHistoryDialog by rememberSaveable { mutableStateOf(false) }
  ClearHistoryButton(onClick = { showClearHistoryDialog = true })
  if (showClearHistoryDialog) {
    ClearHistoryDialog(
      onConfirm = {
        onClearHistoryClick()
        showClearHistoryDialog = false
      },
      onDismiss = { showClearHistoryDialog = false },
    )
  }
}

private suspend fun AnchoredDraggableState<DragState>.toggleDragState(
  isExpanding: Boolean,
  dragAnimationSpec: AnimationSpec<Float>,
) {
  val target =
    when (this.currentValue) {
      DragState.CLOSED ->
        if (this.anchors.hasPositionFor(DragState.PARTIAL)) DragState.PARTIAL else DragState.OPEN
      DragState.PARTIAL -> if (isExpanding) DragState.OPEN else DragState.CLOSED
      DragState.OPEN ->
        if (this.anchors.hasPositionFor(DragState.PARTIAL)) DragState.PARTIAL else DragState.CLOSED
    }

  if (this.anchors.hasPositionFor(target)) {
    this.animateTo(target, dragAnimationSpec)
  }
}

private const val TEXT_BOX_HEIGHT_FACTOR_COMPACT = 0.4f
private const val TEXT_BOX_HEIGHT_FACTOR_EXPANDED = 0.25f
private const val REMEMBER_PARTIAL_HISTORY_VIEW_STATE_DELAY_MS = 1_000L

@Preview(widthDp = 432, heightDp = 1008, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(widthDp = 432, heightDp = 864, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(widthDp = 597, heightDp = 1393, device = "spec:parent=pixel_5,orientation=portrait")
@Preview(heightDp = 432, widthDp = 1008, device = "spec:parent=pixel_5,orientation=landscape")
@Preview(heightDp = 432, widthDp = 864, device = "spec:parent=pixel_5,orientation=landscape")
@Preview(heightDp = 597, widthDp = 1393, device = "spec:parent=pixel_5,orientation=landscape")
@Preview(heightDp = 800, widthDp = 1600, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun PreviewCalculatorScreen() {
  val calculatorHistoryItems = remember {
    flowOf(
      PagingData.from(
        List(3) {
          CalculatorHistoryModel.Item(
            id = it,
            timestamp = Clock.System.now().epochSeconds,
            expression = "123".repeat(1 * it + 1),
            result = "45678",
            isFavorite = it % 2 == 0,
            label = if (it % 3 == 0) "Label content" else null,
          ) as CalculatorHistoryModel
        }
      )
    )
  }

  BoxWithConstraints(Modifier.fillMaxSize()) {
    val dpSize = DpSize(this.minWidth, this.minHeight)
    val windowSizeClass = WindowSizeClass.calculateFromSize(dpSize)
    CompositionLocalProvider(
      LocalWindowSize provides windowSizeClass,
      LocalNumberTypography provides numberTypographyUnitto(),
    ) {
      Ready(
        uiState =
          CalculatorUIState.Ready(
            input = TextFieldState("1.2345"),
            output = CalculationResult.Success("1234"),
            radianMode = false,
            precision = 3,
            outputFormat = OutputFormat.PLAIN,
            formatterSymbols = FormatterSymbols(Token.Space, Token.Period, false),
            history = calculatorHistoryItems,
            middleZero = false,
            acButton = true,
            additionalButtons = false,
            inverseMode = false,
            partialHistoryView = true,
            steppedPartialHistoryView = true,
            initialPartialHistoryView = false,
            openHistoryViewButton = true,
          ),
        openDrawer = {},
        onAddTokenClick = {},
        onBracketsClick = {},
        onDeleteClick = {},
        onClearClick = {},
        onEqualClick = {},
        onRadianModeClick = {},
        onAdditionalButtonsClick = {},
        onInverseModeClick = {},
        onClearHistoryClick = {},
        onDeleteHistoryItemClick = {},
        onUpdateHistoryItemLabel = { _, _ -> },
        updateInitialPartialHistoryView = {},
        onHardwareInput = {},
      )
    }
  }
}

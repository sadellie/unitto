/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import com.sadellie.unitto.core.model.calculator.CalculatorHistoryItem
import com.sadellie.unitto.core.ui.BackHandler
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.feature.calculator.components.CalculatorHistoryList
import com.sadellie.unitto.feature.calculator.components.CalculatorKeyboard
import com.sadellie.unitto.feature.calculator.components.HistoryItemHeight
import com.sadellie.unitto.feature.calculator.components.TextBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.calculator_clear_history
import unitto.core.common.generated.resources.calculator_clear_history_support
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_clear
import unitto.core.common.generated.resources.settings_history_view_button

@Composable
internal fun CalculatorRoute(openDrawer: () -> Unit) {
  val viewModel: CalculatorViewModel = koinViewModel()
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
        updateInitialPartialHistoryView = viewModel::updateInitialPartialHistoryView,
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
  onDeleteHistoryItemClick: (CalculatorHistoryItem) -> Unit,
  updateInitialPartialHistoryView: (Boolean) -> Unit,
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
  onDeleteHistoryItemClick: (CalculatorHistoryItem) -> Unit,
  updateInitialPartialHistoryView: (Boolean) -> Unit,
) {
  val focusManager = LocalFocusManager.current
  var showClearHistoryDialog by rememberSaveable { mutableStateOf(false) }
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
      val target = dragState.previousState() ?: return@launch
      dragState.animateTo(target, dragAnimationSpec)
    }
  }

  ScaffoldWithTopBar(
    title = {},
    navigationIcon = { DrawerButton(onClick = openDrawer) },
    colors =
      TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    actions = {
      ClearHistoryButton(onClick = { showClearHistoryDialog = true }, isOpen = isOpen)

      if (uiState.openHistoryViewButton) {
        OpenHistoryViewButton(
          onClick = {
            draggableScope.launch {
              val target = if (isExpanding) dragState.nextState() else dragState.previousState()
              if (target == null) return@launch
              // change direction when hit edges
              if (target == DragState.OPEN) isExpanding = false
              if (target == DragState.CLOSED) isExpanding = true
              dragState.animateTo(target, dragAnimationSpec)
            }
          },
          isOpen = isOpen,
        )
      }
    },
  ) { paddingValues ->
    LiquidCalculatorView(
      modifier = Modifier.padding(paddingValues),
      calculatorHistory = { height ->
        CalculatorHistoryList(
          modifier =
            Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
              .fillMaxWidth()
              .height(height),
          calculatorHistoryItems = uiState.history,
          formatterSymbols = uiState.formatterSymbols,
          addTokens = onAddTokenClick,
          onDelete = onDeleteHistoryItemClick,
          showDeleteButtons = isOpen,
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
          showHandle = true,
        )
      },
      keyboard = { offset, height ->
        CalculatorKeyboard(
          modifier =
            Modifier.semantics { testTag = "ready" }
              .offset(offset)
              .height(height)
              .fillMaxWidth()
              .padding(horizontal = 8.dp, vertical = 4.dp),
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
  onDeleteHistoryItemClick: (CalculatorHistoryItem) -> Unit,
) {
  var showClearHistoryDialog by rememberSaveable { mutableStateOf(false) }
  Scaffold(containerColor = MaterialTheme.colorScheme.surfaceContainer) { paddingValues ->
    Row(
      modifier = Modifier.padding(paddingValues).consumeWindowInsets(paddingValues).fillMaxSize()
    ) {
      CalculatorHistoryList(
        modifier = Modifier.weight(2f).fillMaxHeight(),
        calculatorHistoryItems = uiState.history,
        formatterSymbols = uiState.formatterSymbols,
        addTokens = onAddTokenClick,
        onDelete = onDeleteHistoryItemClick,
        showDeleteButtons = true,
      )
      ScaffoldWithTopBar(
        modifier = Modifier.weight(3f),
        title = {},
        navigationIcon = {},
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
          ),
        actions = { ClearHistoryButton(onClick = { showClearHistoryDialog = true }, isOpen = true) },
      ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxHeight()) {
          TextBox(
            modifier = Modifier.fillMaxHeight(TEXT_BOX_HEIGHT_FACTOR_COMPACT).fillMaxWidth(),
            formatterSymbols = uiState.formatterSymbols,
            state = uiState.input,
            output = uiState.output,
            showHandle = false,
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

@Composable
private fun LiquidCalculatorView(
  modifier: Modifier,
  calculatorHistory: @Composable (height: Dp) -> Unit,
  textBox: @Composable (offset: Density.() -> IntOffset, height: Dp) -> Unit,
  keyboard: @Composable (offset: Density.() -> IntOffset, height: Dp) -> Unit,
  partialHistoryView: Boolean,
  steppedPartialHistoryView: Boolean,
  updateInitialPartialHistoryView: (Boolean) -> Unit,
  dragState: AnchoredDraggableState<DragState>,
) =
  BoxWithConstraints(modifier) {
    val density = LocalDensity.current
    val textBoxHeight =
      if (LocalWindowSize.current.heightSizeClass == WindowHeightSizeClass.Compact) {
        maxHeight * TEXT_BOX_HEIGHT_FACTOR_COMPACT
      } else {
        maxHeight * TEXT_BOX_HEIGHT_FACTOR_EXPANDED
      }
    var historyListHeight by remember { mutableStateOf(0.dp) }
    val keyboardHeight by
      remember(historyListHeight, textBoxHeight) {
        derivedStateOf {
          if (historyListHeight > HistoryItemHeight) {
            maxHeight - textBoxHeight - HistoryItemHeight
          } else {
            maxHeight - textBoxHeight - historyListHeight
          }
        }
      }

    LaunchedEffect(partialHistoryView, textBoxHeight, dragState.settledValue) {
      val anchors: DraggableAnchors<DragState> =
        updateDraggableAnchors(
          density = density,
          partialHistoryView = partialHistoryView,
          steppedPartialHistoryView = steppedPartialHistoryView,
          textBoxHeight = textBoxHeight,
          maxHeight = maxHeight,
          settledValue = dragState.settledValue,
        )
      dragState.updateAnchors(anchors)
      // reset last velocity. otherwise text fields become untouchable
      dragState.settle(snap())
    }

    LaunchedEffect(dragState.offset) {
      with(density) {
        if (!dragState.offset.isNaN()) {
          historyListHeight = dragState.requireOffset().toDp()
        }
      }
    }

    val hapticFeedbackManager = LocalHapticFeedbackManager.current
    val vibrationScope = rememberCoroutineScope()
    LaunchedEffect(dragState.targetValue, dragState.settledValue) {
      if (dragState.targetValue != dragState.settledValue) {
        hapticFeedbackManager.vibrateGestureThresholdActivate(vibrationScope)
      }
    }

    LaunchedEffect(dragState.settledValue) {
      // save partial history view state
      delay(REMEMBER_PARTIAL_HISTORY_VIEW_STATE_DELAY_MS)
      updateInitialPartialHistoryView(dragState.settledValue == DragState.PARTIAL)
    }

    calculatorHistory(historyListHeight)
    textBox({ IntOffset(0, historyListHeight.roundToPx()) }, textBoxHeight)
    keyboard({ IntOffset(0, (historyListHeight + textBoxHeight).roundToPx()) }, keyboardHeight)
  }

@Composable
private fun ClearHistoryDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
  AlertDialog(
    icon = { Icon(Symbols.Delete, stringResource(Res.string.calculator_clear_history)) },
    title = { Text(stringResource(Res.string.calculator_clear_history)) },
    text = { Text(stringResource(Res.string.calculator_clear_history_support)) },
    confirmButton = {
      TextButton(onClick = onConfirm) { Text(stringResource(Res.string.common_clear)) }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) { Text(stringResource(Res.string.common_cancel)) }
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
private fun ClearHistoryButton(onClick: () -> Unit, isOpen: Boolean) {
  AnimatedVisibility(
    visible = isOpen,
    label = "Clear history button reveal",
    enter = fadeIn() + expandHorizontally(),
    exit = fadeOut() + shrinkHorizontally(),
  ) {
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
}

@Composable
private fun OpenHistoryViewButton(onClick: () -> Unit, isOpen: Boolean) {
  IconButton(onClick = onClick) {
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

private fun AnchoredDraggableState<DragState>.previousState(): DragState? {
  if (anchors.size == 0) return null
  val minPosition = anchors.minPosition()
  if (minPosition.isNaN()) return null
  return anchors.closestAnchor(position = minPosition)
}

private fun AnchoredDraggableState<DragState>.nextState(): DragState? {
  if (anchors.size == 0) return null
  val maxPosition = anchors.maxPosition()
  if (maxPosition.isNaN()) return null
  return anchors.closestAnchor(position = maxPosition)
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
  val calculatorHistoryItems =
    listOf(
        "13.06.1989 23:59:15",
        "13.06.1989 23:59:16",
        "13.06.1989 23:59:17",
        "14.06.1989 23:59:17",
        "14.06.1989 23:59:18",
        "14.07.1989 23:59:18",
        "14.07.1989 23:59:19",
        "14.07.2005 23:59:19",
      )
      .map {
        CalculatorHistoryItem(id = it.hashCode(), expression = "12345".repeat(10), result = "1234")
      }

  BoxWithConstraints(Modifier.fillMaxSize()) {
    val dpSize = DpSize(this.minWidth, this.minHeight)
    val windowSizeClass = WindowSizeClass.calculateFromSize(dpSize)
    CompositionLocalProvider(LocalWindowSize provides windowSizeClass) {
      Ready(
        uiState =
          CalculatorUIState.Ready(
            input = TextFieldState("1.2345"),
            output = CalculationResult.Success("1234"),
            radianMode = false,
            precision = 3,
            outputFormat = OutputFormat.PLAIN,
            formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
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
        updateInitialPartialHistoryView = {},
      )
    }
  }
}

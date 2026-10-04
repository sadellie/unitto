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

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.OutputFormat
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography
import com.sadellie.unitto.core.designsystem.theme.numberTypographyUnitto
import com.sadellie.unitto.core.ui.BackHandler
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.calculators.CalculationResult
import com.sadellie.unitto.core.ui.calculators.CalculatorHistoryList
import com.sadellie.unitto.core.ui.calculators.CalculatorHistoryListItem
import com.sadellie.unitto.core.ui.calculators.CalculatorTextBoxDefaults
import com.sadellie.unitto.core.ui.calculators.CalculatorWithHistoryLayoutDefault
import com.sadellie.unitto.core.ui.calculators.ClearHistoryButton
import com.sadellie.unitto.core.ui.calculators.DragState
import com.sadellie.unitto.core.ui.calculators.LiquidCalculatorView
import com.sadellie.unitto.core.ui.calculators.OpenHistoryViewButton
import com.sadellie.unitto.core.ui.calculators.TextBox
import com.sadellie.unitto.core.ui.calculators.deferredHeight
import com.sadellie.unitto.core.ui.calculators.liquidFlingBehaviour
import com.sadellie.unitto.core.ui.calculators.toggleDragState
import com.sadellie.unitto.core.ui.textfield.ExpressionTextField
import com.sadellie.unitto.feature.calculator.components.CalculatorKeyboard
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.time.Clock

@Composable
internal fun CalculatorRoute(openDrawer: () -> Unit) {
  val viewModel: CalculatorViewModel = metroViewModel()
  LaunchedEffect(Unit) { viewModel.observeInput() }

  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
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
  onDeleteHistoryItemClick: (CalculatorHistoryListItem.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryListItem.Item, String) -> Unit,
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
  onDeleteHistoryItemClick: (CalculatorHistoryListItem.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryListItem.Item, String) -> Unit,
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
        CalculatorTextBox(
          modifier =
            Modifier.offset(offset)
              .height(height)
              .fillMaxWidth()
              .anchoredDraggable(
                state = dragState,
                orientation = Orientation.Vertical,
                flingBehavior = dragState.liquidFlingBehaviour(),
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
  onDeleteHistoryItemClick: (CalculatorHistoryListItem.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryListItem.Item, String) -> Unit,
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
          CalculatorTextBox(
            modifier =
              Modifier.fillMaxHeight(
                  CalculatorWithHistoryLayoutDefault.TEXT_BOX_HEIGHT_FACTOR_COMPACT
                )
                .fillMaxWidth(),
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
private fun ReadyCompactActions(
  isOpen: Boolean,
  onOpenHistoryView: () -> Unit,
  onClearHistoryClick: () -> Unit,
  openHistoryViewButton: Boolean,
) {
  ClearHistoryButton(onClearHistoryClick, isOpen)

  if (openHistoryViewButton) {
    OpenHistoryViewButton(
      onClick = onOpenHistoryView,
      isOpen = isOpen,
    )
  }
}

@Composable
private fun ReadyExpandedActions(onClearHistoryClick: () -> Unit) {
  ClearHistoryButton(onClearHistoryClick, true)
}

@Composable
private fun CalculatorTextBox(
  modifier: Modifier,
  formatterSymbols: FormatterSymbols,
  state: TextFieldState,
  output: CalculationResult,
  onEnter: () -> Unit,
  showHandle: Boolean,
  onHardwareInput: (() -> Unit)?,
) {
  TextBox(
    modifier = modifier,
    formatterSymbols = formatterSymbols,
    input = {
      ExpressionTextField(
        modifier = Modifier.fillMaxWidth(),
        state = state,
        minRatio = CalculatorTextBoxDefaults.INPUT_TEXT_FIELD_MIN_RATIO,
        formatterSymbols = formatterSymbols,
        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onKeyboardAction =
          KeyboardActionHandler {
            onEnter()
            it()
          },
        onHardwareInput = onHardwareInput,
      )
    },
    output = output,
    showHandle = showHandle,
  )
}

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
          CalculatorHistoryListItem.Item(
            id = it,
            timestamp = Clock.System.now().epochSeconds,
            expression = "123".repeat(1 * it + 1),
            result = "45678",
            isFavorite = it % 2 == 0,
            label = if (it % 3 == 0) "Label content" else null,
          ) as CalculatorHistoryListItem
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

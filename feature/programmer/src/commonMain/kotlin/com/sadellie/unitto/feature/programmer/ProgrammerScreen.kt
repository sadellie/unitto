/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package com.sadellie.unitto.feature.programmer

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.sadellie.unitto.core.common.DataUnit
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import com.sadellie.unitto.core.model.programmer.ShiftType
import com.sadellie.unitto.core.ui.BackHandler
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.core.ui.calculators.CalculationResult
import com.sadellie.unitto.core.ui.calculators.CalculatorHistoryList
import com.sadellie.unitto.core.ui.calculators.CalculatorHistoryListItem
import com.sadellie.unitto.core.ui.calculators.CalculatorWithHistoryLayoutDefault
import com.sadellie.unitto.core.ui.calculators.ClearHistoryButton
import com.sadellie.unitto.core.ui.calculators.DragState
import com.sadellie.unitto.core.ui.calculators.LiquidCalculatorView
import com.sadellie.unitto.core.ui.calculators.OpenHistoryViewButton
import com.sadellie.unitto.core.ui.calculators.deferredHeight
import com.sadellie.unitto.core.ui.calculators.liquidFlingBehaviour
import com.sadellie.unitto.core.ui.calculators.toggleDragState
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.time.Clock

@Composable
internal fun ProgrammerRoute(openDrawer: () -> Unit) {
  val viewModel: ProgrammerViewModel = metroViewModel()
  LaunchedEffect(Unit) { viewModel.observeInput() }

  when (val uiState = viewModel.uiState.collectAsStateWithLifecycle().value) {
    ProgrammerScreenUIState.Loading -> EmptyScreen()
    is ProgrammerScreenUIState.Ready ->
      ProgrammerScreen(
        uiState = uiState,
        openDrawer = openDrawer,
        onClearClick = viewModel::cleanInput,
        onBracketsClick = viewModel::addBracket,
        onAddTokenClick = viewModel::addTokens,
        onDeleteClick = viewModel::deleteTokens,
        onEqualClick = viewModel::onEqual,
        toggleSize = viewModel::toggleSize,
        toggleBase = viewModel::toggleBase,
        toggleShiftType = viewModel::toggleShiftType,
        onClearHistoryClick = viewModel::clearHistory,
        onDeleteHistoryItemClick = viewModel::deleteHistoryItem,
        onUpdateHistoryItemLabel = viewModel::updateHistoryItemLabel,
        updateInitialPartialHistoryView = viewModel::updateInitialPartialHistoryView,
      )
  }
}

@Composable
private fun ProgrammerScreen(
  uiState: ProgrammerScreenUIState.Ready,
  openDrawer: () -> Unit,
  onClearClick: () -> Unit,
  onBracketsClick: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onDeleteClick: () -> Unit,
  onEqualClick: () -> Unit,
  toggleSize: () -> Unit,
  toggleBase: () -> Unit,
  toggleShiftType: () -> Unit,
  onClearHistoryClick: () -> Unit,
  onDeleteHistoryItemClick: (CalculatorHistoryListItem.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryListItem.Item, String) -> Unit,
  updateInitialPartialHistoryView: (Boolean) -> Unit,
) {
  val windowSizeClass = LocalWindowSize.current
  if (
    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
      windowSizeClass.heightSizeClass >= WindowHeightSizeClass.Medium
  ) {
    ProgrammerScreenExpanded(
      uiState = uiState,
      onClearClick = onClearClick,
      onBracketsClick = onBracketsClick,
      onAddTokenClick = onAddTokenClick,
      onDeleteClick = onDeleteClick,
      onEqualClick = onEqualClick,
      toggleSize = toggleSize,
      toggleBase = toggleBase,
      toggleShiftType = toggleShiftType,
      onClearHistoryClick = onClearHistoryClick,
      onDeleteHistoryItemClick = onDeleteHistoryItemClick,
      onUpdateHistoryItemLabel = onUpdateHistoryItemLabel,
    )
  } else {
    ProgrammerScreenCompact(
      uiState = uiState,
      openDrawer = openDrawer,
      onClearClick = onClearClick,
      onBracketsClick = onBracketsClick,
      onAddTokenClick = onAddTokenClick,
      onDeleteClick = onDeleteClick,
      onEqualClick = onEqualClick,
      toggleSize = toggleSize,
      toggleBase = toggleBase,
      toggleShiftType = toggleShiftType,
      updateInitialPartialHistoryView = updateInitialPartialHistoryView,
      onClearHistoryClick = onClearHistoryClick,
      onDeleteHistoryItemClick = onDeleteHistoryItemClick,
      onUpdateHistoryItemLabel = onUpdateHistoryItemLabel,
    )
  }
}

@Composable
private fun ProgrammerScreenCompact(
  uiState: ProgrammerScreenUIState.Ready,
  openDrawer: () -> Unit,
  onClearClick: () -> Unit,
  onBracketsClick: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onDeleteClick: () -> Unit,
  onEqualClick: () -> Unit,
  toggleSize: () -> Unit,
  toggleBase: () -> Unit,
  toggleShiftType: () -> Unit,
  updateInitialPartialHistoryView: (Boolean) -> Unit,
  onClearHistoryClick: () -> Unit,
  onDeleteHistoryItemClick: (CalculatorHistoryListItem.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryListItem.Item, String) -> Unit,
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
    title = { ProgrammerTitle(uiState) },
    navigationIcon = { DrawerButton(onClick = openDrawer) },
    colors =
      TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    actions = {
      ProgrammerCompactActions(
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
        TextFieldsBox(
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
          showHandle = true,
        )
      },
      keyboard = { offset, height ->
        ProgrammerKeyboard(
          modifier =
            Modifier.offset(offset)
              .deferredHeight(height)
              .fillMaxWidth()
              .padding(horizontal = Sizes.small, vertical = Sizes.extraSmall),
          showAcButton = uiState.showAcButton,
          onClearClick = onClearClick,
          onBracketsClick = onBracketsClick,
          onAddTokenClick = onAddTokenClick,
          onDeleteClick = onDeleteClick,
          onEqualClick = {
            focusManager.clearFocus()
            onEqualClick()
          },
          middleZero = uiState.middleZero,
          toggleSize = toggleSize,
          toggleBase = toggleBase,
          toggleShiftType = toggleShiftType,
          base = uiState.base,
          shiftType = uiState.shiftType,
          // additionalButtons = uiState.additionalButtons,
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
private fun ProgrammerScreenExpanded(
  uiState: ProgrammerScreenUIState.Ready,
  onClearClick: () -> Unit,
  onBracketsClick: () -> Unit,
  onAddTokenClick: (String) -> Unit,
  onDeleteClick: () -> Unit,
  onEqualClick: () -> Unit,
  toggleSize: () -> Unit,
  toggleBase: () -> Unit,
  toggleShiftType: () -> Unit,
  onClearHistoryClick: () -> Unit,
  onDeleteHistoryItemClick: (CalculatorHistoryListItem.Item) -> Unit,
  onUpdateHistoryItemLabel: (CalculatorHistoryListItem.Item, String) -> Unit,
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
        title = { ProgrammerTitle(uiState) },
        navigationIcon = {
          ReadyExpandedActions(onClearHistoryClick = onClearHistoryClick)
        },
        colors =
          TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
          ),
      ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxHeight()) {
          TextFieldsBox(
            modifier =
              Modifier.fillMaxHeight(
                  CalculatorWithHistoryLayoutDefault.TEXT_BOX_HEIGHT_FACTOR_COMPACT
                )
                .fillMaxWidth(),
            formatterSymbols = uiState.formatterSymbols,
            state = uiState.input,
            output = uiState.output,
            showHandle = false,
          )
          val focusManager = LocalFocusManager.current
          ProgrammerKeyboard(
            modifier = Modifier.padding(Sizes.large).weight(1f).fillMaxWidth(),
            showAcButton = uiState.showAcButton,
            onClearClick = onClearClick,
            onBracketsClick = onBracketsClick,
            onAddTokenClick = onAddTokenClick,
            onDeleteClick = onDeleteClick,
            onEqualClick = {
              focusManager.clearFocus()
              onEqualClick()
            },
            middleZero = uiState.middleZero,
            toggleSize = toggleSize,
            toggleBase = toggleBase,
            toggleShiftType = toggleShiftType,
            base = uiState.base,
            shiftType = uiState.shiftType,
          )
        }
      }
    }
  }
}

@Composable
private fun ProgrammerTitle(uiState: ProgrammerScreenUIState.Ready) {
  Text(
    text = "${uiState.base} (${uiState.dataUnit.name})",
    modifier = Modifier.fillMaxWidth(),
    textAlign = TextAlign.End,
    color = MaterialTheme.colorScheme.onSurfaceVariant,
  )
}

@Composable
private fun ProgrammerCompactActions(
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
@Preview
private fun PreviewProgrammerScreen() = ExpressivePreview {
  ProgrammerScreen(
    uiState =
      remember {
        ProgrammerScreenUIState.Ready(
          input = TextFieldState("123andABC"),
          output = CalculationResult.Success("789"),
          showAcButton = true,
          formatterSymbols = FormatterSymbols(Token.Space, Token.Period, false),
          middleZero = true,
          dataUnit = DataUnit.QWORD,
          base = 10,
          shiftType = ShiftType.SHIFT,
          partialHistoryView = true,
          steppedPartialHistoryView = true,
          initialPartialHistoryView = false,
          openHistoryViewButton = true,
          history =
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
            ),
        )
      },
    openDrawer = {},
    onClearClick = {},
    onBracketsClick = {},
    onAddTokenClick = {},
    onDeleteClick = {},
    onEqualClick = {},
    toggleSize = {},
    toggleBase = {},
    toggleShiftType = {},
    updateInitialPartialHistoryView = {},
    onClearHistoryClick = {},
    onDeleteHistoryItemClick = {},
    onUpdateHistoryItemLabel = { _, _ -> },
  )
}

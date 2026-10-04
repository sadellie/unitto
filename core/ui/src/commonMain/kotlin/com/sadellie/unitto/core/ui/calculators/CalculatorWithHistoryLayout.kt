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

package com.sadellie.unitto.core.ui.calculators

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.LocalHapticFeedbackManager
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.designsystem.icons.symbols.Delete
import com.sadellie.unitto.core.designsystem.icons.symbols.History
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.calculator_clear_history
import unitto.core.common.generated.resources.calculator_clear_history_support
import unitto.core.common.generated.resources.common_cancel
import unitto.core.common.generated.resources.common_clear
import unitto.core.common.generated.resources.settings_history_view_button

enum class DragState {
  CLOSED,
  PARTIAL,
  OPEN,
}

@Composable
fun AnchoredDraggableState<DragState>.liquidFlingBehaviour() =
  AnchoredDraggableDefaults.flingBehavior(
    state = this,
    animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
  )

suspend fun AnchoredDraggableState<DragState>.toggleDragState(
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

fun Modifier.deferredHeight(height: () -> Dp): Modifier = layout { measurable, constraints ->
  val h = height().roundToPx().coerceAtLeast(0)
  val placeable = measurable.measure(constraints.copy(minHeight = h, maxHeight = h))
  layout(placeable.width, h) {
    placeable.place(0, 0)
  }
}

@Composable
fun LiquidCalculatorView(
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
      maxHeight *
        if (isCompact) CalculatorWithHistoryLayoutDefault.TEXT_BOX_HEIGHT_FACTOR_COMPACT
        else CalculatorWithHistoryLayoutDefault.TEXT_BOX_HEIGHT_FACTOR_EXPANDED

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
      delay(CalculatorWithHistoryLayoutDefault.REMEMBER_PARTIAL_HISTORY_VIEW_STATE_DELAY_MS)
      updateInitialPartialHistoryView(dragState.settledValue == DragState.PARTIAL)
    }

    calculatorHistory(historyHeight)
    textBox(textBoxOffset, textBoxHeight)
    keyboard(keyboardOffset, keyboardHeight)
  }

@Composable
fun OpenHistoryViewButton(onClick: () -> Unit, isOpen: Boolean) {
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
fun ClearHistoryButton(onClearHistoryClick: () -> Unit, isOpen: Boolean) {
  val transitionSpec = MaterialTheme.motionScheme.defaultEffectsSpec<Float>()
  var showClearHistoryDialog by rememberSaveable { mutableStateOf(false) }
  AnimatedVisibility(
    visible = isOpen,
    label = "History buttons reveal",
    enter = fadeIn(transitionSpec) + scaleIn(transitionSpec, 0.5f),
    exit = fadeOut(transitionSpec) + scaleOut(transitionSpec, 0.5f),
  ) {
    IconButton(
      onClick = { showClearHistoryDialog = true },
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
fun ClearHistoryDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
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

data object CalculatorWithHistoryLayoutDefault {
  const val TEXT_BOX_HEIGHT_FACTOR_COMPACT = 0.4f
  const val TEXT_BOX_HEIGHT_FACTOR_EXPANDED = 0.25f
  const val REMEMBER_PARTIAL_HISTORY_VIEW_STATE_DELAY_MS = 1_000L
}

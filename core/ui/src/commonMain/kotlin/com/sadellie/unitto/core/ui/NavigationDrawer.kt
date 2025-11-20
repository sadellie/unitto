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

package com.sadellie.unitto.core.ui

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.navigation.DrawerItem
import com.sadellie.unitto.core.navigation.TopLevelRoute
import com.sadellie.unitto.core.ui.UnittoDrawerState.Companion.saver
import kotlin.math.roundToInt
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
  modifier: Modifier,
  gesturesEnabled: Boolean,
  state: UnittoDrawerState,
  mainTabs: List<DrawerItem>,
  additionalTabs: List<DrawerItem>,
  currentDestination: TopLevelRoute?,
  onItemClick: (DrawerItem) -> Unit,
  content: @Composable () -> Unit,
) {
  if (LocalWindowSize.current.widthSizeClass == WindowWidthSizeClass.Expanded) {
    PermanentNavigationDrawer(
      modifier = modifier,
      drawerContent = {
        PermanentDrawerSheet(
          modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()),
          drawerContainerColor = MaterialTheme.colorScheme.surfaceContainer,
          drawerContentColor = MaterialTheme.colorScheme.onSurface,
        ) {
          SheetContent(
            mainTabs = mainTabs,
            additionalTabs = additionalTabs,
            currentDestination = currentDestination,
            onItemClick = onItemClick,
          )
        }
      },
      content = content,
    )
  } else {
    UnittoModalNavigationDrawer(
      modifier = modifier,
      drawerContent = {
        ModalDrawerSheet(
          modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()),
          drawerContainerColor = MaterialTheme.colorScheme.surfaceContainer,
          drawerContentColor = MaterialTheme.colorScheme.onSurface,
        ) {
          SheetContent(
            mainTabs = mainTabs,
            additionalTabs = additionalTabs,
            currentDestination = currentDestination,
            onItemClick = onItemClick,
          )
        }
      },
      gesturesEnabled = gesturesEnabled,
      state = state,
      content = content,
    )
  }
}

/** Possible values of [UnittoDrawerState]. */
enum class UnittoDrawerValue {
  /** The state of the drawer when it is closed. */
  Closed,

  /** The state of the drawer when it is open. */
  Open,
}

/**
 * State of the [ModalNavigationDrawer] and [DismissibleNavigationDrawer] composable.
 *
 * @param initialValue The initial value of the state.
 */
@Suppress("NotCloseable")
@Stable
class UnittoDrawerState(initialValue: UnittoDrawerValue) {
  internal val anchoredDraggableState = AnchoredDraggableState(initialValue = initialValue)

  /** Whether the drawer is open. */
  val isOpen: Boolean
    get() = currentValue == UnittoDrawerValue.Open

  /** Whether the drawer is closed. */
  val isClosed: Boolean
    get() = currentValue == UnittoDrawerValue.Closed

  /**
   * The current value of the state.
   *
   * If no swipe or animation is in progress, this corresponds to the start the drawer currently in.
   * If a swipe or an animation is in progress, this corresponds the state drawer was in before the
   * swipe or animation started.
   */
  val currentValue: UnittoDrawerValue
    get() {
      return anchoredDraggableState.currentValue
    }

  /**
   * Open the drawer with animation and suspend until it if fully opened or animation has been
   * cancelled. This method will throw [CancellationException] if the animation is interrupted
   *
   * @return the reason the open animation ended
   */
  suspend fun open() =
    animateTo(targetValue = UnittoDrawerValue.Open, animationSpec = openDrawerMotionSpec)

  /**
   * Close the drawer with animation and suspend until it if fully closed or animation has been
   * cancelled. This method will throw [CancellationException] if the animation is interrupted
   *
   * @return the reason the close animation ended
   */
  suspend fun close() =
    animateTo(targetValue = UnittoDrawerValue.Closed, animationSpec = closeDrawerMotionSpec)

  /**
   * The current position (in pixels) of the drawer sheet, or Float.NaN before the offset is
   * initialized.
   *
   * @see [AnchoredDraggableState.offset] for more information.
   */
  private val currentOffset: Float
    get() = anchoredDraggableState.offset

  internal var density: Density? by mutableStateOf(null)

  private val openDrawerMotionSpec: FiniteAnimationSpec<Float> = spring(0.9f, 700.0f)

  private val closeDrawerMotionSpec: FiniteAnimationSpec<Float> = spring(1.0f, 1600.0f)

  internal fun requireOffset(): Float = anchoredDraggableState.requireOffset()

  private suspend fun animateTo(
    targetValue: UnittoDrawerValue,
    animationSpec: AnimationSpec<Float>,
    velocity: Float = anchoredDraggableState.lastVelocity,
  ) {
    anchoredDraggableState.anchoredDrag(targetValue = targetValue) { anchors, latestTarget ->
      val targetOffset = anchors.positionOf(latestTarget)
      if (!targetOffset.isNaN()) {
        var prev = if (currentOffset.isNaN()) 0f else currentOffset
        animate(prev, targetOffset, velocity, animationSpec) { value, velocity ->
          // Our onDrag coerces the value within the bounds, but an animation may
          // overshoot, for example a spring animation or an overshooting interpolator
          // We respect the user's intention and allow the overshoot, but still use
          // DraggableState's drag for its mutex.
          dragTo(value, velocity)
          prev = value
        }
      }
    }
  }

  companion object {
    /** The default [saver] implementation for [UnittoDrawerState]. */
    fun saver() =
      Saver<UnittoDrawerState, UnittoDrawerValue>(
        save = { it.currentValue },
        restore = { UnittoDrawerState(it) },
      )
  }
}

/**
 * Create and [remember] a [UnittoDrawerState].
 *
 * @param initialValue The initial value of the state.
 */
@Composable
fun rememberUnittoDrawerState(initialValue: UnittoDrawerValue): UnittoDrawerState =
  rememberSaveable(saver = UnittoDrawerState.saver()) { UnittoDrawerState(initialValue) }

/** Compost team should be deprecated and replaced with Gemini. */
@Composable
private fun UnittoModalNavigationDrawer(
  modifier: Modifier,
  drawerContent: @Composable () -> Unit,
  gesturesEnabled: Boolean,
  state: UnittoDrawerState = rememberUnittoDrawerState(UnittoDrawerValue.Closed),
  content: @Composable () -> Unit,
) {
  Box(modifier = modifier.fillMaxSize()) {
    val density = LocalDensity.current
    val drawerScope = rememberCoroutineScope()
    val drawerWidthPx = with(density) { UnittoModalDrawerMaxWidth.toPx() }
    val minValue = -drawerWidthPx
    val maxValue = 0f

    SideEffect {
      state.density = density
      state.anchoredDraggableState.updateAnchors(
        DraggableAnchors {
          UnittoDrawerValue.Closed at minValue
          UnittoDrawerValue.Open at maxValue
        }
      )
    }

    content()

    // Drag handle
    DragHandle(state.anchoredDraggableState, state.isOpen, gesturesEnabled)

    Scrim(
      open = state.isOpen,
      onClose = { if (gesturesEnabled) drawerScope.launch { state.close() } },
      fraction = { fraction(minValue, maxValue, state.anchoredDraggableState.requireOffset()) },
    )

    // Drawer box
    Box(
      modifier =
        Modifier.offset {
            IntOffset(x = state.anchoredDraggableState.requireOffset().roundToInt(), y = 0)
          }
          .anchoredDraggable(
            state = state.anchoredDraggableState,
            orientation = Orientation.Horizontal,
            enabled = gesturesEnabled or state.isOpen,
          )
    ) {
      drawerContent()
    }
  }
}

@Composable
internal expect fun DragHandle(
  anchoredDraggableState: AnchoredDraggableState<UnittoDrawerValue>,
  isOpen: Boolean,
  gesturesEnabled: Boolean,
)

@Composable
private fun Scrim(
  open: Boolean,
  onClose: () -> Unit,
  fraction: () -> Float,
  color: Color = DrawerDefaults.scrimColor,
) {
  val dismissDrawer =
    if (open) {
      Modifier.pointerInput(onClose) { detectTapGestures { onClose() } }
    } else {
      Modifier
    }

  Canvas(Modifier.fillMaxSize().then(dismissDrawer)) { drawRect(color, alpha = fraction()) }
}

@Suppress("SameParameterValue")
private fun fraction(a: Float, b: Float, pos: Float) = ((pos - a) / (b - a)).coerceIn(0f, 1f)

internal val UnittoModalDrawerDragHandleWidth = 36.dp
private val UnittoModalDrawerMaxWidth = 360.dp

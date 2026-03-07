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

package com.sadellie.unitto.core.ui

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.LocalWindowSize
import com.sadellie.unitto.core.navigation.CalculatorStartRoute
import com.sadellie.unitto.core.navigation.DrawerItem
import com.sadellie.unitto.core.navigation.TopLevelRoute
import com.sadellie.unitto.core.navigation.additionalDrawerItems
import com.sadellie.unitto.core.navigation.mainDrawerItems
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
  modifier: Modifier,
  edgeGesture: Boolean,
  state: DrawerState,
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
    ModalNavigationDrawer(
      modifier = modifier.drawerEdgeGesture(edgeGesture, state),
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
      gesturesEnabled = state.isOpen,
      drawerState = state,
      content = content,
    )
  }
}

private fun Modifier.drawerEdgeGesture(enabled: Boolean, state: DrawerState) = composed {
  val dragThresholdPx = with(LocalDensity.current) { UnittoModalDrawerDragHandleThreshold.toPx() }
  val dragStartPx = with(LocalDensity.current) { UnittoModalDrawerDragHandleWidth.toPx() }
  val scope = rememberCoroutineScope()
  this.pointerInput(enabled) {
    if (!enabled) return@pointerInput
    var consumedDistance = 0f
    var startX = 0f
    detectHorizontalDragGestures(
      onDragStart = {
        consumedDistance = 0f
        startX = it.x
      },
      onHorizontalDrag = { _, dragAmount ->
        if (dragAmount <= 0f || startX > dragStartPx) return@detectHorizontalDragGestures
        consumedDistance += dragAmount
        if (consumedDistance >= dragThresholdPx) {
          scope.launch { state.open() }
        }
      },
    )
  }
}

private val UnittoModalDrawerDragHandleWidth = 46.dp
private val UnittoModalDrawerDragHandleThreshold = 24.dp

@Preview(backgroundColor = 0xFFC8F7D4, showBackground = true, widthDp = 840, heightDp = 500)
@Preview(backgroundColor = 0xFFC8F7D4, showBackground = true, widthDp = 600, heightDp = 500)
@Preview(backgroundColor = 0xFFC8F7D4, showBackground = true, widthDp = 400, heightDp = 500)
@Composable
private fun PreviewUnittoModalNavigationDrawer() =
  BoxWithConstraints(Modifier.fillMaxSize()) {
    val ws = WindowSizeClass.calculateFromSize(DpSize(this.minWidth, this.minHeight))
    CompositionLocalProvider(LocalWindowSize provides ws) {
      val corScope = rememberCoroutineScope()
      val drawerState = rememberDrawerState(DrawerValue.Open)
      var clicked by remember { mutableIntStateOf(0) }

      NavigationDrawer(
        modifier = Modifier,
        state = drawerState,
        edgeGesture = true,
        mainTabs = mainDrawerItems,
        additionalTabs = additionalDrawerItems,
        currentDestination = CalculatorStartRoute,
        onItemClick = {},
        content = {
          Column {
            Text(text = "Clicked: $clicked")
            Button(
              onClick = { corScope.launch { drawerState.open() } },
              shapes = ButtonDefaults.shapes(),
            ) {
              Text(text = "Open drawer")
            }
            Button(onClick = { clicked += 1 }, shapes = ButtonDefaults.shapes()) {
              Text(text = "Click")
            }
          }
        },
      )
    }
  }

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.navigation.CalculatorStartRoute
import com.sadellie.unitto.core.navigation.additionalDrawerItems
import com.sadellie.unitto.core.navigation.mainDrawerItems
import kotlinx.coroutines.launch

@Preview(
  backgroundColor = 0xFFC8F7D4,
  showBackground = true,
  device = "spec:width=320dp,height=500dp,dpi=320",
)
@Preview(
  backgroundColor = 0xFFC8F7D4,
  showBackground = true,
  device = "spec:width=440dp,height=500dp,dpi=440",
)
@Composable
private fun PreviewUnittoModalNavigationDrawer() {
  val corScope = rememberCoroutineScope()
  val drawerState = rememberUnittoDrawerState(UnittoDrawerValue.Open)
  var clicked by remember { mutableIntStateOf(0) }

  NavigationDrawer(
    modifier = Modifier,
    state = drawerState,
    gesturesEnabled = true,
    mainTabs = mainDrawerItems,
    additionalTabs = additionalDrawerItems,
    currentDestination = CalculatorStartRoute,
    onItemClick = {},
    content = {
      Column {
        Text(text = "Clicked: $clicked")
        Button(onClick = { corScope.launch { drawerState.open() } }) { Text(text = "Open drawer") }
        Button(onClick = { clicked += 1 }) { Text(text = "Click") }
      }
    },
  )
}

@Composable
internal actual fun DragHandle(
  anchoredDraggableState: AnchoredDraggableState<UnittoDrawerValue>,
  isOpen: Boolean,
  gesturesEnabled: Boolean,
) {
  Box(
    modifier =
      Modifier.width(UnittoModalDrawerDragHandleWidth)
        .fillMaxHeight()
        .pointerInteropFilter { false }
        .anchoredDraggable(
          state = anchoredDraggableState,
          orientation = Orientation.Horizontal,
          enabled = gesturesEnabled || isOpen,
        )
  )
}

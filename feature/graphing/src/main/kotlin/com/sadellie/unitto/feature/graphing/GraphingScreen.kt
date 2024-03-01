/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.graphing

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.designsystem.icons.symbols.Add
import com.sadellie.unitto.core.designsystem.icons.symbols.CenterFocusWeak
import com.sadellie.unitto.core.designsystem.icons.symbols.Edit
import com.sadellie.unitto.core.designsystem.icons.symbols.EditOff
import com.sadellie.unitto.core.designsystem.icons.symbols.Remove
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.ui.DrawerButton
import com.sadellie.unitto.core.ui.EmptyScreen
import com.sadellie.unitto.core.ui.ScaffoldWithTopBar
import com.sadellie.unitto.feature.graphing.components.GraphingCanvas
import com.sadellie.unitto.feature.graphing.components.GraphingFunctionsList
import com.sadellie.unitto.feature.graphing.components.naturalZoom
import kotlin.math.roundToInt
import kotlinx.coroutines.delay

@Composable
internal fun GraphingRoute(
  openDrawer: () -> Unit,
  viewModel: GraphingViewModel,
  navigateToEditor: (GraphFunction) -> Unit,
  navigateToCreator: () -> Unit,
) {
  LaunchedEffect(Unit) { viewModel.observeInput() }
  val uiState = viewModel.mainUiState.collectAsStateWithLifecycle().value

  if (uiState == null) {
    EmptyScreen()
  } else {
    Ready(
      openDrawer = openDrawer,
      uiState = uiState,
      onCanvasUpdate = viewModel::onCanvasUpdate,
      updateTileZoom = viewModel::updateTileZoom,
      onRemoveFunction = viewModel::onRemoveFunction,
      onEditFunction = navigateToEditor,
      onCreateFunction = navigateToCreator,
    )
  }
}

@Composable
private fun Ready(
  openDrawer: () -> Unit,
  uiState: GraphingUIState,
  onCanvasUpdate: (minX: Int, maxX: Int) -> Unit,
  updateTileZoom: (newTileZoom: Float) -> Unit,
  onRemoveFunction: (removedFunction: GraphFunction) -> Unit,
  onCreateFunction: () -> Unit,
  onEditFunction: (GraphFunction) -> Unit,
) {
  ScaffoldWithTopBar(
    title = { Text(stringResource(R.string.graphing_title)) },
    navigationIcon = { DrawerButton(openDrawer) },
  ) { paddingValues ->
    Column(Modifier.padding(paddingValues).fillMaxSize()) {
      var showFunctionList by rememberSaveable { mutableStateOf(true) }

      BackHandler(showFunctionList) { showFunctionList = false }

      BoxWithConstraints(modifier = Modifier.weight(1f)) {
        val boxScope = this
        var offset by remember { mutableStateOf(Offset.Zero) }

        LaunchedEffect(boxScope.constraints.maxWidth, offset.x) {
          // debounce to avoid too many calls, cancelled on new value
          delay(200L)
          val centerXPx = boxScope.constraints.maxWidth / 2
          val betweenLeftEdgeAndCenterWithOffset = offset.x.roundToInt() + (centerXPx)
          val minPx = -betweenLeftEdgeAndCenterWithOffset
          val maxX = minPx + boxScope.constraints.maxWidth
          onCanvasUpdate(minPx, maxX)
        }

        GraphingCanvas(
          offset = offset,
          updateOffset = { offset = it },
          tileZoom = uiState.tileZoom,
          updateTileZoom = updateTileZoom,
          graphs = uiState.graphs,
        )

        OutlinedIconButton(
          onClick = { showFunctionList = !showFunctionList },
          modifier = Modifier.padding(8.dp).align(Alignment.BottomEnd),
        ) {
          Crossfade(targetState = showFunctionList, label = "Show list button") {
            if (it) {
              Icon(Symbols.EditOff, null)
            } else {
              Icon(Symbols.Edit, null)
            }
          }
        }

        ZoomControls(
          modifier = Modifier.padding(8.dp).align(Alignment.TopEnd),
          onZoomIncrease = {
            val (newOffset, newZoom) = naturalZoom(uiState.tileZoom, uiState.tileZoom + 10f, offset)
            offset = newOffset
            updateTileZoom(newZoom)
          },
          onZoomDecrease = {
            val (newOffset, newZoom) = naturalZoom(uiState.tileZoom, uiState.tileZoom - 10f, offset)
            offset = newOffset
            updateTileZoom(newZoom)
          },
          onReset = { offset = Offset.Zero },
        )
      }

      // Bottom sheet was made by stupid for stupid
      AnimatedVisibility(
        visible = showFunctionList,
        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom),
      ) {
        GraphingFunctionsList(
          modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
          functions = uiState.functions,
          onRemove = onRemoveFunction,
          onCreateFunction = onCreateFunction,
          onEditFunction = onEditFunction,
        )
      }
    }
  }
}

@Composable
private fun ZoomControls(
  modifier: Modifier,
  onZoomIncrease: () -> Unit,
  onZoomDecrease: () -> Unit,
  onReset: () -> Unit,
) {
  Column(modifier = modifier) {
    OutlinedIconButton(onZoomIncrease) { Icon(Symbols.Add, null) }
    OutlinedIconButton(onZoomDecrease) { Icon(Symbols.Remove, null) }
    OutlinedIconButton(onReset) { Icon(Symbols.CenterFocusWeak, null) }
  }
}

@Composable
@Preview
private fun PreviewReady() {
  val tileZoom = remember { mutableFloatStateOf(DEFAULT_ZOOM) }
  val graphFunctions = remember {
    listOf(GraphFunction(0, "x^2", Color.Cyan), GraphFunction(1, "cos(x)", Color.Red))
  }
  val graphs = remember {
    setOf(
      GraphLine(
        graphFunction = graphFunctions[0],
        offsets = List(100) { Offset(it.toFloat(), (it.toFloat() * it.toFloat())) },
      ),
      GraphLine(
        graphFunction = graphFunctions[1],
        offsets = listOf(Offset(1f, 0.5f), Offset(2f, 1.5f), Offset(3f, 0f)),
      ),
    )
  }

  Ready(
    openDrawer = {},
    uiState =
      GraphingUIState(
        functions = graphFunctions,
        graphs = graphs,
        tileZoom = tileZoom.floatValue,
        middleZero = true,
        showAcButton = false,
        formatterSymbols = FormatterSymbols(Token.SPACE, Token.PERIOD),
        inverseMode = false,
      ),
    onCanvasUpdate = { _, _ -> },
    updateTileZoom = { tileZoom.floatValue = it },
    onEditFunction = {},
    onRemoveFunction = {},
    onCreateFunction = {},
  )
}

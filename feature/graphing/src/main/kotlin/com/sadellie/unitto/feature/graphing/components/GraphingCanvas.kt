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

package com.sadellie.unitto.feature.graphing.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.sadellie.unitto.feature.graphing.GraphFunction
import com.sadellie.unitto.feature.graphing.GraphLine
import kotlin.math.floor

// crazy stuff below! read comments carefully
@Composable
internal fun GraphingCanvas(
  offset: Offset,
  tileZoom: Float,
  graphs: Set<GraphLine>,
  updateOffset: (Offset) -> Unit,
  updateTileZoom: (Float) -> Unit,
) {
  val axisColor = MaterialTheme.colorScheme.outlineVariant
  val axisTextStyle =
    MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
  val textMeasurer = rememberTextMeasurer()
  val backgroundColor = MaterialTheme.colorScheme.background
  // Long live in gesture
  val currentOffset by rememberUpdatedState(offset)
  val currentTileZoom by rememberUpdatedState(tileZoom)

  val tileStep =
    remember(currentTileZoom) {
      // smooth brain stuff (in reality it adds more control over ticks)
      when {
        currentTileZoom <= 10f -> 15f
        currentTileZoom <= 20f -> 10f
        currentTileZoom <= 30f -> 5f
        currentTileZoom <= 50f -> 4f
        currentTileZoom <= 100f -> 2f
        currentTileZoom <= 200f -> 1f
        else -> 0.5f
      }
    }
  val zoomedTileSize = currentTileZoom * tileStep

  Canvas(
    modifier =
      Modifier.background(backgroundColor).fillMaxSize().pointerInput(Unit) {
        detectTransformGestures { _, pan, gestureZoom, _ ->
          val (newOffset, newZoom) =
            naturalZoom(
              currentZoom = currentTileZoom,
              newZoom = currentTileZoom * gestureZoom,
              offset = currentOffset + pan,
            )

          updateOffset(newOffset)
          updateTileZoom(newZoom)
        }
      }
  ) {
    val centerWithOffset = center + offset
    drawAxisX(
      centerWithOffset,
      axisColor,
      tileStep,
      textMeasurer,
      axisTextStyle,
      zoomedTileSize,
      backgroundColor,
    )
    drawAxisY(
      centerWithOffset,
      axisColor,
      tileStep,
      textMeasurer,
      axisTextStyle,
      zoomedTileSize,
      backgroundColor,
    )
    drawGraphs(centerWithOffset, graphs, currentTileZoom)
  }
}

private fun DrawScope.drawAxisX(
  centerWithOffset: Offset,
  axisColor: Color,
  tileStep: Float,
  textMeasurer: TextMeasurer,
  textStyle: TextStyle,
  tileSize: Float,
  textBackgroundColor: Color,
) {
  // percentage of item size
  val paddingScale = 1.5f

  // Ticks
  // if positive: how many negative ticks need to be drawn
  // if negative or zero: how many positive ticks (or initial tick) were skipped (out of screen)
  val screenBounds = 0f..size.height
  val skipXTicks = floor(centerWithOffset.x / tileSize)
  val startX = centerWithOffset.x - skipXTicks * tileSize
  val firstXTick = -skipXTicks * tileStep
  // how many ticks fit in screen
  val xTileCount = floor((size.width - startX) / tileSize)

  var currentTick = 0f
  while (currentTick <= xTileCount) {
    val tickOffset = Offset(startX + tileSize * currentTick, centerWithOffset.y)
    if (tickOffset.x == centerWithOffset.x) {
      // do not draw 0
      currentTick += 1
      continue
    }
    drawLine(axisColor, Offset(tickOffset.x, 0f), Offset(tickOffset.x, size.height), 2f)

    if (tickOffset.y in screenBounds) {
      val tickText = "${firstXTick + currentTick * tileStep}"
      val textSize =
        textMeasurer.measure(tickText, textStyle, softWrap = false, maxLines = 1).size.toSize()
      // how much extra space is added to each side
      val textPadding = (textSize.width * paddingScale - textSize.width) / 2
      // center text horizontally
      val textOffset = Offset(tickOffset.x - textSize.width / 2f, centerWithOffset.y + textPadding)

      // Draw background behind tick text
      // Add padding
      val backgroundBoxSize = textSize * paddingScale
      // center background horizontally
      val backgroundBoxOffset =
        Offset(tickOffset.x - backgroundBoxSize.width / 2f, centerWithOffset.y)

      // Draw background behind tick text
      drawRect(textBackgroundColor, backgroundBoxOffset, backgroundBoxSize)
      drawText(
        textMeasurer = textMeasurer,
        text = tickText,
        topLeft = textOffset,
        softWrap = false,
        maxLines = 1,
        style = textStyle.copy(textAlign = TextAlign.Center),
        size = textSize,
      )
    }
    currentTick += 1
  }

  // Line
  val xAxisPath =
    Path().apply {
      // place at the top
      this.moveTo(0f, centerWithOffset.y)
      this.lineTo(size.width, centerWithOffset.y)
    }
  drawPath(xAxisPath, axisColor, style = Stroke(width = 10f))
}

private fun DrawScope.drawAxisY(
  centerWithOffset: Offset,
  axisColor: Color,
  tileStep: Float,
  textMeasurer: TextMeasurer,
  textStyle: TextStyle,
  tileSize: Float,
  textBackgroundColor: Color,
) {
  val screenBounds = 0f..size.width
  // percentage of item size
  val paddingScale = 1.5f

  // Ticks
  val skipYTicks = floor(centerWithOffset.y / tileSize)
  val startY = centerWithOffset.y - skipYTicks * tileSize
  val firstYTick = -skipYTicks * tileStep
  // how many ticks fit in screen
  val yTileCount = floor((size.height - startY) / tileSize)

  var currentTick = 0f
  while (currentTick <= yTileCount) {
    val tickOffset = Offset(centerWithOffset.x, startY + tileSize * currentTick)
    if (tickOffset.y == centerWithOffset.y) {
      // do not draw 0
      currentTick += 1
      continue
    }
    drawLine(axisColor, Offset(0f, tickOffset.y), Offset(size.width, tickOffset.y), 2f)

    if (tickOffset.x in screenBounds) {
      // -1 because top to bottom (0 -> size.height)
      val tickText = "${(firstYTick + currentTick * tileStep) * -1}"
      val textSize =
        textMeasurer.measure(tickText, textStyle, softWrap = false, maxLines = 1).size.toSize()
      val textPadding = (textSize.width * paddingScale - textSize.width) / 2

      // center text vertically and add horizontal padding
      val textOffset = Offset(centerWithOffset.x + textPadding, tickOffset.y - textSize.height / 2f)

      // Draw background behind tick text
      // Add padding
      val backgroundBoxSize = textSize * paddingScale
      // center background vertically
      val backgroundBoxOffset =
        Offset(centerWithOffset.x, tickOffset.y - backgroundBoxSize.height / 2f)

      drawRect(textBackgroundColor, backgroundBoxOffset, backgroundBoxSize)
      drawText(
        textMeasurer = textMeasurer,
        text = tickText,
        topLeft = textOffset,
        softWrap = false,
        maxLines = 1,
        style = textStyle.copy(textAlign = TextAlign.Center),
        size = textSize,
      )
    }

    currentTick += 1
  }

  // Line
  val yAxisPath =
    Path().apply {
      // start at the top in the center horizontally
      this.moveTo(centerWithOffset.x, 0f)
      // draw all the way down, height is always entire screen
      // Y axis moves left and right only
      this.lineTo(centerWithOffset.x, size.height)
    }
  drawPath(yAxisPath, axisColor, style = Stroke(width = 10f))
}

private fun DrawScope.drawGraphs(
  centerWithOffset: Offset,
  graphLines: Set<GraphLine>,
  tileZoom: Float,
) {
  graphLines.forEach { (graphFunction, offsets) ->
    if (offsets.isNotEmpty()) {
      val graphPath =
        Path().apply {
          // apply zoom
          val offsetsZoomed = offsets.map { offset -> offset * tileZoom }

          // place initial offset
          val initialOffset = offsetsZoomed.first()
          moveTo(centerWithOffset.x + initialOffset.x, centerWithOffset.y - initialOffset.y)

          // draw subsequently (starting point is shifter every iteration)
          offsetsZoomed.forEach { offset ->
            lineTo(centerWithOffset.x + offset.x, centerWithOffset.y - offset.y)
          }
        }
      drawPath(graphPath, graphFunction.color, style = Stroke(width = 10f))
    }
  }
}

@Composable
@Preview
private fun GraphingCanvasPreview() {
  var offset by remember { mutableStateOf(Offset.Zero) }
  val tileZoom = remember { mutableFloatStateOf(100f) }
  val graphs = remember {
    setOf(
      GraphLine(
        graphFunction = GraphFunction(0, "x^2", Color.Cyan),
        offsets = List(100) { Offset(it.toFloat(), (it.toFloat() * it.toFloat())) },
      ),
      GraphLine(
        graphFunction = GraphFunction(1, "cos(x)", Color.Red),
        offsets = listOf(Offset(1f, 0.5f), Offset(2f, 1.5f), Offset(3f, 0f)),
      ),
    )
  }

  GraphingCanvas(
    graphs = graphs,
    offset = offset,
    tileZoom = tileZoom.floatValue,
    updateOffset = { offset = it },
    updateTileZoom = { tileZoom.floatValue = it },
  )
}

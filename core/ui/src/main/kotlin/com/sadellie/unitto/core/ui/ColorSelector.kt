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

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.ExpressivePreview
import com.sadellie.unitto.core.designsystem.shapes.Sizes

@Composable
fun ColorSelector(
  modifier: Modifier = Modifier,
  currentColor: Color,
  onColorClick: (Color) -> Unit,
  colors: List<Color>,
  contentPadding: PaddingValues = PaddingValues(0.dp),
) {
  val listState = rememberLazyListState()

  LaunchedEffect(Unit) {
    val index = colors.indexOf(currentColor)
    if (index >= 0) listState.scrollToItem(index)
  }

  LazyRow(
    modifier = modifier,
    state = listState,
    horizontalArrangement = Arrangement.spacedBy(Sizes.extraSmall),
    contentPadding = contentPadding,
  ) {
    items(colors, { it.value.toLong() }) {
      ColorCheckbox(color = it, selected = it == currentColor, onClick = { onColorClick(it) })
    }
  }
}

@Composable
fun BasicColoredCheckbox(
  selected: Boolean,
  onClick: () -> Unit,
  color: Color,
  borderColor: Color = MaterialTheme.colorScheme.primary,
  content: @Composable BoxScope.() -> Unit,
) {
  val transition = updateTransition(selected, "selected")
  val cornerRadius by
    transition.animateInt(transitionSpec = { MaterialTheme.motionScheme.defaultSpatialSpec() }) {
      if (it) 35 else 50
    }
  val borderColor by
    transition.animateColor(transitionSpec = { MaterialTheme.motionScheme.defaultSpatialSpec() }) {
      if (it) borderColor else Color.Transparent
    }
  val shape = RoundedCornerShape(cornerRadius)
  Box(
    modifier =
      Modifier.size(48.dp).clip(shape).clickable { onClick() }.border(2.dp, borderColor, shape)
  ) {
    Box(modifier = Modifier.fillMaxSize().padding(4.dp).clip(shape).background(color)) { content() }
  }
}

@Composable
private fun ColorCheckbox(selected: Boolean, onClick: () -> Unit, color: Color) {
  BasicColoredCheckbox(selected = selected, onClick = onClick, color = color, content = {})
}

@Preview
@Composable
private fun PreviewColorSelector() = ExpressivePreview {
  var selectedColor by remember { mutableStateOf(Color.Red) }
  ColorSelector(
    modifier = Modifier.background(MaterialTheme.colorScheme.background),
    currentColor = selectedColor,
    colors =
      remember {
        listOf(Color.Red, Color.Yellow, Color.Blue, Color.Green, Color.Magenta, Color.LightGray)
      },
    onColorClick = { selectedColor = it },
  )
}

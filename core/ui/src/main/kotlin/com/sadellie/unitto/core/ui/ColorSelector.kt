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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.designsystem.icons.symbols.Check
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.designsystem.shapes.Shapes
import com.sadellie.unitto.core.designsystem.theme.isDark

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
    horizontalArrangement = Arrangement.spacedBy(8.dp),
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
  checkIconColor: Color,
  checkBackgroundColor: Color,
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier =
      Modifier.size(72.dp)
        .clip(Shapes.Large)
        .clickable(onClick = onClick)
        .background(MaterialTheme.colorScheme.surfaceContainer),
    contentAlignment = Alignment.Center,
  ) {
    Box(
      modifier =
        Modifier.size(56.dp)
          .clip(CircleShape)
          .background(color)
          .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
    ) {
      content()
      AnimatedVisibility(
        visible = selected,
        enter = fadeIn(tween(FADE_DURATION_MS)) + scaleIn(tween(SCALE_DURATION_MS)),
        exit = fadeOut(tween(FADE_DURATION_MS)) + scaleOut(tween(SCALE_DURATION_MS)),
        modifier = Modifier.align(Alignment.Center),
      ) {
        Icon(
          imageVector = Symbols.Check,
          contentDescription = stringResource(R.string.common_selected_item),
          tint = checkIconColor,
          modifier = Modifier.background(checkBackgroundColor, CircleShape).padding(4.dp),
        )
      }
    }
  }
}

@Composable
private fun ColorCheckbox(color: Color, selected: Boolean, onClick: () -> Unit) {
  BasicColoredCheckbox(
    selected = selected,
    onClick = onClick,
    color = color,
    checkIconColor = if (color.isDark()) Color.White else Color.Black,
    checkBackgroundColor = Color.Transparent,
    content = {},
  )
}

private const val FADE_DURATION_MS = 250
private const val SCALE_DURATION_MS = 150

@Preview
@Composable
private fun PreviewColorSelector() {
  ColorSelector(
    modifier = Modifier.background(MaterialTheme.colorScheme.background),
    currentColor = Color.Red,
    colors =
      remember {
        listOf(Color.Red, Color.Yellow, Color.Blue, Color.Green, Color.Magenta, Color.LightGray)
      },
    onColorClick = {},
  )
}

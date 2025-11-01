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

import androidx.annotation.IntRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
fun Slider(
  modifier: Modifier = Modifier,
  value: Float,
  valueRange: ClosedFloatingPointRange<Float>,
  onValueChange: (Float) -> Unit,
  onValueChangeFinished: () -> Unit = {},
  @IntRange(from = 0) steps: Int = 0,
) {
  val animated =
    animateFloatAsState(
      targetValue = value.roundToInt().toFloat(),
      animationSpec = spring(),
      label = "Thumb animation",
    )

  Slider(
    value = animated.value,
    onValueChange = onValueChange,
    modifier = modifier,
    valueRange = valueRange,
    onValueChangeFinished = onValueChangeFinished,
    steps = steps,
  )
}

@Preview
@Composable
private fun PreviewNewSlider() {
  var currentValue by remember { mutableFloatStateOf(9f) }

  com.sadellie.unitto.core.ui.Slider(
    value = currentValue,
    valueRange = 0f..16f,
    onValueChange = { currentValue = it },
  )
}

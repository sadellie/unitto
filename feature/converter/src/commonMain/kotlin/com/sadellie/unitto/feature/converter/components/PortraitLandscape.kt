/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2026 Elshan Agaev
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

package com.sadellie.unitto.feature.converter.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.LocalWindowSize

/**
 * When Portrait mode will place [content1] and [content2] in a
 * [androidx.compose.foundation.layout.Column].
 *
 * When Landscape mode will place [content1] and [content2] in a
 * [androidx.compose.foundation.layout.Row].
 */
@Composable
fun PortraitLandscape(
  modifier: Modifier,
  content1: @Composable BoxScope.() -> Unit,
  content2: @Composable BoxScope.() -> Unit,
) {
  if (LocalWindowSize.current.heightSizeClass > WindowHeightSizeClass.Compact) {
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    Column(modifier.onSizeChanged { containerSize = it }) {
      val density = LocalDensity.current
      // content1
      val content1Padding =
        remember(density, containerSize) {
          with(density) {
            val widthDp = with(density) { containerSize.width.toDp() }
            PaddingValues(horizontal = widthDp * EXPANDED_CONTENT_HORIZONTAL_PADDING_FACTOR)
          }
        }
      Box(
        modifier = Modifier.fillMaxHeight(EXPANDED_CONTENT1_HEIGHT_FACTOR).padding(content1Padding)
      ) {
        content1()
      }
      val content2Padding =
        remember(density, containerSize) {
          with(density) {
            val widthDp = with(density) { containerSize.width.toDp() }
            val heightDp = with(density) { containerSize.height.toDp() }
            PaddingValues(
              horizontal = widthDp * EXPANDED_CONTENT_HORIZONTAL_PADDING_FACTOR,
              vertical = heightDp * EXPANDED_CONTENT2_VERTICAL_PADDING_FACTOR,
            )
          }
        }
      Box(Modifier.fillMaxSize().padding(content2Padding), content = content2)
    }
  } else {
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    Row(modifier.onSizeChanged { containerSize = it }) {
      val density = LocalDensity.current
      val contentPadding =
        remember(density, containerSize) {
          val widthDp = with(density) { containerSize.width.toDp() }
          val heightDp = with(density) { containerSize.height.toDp() }
          PaddingValues(
            start = widthDp * COMPACT_CONTENT_HORIZONTAL_PADDING_FACTOR,
            top = 0.dp,
            end = widthDp * COMPACT_CONTENT_HORIZONTAL_PADDING_FACTOR,
            bottom = heightDp * COMPACT_CONTENT_BOTTOM_PADDING_FACTOR,
          )
        }
      val contentModifier = Modifier.weight(1f).fillMaxSize().padding(contentPadding)
      Box(contentModifier, content = content1)
      Box(contentModifier, content = content2)
    }
  }
}

private const val EXPANDED_CONTENT1_HEIGHT_FACTOR = 0.38f
private const val EXPANDED_CONTENT_HORIZONTAL_PADDING_FACTOR = 0.03f
private const val EXPANDED_CONTENT2_VERTICAL_PADDING_FACTOR = 0.015f
private const val COMPACT_CONTENT_HORIZONTAL_PADDING_FACTOR = 0.015f
private const val COMPACT_CONTENT_BOTTOM_PADDING_FACTOR = 0.03f

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.LocalWindowSize

/**
 * When Portrait mode will place [content1] and [content2] in a [Column].
 *
 * When Landscape mode will place [content1] and [content2] in a [Row].
 */
@Composable
fun PortraitLandscape(
  modifier: Modifier,
  content1: @Composable (Modifier) -> Unit,
  content2: @Composable (Modifier) -> Unit,
) {
  if (
    (LocalWindowSize.current.widthSizeClass > WindowWidthSizeClass.Expanded) ||
      (LocalWindowSize.current.heightSizeClass > WindowHeightSizeClass.Compact)
  ) {
    ColumnWithConstraints(modifier) {
      val horizontalPadding = it.maxWidth * EXPANDED_CONTENT_HORIZONTAL_PADDING_FACTOR
      val content2VerticalPadding = it.maxHeight * EXPANDED_CONTENT2_VERTICAL_PADDING_FACTOR
      content1(
        Modifier.fillMaxHeight(EXPANDED_CONTENT1_HEIGHT_FACTOR)
          .padding(horizontal = horizontalPadding)
      )
      content2(Modifier.fillMaxSize().padding(horizontalPadding, content2VerticalPadding))
    }
  } else {
    RowWithConstraints(modifier) {
      val contentModifier =
        Modifier.weight(1f)
          .fillMaxSize()
          .padding(
            start = it.maxWidth * COMPACT_CONTENT_HORIZONTAL_PADDING_FACTOR,
            top = 0.dp,
            end = it.maxWidth * COMPACT_CONTENT_HORIZONTAL_PADDING_FACTOR,
            bottom = it.maxHeight * COMPACT_CONTENT_BOTTOM_PADDING_FACTOR,
          )
      content1(contentModifier)
      content2(contentModifier)
    }
  }
}

private const val EXPANDED_CONTENT1_HEIGHT_FACTOR = 0.38f
private const val EXPANDED_CONTENT_HORIZONTAL_PADDING_FACTOR = 0.03f
private const val EXPANDED_CONTENT2_VERTICAL_PADDING_FACTOR = 0.015f
private const val COMPACT_CONTENT_HORIZONTAL_PADDING_FACTOR = 0.015f
private const val COMPACT_CONTENT_BOTTOM_PADDING_FACTOR = 0.03f

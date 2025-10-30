/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.designsystem.shapes.Sizes

@Composable
fun ListHeader(
  text: String,
  modifier: Modifier =
    Modifier.padding(
      start = Sizes.small,
      end = Sizes.small,
      top = Sizes.large,
      bottom = Sizes.small,
    ),
  color: Color = MaterialTheme.colorScheme.primary,
  style: TextStyle = MaterialTheme.typography.labelLarge,
) {
  Text(text = text, modifier = modifier, color = color, style = style)
}

@Composable
@Preview
private fun PreviewListHeader() {
  LazyColumn(
    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
    verticalArrangement = ListItemExpressiveDefaults.ListArrangement,
  ) {
    item { ListHeader("Text") }
    items(10) {
      ListItemExpressive(
        headlineContent = { Text("Item $it") },
        shape = ListItemExpressiveDefaults.listedShaped(it, 10),
      )
    }
  }
}

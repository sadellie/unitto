/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.icons.symbols.Settings
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols

@Composable
fun FilterChip(
  modifier: Modifier = Modifier,
  isSelected: Boolean,
  onClick: () -> Unit,
  label: String,
) {
  val transition = updateTransition(targetState = isSelected, label = "Selected transition")
  val backgroundColor =
    transition.animateColor(label = "Background color") {
      if (it) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    }
  val borderColor =
    transition.animateColor(label = "Border color") {
      if (it) Color.Transparent else MaterialTheme.colorScheme.outline
    }

  Row(
    modifier =
      modifier
        .padding(vertical = 8.dp)
        .clip(FilterChipDefaults.shape)
        .clickable { onClick() }
        .background(backgroundColor.value)
        .border(width = 1.dp, color = borderColor.value, shape = FilterChipDefaults.shape)
        .height(FilterChipDefaults.Height)
        .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelLarge,
      color =
        if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Composable
fun AssistChip(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
  imageVector: ImageVector,
  contentDescription: String,
) {
  Row(
    modifier =
      modifier
        .padding(vertical = 8.dp)
        .clip(FilterChipDefaults.shape)
        .clickable { onClick() }
        .background(MaterialTheme.colorScheme.surface)
        .border(
          width = 1.dp,
          color = MaterialTheme.colorScheme.outline,
          shape = AssistChipDefaults.shape,
        )
        .height(32.dp)
        .padding(horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      modifier = Modifier.height(AssistChipDefaults.IconSize),
      imageVector = imageVector,
      contentDescription = contentDescription,
      tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Preview
@Composable
fun PreviewAssistChip() {
  AssistChip(onClick = {}, imageVector = Symbols.Settings, contentDescription = "")
}

@Preview
@Composable
fun PreviewFilterChip() {
  var isSelected by remember { mutableStateOf(true) }

  FilterChip(isSelected = isSelected, onClick = { isSelected = !isSelected }, label = "Label")
}

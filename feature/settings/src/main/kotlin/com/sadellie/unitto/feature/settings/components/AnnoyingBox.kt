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

package com.sadellie.unitto.feature.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.icons.symbols.Help
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols
import com.sadellie.unitto.core.ui.squashable

@Composable
internal fun AnnoyingBox(
  modifier: Modifier,
  onClick: () -> Unit,
  imageVector: ImageVector,
  imageVectorContentDescription: String,
  title: String,
  support: String,
) {
  Row(
    modifier =
      modifier
        .squashable(
          onClick = onClick,
          interactionSource = remember { MutableInteractionSource() },
          cornerRadiusRange = 15..25,
        )
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .padding(16.dp, 8.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      modifier = Modifier.size(24.dp),
      imageVector = imageVector,
      contentDescription = imageVectorContentDescription,
      tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Column(modifier = Modifier.weight(1f).padding(vertical = 8.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
      )
      Text(
        text = support,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
      )
    }
  }
}

@Preview
@Composable
fun PreviewAnnoyingBox() {
  AnnoyingBox(
    modifier = Modifier.fillMaxWidth(),
    onClick = {},
    imageVector = Symbols.Help,
    imageVectorContentDescription = "",
    title = "Title text",
    support = "Lorem ipsum or something",
  )
}

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.designsystem.shapes.Shapes
import com.sadellie.unitto.core.designsystem.shapes.Sizes

@Composable
fun TextFieldBox(modifier: Modifier = Modifier, content: @Composable TextFieldBoxScope.() -> Unit) {
  Column(
    modifier = Modifier.clip(Shapes.ExtraLarge).then(modifier),
    verticalArrangement = Arrangement.spacedBy(Sizes.extraSmall),
  ) {
    val scope = remember { TextFieldBoxScopeWrapper(this) }
    scope.content()
  }
}

interface TextFieldBoxScope : ColumnScope

object TextFieldBoxDefaults {
  val Padding =
    PaddingValues(start = Sizes.large, end = Sizes.large, top = Sizes.large, bottom = 24.dp)
}

private class TextFieldBoxScopeWrapper(scope: ColumnScope) :
  TextFieldBoxScope, ColumnScope by scope

@Suppress("UnusedReceiverParameter")
@Composable
fun TextFieldBoxScope.TextFieldRow(
  modifier: Modifier = Modifier,
  content: @Composable RowScope.() -> Unit,
) {
  Row(
    modifier = modifier.fillMaxWidth().heightIn(60.dp), // 56 + 4 for text field without labels
    horizontalArrangement = Arrangement.spacedBy(Sizes.small),
    verticalAlignment = Alignment.Bottom,
  ) {
    content()
  }
}

@Preview
@Composable
private fun PreviewTextFieldBox() {
  TextFieldBox(Modifier.background(MaterialTheme.colorScheme.secondaryContainer).fillMaxWidth()) {
    TextFieldRow {
      OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "Text 1",
        onValueChange = {},
        label = { Text("Label") },
      )
    }
    TextFieldRow {
      OutlinedTextField(
        modifier = Modifier.weight(1f),
        value = "Text 2",
        onValueChange = {},
        label = { Text("Label") },
      )
      OutlinedTextField(
        modifier = Modifier.weight(1f),
        value = "Text 3",
        onValueChange = {},
        label = { Text("Label") },
      )
    }
    TextFieldRow {
      OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = "Text 4", onValueChange = {})
    }
  }
}

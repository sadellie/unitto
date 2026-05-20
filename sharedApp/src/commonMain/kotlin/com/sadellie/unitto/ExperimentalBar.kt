/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025-2026 Elshan Agaev
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

package com.sadellie.unitto

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.ui.ListItemExpressive
import com.sadellie.unitto.core.ui.rectangleShapes
import com.sadellie.unitto.core.ui.rememberLinkOpener

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ExperimentalBar(modifier: Modifier) {
  val linkOpener = rememberLinkOpener()
  ListItemExpressive(
    modifier = modifier,
    content = { Text(text = "Alpha") },
    onClick = {},
    supportingContent = {
      Text(text = "Web version of Unitto is currently in Alpha. Expect the unexpected (bugs)")
    },
    trailingContent = {
      Button(
        onClick = { linkOpener.launch("https://github.com/sadellie/unitto/issues/new") },
        shapes = ButtonDefaults.shapes(),
      ) {
        Text("Report an issue")
      }
    },
    shapes = ListItemDefaults.rectangleShapes,
  )
}

@Preview
@Composable
private fun PreviewInfectedAssaultScreen() {
  ExperimentalBar(modifier = Modifier.fillMaxWidth())
}

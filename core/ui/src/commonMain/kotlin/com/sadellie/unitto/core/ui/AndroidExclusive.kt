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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.common.Config
import com.sadellie.unitto.core.designsystem.shapes.Sizes
import org.jetbrains.compose.resources.stringResource
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.android_exclusive_action
import unitto.core.common.generated.resources.android_exclusive_text
import unitto.core.common.generated.resources.android_exclusive_title

@Composable
fun AndroidExclusiveScreen(navigateUpAction: () -> Unit) {
  ScaffoldWithLargeTopBar(
    title = stringResource(Res.string.android_exclusive_title),
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { paddingValues ->
    Column(
      Modifier.padding(paddingValues + PaddingValues(horizontal = Sizes.large)),
      verticalArrangement = Arrangement.spacedBy(Sizes.large),
    ) {
      Text(stringResource(Res.string.android_exclusive_text))
      val linkOpener = rememberLinkOpener()
      Button(
        onClick = { linkOpener.launch(Config.STORE_LINK) },
        shapes = ButtonDefaults.shapes(),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(stringResource(Res.string.android_exclusive_action))
      }
    }
  }
}

@Composable
fun AndroidExclusiveDialog(onDismissRequest: () -> Unit) {
  AlertDialog(
    title = { Text(stringResource(Res.string.android_exclusive_title)) },
    text = { Text(stringResource(Res.string.android_exclusive_text)) },
    onDismissRequest = onDismissRequest,
    confirmButton = {
      val linkOpener = rememberLinkOpener()
      Button(
        onClick = { linkOpener.launch(Config.STORE_LINK) },
        shapes = ButtonDefaults.shapes(),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(stringResource(Res.string.android_exclusive_action))
      }
    },
  )
}

@Composable
@Preview
private fun AndroidExclusiveScreen() {
  AndroidExclusiveScreen(navigateUpAction = {})
}

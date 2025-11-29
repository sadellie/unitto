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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sadellie.unitto.core.designsystem.shapes.Sizes

@Composable
fun NotAvailableInWasm(navigateUpAction: () -> Unit) {
  ScaffoldWithLargeTopBar(
    title = "Not available in web",
    navigationIcon = { NavigateUpButton(navigateUpAction) },
  ) { paddingValues ->
    Column(
      Modifier.padding(paddingValues + PaddingValues(horizontal = Sizes.large)),
      verticalArrangement = Arrangement.spacedBy(Sizes.large),
    ) {
      Text("This feature is only available in Android version of the app")
      val linkOpener = rememberLinkOpener()
      Button(
        onClick = {
          linkOpener.launch("https://play.google.com/store/apps/details?id=com.sadellie.unitto")
        },
        shapes = ButtonDefaults.shapes(),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text("Get Android version")
      }
    }
  }
}

@Composable
@Preview
private fun PreviewNotAvailableInWasm() {
  NotAvailableInWasm(navigateUpAction = {})
}

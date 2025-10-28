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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.designsystem.icons.symbols.SearchOff
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchPlaceholder(onButtonClick: () -> Unit, supportText: String, buttonLabel: String) {
  Column(
    modifier = Modifier.fillMaxWidth().padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    Icon(
      imageVector = Symbols.SearchOff,
      contentDescription = stringResource(R.string.common_no_results_description),
      modifier = Modifier.size(48.dp),
    )
    Text(
      text = stringResource(R.string.common_no_results),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )
    Text(
      text = supportText,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodySmall,
    )
    OutlinedButton(
      onClick = onButtonClick,
      shapes = ButtonDefaults.shapes(),
      contentPadding = ButtonDefaults.SmallContentPadding,
      modifier = Modifier.height(ButtonDefaults.MinHeight),
    ) {
      Text(text = buttonLabel, style = ButtonDefaults.textStyleFor(ButtonDefaults.MinHeight))
    }
  }
}

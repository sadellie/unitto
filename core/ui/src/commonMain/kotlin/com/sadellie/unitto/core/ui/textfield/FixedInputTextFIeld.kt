/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

package com.sadellie.unitto.core.ui.textfield

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.TextStyle
import com.sadellie.unitto.core.common.FormatterSymbols

@Composable
fun FixedExpressionInputTextField(
  modifier: Modifier = Modifier,
  value: String,
  formatterSymbols: FormatterSymbols,
  textStyle: TextStyle,
  onClick: () -> Unit,
) {
  val clipboardManager = createExpressionClipboard(formatterSymbols, LocalClipboard.current)
  CompositionLocalProvider(LocalClipboard provides clipboardManager) {
    SelectionContainer(
      modifier =
        Modifier.horizontalScroll(rememberScrollState()) // Must be first
          .combinedClickable(onClick = onClick)
          .then(modifier)
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = value.formatExpression(formatterSymbols),
        style = textStyle,
      )
    }
  }
}

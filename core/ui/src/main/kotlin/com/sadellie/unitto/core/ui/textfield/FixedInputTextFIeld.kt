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

package com.sadellie.unitto.core.ui.textfield

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.designsystem.theme.LocalNumberTypography

@Composable
fun FixedExpressionInputTextField(
  modifier: Modifier = Modifier,
  value: String,
  formatterSymbols: FormatterSymbols,
  textColor: Color,
  onClick: () -> Unit,
) {
  val clipboardManager =
    ExpressionClipboardManager(
      formatterSymbols = formatterSymbols,
      nativeClipboard =
        LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE)
          as android.content.ClipboardManager,
    )

  CompositionLocalProvider(LocalClipboard provides clipboardManager) {
    SelectionContainer(
      modifier =
        Modifier.horizontalScroll(rememberScrollState()) // Must be first
          .clickable(onClick = onClick)
          .then(modifier)
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = value.formatExpression(formatterSymbols),
        style =
          LocalNumberTypography.current.displaySmall.copy(
            color = textColor,
            textAlign = TextAlign.End,
          ),
      )
    }
  }
}

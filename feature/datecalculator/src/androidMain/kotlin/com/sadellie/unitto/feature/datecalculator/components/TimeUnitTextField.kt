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

package com.sadellie.unitto.feature.datecalculator.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.ui.textfield.OutlinedDecimalTextField
import com.sadellie.unitto.core.ui.textfield.UnexpectedDigitsInputTransformation

@Composable
internal fun TimeUnitTextField(
  modifier: Modifier,
  state: TextFieldState,
  label: String,
  formatterSymbols: FormatterSymbols,
  maxValue: Double,
  imeAction: ImeAction = ImeAction.Next,
  allowFraction: Boolean = true,
) {
  OutlinedDecimalTextField(
    modifier = modifier,
    state = state,
    label = { AnimatedContent(label, label = "Text field label") { Text(it) } },
    colors =
      OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
      ),
    outputTransformation = null,
    // outputTransformation = ExpressionOutputTransformation(formatterSymbols), TODO 1.11.0-alpha05
    inputTransformation = UnexpectedDigitsInputTransformation(maxValue, allowFraction),
    imeAction = imeAction,
  )
}

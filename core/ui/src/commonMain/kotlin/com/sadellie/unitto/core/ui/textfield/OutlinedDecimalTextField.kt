/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.sadellie.unitto.core.designsystem.icons.symbols.Close
import com.sadellie.unitto.core.designsystem.icons.symbols.Symbols

@Composable
fun OutlinedDecimalTextField(
  modifier: Modifier,
  state: TextFieldState,
  label: @Composable () -> Unit,
  colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
  inputTransformation: InputTransformation,
  outputTransformation: OutputTransformation?,
  imeAction: ImeAction = ImeAction.Next,
) {
  OutlinedTextField(
    shape = MaterialTheme.shapes.large,
    modifier = modifier,
    state = state,
    trailingIcon = {
      AnimatedVisibility(state.text.isNotBlank(), enter = scaleIn(), exit = scaleOut()) {
        IconButton(onClick = state::clearText, shapes = IconButtonDefaults.shapes()) {
          Icon(Symbols.Close, null)
        }
      }
    },
    label = { label() },
    lineLimits = TextFieldLineLimits.SingleLine,
    inputTransformation = inputTransformation,
    outputTransformation = outputTransformation,
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = imeAction),
    colors = colors,
  )
}

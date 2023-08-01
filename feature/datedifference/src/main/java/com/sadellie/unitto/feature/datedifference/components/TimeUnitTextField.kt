/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.feature.datedifference.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.sadellie.unitto.core.ui.common.textfield.ExpressionTransformer
import com.sadellie.unitto.core.ui.common.textfield.FormatterSymbols

@Composable
internal fun TimeUnitTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction = ImeAction.Next,
    formatterSymbols: FormatterSymbols
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue.filter { it.isDigit() })
        },
        label = { Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotBlank(),
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(Icons.Outlined.Clear, null)
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Decimal,
            imeAction = imeAction
        ),
        visualTransformation = ExpressionTransformer(formatterSymbols)
    )
}

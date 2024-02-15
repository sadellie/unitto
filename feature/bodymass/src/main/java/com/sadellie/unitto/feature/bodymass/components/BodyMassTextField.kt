/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.feature.bodymass.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.sadellie.unitto.core.base.Token
import com.sadellie.unitto.core.ui.common.textfield.OutlinedDecimalTextField

@Composable
internal fun BodyMassTextField(
    modifier: Modifier,
    value: TextFieldValue,
    label: String,
    onValueChange: (TextFieldValue) -> Unit,
    expressionFormatter: VisualTransformation,
    imeAction: ImeAction = ImeAction.Next,
) {
    OutlinedDecimalTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            val cleanText = it.text
                .replace(",", ".")
                .filter { char ->
                    Token.Digit.allWithDot.contains(char.toString())
                }
            onValueChange(it.copy(cleanText))
        },
        label = { AnimatedContent(label, label = "Text field label") { Text(it) } },
        colors = OutlinedTextFieldDefaults
            .colors(
                focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
        expressionFormatter = expressionFormatter,
        imeAction = imeAction,
    )
}

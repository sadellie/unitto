/*
 * Unitto is a unit converter for Android
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.sadellie.unitto.core.base.Token

@Composable
internal fun BodyMassTextField(
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    expressionFormatter: VisualTransformation,
    imeAction: ImeAction
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
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
        label = { AnimatedContent(label) { Text(it) } },
        singleLine = true,
        visualTransformation = expressionFormatter,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = imeAction
        ),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

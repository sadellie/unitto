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

package com.sadellie.unitto.feature.epoch.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayMedium

@Composable
internal fun DateUnixTextFields(
    fromTextFieldValue: TextFieldValue,
    onCursorChange: (IntRange) -> Unit,
    fromSupportText: String,
    toTextValue: String,
    toSupportText: String,
    visualTransformation: VisualTransformation,
    fromPlaceholderText: String,
    toPlaceholderText: String
) {
    Column {
        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            BasicTextField(
                value = fromTextFieldValue,
                onValueChange = { onCursorChange(it.selection.start..it.selection.end) },
                textStyle = NumbersTextStyleDisplayMedium.copy(
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                minLines = 1,
                maxLines = 2,
                visualTransformation = visualTransformation,
                decorationBox = { innerTextField ->
                    Text(
                        text = fromPlaceholderText,
                        minLines = 1,
                        maxLines = 2,
                        style = NumbersTextStyleDisplayMedium,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Start
                    )
                    innerTextField()
                }
            )
        }
        Text(text = fromSupportText)
        Text(
            text = toTextValue.ifEmpty { toPlaceholderText },
            style = NumbersTextStyleDisplayMedium,
        )
        Text(text = toSupportText)
    }
}

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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayMedium

@Composable
fun TopPart(
    modifier: Modifier,
    dateToUnix: Boolean,
    swap: () -> Unit,
    dateValue: String,
    unixValue: String,
    selection: TextRange,
    onCursorChange: (TextFieldValue) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Crossfade(dateToUnix) {
            if (it) {
                DateUnixTextFields(
                    fromTextFieldValue = TextFieldValue(text = dateValue, selection = selection),
                    onCursorChange = onCursorChange,
                    fromSupportText = "date",
                    toTextValue = unixValue,
                    toSupportText = "unix",
                    visualTransformation = DateVisTrans,
                    fromPlaceholderText = dateValue.padEnd(14, '0').toDateMask(),
                    toPlaceholderText = "0"
                )
            } else {
                val dateMasked = dateValue.padEnd(14, '0').toDateMask()
                DateUnixTextFields(
                    fromTextFieldValue = TextFieldValue(text = unixValue, selection = selection),
                    onCursorChange = onCursorChange,
                    fromSupportText = "unix",
                    toTextValue = dateMasked,
                    toSupportText = "date",
                    visualTransformation = VisualTransformation.None,
                    fromPlaceholderText = if (unixValue.isEmpty()) "0" else "",
                    toPlaceholderText = dateMasked,
                )
            }
        }
        SwapButton(modifier = Modifier.fillMaxWidth(), swap = swap)
    }
}

@Composable
fun DateUnixTextFields(
    fromTextFieldValue: TextFieldValue,
    onCursorChange: (TextFieldValue) -> Unit,
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
                onValueChange = onCursorChange,
                textStyle = NumbersTextStyleDisplayMedium.copy(textAlign = TextAlign.Start),
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
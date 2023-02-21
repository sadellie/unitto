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

package com.sadellie.unitto.feature.calculator.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayLarge

@Composable
internal fun InputTextField(
    modifier: Modifier,
    value: TextFieldValue,
    onCursorChange: (IntRange) -> Unit,
    pasteCallback: (String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    CompositionLocalProvider(
        LocalTextInputService provides null,
        LocalTextToolbar provides UnittoTextToolbar(
            view = LocalView.current,
            pasteCallback = { pasteCallback(clipboardManager.getText()?.text ?: "") }
        )
    ) {
        BasicTextField(
            modifier = modifier,
            singleLine = true,
            value = value,
            onValueChange = {
                onCursorChange(it.selection.start..it.selection.end)
            },
            textStyle = NumbersTextStyleDisplayLarge.copy(
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            minLines = 1,
            maxLines = 1,
        )
    }
}

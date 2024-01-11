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

package com.sadellie.unitto.core.ui.common.textfield

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.sadellie.unitto.core.ui.common.textfield.autosize.AutoSizeTextField
import com.sadellie.unitto.core.ui.theme.LocalNumberTypography

@Composable
fun ExpressionTextField(
    modifier: Modifier,
    value: TextFieldValue,
    minRatio: Float = 1f,
    cutCallback: () -> Unit = {},
    pasteCallback: (String) -> Unit = {},
    onCursorChange: (TextRange) -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    formatterSymbols: FormatterSymbols,
    readOnly: Boolean = false,
    placeholder: String = "",
) {
    val localView = LocalView.current
    val clipboardManager = LocalClipboardManager.current
    val expressionTransformer = remember(formatterSymbols) { ExpressionTransformer(formatterSymbols) }

    fun copyCallback() {
        clipboardManager.copyWithoutGrouping(value, formatterSymbols)
        onCursorChange(TextRange(value.selection.end))
    }

    val textToolbar: UnittoTextToolbar = if (readOnly) {
        UnittoTextToolbar(
            view = localView,
            copyCallback = ::copyCallback,
        )
    } else {
        UnittoTextToolbar(
            view = localView,
            copyCallback = ::copyCallback,
            pasteCallback = {
                pasteCallback(clipboardManager.getText()?.text?.clearAndFilterExpression(formatterSymbols) ?: "")
            },
            cutCallback = {
                clipboardManager.copyWithoutGrouping(value, formatterSymbols)
                cutCallback()
            }
        )
    }

    AutoSizeTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onCursorChange(it.selection) },
        placeholder = placeholder,
        textToolbar = textToolbar,
        readOnly = readOnly,
        textStyle = LocalNumberTypography.current.displayLarge.copy(textColor),
        visualTransformation = expressionTransformer,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
        minRatio = minRatio
    )
}

@Composable
fun UnformattedTextField(
    modifier: Modifier,
    value: TextFieldValue,
    minRatio: Float = 1f,
    cutCallback: () -> Unit = {},
    pasteCallback: (String) -> Unit = {},
    onCursorChange: (TextRange) -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    readOnly: Boolean = false,
    placeholder: String = "",
) {
    val localView = LocalView.current
    val clipboardManager = LocalClipboardManager.current
    fun copyCallback() {
        clipboardManager.copy(value)
        onCursorChange(TextRange(value.selection.end))
    }

    val textToolbar: UnittoTextToolbar = remember(readOnly) {
        if (readOnly) {
            UnittoTextToolbar(
                view = localView,
                copyCallback = ::copyCallback,
            )
        } else {
            UnittoTextToolbar(
                view = localView,
                copyCallback = ::copyCallback,
                pasteCallback = {
                    pasteCallback(clipboardManager.getText()?.text?.clearAndFilterNumberBase() ?: "")
                },
                cutCallback = {
                    clipboardManager.copy(value)
                    cutCallback()
                }
            )
        }
    }

    AutoSizeTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onCursorChange(it.selection) },
        placeholder = placeholder,
        textToolbar = textToolbar,
        readOnly = readOnly,
        textStyle = LocalNumberTypography.current.displayLarge.copy(color = textColor),
        minRatio = minRatio,
    )
}

/**
 * Copy value to clipboard without grouping symbols.
 *
 * Example:
 * "123.456,789" will be copied as "123456,789"
 *
 * @param value Formatted value that has grouping symbols.
 */
fun ClipboardManager.copyWithoutGrouping(
    value: TextFieldValue,
    formatterSymbols: FormatterSymbols
) = this.setText(
    AnnotatedString(
        value.annotatedString
            .subSequence(value.selection)
            .text
            .replace(formatterSymbols.grouping, "")
    )
)

private fun ClipboardManager.copy(value: TextFieldValue) = this.setText(
    AnnotatedString(
        value.annotatedString
            .subSequence(value.selection)
            .text
    )
)

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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.sadellie.unitto.core.ui.common.autosize.AutoSizeTextStyleBox
import com.sadellie.unitto.core.ui.common.textfield.texttoolbar.UnittoTextToolbar
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
 * Based on: https://gist.github.com/inidamleader/b594d35362ebcf3cedf81055df519300
 *
 * @param placeholder Placeholder text, shown when [value] is empty.
 * @param textToolbar [TextToolbar] with modified actions in menu.
 * @param alignment The alignment of the text within its container.
 * @see [BasicTextField]
 * @see [AutoSizeTextStyleBox]
 */
@Composable
private fun AutoSizeTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String? = null,
    textToolbar: TextToolbar = LocalTextToolbar.current,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    maxTextSize: TextUnit = TextUnit.Unspecified,
    minRatio: Float = 1f,
    alignment: Alignment = Alignment.BottomEnd,
) = AutoSizeTextStyleBox(
    modifier = modifier,
    text = visualTransformation.filter(value.annotatedString).text,
    maxTextSize = maxTextSize,
    maxLines = maxLines,
    minLines = minLines,
    softWrap = false,
    style = textStyle,
    minRatio = minRatio,
    alignment = alignment
) {
    CompositionLocalProvider(
        LocalTextInputService provides null,
        LocalTextToolbar provides textToolbar
    ) {
        val currentTextToolbar = LocalTextToolbar.current
        val style = LocalTextStyle.current
        val focusRequester = remember { FocusRequester() }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        currentTextToolbar.hide()
                        focusRequester.requestFocus()
                        onValueChange(value.copy(selection = TextRange.Zero))
                        currentTextToolbar.showMenu(Rect(Offset.Zero, 0f))
                    }
                ),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = style,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            onTextLayout = onTextLayout,
            interactionSource = interactionSource,
            cursorBrush = cursorBrush,
            decorationBox = { innerTextField ->
                if (value.text.isEmpty() and !placeholder.isNullOrEmpty()) {
                    Text(
                        text = placeholder!!,
                        style = style.copy(
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    )
                }
                innerTextField()
            },
        )
    }
}

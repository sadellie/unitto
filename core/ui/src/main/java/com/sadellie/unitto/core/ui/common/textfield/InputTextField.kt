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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sadellie.unitto.core.ui.theme.LocalNumberTypography
import kotlin.math.roundToInt

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
    val clipboardManager = LocalClipboardManager.current
    fun copyCallback() {
        clipboardManager.copyWithoutGrouping(value, formatterSymbols)
        onCursorChange(TextRange(value.selection.end))
    }

    val textToolbar: UnittoTextToolbar = if (readOnly) {
        UnittoTextToolbar(
            view = LocalView.current,
            copyCallback = ::copyCallback,
        )
    } else {
        UnittoTextToolbar(
            view = LocalView.current,
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

    AutoSizableTextField(
        modifier = modifier,
        value = value,
        formattedValue = value.text.formatExpression(formatterSymbols),
        textStyle = LocalNumberTypography.current.displayLarge.copy(color = textColor),
        minRatio = minRatio,
        onValueChange = { onCursorChange(it.selection) },
        readOnly = readOnly,
        showToolbar = textToolbar::showMenu,
        hideToolbar = textToolbar::hide,
        visualTransformation = ExpressionTransformer(formatterSymbols),
        placeholder = placeholder,
        textToolbar = textToolbar,
        stepGranularityTextSize = 1.sp
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
    val clipboardManager = LocalClipboardManager.current
    fun copyCallback() {
        clipboardManager.copy(value)
        onCursorChange(TextRange(value.selection.end))
    }

    val textToolbar: UnittoTextToolbar = if (readOnly) {
        UnittoTextToolbar(
            view = LocalView.current,
            copyCallback = ::copyCallback,
        )
    } else {
        UnittoTextToolbar(
            view = LocalView.current,
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

    AutoSizableTextField(
        modifier = modifier,
        value = value,
        textStyle = LocalNumberTypography.current.displayLarge.copy(color = textColor),
        minRatio = minRatio,
        onValueChange = { onCursorChange(it.selection) },
        readOnly = readOnly,
        showToolbar = textToolbar::showMenu,
        hideToolbar = textToolbar::hide,
        placeholder = placeholder,
        textToolbar = textToolbar
    )
}

// https://gist.github.com/inidamleader/b594d35362ebcf3cedf81055df519300
@Composable
fun AutoSizableTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    formattedValue: String = value.text,
    textStyle: TextStyle = TextStyle(),
    onValueChange: (TextFieldValue) -> Unit,
    readOnly: Boolean = false,
    showToolbar: (rect: Rect) -> Unit = {},
    hideToolbar: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholder: String = "",
    textToolbar: UnittoTextToolbar,
    minRatio: Float = 1f,
    stepGranularityTextSize: TextUnit = 1.sp,
    suggestedFontSizes: List<TextUnit> = emptyList(),
) {
    val localDensity = LocalDensity.current
    val density = localDensity.density
    val focusRequester = remember { FocusRequester() }

    var offset by remember { mutableStateOf(Offset.Zero) }

    val displayValue = value.copy(text = value.text.take(2000))
    // Change font scale to 1
    CompositionLocalProvider(
        LocalDensity provides Density(density = density, fontScale = 1F),
        LocalTextInputService provides null,
        LocalTextToolbar provides textToolbar
    ) {
        BoxWithConstraints(
            modifier = modifier,
            contentAlignment = Alignment.BottomStart,
        ) {
            val resizeableTextStyle = resizeableTextStyle(
                text = formattedValue.ifEmpty { placeholder },
                textStyle = textStyle,
                minRatio = minRatio
            )

            BasicTextField(
                value = displayValue,
                onValueChange = {
                    showToolbar(Rect(offset, 0f))
                    hideToolbar()
                    onValueChange(it)
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            hideToolbar()
                            focusRequester.requestFocus()
                            onValueChange(value.copy(selection = TextRange.Zero))
                            showToolbar(Rect(offset, 0f))
                        }
                    )
                    .widthIn(max = with(localDensity) { resizeableTextStyle.layoutResult.multiParagraph.width.toDp() })
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        // TextField size is changed with a delay (text jumps). Here we correct it.
                        layout(placeable.width, placeable.height) {
                            placeable.place(
                                x = (resizeableTextStyle.layoutResult.multiParagraph.width - resizeableTextStyle.layoutResult.multiParagraph.maxIntrinsicWidth)
                                    .coerceAtLeast(0f)
                                    .roundToInt(),
                                y = (placeable.height - resizeableTextStyle.layoutResult.multiParagraph.height).roundToInt()
                            )
                        }
                    }

                    .onGloballyPositioned { layoutCoords ->
                        offset = layoutCoords.positionInWindow()
                    },
                readOnly = readOnly,
                textStyle = resizeableTextStyle.textStyle,
                singleLine = true,
                visualTransformation = visualTransformation,
                onTextLayout = {},
                interactionSource = remember { MutableInteractionSource() },
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                decorationBox = { innerTextField ->
                    if (displayValue.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = resizeableTextStyle.textStyle,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
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

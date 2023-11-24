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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sadellie.unitto.core.ui.theme.NumberTypographyUnitto
import kotlin.math.ceil
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
    val localView = LocalView.current
    val clipboardManager = LocalClipboardManager.current
    val expressionTransformer = remember(formatterSymbols) { ExpressionTransformer(formatterSymbols) }

    fun copyCallback() {
        clipboardManager.copyWithoutGrouping(value, formatterSymbols)
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
                    pasteCallback(clipboardManager.getText()?.text?.clearAndFilterExpression(formatterSymbols) ?: "")
                },
                cutCallback = {
                    clipboardManager.copyWithoutGrouping(value, formatterSymbols)
                    cutCallback()
                }
            )
        }
    }

    AutoSizableTextField(
        modifier = modifier,
        value = value,
        formattedValue = value.text.formatExpression(formatterSymbols),
        textStyle = NumberTypographyUnitto.displayLarge.copy(color = textColor),
        minRatio = minRatio,
        onValueChange = { onCursorChange(it.selection) },
        readOnly = readOnly,
        showToolbar = textToolbar::showMenu,
        hideToolbar = textToolbar::hide,
        visualTransformation = expressionTransformer,
        placeholder = placeholder,
        textToolbar = textToolbar,
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

    AutoSizableTextField(
        modifier = modifier,
        value = value,
        textStyle = NumberTypographyUnitto.displayLarge.copy(color = textColor),
        minRatio = minRatio,
        onValueChange = { onCursorChange(it.selection) },
        readOnly = readOnly,
        showToolbar = textToolbar::showMenu,
        hideToolbar = textToolbar::hide,
        placeholder = placeholder,
        textToolbar = textToolbar
    )
}

@Composable
private fun AutoSizableTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    formattedValue: String = value.text,
    textStyle: TextStyle = TextStyle(),
    scaleFactor: Float = 0.95f,
    minRatio: Float = 1f,
    onValueChange: (TextFieldValue) -> Unit,
    readOnly: Boolean = false,
    showToolbar: (rect: Rect) -> Unit = {},
    hideToolbar: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholder: String? = null,
    textToolbar: UnittoTextToolbar
) {
    val focusRequester = remember { FocusRequester() }
    val density = LocalDensity.current

    val textValue = value.copy(value.text.take(2000))
    var nFontSize: TextUnit by remember { mutableStateOf(0.sp) }
    var minFontSize: TextUnit

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ) {
        with(density) {
            // Cursor handle is not visible without this, 0.836f is the minimum required factor here
            nFontSize = maxHeight.toSp() * 0.83f
            minFontSize = nFontSize * minRatio
        }

        // Modified: https://blog.canopas.com/autosizing-textfield-in-jetpack-compose-7a80f0270853
        val calculateParagraph = @Composable {
            Paragraph(
                text = formattedValue,
                style = textStyle.copy(fontSize = nFontSize),
                constraints = Constraints(
                    maxWidth = ceil(with(density) { maxWidth.toPx() }).toInt()
                ),
                density = density,
                fontFamilyResolver = createFontFamilyResolver(LocalContext.current),
                spanStyles = listOf(),
                placeholders = listOf(),
                maxLines = 1,
                ellipsis = false
            )
        }

        var intrinsics = calculateParagraph()
        with(density) {
            while ((intrinsics.maxIntrinsicWidth > maxWidth.toPx()) && nFontSize >= minFontSize) {
                nFontSize *= scaleFactor
                intrinsics = calculateParagraph()
            }
        }

        val nTextStyle = textStyle.copy(
            // https://issuetracker.google.com/issues/266470454
            // textAlign = TextAlign.End,
            fontSize = nFontSize
        )
        var offset = Offset.Zero

        CompositionLocalProvider(
            LocalTextInputService provides null,
            LocalTextToolbar provides textToolbar
        ) {
            BasicTextField(
                value = textValue,
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
                    .widthIn(max = with(density) { intrinsics.width.toDp() })
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        // TextField size is changed with a delay (text jumps). Here we correct it.
                        layout(placeable.width, placeable.height) {
                            placeable.place(
                                x = (intrinsics.width - intrinsics.maxIntrinsicWidth)
                                    .coerceAtLeast(0f)
                                    .roundToInt(),
                                y = (placeable.height - intrinsics.height).roundToInt()
                            )
                        }
                    }
                    .onGloballyPositioned { layoutCoords ->
                        offset = layoutCoords.positionInWindow()
                    },
                textStyle = nTextStyle,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
                singleLine = true,
                readOnly = readOnly,
                visualTransformation = visualTransformation,
                decorationBox = { innerTextField ->
                    if (textValue.text.isEmpty() and !placeholder.isNullOrEmpty()) {
                        Text(
                            text = placeholder!!, // It's not null, i swear
                            style = nTextStyle,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)

                                layout(placeable.width, placeable.height) {
                                    placeable.place(x = -placeable.width, y = 0)
                                }
                            }
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

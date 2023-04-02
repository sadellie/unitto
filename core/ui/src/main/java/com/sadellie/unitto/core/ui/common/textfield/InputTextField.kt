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
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sadellie.unitto.core.base.Separator
import com.sadellie.unitto.core.ui.Formatter
import com.sadellie.unitto.core.ui.theme.NumbersTextStyleDisplayLarge
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun InputTextField(
    modifier: Modifier,
    value: TextFieldValue,
    textStyle: TextStyle = NumbersTextStyleDisplayLarge,
    minRatio: Float = 1f,
    cutCallback: () -> Unit,
    pasteCallback: (String) -> Unit,
    onCursorChange: (IntRange) -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val clipboardManager = LocalClipboardManager.current
    fun copyCallback() = clipboardManager.copyWithoutGrouping(value)

    val textToolbar = UnittoTextToolbar(
        view = LocalView.current,
        copyCallback = ::copyCallback,
        pasteCallback = {
            pasteCallback(
                Formatter.toSeparator(
                    clipboardManager.getText()?.text ?: "", Separator.COMMA
                )
            )
        },
        cutCallback = {
            copyCallback()
            cutCallback()
            onCursorChange(value.selection.end..value.selection.end)
        }
    )

    CompositionLocalProvider(
        LocalTextInputService provides null,
        LocalTextToolbar provides textToolbar
    ) {
        AutoSizableTextField(
            modifier = modifier,
            value = value,
            textStyle = textStyle.copy(color = textColor),
            minRatio = minRatio,
            onValueChange = {
                onCursorChange(it.selection.start..it.selection.end)
            },
            showToolbar = textToolbar::showMenu,
            hideToolbar = textToolbar::hide
        )
    }
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = NumbersTextStyleDisplayLarge,
    minRatio: Float = 1f,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    var textFieldValue by remember(value) {
        mutableStateOf(TextFieldValue(value, selection = TextRange(value.length)))
    }
    val clipboardManager = LocalClipboardManager.current
    fun copyCallback() {
        clipboardManager.copyWithoutGrouping(textFieldValue)
        textFieldValue = textFieldValue.copy(selection = TextRange(textFieldValue.selection.end))
    }

    CompositionLocalProvider(
        LocalTextInputService provides null,
        LocalTextToolbar provides UnittoTextToolbar(
            view = LocalView.current,
            copyCallback = ::copyCallback,
        )
    ) {
        AutoSizableTextField(
            modifier = modifier,
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            textStyle = textStyle.copy(color = textColor),
            minRatio = minRatio,
            readOnly = true,
            interactionSource = interactionSource
        )
    }
}

@Composable
private fun AutoSizableTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    textStyle: TextStyle = TextStyle(),
    scaleFactor: Float = 0.95f,
    minRatio: Float = 1f,
    onValueChange: (TextFieldValue) -> Unit,
    readOnly: Boolean = false,
    showToolbar: (rect: Rect) -> Unit = {},
    hideToolbar: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val focusRequester = remember { FocusRequester() }
    val density = LocalDensity.current

    var nFontSize: TextUnit by remember { mutableStateOf(0.sp) }
    var minFontSize: TextUnit

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.BottomStart
    ) {
        with(density) {
            // Cursor handle is not visible without this, 0.836f is the minimum required factor here
            nFontSize = maxHeight.toSp() * 0.836f
            minFontSize = nFontSize * minRatio
        }

        // Modified: https://blog.canopas.com/autosizing-textfield-in-jetpack-compose-7a80f0270853
        val calculateParagraph = @Composable {
            Paragraph(
                text = value.text,
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

        BasicTextField(
            value = value,
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
                .onGloballyPositioned { layoutCoords -> offset = layoutCoords.positionInWindow() },
            textStyle = nTextStyle,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
            singleLine = true,
            readOnly = readOnly,
            interactionSource = interactionSource
        )
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
fun ClipboardManager.copyWithoutGrouping(value: TextFieldValue) = this.setText(
    AnnotatedString(
        Formatter.removeGrouping(value.annotatedString.subSequence(value.selection).text)
    )
)

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

package com.sadellie.unitto.core.ui.common.textfield.autosize

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.InternalFoundationTextApi
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextDelegate
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sadellie.unitto.core.ui.common.textfield.autosize.SuggestedFontSizesStatus.Companion.suggestedFontSizesStatus
import kotlin.math.min

/**
 * Based on: https://gist.github.com/inidamleader/b594d35362ebcf3cedf81055df519300
 *
 * @param value The [androidx.compose.ui.text.input.TextFieldValue] to be shown in the
 * [BasicTextField].
 * @param onValueChange Called when the input service updates the values in [TextFieldValue].
 * @param placeholder Placeholder text, shown when [value] is empty.
 * @param textToolbar [TextToolbar] with modified actions in menu.
 * @param modifier optional [Modifier] for this text field.
 * @param enabled controls the enabled state of the [BasicTextField]. When `false`, the text
 * field will be neither editable nor focusable, the input of the text field will not be selectable
 * @param readOnly controls the editable state of the [BasicTextField]. When `true`, the text
 * field can not be modified, however, a user can focus it and copy text from it. Read-only text
 * fields are usually used to display pre-filled forms that user can not edit
 * @param textStyle Style configuration that applies at character level such as color, font etc.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when set to true, this text field becomes a single horizontally scrolling
 * text field instead of wrapping onto multiple lines. The keyboard will be informed to not show
 * the return key as the [ImeAction]. [maxLines] and [minLines] are ignored as both are
 * automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param visualTransformation The visual transformation filter for changing the visual
 * representation of the input. By default no visual transformation is applied.
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw a cursor or selection around the text.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 * @param cursorBrush [Brush] to paint cursor with. If [SolidColor] with [Color.Unspecified]
 * provided, there will be no cursor drawn
 * @param suggestedFontSizes The suggested font sizes to choose from (Should be sorted from smallest to largest, not empty and contains only sp text unit).
 * @param suggestedFontSizesStatus Whether or not suggestedFontSizes is valid: not empty - contains oly sp text unit - sorted.
 * You can check validity by invoking [List<TextUnit>.suggestedFontSizesStatus]
 * @param stepGranularityTextSize The step size for adjusting the text size.
 * @param minTextSize The minimum text size allowed.
 * @param maxTextSize The maximum text size allowed.
 * @param minRatio How small font can be. Percentage from calculated max font size.
 * @param lineSpacingRatio The ratio of line spacing to text size.
 * @param alignment The alignment of the text within its container.
 */
@Composable
internal fun AutoSizeTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String? = "",
    textToolbar: TextToolbar,
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
    suggestedFontSizes: ImmutableWrapper<List<TextUnit>> = emptyList<TextUnit>().toImmutableWrapper(),
    suggestedFontSizesStatus: SuggestedFontSizesStatus = suggestedFontSizes.value.suggestedFontSizesStatus,
    stepGranularityTextSize: TextUnit = TextUnit.Unspecified,
    minTextSize: TextUnit = TextUnit.Unspecified,
    maxTextSize: TextUnit = TextUnit.Unspecified,
    minRatio: Float = 1f,
    lineSpacingRatio: Float = textStyle.lineHeight.value / textStyle.fontSize.value,
    alignment: Alignment = Alignment.BottomEnd,
) = CompositionLocalProvider(
    LocalTextInputService provides null,
    LocalTextToolbar provides textToolbar
) {
    val localTextToolbar = LocalTextToolbar.current

    AutoSizeTextField(
        value = value,
        onValueChange = {
            localTextToolbar.showMenu(Rect(Offset.Zero, 0f))
            localTextToolbar.hide()
            onValueChange(it)
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        decorationBox = { innerTextField, calculatedStyle ->
            if (value.text.isEmpty() and !placeholder.isNullOrEmpty()) {
                Text(
                    text = placeholder!!,
                    style = calculatedStyle().copy(
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    )
                )
            }
            innerTextField()
        },
        suggestedFontSizes = suggestedFontSizes,
        suggestedFontSizesStatus = suggestedFontSizesStatus,
        stepGranularityTextSize = stepGranularityTextSize,
        minTextSize = minTextSize,
        maxTextSize = maxTextSize,
        minRatio = minRatio,
        lineSpacingRatio = lineSpacingRatio,
        alignment = alignment,
    )
}

/**
 * Based on: https://gist.github.com/inidamleader/b594d35362ebcf3cedf81055df519300
 *
 * @param value The [androidx.compose.ui.text.input.TextFieldValue] to be shown in the
 * [BasicTextField].
 * @param onValueChange Called when the input service updates the values in [TextFieldValue].
 * @param modifier optional [Modifier] for this text field.
 * @param enabled controls the enabled state of the [BasicTextField]. When `false`, the text
 * field will be neither editable nor focusable, the input of the text field will not be selectable
 * @param readOnly controls the editable state of the [BasicTextField]. When `true`, the text
 * field can not be modified, however, a user can focus it and copy text from it. Read-only text
 * fields are usually used to display pre-filled forms that user can not edit
 * @param textStyle Style configuration that applies at character level such as color, font etc.
 * @param keyboardOptions software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine when set to true, this text field becomes a single horizontally scrolling
 * text field instead of wrapping onto multiple lines. The keyboard will be informed to not show
 * the return key as the [ImeAction]. [maxLines] and [minLines] are ignored as both are
 * automatically set to 1.
 * @param maxLines the maximum height in terms of maximum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param minLines the minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines]. This parameter is ignored when [singleLine] is true.
 * @param visualTransformation The visual transformation filter for changing the visual
 * representation of the input. By default no visual transformation is applied.
 * @param onTextLayout Callback that is executed when a new text layout is calculated. A
 * [TextLayoutResult] object that callback provides contains paragraph information, size of the
 * text, baselines and other details. The callback can be used to add additional decoration or
 * functionality to the text. For example, to draw a cursor or selection around the text.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TextField. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TextField in different [Interaction]s.
 * @param cursorBrush [Brush] to paint cursor with. If [SolidColor] with [Color.Unspecified]
 * provided, there will be no cursor drawn
 * @param decorationBox Composable lambda that allows to add decorations around text field, such
 * as icon, placeholder, helper messages or similar, and automatically increase the hit target area
 * of the text field. To allow you to control the placement of the inner text field relative to your
 * decorations, the text field implementation will pass in a framework-controlled composable
 * parameter "innerTextField" to the decorationBox lambda you provide. You must call
 * innerTextField exactly once.
 * @param suggestedFontSizes The suggested font sizes to choose from (Should be sorted from smallest to largest, not empty and contains only sp text unit).
 * @param suggestedFontSizesStatus Whether or not suggestedFontSizes is valid: not empty - contains oly sp text unit - sorted.
 * You can check validity by invoking [List<TextUnit>.suggestedFontSizesStatus]
 * @param stepGranularityTextSize The step size for adjusting the text size.
 * @param minTextSize The minimum text size allowed.
 * @param maxTextSize The maximum text size allowed.
 * @param minRatio How small font can be. Percentage from calculated max font size.
 * @param lineSpacingRatio The ratio of line spacing to text size.
 * @param alignment The alignment of the text within its container.
 */
@Composable
private fun AutoSizeTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(Color.Black),
    decorationBox: @Composable (
        innerTextField: @Composable () -> Unit,
        textStyle: () -> TextStyle, // Passing lambda for performance
    ) -> Unit = { _, _ -> },
    suggestedFontSizes: ImmutableWrapper<List<TextUnit>> = emptyList<TextUnit>().toImmutableWrapper(),
    suggestedFontSizesStatus: SuggestedFontSizesStatus = suggestedFontSizes.value.suggestedFontSizesStatus,
    stepGranularityTextSize: TextUnit = TextUnit.Unspecified,
    minTextSize: TextUnit = TextUnit.Unspecified,
    maxTextSize: TextUnit = TextUnit.Unspecified,
    minRatio: Float = 1f,
    lineSpacingRatio: Float = textStyle.lineHeight.value / textStyle.fontSize.value,
    alignment: Alignment = Alignment.TopStart,
) {
    val focusRequester = remember { FocusRequester() }
    val density = LocalDensity.current
    val localTextToolbar = LocalTextToolbar.current
    // Change font scale to 1F
    CompositionLocalProvider(
        LocalDensity provides Density(density = density.density, fontScale = 1F)
    ) {
        BoxWithConstraints(
            modifier = modifier,
            contentAlignment = alignment,
        ) {
            val combinedTextStyle = LocalTextStyle.current + textStyle.copy(
                textAlign = when (alignment) {
                    Alignment.TopStart, Alignment.CenterStart, Alignment.BottomStart -> TextAlign.Start
                    Alignment.TopCenter, Alignment.Center, Alignment.BottomCenter -> TextAlign.Center
                    Alignment.TopEnd, Alignment.CenterEnd, Alignment.BottomEnd -> TextAlign.End
                    else -> TextAlign.Unspecified
                },
            )

            val candidateFontSizes =
                if (suggestedFontSizesStatus == SuggestedFontSizesStatus.VALID)
                    suggestedFontSizes.value
                else
                    remember(suggestedFontSizes) {
                        suggestedFontSizes.value
                            .filter { it.isSp }
                            .takeIf { it.isNotEmpty() }
                            ?.sortedBy { it.value }
                    } ?: rememberCandidateFontSizes(
                        density = density,
                        maxDpSize = DpSize(maxWidth, maxHeight),
                        maxTextSize = maxTextSize,
                        minTextSize = minTextSize,
                        stepGranularityTextSize = stepGranularityTextSize,
                    )

            val layoutDirection = LocalLayoutDirection.current
            val currentDensity = LocalDensity.current
            val fontFamilyResolver = LocalFontFamilyResolver.current
            val coercedLineSpacingRatio = lineSpacingRatio.coerceAtLeast(1f).takeIf { !lineSpacingRatio.isNaN() } ?: 1f
            if (candidateFontSizes.isEmpty()) return@BoxWithConstraints
            val (electedIndex, _) = candidateFontSizes.findElectedIndex {
                calculateLayout(
                    text = visualTransformation.filter(value.annotatedString).text,
                    textStyle = combinedTextStyle.copy(
                        fontSize = it,
                        lineHeight = it * coercedLineSpacingRatio,
                    ),
                    maxLines = maxLines,
                    minLines = minLines,
                    softWrap = true,
                    layoutDirection = layoutDirection,
                    density = currentDensity,
                    fontFamilyResolver = fontFamilyResolver,
                )
            }
            val minFontSize = candidateFontSizes.maxBy { it.value } * minRatio
            val electedFontSize = candidateFontSizes[electedIndex]
            val fSize: TextUnit = listOf(minFontSize, electedFontSize).maxBy { it.value }
            val calculatedTextStyle = combinedTextStyle.copy(
                fontSize = fSize,
                lineHeight = fSize * coercedLineSpacingRatio
            )

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
                            localTextToolbar.hide()
                            focusRequester.requestFocus()
                            onValueChange(value.copy(selection = TextRange.Zero))
                            localTextToolbar.showMenu(Rect(Offset.Zero, 0f))
                        }
                    ),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = calculatedTextStyle,
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
                    decorationBox(innerTextField) {
                        combinedTextStyle.copy(
                            fontSize = electedFontSize,
                            lineHeight = electedFontSize * coercedLineSpacingRatio
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun rememberCandidateFontSizes(
    density: Density,
    maxDpSize: DpSize,
    minTextSize: TextUnit = TextUnit.Unspecified,
    maxTextSize: TextUnit = TextUnit.Unspecified,
    stepGranularityTextSize: TextUnit = TextUnit.Unspecified,
): List<TextUnit> {
    val max = remember(maxTextSize, maxDpSize, density) {
        val size = density.toIntSize(maxDpSize)
        min(size.width, size.height).let { max ->
            maxTextSize
                .takeIf { it.isSp }
                ?.let { density.roundToPx(it) }
                ?.coerceIn(range = 0..max)
                ?: max
        }
    }

    val min = remember(minTextSize, max, density) {
        minTextSize
            .takeIf { it.isSp }
            ?.let { density.roundToPx(it) }
            ?.coerceIn(range = 0..max)
            ?: 0
    }

    val step = remember(stepGranularityTextSize, min, max, density) {
        stepGranularityTextSize
            .takeIf { it.isSp }
            ?.let { density.roundToPx(it) }
            ?.coerceAtLeast(minimumValue = 1)
            ?: 1
    }

    return remember(max, min, step) {
        buildList {
            var current = min
            while (current <= max) {
                add(density.toSp(current))
                current += step
            }
        }
    }
}

@OptIn(InternalFoundationTextApi::class)
private fun BoxWithConstraintsScope.calculateLayout(
    text: AnnotatedString,
    textStyle: TextStyle,
    maxLines: Int,
    minLines: Int,
    softWrap: Boolean,
    layoutDirection: LayoutDirection,
    density: Density,
    fontFamilyResolver: FontFamily.Resolver,
) = TextDelegate(
    text = text,
    style = textStyle,
    maxLines = maxLines,
    minLines = minLines,
    softWrap = softWrap,
    overflow = TextOverflow.Clip,
    density = density,
    fontFamilyResolver = fontFamilyResolver,
).layout(
    constraints = constraints,
    layoutDirection = layoutDirection,
)

// This function works by using a binary search algorithm
fun List<TextUnit>.findElectedIndex(
    shouldMoveBackward: (TextUnit) -> TextLayoutResult
): Pair<Int, TextLayoutResult?> {
    val elements = this
    var low = 0
    var high = elements.lastIndex
    var textLayoutResult: TextLayoutResult? = null
    while (low <= high) {
        val mid = low + (high - low) / 2
        textLayoutResult = shouldMoveBackward(elements[mid])
        if (textLayoutResult.hasVisualOverflow)
            high = mid - 1
        else
            low = mid + 1
    }
    return high.coerceIn(elements.indices) to textLayoutResult
}

enum class SuggestedFontSizesStatus {
    VALID, INVALID;

    companion object {
        val List<TextUnit>.suggestedFontSizesStatus
            get() = if (isNotEmpty() && all { it.isSp } && this.sortedBy { it.value } == this)
                VALID
            else
                INVALID
    }
}

@Preview(widthDp = 200, heightDp = 100)
@Preview(widthDp = 200, heightDp = 30)
@Preview(widthDp = 60, heightDp = 30)
@Composable
fun PreviewAutoSizeTextWithMaxLinesSetToIntMaxValue() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.primary) {
            AutoSizeTextField(
                value = TextFieldValue("This is a bunch of text that will be auto sized"),
                onValueChange = {},
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.CenterStart,
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(widthDp = 200, heightDp = 100)
@Preview(widthDp = 200, heightDp = 30)
@Preview(widthDp = 60, heightDp = 30)
@Composable
fun PreviewAutoSizeTextWithMinSizeSetTo14() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            AutoSizeTextField(
                value = TextFieldValue("This is a bunch of text that will be auto sized"),
                onValueChange = {},
                modifier = Modifier.fillMaxSize(),
                minTextSize = 14.sp,
                alignment = Alignment.CenterStart,
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(widthDp = 200, heightDp = 100)
@Preview(widthDp = 200, heightDp = 30)
@Preview(widthDp = 60, heightDp = 30)
@Composable
fun PreviewAutoSizeTextWithMaxLinesSetToOne() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.tertiary) {
            AutoSizeTextField(
                value = TextFieldValue("This is a bunch of text that will be auto sized"),
                onValueChange = {},
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                maxLines = 1,
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

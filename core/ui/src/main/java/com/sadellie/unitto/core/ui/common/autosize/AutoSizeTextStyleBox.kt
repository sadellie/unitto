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

package com.sadellie.unitto.core.ui.common.autosize

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.text.InternalFoundationTextApi
import androidx.compose.foundation.text.TextDelegate
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import kotlin.math.min
import kotlin.math.roundToInt

// HUGE performance drop. Don't use for buttons
///**
// * Composable function that automatically adjusts the text size to fit within given constraints using AnnotatedString, considering the ratio of line spacing to text size.
// *
// * Features:
// *  Similar to AutoSizeText(String), with support for AnnotatedString.
// *
// * @param inlineContent a map storing composables that replaces certain ranges of the text, used to
// * insert composables into text layout. See [InlineTextContent].
// * @see AutoSizeText
// */
//@Composable
//internal fun AutoSizeText(
//    text: AnnotatedString,
//    modifier: Modifier = Modifier,
//    minRatio: Float = 1f,
//    maxTextSize: TextUnit = TextUnit.Unspecified,
//    alignment: Alignment = Alignment.TopStart,
//    overflow: TextOverflow = TextOverflow.Clip,
//    softWrap: Boolean = true,
//    maxLines: Int = Int.MAX_VALUE,
//    minLines: Int = 1,
//    inlineContent: Map<String, InlineTextContent> = mapOf(),
//    onTextLayout: (TextLayoutResult) -> Unit = {},
//    style: TextStyle = LocalTextStyle.current,
//) = AutoSizeTextStyleBox(
//    modifier = modifier,
//    text = text,
//    maxTextSize = maxTextSize,
//    maxLines = maxLines,
//    minLines = minLines,
//    softWrap = softWrap,
//    style = style,
//    minRatio = minRatio,
//    alignment = alignment
//) {
//    Text(
//        text = text,
//        overflow = overflow,
//        softWrap = softWrap,
//        maxLines = maxLines,
//        minLines = minLines,
//        inlineContent = inlineContent,
//        onTextLayout = onTextLayout,
//        style = LocalTextStyle.current,
//    )
//}

/**
 * [BoxWithConstraints] with [autoTextStyle] passed via [LocalTextStyle].
 *
 * @param modifier Modifier the [Modifier] to be applied to this layout node,
 * @param text Text the text to be displayed,
 * @param maxTextSize The maximum text size allowed.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 * If the text exceeds the given number of lines, it will be truncated according to overflow and
 * [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines].
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * overflow and TextAlign may have unexpected effects.
 * @param style Original style.
 * @param minRatio How small font can be. Percentage from calculated max font size.
 * @param alignment The alignment of the text within its container.
 * @param content Content in [BoxWithConstraints]. Use [LocalTextStyle] to access calculated
 * [TextStyle].
 */
@Composable
internal fun AutoSizeTextStyleBox(
    modifier: Modifier,
    text: AnnotatedString,
    maxTextSize: TextUnit,
    maxLines: Int,
    minLines: Int,
    softWrap: Boolean,
    style: TextStyle,
    minRatio: Float,
    alignment: Alignment,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    CompositionLocalProvider(
        LocalDensity provides Density(density = density.density, fontScale = 1F)
    ) {
        BoxWithConstraints(
            modifier = modifier,
            contentAlignment = alignment,
        ) {
            val autoTextStyle = autoTextStyle(
                text = text,
                maxTextSize = maxTextSize,
                maxLines = maxLines,
                minLines = minLines,
                softWrap = softWrap,
                style = style,
                alignment = alignment,
                density = density,
                minRatio = minRatio
            )

            CompositionLocalProvider(
                value = LocalTextStyle.provides(autoTextStyle),
                content = content
            )
        }
    }
}

/**
 * Calculates text size that will fill available space in [BoxWithConstraints].
 *
 * @param text Text the text to be displayed,
 * @param maxTextSize The maximum text size allowed.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 * If the text exceeds the given number of lines, it will be truncated according to overflow and
 * [softWrap]. It is required that 1 <= [minLines] <= [maxLines].
 * @param minLines The minimum height in terms of minimum number of visible lines. It is required
 * that 1 <= [minLines] <= [maxLines].
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 * text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 * overflow and TextAlign may have unexpected effects.
 * @param style Original style.
 * @param alignment The alignment of the text within its container.
 * @param density Original [Density]. This is required since [AutoSizeTextStyleBox] overrides
 * [Density.fontScale] to `1F`.
 * @param minRatio How small font can be. Percentage from calculated max font size.
 * @return Calculated [TextStyle].
 */
@Composable
private fun BoxWithConstraintsScope.autoTextStyle(
    text: AnnotatedString,
    maxTextSize: TextUnit = TextUnit.Unspecified,
    maxLines: Int,
    minLines: Int,
    softWrap: Boolean,
    style: TextStyle,
    alignment: Alignment,
    density: Density,
    minRatio: Float,
): TextStyle {
    val combinedTextStyle = LocalTextStyle.current + style.copy(
        textAlign = when (alignment) {
            Alignment.TopStart, Alignment.CenterStart, Alignment.BottomStart -> TextAlign.Start
            Alignment.TopCenter, Alignment.Center, Alignment.BottomCenter -> TextAlign.Center
            Alignment.TopEnd, Alignment.CenterEnd, Alignment.BottomEnd -> TextAlign.End
            else -> TextAlign.Unspecified
        },
    )

    val layoutDirection = LocalLayoutDirection.current
    val currentDensity = LocalDensity.current
    val fontFamilyResolver = LocalFontFamilyResolver.current
    val coercedLineSpacingRatio = (style.lineHeight.value / style.fontSize.value)
        .takeIf { it.isFinite() && it >= 1 } ?: 1F
    val shouldMoveBackward: (TextUnit) -> Boolean = {
        shouldShrink(
            text = text,
            textStyle = combinedTextStyle.copy(
                fontSize = it,
                lineHeight = it * coercedLineSpacingRatio,
            ),
            maxLines = maxLines,
            minLines = minLines,
            softWrap = softWrap,
            layoutDirection = layoutDirection,
            density = currentDensity,
            fontFamilyResolver = fontFamilyResolver,
        )
    }

    val electedFontSize = rememberCandidateFontSizesIntProgress(
        density = density,
        dpSize = DpSize(maxWidth, maxHeight),
        maxTextSize = maxTextSize,
        minRatio = minRatio,
    ).findElectedValue(
        transform = { density.toSp(it) },
        shouldMoveBackward = shouldMoveBackward,
    )

    return combinedTextStyle.copy(
        fontSize = electedFontSize,
        lineHeight = electedFontSize * coercedLineSpacingRatio,
    )
}

@OptIn(InternalFoundationTextApi::class)
private fun BoxWithConstraintsScope.shouldShrink(
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
).hasVisualOverflow

@Composable
private fun rememberCandidateFontSizesIntProgress(
    density: Density,
    dpSize: DpSize,
    maxTextSize: TextUnit = TextUnit.Unspecified,
    minRatio: Float = 1f,
): IntProgression {
    val max = remember(maxTextSize, dpSize, density) {
        val intSize = density.toIntSize(dpSize)
        min(intSize.width, intSize.height).let { max ->
            maxTextSize
                .takeIf { it.isSp }
                ?.let { density.roundToPx(it) }
                ?.coerceIn(range = 0..max)
                ?: max
        }
    }

    val min = remember(minRatio, max, density) {
        (max * minRatio).roundToInt()
    }

    return remember(min, max) {
        min..max step 1
    }
}

// This function works by using a binary search algorithm
private fun <E> IntProgression.findElectedValue(
    transform: (Int) -> E,
    shouldMoveBackward: (E) -> Boolean,
) = run {
    var low = first
    var high = last
    while (low <= high) {
        val mid = low + (high - low) / 2
        if (shouldMoveBackward(transform(mid)))
            high = mid - 1
        else
            low = mid + 1
    }
    transform(high.coerceAtLeast(minimumValue = first))
}

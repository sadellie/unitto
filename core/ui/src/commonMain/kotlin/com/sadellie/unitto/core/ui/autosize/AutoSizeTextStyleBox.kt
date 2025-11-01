/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui.autosize

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.text.input.TextFieldLineLimits
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
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * [BoxWithConstraints] with [autoTextStyle] passed via [LocalTextStyle].
 *
 * @param modifier Modifier the [Modifier] to be applied to this layout node,
 * @param text Text the text to be displayed,
 * @param maxTextSize The maximum text size allowed.
 * @param lineLimits Whether the text field should be [TextFieldLineLimits.SingleLine], scroll
 *   horizontally, and ignore newlines; or [TextFieldLineLimits.MultiLine] and grow and scroll
 *   vertically. If [TextFieldLineLimits.SingleLine] is passed, all newline characters ('\n') within
 *   the text will be replaced with regular whitespace (' '), ensuring that the contents of the text
 *   field are presented in a single line.
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 *   overflow and TextAlign may have unexpected effects.
 * @param style Original style.
 * @param minRatio How small font can be. Percentage from calculated max font size.
 * @param alignment The alignment of the text within its container.
 * @param content Content in [BoxWithConstraints]. Use [LocalTextStyle] to access calculated
 *   [TextStyle].
 */
@Composable
internal fun AutoSizeTextStyleBox(
  modifier: Modifier,
  text: AnnotatedString,
  maxTextSize: TextUnit,
  lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
  softWrap: Boolean,
  style: TextStyle,
  minRatio: Float,
  alignment: Alignment,
  content: @Composable () -> Unit,
) {
  val density = LocalDensity.current
  CompositionLocalProvider(
    LocalDensity provides Density(density = density.density, fontScale = 1F)
  ) {
    BoxWithConstraints(modifier = modifier, contentAlignment = alignment) {
      val maxLines =
        remember(lineLimits) {
          when (lineLimits) {
            is TextFieldLineLimits.MultiLine -> lineLimits.maxHeightInLines
            TextFieldLineLimits.SingleLine -> 1
          }
        }

      val autoTextStyle =
        autoTextStyle(
          text = text,
          maxTextSize = maxTextSize,
          maxLines = maxLines,
          softWrap = softWrap,
          style = style,
          alignment = alignment,
          density = density,
          minRatio = minRatio,
        )

      CompositionLocalProvider(value = LocalTextStyle.provides(autoTextStyle), content = content)
    }
  }
}

/**
 * Calculates text size that will fill available space in [BoxWithConstraints].
 *
 * @param text Text the text to be displayed,
 * @param maxTextSize The maximum text size allowed.
 * @param maxLines An optional maximum number of lines for the text to span, wrapping if necessary.
 *   If the text exceeds the given number of lines, it will be truncated according to overflow and
 *   [softWrap].
 * @param softWrap whether the text should break at soft line breaks. If false, the glyphs in the
 *   text will be positioned as if there was unlimited horizontal space. If [softWrap] is false,
 *   overflow and TextAlign may have unexpected effects.
 * @param style Original style.
 * @param alignment The alignment of the text within its container.
 * @param density Original [Density]. This is required since [AutoSizeTextStyleBox] overrides
 *   [Density.fontScale] to `1F`.
 * @param minRatio How small font can be. Percentage from calculated max font size.
 * @return Calculated [TextStyle].
 */
@Composable
private fun BoxWithConstraintsScope.autoTextStyle(
  text: AnnotatedString,
  maxTextSize: TextUnit = TextUnit.Unspecified,
  maxLines: Int,
  softWrap: Boolean,
  style: TextStyle,
  alignment: Alignment,
  density: Density,
  minRatio: Float,
): TextStyle {
  val textMeasurer = rememberTextMeasurer()
  val combinedTextStyle =
    LocalTextStyle.current +
      style.copy(
        textAlign =
          when (alignment) {
            Alignment.TopStart,
            Alignment.CenterStart,
            Alignment.BottomStart -> TextAlign.Start
            Alignment.TopCenter,
            Alignment.Center,
            Alignment.BottomCenter -> TextAlign.Center
            Alignment.TopEnd,
            Alignment.CenterEnd,
            Alignment.BottomEnd -> TextAlign.End
            else -> TextAlign.Unspecified
          }
      )

  val layoutDirection = LocalLayoutDirection.current
  val currentDensity = LocalDensity.current
  val fontFamilyResolver = LocalFontFamilyResolver.current
  val coercedLineSpacingRatio =
    (style.lineHeight.value / style.fontSize.value).takeIf { it.isFinite() && it >= 1 } ?: 1F
  val shouldMoveBackward: (TextUnit) -> Boolean = {
    shouldShrink(
      text = text,
      textStyle = combinedTextStyle.copy(fontSize = it, lineHeight = it * coercedLineSpacingRatio),
      maxLines = maxLines,
      layoutDirection = layoutDirection,
      softWrap = softWrap,
      density = currentDensity,
      fontFamilyResolver = fontFamilyResolver,
      textMeasurer = textMeasurer,
    )
  }

  val electedFontSize =
    rememberCandidateFontSizesIntProgress(
        density = density,
        dpSize = DpSize(maxWidth, maxHeight),
        maxTextSize = maxTextSize,
        minRatio = minRatio,
      )
      .findElectedValue(transform = { density.toSp(it) }, shouldMoveBackward = shouldMoveBackward)

  return combinedTextStyle.copy(
    fontSize = electedFontSize,
    lineHeight = electedFontSize * coercedLineSpacingRatio,
  )
}

private fun BoxWithConstraintsScope.shouldShrink(
  text: AnnotatedString,
  textStyle: TextStyle,
  maxLines: Int,
  layoutDirection: LayoutDirection,
  softWrap: Boolean,
  density: Density,
  fontFamilyResolver: FontFamily.Resolver,
  textMeasurer: TextMeasurer,
) =
  textMeasurer
    .measure(
      text = text,
      style = textStyle,
      overflow = TextOverflow.Clip,
      softWrap = softWrap,
      maxLines = maxLines,
      constraints = constraints,
      layoutDirection = layoutDirection,
      density = density,
      fontFamilyResolver = fontFamilyResolver,
    )
    .hasVisualOverflow

@Composable
private fun rememberCandidateFontSizesIntProgress(
  density: Density,
  dpSize: DpSize,
  maxTextSize: TextUnit = TextUnit.Unspecified,
  minRatio: Float = 1f,
): IntProgression {
  val max =
    remember(maxTextSize, dpSize, density) {
      val intSize = density.toIntSize(dpSize)
      min(intSize.width, intSize.height).let { max ->
        maxTextSize.takeIf { it.isSp }?.let { density.roundToPx(it) }?.coerceIn(range = 0..max)
          ?: max
      }
    }

  val min = remember(minRatio, max, density) { (max * minRatio).roundToInt() }

  return remember(min, max) { min..max step 1 }
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
    if (shouldMoveBackward(transform(mid))) {
      high = mid - 1
    } else {
      low = mid + 1
    }
  }
  transform(high.coerceAtLeast(minimumValue = first))
}

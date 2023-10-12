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

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.text.InternalFoundationTextApi
import androidx.compose.foundation.text.TextDelegate
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.reflect.KProperty

@Composable
internal fun BoxWithConstraintsScope.resizeableTextStyle(
    text: String,
    textStyle: TextStyle,
    minRatio: Float,
    stepGranularityTextSize: TextUnit = 1.sp,
    suggestedFontSizes: List<TextUnit> = emptyList(),
): ResizeableTextStyle {
    val fontSizes = suggestedFontSizes.toImmutableWrapper()
    val localDensity = LocalDensity.current
    val maxTextSize by remember {
        with(localDensity) {
            mutableStateOf(maxHeight.toSp())
        }
    }

    val minTextSize by remember {
        mutableStateOf(maxTextSize * minRatio)
    }

    // (1 / density).sp represents 1px when font scale equals 1
    val step = remember(stepGranularityTextSize) {
        (1 / localDensity.density).let {
            if (stepGranularityTextSize.isUnspecified)
                it.sp
            else
                stepGranularityTextSize.value.coerceAtLeast(it).sp
        }
    }

    val max = remember(maxWidth, maxHeight, maxTextSize) {
        min(maxWidth, maxHeight).value.let {
            if (maxTextSize.isUnspecified)
                it.sp
            else
                maxTextSize.value.coerceAtMost(it).sp
        }
    }

    val min = remember(minTextSize, step, max) {
        if (minTextSize.isUnspecified)
            step
        else
            minTextSize.value.coerceIn(
                minimumValue = step.value,
                maximumValue = max.value
            ).sp
    }

    val possibleFontSizes = remember(fontSizes, min, max, step) {
        if (fontSizes.value.isEmpty()) {
            val firstIndex = ceil(min.value / step.value).toInt()
            val lastIndex = floor(max.value / step.value).toInt()
            MutableList(size = (lastIndex - firstIndex) + 1) { index ->
                step * (lastIndex - index)
            }
        } else
            fontSizes.value.filter {
                it.isSp && it.value in min.value..max.value
            }.sortedByDescending {
                it.value
            }
    }

    var combinedTextStyle = LocalTextStyle.current + textStyle
    var layoutResult: TextLayoutResult = layoutText(
        text = text,
        textStyle = combinedTextStyle,
        maxLines = 1,
        softWrap = false,
    )

    if (possibleFontSizes.isNotEmpty()) {
        // Dichotomous binary search
        var low = 0
        var high = possibleFontSizes.lastIndex
        while (low <= high) {
            val mid = low + (high - low) / 2
            layoutResult = layoutText(
                text = text,
                textStyle = combinedTextStyle.copy(fontSize = possibleFontSizes[mid]),
                maxLines = 1,
                softWrap = false,
            )

            if (layoutResult.hasVisualOverflow) low = mid + 1
            else high = mid - 1
        }
        combinedTextStyle = combinedTextStyle.copy(
            fontSize = possibleFontSizes[low.coerceIn(possibleFontSizes.indices)],
        )
    }

    return ResizeableTextStyle(
        combinedTextStyle, layoutResult
    )
}

@OptIn(InternalFoundationTextApi::class)
@Composable
internal fun BoxWithConstraintsScope.layoutText(
    text: String,
    textStyle: TextStyle,
    maxLines: Int,
    softWrap: Boolean,
): TextLayoutResult = TextDelegate(
    text = AnnotatedString(text),
    style = textStyle,
    maxLines = maxLines,
    softWrap = softWrap,
    overflow = TextOverflow.Clip,
    density = LocalDensity.current,
    fontFamilyResolver = LocalFontFamilyResolver.current,
)
    .layout(constraints, LocalLayoutDirection.current)

@Immutable
internal data class ResizeableTextStyle(
    val textStyle: TextStyle,
    val layoutResult: TextLayoutResult,
)

@Immutable
private data class ImmutableWrapper<T>(
    val value: T,
)

private fun <T> T.toImmutableWrapper() = ImmutableWrapper(this)

private operator fun <T> ImmutableWrapper<T>.getValue(thisRef: Any?, property: KProperty<*>) = value

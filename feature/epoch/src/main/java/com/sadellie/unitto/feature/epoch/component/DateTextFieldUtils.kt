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

package com.sadellie.unitto.feature.epoch.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

internal object DateVisTrans : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText = TransformedText(
        text = AnnotatedString(text.text.toDateMask()),
        offsetMapping = offsetMapping
    )

    private val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 4) return offset + 1
            if (offset <= 6) return offset + 2
            if (offset <= 8) return offset + 3
            if (offset <= 10) return offset + 4
            if (offset <= 14) return offset + 5
            return 20
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 9) return offset - 2
            if (offset <= 11) return offset - 3
            if (offset <= 14) return offset - 4
            if (offset <= 19) return offset - 5
            return 14
        }
    }
}

internal fun String.toDateMask(): String {
    var maskedText = this

    if (maskedText.length > 2) maskedText = maskedText.replaceRange(2, 2, ":")
    if (maskedText.length > 5) maskedText = maskedText.replaceRange(5, 5, ":")
    if (maskedText.length > 8) maskedText = maskedText.replaceRange(8, 8, "\n")
    if (maskedText.length > 10) maskedText = maskedText.replaceRange(11, 11, "d")
    if (maskedText.length > 13) maskedText = maskedText.replaceRange(14, 14, "m")
    if (maskedText.length > 18) maskedText = maskedText.replaceRange(19, 19, "y")

    return maskedText
}

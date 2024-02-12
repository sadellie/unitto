/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.sadellie.unitto.core.base.FormatterSymbols

class ExpressionTransformer(private val formatterSymbols: FormatterSymbols) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val formatted = text.text.formatExpression(formatterSymbols)
        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = ExpressionMapping(text.text, formatted)
        )
    }

    inner class ExpressionMapping(
        private val original: String,
        private val transformed: String
    ) : OffsetMapping {
        // Called when entering text (on each text change)
        // Basically moves cursor to the right position
        //
        // original input is "1000" and cursor is placed at the end "1000|"
        // the transformed is "1,000" where cursor should be? - "1,000|"
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 0) return 0
            if (offset >= original.length) return transformed.length

            val unformattedSubstr = original.take(offset)
            var buffer = ""
            var groupings = 0

            run {
                transformed.forEach {
                    when (it) {
                        formatterSymbols.grouping.first() -> groupings++
                        formatterSymbols.fractional.first() -> buffer += "."
                        else -> buffer += it
                    }
                    if (buffer == unformattedSubstr) return@run
                }
            }

            return transformed.fixCursor(buffer.length + groupings, formatterSymbols.grouping)
        }

        // Called when clicking transformed text
        // Snaps cursor to the right position
        //
        // the transformed is "1,000" and cursor is placed at the end "1,000|"
        // original input is "1000" where cursor should be? - "1000|"
        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 0) return 0
            if (offset >= transformed.length) return original.length

            val grouping = formatterSymbols.grouping.first()
            val fixedCursor = transformed.fixCursor(offset, formatterSymbols.grouping)
            val addedSymbols = transformed.take(fixedCursor).count { it == grouping }
            return fixedCursor - addedSymbols
        }
    }
}

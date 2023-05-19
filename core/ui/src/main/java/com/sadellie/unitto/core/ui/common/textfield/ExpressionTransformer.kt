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

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ExpressionTransformer(private val formatterSymbols: FormatterSymbols) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val formatted = text.text.formatExpression(formatterSymbols)
        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = ExpressionMapping(text.text, formatted)
        )
    }

    inner class ExpressionMapping(
        private val unformatted: String,
        private val formatted: String
    ) : OffsetMapping {
        // Called when entering text (on each text change)
        // Basically moves cursor to the right position
        //
        // original input is "1000" and cursor is placed at the end "1000|"
        // the formatted is "1,000" where cursor should be? - "1,000|"
        override fun originalToTransformed(offset: Int): Int {
            val unformattedSubstr = unformatted.take(offset)
            var buffer = ""
            var groupings = 0

            run {
                formatted.forEach {
                    when (it) {
                        formatterSymbols.grouping.first() -> groupings++
                        formatterSymbols.fractional.first() -> buffer += "."
                        else -> buffer += it
                    }
                    if (buffer == unformattedSubstr) return@run
                }
            }

            return formatted.fixCursor(buffer.length + groupings, formatterSymbols.grouping)
        }

        // Called when clicking formatted text
        // Snaps cursor to the right position
        //
        // the formatted is "1,000" and cursor is placed at the end "1,000|"
        // original input is "1000" where cursor should be? - "1000|"
        override fun transformedToOriginal(offset: Int): Int {
            val grouping = formatterSymbols.grouping.first()
            val fixedCursor = formatted.fixCursor(offset, formatterSymbols.grouping)
            val addedSymbols = formatted.take(fixedCursor).count { it == grouping }
            return fixedCursor - addedSymbols
        }
    }
}

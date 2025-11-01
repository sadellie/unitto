/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

package com.sadellie.unitto.core.ui.textfield

import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert
import androidx.compose.runtime.Stable
import com.sadellie.unitto.core.common.FormatterSymbols

/**
 * - Inserts missing grouping symbols
 * - Replaces fraction symbol to user defined one
 * - Doesn't filter output
 */
@Stable
data class ExpressionOutputTransformation(private val formatterSymbols: FormatterSymbols) :
  OutputTransformation {
  override fun TextFieldBuffer.transformOutput() {
    if (length == 0) return
    val formattedText = this.toString().formatExpression(formatterSymbols)

    formattedText.forEachIndexed { index, char ->
      when (char) {
        formatterSymbols.grouping.first() -> insert(index, formatterSymbols.grouping)

        formatterSymbols.fractional.first() ->
          replace(index, index + 1, formatterSymbols.fractional)
      }
    }
  }
}

/**
 * - Uppercase for all chars
 * - Doesn't filter output
 */
object NumberBaseOutputTransformation : OutputTransformation {
  override fun TextFieldBuffer.transformOutput() {
    if (length == 0) return
    for (charIndex in 0..<length) {
      replace(charIndex, charIndex + 1, charAt(charIndex).uppercase())
    }
  }
}

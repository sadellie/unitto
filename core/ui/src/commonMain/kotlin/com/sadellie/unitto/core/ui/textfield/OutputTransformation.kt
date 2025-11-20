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
import androidx.compose.runtime.Stable
import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token

/**
 * - Inserts missing grouping symbols
 * - Replaces fraction symbol to user defined one
 * - Doesn't filter output
 */
@Stable
data class ExpressionOutputTransformation(private val formatterSymbols: FormatterSymbols) :
  OutputTransformation {
  private val groupingChar = formatterSymbols.grouping.first()
  private val fractionalChar = formatterSymbols.fractional.first()

  override fun TextFieldBuffer.transformOutput() {
    if (length == 0) return

    val isFractionalString = Token.DisplayOnly.FRACTION in this.asCharSequence()
    if (isFractionalString) {
      formatFractional()
    } else {
      // format number in expression
      formatNoFractional()
    }
  }

  private fun TextFieldBuffer.formatFractional() {
    // format fraction
    // fractions are represented like so (123 is integral, 3/4 is fraction): 123 3/4
    val splitFractional = this.asCharSequence().splitToSequence(' ')
    // no integral found. do not format fractional
    if (splitFractional.count() != 2) return

    val (integralStart, integralEnd) = this.findNumberPositions().firstOrNull() ?: return
    // invalid integral number

    // format integral part (whole number). passing position of 123
    formatNumber(integralStart, integralEnd)
  }

  private fun TextFieldBuffer.formatNoFractional() {
    // starts and ends of numbers
    val numberPositions = this.findNumberPositions()
    numberPositions.forEach { (start, end) -> formatNumber(start, end) }
  }

  private fun TextFieldBuffer.findNumberPositions(): Set<Pair<Int, Int>> {
    val numberPositions = mutableSetOf<Pair<Int, Int>>()
    this.asCharSequence().findNumbers { startIndex, number ->
      numberPositions.add(startIndex to startIndex + number.length)
    }
    return numberPositions
  }

  /**
   * Format number at given position. [start] is inclusive, [end] is exclusive.
   *
   * if string is 12345, and you want to pass 123: start is 0, end is 3
   */
  private fun TextFieldBuffer.formatNumber(start: Int, end: Int) {
    val originalNumber = this.asCharSequence().substring(start, end)
    val formattedNumber = originalNumber.formatNumber(formatterSymbols)
    formattedNumber.forEachIndexed { index, char ->
      val indexInBuffer = start + index
      when (char) {
        groupingChar -> {
          val nextSymbol = formattedNumber.getOrNull(index + 1) ?: ""
          // [1][2][3][4][5][6] -> [1][2][3][ 4][5][6]
          replace(indexInBuffer, indexInBuffer + 1, "${formatterSymbols.grouping}${nextSymbol}")
        }

        fractionalChar -> replace(indexInBuffer, indexInBuffer + 1, formatterSymbols.fractional)
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

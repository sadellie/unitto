/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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

import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token

fun String.formatExpression(formatterSymbols: FormatterSymbols): String {
  if (isEmpty()) return this

  return if (Token.Fraction.symbol in this) {
    formatFraction(formatterSymbols)
  } else {
    formatExpressionNoFraction(formatterSymbols)
  }
}

private fun String.formatFraction(formatterSymbols: FormatterSymbols): String {
  // Only format integral part
  val splitFractional = this.split(" ")
  // No integral part
  if (splitFractional.size != 2) return this

  val formattedWholeNumber = splitFractional[0].formatNumber(formatterSymbols)
  val formattedFractionalPart = splitFractional[1]
  return "$formattedWholeNumber $formattedFractionalPart"
}

private fun String.formatExpressionNoFraction(formatterSymbols: FormatterSymbols): String {
  var input = this
  val numberPositions = mutableSetOf<Pair<Int, Int>>()
  this.findNumbers { startIndex, number ->
    numberPositions.add(startIndex to startIndex + number.length)
  }

  var shift = 0
  numberPositions.forEach { (start, end) ->
    val number = input.substring(start + shift, end + shift)
    val formattedNumber = number.formatNumber(formatterSymbols)
    val updatedInput = input.replaceRange(start + shift, end + shift, formattedNumber)
    shift += formattedNumber.length - number.length
    input = updatedInput
  }

  // Replace ugly symbols
  Token.sexyToUgly.forEach { (token, uglySymbols) ->
    uglySymbols.forEach { uglySymbol -> input = input.replace(uglySymbol, token) }
  }

  return input
}

/**
 * Format a single number ([this]) using [formatterSymbols]. This method inserts grouping symbols.
 *
 * @see FormatterSymbols.indian
 */
internal fun String.formatNumber(formatterSymbols: FormatterSymbols): String {
  if (this.any { it.isLetter() }) return this

  val firstPart = this.takeWhile { it != '.' }
  val remainingPart = this.removePrefix(firstPart)

  val output =
    when {
      firstPart.length <= 3 -> firstPart
      formatterSymbols.indian ->
        firstPart
          .dropLast(3) // 12354678 -> 12345
          .reversed() // 54321
          .chunked(2) // 54 32 1
          .joinToString(formatterSymbols.grouping.symbol) // 54,32,1
          .reversed() // 1,23,45
          .plus(formatterSymbols.grouping.symbol)
          .plus(firstPart.takeLast(3)) // last group is 12345[678]
      else ->
        firstPart
          .reversed() // 12345678 -> 87654321
          .chunked(3) // 876 543 21
          .joinToString(formatterSymbols.grouping.symbol) // 876,543,21
          .reversed() // 12,345,678
    }

  return output.plus(remainingPart.replace(".", formatterSymbols.fractional.symbol))
}

internal fun CharSequence.findNumbers(onFind: (startIndex: Int, number: String) -> Unit) {
  var index = 0
  while (index <= this.lastIndex) {
    val number = this.substring(index).takeWhile { it.toString() in Token.digitsWithDotSymbols }
    if (number.isBlank()) {
      // no number
      index++
    } else {
      val isValidNumber = number.count { it == '.' } <= 1
      if (isValidNumber) onFind(index, number)
      // even if number was invalid, move index past it
      index += number.length
    }
  }
}

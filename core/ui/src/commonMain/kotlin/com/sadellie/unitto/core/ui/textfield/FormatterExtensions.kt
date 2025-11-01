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

import com.sadellie.unitto.core.common.FormatterSymbols
import com.sadellie.unitto.core.common.Token

fun String.formatExpression(formatterSymbols: FormatterSymbols): String {
  if (isEmpty()) return this

  return if (Token.DisplayOnly.FRACTION in this) {
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

  // format numbers
  numbersRegex.findAll(input).map(MatchResult::value).forEach {
    input = input.replace(it, it.formatNumber(formatterSymbols))
  }

  // Replace ugly symbols
  Token.sexyToUgly.forEach { (token, uglySymbols) ->
    uglySymbols.forEach { uglySymbol -> input = input.replace(uglySymbol, token) }
  }

  return input
}

private fun String.formatNumber(formatterSymbols: FormatterSymbols): String {
  val input = this

  if (input.any { it.isLetter() }) return input

  var firstPart = input.takeWhile { it != '.' }
  val remainingPart = input.removePrefix(firstPart)

  // Number of empty symbols (spaces) we need to add to correctly split into chunks.
  val offset = 3 - firstPart.length.mod(3)
  val output =
    if (offset != 3) {
      // We add some spaces at the beginning so that last chunk has 3 symbols
      firstPart = " ".repeat(offset) + firstPart
      firstPart.chunked(3).joinToString(formatterSymbols.grouping).drop(offset)
    } else {
      firstPart.chunked(3).joinToString(formatterSymbols.grouping)
    }

  return output.plus(remainingPart.replace(".", formatterSymbols.fractional))
}

private val numbersRegex by lazy { Regex("[\\d.]+") }

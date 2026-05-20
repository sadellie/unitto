/*
 * Unitto is a calculator for Android
 * Copyright (c) 2026 Elshan Agaev
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

package io.github.sadellie.evaluatto.programmer

import com.sadellie.unitto.core.common.Token
import io.github.sadellie.evaluatto.findTokenAhead
import io.github.sadellie.evaluatto.fixupUnary
import io.github.sadellie.evaluatto.math.TokenizerException
import io.github.sadellie.evaluatto.missingClosingBrackets

/** Numbers use uppercase, everything else uses lowercase */
internal fun String.tokenizeProgrammer(): List<Token.Programmer> {
  val input = this
  if (input.isEmpty()) return emptyList()
  var cursor = 0
  val tokens = mutableListOf<Token.Programmer>()

  while (cursor != input.length) {
    val tokenAhead = findTokenAhead(input, cursor, Token.parseableProgrammerTokens)
    if (tokenAhead != null) {
      tokens.add(tokenAhead)
      cursor += tokenAhead.symbol.length
      continue
    }

    if (input[cursor].isDigitOrUpperCase()) {
      val numberTokenAhead = findNumberTokenAhead(input, cursor)
      tokens.add(numberTokenAhead)
      cursor += numberTokenAhead.symbol.length
      continue
    }

    cursor++
  }

  return tokens.fixupUnary().missingClosingBrackets()
}

private fun findNumberTokenAhead(input: String, cursor: Int): Token.Number {
  // walk left while
  val number = input.substring(cursor).takeWhile { char -> char.isDigitOrUpperCase() }
  if (number.count { it == '.' } > 1) throw TokenizerException.TooManyFractionSymbols()
  return Token.Number(number)
}

private fun Char.isDigitOrUpperCase() = isDigit() || isUpperCase()

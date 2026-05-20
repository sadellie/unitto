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

package io.github.sadellie.evaluatto

import com.sadellie.unitto.core.common.Token

internal fun <T : Token> findTokenAhead(input: String, cursor: Int, parseableTokens: List<T>): T? {
  for (token in parseableTokens) {
    // look in front for possible match with current token
    val lookUpString =
      input.substring(cursor, (cursor + token.symbol.length).coerceAtMost(input.length))
    val isMatched = token.symbol == lookUpString
    if (isMatched) return token
  }
  return null
}

internal inline fun <reified T : Token> MutableList<T>.missingClosingBrackets(): MutableList<T> {
  val leftBracket = this.count { it == Token.LeftBracket }
  val rightBrackets = this.count { it == Token.RightBracket }
  val neededBrackets = leftBracket - rightBrackets

  if (neededBrackets <= 0) return this

  repeat(neededBrackets) { this.add(Token.RightBracket as T) }
  return this
}

internal inline fun <reified T : Token> MutableList<T>.fixupUnary(): MutableList<T> {
  for ((index, token) in this.withIndex()) {
    if (token != Token.Minus) continue

    // first symbol is always unary
    if (index == 0) {
      this[index] = Token.UnaryMinus as T
      continue
    }

    // always unary when first in brackets
    // always unary when following another operator
    val previousToken = this[index - 1]
    if (
      previousToken is Token.LeftBracket ||
        (previousToken is Token.Operator &&
          !(previousToken.isUnary &&
            previousToken.associativity == Token.Operator.Associativity.LEFT))
    ) {
      this[index] = Token.UnaryMinus as T
    }
  }
  return this
}

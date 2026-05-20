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

package io.github.sadellie.evaluatto.math

import com.sadellie.unitto.core.common.Token
import io.github.sadellie.evaluatto.findTokenAhead
import io.github.sadellie.evaluatto.fixupUnary
import io.github.sadellie.evaluatto.missingClosingBrackets

internal sealed class TokenizerException(message: String) : Exception(message) {
  class TooManyFractionSymbols : TokenizerException("Number has multiple commas in it")

  class FailedToUnpackNumber : TokenizerException("Unexpected token before percentage")

  class BadScientificNotation : TokenizerException("Expected plus or minus symbol after \"E\"")
}

internal fun String.tokenizeMath(): List<Token.Math> {
  val input = this
  if (input.isEmpty()) return emptyList()
  var cursor = 0
  val tokens = mutableListOf<Token.Math>()

  while (cursor != input.length) {
    // try to find token
    val tokenAhead = findTokenAhead(input, cursor, Token.parseableMathTokens)

    // if found add token to result and move cursor
    if (tokenAhead != null) {
      tokens.add(tokenAhead)
      cursor += tokenAhead.symbol.length
      continue
    }

    val charInFront = input[cursor]
    // not found maybe number if starts with a number
    if (charInFront.isDigitOrDot()) {
      // start with a number. go right until numbers end
      val numberTokenAhead = findNumberTokenAhead(input, cursor)
      // add number token to result and move cursor
      tokens.add(numberTokenAhead)
      cursor += numberTokenAhead.symbol.length
      continue
    }

    cursor++
  }

  return tokens.fixupUnary().repairLexicon()
}

private fun findNumberTokenAhead(input: String, cursor: Int): Token.Number {
  // walk left while
  val number = input.substring(cursor).takeWhile { char -> char.isDigitOrDot() }
  if (number.count { it == '.' } > 1) throw TokenizerException.TooManyFractionSymbols()
  return Token.Number(number)
}

private fun Char.isDigitOrDot(): Boolean = isDigit() || this == '.'

private fun MutableList<Token.Math>.repairLexicon(): List<Token.Math> {
  return this.missingClosingBrackets().unpackNotation().missingMultiply()
}

private fun MutableList<Token.Math>.missingMultiply(): MutableList<Token.Math> {
  val iterator = this.listIterator()

  // input    : (12)34
  // tokenized: ["(", "1", "2", ")", "3", "4"]
  // scan from left to right by one token:
  // "(" can not be followed by a missing multiply, skip
  // "1" can be followed by a missing multiply
  // Next token is "2" and it doesn't need multiply symbol between tokens: "12" is valid
  // "2" same. "2)" is valid
  // ")" can be followed by a missing multiply
  // Next token is a digit. ")3" is not valid, insert multiply symbol
  // "3" is skipped. "34" is valid
  // "4" is skipped, no more tokens
  // can be replaced with "windowed" from kotlin

  while (iterator.hasNext()) {
    val currentToken = iterator.next()

    // Need two token for checks
    if (!iterator.hasNext()) break

    // may need a multiplication after one of these
    val isDigit1 = currentToken is Token.Number
    val isConst1 = currentToken is Token.Const
    val isRightBracket1 = currentToken == Token.RightBracket
    val isFactorial1 = currentToken == Token.Factorial
    val canBeFollowedByMultiplyToken = isDigit1 || isConst1 || isRightBracket1 || isFactorial1
    if (canBeFollowedByMultiplyToken) {
      // Peek next, but then go back
      val nextToken = iterator.next()
      // basically doing this: "12|" -> "1|2"
      iterator.previous()

      // needs a multiply symbol between tokens if one of these
      val isLeftBracket2 = nextToken == Token.LeftBracket
      val isFunction2 = nextToken is Token.Func
      val isConst2 = nextToken is Token.Const
      val isSqrt2 = nextToken == Token.Sqrt
      val isDigit2 = nextToken is Token.Number

      val canHaveMultiplyBefore = isLeftBracket2 || isFunction2 || isConst2 || isSqrt2 || isDigit2
      if (canHaveMultiplyBefore) {
        iterator.add(Token.Multiply)
      }
    }
  }

  return this
}

private fun MutableList<Token.Math>.unpackNotation(): MutableList<Token.Math> {
  // Transform 1E+7 ==> 1*10^7
  // Transform 1E-7 ==> 1/10^7
  val iterator = this.listIterator()

  while (iterator.hasNext()) {
    if (iterator.next() == Token.EngineeringE) {
      iterator.remove()

      val tokenAfterE =
        try {
          iterator.next()
        } catch (_: Exception) {
          throw TokenizerException.BadScientificNotation()
        }

      iterator.remove()

      when (tokenAfterE) {
        Token.Minus -> iterator.add(Token.Divide)
        Token.Plus -> iterator.add(Token.Multiply)
        else -> throw TokenizerException.BadScientificNotation()
      }

      iterator.add(Token.Number("10"))
      iterator.add(Token.Power)
    }
  }

  return this
}

/**
 * Extracts tokenized number or entire expression before [pos] in list of tokens ([this]). Cursor
 * must be after number or right bracket.
 *
 * Example ([pos] is indicated with '|'):
 * - 123456| -> 123456
 * - 132.5+(14)|% -> (14)
 * - 132.5+(15+4)|% -> (15+4)
 * - 132.5+(15+4)%| -> throws [TokenizerException.FailedToUnpackNumber]
 */
internal fun List<Token.Math>.getExpressionBefore(pos: Int): List<Token.Math> {
  val tokenInFront = this[pos]

  // Just number
  if (tokenInFront is Token.Number) return listOf(tokenInFront)

  // For cases like "100+(2+5)|%" or "2!|+5". The check above won't pass, so the next expected thing
  // is
  // a number or expression in brackets. Anything else is not expected.
  val validTokenInFront = tokenInFront == Token.RightBracket || tokenInFront == Token.Factorial
  if (!validTokenInFront) throw TokenizerException.FailedToUnpackNumber()

  var cursor = pos
  var leftBrackets = 0
  var rightBrackets = 0

  // Start walking left until we get balanced brackets
  while (cursor >= 0) {
    val currentToken = this[cursor]
    if (currentToken == Token.LeftBracket) leftBrackets++
    if (currentToken == Token.RightBracket) rightBrackets++
    if (leftBrackets == rightBrackets && currentToken != Token.Factorial) break
    cursor--
  }

  // +1 because subList end is exclusive
  return this.subList(cursor.coerceAtLeast(0), pos + 1)
}

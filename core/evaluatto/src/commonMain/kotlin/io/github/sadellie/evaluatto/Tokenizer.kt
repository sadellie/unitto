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

package io.github.sadellie.evaluatto

import com.sadellie.unitto.core.common.Token2

internal sealed class TokenizerException(message: String) : Exception(message) {

  class TooManyFractionSymbols : TokenizerException("Number has multiple commas in it")

  class FailedToUnpackNumber : TokenizerException("Unexpected token before percentage")

  class BadScientificNotation : TokenizerException("Expected plus or minus symbol after \"E\"")
}

internal fun String.tokenize(): List<Token2> {
  val input = this
  if (input.isEmpty()) return emptyList()
  var cursor = 0
  val tokens = mutableListOf<Token2>()

  while (cursor != input.length) {
    // try to find token
    val tokenAhead = findTokenAhead(input, cursor)

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

  for ((index, token) in tokens.withIndex()) {
    if (token != Token2.Minus) continue

    // first symbol is always unary
    if (index == 0) {
      tokens[index] = Token2.UnaryMinus
      continue
    }

    // always unary when first in brackets
    // always unary when following another operator
    val previousToken = tokens[index - 1]
    if (
      previousToken is Token2.LeftBracket ||
        (previousToken is Token2.Operator &&
          // NOT after factorial, percentage and similar tokens
          (!previousToken.isUnary &&
            previousToken.associativity != Token2.Operator.Associativity.LEFT))
    ) {
      tokens[index] = Token2.UnaryMinus
    }
  }

  return tokens.repairLexicon()
}

private fun findTokenAhead(input: String, cursor: Int): Token2? {
  for (token in Token2.parseableTokens) {
    // look in front for possible match with current token
    val lookUpString =
      input.substring(cursor, (cursor + token.symbol.length).coerceAtMost(input.length))
    val isMatched = token.symbol == lookUpString
    if (isMatched) return token
  }
  return null
}

private fun findNumberTokenAhead(input: String, cursor: Int): Token2 {
  // walk left while
  val number = input.substring(cursor).takeWhile { char -> char.isDigitOrDot() }
  if (number.count { it == '.' } > 1) throw TokenizerException.TooManyFractionSymbols()
  return Token2.Number(number)
}

private fun Char.isDigitOrDot(): Boolean = isDigit() || this == '.'

private fun MutableList<Token2>.repairLexicon(): List<Token2> {
  return this.missingClosingBrackets()
    .unpackNotation()
    .missingMultiply()
    .unpackAllPercents()
    // input like 80%80% should be treated as 80%*80%.
    // After unpacking we get (80/100)(80/100), the multiply is missing (!!!)
    // No, we can't unpack before fixing missing multiply.
    // Ideally we need to add missing multiply for 80%80%
    // In that case unpackAllPercents gets input with all operators 80%*80% in this case
    // Can't be done right now since missingMultiply checks for tokens in front only
    .missingMultiply()
}

private fun MutableList<Token2>.missingClosingBrackets(): MutableList<Token2> {
  val leftBracket = this.count { it == Token2.LeftBracket }
  val rightBrackets = this.count { it == Token2.RightBracket }
  val neededBrackets = leftBracket - rightBrackets

  if (neededBrackets <= 0) return this

  repeat(neededBrackets) { this.add(Token2.RightBracket) }
  return this
}

private fun MutableList<Token2>.missingMultiply(): MutableList<Token2> {
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
    val isDigit1 = currentToken is Token2.Number
    val isConst1 = currentToken is Token2.Const
    val isRightBracket1 = currentToken == Token2.RightBracket
    val isFactorial1 = currentToken == Token2.Factorial
    val canBeFollowedByMultiplyToken = isDigit1 || isConst1 || isRightBracket1 || isFactorial1
    if (canBeFollowedByMultiplyToken) {
      // Peek next, but then go back
      val nextToken = iterator.next()
      // basically doing this: "12|" -> "1|2"
      iterator.previous()

      // needs a multiply symbol between tokens if one of these
      val isLeftBracket2 = nextToken == Token2.LeftBracket
      val isFunction2 = nextToken is Token2.Func
      val isConst2 = nextToken is Token2.Const
      val isSqrt2 = nextToken == Token2.Sqrt
      val isDigit2 = nextToken is Token2.Number

      val canHaveMultiplyBefore = isLeftBracket2 || isFunction2 || isConst2 || isSqrt2 || isDigit2
      if (canHaveMultiplyBefore) {
        iterator.add(Token2.Multiply)
      }
    }
  }

  return this
}

private fun MutableList<Token2>.unpackNotation(): MutableList<Token2> {
  // Transform 1E+7 ==> 1*10^7
  // Transform 1E-7 ==> 1/10^7
  val iterator = this.listIterator()

  while (iterator.hasNext()) {
    if (iterator.next() == Token2.EngineeringE) {
      iterator.remove()

      val tokenAfterE =
        try {
          iterator.next()
        } catch (_: Exception) {
          throw TokenizerException.BadScientificNotation()
        }

      iterator.remove()

      when (tokenAfterE) {
        Token2.Minus -> iterator.add(Token2.Divide)
        Token2.Plus -> iterator.add(Token2.Multiply)
        else -> throw TokenizerException.BadScientificNotation()
      }

      iterator.add(Token2.Number("10"))
      iterator.add(Token2.Power)
    }
  }

  return this
}

private fun MutableList<Token2>.unpackAllPercents(): MutableList<Token2> {
  var result = this

  var percentIndex = result.indexOf(Token2.Percent)
  while (percentIndex != -1) {
    result = result.unpackPercentAt(percentIndex)
    percentIndex = result.indexOf(Token2.Percent)
  }
  return result
}

/**
 * Unpack unethical percentages. See tests for examples. This methods wraps expressions in more
 * brackets for safer calculation.
 */
private fun MutableList<Token2>.unpackPercentAt(percentIndex: Int): MutableList<Token2> {
  var cursor = percentIndex

  // get whatever is the percentage
  // can be number or expression in brackets
  // -1 to check what is before percentage token
  val expressionTokensBefore = this.getExpressionBefore(percentIndex - 1)
  // Move cursor
  // 123+345%| -> 123+|345%
  cursor -= expressionTokensBefore.size

  // get the operator in front
  // 123+|345% -> 123|+345%
  cursor -= 1
  val operator = this.getOrNull(cursor)

  // Don't go further. Percentage doesn't follow anything. Fallback: wrap expression with division
  // by 100: 123% -> (123/100)
  if ((operator == null) or (operator !in listOf(Token2.Plus, Token2.Minus))) {
    val mutList = this.toMutableList()

    // Remove percentage
    // 123% -> 123
    mutList.removeAt(percentIndex)

    // Add opening bracket before percentage
    // 123 -> (123
    mutList.add(percentIndex - expressionTokensBefore.size, Token2.LeftBracket)

    // Add "/100)" and closing bracket
    // (123 -> (123/100)
    mutList.addAll(
      percentIndex + 1,
      listOf(Token2.Divide, Token2.Number("100"), Token2.RightBracket),
    )

    return mutList
  }

  // move cursor to jump over operator
  // 123+|345% -> 123|+345%
  cursor -= 1
  // Get the base
  val base = this.getExpressionBefore(cursor)
  val mutList = this.toMutableList()

  // Remove percentage
  mutList.removeAt(percentIndex)

  // Add opening bracket before percentage
  mutList.add(percentIndex - expressionTokensBefore.size, Token2.LeftBracket)

  // Add "/ 100" and other stuff
  mutList.addAll(
    percentIndex + 1,
    listOf(
      Token2.Divide,
      Token2.Number("100"),
      Token2.Multiply,
      Token2.LeftBracket,
      *base.toTypedArray(),
      Token2.RightBracket,
      Token2.RightBracket,
    ),
  )

  return mutList
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
internal fun List<Token2>.getExpressionBefore(pos: Int): List<Token2> {
  val tokenInFront = this[pos]

  // Just number
  if (tokenInFront is Token2.Number) return listOf(tokenInFront)

  // For cases like "100+(2+5)|%" or "2!|+5". The check above won't pass, so the next expected thing
  // is
  // a number or expression in brackets. Anything else is not expected.
  val validTokenInFront = tokenInFront == Token2.RightBracket || tokenInFront == Token2.Factorial
  if (!validTokenInFront) throw TokenizerException.FailedToUnpackNumber()

  var cursor = pos
  var leftBrackets = 0
  var rightBrackets = 0

  // Start walking left until we get balanced brackets
  while (cursor >= 0) {
    val currentToken = this[cursor]
    if (currentToken == Token2.LeftBracket) leftBrackets++
    if (currentToken == Token2.RightBracket) rightBrackets++
    if (leftBrackets == rightBrackets && currentToken != Token2.Factorial) break
    cursor--
  }

  // +1 because subList end is exclusive
  return this.subList(cursor.coerceAtLeast(0), pos + 1)
}

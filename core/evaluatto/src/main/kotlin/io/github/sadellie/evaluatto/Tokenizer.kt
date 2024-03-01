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

package io.github.sadellie.evaluatto

import com.sadellie.unitto.core.common.Token

internal sealed class TokenizerException(message: String) : Exception(message) {

  class TooManyFractionSymbols : TokenizerException("Number has multiple commas in it")

  class FailedToUnpackNumber : TokenizerException("Unexpected token before percentage")

  class BadScientificNotation : TokenizerException("Expected plus or minus symbol after \"E\"")
}

internal fun String.tokenize(): List<String> {
  var cursor = 0
  val tokens: MutableList<String> = mutableListOf()

  while (cursor != this.length) {
    val nextToken = peekTokenAfter(this, cursor)

    if (nextToken != null) {
      tokens.add(nextToken)
      cursor += nextToken.length
    } else {
      // Didn't find any token, move left slowly (by 1 symbol)
      cursor++
    }
  }

  return tokens.repairLexicon()
}

private fun peekTokenAfter(streamOfTokens: String, cursor: Int): String? {
  Token.expressionTokens.forEach { token ->
    val subs =
      streamOfTokens.substring(cursor, (cursor + token.length).coerceAtMost(streamOfTokens.length))
    if (subs == token) {
      // Got a digit, see if there are other digits coming after
      if (token in Token.Digit.allWithDot) {
        val number =
          streamOfTokens.substring(cursor).takeWhile {
            Token.Digit.allWithDot.contains(it.toString())
          }

        if (number.count { it.toString() == Token.Digit.DOT } > 1) {
          throw TokenizerException.TooManyFractionSymbols()
        }

        return number
      }
      return token
    }
  }
  return null
}

private fun MutableList<String>.repairLexicon(): List<String> {
  return this.missingClosingBrackets()
    .unpackNotation()
    .missingMultiply()
    .unpackAllPercents()
    // input like 80%80% should be treated as 80%*80%.
    // After unpacking we get (80/100)(80/100), the multiply is missing (!!!)
    // No, we can't unpack before fixing missing multiply.
    // Ideally we we need to add missing multiply for 80%80%
    // In that case unpackAllPercents gets input with all operators 80%*80% in this case
    // Can't be done right now since missingMultiply checks for tokens in front only
    .missingMultiply()
}

private fun MutableList<String>.missingClosingBrackets(): MutableList<String> {
  val leftBracket = this.count { it == Token.Operator.LEFT_BRACKET }
  val rightBrackets = this.count { it == Token.Operator.RIGHT_BRACKET }
  val neededBrackets = leftBracket - rightBrackets

  if (neededBrackets <= 0) return this

  repeat(neededBrackets) { this.add(Token.Operator.RIGHT_BRACKET) }
  return this
}

private fun MutableList<String>.missingMultiply(): MutableList<String> {
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
    val isDigit1 = currentToken.isDigitToken()
    val isConst1 = currentToken in Token.Const.all
    val isRightBracket1 = currentToken == Token.Operator.RIGHT_BRACKET
    val isFactorial1 = currentToken == Token.Operator.FACTORIAL
    val canBeFollowedByMultiplyToken = isDigit1 || isConst1 || isRightBracket1 || isFactorial1
    if (canBeFollowedByMultiplyToken) {
      // Peek next, but then go back
      val nextToken = iterator.next()
      // basically doing this: "12|" -> "1|2"
      iterator.previous()

      // needs a multiply symbol between tokens if one of these
      val isLeftBracket2 = nextToken == Token.Operator.LEFT_BRACKET
      val isFunction2 = nextToken in Token.Func.all
      val isConst2 = nextToken in Token.Const.all
      val isSqrt2 = nextToken == Token.Operator.SQRT
      val isDigit2 = nextToken.isDigitToken()

      val canHaveMultiplyBefore = isLeftBracket2 || isFunction2 || isConst2 || isSqrt2 || isDigit2
      if (canHaveMultiplyBefore) {
        iterator.add(Token.Operator.MULTIPLY)
      }
    }
  }

  return this
}

private fun MutableList<String>.unpackNotation(): MutableList<String> {
  // Transform 1E+7 ==> 1*10^7
  // Transform 1E-7 ==> 1/10^7
  val iterator = this.listIterator()

  while (iterator.hasNext()) {
    if (iterator.next() == Token.DisplayOnly.ENGINEERING_E) {
      iterator.remove()

      val tokenAfterE =
        try {
          iterator.next()
        } catch (e: Exception) {
          throw TokenizerException.BadScientificNotation()
        }

      iterator.remove()

      when (tokenAfterE) {
        Token.Operator.MINUS -> iterator.add(Token.Operator.DIVIDE)
        Token.Operator.PLUS -> iterator.add(Token.Operator.MULTIPLY)
        else -> throw TokenizerException.BadScientificNotation()
      }

      iterator.add("10")
      iterator.add(Token.Operator.POWER)
    }
  }

  return this
}

private fun MutableList<String>.unpackAllPercents(): MutableList<String> {
  var result = this

  var percentIndex = result.indexOf(Token.Operator.PERCENT)
  while (percentIndex != -1) {
    result = result.unpackPercentAt(percentIndex)
    percentIndex = result.indexOf(Token.Operator.PERCENT)
  }
  return result
}

/**
 * Unpack unethical percentages. See tests for examples. This methods wraps expressions in more
 * brackets for safer calculation.
 */
private fun MutableList<String>.unpackPercentAt(percentIndex: Int): MutableList<String> {
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
  if ((operator == null) or (operator !in listOf(Token.Operator.PLUS, Token.Operator.MINUS))) {
    val mutList = this.toMutableList()

    // Remove percentage
    // 123% -> 123
    mutList.removeAt(percentIndex)

    // Add opening bracket before percentage
    // 123 -> (123
    mutList.add(percentIndex - expressionTokensBefore.size, Token.Operator.LEFT_BRACKET)

    // Add "/100)" and closing bracket
    // (123 -> (123/100)
    mutList.addAll(
      percentIndex + 1,
      listOf(Token.Operator.DIVIDE, "100", Token.Operator.RIGHT_BRACKET),
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
  mutList.add(percentIndex - expressionTokensBefore.size, Token.Operator.LEFT_BRACKET)

  // Add "/ 100" and other stuff
  mutList.addAll(
    percentIndex + 1,
    listOf(
      Token.Operator.DIVIDE,
      "100",
      Token.Operator.MULTIPLY,
      Token.Operator.LEFT_BRACKET,
      *base.toTypedArray(),
      Token.Operator.RIGHT_BRACKET,
      Token.Operator.RIGHT_BRACKET,
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
internal fun List<String>.getExpressionBefore(pos: Int): List<String> {
  val tokenInFront = this[pos]

  // Just number
  if (tokenInFront.isDigitToken()) return listOf(tokenInFront)

  // For cases like "100+(2+5)|%" or "2!|+5". The check above won't pass, so the next expected thing
  // is
  // a number or expression in brackets. Anything else is not expected.
  val validTokenInFront =
    tokenInFront == Token.Operator.RIGHT_BRACKET || tokenInFront == Token.Operator.FACTORIAL
  if (!validTokenInFront) throw TokenizerException.FailedToUnpackNumber()

  var cursor = pos
  var leftBrackets = 0
  var rightBrackets = 0

  // Start walking left until we get balanced brackets
  while (cursor >= 0) {
    val currentToken = this[cursor]
    if (currentToken == Token.Operator.LEFT_BRACKET) leftBrackets++
    if (currentToken == Token.Operator.RIGHT_BRACKET) rightBrackets++
    if (leftBrackets == rightBrackets && currentToken != Token.Operator.FACTORIAL) break
    cursor--
  }

  // +1 because subList end is exclusive
  return this.subList(cursor.coerceAtLeast(0), pos + 1)
}

/**
 * Digit tokens can be found by checking first symbol.
 * - 123456 is a digit token
 * - 123.456 is a digit token
 * - .456 is a digit token
 */
private fun String.isDigitToken(): Boolean = first().toString() in Token.Digit.allWithDot

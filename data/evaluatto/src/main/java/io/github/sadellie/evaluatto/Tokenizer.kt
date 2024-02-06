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

import com.sadellie.unitto.core.base.Token

sealed class TokenizerException(message: String) : Exception(message) {
    class TooManyFractionSymbols : TokenizerException("Number has multiple commas in it")
    class FailedToUnpackNumber : TokenizerException("Unexpected token before percentage")
    class BadScientificNotation : TokenizerException("Expected plus or minus symbol after \"E\"")
}

fun String.tokenize(): List<String> {
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

private fun peekTokenAfter(
    streamOfTokens: String,
    cursor: Int
): String? {
    Token.expressionTokens.forEach { token ->
        val subs = streamOfTokens
            .substring(
                cursor,
                (cursor + token.length).coerceAtMost(streamOfTokens.length)
            )
        if (subs == token) {
            // Got a digit, see if there are other digits coming after
            if (token in Token.Digit.allWithDot) {
                val number = streamOfTokens
                    .substring(cursor)
                    .takeWhile { Token.Digit.allWithDot.contains(it.toString()) }

                if (number.count { it.toString() == Token.Digit.dot } > 1) {
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
    return this
        .missingClosingBrackets()
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
    val leftBracket = this.count { it == Token.Operator.leftBracket }
    val rightBrackets = this.count { it == Token.Operator.rightBracket }
    val neededBrackets = leftBracket - rightBrackets

    if (neededBrackets <= 0) return this

    repeat(neededBrackets) {
        this.add(Token.Operator.rightBracket)
    }
    return this
}

private fun MutableList<String>.missingMultiply(): MutableList<String> {
    val iterator = this.listIterator()

    while (iterator.hasNext()) {
        val currentToken = iterator.next()

        // Need two token for checks
        if (!iterator.hasNext()) break

        val isDigit = currentToken.isDigitToken()
        val isConst = currentToken in Token.Const.all
        val isRightBracket = currentToken == Token.Operator.rightBracket

        // may need a multiplication after
        if (isDigit || isConst || isRightBracket) {
            // Peek next, but then go back
            val tokenAfter = iterator.next()
            iterator.previous()

            if (tokenAfter == Token.Operator.leftBracket ||
                tokenAfter in Token.Func.all ||
                tokenAfter in Token.Const.all ||
                tokenAfter == Token.Operator.sqrt ||
                tokenAfter.isDigitToken()) {

                iterator.add(Token.Operator.multiply)
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
        if (iterator.next() == Token.DisplayOnly.engineeringE) {
            iterator.remove()

            val tokenAfterE = try {
                iterator.next()
            } catch (e: Exception) {
                throw TokenizerException.BadScientificNotation()
            }

            iterator.remove()

            when (tokenAfterE) {
                Token.Operator.minus -> iterator.add(Token.Operator.divide)
                Token.Operator.plus -> iterator.add(Token.Operator.multiply)
                else -> throw TokenizerException.BadScientificNotation()
            }

            iterator.add("10")
            iterator.add(Token.Operator.power)
        }
    }

    return this
}

private fun MutableList<String>.unpackAllPercents(): MutableList<String> {
    var result = this
    while (result.contains(Token.Operator.percent)) {
        val percIndex = result.indexOf(Token.Operator.percent)
        result = result.unpackPercentAt(percIndex)
    }
    return result
}

private fun MutableList<String>.unpackPercentAt(percentIndex: Int): MutableList<String> {
    var cursor = percentIndex

    // get whatever is the percentage
    val percentage = this.getNumberOrExpressionBefore(percentIndex)
    // Move cursor
    cursor -= percentage.size

    // get the operator in front
    cursor -= 1
    val operator = this.getOrNull(cursor)

    // Don't go further
    if ((operator == null) or (operator !in listOf(Token.Operator.plus, Token.Operator.minus))) {
        val mutList = this.toMutableList()

        // Remove percentage
        mutList.removeAt(percentIndex)

        //Add opening bracket before percentage
        mutList.add(percentIndex - percentage.size, Token.Operator.leftBracket)

        // Add "/ 100" and closing bracket
        mutList.addAll(percentIndex + 1, listOf(Token.Operator.divide, "100", Token.Operator.rightBracket))

        return mutList
    }
    // Get the base
    val base = this.getBaseBefore(cursor)
    val mutList = this.toMutableList()

    // Remove percentage
    mutList.removeAt(percentIndex)

    //Add opening bracket before percentage
    mutList.add(percentIndex - percentage.size, Token.Operator.leftBracket)

    // Add "/ 100" and other stuff
    mutList.addAll(
        percentIndex + 1,
        listOf(
            Token.Operator.divide,
            "100",
            Token.Operator.multiply,
            Token.Operator.leftBracket,
            *base.toTypedArray(),
            Token.Operator.rightBracket,
            Token.Operator.rightBracket
        )
    )

    return mutList
}

private fun MutableList<String>.getNumberOrExpressionBefore(pos: Int): List<String> {
    val digits = Token.Digit.allWithDot.map { it[0] }

    val tokenInFront = this[pos - 1]

    // Just number
    if (tokenInFront.all { it in digits }) return listOf(tokenInFront)

    // For cases like "100+(2+5)|%". The check above won't pass, so the next expected thing is
    // a number in brackets. Anything else is not expected.
    if (tokenInFront != Token.Operator.rightBracket) throw TokenizerException.FailedToUnpackNumber()

    // Start walking left until we get balanced brackets
    var cursor = pos - 1
    var leftBrackets = 0
    var rightBrackets = 1 // We set 1 because we start with closing bracket

    while (leftBrackets != rightBrackets) {
        cursor--
        val currentToken = this[cursor]
        if (currentToken == Token.Operator.leftBracket) leftBrackets++
        if (currentToken == Token.Operator.rightBracket) rightBrackets++
    }

    return this.subList(cursor, pos)
}

private fun List<String>.getBaseBefore(pos: Int): List<String> {
    var cursor = pos
    var leftBrackets = 0
    var rightBrackets = 0

    while ((--cursor >= 0)) {
        val currentToken = this[cursor]

        if (currentToken == Token.Operator.leftBracket) leftBrackets++
        if (currentToken == Token.Operator.rightBracket) rightBrackets++

        if (leftBrackets > rightBrackets) break
    }

    // Return cursor back to last token
    cursor += 1

    return this.subList(cursor, pos)
}

private fun String.isDigitToken(): Boolean = first().toString() in Token.Digit.allWithDot

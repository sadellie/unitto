/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.core.ui.common.textfield

import android.content.Context
import com.sadellie.unitto.core.base.R
import com.sadellie.unitto.core.base.Token
import java.math.BigDecimal
import java.math.RoundingMode

private val numbersRegex by lazy { Regex("[\\d.]+") }

private val timeDivisions by lazy {
    mapOf(
        R.string.day_short to BigDecimal("86400000000000000000000"),
        R.string.hour_short to BigDecimal("3600000000000000000000"),
        R.string.minute_short to BigDecimal("60000000000000000000"),
        R.string.second_short to BigDecimal("1000000000000000000"),
        R.string.millisecond_short to BigDecimal("1000000000000000"),
        R.string.microsecond_short to BigDecimal("1000000000000"),
        R.string.nanosecond_short to BigDecimal("1000000000"),
        R.string.attosecond_short to BigDecimal("1"),
    )
}

internal fun String.clearAndFilterExpression(formatterSymbols: FormatterSymbols): String {
    var clean = this
        .replace(formatterSymbols.grouping, "")
        .replace(formatterSymbols.fractional, Token.Digit.dot)
        .replace(" ", "")

    Token.sexyToUgly.forEach { (token, ugliness) ->
        ugliness.forEach {
            clean = clean.replace(it, token)
        }
    }

    return clean.cleanIt(Token.expressionTokens)
}

internal fun String.clearAndFilterNumberBase(): String {
    return uppercase().cleanIt(Token.numberBaseTokens)
}

/**
 * Format string time conversion result into a more readable format.
 *
 * @param basicUnit Basic unit of the unit we convert to
 * @return String like "1d 12h 12s".
 */
fun String.formatTime(
    context: Context,
    basicUnit: BigDecimal?,
    formatterSymbols: FormatterSymbols
): String {
    // We get ugly version of input (non-fancy minus)
    val input = this

    if (basicUnit == null) return Token.Digit._0

    try {
        // Don't need magic if the input is zero
        if (BigDecimal(input).compareTo(BigDecimal.ZERO) == 0) return Token.Digit._0
    } catch (e: NumberFormatException) {
        // For case such as "10-" and "("
        return Token.Digit._0
    }
    // Attoseconds don't need "magic"
    if (basicUnit.compareTo(BigDecimal.ONE) == 0) return input.formatExpression(formatterSymbols)

    var result = if (input.startsWith("-")) Token.Operator.minus else ""
    var remainingSeconds = BigDecimal(input)
        .abs()
        .multiply(basicUnit)
        .setScale(0, RoundingMode.HALF_EVEN)

    if (remainingSeconds.compareTo(BigDecimal.ZERO) == 0) return Token.Digit._0

    timeDivisions.forEach { (timeStr, divider) ->
        val division = remainingSeconds.divideAndRemainder(divider)
        val time = division.component1()
        remainingSeconds = division.component2()
        if (time.compareTo(BigDecimal.ZERO) != 0) {
            result += "${time.toPlainString().formatExpression(formatterSymbols)}${context.getString(timeStr)} "
        }
    }
    return result.trimEnd()
}

fun String.formatExpression(
    formatterSymbols: FormatterSymbols
): String {
    var input = this
    // Don't do anything to engineering string.
    if (input.contains(Token.DisplayOnly.engineeringE)) {
        return input.replace(Token.Digit.dot, formatterSymbols.fractional)
    }

    numbersRegex
        .findAll(input)
        .map(MatchResult::value)
        .forEach {
            input = input.replace(it, it.formatNumber(formatterSymbols))
        }

    Token.sexyToUgly.forEach { (token, ugliness) ->
        ugliness.forEach { uglySymbol ->
            input = input.replace(uglySymbol, token)
        }
    }

    return input
}

private fun String.formatNumber(
    formatterSymbols: FormatterSymbols
): String {
    val input = this

    if (input.any { it.isLetter() }) return input

    var firstPart = input.takeWhile { it != '.' }
    val remainingPart = input.removePrefix(firstPart)

    // Number of empty symbols (spaces) we need to add to correctly split into chunks.
    val offset = 3 - firstPart.length.mod(3)
    val output = if (offset != 3) {
        // We add some spaces at the beginning so that last chunk has 3 symbols
        firstPart = " ".repeat(offset) + firstPart
        firstPart.chunked(3).joinToString(formatterSymbols.grouping).drop(offset)
    } else {
        firstPart.chunked(3).joinToString(formatterSymbols.grouping)
    }

    return output.plus(remainingPart.replace(".", formatterSymbols.fractional))
}

private fun String.cleanIt(legalTokens: List<String>): String {
    val streamOfTokens = this

    fun peekTokenAfter(cursor: Int): String? {
        legalTokens.forEach { token ->
            val subs = streamOfTokens
                .substring(
                    cursor,
                    (cursor + token.length).coerceAtMost(streamOfTokens.length)
                )
            if (subs == token) {
                // Got a digit, see if there are other digits coming after
                if (token in Token.Digit.allWithDot) {
                    return streamOfTokens
                        .substring(cursor)
                        .takeWhile { Token.Digit.allWithDot.contains(it.toString()) }
                }
                return token
            }
        }
        return null
    }

    var cursor = 0
    var tokens = ""

    while (cursor != streamOfTokens.length) {
        val nextToken = peekTokenAfter(cursor)

        if (nextToken != null) {
            tokens += nextToken
            cursor += nextToken.length
        } else {
            // Didn't find any token, move left slowly (by 1 symbol)
            cursor++
        }
    }

    return tokens
}

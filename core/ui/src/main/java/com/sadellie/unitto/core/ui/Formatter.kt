/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

package com.sadellie.unitto.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sadellie.unitto.core.base.INTERNAL_DISPLAY
import com.sadellie.unitto.core.base.KEY_0
import com.sadellie.unitto.core.base.KEY_COMMA
import com.sadellie.unitto.core.base.KEY_DOT
import com.sadellie.unitto.core.base.KEY_E
import com.sadellie.unitto.core.base.KEY_MINUS
import com.sadellie.unitto.core.base.Separator
import java.math.BigDecimal
import java.math.RoundingMode

// Legacy, LOL. Will change later
object Formatter : UnittoFormatter()

open class UnittoFormatter {
    /**
     * This regex will catch things like "123.456", "123", ".456"
     */
    private val numbersRegex = Regex("[\\d.]+")

    private val SPACE = " "
    private val PERIOD = "."
    private val COMMA = ","

    /**
     * Grouping separator.
     */
    var grouping: String = SPACE

    /**
     * Fractional part separator.
     */
    var fractional = KEY_COMMA

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

    /**
     * Change current separator to another [separator].
     *
     * @see [Separator]
     */
    fun setSeparator(separator: Int) {
        grouping = when (separator) {
            Separator.PERIOD -> PERIOD
            Separator.COMMA -> COMMA
            else -> SPACE
        }
        fractional = if (separator == Separator.PERIOD) KEY_COMMA else KEY_DOT
    }

    /**
     * Format [input].
     *
     * This will replace operators to their more appealing variants: divide, multiply and minus.
     * Plus operator remains unchanged.
     *
     * Numbers will also be formatted.
     *
     * @see [formatNumber]
     */
    fun format(input: String): String {
        // Don't do anything to engineering string.
        if (input.contains(KEY_E)) return input.replace(KEY_DOT, fractional)

        var output = input

        // We may receive expressions. Find all numbers in this expression
        val allNumbers: List<String> = input.getOnlyNumbers()

        allNumbers.forEach {
            output = output.replace(it, formatNumber(it))
        }

        INTERNAL_DISPLAY.forEach {
            output = output.replace(it.key, it.value)
        }

        return output
    }

    /**
     * Remove formatting. Reverses [format]
     */
    fun reFormat(input: String): String {
        // We get  123.45,6789
        // We need 12.345,6789

        // 123.45,6789
        // Remove grouping
        // 12345,6789
        // Replace fractional with "." because formatter accepts only numbers where fractional is a dot

        val cleanString = input
            .replace(grouping, "")
            .replace(fractional, KEY_DOT)
        return format(cleanString)
    }

    /**
     * Format given [input].
     *
     * Input must be a number with dot!!!. Will replace grouping separators and fractional part (dot)
     * separators.
     *
     * @see grouping
     * @see fractional
     */
    private fun formatNumber(input: String): String {
        if (input.any { it.isLetter() }) return input

        var firstPart = input.takeWhile { it != '.' }
        val remainingPart = input.removePrefix(firstPart)

        // Number of empty symbols (spaces) we need to add to correctly split into chunks.
        val offset = 3 - firstPart.length.mod(3)
        val output = if (offset != 3) {
            // We add some spaces at the beginning so that last chunk has 3 symbols
            firstPart = " ".repeat(offset) + firstPart
            firstPart.chunked(3).joinToString(grouping).drop(offset)
        } else {
            firstPart.chunked(3).joinToString(grouping)
        }

        return (output + remainingPart.replace(".", fractional))
    }

    /**
     * Takes [input] and [basicUnit] of the  unit to format it to be more human readable.
     *
     * @return String like "1d 12h 12s".
     */
    @Composable
    fun formatTime(input: String, basicUnit: BigDecimal?): String {
        if (basicUnit == null) return KEY_0

        try {
            // Don't need magic if the input is zero
            if (BigDecimal(input).compareTo(BigDecimal.ZERO) == 0) return KEY_0
        } catch (e: NumberFormatException) {
            // For case such as "10-" and "("
            return KEY_0
        }
        // Attoseconds don't need "magic"
        if (basicUnit.compareTo(BigDecimal.ONE) == 0) return formatNumber(input)

        var result = if (input.startsWith(KEY_MINUS)) KEY_MINUS else ""
        var remainingSeconds = BigDecimal(input)
            .abs()
            .multiply(basicUnit)
            .setScale(0, RoundingMode.HALF_EVEN)

        if (remainingSeconds.compareTo(BigDecimal.ZERO) == 0) return KEY_0

        timeDivisions.forEach { (timeStr, divider) ->
            val division = remainingSeconds.divideAndRemainder(divider)
            val time = division.component1()
            remainingSeconds = division.component2()
            if (time.compareTo(BigDecimal.ZERO) == 1) {
                result += "${formatNumber(time.toPlainString())}${stringResource(timeStr)} "
            }
        }
        return result.trimEnd()
    }

    /**
     * @receiver Must be a string with a dot (".") used as a fractional.
     */
    private fun String.getOnlyNumbers(): List<String> =
        numbersRegex.findAll(this).map(MatchResult::value).toList()
}

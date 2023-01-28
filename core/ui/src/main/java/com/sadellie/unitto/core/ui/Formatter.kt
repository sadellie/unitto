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
import com.sadellie.unitto.core.base.KEY_LEFT_BRACKET
import com.sadellie.unitto.core.base.KEY_MINUS
import com.sadellie.unitto.core.base.KEY_RIGHT_BRACKET
import com.sadellie.unitto.core.base.OPERATORS
import com.sadellie.unitto.core.base.Separator
import java.math.BigDecimal
import java.math.RoundingMode

object Formatter {
    private const val SPACE = " "
    private const val PERIOD = "."
    private const val COMMA = ","

    /**
     * Grouping separator.
     */
    private var grouping: String = SPACE

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

        // We may receive expressions
        // Find all numbers in that expression
        val allNumbers: List<String> = input.split(
            *OPERATORS.toTypedArray(), KEY_LEFT_BRACKET, KEY_RIGHT_BRACKET
        )

        allNumbers.forEach {
            output = output.replace(it, formatNumber(it))
        }

        INTERNAL_DISPLAY.forEach {
            output = output.replace(it.key, it.value)
        }

        return output
    }

    /**
     * Format given [input].
     *
     * Input must be a number. Will replace grouping separators and fractional part separators.
     *
     * @see grouping
     * @see fractional
     */
    private fun formatNumber(input: String): String {
        val splitInput = input.split(".")
        var firstPart = splitInput[0]

        // Number of empty symbols (spaces) we need to add to correctly  split into chunks.
        val offset = 3 - firstPart.length.mod(3)
        var output = if (offset != 3) {
            // We add some spaces at the begging so that last chunk has 3 symbols
            firstPart = " ".repeat(offset) + firstPart
            firstPart.chunked(3).joinToString(grouping).drop(offset)
        } else {
            firstPart.chunked(3).joinToString(grouping)
        }

        // Handling fractional part
        if (input.contains(".")) {
            output = output + fractional + splitInput.getOrElse(1) { "" }
        }

        return output
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
}

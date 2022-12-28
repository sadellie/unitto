/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022 Elshan Agaev
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

package com.sadellie.unitto.screens

import com.sadellie.unitto.data.KEY_COMMA
import com.sadellie.unitto.data.KEY_DOT
import com.sadellie.unitto.data.KEY_E
import com.sadellie.unitto.data.KEY_LEFT_BRACKET
import com.sadellie.unitto.data.KEY_RIGHT_BRACKET
import com.sadellie.unitto.data.OPERATORS
import com.sadellie.unitto.data.preferences.Separator

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
            firstPart.chunked(3).joinToString(this.grouping).drop(offset)
        } else {
            firstPart.chunked(3).joinToString(this.grouping)
        }

        // Handling fractional part
        if (input.contains(".")) {
            output = output + fractional + splitInput.getOrElse(1) { "" }
        }

        return output
    }
}

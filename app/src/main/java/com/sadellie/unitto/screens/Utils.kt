/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.sadellie.unitto.data.KEY_COMMA
import com.sadellie.unitto.data.KEY_DOT
import com.sadellie.unitto.data.KEY_E
import com.sadellie.unitto.data.KEY_LEFT_BRACKET
import com.sadellie.unitto.data.KEY_RIGHT_BRACKET
import com.sadellie.unitto.data.OPERATORS
import com.sadellie.unitto.data.preferences.OutputFormat
import com.sadellie.unitto.data.preferences.Separator
import com.sadellie.unitto.data.units.AbstractUnit
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.max

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

/**
 * Shorthand function to use correct `toString` method according to [outputFormat].
 */
fun BigDecimal.toStringWith(outputFormat: Int): String {
    // Setting result value using a specified OutputFormat
    return when (outputFormat) {
        OutputFormat.ALLOW_ENGINEERING -> this.toString()
        OutputFormat.FORCE_ENGINEERING -> this.toEngineeringString()
        else -> this.toPlainString()
    }
}

/**
 * Sets the minimum scale that is required to get first non zero value in fractional part
 *
 * @param[prefScale] Is the preferred scale, the one which will be compared against
 */
fun BigDecimal.setMinimumRequiredScale(prefScale: Int): BigDecimal {
    /**
     * Here we are getting the amount of zeros in fractional part before non zero value
     * For example, for 0.00000123456 we need the length of 00000
     * Next we add one to get the position of the first non zero value
     * Also, this block is only for VERY small numbers
     */
    return this.setScale(
        max(
            prefScale,
            if (this.abs() < BigDecimal.ONE) {
                // https://stackoverflow.com/a/46136593
                -floor(log10(this.abs().remainder(BigDecimal.ONE).toDouble())).toInt()
            } else {
                0
            }
        ),
        RoundingMode.HALF_EVEN
    )
}

/**
 * Open given link in browser
 */
fun openLink(mContext: Context, url: String) {
    mContext.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
}

/**
 * Compute Levenshtein Distance between this string and [secondString]. Doesn't matter which string is
 * first.
 *
 * @return The amount of changes that are needed to transform one string into another
 */
fun String.lev(secondString: String): Int {
    val stringA = this.lowercase()
    val stringB = secondString.lowercase()

    // Skipping computation for this cases
    if (stringA == stringB) return 0
    if (stringA.isEmpty()) return stringB.length
    // This case is basically unreal in this app, because secondString is a unit name and they are never empty
    if (stringB.isEmpty()) return stringA.length

    var cost = IntArray(stringA.length + 1) { it }
    var newCost = IntArray(stringA.length + 1)

    for (i in 1..stringB.length) {
        // basically shifting this to the right by 1 each time
        newCost[0] = i

        for (j in 1..stringA.length) {
            newCost[j] = minOf(
                // Adding 1 if they don't match, i.e. need to replace
                cost[j - 1] + if (stringA[j - 1] == stringB[i - 1]) 0 else 1,
                // Insert
                cost[j] + 1,
                // Delete
                newCost[j - 1] + 1
            )
        }

        // Swapping costs
        cost = newCost.also { newCost = cost }
    }

    return cost[this.length]
}

/**
 * Sorts sequence of units by Levenshtein distance
 *
 * @param stringA String for Levenshtein distance
 * @return Sorted sequence of units. Units with lower Levenshtein distance are higher
 */
fun Sequence<AbstractUnit>.sortByLev(stringA: String): Sequence<AbstractUnit> {
    val stringToCompare = stringA.lowercase()
    // We don't need units where name is too different, half of the symbols is wrong in this situation
    val threshold: Int = stringToCompare.length / 2
    // List of pair: Unit and it's levDist
    val unitsWithDist = mutableListOf<Pair<AbstractUnit, Int>>()
    this.forEach { unit ->
        val unitName: String = unit.renderedName.lowercase()

        /**
         * There is chance that unit name doesn't need any edits (contains part of the query)
         * So computing levDist is a waste of resources
         */
        when {
            // It's the best possible match if it start with
            unitName.startsWith(stringToCompare) -> {
                unitsWithDist.add(Pair(unit, 0))
                return@forEach
            }
            // It's a little bit worse when it just contains part of the query
            unitName.contains(stringToCompare) -> {
                unitsWithDist.add(Pair(unit, 1))
                return@forEach
            }
        }

        /**
         * Levenshtein Distance for this specific name of this unit
         *
         * We use substring so that we compare not the whole unit name, but only part of it.
         * It's required because without it levDist will be too high for units with longer
         * names than the search query
         *
         * For example:
         * Search query is 'Kelometer' and unit name is 'Kilometer per hour'
         * Without substring levDist will be 9 which means that this unit will be skipped
         *
         * With substring levDist will be 3 so unit will be included
         */
        val levDist = unitName
            .substring(0, minOf(stringToCompare.length, unitName.length))
            .lev(stringToCompare)

        // Threshold
        if (levDist < threshold) {
            unitsWithDist.add(Pair(unit, levDist))
        }
    }
    // Sorting by levDist and getting units
    return unitsWithDist
        .sortedBy { it.second }
        .map { it.first }
        .asSequence()
}

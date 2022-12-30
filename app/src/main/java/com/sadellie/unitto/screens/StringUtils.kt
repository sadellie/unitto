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

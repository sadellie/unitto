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

package com.sadellie.unitto.data.units

import androidx.annotation.StringRes
import com.sadellie.unitto.data.lev
import java.math.BigDecimal

/**
 * This is a basic representation of what a unit must have (properties and convert function)
 *
 * @property unitId Unit ID from [MyUnitIDS]
 * @property displayName String resource with long name, i.e. kilometer
 * @property shortName String resource with short name, i.e. km
 * @property basicUnit Used for conversion. Basically tells how big this unit is if comparing with
 * basicUnit. For example, in [UnitGroup.LENGTH] basic unit is an attometer (1), then nanometer is
 * 1.0E+9 times bigger than that. This number (1.0E+9) is a basic unit for nanometer
 * @property group [UnitGroup] of this unit
 * @property renderedName Used as cache. Stores long name string for this specific device. Need for
 * search functionality.
 * @property renderedShortName Used as cache. Stores short name string for this specific device. Need for
 * search functionality.
 * @property isFavorite Whether this unit is favorite.
 * @property isEnabled Whether we need to show this unit or not
 * @property pairedUnit Latest paired unit on the right
 * @property counter The amount of time this unit was chosen
 */
abstract class AbstractUnit(
    val unitId: String,
    @StringRes val displayName: Int,
    @StringRes val shortName: Int,
    var basicUnit: BigDecimal,
    val group: UnitGroup,
    var renderedName: String = String(),
    var renderedShortName: String = String(),
    var isFavorite: Boolean = false,
    var isEnabled: Boolean = true,
    var pairedUnit: String? = null,
    var counter: Int = 0
) {
    /**
     * Convert this unit into another
     *
     * @param unitTo Unit we want to convert to (right side unit)
     * @param value The amount to convert
     * @param scale Which scale to use (number of decimal places)
     * @return
     */
    abstract fun convert(
        unitTo: AbstractUnit,
        value: BigDecimal,
        scale: Int
    ): BigDecimal
}

/**
 * Sorts sequence of units by Levenshtein distance
 *
 * @param stringA String for Levenshtein distance
 * @return Sorted sequence of units. Units with lower Levenshtein distance are higher
 */
fun Sequence<AbstractUnit>.sortByLev(stringA: String): Sequence<AbstractUnit> {
    val stringToCompare = stringA.trim().lowercase()
    // We don't need units where name is too different, half of the symbols is wrong in this situation
    val threshold: Int = stringToCompare.length / 2
    // List of pair: Unit and it's levDist
    val unitsWithDist = mutableListOf<Pair<AbstractUnit, Int>>()
    this.forEach { unit ->
        val unitShortName: String = unit.renderedShortName.lowercase()
        /**
         * There is a chance that we search for unit with a specific short name. Such cases are
         * should be higher in the list. Best possible match.
         */
        if (stringToCompare == unitShortName) {
            unitsWithDist.add(Pair(unit, 0))
            return@forEach
        }

        val unitFullName: String = unit.renderedName.lowercase()

        /**
         * There is chance that unit name doesn't need any edits (contains part of the query)
         * So computing levDist is a waste of resources
         */
        when {
            // It's the second best possible match if it start with
            unitFullName.startsWith(stringToCompare) -> {
                unitsWithDist.add(Pair(unit, 1))
                return@forEach
            }
            // It's a little bit worse when it just contains part of the query
            unitFullName.contains(stringToCompare) -> {
                unitsWithDist.add(Pair(unit, 2))
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
        val levDist = unitFullName
            .substring(0, minOf(stringToCompare.length, unitFullName.length))
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

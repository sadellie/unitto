/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.core.data.converter

import android.content.Context
import com.sadellie.unitto.core.common.lev
import com.sadellie.unitto.core.common.normalizeSuperscript
import com.sadellie.unitto.core.model.converter.unit.BasicUnit

data class UnitSearchResultItem(
  val basicUnit: BasicUnit,
  val stats: UnitStats,
  val conversion: ConverterResult?,
)

fun Sequence<UnitSearchResultItem>.filterAndSortByLev(
  stringA: String,
  context: Context,
): Sequence<UnitSearchResultItem> {
  val stringToCompare = stringA.trim().lowercase()
  // We don't need units where name is too different, half of the symbols is wrong in this situation
  val threshold: Int = stringToCompare.length / 2
  // List of pair: Unit and it's levDist
  val unitsWithDist = mutableListOf<Pair<UnitSearchResultItem, Int>>()
  this.forEach { unit ->
    val unitShortName: String =
      context.getString(unit.basicUnit.shortName).lowercase().normalizeSuperscript()
    /**
     * There is a chance that we search for unit with a specific short name. Such cases are should
     * be higher in the list. Best possible match.
     */
    if (stringToCompare == unitShortName) {
      unitsWithDist.add(Pair(unit, 0))
      return@forEach
    }

    val unitFullName: String =
      context.getString(unit.basicUnit.displayName).lowercase().normalizeSuperscript()

    /**
     * There is chance that unit name doesn't need any edits (contains part of the query) So
     * computing levDist is a waste of resources
     */
    when {
      // It's the second best possible match if it start with
      unitFullName.startsWith(stringToCompare) -> {
        unitsWithDist.add(Pair(unit, 0))
        return@forEach
      }
      // It's a little bit worse when it just contains part of the query
      unitFullName.contains(stringToCompare) -> {
        unitsWithDist.add(Pair(unit, 1))
        return@forEach
      }
    }

    /**
     * Levenshtein Distance for this specific name of this unit
     *
     * We use substring so that we compare not the whole unit name, but only part of it. It's
     * required because without it levDist will be too high for units with longer names than the
     * search query
     *
     * For example: Search query is 'Kelometer' and unit name is 'Kilometer per hour' Without
     * substring levDist will be 9 which means that this unit will be skipped
     *
     * With substring levDist will be 3 so unit will be included
     */
    val levDist =
      unitFullName
        .substring(0, minOf(stringToCompare.length, unitFullName.length))
        .lev(stringToCompare)

    // Threshold
    if (levDist < threshold) {
      unitsWithDist.add(Pair(unit, levDist))
    }
  }
  // Sorting by levDist and getting units
  return unitsWithDist.sortedBy { it.second }.map { it.first }.asSequence()
}

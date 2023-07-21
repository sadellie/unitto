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

import com.sadellie.unitto.data.model.ALL_UNIT_GROUPS
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.UnitGroup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AllUnitsRepositoryTest {

    private val allUnitsRepository = AllUnitsRepository()

    private val allUnits: List<AbstractUnit> = ALL_UNIT_GROUPS.flatMap { allUnitsRepository.getCollectionByGroup(it) }

    @Test
    fun filterAllUnitsNoFiltersLeft() {
        //  No filters applied, empty search query, from Left side list
        val result = allUnitsRepository.filterUnits(
            hideBrokenUnits = false,
            chosenUnitGroup = null,
            favoritesOnly = false,
            searchQuery = "",
            allUnitsGroups = ALL_UNIT_GROUPS
        )
        assertEquals(allUnits.groupBy { it.group }, result)
    }

    @Test
    fun filterAllUnitsAllFiltersLeft() {
        allUnitsRepository
            .getById(MyUnitIDS.kilometer_per_hour)
            .apply { isFavorite = true; renderedName = "Kilometer per hour" }

        // All filters applied, from Left side list
        val result = allUnitsRepository.filterUnits(
            hideBrokenUnits = false,
            chosenUnitGroup = UnitGroup.SPEED,
            favoritesOnly = true,
            searchQuery = "kilometer per hour",
            allUnitsGroups = ALL_UNIT_GROUPS
        )
        assertEquals(
            mapOf(UnitGroup.SPEED to listOf(allUnitsRepository.getCollectionByGroup(UnitGroup.SPEED)
                .first { it.unitId == MyUnitIDS.kilometer_per_hour })),
            result
        )
    }

    @Test
    fun filterAllUnitsChosenGroupLeft() {
        // Only specific group is needed, left side screen
        val result = allUnitsRepository.filterUnits(
            hideBrokenUnits = false,
            chosenUnitGroup = UnitGroup.TIME,
            favoritesOnly = false,
            searchQuery = "",
            allUnitsGroups = ALL_UNIT_GROUPS
        )
        assertEquals(allUnits.filter { it.group == UnitGroup.TIME }
            .groupBy { it.group }, result)
    }

    @Test
    fun filterAllUnitsFavoritesOnlyLeft() {
        allUnitsRepository.getById(MyUnitIDS.kilometer).isFavorite = true
        // Only favorite units, left side screen
        val result = allUnitsRepository.filterUnits(
            hideBrokenUnits = false,
            chosenUnitGroup = null,
            favoritesOnly = true,
            searchQuery = "",
            allUnitsGroups = ALL_UNIT_GROUPS
        )
        assertEquals(allUnits.filter { it.unitId == MyUnitIDS.kilometer }
            .groupBy { it.group }, result)
    }

    @Test
    fun filterAllUnitsSearchQueryOnlyLeft() {
        allUnitsRepository
            .getById(MyUnitIDS.kilometer_per_hour)
            .apply { renderedName = "Kilometer per hour" }

        // Only search query is entered, other filters are not set, left side screen
        val result = allUnitsRepository.filterUnits(
            hideBrokenUnits = false,
            chosenUnitGroup = null,
            favoritesOnly = false,
            searchQuery = "kilometer per hour",
            allUnitsGroups = ALL_UNIT_GROUPS
        )
        assertEquals(
            mapOf(UnitGroup.SPEED to listOf(allUnitsRepository.getCollectionByGroup(UnitGroup.SPEED)
                .first { it.unitId == MyUnitIDS.kilometer_per_hour })),
            result
        )
    }

    @Test
    fun filterAllUnitsHideBrokenCurrencies() {
        allUnitsRepository
            .getById(MyUnitIDS.currency_btc)
            .apply { basicUnit = BigDecimal.ZERO }
        // Hide broken currencies (i.e. cannot be used for conversion at the moment)
        val result = allUnitsRepository.filterUnits(
            hideBrokenUnits = true,
            chosenUnitGroup = UnitGroup.CURRENCY,
            favoritesOnly = false,
            searchQuery = "",
            allUnitsGroups = ALL_UNIT_GROUPS
        )
        assertEquals(
            mapOf(UnitGroup.CURRENCY to allUnitsRepository.getCollectionByGroup(UnitGroup.CURRENCY)
                .filter { it.unitId != MyUnitIDS.currency_btc }),
            result
        )
    }
}

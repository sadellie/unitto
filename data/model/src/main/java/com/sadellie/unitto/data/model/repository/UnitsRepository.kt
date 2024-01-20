/*
 * Unitto is a unit converter for Android
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

package com.sadellie.unitto.data.model.repository

import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.UnitsListSorting
import com.sadellie.unitto.data.model.unit.AbstractUnit
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface UnitsRepository {
    val units: Flow<List<AbstractUnit>>

    suspend fun getById(id: String): AbstractUnit

    suspend fun getCollection(group: UnitGroup): List<AbstractUnit>

    suspend fun favorite(unit: AbstractUnit)

    suspend fun incrementCounter(unit: AbstractUnit)

    suspend fun setPair(unit: AbstractUnit, pair: AbstractUnit)

    suspend fun updateRates(unit: AbstractUnit): LocalDate?

    suspend fun filterUnits(
        query: String,
        unitGroup: UnitGroup?,
        favoritesOnly: Boolean,
        hideBrokenUnits: Boolean,
        sorting: UnitsListSorting,
        shownUnitGroups: List<UnitGroup> = emptyList(),
    ): Map<UnitGroup, List<AbstractUnit>>
}

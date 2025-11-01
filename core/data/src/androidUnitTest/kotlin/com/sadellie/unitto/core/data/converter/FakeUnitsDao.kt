/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2025 Elshan Agaev
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

import com.sadellie.unitto.core.database.UnitsDao
import com.sadellie.unitto.core.database.UnitsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeUnitsDao : UnitsDao {
  private val entries = MutableStateFlow(emptyList<UnitsEntity>())

  override fun getAllFlow(): Flow<List<UnitsEntity>> = entries

  override suspend fun insertUnit(unit: UnitsEntity) =
    entries.update { currentEntries ->
      // upsert
      currentEntries.filter { it.unitId != unit.unitId } + unit
    }

  override suspend fun getByIdsSortedByFavoriteAndFrequencyDesc(
    unitIds: List<String>
  ): UnitsEntity? =
    entries.value
      .filter { it.unitId in unitIds }
      .sortedByDescending { it.frequency }
      .maxByOrNull { it.isFavorite }

  override suspend fun getByIds(unitIds: List<String>): List<UnitsEntity> =
    entries.value.filter { it.unitId in unitIds }

  override suspend fun getById(unitId: String): UnitsEntity? =
    entries.value.firstOrNull { it.unitId == unitId }

  override suspend fun clear() = entries.update { emptyList() }
}

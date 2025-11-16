/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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

package com.sadellie.unitto.core.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update

interface UnittoDatabase {
  fun calculatorHistoryDao(): CalculatorHistoryDao

  fun unitsDao(): UnitsDao

  fun currencyRatesDao(): CurrencyRatesDao
}

class UnittoDatabaseInMemory : UnittoDatabase {
  override fun calculatorHistoryDao() = CalculatorHistoryDaoInMemory()

  override fun unitsDao(): UnitsDao = UnitsDaoInMemory()

  override fun currencyRatesDao() = CurrencyRatesDaoInMemory()
}

class UnitsDaoInMemory : UnitsDao {
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

class CurrencyRatesDaoInMemory : CurrencyRatesDao {
  private val entries = MutableStateFlow(emptyList<CurrencyRatesEntity>())

  override suspend fun insertRates(currencyRates: List<CurrencyRatesEntity>) =
    entries.update { currentEntries -> currentEntries + currencyRates }

  override suspend fun getLatestRateTimeStamp(baseId: String): Long? =
    entries.value.firstOrNull { it.baseUnitId == baseId }?.date

  override suspend fun getLatestRate(baseId: String, pairId: String): CurrencyRatesEntity? =
    entries.value.firstOrNull { it.baseUnitId == baseId && it.pairUnitId == pairId }

  override fun size(): Flow<Int> = entries.mapLatest { it.size }

  override suspend fun clear() = entries.update { emptyList() }
}

class CalculatorHistoryDaoInMemory : CalculatorHistoryDao {
  private val entries = MutableStateFlow(emptyList<CalculatorHistoryEntity>())

  override fun getAllDescending(): Flow<List<CalculatorHistoryEntity>> =
    entries.mapLatest { currentEntries -> currentEntries.sortedByDescending { it.timestamp } }

  override suspend fun insert(vararg historyEntity: CalculatorHistoryEntity) =
    entries.update { currentEntities ->
      val maxId = currentEntities.maxOfOrNull { it.entityId } ?: -1
      val newEntities =
        historyEntity.asList().mapIndexed { index, entity ->
          entity.copy(entityId = maxId + 1 + index)
        }

      (currentEntities + newEntities).distinctBy { it.entityId }
    }

  override suspend fun delete(entityId: Int) =
    entries.update { currentEntries -> currentEntries.filter { it.entityId != entityId } }

  override suspend fun clear() = entries.update { emptyList() }
}

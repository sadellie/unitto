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

package com.sadellie.unitto.core.data

import com.sadellie.unitto.core.data.converter.UnitSearchResultItem
import com.sadellie.unitto.core.data.converter.UnitStats
import com.sadellie.unitto.core.data.converter.collections.accelerationCollection
import com.sadellie.unitto.core.data.converter.collections.angleCollection
import com.sadellie.unitto.core.data.converter.collections.areaCollection
import com.sadellie.unitto.core.data.converter.collections.currencyCollection
import com.sadellie.unitto.core.data.converter.collections.dataCollection
import com.sadellie.unitto.core.data.converter.collections.dataTransferCollection
import com.sadellie.unitto.core.data.converter.collections.electrostaticCapacitance
import com.sadellie.unitto.core.data.converter.collections.energyCollection
import com.sadellie.unitto.core.data.converter.collections.flowRateCollection
import com.sadellie.unitto.core.data.converter.collections.fluxCollection
import com.sadellie.unitto.core.data.converter.collections.forceCollection
import com.sadellie.unitto.core.data.converter.collections.fuelConsumptionCollection
import com.sadellie.unitto.core.data.converter.collections.lengthCollection
import com.sadellie.unitto.core.data.converter.collections.luminanceCollection
import com.sadellie.unitto.core.data.converter.collections.massCollection
import com.sadellie.unitto.core.data.converter.collections.numberBaseCollection
import com.sadellie.unitto.core.data.converter.collections.powerCollection
import com.sadellie.unitto.core.data.converter.collections.prefixCollection
import com.sadellie.unitto.core.data.converter.collections.pressureCollection
import com.sadellie.unitto.core.data.converter.collections.speedCollection
import com.sadellie.unitto.core.data.converter.collections.temperatureCollection
import com.sadellie.unitto.core.data.converter.collections.timeCollection
import com.sadellie.unitto.core.data.converter.collections.torqueCollection
import com.sadellie.unitto.core.data.converter.collections.volumeCollection
import com.sadellie.unitto.core.data.converter.filterAndSortByLev
import com.sadellie.unitto.core.database.UnitsDao
import com.sadellie.unitto.core.database.UnitsEntity
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import com.sadellie.unitto.core.model.converter.unit.BasicUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.getString

class UnitsRepository(private val unitsDao: UnitsDao) {
  private val allUnits =
    lengthCollection +
      currencyCollection +
      massCollection +
      speedCollection +
      temperatureCollection +
      areaCollection +
      timeCollection +
      volumeCollection +
      dataCollection +
      pressureCollection +
      accelerationCollection +
      energyCollection +
      powerCollection +
      angleCollection +
      dataTransferCollection +
      fluxCollection +
      numberBaseCollection +
      electrostaticCapacitance +
      prefixCollection +
      forceCollection +
      torqueCollection +
      flowRateCollection +
      luminanceCollection +
      fuelConsumptionCollection

  suspend fun getById(id: String): BasicUnit =
    withContext(Dispatchers.Default) { allUnits.first { it.id == id } }

  suspend fun getByIdOrNull(id: String): BasicUnit? =
    withContext(Dispatchers.Default) { allUnits.firstOrNull { it.id == id } }

  suspend fun getPairId(id: String): String =
    withContext(Dispatchers.IO) {
      val basedUnitPair = getUnitStats(id).pairedUnitId
      if (basedUnitPair != null) return@withContext basedUnitPair

      // unit has no pair, get most popular unit
      val unit = getById(id)
      // same group, except for unit itself
      val potentialPairIds = allUnits.filter { it.group == unit.group && it.id != id }.map { it.id }
      val unitsStatsForUnitsInUnitGroup =
        unitsDao.getByIdsSortedByFavoriteAndFrequencyDesc(potentialPairIds)

      // fallback in specific order
      // most popular favorite, just popular or first in collection
      return@withContext unitsStatsForUnitsInUnitGroup?.unitId ?: potentialPairIds.first()
    }

  suspend fun setPair(id: String, pairId: String) =
    withContext(Dispatchers.IO) {
      val basedUnit = unitsDao.getById(id)

      if (basedUnit == null) {
        unitsDao.insertUnit(UnitsEntity(unitId = id, pairedUnitId = pairId))
      } else {
        unitsDao.insertUnit(
          UnitsEntity(
            unitId = basedUnit.unitId,
            isFavorite = basedUnit.isFavorite,
            pairedUnitId = pairId,
            frequency = basedUnit.frequency,
          )
        )
      }
    }

  suspend fun favorite(id: String) =
    withContext(Dispatchers.IO) {
      val basedUnit = unitsDao.getById(id)

      if (basedUnit == null) {
        unitsDao.insertUnit(UnitsEntity(unitId = id, isFavorite = true))
      } else {
        unitsDao.insertUnit(
          UnitsEntity(
            unitId = basedUnit.unitId,
            isFavorite = !basedUnit.isFavorite,
            pairedUnitId = basedUnit.pairedUnitId,
            frequency = basedUnit.frequency,
          )
        )
      }
    }

  suspend fun filter(
    query: String,
    unitGroups: List<UnitGroup>,
    favoritesOnly: Boolean,
    sorting: UnitsListSorting,
  ): Sequence<UnitSearchResultItem> =
    withContext(Dispatchers.IO) {
      val unitsInUnitGroup = getByGroups(unitGroups)
      val unitStats = getUnitStats(unitsInUnitGroup.map { it.id }).toSet()
      val initialUnitSearchResult = mutableListOf<UnitSearchResultItem>()
      for (unit in unitsInUnitGroup) {
        val stats = unitStats.firstOrNull { it.id == unit.id } ?: UnitStats(unit.id)
        val unitSearchResultItem = UnitSearchResultItem(unit, stats, null)
        initialUnitSearchResult.add(unitSearchResultItem)
      }

      var units = initialUnitSearchResult.asSequence()
      if (favoritesOnly) {
        units = units.filter { it.stats.isFavorite }
      }

      units =
        when (sorting) {
          UnitsListSorting.USAGE -> units.sortedByDescending { it.stats.frequency }
          UnitsListSorting.ALPHABETICAL ->
            units
              .toList()
              .map { it to getString(it.basicUnit.displayName) }
              .sortedBy { it.second }
              .map { it.first }
              .asSequence()
          UnitsListSorting.SCALE_ASC -> units.sortedBy { it.basicUnit.factor }
          UnitsListSorting.SCALE_DESC -> units.sortedByDescending { it.basicUnit.factor }
        }
      units =
        if (query.isEmpty()) {
          units.sortedByDescending { it.stats.isFavorite }
        } else {
          units.filterAndSortByLev(query)
        }
      return@withContext units
    }

  suspend fun incrementCounter(id: String) =
    withContext(Dispatchers.IO) {
      val basedUnit = unitsDao.getById(id)

      if (basedUnit == null) {
        unitsDao.insertUnit(UnitsEntity(unitId = id, frequency = 1))
      } else {
        unitsDao.insertUnit(
          UnitsEntity(
            unitId = basedUnit.unitId,
            isFavorite = basedUnit.isFavorite,
            pairedUnitId = basedUnit.pairedUnitId,
            frequency = basedUnit.frequency + 1,
          )
        )
      }
    }

  private suspend fun getByGroups(groups: List<UnitGroup>): List<BasicUnit> =
    withContext(Dispatchers.Default) { allUnits.filter { it.group in groups } }

  private suspend fun getUnitStats(id: String): UnitStats {
    val unitStatsFromDatabase = withContext(Dispatchers.IO) { unitsDao.getById(id) }
    return unitStatsFromDatabase?.toUnitStats() ?: UnitStats(id)
  }

  private suspend fun getUnitStats(ids: List<String>): List<UnitStats> {
    val unitStatsFromDatabase = withContext(Dispatchers.IO) { unitsDao.getByIds(ids) }
    if (unitStatsFromDatabase.isEmpty()) return emptyList()
    return unitStatsFromDatabase.map { it.toUnitStats() }
  }

  private fun UnitsEntity.toUnitStats(): UnitStats =
    UnitStats(unitId, isFavorite, pairedUnitId, frequency)
}

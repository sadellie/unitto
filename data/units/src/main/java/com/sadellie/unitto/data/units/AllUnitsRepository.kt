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

import android.content.Context
import com.sadellie.unitto.core.base.MAX_PRECISION
import com.sadellie.unitto.data.database.UnitsEntity
import com.sadellie.unitto.data.model.AbstractUnit
import com.sadellie.unitto.data.model.UnitGroup
import com.sadellie.unitto.data.model.sortByLev
import com.sadellie.unitto.data.units.collections.accelerationCollection
import com.sadellie.unitto.data.units.collections.angleCollection
import com.sadellie.unitto.data.units.collections.areaCollection
import com.sadellie.unitto.data.units.collections.currencyCollection
import com.sadellie.unitto.data.units.collections.dataCollection
import com.sadellie.unitto.data.units.collections.dataTransferCollection
import com.sadellie.unitto.data.units.collections.electrostaticCapacitance
import com.sadellie.unitto.data.units.collections.energyCollection
import com.sadellie.unitto.data.units.collections.fluxCollection
import com.sadellie.unitto.data.units.collections.forceCollection
import com.sadellie.unitto.data.units.collections.lengthCollection
import com.sadellie.unitto.data.units.collections.massCollection
import com.sadellie.unitto.data.units.collections.numberBaseCollection
import com.sadellie.unitto.data.units.collections.powerCollection
import com.sadellie.unitto.data.units.collections.prefixCollection
import com.sadellie.unitto.data.units.collections.pressureCollection
import com.sadellie.unitto.data.units.collections.speedCollection
import com.sadellie.unitto.data.units.collections.temperatureCollection
import com.sadellie.unitto.data.units.collections.timeCollection
import com.sadellie.unitto.data.units.collections.torqueCollection
import com.sadellie.unitto.data.units.collections.volumeCollection
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This repository provides access to all collection of units in the app.
 */
@Singleton
class AllUnitsRepository @Inject constructor() {
    /**
     * This is a collection of all available units.
     */
    private val allUnits: List<AbstractUnit> by lazy {
        mapOfCollections.values.flatten()
    }

    /**
     * Mapped [UnitGroup] to [List] of [AbstractUnit]s.
     */
    private val mapOfCollections by lazy {
        hashMapOf(
            UnitGroup.LENGTH to lengthCollection,
            UnitGroup.CURRENCY to currencyCollection,
            UnitGroup.MASS to massCollection,
            UnitGroup.SPEED to speedCollection,
            UnitGroup.TEMPERATURE to temperatureCollection,
            UnitGroup.AREA to areaCollection,
            UnitGroup.TIME to timeCollection,
            UnitGroup.VOLUME to volumeCollection,
            UnitGroup.DATA to dataCollection,
            UnitGroup.PRESSURE to pressureCollection,
            UnitGroup.ACCELERATION to accelerationCollection,
            UnitGroup.ENERGY to energyCollection,
            UnitGroup.POWER to powerCollection,
            UnitGroup.ANGLE to angleCollection,
            UnitGroup.DATA_TRANSFER to dataTransferCollection,
            UnitGroup.FLUX to fluxCollection,
            UnitGroup.NUMBER_BASE to numberBaseCollection,
            UnitGroup.ELECTROSTATIC_CAPACITANCE to electrostaticCapacitance,
            UnitGroup.PREFIX to prefixCollection,
            UnitGroup.FORCE to forceCollection,
            UnitGroup.TORQUE to torqueCollection
        )
    }

    /**
     * Get [AbstractUnit] by specified id from [MyUnitIDS].
     *
     * @param unitId Unit id from [MyUnitIDS]. Don't use literal strings here.
     * @return [AbstractUnit] from [AllUnitsRepository.allUnits] that has the given id.
     * @throws NoSuchElementException If there is no [AbstractUnit] in [AllUnitsRepository.allUnits]
     * that has the requested unitId.
     */
    fun getById(unitId: String): AbstractUnit {
        return allUnits.first { it.unitId == unitId }
    }

    /**
     * Looks for a collection of units of the given [UnitGroup].
     *
     * @param unitGroup Requested [UnitGroup]
     * @return List of [AbstractUnit]s. Will return null if the is no collection for the specified
     * [UnitGroup].
     *
     * @throws [NoSuchElementException] from [Map.getValue]
     */
    fun getCollectionByGroup(unitGroup: UnitGroup): List<AbstractUnit> {
        return mapOfCollections.getValue(unitGroup)
    }

    /**
     * Filter [AllUnitsRepository.allUnits] and group them.
     *
     * @param hideBrokenCurrencies When set to True will remove [AbstractUnit]s that have
     * [AbstractUnit.isEnabled] set to False, which means that [AbstractUnit] can not be used.
     * @param chosenUnitGroup If provided will scope list to a specific [UnitGroup].
     * @param favoritesOnly When True will filter only [AbstractUnit]s with [AbstractUnit.isFavorite]
     * set to True.
     * @param searchQuery When not empty, will search by [AbstractUnit.renderedName] using [sortByLev].
     * @param allUnitsGroups All [UnitGroup]s. Determines which units will be used for filtering.
     * @return Grouped by [UnitGroup] list of [AbstractUnit]s.
     */
    fun filterUnits(
        hideBrokenCurrencies: Boolean,
        chosenUnitGroup: UnitGroup?,
        favoritesOnly: Boolean,
        searchQuery: String,
        allUnitsGroups: List<UnitGroup>
    ): Map<UnitGroup, List<AbstractUnit>> {
        var basicFilteredUnits: Sequence<AbstractUnit> = if (chosenUnitGroup == null) {
            allUnits.filter { it.group in allUnitsGroups }
        } else {
            val collection = getCollectionByGroup(chosenUnitGroup)
            collection
        }.asSequence()

        if (favoritesOnly) {
            basicFilteredUnits = basicFilteredUnits.filter { it.isFavorite }
        }
        if (hideBrokenCurrencies) {
            basicFilteredUnits = basicFilteredUnits.filter { it.isEnabled }
        }
        val unitsToShow = if (searchQuery.isEmpty()) {
            // Query is empty, i.e. we want to see all units and they need to be sorted by usage
            basicFilteredUnits.sortedByDescending { it.counter }
        } else {
            // We sort by popularity and Levenshtein distance (short and long name).
            basicFilteredUnits.sortedByDescending { it.counter }.sortByLev(searchQuery)
        }

        return unitsToShow.groupBy { it.group }
    }

    /**
     * Maps data from database to [allUnits] item: favorites, counters, renderedNames and etc.
     *
     * @param context [Context] that is used to fill [AbstractUnit.renderedName]. Rendered names are used when
     * searching.
     * @param allUnits List from database. See: [UnitsEntity].
     */
    fun loadFromDatabase(context: Context, allUnits: List<UnitsEntity>) {
        this.allUnits.forEach {
            // Loading unit names so that we can search through them
            it.renderedName = context.getString(it.displayName)
            it.renderedShortName = context.getString(it.shortName)
            val fromDb = allUnits.firstOrNull { fromDb -> fromDb.unitId == it.unitId }
            // Loading paired units
            it.pairedUnit = fromDb?.pairedUnitId
            // Loading favorite state
            it.isFavorite = fromDb?.isFavorite ?: false
            it.counter = fromDb?.frequency ?: 0
        }
    }

    /**
     * Update [AbstractUnit.basicUnit] properties for currencies from [currencyCollection].
     *
     * @param conversions Map: [AbstractUnit.unitId] and [BigDecimal] that will replace current
     * [AbstractUnit.basicUnit].
     */
    fun updateBasicUnitsForCurrencies(
        conversions: Map<String, BigDecimal>
    ) {
        getCollectionByGroup(UnitGroup.CURRENCY).forEach {
            // Getting rates from map. We set ZERO as default so that it can be skipped
            val rate = conversions.getOrElse(it.unitId) { BigDecimal.ZERO }
            // We make sure that we don't divide by zero
            if (rate > BigDecimal.ZERO) {
                it.isEnabled = true
                it.basicUnit = BigDecimal.ONE.setScale(MAX_PRECISION).div(rate)
            } else {
                // Hiding broken currencies
                it.isEnabled = false
            }
        }
    }
}

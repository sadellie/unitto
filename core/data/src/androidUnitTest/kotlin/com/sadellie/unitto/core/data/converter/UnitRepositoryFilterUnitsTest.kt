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
import com.sadellie.unitto.core.database.UnitsDaoInMemory
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class UnitRepositoryFilterUnitsTest {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  // FIXME not provided to resource loader
  private val context = RuntimeEnvironment.getApplication().applicationContext
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = UnitsDaoInMemory()
  private val unitsRepository = UnitsRepository(fakeUnitsDao)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun filterUnits_getAll() =
    testScope.runTest {
      val expected =
        mapOf(
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
            UnitGroup.TORQUE to torqueCollection,
            UnitGroup.FLOW_RATE to flowRateCollection,
            UnitGroup.LUMINANCE to luminanceCollection,
            UnitGroup.FUEL_CONSUMPTION to fuelConsumptionCollection,
          )
          .mapValues { (_, v) ->
            v.map { unit -> UnitSearchResultItem(unit, UnitStats(unit.id), null) }
          }

      val actual =
        unitConverterRepo.filterUnits(
          query = "",
          unitGroups =
            listOf(
              UnitGroup.LENGTH,
              UnitGroup.CURRENCY,
              UnitGroup.MASS,
              UnitGroup.SPEED,
              UnitGroup.TEMPERATURE,
              UnitGroup.AREA,
              UnitGroup.TIME,
              UnitGroup.VOLUME,
              UnitGroup.DATA,
              UnitGroup.PRESSURE,
              UnitGroup.ACCELERATION,
              UnitGroup.ENERGY,
              UnitGroup.POWER,
              UnitGroup.ANGLE,
              UnitGroup.DATA_TRANSFER,
              UnitGroup.FLUX,
              UnitGroup.NUMBER_BASE,
              UnitGroup.ELECTROSTATIC_CAPACITANCE,
              UnitGroup.PREFIX,
              UnitGroup.FORCE,
              UnitGroup.TORQUE,
              UnitGroup.FLOW_RATE,
              UnitGroup.LUMINANCE,
              UnitGroup.FUEL_CONSUMPTION,
            ),
          favoritesOnly = false,
          sorting = UnitsListSorting.USAGE,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun filterUnits_verySpecific() =
    testScope.runTest {
      // fill database with some garbage
      unitConverterRepo.incrementCounter(UnitID.mile)
      unitConverterRepo.incrementCounter(UnitID.mile)
      unitConverterRepo.incrementCounter(UnitID.mile)
      unitConverterRepo.incrementCounter(UnitID.kilometer)
      unitConverterRepo.incrementCounter(UnitID.kilometer)
      unitConverterRepo.incrementCounter(UnitID.meter)
      unitConverterRepo.incrementCounter(UnitID.meter)
      unitConverterRepo.favorite(UnitID.meter)
      unitConverterRepo.incrementCounter(UnitID.attometer)

      val expected =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
              UnitSearchResultItem(
                lengthCollection.first { it.id == UnitID.meter },
                UnitStats(UnitID.meter, isFavorite = true, frequency = 2),
                null,
              )
            )
        )

      val actual =
        unitConverterRepo.filterUnits(
          // intentional typo in query
          query = "meteer",
          unitGroups = listOf(UnitGroup.LENGTH),
          favoritesOnly = true,
          sorting = UnitsListSorting.USAGE,
        )

      assertEquals(expected, actual)
    }
}

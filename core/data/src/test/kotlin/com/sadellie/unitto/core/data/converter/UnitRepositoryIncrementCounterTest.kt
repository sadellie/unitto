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

import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.database.UnitsEntity
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class UnitRepositoryIncrementCounterTest {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val context = RuntimeEnvironment.getApplication().applicationContext
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = FakeUnitsDao()
  private val unitsRepository = UnitsRepository(fakeUnitsDao, context)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun incrementCounter_createNewEntry() =
    testScope.runTest {
      unitConverterRepo.incrementCounter(UnitID.kilometer)

      val expected = UnitsEntity(unitId = UnitID.kilometer, frequency = 1)
      val actual = fakeUnitsDao.getById(UnitID.kilometer)

      assertEquals(expected, actual)
    }

  @Test
  fun incrementCounter_updateExisting() =
    testScope.runTest {
      unitConverterRepo.incrementCounter(UnitID.kilometer)
      unitConverterRepo.incrementCounter(UnitID.kilometer)
      unitConverterRepo.incrementCounter(UnitID.kilometer)
      val expected = UnitsEntity(unitId = UnitID.kilometer, frequency = 3)
      val actual = fakeUnitsDao.getById(UnitID.kilometer)

      assertEquals(expected, actual)
    }
}

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

import com.sadellie.unitto.core.common.R
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import java.math.BigDecimal
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class UnitRepositoryGetByIdTest {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val context = RuntimeEnvironment.getApplication().applicationContext
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = FakeUnitsDao()
  private val unitsRepository = UnitsRepository(fakeUnitsDao, context)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun getById_findRealUnit() =
    testScope.runTest {
      val expected =
        NormalUnit(
          UnitID.attometer,
          BigDecimal("1"),
          UnitGroup.LENGTH,
          R.string.unit_attometer,
          R.string.unit_attometer_short,
        )
      val actual = unitConverterRepo.getById(UnitID.attometer)

      assertEquals(expected, actual)
    }

  @Test
  fun getById_failToFind() =
    testScope.runTest {
      assertThrows(NoSuchElementException::class.java) {
        runBlocking { unitConverterRepo.getById("") }
      }
    }
}

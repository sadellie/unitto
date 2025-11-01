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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.unit.NormalUnit
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import unitto.core.common.generated.resources.Res
import unitto.core.common.generated.resources.unit_attometer
import unitto.core.common.generated.resources.unit_attometer_short

@RunWith(RobolectricTestRunner::class)
class UnitRepositoryGetByIdTest {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = FakeUnitsDao()
  private val unitsRepository = UnitsRepository(fakeUnitsDao)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun getById_findRealUnit() =
    testScope.runTest {
      val expected =
        NormalUnit(
          UnitID.attometer,
          KBigDecimal("1"),
          UnitGroup.LENGTH,
          Res.string.unit_attometer,
          Res.string.unit_attometer_short,
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

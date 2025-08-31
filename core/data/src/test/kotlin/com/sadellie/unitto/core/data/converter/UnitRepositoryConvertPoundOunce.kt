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

package com.sadellie.unitto.core.data.converter

import com.sadellie.unitto.core.common.setMaxScale
import com.sadellie.unitto.core.data.UnitsRepository
import java.math.BigDecimal
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class UnitRepositoryConvertPoundOunce {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val context = RuntimeEnvironment.getApplication().applicationContext
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = FakeUnitsDao()
  private val unitsRepository = UnitsRepository(fakeUnitsDao, context)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun convert_poundOunceOutput() =
    testScope.runTest {
      val expected =
        ConverterResult.PoundOunce(
          pound = BigDecimal("4").setMaxScale(),
          ounce = BigDecimal("8").setMaxScale(),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.ounce,
          unitToId = UnitID.pound,
          value1 = "72",
          value2 = "",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_poundOunceInput() =
    testScope.runTest {
      val expected =
        ConverterResult.Default(
          value = BigDecimal("40").setMaxScale(),
          calculation = BigDecimal("2.5").setMaxScale(),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.pound,
          unitToId = UnitID.ounce,
          value1 = "2",
          value2 = "8",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_poundOunceInputAndOutput() =
    testScope.runTest {
      val expected =
        ConverterResult.PoundOunce(
          pound = BigDecimal("2").setMaxScale(),
          ounce = BigDecimal("8").setMaxScale(),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.pound,
          unitToId = UnitID.pound,
          value1 = "2",
          value2 = "8",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }
}

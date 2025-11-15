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

import com.sadellie.unitto.core.common.KBigDecimal
import com.sadellie.unitto.core.common.setMaxScale
import com.sadellie.unitto.core.database.UnitsDaoInMemory
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitRepositoryConvertFootInch {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = UnitsDaoInMemory()
  private val unitsRepository = UnitsRepository(fakeUnitsDao)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun convert_footInchOutput() =
    testScope.runTest {
      val expected =
        ConverterResult.FootInch(foot = KBigDecimal("6").setMaxScale(), inch = KBigDecimal("0"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.inch,
          unitToId = UnitID.foot,
          value1 = "72",
          value2 = "",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_footInchInput() =
    testScope.runTest {
      val expected =
        ConverterResult.Default(
          value = KBigDecimal("870").setMaxScale(),
          calculation = KBigDecimal("72.5").setMaxScale(),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.foot,
          unitToId = UnitID.inch,
          value1 = "72",
          value2 = "6",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_footInchInputAndOutput() =
    testScope.runTest {
      val expected =
        ConverterResult.FootInch(
          foot = KBigDecimal("72").setMaxScale(),
          inch = KBigDecimal("6").setMaxScale(),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.foot,
          unitToId = UnitID.foot,
          value1 = "72",
          value2 = "6",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }
}

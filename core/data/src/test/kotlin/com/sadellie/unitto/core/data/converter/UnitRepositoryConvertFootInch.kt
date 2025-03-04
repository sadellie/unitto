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
class UnitRepositoryConvertFootInch {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val context = RuntimeEnvironment.getApplication().applicationContext
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = FakeUnitsDao()
  private val unitsRepository =
    UnitsRepositoryImpl(fakeUnitsDao, fakeCurrencyRatesDao, fakeCurrencyApiService, context)

  @Test
  fun convert_footInchOutput() =
    testScope.runTest {
      val expected =
        ConverterResult.FootInch(foot = BigDecimal("6").setMaxScale(), inch = BigDecimal("0"))
      val actual =
        unitsRepository.convert(
          unitFromId = UnitID.inch,
          unitToId = UnitID.foot,
          value1 = "72",
          value2 = "",
          formatTime = false,
          scale = TEST_CONVERTER_SCALE,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_footInchInput() =
    testScope.runTest {
      val expected =
        ConverterResult.Default(
          value = BigDecimal("870").setMaxScale(),
          calculation = BigDecimal("72.5").setMaxScale(),
        )
      val actual =
        unitsRepository.convert(
          unitFromId = UnitID.foot,
          unitToId = UnitID.inch,
          value1 = "72",
          value2 = "6",
          formatTime = false,
          scale = TEST_CONVERTER_SCALE,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_footInchInputAndOutput() =
    testScope.runTest {
      val expected =
        ConverterResult.FootInch(
          foot = BigDecimal("72").setMaxScale(),
          inch = BigDecimal("6").setMaxScale(),
        )
      val actual =
        unitsRepository.convert(
          unitFromId = UnitID.foot,
          unitToId = UnitID.foot,
          value1 = "72",
          value2 = "6",
          formatTime = false,
          scale = TEST_CONVERTER_SCALE,
        )

      assertEquals(expected, actual)
    }
}

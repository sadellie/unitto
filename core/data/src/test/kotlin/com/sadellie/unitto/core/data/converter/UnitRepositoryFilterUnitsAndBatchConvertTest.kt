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

import com.sadellie.unitto.core.common.setMaxScale
import com.sadellie.unitto.core.data.converter.collections.currencyCollection
import com.sadellie.unitto.core.data.converter.collections.lengthCollection
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import java.math.BigDecimal
import java.math.RoundingMode
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class UnitRepositoryFilterUnitsAndBatchConvertTest {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val context = RuntimeEnvironment.getApplication().applicationContext
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = FakeUnitsDao()
  private val unitsRepository =
    UnitsRepositoryImpl(fakeUnitsDao, fakeCurrencyRatesDao, fakeCurrencyApiService, context)

  @Test
  fun filterUnitsAndBatchConvert_convertWithoutIssues() =
    testScope.runTest {
      unitsRepository.favorite(UnitID.meter)

      val expected =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
              UnitSearchResultItem(
                lengthCollection.first { it.id == UnitID.meter },
                UnitStats(UnitID.meter, isFavorite = true, frequency = 0),
                ConverterResult.Default(
                  value = BigDecimal("12000").setMaxScale(),
                  calculation = BigDecimal("12").setMaxScale(),
                ),
              )
            )
        )

      val actual =
        unitsRepository.filterUnitsAndBatchConvert(
          query = "",
          unitGroup = UnitGroup.LENGTH,
          favoritesOnly = true,
          sorting = UnitsListSorting.USAGE,
          unitFromId = UnitID.kilometer,
          input1 = "12",
          input2 = "",
        )

      assertEquals(expected, actual)
    }

  @Test
  fun filterUnitsAndBatchConvert_convertCurrency() =
    testScope.runTest {
      unitsRepository.favorite(UnitID.currency_usd)

      val expected =
        mapOf(
          UnitGroup.CURRENCY to
            listOf(
              UnitSearchResultItem(
                currencyCollection.first { it.id == UnitID.currency_usd },
                UnitStats(UnitID.currency_usd, isFavorite = true, frequency = 0),
                ConverterResult.Default(
                  value =
                    BigDecimal("24")
                      .setScale(BATCH_CURRENCY_CONVERSION_SCALE, RoundingMode.HALF_EVEN),
                  calculation = BigDecimal("12").setMaxScale(),
                ),
              )
            )
        )

      val actual =
        unitsRepository.filterUnitsAndBatchConvert(
          query = "",
          unitGroup = UnitGroup.CURRENCY,
          favoritesOnly = true,
          sorting = UnitsListSorting.USAGE,
          unitFromId = UnitID.currency_eur,
          input1 = "12",
          input2 = "",
        )

      assertEquals(expected, actual)
    }

  @Test
  fun filterUnitsAndBatchConvert_brokenInput() =
    testScope.runTest {
      unitsRepository.favorite(UnitID.meter)

      val expected =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
              UnitSearchResultItem(
                lengthCollection.first { it.id == UnitID.meter },
                UnitStats(UnitID.meter, isFavorite = true, frequency = 0),
                null,
              )
            )
        )

      val actual =
        unitsRepository.filterUnitsAndBatchConvert(
          query = "",
          unitGroup = UnitGroup.LENGTH,
          favoritesOnly = true,
          sorting = UnitsListSorting.USAGE,
          unitFromId = UnitID.kilometer,
          input1 = "qqqq",
          input2 = "",
        )

      assertEquals(expected, actual)
    }

  @Test
  fun filterUnitsAndBatchConvert_brokenCurrenciesApi() =
    testScope.runTest {
      unitsRepository.favorite(UnitID.currency_eth)

      // do not return units if they can't be converted
      val expected = emptyMap<UnitSearchResultItem, ConverterResult>()

      val actual =
        unitsRepository.filterUnitsAndBatchConvert(
          query = "",
          unitGroup = UnitGroup.CURRENCY,
          favoritesOnly = true,
          sorting = UnitsListSorting.USAGE,
          unitFromId = UnitID.currency_eth,
          input1 = "12",
          input2 = "",
        )

      assertEquals(expected, actual)
    }

  @Test
  fun filterUnitsAndBatchConvert_convertFootInchInput() =
    testScope.runTest {
      unitsRepository.favorite(UnitID.inch)

      val expected =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
              UnitSearchResultItem(
                lengthCollection.first { it.id == UnitID.inch },
                UnitStats(UnitID.inch, isFavorite = true, frequency = 0),
                ConverterResult.Default(
                  value = BigDecimal("126").setMaxScale(),
                  calculation = BigDecimal("10.5").setMaxScale(),
                ),
              )
            )
        )

      val actual =
        unitsRepository.filterUnitsAndBatchConvert(
          query = "",
          unitGroup = UnitGroup.LENGTH,
          favoritesOnly = true,
          sorting = UnitsListSorting.USAGE,
          unitFromId = UnitID.foot,
          input1 = "10",
          input2 = "6",
        )

      assertEquals(expected, actual)
    }
}

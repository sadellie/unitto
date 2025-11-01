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
import com.sadellie.unitto.core.common.KRoundingMode
import com.sadellie.unitto.core.common.setMaxScale
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.data.converter.collections.currencyCollection
import com.sadellie.unitto.core.data.converter.collections.lengthCollection
import com.sadellie.unitto.core.model.converter.UnitGroup
import com.sadellie.unitto.core.model.converter.UnitsListSorting
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UnitRepositoryFilterUnitsAndBatchConvertTest {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = FakeUnitsDao()
  private val unitsRepository = UnitsRepository(fakeUnitsDao)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun filterUnitsAndBatchConvert_convertWithoutIssues() =
    testScope.runTest {
      unitConverterRepo.favorite(UnitID.meter)

      val expected =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
              UnitSearchResultItem(
                lengthCollection.first { it.id == UnitID.meter },
                UnitStats(UnitID.meter, isFavorite = true, frequency = 0),
                ConverterResult.Default(
                  value = KBigDecimal("12000").setMaxScale(),
                  calculation = KBigDecimal("12").setMaxScale(),
                ),
              )
            )
        )

      val actual =
        unitConverterRepo.filterUnitsAndBatchConvert(
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
      unitConverterRepo.favorite(UnitID.currency_usd)

      val expected =
        mapOf(
          UnitGroup.CURRENCY to
            listOf(
              UnitSearchResultItem(
                currencyCollection.first { it.id == UnitID.currency_usd },
                UnitStats(UnitID.currency_usd, isFavorite = true, frequency = 0),
                ConverterResult.Default(
                  value =
                    KBigDecimal("24")
                      .setScale(BATCH_CURRENCY_CONVERSION_SCALE, KRoundingMode.HALF_EVEN),
                  calculation = KBigDecimal("12").setMaxScale(),
                ),
              )
            )
        )

      val actual =
        unitConverterRepo.filterUnitsAndBatchConvert(
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
      unitConverterRepo.favorite(UnitID.meter)

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
        unitConverterRepo.filterUnitsAndBatchConvert(
          query = "",
          unitGroup = UnitGroup.LENGTH,
          favoritesOnly = true,
          sorting = UnitsListSorting.USAGE,
          unitFromId = UnitID.kilometer,
          input1 = "!!!",
          input2 = "",
        )

      assertEquals(expected, actual)
    }

  @Test
  fun filterUnitsAndBatchConvert_brokenCurrenciesApi() =
    testScope.runTest {
      unitConverterRepo.favorite(UnitID.currency_eth)

      // do not return units if they can't be converted
      val expected = emptyMap<UnitGroup, List<UnitSearchResultItem>>()

      val actual =
        unitConverterRepo.filterUnitsAndBatchConvert(
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
      unitConverterRepo.favorite(UnitID.inch)

      val expected =
        mapOf(
          UnitGroup.LENGTH to
            listOf(
              UnitSearchResultItem(
                lengthCollection.first { it.id == UnitID.inch },
                UnitStats(UnitID.inch, isFavorite = true, frequency = 0),
                ConverterResult.Default(
                  value = KBigDecimal("126").setMaxScale(),
                  calculation = KBigDecimal("10.5").setMaxScale(),
                ),
              )
            )
        )

      val actual =
        unitConverterRepo.filterUnitsAndBatchConvert(
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

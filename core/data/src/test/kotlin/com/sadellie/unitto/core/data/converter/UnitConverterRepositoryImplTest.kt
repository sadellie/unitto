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

import com.sadellie.unitto.core.common.Token
import com.sadellie.unitto.core.common.setMaxScale
import com.sadellie.unitto.core.data.UnitsRepository
import com.sadellie.unitto.core.database.CurrencyRatesEntity
import io.github.sadellie.evaluatto.ExpressionException
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
class UnitConverterRepositoryImplTest {
  private val testScope = TestScope(UnconfinedTestDispatcher())
  private val context = RuntimeEnvironment.getApplication().applicationContext
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = FakeCurrencyRatesDao()
  private val fakeUnitsDao = FakeUnitsDao()
  private val unitsRepository = UnitsRepository(fakeUnitsDao, context)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun convert_numberBase() =
    testScope.runTest {
      val expected = ConverterResult.NumberBase("10")
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.decimal,
          unitToId = UnitID.binary,
          value1 = "2",
          value2 = "",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_numberBaseBrokenInput() =
    testScope.runTest {
      assertThrows(NumberFormatException::class.java) {
        runBlocking {
          unitConverterRepo.convert(
            unitFromId = UnitID.binary,
            unitToId = UnitID.decimal,
            value1 = "7",
            value2 = "",
            formatTime = false,
          )
        }
      }
    }

  @Test
  fun convert_timeFormattedNegative() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(negative = true, attosecond = BigDecimal("28").setMaxScale())
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.attosecond,
          unitToId = UnitID.day,
          value1 = "${Token.Operator.MINUS}28",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedZero() =
    testScope.runTest {
      val expected = ConverterResult.Time()
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.attosecond,
          unitToId = UnitID.day,
          value1 = "0",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedNegativeZero() =
    testScope.runTest {
      val expected = ConverterResult.Time()
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.attosecond,
          unitToId = UnitID.day,
          value1 = "${Token.Operator.MINUS}0",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedDecimal() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(negative = false, attosecond = BigDecimal("0.05").setMaxScale())
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.attosecond,
          unitToId = UnitID.day,
          value1 = "0.05",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedDays1() =
    testScope.runTest {
      val expected = ConverterResult.Time(hour = BigDecimal("12"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.day,
          unitToId = UnitID.day,
          value1 = "0.5",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedDays2() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(
          day = BigDecimal("90"),
          minute = BigDecimal("7"),
          second = BigDecimal("12"),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.day,
          unitToId = UnitID.day,
          value1 = "90.005",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedHours1() =
    testScope.runTest {
      val expected = ConverterResult.Time(minute = BigDecimal("30"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.hour,
          unitToId = UnitID.day,
          value1 = "0.5",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedHours2() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(
          day = BigDecimal("3"),
          hour = BigDecimal("18"),
          second = BigDecimal("18"),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.hour,
          unitToId = UnitID.day,
          value1 = "90.005",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedMinutes1() =
    testScope.runTest {
      val expected = ConverterResult.Time(second = BigDecimal("30"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.minute,
          unitToId = UnitID.day,
          value1 = "0.5",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedMinutes2() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(
          hour = BigDecimal("1"),
          minute = BigDecimal("30"),
          millisecond = BigDecimal("300"),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.minute,
          unitToId = UnitID.day,
          value1 = "90.005",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedSeconds1() =
    testScope.runTest {
      val expected = ConverterResult.Time(millisecond = BigDecimal("500"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.second,
          unitToId = UnitID.day,
          value1 = "0.5",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedSeconds2() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(
          minute = BigDecimal("1"),
          second = BigDecimal("30"),
          millisecond = BigDecimal("5"),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.second,
          unitToId = UnitID.day,
          value1 = "90.005",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedMilliseconds1() =
    testScope.runTest {
      val expected = ConverterResult.Time(microsecond = BigDecimal("500"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.millisecond,
          unitToId = UnitID.day,
          value1 = "0.5",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedMilliseconds2() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(millisecond = BigDecimal("90"), microsecond = BigDecimal("5"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.millisecond,
          unitToId = UnitID.day,
          value1 = "90.005",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedMicroseconds1() =
    testScope.runTest {
      val expected = ConverterResult.Time(nanosecond = BigDecimal("500"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.microsecond,
          unitToId = UnitID.day,
          value1 = "0.5",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedMicroseconds2() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(microsecond = BigDecimal("90"), nanosecond = BigDecimal("5"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.microsecond,
          unitToId = UnitID.day,
          value1 = "90.005",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedNanoseconds1() =
    testScope.runTest {
      val expected = ConverterResult.Time(attosecond = BigDecimal("500000000.0").setMaxScale())
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.nanosecond,
          unitToId = UnitID.day,
          value1 = "0.5",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedNanoseconds2() =
    testScope.runTest {
      val expected =
        ConverterResult.Time(nanosecond = BigDecimal("90"), attosecond = BigDecimal("5000000"))
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.nanosecond,
          unitToId = UnitID.day,
          value1 = "90.005",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedAttoseconds1() =
    testScope.runTest {
      val expected = ConverterResult.Time(attosecond = BigDecimal("0.5").setMaxScale())
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.attosecond,
          unitToId = UnitID.day,
          value1 = "0.5",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_timeFormattedAttoseconds2() =
    testScope.runTest {
      val expected = ConverterResult.Time(attosecond = BigDecimal("90.005").setMaxScale())
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.attosecond,
          unitToId = UnitID.day,
          value1 = "90.005",
          value2 = "",
          formatTime = true,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_currenciesOk() =
    testScope.runTest {
      val expected =
        ConverterResult.Default(
          value = BigDecimal("36").setMaxScale(),
          calculation = BigDecimal("72").setMaxScale(),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.currency_usd,
          unitToId = UnitID.currency_eur,
          value1 = "72",
          value2 = "",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_currenciesFromCache() =
    testScope.runTest {
      // insert rates that are not currencyApi
      fakeCurrencyRatesDao.insertRates(
        listOf(
          CurrencyRatesEntity(
            entityId = 201,
            baseUnitId = UnitID.currency_btc,
            date = 0,
            pairUnitId = UnitID.currency_1inch,
            pairUnitValue = BigDecimal("40").setMaxScale(),
          ),
          CurrencyRatesEntity(
            entityId = 202,
            baseUnitId = UnitID.currency_btc,
            date = 0,
            pairUnitId = UnitID.currency_eth,
            pairUnitValue = BigDecimal("234").setMaxScale(),
          ),
        )
      )
      val expected =
        ConverterResult.Default(
          value = BigDecimal("120").setMaxScale(),
          calculation = BigDecimal("3").setMaxScale(),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.currency_btc,
          unitToId = UnitID.currency_1inch,
          value1 = "3",
          value2 = "",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_currenciesNoConnectionAndNoCache() =
    testScope.runTest {
      val expected = ConverterResult.Error.CurrencyError
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.currency_btc,
          unitToId = UnitID.currency_1inch,
          value1 = "3",
          value2 = "",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_default() =
    testScope.runTest {
      val expected =
        ConverterResult.Default(
          value = BigDecimal("72000").setMaxScale(),
          calculation = BigDecimal("72").setMaxScale(),
        )
      val actual =
        unitConverterRepo.convert(
          unitFromId = UnitID.kilometer,
          unitToId = UnitID.meter,
          value1 = "72",
          value2 = "",
          formatTime = false,
        )

      assertEquals(expected, actual)
    }

  @Test
  fun convert_failBadExpression() =
    testScope.runTest {
      assertThrows(ExpressionException.BadExpression::class.java) {
        runBlocking {
          unitConverterRepo.convert(
            unitFromId = UnitID.kilometer,
            unitToId = UnitID.meter,
            value1 = "!",
            value2 = "",
            formatTime = false,
          )
        }
      }
    }
}

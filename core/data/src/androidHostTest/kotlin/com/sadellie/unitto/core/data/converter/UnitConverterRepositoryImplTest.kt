/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024-2026 Elshan Agaev
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
import com.sadellie.unitto.core.common.Token2
import com.sadellie.unitto.core.common.setMaxScale
import com.sadellie.unitto.core.database.CurrencyRatesDaoInMemory
import com.sadellie.unitto.core.database.CurrencyRatesEntity
import com.sadellie.unitto.core.database.UnitsDaoInMemory
import io.github.sadellie.evaluatto.ExpressionException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class UnitConverterRepositoryImplTest {
  private val fakeCurrencyApiService = FakeCurrencyApiService()
  private val fakeCurrencyRatesDao = CurrencyRatesDaoInMemory()
  private val fakeUnitsDao = UnitsDaoInMemory()
  private val unitsRepository = UnitsRepository(fakeUnitsDao)
  private val unitConverterRepo =
    UnitConverterRepositoryImpl(unitsRepository, fakeCurrencyRatesDao, fakeCurrencyApiService)

  @Test
  fun convert_numberBase() = runTest {
    val expected = ConverterResult.NumberBase("10")
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.decimal,
        unitToId = UnitID.binary,
        value1 = "2",
        value2 = "",
        formatTime = false,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_numberBaseBrokenInput() = runTest {
    assertThrows(NumberFormatException::class.java) {
      runBlocking {
        unitConverterRepo.convert(
          unitFromId = UnitID.binary,
          unitToId = UnitID.decimal,
          value1 = "7",
          value2 = "",
          formatTime = false,
          apiUrl = "",
        )
      }
    }
  }

  @Test
  fun convert_timeFormattedNegative() = runTest {
    val expected =
      ConverterResult.Time(negative = true, attosecond = KBigDecimal("28").setMaxScale())
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.attosecond,
        unitToId = UnitID.day,
        value1 = "${Token2.Minus.symbol}28",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedZero() = runTest {
    val expected = ConverterResult.Time()
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.attosecond,
        unitToId = UnitID.day,
        value1 = "0",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedNegativeZero() = runTest {
    val expected = ConverterResult.Time()
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.attosecond,
        unitToId = UnitID.day,
        value1 = "${Token2.Minus.symbol}0",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedDecimal() = runTest {
    val expected =
      ConverterResult.Time(negative = false, attosecond = KBigDecimal("0.05").setMaxScale())
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.attosecond,
        unitToId = UnitID.day,
        value1 = "0.05",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedDays1() = runTest {
    val expected = ConverterResult.Time(hour = KBigDecimal("12"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.day,
        unitToId = UnitID.day,
        value1 = "0.5",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedDays2() = runTest {
    val expected =
      ConverterResult.Time(
        day = KBigDecimal("90"),
        minute = KBigDecimal("7"),
        second = KBigDecimal("12"),
      )
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.day,
        unitToId = UnitID.day,
        value1 = "90.005",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedHours1() = runTest {
    val expected = ConverterResult.Time(minute = KBigDecimal("30"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.hour,
        unitToId = UnitID.day,
        value1 = "0.5",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedHours2() = runTest {
    val expected =
      ConverterResult.Time(
        day = KBigDecimal("3"),
        hour = KBigDecimal("18"),
        second = KBigDecimal("18"),
      )
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.hour,
        unitToId = UnitID.day,
        value1 = "90.005",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedMinutes1() = runTest {
    val expected = ConverterResult.Time(second = KBigDecimal("30"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.minute,
        unitToId = UnitID.day,
        value1 = "0.5",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedMinutes2() = runTest {
    val expected =
      ConverterResult.Time(
        hour = KBigDecimal("1"),
        minute = KBigDecimal("30"),
        millisecond = KBigDecimal("300"),
      )
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.minute,
        unitToId = UnitID.day,
        value1 = "90.005",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedSeconds1() = runTest {
    val expected = ConverterResult.Time(millisecond = KBigDecimal("500"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.second,
        unitToId = UnitID.day,
        value1 = "0.5",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedSeconds2() = runTest {
    val expected =
      ConverterResult.Time(
        minute = KBigDecimal("1"),
        second = KBigDecimal("30"),
        millisecond = KBigDecimal("5"),
      )
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.second,
        unitToId = UnitID.day,
        value1 = "90.005",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedMilliseconds1() = runTest {
    val expected = ConverterResult.Time(microsecond = KBigDecimal("500"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.millisecond,
        unitToId = UnitID.day,
        value1 = "0.5",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedMilliseconds2() = runTest {
    val expected =
      ConverterResult.Time(millisecond = KBigDecimal("90"), microsecond = KBigDecimal("5"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.millisecond,
        unitToId = UnitID.day,
        value1 = "90.005",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedMicroseconds1() = runTest {
    val expected = ConverterResult.Time(nanosecond = KBigDecimal("500"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.microsecond,
        unitToId = UnitID.day,
        value1 = "0.5",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedMicroseconds2() = runTest {
    val expected =
      ConverterResult.Time(microsecond = KBigDecimal("90"), nanosecond = KBigDecimal("5"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.microsecond,
        unitToId = UnitID.day,
        value1 = "90.005",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedNanoseconds1() = runTest {
    val expected = ConverterResult.Time(attosecond = KBigDecimal("500000000.0").setMaxScale())
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.nanosecond,
        unitToId = UnitID.day,
        value1 = "0.5",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedNanoseconds2() = runTest {
    val expected =
      ConverterResult.Time(nanosecond = KBigDecimal("90"), attosecond = KBigDecimal("5000000"))
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.nanosecond,
        unitToId = UnitID.day,
        value1 = "90.005",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedAttoseconds1() = runTest {
    val expected = ConverterResult.Time(attosecond = KBigDecimal("0.5").setMaxScale())
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.attosecond,
        unitToId = UnitID.day,
        value1 = "0.5",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_timeFormattedAttoseconds2() = runTest {
    val expected = ConverterResult.Time(attosecond = KBigDecimal("90.005").setMaxScale())
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.attosecond,
        unitToId = UnitID.day,
        value1 = "90.005",
        value2 = "",
        formatTime = true,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_currenciesOk() = runTest {
    val expected =
      ConverterResult.Default(
        value = KBigDecimal("36").setMaxScale(),
        calculation = KBigDecimal("72").setMaxScale(),
      )
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.currency_usd,
        unitToId = UnitID.currency_eur,
        value1 = "72",
        value2 = "",
        formatTime = false,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_currenciesFromCache() = runTest {
    // insert rates that are not currencyApi
    fakeCurrencyRatesDao.insertRates(
      listOf(
        CurrencyRatesEntity(
          entityId = 201,
          baseUnitId = UnitID.currency_btc,
          date = 0,
          pairUnitId = UnitID.currency_1inch,
          pairUnitValue = KBigDecimal("40").setMaxScale(),
        ),
        CurrencyRatesEntity(
          entityId = 202,
          baseUnitId = UnitID.currency_btc,
          date = 0,
          pairUnitId = UnitID.currency_eth,
          pairUnitValue = KBigDecimal("234").setMaxScale(),
        ),
      )
    )
    val expected =
      ConverterResult.Default(
        value = KBigDecimal("120").setMaxScale(),
        calculation = KBigDecimal("3").setMaxScale(),
      )
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.currency_btc,
        unitToId = UnitID.currency_1inch,
        value1 = "3",
        value2 = "",
        formatTime = false,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_currenciesNoConnectionAndNoCache() = runTest {
    val expected = ConverterResult.Error.CurrencyError
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.currency_btc,
        unitToId = UnitID.currency_1inch,
        value1 = "3",
        value2 = "",
        formatTime = false,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_default() = runTest {
    val expected =
      ConverterResult.Default(
        value = KBigDecimal("72000").setMaxScale(),
        calculation = KBigDecimal("72").setMaxScale(),
      )
    val actual =
      unitConverterRepo.convert(
        unitFromId = UnitID.kilometer,
        unitToId = UnitID.meter,
        value1 = "72",
        value2 = "",
        formatTime = false,
        apiUrl = "",
      )

    assertEquals(expected, actual)
  }

  @Test
  fun convert_failBadExpression() = runTest {
    assertThrows(ExpressionException.BadExpression::class.java) {
      runBlocking {
        unitConverterRepo.convert(
          unitFromId = UnitID.kilometer,
          unitToId = UnitID.meter,
          value1 = "!",
          value2 = "",
          formatTime = false,
          apiUrl = "",
        )
      }
    }
  }
}

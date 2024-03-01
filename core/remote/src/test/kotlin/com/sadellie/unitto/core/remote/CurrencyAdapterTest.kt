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

package com.sadellie.unitto.core.remote

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyAdapterTest {
  private val currencyAdapter = CurrencyAdapter()

  @Test
  fun fromJson_validShouldPass() {
    val response =
      mapOf(
        "date" to "some date",
        "eur" to mapOf("usd" to 123.456, "btc" to 0.01, "eth" to 777, "eur" to 1.0),
      )
    val expected =
      CurrencyUnitResponse(
        date = "some date",
        currency = mapOf("usd" to 123.456, "btc" to 0.01, "eth" to 777.0, "eur" to 1.0),
      )
    val actual = currencyAdapter.fromJson(response)

    assertEquals(expected, actual)
  }

  @Test
  fun fromJson_invalidShouldFail() {
    val response = mapOf("date" to "some date", "eur" to mapOf("usd" to "undefined"))
    val expected = CurrencyUnitResponse(date = "some date", currency = emptyMap())
    val actual = currencyAdapter.fromJson(response)

    assertEquals(expected, actual)
  }
}

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

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonUnquotedLiteral
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalSerializationApi::class)
class CurrencyAdapterTest {

  @Test
  fun fromJson_validShouldPass() {
    val currencyCode = "usd"
    val jsonElement =
      JsonObject(
        mapOf(
          "date" to JsonUnquotedLiteral("2025-03-17"),
          "usd" to
            JsonObject(
              mapOf(
                "usd" to JsonUnquotedLiteral("1.0"),
                "btc" to JsonUnquotedLiteral("0.01"),
                "eth" to JsonUnquotedLiteral("777.0"),
                "eur" to JsonUnquotedLiteral("1.0"),
              )
            ),
        )
      )

    val actual = CurrencyApiResponse.fromJsonElement(jsonElement, currencyCode)
    val expected =
      CurrencyApiResponse(
        date = "2025-03-17",
        currency = mapOf("usd" to 1.0, "btc" to 0.01, "eth" to 777.0, "eur" to 1.0),
      )

    assertEquals(expected, actual)
  }

  @Test
  fun fromJson_invalidShouldFail() {
    val currencyCode = "usd"
    val jsonElement =
      JsonObject(
        mapOf(
          "date" to JsonUnquotedLiteral("some date"),
          "usd" to JsonObject(mapOf("usd" to JsonUnquotedLiteral("undefined"))),
        )
      )
    val actual = CurrencyApiResponse.fromJsonElement(jsonElement, currencyCode)
    val expected = CurrencyApiResponse(date = "some date", currency = emptyMap())

    assertEquals(expected, actual)
  }
}

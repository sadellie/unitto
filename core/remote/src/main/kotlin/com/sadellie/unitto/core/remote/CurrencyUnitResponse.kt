/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

/**
 * Represents a response object from currency-api
 *
 * @property date Date when this information (rates) was collected
 * @property currency Map with currencies and basicUnits for right side units
 */
data class CurrencyUnitResponse(val date: String, val currency: Map<String, Double>)

/**
 * Custom parser because API has a weird json structure (dynamic field names)
 *
 * ```json
 * {
 * 	 "date": "2024-11-06",
 * 	 "btc": {
 *          "1000sats": 324969538.4126074,
 *          "1inch": 1
 * 	 }
 * }
 * ```
 */
internal class CurrencyAdapter {
  @Suppress("UNUSED", "UNUSED_PARAMETER", "SameReturnValue")
  @ToJson
  fun toJson(card: CurrencyUnitResponse): String? = null

  @Suppress("UNUSED", "UNCHECKED_CAST")
  @FromJson
  fun fromJson(response: Map<String, Any>): CurrencyUnitResponse {
    val values = response[response.keys.elementAt(1)] as Map<String, Any>
    val filteredValues = mutableMapOf<String, Double>()
    values.forEach { (key, value) ->
      val valueAsDouble = value.toString().toDoubleOrNull()
      if (valueAsDouble != null) {
        // can be converted (was int or double)
        filteredValues[key] = valueAsDouble
      }
    }

    return CurrencyUnitResponse(date = response["date"] as String, currency = filteredValues)
  }
}

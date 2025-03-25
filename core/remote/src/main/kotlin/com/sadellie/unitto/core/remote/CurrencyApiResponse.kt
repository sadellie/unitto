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

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

/**
 * Represents a response object from currency-api
 *
 * @property date Date when this information (rates) was collected
 * @property currency Map with currencies and basicUnits for right side units
 */
data class CurrencyApiResponse(val date: String, val currency: Map<String, Double>) {
  companion object {

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
    internal fun fromJsonElement(
      jsonElement: JsonElement,
      currencyCode: String,
    ): CurrencyApiResponse {
      val dateValue = jsonElement.jsonObject["date"] ?: error("No date in response")
      val conversionsValue =
        jsonElement.jsonObject[currencyCode] ?: error("No conversions in response")
      val conversionMap = mutableMapOf<String, Double>()

      conversionsValue.jsonObject.forEach { key, value ->
        // can be converted (was int or double)
        val valueAsDouble = value.toString().toDoubleOrNull()
        if (valueAsDouble != null) {
          conversionMap[key] = valueAsDouble
        }
      }

      return CurrencyApiResponse(date = dateValue.toString(), currency = conversionMap)
    }
  }
}

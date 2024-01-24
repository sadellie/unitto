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

package com.sadellie.unitto.data.converter.remote

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

/**
 * Represents a response object from currency-api
 *
 * @property date Date when this information (rates) was collected
 * @property currency Map with currencies and basicUnits for right side units
 */
data class CurrencyUnitResponse(
    val date: String,
    val currency: Map<String, Double>
)

/**
 * Custom parser because API has a weird json structure (dynamic field names)
 */
internal class CurrencyAdapter {
    @Suppress("UNUSED", "UNUSED_PARAMETER", "SameReturnValue")
    @ToJson fun toJson(card: CurrencyUnitResponse): String? = null

    @Suppress("UNUSED", "UNCHECKED_CAST")
    @FromJson fun fromJson(response: Map<String, Any>): CurrencyUnitResponse {
        return CurrencyUnitResponse(
            date = response["date"] as String,
            currency = (response[response.keys.elementAt(1)]) as Map<String, Double>
        )
    }
}

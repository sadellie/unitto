package com.sadellie.unitto.data.units.remote

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

/**
 * Represents a response object from currency-api
 *
 * @property date Date when this information (rates) was collected
 * @property currency Map with currencies and basicUnits for right side units
 */
data class CurrencyUnitResponse(
    val date: String,
    val currency: Map<String, BigDecimal>
)

/**
 * Custom parser because API has a weird json structure (dynamic field names)
 */
class CurrencyAdapter {
    @Suppress("UNUSED", "UNUSED_PARAMETER")
    @ToJson fun toJson(card: CurrencyUnitResponse): String? = null

    @Suppress("UNCHECKED_CAST")
    @FromJson fun fromJson(response: Map<String, Any>): CurrencyUnitResponse {
        val pairsBD: Map<String, BigDecimal> = (response[response.keys.elementAt(1)] as Map<String, Double>)
            .mapValues { it.value.toBigDecimal() }

        return CurrencyUnitResponse(
            date = response["date"] as String,
            currency = pairsBD
        )
    }
}

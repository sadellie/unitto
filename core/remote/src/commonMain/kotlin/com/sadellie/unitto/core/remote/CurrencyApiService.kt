/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2025 Elshan Agaev
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

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.JsonElement

interface CurrencyApiService {
  suspend fun getCurrencyPairs(baseCurrency: String): CurrencyApiResponse
}

internal class CurrencyApiServiceImpl() : CurrencyApiService {
  private val client by lazy { HttpClient { install(ContentNegotiation) { json() } } }

  override suspend fun getCurrencyPairs(baseCurrency: String): CurrencyApiResponse {
    val response = client.get("$BASE_URL/${baseCurrency}.json").body<JsonElement>()
    val currencyApiResponse = CurrencyApiResponse.fromJsonElement(response, baseCurrency)
    return currencyApiResponse
  }
}

private const val BASE_URL =
  "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies"

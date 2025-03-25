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

import com.sadellie.unitto.core.remote.CurrencyApiResponse
import com.sadellie.unitto.core.remote.CurrencyApiService

class FakeCurrencyApiService : CurrencyApiService {
  override suspend fun getCurrencyPairs(baseCurrency: String): CurrencyApiResponse {
    val currencyMap =
      when (baseCurrency) {
        UnitID.currency_usd ->
          mapOf(
            UnitID.currency_usd to 1.0,
            UnitID.currency_eur to 0.5,
            UnitID.currency_btc to 69.420,
          )
        UnitID.currency_eur ->
          mapOf(
            UnitID.currency_usd to 2.0,
            UnitID.currency_eur to 1.0,
            UnitID.currency_btc to 69.420,
          )
        UnitID.currency_btc ->
          mapOf(
            UnitID.currency_usd to 0.001,
            UnitID.currency_eur to 0.002,
            UnitID.currency_btc to 1.0,
          )
        else -> error("nope")
      }
    return CurrencyApiResponse("", currencyMap)
  }
}

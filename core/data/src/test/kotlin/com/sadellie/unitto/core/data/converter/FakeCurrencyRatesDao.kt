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

import com.sadellie.unitto.core.database.CurrencyRatesDao
import com.sadellie.unitto.core.database.CurrencyRatesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update

class FakeCurrencyRatesDao : CurrencyRatesDao {
  private val entries = MutableStateFlow(emptyList<CurrencyRatesEntity>())

  override suspend fun insertRates(currencyRates: List<CurrencyRatesEntity>) =
    entries.update { currentEntries -> currentEntries + currencyRates }

  override suspend fun getLatestRateTimeStamp(baseId: String): Long? =
    entries.value.firstOrNull { it.baseUnitId == baseId }?.date

  override suspend fun getLatestRate(baseId: String, pairId: String): CurrencyRatesEntity? =
    entries.value.firstOrNull { it.baseUnitId == baseId && it.pairUnitId == pairId }

  override fun size(): Flow<Int> = entries.mapLatest { it.size }

  override suspend fun clear() = entries.update { emptyList() }
}

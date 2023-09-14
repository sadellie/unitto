/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2023 Elshan Agaev
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

package com.sadellie.unitto.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(currencyRates: List<CurrencyRatesEntity>)

    @Query("SELECT DISTINCT * FROM currency_rates WHERE timestamp = (SELECT MAX(timestamp) FROM currency_rates) AND base_unit_id = :baseId")
    suspend fun getLatestRates(baseId: String): List<CurrencyRatesEntity>
}

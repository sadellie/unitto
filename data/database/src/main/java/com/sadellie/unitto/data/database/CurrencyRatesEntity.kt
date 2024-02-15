/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2024 Elshan Agaev
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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "currency_rates")
class CurrencyRatesEntity(
    @PrimaryKey(autoGenerate = true) val entityId: Int = 0,
    @ColumnInfo(name = "base_unit_id") val baseUnitId: String,
    @ColumnInfo(name = "timestamp") val date: Long,
    @ColumnInfo(name = "pair_unit_id") val pairUnitId: String,
    @ColumnInfo(name = "pair_unit_value") val pairUnitValue: BigDecimal?,
)

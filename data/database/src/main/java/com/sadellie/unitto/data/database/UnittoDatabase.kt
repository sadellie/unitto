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

package com.sadellie.unitto.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sadellie.unitto.data.database.converters.Converters

@Database(
    version = 5,
    exportSchema = true,
    entities = [
        UnitsEntity::class,
        CalculatorHistoryEntity::class,
        TimeZoneEntity::class,
        CurrencyRatesEntity::class,
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
    ],
)
@TypeConverters(Converters::class)
abstract class UnittoDatabase : RoomDatabase() {
    abstract fun unitsDao(): UnitsDao
    abstract fun calculatorHistoryDao(): CalculatorHistoryDao
    abstract fun timeZoneDao(): TimeZoneDao
    abstract fun currencyRatesDao(): CurrencyRatesDao
    internal abstract fun rawDao(): RawDao
}

suspend fun UnittoDatabase.checkpoint() = this.rawDao().walCheckpoint()

/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2023 Elshan Agaev
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

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module for database. Used to access same instance of database
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class UnittoDatabaseModule {
    /**
     * Tells Hilt to use this method to get [UnitsDao]
     *
     * @param unittoDatabase Database for which we need DAO
     * @return Singleton of [UnitsDao]
     */
    @Provides
    fun provideUnitsDao(unittoDatabase: UnittoDatabase): UnitsDao {
        return unittoDatabase.unitsDao()
    }

    /**
     * Tells Hilt to use this method to get [CalculatorHistoryDao]
     *
     * @param unittoDatabase Database for which we need DAO
     * @return Singleton of [CalculatorHistoryDao]
     */
    @Provides
    fun provideCalculatorHistoryDao(unittoDatabase: UnittoDatabase): CalculatorHistoryDao {
        return unittoDatabase.calculatorHistoryDao()
    }

//    For some reason this fucks up the migration
//    /**
//     * Tells Hilt to use this method to get [TimeZoneDao]
//     *
//     * @param unittoDatabase Database for which we need DAO
//     * @return Singleton of [TimeZoneDao]
//     */
//    @Provides
//    fun provideTimeZoneDao(unittoDatabase: UnittoDatabase): TimeZoneDao {
//        return unittoDatabase.timeZoneDao()
//    }

    /**
     * Tells Hilt to use this method to get [UnittoDatabase]
     *
     * @param appContext Context
     * @return Singleton of [UnittoDatabase]
     */
    @Provides
    @Singleton
    fun provideUnittoDatabase(@ApplicationContext appContext: Context): UnittoDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            UnittoDatabase::class.java,
            "unitto_database"
        ).build()
    }
}
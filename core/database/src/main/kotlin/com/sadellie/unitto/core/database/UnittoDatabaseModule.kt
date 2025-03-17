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

package com.sadellie.unitto.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "unitto_database"

/** Module for database. Used to access same instance of database */
@InstallIn(SingletonComponent::class)
@Module
class UnittoDatabaseModule {
  @Provides
  fun provideRawDao(unittoDatabase: UnittoDatabase): RawDao {
    return unittoDatabase.rawDao()
  }

  @Provides
  fun provideUnitsDao(unittoDatabase: UnittoDatabase): UnitsDao {
    return unittoDatabase.unitsDao()
  }

  @Provides
  fun provideCalculatorHistoryDao(unittoDatabase: UnittoDatabase): CalculatorHistoryDao {
    return unittoDatabase.calculatorHistoryDao()
  }

  /**
   * Tells Hilt to use this method to get [TimeZoneDao]
   *
   * @param unittoDatabase Database for which we need DAO
   * @return Singleton of [TimeZoneDao]
   */
  @Provides
  fun provideTimeZoneDao(unittoDatabase: UnittoDatabase): TimeZoneDao {
    return unittoDatabase.timeZoneDao()
  }

  @Provides
  fun provideCurrencyRatesDao(unittoDatabase: UnittoDatabase): CurrencyRatesDao {
    return unittoDatabase.currencyRatesDao()
  }

  @Provides
  fun provideConverterWidgetUnitsPairDao(
    unittoDatabase: UnittoDatabase
  ): ConverterWidgetUnitPairDao {
    return unittoDatabase.converterWidgetUnitsPairDao()
  }

  @Provides
  fun provideAppStatsDao(unittoDatabase: UnittoDatabase): AppStatsDao {
    return unittoDatabase.appStatsDao()
  }

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
        DATABASE_NAME,
      )
      .build()
  }
}

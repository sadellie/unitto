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

package com.sadellie.unitto.core.database

import android.content.Context
import androidx.room.Room
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@BindingContainer
actual object DatabaseBindings {
  @Provides
  @SingleIn(AppScope::class)
  fun provideDatabaseAndroid(context: Context): UnittoDatabaseAndroid =
    Room.databaseBuilder(
        context.applicationContext,
        UnittoDatabaseAndroid::class.java,
        DATABASE_NAME,
      )
      .build()

  @Provides
  @SingleIn(AppScope::class)
  fun provideDatabase(unittoDatabaseAndroid: UnittoDatabaseAndroid): UnittoDatabase =
    unittoDatabaseAndroid as UnittoDatabase

  @Provides fun provideRawDao(database: UnittoDatabaseAndroid): RawDao = database.rawDao()

  @Provides fun provideUnitsDao(database: UnittoDatabase): UnitsDao = database.unitsDao()

  @Provides
  fun provideCalculatorHistoryDao(database: UnittoDatabase): CalculatorHistoryDao =
    database.calculatorHistoryDao()

  @Provides
  fun provideTimeZoneDao(database: UnittoDatabaseAndroid): TimeZoneDao = database.timeZoneDao()

  @Provides
  fun provideCurrencyRatesDao(database: UnittoDatabase): CurrencyRatesDao =
    database.currencyRatesDao()

  @Provides
  fun provideConverterWidgetUnitPairDao(
    database: UnittoDatabaseAndroid
  ): ConverterWidgetUnitPairDao = database.converterWidgetUnitsPairDao()

  @Provides
  fun provideConverterAppStatsDao(database: UnittoDatabaseAndroid): AppStatsDao =
    database.appStatsDao()
}

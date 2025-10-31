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

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.lazyModule

const val DATABASE_NAME = "unitto_database"

val unittoDatabaseModule = lazyModule {
  single<UnittoDatabase> {
    val appContext = androidContext()
    Room.databaseBuilder(appContext.applicationContext, UnittoDatabase::class.java, DATABASE_NAME)
      .build()
  }

  factory<RawDao> { get<UnittoDatabase>().rawDao() }
  factory<UnitsDao> { get<UnittoDatabase>().unitsDao() }
  factory<CalculatorHistoryDao> { get<UnittoDatabase>().calculatorHistoryDao() }
  factory<TimeZoneDao> { get<UnittoDatabase>().timeZoneDao() }
  factory<CurrencyRatesDao> { get<UnittoDatabase>().currencyRatesDao() }
  factory<ConverterWidgetUnitPairDao> { get<UnittoDatabase>().converterWidgetUnitsPairDao() }
  factory<AppStatsDao> { get<UnittoDatabase>().appStatsDao() }
}

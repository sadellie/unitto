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

package com.sadellie.unitto.core.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sadellie.unitto.core.data.calculator.CalculatorHistoryRepository
import com.sadellie.unitto.core.data.calculator.CalculatorHistoryRepositoryImpl
import com.sadellie.unitto.core.data.converter.UnitConverterRepository
import com.sadellie.unitto.core.data.converter.UnitConverterRepositoryImpl
import com.sadellie.unitto.core.data.timezone.TimeZonesRepository
import com.sadellie.unitto.core.data.timezone.TimeZonesRepositoryImpl
import com.sadellie.unitto.core.database.CalculatorHistoryDao
import com.sadellie.unitto.core.database.CurrencyRatesDao
import com.sadellie.unitto.core.database.TimeZoneDao
import com.sadellie.unitto.core.database.UnitsDao
import com.sadellie.unitto.core.remote.CurrencyApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
  @Provides
  fun provideCalculatorHistoryRepository(
    calculatorHistoryDao: CalculatorHistoryDao
  ): CalculatorHistoryRepository = CalculatorHistoryRepositoryImpl(calculatorHistoryDao)

  @Provides
  fun provideUnitsRepository(
    unitsDao: UnitsDao,
    @ApplicationContext mContext: Context,
  ): UnitsRepository = UnitsRepository(unitsDao, mContext)

  @Provides
  fun provideUnitConverterRepository(
    unitsRepository: UnitsRepository,
    currencyRatesDao: CurrencyRatesDao,
  ): UnitConverterRepository =
    UnitConverterRepositoryImpl(unitsRepository, currencyRatesDao, CurrencyApiServiceImpl)

  @RequiresApi(Build.VERSION_CODES.N)
  @Provides
  fun provideTimeZonesRepository(dao: TimeZoneDao): TimeZonesRepository =
    TimeZonesRepositoryImpl(dao)
}

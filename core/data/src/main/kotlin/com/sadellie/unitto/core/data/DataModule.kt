/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2025 Elshan Agaev
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

import com.sadellie.unitto.core.data.calculator.CalculatorHistoryRepository
import com.sadellie.unitto.core.data.calculator.CalculatorHistoryRepositoryImpl
import com.sadellie.unitto.core.data.converter.UnitConverterRepository
import com.sadellie.unitto.core.data.converter.UnitConverterRepositoryImpl
import com.sadellie.unitto.core.data.timezone.TimeZonesRepository
import com.sadellie.unitto.core.data.timezone.TimeZonesRepositoryImpl
import com.sadellie.unitto.core.remote.CurrencyApiServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.lazyModule

val dataModule = lazyModule {
  factory<CalculatorHistoryRepository> {
    CalculatorHistoryRepositoryImpl(calculatorHistoryDao = get())
  }

  factory<UnitsRepository> { UnitsRepository(unitsDao = get(), mContext = androidContext()) }

  factory<UnitConverterRepository> {
    UnitConverterRepositoryImpl(
      unitsRepo = get(),
      currencyRatesDao = get(),
      currencyApiService = CurrencyApiServiceImpl,
    )
  }

  single<TimeZonesRepository> { TimeZonesRepositoryImpl(dao = get()) }
}

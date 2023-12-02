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

package com.sadellie.unitto.data.converter

import android.content.Context
import com.sadellie.unitto.data.database.CurrencyRatesDao
import com.sadellie.unitto.data.database.UnitsDao
import com.sadellie.unitto.data.model.repository.UnitsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    fun provideUnitsRepository(
        unitsDao: UnitsDao,
        currencyRatesDao: CurrencyRatesDao,
        @ApplicationContext appContext: Context
    ): UnitsRepository {
        return UnitsRepositoryImpl(
            unitsDao = unitsDao,
            currencyRatesDao = currencyRatesDao,
            mContext = appContext
        )
    }
}

/*
 * Unitto is a unit converter for Android
 * Copyright (c) 2022-2022 Elshan Agaev
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

package com.sadellie.unitto.data.units.database

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
class BasedUnitDatabaseModule {
    /**
     * Tells Hilt to use this method to get [MyBasedUnitDao]
     *
     * @param myBasedUnitDatabase Database for which we need DAO
     * @return Singleton of [MyBasedUnitDao]
     */
    @Provides
    fun provideMyBasedUnitDao(myBasedUnitDatabase: MyBasedUnitDatabase): MyBasedUnitDao {
        return myBasedUnitDatabase.myBasedUnitDao()
    }

    /**
     * Tells Hilt to use this method to get [MyBasedUnitDatabase]
     *
     * @param appContext Context
     * @return Singleton of [MyBasedUnitDatabase]
     */
    @Provides
    @Singleton
    fun provideMyBasedUnitDatabase(@ApplicationContext appContext: Context): MyBasedUnitDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            MyBasedUnitDatabase::class.java,
            "unitto_database"
        ).build()
    }
}
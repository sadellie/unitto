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
package com.sadellie.unitto.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// DON'T TOUCH!!!
private const val USER_PREFERENCES = "settings"

/**
 * This module is for DataStore dependency injection
 */
@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule {

    /**
     * Tells Hilt to use this method to get [DataStore]
     *
     * @param appContext
     * @return Singleton of [DataStore]
     */
    @Singleton
    @Provides
    fun provideUserPreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}

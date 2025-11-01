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

package com.sadellie.unitto.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.lazyModule

// DON'T TOUCH!!!
const val USER_PREFERENCES = "settings"

val dataStoreModule = lazyModule {
  single<DataStore<Preferences>> {
    PreferenceDataStoreFactory.create(
      corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
      produceFile = { androidContext().preferencesDataStoreFile(USER_PREFERENCES) },
    )
  }

  factory<UserPreferencesRepository> { UserPreferencesRepositoryImpl(dataStore = get()) }
}

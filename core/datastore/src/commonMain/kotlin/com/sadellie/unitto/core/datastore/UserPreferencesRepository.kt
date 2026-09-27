/*
 * Unitto is a calculator for Android
 * Copyright (c) 2023-2026 Elshan Agaev
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
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class UserPrefDataSource(private val dataStore: DataStore<Preferences>) {
  val rawData: Flow<Preferences> =
    dataStore.data
      .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
      .distinctUntilChanged()

  fun <T> flow(key: Preferences.Key<T>, default: T): Flow<T> =
    rawData.map { it[key] ?: default }.distinctUntilChanged()

  suspend fun edit(transform: suspend (MutablePreferences) -> Unit) {
    dataStore.edit(transform)
  }
}

internal inline fun <T, R> T.letTryOrNull(block: (T) -> R): R? =
  try {
    this?.let(block)
  } catch (_: Exception) {
    null
  }

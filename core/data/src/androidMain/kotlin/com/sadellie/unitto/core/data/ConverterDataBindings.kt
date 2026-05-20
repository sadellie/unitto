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

package com.sadellie.unitto.core.data

import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import com.sadellie.unitto.core.data.timezone.TimeZonesRepository
import com.sadellie.unitto.core.data.timezone.TimeZonesRepositoryImpl
import com.sadellie.unitto.core.database.DatabaseBindings
import com.sadellie.unitto.core.database.TimeZoneDao
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import com.sadellie.unitto.core.model.timezone.SearchResultZone
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.emptyFlow

@BindingContainer(includes = [DatabaseBindings::class])
object TimeZoneDataBindings {
  @Provides
  @SingleIn(AppScope::class)
  fun provideTimeZonesRepository(timeZoneDao: TimeZoneDao): TimeZonesRepository =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      TimeZonesRepositoryImpl(dao = timeZoneDao)
    } else {
      // unused dummy is easier to implement for this case
      object : TimeZonesRepository {
        override val favoriteTimeZones = emptyFlow<List<FavoriteZone>>()

        override suspend fun updatePosition(timeZone: FavoriteZone, targetPosition: Int) {}

        override suspend fun addToFavorites(timeZone: TimeZone) {}

        override suspend fun removeFromFavorites(timeZone: FavoriteZone) {}

        override suspend fun updateLabel(timeZone: FavoriteZone, label: String) {}

        override suspend fun filter(searchQuery: String, locale: ULocale): List<SearchResultZone> =
          emptyList()
      }
    }
}

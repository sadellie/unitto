/*
 * Unitto is a calculator for Android
 * Copyright (c) 2024 Elshan Agaev
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

package com.sadellie.unitto.core.data.timezone

import android.icu.util.TimeZone
import android.icu.util.ULocale
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import com.sadellie.unitto.core.model.timezone.SearchResultZone
import kotlinx.coroutines.flow.Flow

interface TimeZonesRepository {
  val favoriteTimeZones: Flow<List<FavoriteZone>>

  suspend fun updatePosition(timeZone: FavoriteZone, targetPosition: Int)

  suspend fun addToFavorites(timeZone: TimeZone)

  suspend fun removeFromFavorites(timeZone: FavoriteZone)

  suspend fun updateLabel(timeZone: FavoriteZone, label: String)

  suspend fun filter(searchQuery: String, locale: ULocale): List<SearchResultZone>
}

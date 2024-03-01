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

package com.sadellie.unitto.core.data.timezone

import android.icu.text.LocaleDisplayNames
import android.icu.text.TimeZoneNames
import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import androidx.annotation.RequiresApi
import com.sadellie.unitto.core.common.displayName
import com.sadellie.unitto.core.common.lev
import com.sadellie.unitto.core.common.regionName
import com.sadellie.unitto.core.database.TimeZoneDao
import com.sadellie.unitto.core.model.timezone.FavoriteZone
import com.sadellie.unitto.core.model.timezone.SearchResultZone
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.N)
@Singleton
class TimeZonesRepositoryImpl @Inject constructor(private val dao: TimeZoneDao) :
  TimeZonesRepository {
  override val favoriteTimeZones: Flow<List<FavoriteZone>> =
    dao.getFavorites().map { list ->
      val favorites = mutableListOf<FavoriteZone>()
      list.forEach { entity ->
        val tz = TimeZone.getTimeZone(entity.id)
        if (tz.id == "Etc/Unknown") return@forEach
        favorites.add(FavoriteZone(tz, entity.position, entity.label))
      }

      favorites
    }

  override suspend fun updatePosition(timeZone: FavoriteZone, targetPosition: Int) =
    withContext(Dispatchers.IO) {
      dao.updatePosition(timeZone.timeZone.id, timeZone.position, targetPosition)
    }

  override suspend fun addToFavorites(timeZone: TimeZone) {
    withContext(Dispatchers.IO) { dao.addToFavorites(timeZone.id) }
  }

  override suspend fun removeFromFavorites(timeZone: FavoriteZone) =
    withContext(Dispatchers.IO) { dao.removeFromFavorites(timeZone.timeZone.id) }

  override suspend fun updateLabel(timeZone: FavoriteZone, label: String) =
    withContext(Dispatchers.IO) { dao.updateLabel(timeZone.timeZone.id, label) }

  override suspend fun filter(searchQuery: String, locale: ULocale): List<SearchResultZone> =
    withContext(Dispatchers.IO) {
      val timeZoneNames = TimeZoneNames.getInstance(locale)
      val localeDisplayNames = LocaleDisplayNames.getInstance(locale)

      val favorites = dao.getFavorites().first().map { it.id }
      val timezones =
        TimeZone.getAvailableIDs().filter { it !in favorites }.map { TimeZone.getTimeZone(it) }

      if (searchQuery.isBlank()) {
        return@withContext timezones
          .map { timeZone ->
            val displayName = timeZone.displayName(locale)
            val regionName = timeZone.regionName(timeZoneNames, localeDisplayNames)
            SearchResultZone(timeZone, displayName, regionName, 0)
          }
          .sortedBy { it.name }
      }

      val query = searchQuery.trim().lowercase()
      val threshold: Int = query.length / 2
      val timeZonesWithDist = mutableSetOf<SearchResultZone>()

      // Don't use map here so that only needed SearchResultZone objects will be created
      timezones.forEach { timeZone ->
        val displayName = timeZone.displayName(locale)
        val regionName = timeZone.regionName(timeZoneNames, localeDisplayNames)

        val nameMatch = matchProperty(displayName, query, threshold)
        if (nameMatch != null) {
          timeZonesWithDist.add(SearchResultZone(timeZone, displayName, regionName, nameMatch))
          return@forEach
        }

        val regionMatch = matchProperty(regionName, query, threshold)
        if (regionMatch != null) {
          timeZonesWithDist.add(SearchResultZone(timeZone, displayName, regionName, regionMatch))
          return@forEach
        }
      }

      return@withContext timeZonesWithDist.sortedBy { it.rank }
    }

  /**
   * Checks strings and returns their similarity score or null if didn't match. Similarity score is
   * not always Levenshtein Distance!
   */
  private fun matchProperty(prop: String, query: String, levThreshold: Int): Int? {
    if (prop.startsWith(query, true)) return 0

    if (prop.contains(query, true)) return 1

    val levDist = prop.substring(0, minOf(query.length, prop.length)).lev(query)
    if (levDist < levThreshold) return levDist

    return null
  }
}

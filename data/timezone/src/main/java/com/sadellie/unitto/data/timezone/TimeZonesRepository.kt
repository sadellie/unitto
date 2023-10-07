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

package com.sadellie.unitto.data.timezone

import android.icu.text.LocaleDisplayNames
import android.icu.text.TimeZoneNames
import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import androidx.annotation.RequiresApi
import com.sadellie.unitto.data.common.lev
import com.sadellie.unitto.data.common.regionName
import com.sadellie.unitto.data.database.TimeZoneDao
import com.sadellie.unitto.data.model.timezone.FavoriteZone
import com.sadellie.unitto.data.model.timezone.SearchResultZone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.N)
@Singleton
class TimeZonesRepository @Inject constructor(
    private val dao: TimeZoneDao
) {
//    Not implemented because it will take me too much time to map 600+ TimeZones and codes
//    private val codeToTimeZoneId: HashMap<String, String> by lazy {
//        hashMapOf()
//    }

    val favoriteTimeZones: Flow<List<FavoriteZone>> = dao
        .getFavorites()
        .map { list ->
            val favorites = mutableListOf<FavoriteZone>()
            list.forEach { entity ->
                favorites.add(
                    FavoriteZone(
                        timeZone = TimeZone.getTimeZone(entity.id),
                        position = entity.position,
                        label = entity.label
                    )
                )
            }

            favorites
        }

    suspend fun moveTimeZone(
        timeZone: FavoriteZone,
        targetPosition: Int
    ) = withContext(Dispatchers.IO) {
        dao.moveMove(timeZone.timeZone.id, timeZone.position, targetPosition)
    }

    suspend fun addToFavorites(
        timeZone: TimeZone
    ) {
        withContext(Dispatchers.IO) {
            dao.addToFavorites(timeZone.id)
        }
    }

    suspend fun removeFromFavorites(
        timeZone: FavoriteZone
    ) = withContext(Dispatchers.IO) {
        dao.removeFromFavorites(timeZone.timeZone.id)
    }

    suspend fun updateLabel(
        timeZone: FavoriteZone,
        label: String
    ) = withContext(Dispatchers.IO) {
        dao.updateLabel(timeZone.timeZone.id, label)
    }

    suspend fun filterAllTimeZones(
        searchQuery: String,
        locale: ULocale,
    ): List<SearchResultZone> =
        withContext(Dispatchers.IO) {
            val favorites = dao.getFavorites().first().map { it.id }
            val timeZoneNames = TimeZoneNames.getInstance(locale)
            val localeDisplayNames = LocaleDisplayNames.getInstance(locale)

            val query = searchQuery.trim().lowercase()
            val threshold: Int = query.length / 2
            val timeZonesWithDist = mutableListOf<Pair<SearchResultZone, Int>>()

            TimeZone.getAvailableIDs().forEach { timeZoneId ->
                if (timeZoneId in favorites) return@forEach

                val timeZone = TimeZone.getTimeZone(timeZoneId)
                val displayName = timeZone.displayName
                val regionName = timeZone.regionName(timeZoneNames, localeDisplayNames)

//                // CODE Match
//                if (codeToTimeZoneId[timeZone.id]?.lowercase() == query) {
//                    timeZonesWithDist.add(SearchResultZone(timeZone, id) to 1)
//                    return@forEach
//                }

                // Display name match
                when {
                    // not zero, so that lev can have that
                    displayName.startsWith(query) -> {
                        timeZonesWithDist.add(SearchResultZone(timeZone, regionName) to 1)
                        return@forEach
                    }

                    displayName.contains(query) -> {
                        timeZonesWithDist.add(SearchResultZone(timeZone, regionName) to 2)
                        return@forEach
                    }
                }
                val displayNameLevDist = displayName
                    .substring(0, minOf(query.length, displayName.length))
                    .lev(query)
                if (displayNameLevDist < threshold) {
                    timeZonesWithDist.add(SearchResultZone(timeZone, regionName) to displayNameLevDist)
                    return@forEach
                }

                // ID Match
                when {
                    // not zero, so that lev can have that
                    regionName.startsWith(query) -> {
                        timeZonesWithDist.add(SearchResultZone(timeZone, regionName) to 1)
                        return@forEach
                    }

                    regionName.contains(query) -> {
                        timeZonesWithDist.add(SearchResultZone(timeZone, regionName) to 2)
                        return@forEach
                    }
                }
                val regionNameLevDist = regionName
                    .substring(0, minOf(query.length, regionName.length))
                    .lev(query)
                if (regionNameLevDist < threshold) {
                    timeZonesWithDist.add(SearchResultZone(timeZone, regionName) to regionNameLevDist)
                    return@forEach
                }
            }

            return@withContext timeZonesWithDist.sortedBy { it.second }.map { it.first }
        }
}

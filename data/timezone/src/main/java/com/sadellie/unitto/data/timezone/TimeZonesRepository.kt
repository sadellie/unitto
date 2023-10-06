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

import android.icu.util.TimeZone
import android.os.Build
import androidx.annotation.RequiresApi
import com.sadellie.unitto.data.common.lev
import com.sadellie.unitto.data.common.region
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
        searchQuery: String
    ): List<SearchResultZone> =
        withContext(Dispatchers.IO) {
            val favorites = dao.getFavorites().first().map { it.id }

            val query = searchQuery.trim().lowercase()
            val threshold: Int = query.length / 2
            val timeZonesWithDist = mutableListOf<Pair<SearchResultZone, Int>>()

            TimeZone.getAvailableIDs().forEach { timeZoneId ->
                if (timeZoneId in favorites) return@forEach

                val timeZone = TimeZone.getTimeZone(timeZoneId)
                val name = timeZone.displayName
                val id = timeZone.region

//                // CODE Match
//                if (codeToTimeZoneId[timeZone.id]?.lowercase() == query) {
//                    timeZonesWithDist.add(SearchResultZone(timeZone, id) to 1)
//                    return@forEach
//                }

                // Display name match
                when {
                    // not zero, so that lev can have that
                    name.startsWith(query) -> {
                        timeZonesWithDist.add(SearchResultZone(timeZone, id) to 1)
                        return@forEach
                    }

                    name.contains(query) -> {
                        timeZonesWithDist.add(SearchResultZone(timeZone, id) to 2)
                        return@forEach
                    }
                }
                val nameLevDist = name
                    .substring(0, minOf(query.length, name.length))
                    .lev(query)
                if (nameLevDist < threshold) {
                    timeZonesWithDist.add(SearchResultZone(timeZone, id) to nameLevDist)
                    return@forEach
                }

                // ID Match
                when {
                    // not zero, so that lev can have that
                    id.startsWith(query) -> {
                        timeZonesWithDist.add(SearchResultZone(timeZone, id) to 1)
                        return@forEach
                    }

                    id.contains(query) -> {
                        timeZonesWithDist.add(SearchResultZone(timeZone, id) to 2)
                        return@forEach
                    }
                }
                val idLevDist = id
                    .substring(0, minOf(query.length, id.length))
                    .lev(query)
                if (idLevDist < threshold) {
                    timeZonesWithDist.add(SearchResultZone(timeZone, id) to idLevDist)
                    return@forEach
                }
            }

            return@withContext timeZonesWithDist.sortedBy { it.second }.map { it.first }
        }
}

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

package com.sadellie.unitto.data.timezone

import android.icu.text.LocaleDisplayNames
import android.icu.text.TimeZoneNames
import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import androidx.annotation.RequiresApi
import com.sadellie.unitto.data.common.displayName
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
    private val dao: TimeZoneDao,
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
                val tz = TimeZone.getTimeZone(entity.id)
                if (tz.id == "Etc/Unknown") return@forEach
                favorites.add(
                    FavoriteZone(
                        timeZone = tz,
                        position = entity.position,
                        label = entity.label,
                    ),
                )
            }

            favorites
        }

    suspend fun moveTimeZone(
        timeZone: FavoriteZone,
        targetPosition: Int,
    ) = withContext(Dispatchers.IO) {
        dao.moveMove(timeZone.timeZone.id, timeZone.position, targetPosition)
    }

    suspend fun addToFavorites(
        timeZone: TimeZone,
    ) {
        withContext(Dispatchers.IO) {
            dao.addToFavorites(timeZone.id)
        }
    }

    suspend fun removeFromFavorites(
        timeZone: FavoriteZone,
    ) = withContext(Dispatchers.IO) {
        dao.removeFromFavorites(timeZone.timeZone.id)
    }

    suspend fun updateLabel(
        timeZone: FavoriteZone,
        label: String,
    ) = withContext(Dispatchers.IO) {
        dao.updateLabel(timeZone.timeZone.id, label)
    }

    suspend fun filter(
        searchQuery: String,
        locale: ULocale,
    ): List<SearchResultZone> =
        withContext(Dispatchers.IO) {
            val timeZoneNames = TimeZoneNames.getInstance(locale)
            val localeDisplayNames = LocaleDisplayNames.getInstance(locale)

            val favorites = dao.getFavorites().first().map { it.id }
            val timezones = TimeZone.getAvailableIDs()
                .filter { it !in favorites }
                .map { TimeZone.getTimeZone(it) }

            if (searchQuery.isBlank()) {
                return@withContext timezones
                    .map {
                        val displayName = it.displayName(locale)
                        val regionName = it.regionName(timeZoneNames, localeDisplayNames)

                        SearchResultZone(
                            timeZone = it,
                            name = displayName,
                            region = regionName,
                            rank = 0,
                        )
                    }
                    .sortedBy { it.name }
            }

            val query = searchQuery.trim().lowercase()
            val threshold: Int = query.length / 2
            val timeZonesWithDist = mutableSetOf<SearchResultZone>()

            // Don't use map here so that only needed SearchResultZone objects will be created
            timezones.forEach {
                val displayName = it.displayName(locale)
                val regionName = it.regionName(timeZoneNames, localeDisplayNames)

//                // CODE Match
//                if (codeToTimeZoneId[timeZone.id]?.lowercase() == query) {
//                    timeZonesWithDist.add(SearchResultZone(timeZone, id) to 1)
//                    return@forEach
//                }

                val nameMatch = matchProperty(displayName, query, threshold)
                if (nameMatch != null) {
                    timeZonesWithDist.add(
                        SearchResultZone(
                            timeZone = it,
                            name = displayName,
                            region = regionName,
                            rank = nameMatch,
                        ),
                    )
                    return@forEach
                }

                val regionMatch = matchProperty(regionName, query, threshold)
                if (regionMatch != null) {
                    timeZonesWithDist.add(
                        SearchResultZone(
                            timeZone = it,
                            name = displayName,
                            region = regionName,
                            rank = regionMatch,
                        ),
                    )
                    return@forEach
                }
            }

            return@withContext timeZonesWithDist.sortedBy { it.rank }
        }

    private fun matchProperty(
        prop: String,
        query: String,
        levThreshold: Int,
    ): Int? {
        if (prop.startsWith(query, true)) return 0

        if (prop.contains(query, true)) return 1

        val levDist = prop
            .substring(0, minOf(query.length, prop.length))
            .lev(query)
        if (levDist < levThreshold) return levDist

        return null
    }
}

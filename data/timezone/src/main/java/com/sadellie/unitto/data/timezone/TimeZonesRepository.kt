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

import com.sadellie.unitto.data.common.lev
import com.sadellie.unitto.data.database.TimeZoneDao
import com.sadellie.unitto.data.database.TimeZoneEntity
import com.sadellie.unitto.data.model.UnittoTimeZone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeZonesRepository @Inject constructor(
    private val dao: TimeZoneDao
) {
    private val allTimeZones: HashMap<String, UnittoTimeZone> = hashMapOf(
        "zulu_time_zone" to UnittoTimeZone(id = "zulu_time_zone", nameRes = "Zulu Time Zone", offsetSeconds = 0)
    )

    val favoriteTimeZones: Flow<List<UnittoTimeZone>> = dao
        .getAll()
        .map { list ->
            val favorites = mutableListOf<UnittoTimeZone>()
            list.forEach { entity ->
                val foundTimeZone = allTimeZones[entity.id] ?: return@forEach
                val mapped = foundTimeZone.copy(
                    position = entity.position
                )
                favorites.add(mapped)
            }

            favorites
        }

    suspend fun swapTimeZones(from: String, to: String) = withContext(Dispatchers.IO) {
        dao.swap(from, to)

        return@withContext
    }

    suspend fun delete(timeZone: UnittoTimeZone) = withContext(Dispatchers.IO) {
        // Only PrimaryKey is needed
        dao.remove(TimeZoneEntity(id = timeZone.id, position = 0))
    }

    suspend fun filterAllTimeZones(searchQuery: String): List<UnittoTimeZone> =
        withContext(Dispatchers.IO) {
            val query = searchQuery.trim().lowercase()
            val threshold: Int = query.length / 2
            val timeZonesWithDist = mutableListOf<Pair<UnittoTimeZone, Int>>()

            allTimeZones.values.forEach { timeZone ->
                val timeZoneName = timeZone.nameRes

                if (timeZone.code.lowercase() == query) {
                    timeZonesWithDist.add(timeZone to 1)
                    return@forEach
                }

                when {
                    // not zero, so that lev can have that
                    timeZoneName.startsWith(query) -> {
                        timeZonesWithDist.add(timeZone to 1)
                        return@forEach
                    }

                    timeZoneName.contains(query) -> {
                        timeZonesWithDist.add(timeZone to 2)
                        return@forEach
                    }
                }

                val levDist = timeZoneName
                    .substring(0, minOf(query.length, timeZoneName.length))
                    .lev(query)

                if (levDist < threshold) {
                    timeZonesWithDist.add(timeZone to levDist)
                }
            }

            return@withContext timeZonesWithDist.sortedBy { it.second }.map { it.first }
        }

    suspend fun addToFavorites(timeZone: UnittoTimeZone) {
//        UNCOMMENT FOR RELEASE
        dao.insert(
            TimeZoneEntity(
                id = timeZone.id,
                position = System.currentTimeMillis().toInt()
            )
        )
    }
}

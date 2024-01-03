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

package com.sadellie.unitto.data.backup

import com.sadellie.unitto.data.database.TimeZoneDao
import com.sadellie.unitto.data.database.TimeZoneEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

var timeZones = listOf(
    TimeZoneEntity(
        id = "id1",
        position = 9,
        label = "label"
    ),
    TimeZoneEntity(
        id = "id2",
        position = 9,
        label = "label"
    )
)

object FakeTimeZoneDao: TimeZoneDao {
    override fun getFavorites(): Flow<List<TimeZoneEntity>> = flow {
        emit(timeZones)
    }

    override fun getMaxPosition(): Int = 0
    override suspend fun insert(vararg timeZoneEntity: TimeZoneEntity) {
        timeZones += timeZoneEntity
    }
    override suspend fun removeFromFavorites(id: String) {}
    override suspend fun updateLabel(id: String, label: String) {}
    override suspend fun updateDragged(id: String, oldPosition: Int, newPosition: Int) {}
    override suspend fun moveDown(currentPosition: Int, targetPosition: Int) {}
    override suspend fun moveUp(currentPosition: Int, targetPosition: Int) {}
    override suspend fun clear() {
        timeZones = emptyList()
    }
}
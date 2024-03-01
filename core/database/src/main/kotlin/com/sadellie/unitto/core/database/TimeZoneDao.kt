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

package com.sadellie.unitto.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeZoneDao {

  @Query("SELECT * FROM time_zones WHERE position >= 0 ORDER BY position ASC")
  fun getFavorites(): Flow<List<TimeZoneEntity>>

  @Query("SELECT MAX(position) FROM time_zones") fun getMaxPosition(): Int

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(vararg timeZoneEntity: TimeZoneEntity)

  @Transaction
  suspend fun addToFavorites(id: String) {
    insert(TimeZoneEntity(id = id, position = getMaxPosition() + 1))
  }

  @Query("UPDATE time_zones SET position = -1 WHERE id = :id")
  suspend fun removeFromFavorites(id: String)

  @Query("UPDATE time_zones SET label = :label WHERE id = :id")
  suspend fun updateLabel(id: String, label: String)

  @Query("UPDATE time_zones SET position = :newPosition WHERE position = :oldPosition AND id = :id")
  suspend fun updateDragged(id: String, oldPosition: Int, newPosition: Int)

  @Query(
    "UPDATE time_zones SET position = (position - 1) WHERE position > :currentPosition and position <= :targetPosition"
  )
  suspend fun moveDown(currentPosition: Int, targetPosition: Int)

  @Query(
    "UPDATE time_zones SET position = (position + 1) WHERE position >= :targetPosition AND position < :currentPosition"
  )
  suspend fun moveUp(currentPosition: Int, targetPosition: Int)

  @Transaction
  suspend fun updatePosition(id: String, currentPosition: Int, targetPosition: Int) {
    // Very good explanation
    // https://www.c-sharpcorner.com/article/updating-display-order-in-database-with-drag-drop5/
    updateDragged(id, currentPosition, 0)
    if (targetPosition > currentPosition) {
      moveDown(currentPosition, targetPosition)
    } else {
      moveUp(currentPosition, targetPosition)
    }
    updateDragged(id, 0, targetPosition)
  }

  @Query("DELETE FROM time_zones") suspend fun clear()
}

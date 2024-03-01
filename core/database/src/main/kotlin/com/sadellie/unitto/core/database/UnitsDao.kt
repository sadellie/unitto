/*
 * Unitto is a calculator for Android
 * Copyright (c) 2022-2024 Elshan Agaev
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
import kotlinx.coroutines.flow.Flow

@Dao
interface UnitsDao {

  @Query("SELECT * FROM units") fun getAllFlow(): Flow<List<UnitsEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertUnit(unit: UnitsEntity)

  @Query("SELECT * FROM units WHERE unitId == :unitId LIMIT 1")
  suspend fun getById(unitId: String): UnitsEntity?

  @Query("SELECT * FROM units WHERE unitId IN (:unitIds) ORDER BY frequency DESC, is_favorite DESC LIMIT 1")
  suspend fun getByIdsSortedByFavoriteAndFrequencyDesc(unitIds: List<String>): UnitsEntity?

  @Query("SELECT * FROM units WHERE unitId IN (:unitIds)")
  suspend fun getByIds(unitIds: List<String>): List<UnitsEntity>

  @Query("DELETE FROM units") suspend fun clear()
}

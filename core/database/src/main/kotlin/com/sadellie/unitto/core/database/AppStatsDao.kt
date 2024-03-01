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

package com.sadellie.unitto.core.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppStatsDao {
  @Query("SELECT COUNT(*) from units WHERE is_favorite == 1") fun favoriteUnitsSize(): Flow<Int>

  @Query("SELECT SUM(frequency) from units") fun usedUnitsCount(): Flow<Int>

  @Query("SELECT COUNT(*) from time_zones WHERE position >= 0") fun favoriteTimeZones(): Flow<Int>

  @Query("SELECT COUNT(*) from calculator_history") fun savedExpressionCount(): Flow<Int>
}

/*
 * Unitto is a calculator for Android
 * Copyright (c) 2025 Elshan Agaev
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
interface ConverterWidgetUnitPairDao {
  @Query(
    "SELECT * FROM converter_widget_unit_pair WHERE appWidgetId = :appWidgetId ORDER BY position ASC"
  )
  fun getByAppWidgetId(appWidgetId: Int): Flow<List<ConverterWidgetUnitPairEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(converterWidgetUnitPairEntities: List<ConverterWidgetUnitPairEntity>)

  @Query("DELETE FROM converter_widget_unit_pair WHERE appWidgetId = :appWidgetId")
  suspend fun deleteByAppWidgetId(appWidgetId: Int)
}

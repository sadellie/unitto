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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents one Row in units table in database
 *
 * @param isFavorite Is this Unit favorite? Used in a favorites filter in units list
 * @param pairedUnitId Latest unitId of a unit on the right side.
 * @param frequency Show the amount of time this unit was used
 */
@Entity(tableName = "units")
data class UnitsEntity(
  @PrimaryKey val unitId: String,
  @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false,
  @ColumnInfo(name = "paired_unit_id") val pairedUnitId: String? = null,
  @ColumnInfo(name = "frequency") val frequency: Int = 0,
)

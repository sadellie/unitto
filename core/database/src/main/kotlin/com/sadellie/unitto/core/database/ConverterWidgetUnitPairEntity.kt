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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "converter_widget_unit_pair")
data class ConverterWidgetUnitPairEntity(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val entityId: Int = 0,
  @ColumnInfo(name = "appWidgetId") val appWidgetId: Int,
  @ColumnInfo(name = "unitFromId") val unitFromId: String,
  @ColumnInfo(name = "unitToId") val unitToId: String,
  @ColumnInfo(name = "position") val position: Int,
)

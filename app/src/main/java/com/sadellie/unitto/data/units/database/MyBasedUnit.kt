package com.sadellie.unitto.data.units.database

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
class MyBasedUnit(
    @PrimaryKey val unitId: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean?,
    @ColumnInfo(name = "paired_unit_id") val pairedUnitId: String?,
    @ColumnInfo(name = "frequency") val frequency: Int? = 0,
)

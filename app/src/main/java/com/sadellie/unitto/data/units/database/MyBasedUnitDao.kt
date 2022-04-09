package com.sadellie.unitto.data.units.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyBasedUnitDao {
    @Query("SELECT * FROM units")
    suspend fun getAll(): List<MyBasedUnit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(vararg units: MyBasedUnit)
}

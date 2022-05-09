package com.sadellie.unitto.data.units.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyBasedUnit::class], version = 1, exportSchema = false)
abstract class MyBasedUnitDatabase : RoomDatabase() {
    abstract fun myBasedUnitDao(): MyBasedUnitDao
}

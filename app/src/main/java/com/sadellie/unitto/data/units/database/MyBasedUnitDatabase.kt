package com.sadellie.unitto.data.units.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyBasedUnit::class], version = 1, exportSchema = false)
abstract class MyBasedUnitDatabase : RoomDatabase() {
    abstract fun myBasedUnitDao(): MyBasedUnitDao

    companion object {
        @Volatile
        private var INSTANCE: MyBasedUnitDatabase? = null

        fun getDatabase(context: Context): MyBasedUnitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyBasedUnitDatabase::class.java,
                    "unitto_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

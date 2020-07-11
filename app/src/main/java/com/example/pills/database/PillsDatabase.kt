package com.example.pills.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PillsDatabaseEntity::class], version = 1)
abstract class PillsDatabase : RoomDatabase() {
    abstract val dao: PillsDao
}

private var INSTANCE: PillsDatabase? = null

fun getDatabase(context: Context): PillsDatabase {
    synchronized(PillsDatabase::class.java) {
        var instance = INSTANCE
        if (instance == null) {
            instance = Room.databaseBuilder(
                context,
                PillsDatabase::class.java,
                "database"
            ).build()
            INSTANCE = instance
        }
        return instance
    }

}


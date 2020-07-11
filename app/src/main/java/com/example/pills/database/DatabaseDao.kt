package com.example.pills.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PillsDao {
    @Query("select * from pillsdatabaseentity")
    fun getPillsList(): LiveData<List<PillsDatabaseEntity>>

    @Query("select count(*) from pillsdatabaseentity where name=:name")
    suspend fun count(name: String): Int

    @Insert
    suspend fun insert(pill: PillsDatabaseEntity)
}
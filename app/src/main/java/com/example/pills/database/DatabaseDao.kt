package com.example.pills.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PillsDao {
    @Query("select * from pillsdatabaseentity order by LOWER(name) ASC")
    fun getPillsList(): LiveData<List<PillsDatabaseEntity>>

    @Query("select count(*) from pillsdatabaseentity where name=:name")
    suspend fun count(name: String): Int

    @Insert
    suspend fun insert(pill: PillsDatabaseEntity)

    @Delete
    suspend fun delete(pill: PillsDatabaseEntity)

    @Update
    suspend fun update(pill: PillsDatabaseEntity)
}
package com.example.pills

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.pills.database.PillsDatabase
import com.example.pills.database.toPillModel
import com.example.pills.models.Pill
import com.example.pills.models.toDatabaseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PillsRepository(private val database: PillsDatabase) {

    private val pillsList = Transformations.map(database.dao.getPillsList()) { list ->
        list.map { it.toPillModel() }
    }

    suspend fun pillExists(name: String): Boolean {
        return withContext(Dispatchers.IO) {
            database.dao.count(name) == 1
        }
    }

    suspend fun insert(pill: Pill) {
        withContext(Dispatchers.IO) {
            database.dao.insert(pill.toDatabaseEntity())
        }
    }

    fun getPillsList() = pillsList
}
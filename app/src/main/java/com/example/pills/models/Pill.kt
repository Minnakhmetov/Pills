package com.example.pills.models

import com.example.pills.database.PillsDatabaseEntity

data class Pill(
    val name: String,
    val lastModified: Long,
    val requiredDoses: Int,
    val currentDoses: Int = 0
)

fun Pill.toDatabaseEntity(): PillsDatabaseEntity {
    return this.run {
        PillsDatabaseEntity(name, lastModified, requiredDoses, currentDoses)
    }
}
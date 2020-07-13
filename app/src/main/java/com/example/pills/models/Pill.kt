package com.example.pills.models

import com.example.pills.database.PillsDatabaseEntity
import java.util.*

data class Pill(
    val name: String,
    val lastModified: Long,
    val requiredDoses: Int,
    val currentDoses: Int = 0
)

fun Pill.getDoseTakenToday(): Int {
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    return if (cal.timeInMillis > lastModified) 0 else currentDoses
}

fun Pill.toDatabaseEntity(): PillsDatabaseEntity {
    return this.run {
        PillsDatabaseEntity(name, lastModified, requiredDoses, currentDoses)
    }
}
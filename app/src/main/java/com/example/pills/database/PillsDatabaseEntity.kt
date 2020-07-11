package com.example.pills.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pills.models.Pill

@Entity
data class PillsDatabaseEntity(
    @PrimaryKey
    val name: String,
    val lastModified: Long,
    val requiredDoses: Int,
    val currentDoses: Int
)

fun PillsDatabaseEntity.toPillModel() = Pill(
    this.name,
    this.lastModified,
    this.requiredDoses,
    this.currentDoses
)
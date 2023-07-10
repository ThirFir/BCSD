package com.bcsd.bcsd_week3.week12

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey val name: String,
    val meaning: String,
    val createdAt: Long,
)
package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_searches")
data class RecentSearch(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val wordId: Int,
    val queryPashto: String,
    val queryEnglish: String,
    val timestamp: Long = System.currentTimeMillis()
)

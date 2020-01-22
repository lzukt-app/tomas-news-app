package com.example.tomas_news_app.utils.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SourceEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val description: String
)

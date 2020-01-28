package com.example.tomasNewsApp.utils.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    val chipId: Int,
    val sourceId: String,
    val author: String?,
    val title: String?,
    val description: String?,
    @PrimaryKey(autoGenerate = false) val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val favorite: Boolean
)

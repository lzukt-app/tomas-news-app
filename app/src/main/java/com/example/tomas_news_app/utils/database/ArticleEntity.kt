package com.example.tomas_news_app.utils.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val chipId: Int,
    val sourceId: String,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
)

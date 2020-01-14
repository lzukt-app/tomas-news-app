package com.example.tomas_news_app.source

data class SourceListResponse(
    val status: String,
    val sources: List<SourceResponse>
)

data class SourceResponse(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)

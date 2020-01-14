package com.example.tomas_news_app.source

import retrofit2.Call
import retrofit2.http.GET

interface SourceService {
    @GET("/v2/sources?apiKey=57a79eac5a8f44efa2bd3408139b83f3")
    fun getSources(): Call<SourceListResponse>
}
package com.example.tomas_news_app.news

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("/v2/top-headlines?sources=abc-news&apiKey=57a79eac5a8f44efa2bd3408139b83f3")
    fun getSources(): Call<NewsListResponse>
}
package com.example.tomasNewsApp.news

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/top-headlines?apiKey=99a854b9996b4be39a07090624c62e4d")
    fun getTopNewsFromSource(
        @Query("sources") sourceId: String
    ): Call<NewsListResponse>

    @GET("/v2/everything?apiKey=99a854b9996b4be39a07090624c62e4d")
    fun getPopularTodayFromSource(
        @Query("sources") sourceId: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String
    ): Call<NewsListResponse>

    @GET("/v2/everything?apiKey=99a854b9996b4be39a07090624c62e4d")
    fun getNewestFromSource(
        @Query("sources") sourceId: String,
        @Query("from") fromDate: String
    ): Call<NewsListResponse>
}
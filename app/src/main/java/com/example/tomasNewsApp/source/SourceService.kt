package com.example.tomasNewsApp.source

import io.reactivex.Single
import retrofit2.http.GET

interface SourceService {
    @GET("/v2/sources?apiKey=99a854b9996b4be39a07090624c62e4d")
    fun getSources(): Single<SourceListResponse>
}
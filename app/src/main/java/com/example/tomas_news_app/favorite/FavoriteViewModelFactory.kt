package com.example.tomas_news_app.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tomas_news_app.news.NewsService
import com.example.tomas_news_app.utils.database.NewsDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoriteViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        val client = OkHttpClient.Builder()
//            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .client(client)
//            .baseUrl("https://newsapi.org")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val service = retrofit.create(NewsService::class.java)

        return FavoriteViewModel(
            //service,
            NewsDatabase.getInstance(application).articleDao
        ).apply { onCreate() } as T
    }
}

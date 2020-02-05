package com.example.tomasNewsApp.source

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tomasNewsApp.utils.database.NewsDatabase
import com.example.tomasNewsApp.utils.location.LocationManager
import com.google.android.gms.location.LocationServices
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SourceViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(
        application
    ) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val manager = LocationManager(
            LocationServices.getFusedLocationProviderClient(application),
            LocationServices.getSettingsClient(application)
        )

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        val service = retrofit.create(SourceService::class.java)

        return SourceViewModel(
            service,
            NewsDatabase.getInstance(application).sourceDao,
            manager
        ).apply { onCreate() } as T
    }
}
package com.example.tomas_news_app.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tomas_news_app.BuildConfig

class MainViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(
        application
    ) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            application.getSharedPreferences(
                BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE
            )
        ).apply { onCreate() } as T
    }

}
package com.example.tomasNewsApp.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tomasNewsApp.utils.database.NewsDatabase

class FavoriteViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(
            NewsDatabase.getInstance(application).articleDao
        ).apply { onCreate() } as T
    }
}
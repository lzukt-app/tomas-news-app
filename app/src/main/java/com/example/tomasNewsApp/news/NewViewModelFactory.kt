package com.example.tomasNewsApp.news

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tomasNewsApp.NewsApplication
import com.example.tomasNewsApp.utils.database.NewsDatabase

class NewViewModelFactory(private val application: Application, private val sourceId: String) :
    ViewModelProvider.AndroidViewModelFactory(
        application
    ) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val service = (application as NewsApplication).retrofit.create(NewsService::class.java)

        return NewViewModel(
            service,
            sourceId,
            NewsDatabase.getInstance(application).articleDao
        ).apply { onCreate() } as T
    }
}

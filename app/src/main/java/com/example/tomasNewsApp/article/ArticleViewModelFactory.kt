package com.example.tomasNewsApp.article

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tomasNewsApp.news.NewsItem

class ArticleViewModelFactory(
    private val application: Application,
    private val article: NewsItem?
) :
    ViewModelProvider.AndroidViewModelFactory(
        application
    ) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ArticleViewModel(article).apply { onCreate() } as T
}
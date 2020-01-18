package com.example.tomas_news_app.article

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleViewModelFactory(
    private val application: Application,
    private val article: Article
) : ViewModelProvider.AndroidViewModelFactory(
    application
) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(article) as T
    }
}
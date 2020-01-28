package com.example.tomasNewsApp.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomasNewsApp.news.NewsItem

class ArticleViewModel(
    private val article: NewsItem?

) : ViewModel() {

    private val _data = MutableLiveData<NewsItem>()
    val data: LiveData<NewsItem> get() = _data

    fun onCreate() {
        _data.postValue(article)
    }
}
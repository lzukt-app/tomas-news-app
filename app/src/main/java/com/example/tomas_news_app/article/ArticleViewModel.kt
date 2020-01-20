package com.example.tomas_news_app.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomas_news_app.news.NewsItem
import com.example.tomas_news_app.news.NewsService

class ArticleViewModel(
    //private val service: NewsService,
    private val article: NewsItem?

) : ViewModel() {

    private val _data = MutableLiveData<NewsItem>()
    val data: LiveData<NewsItem> get() = _data

    init {
        _data.postValue(article)
    }

}
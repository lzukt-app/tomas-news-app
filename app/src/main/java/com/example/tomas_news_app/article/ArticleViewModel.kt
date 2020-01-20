package com.example.tomas_news_app.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomas_news_app.news.NewsItem

class ArticleViewModel(
    article: NewsItem?

) : ViewModel() {

    private val _data = MutableLiveData<NewsItem>()
    val data: LiveData<NewsItem> get() = _data

    init {
        _data.postValue(article)
    }

}
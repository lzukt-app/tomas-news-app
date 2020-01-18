package com.example.tomas_news_app.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel(
    private val article: Article
) : ViewModel() {

    private val _data = MutableLiveData<Article>()
    val data: LiveData<Article> get() = _data

    init {
        _data.postValue(article)
    }

}
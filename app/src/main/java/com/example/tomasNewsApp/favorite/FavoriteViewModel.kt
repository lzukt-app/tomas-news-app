package com.example.tomasNewsApp.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomasNewsApp.news.NewsItem
import com.example.tomasNewsApp.utils.database.ArticleDao
import kotlin.concurrent.thread

class FavoriteViewModel(
    // private val service: NewsService,
    private val articleDao: ArticleDao

) : ViewModel() {
    private val _data = MutableLiveData<List<NewsItem>>()
    val data: LiveData<List<NewsItem>> get() = _data

    fun onCreate() {
        getArticlesFromDB()
    }

    private fun getArticlesFromDB() {
        thread {
            articleDao.getFavorite()
                .map {
                    NewsItem(
                        it.author,
                        it.title,
                        it.description,
                        it.url,
                        it.urlToImage,
                        it.publishedAt,
                        it.favorite,
                        it.sourceId
                    )
                }
                .let { _data.postValue(it) }
        }
    }

    fun changeArticleFavoriteStatus(article: NewsItem) {
        thread {
            articleDao.changeFavoriteStatus(article.url)
            getArticlesFromDB()
        }
    }
}

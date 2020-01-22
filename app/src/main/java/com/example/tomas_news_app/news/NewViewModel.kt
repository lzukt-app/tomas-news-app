package com.example.tomas_news_app.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomas_news_app.utils.database.ArticleDao
import com.example.tomas_news_app.utils.database.ArticleEntity
import com.example.tomas_news_app.utils.formatDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.thread

class NewViewModel(
    private val service: NewsService,
    private val sourceId: String,
    private val articleDao: ArticleDao

) : ViewModel() {
    private val _data = MutableLiveData<List<NewsItem>>()
    val data: LiveData<List<NewsItem>> get() = _data

    fun onCreate() {
        this.onPopularTodayArticlesSelected()
    }

    fun onPopularTodayArticlesSelected() {
        getArticlesFromDB(1)
        service
            .getTopNewsFromSource(sourceId)
            .enqueue(object : Callback<NewsListResponse> {
                override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<NewsListResponse>,
                    response: Response<NewsListResponse>
                ) {
                    updateArticleData(response,1)
                }
            })
    }

    private fun getArticlesFromDB(chipId: Int) {
        thread {
            articleDao.query(sourceId, chipId)
                .map {
                    NewsItem(
                        it.author,
                        it.title,
                        it.description,
                        it.url,
                        it.urlToImage,
                        it.publishedAt
                    )
                }
                .let { _data.postValue(it) }
        }
    }

    fun onAllTimeArticlesSelected() {
        getArticlesFromDB(2)
        service
            .getPopularTodayFromSource(
                sourceId,
                formatDate(
                    Date(
                        Calendar.getInstance().apply {
                            time = Date()
                            add(Calendar.DAY_OF_YEAR, -10)
                        }.timeInMillis
                    )
                ),
                formatDate(
                    Date(
                        Calendar.getInstance().apply {
                            time = Date()
                            add(Calendar.DAY_OF_YEAR, -5)
                        }.timeInMillis
                    )
                )
            )
            .enqueue(object : Callback<NewsListResponse> {
                override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<NewsListResponse>,
                    response: Response<NewsListResponse>
                ) {
                    updateArticleData(response, 2)
                }
            })
    }

    fun onNewestArticlesSelected() {
        getArticlesFromDB(3)
        service
            .getNewestFromSource(
                sourceId,
                formatDate(Date())
            )
            .enqueue(object : Callback<NewsListResponse> {
                override fun onFailure(call: Call<NewsListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<NewsListResponse>,
                    response: Response<NewsListResponse>
                ) {
                    updateArticleData(response, 3)
                }
            })
    }

    private fun updateArticleData(response: Response<NewsListResponse>, chipId: Int) {
        thread {
            response.body()!!.articles!!
                .map {
                    NewsItem(
                        it.author,
                        it.title,
                        it.description,
                        it.url,
                        it.urlToImage,
                        it.publishedAt
                    )
                }
                .map {
                    ArticleEntity(
                        sourceId = sourceId,
                        chipId = chipId,
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt
                    )
                }
                .also { articleDao.insert(it) }
                .let { articleDao.query(sourceId, chipId) }
                .map {
                    NewsItem(
                        it.author,
                        it.title,
                        it.description,
                        it.url,
                        it.urlToImage,
                        it.publishedAt
                    )
                }
                .let { _data.postValue(it) }
        }
    }


//    private fun getArticles(chipId: Int) {
//        thread {
//            postArticleList(
//                articleDao.get(
//                    chipId,
//                    sourceId
//                ).map {
//                    NewsItem(
//                        it.author,
//                        it.title,
//                        it.description,
//                        it.url,
//                        it.urlToImage,
//                        it.publishedAt
//                    )
//                }
//            )
//        }
//    }
//
//    private fun postArticleList(listArticle: List<NewsItem>) {
//        _data.postValue(listArticle)
//    }
}

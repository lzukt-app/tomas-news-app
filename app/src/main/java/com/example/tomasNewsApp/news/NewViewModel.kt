package com.example.tomasNewsApp.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomasNewsApp.utils.database.ArticleDao
import com.example.tomasNewsApp.utils.database.ArticleEntity
import com.example.tomasNewsApp.utils.formatDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.thread

const val POPULAR_TODAY_CHIP_ID = 1
const val POPULAR_ALL_TIME_CHIP_ID = 2
const val NEWEST_ARTICLE_CHIP_ID = 3
const val DAYS_AMOUNT_10 = 10
const val DAYS_AMOUNT_5 = 5

class NewViewModel(
    private val service: NewsService,
    private val sourceId: String,
    private val articleDao: ArticleDao

) : ViewModel() {

    var chipID = MutableLiveData(POPULAR_TODAY_CHIP_ID)
    val chipid: LiveData<Int> get() = chipID

    private val _data = MutableLiveData<List<NewsItem>>()
    val data: LiveData<List<NewsItem>> get() = _data

    fun onCreate() {
        when (chipid.value) {
            POPULAR_TODAY_CHIP_ID -> this.onPopularTodayArticlesSelected()
            POPULAR_ALL_TIME_CHIP_ID -> this.onAllTimeArticlesSelected()
            else -> this.onNewestArticlesSelected()
        }
    }

    fun onPopularTodayArticlesSelected() {
        getArticlesFromDB(POPULAR_TODAY_CHIP_ID)
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
                    updateArticleData(response, POPULAR_TODAY_CHIP_ID)
                }
            })
    }

    fun onAllTimeArticlesSelected() {
        getArticlesFromDB(POPULAR_ALL_TIME_CHIP_ID)
        service
            .getPopularTodayFromSource(
                sourceId,
                formatDate(
                    Date(
                        Calendar.getInstance().apply {
                            time = Date()
                            add(Calendar.DAY_OF_YEAR, -DAYS_AMOUNT_10)
                        }.timeInMillis
                    )
                ),
                formatDate(
                    Date(
                        Calendar.getInstance().apply {
                            time = Date()
                            add(Calendar.DAY_OF_YEAR, -DAYS_AMOUNT_5)
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
                    updateArticleData(response, POPULAR_ALL_TIME_CHIP_ID)
                }
            })
    }

    fun onNewestArticlesSelected() {
        getArticlesFromDB(NEWEST_ARTICLE_CHIP_ID)
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
                    updateArticleData(response, NEWEST_ARTICLE_CHIP_ID)
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
                        it.publishedAt,
                        it.favorite,
                        it.sourceId
                    )
                }
                .let { _data.postValue(it) }
        }
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
                        it.publishedAt,
                        it.favorite,
                        sourceId
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
                        publishedAt = it.publishedAt,
                        favorite = it.favorite
                    )
                }
                .also { articleDao.deleteAll(sourceId, chipId) }
                .also { articleDao.insert(it) }
                .let { articleDao.query(sourceId, chipId) }
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
            getArticlesFromDB(chipid.value!!)
        }
    }
}

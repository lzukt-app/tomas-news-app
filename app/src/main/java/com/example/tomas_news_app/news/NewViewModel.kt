package com.example.tomas_news_app.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomas_news_app.utils.formatDate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NewViewModel(
    private val service: NewsService,
    private val sourceId: String

) : ViewModel() {
    private val _data = MutableLiveData<List<NewsItem>>()
    val data: LiveData<List<NewsItem>> get() = _data

    init {
        this.onPopularTodayArticlesSelected()
    }

    fun onPopularTodayArticlesSelected() {
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
                    //Log.d("TEST2", "${response.body()!!.articles!!.size}")
                    response.body()!!.articles!!
                        .map {
                            NewsItem(it.urlToImage, it.title, it.description, it.publishedAt)
                        }
                        .let { _data.postValue(it) }
                }
            })
    }

    fun onAllTimeArticlesSelected() {
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
                    //Log.d("TEST2", "${response.body()}")
                    response.body()!!.articles
                        ?.map {
                            NewsItem(it.urlToImage, it.title, it.description, it.publishedAt)
                        }
                        .let { _data.postValue(it) }
                }
            })
    }

    fun onNewestArticlesSelected() {
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
                    response.body()!!.articles
                        ?.map {
                            NewsItem(it.urlToImage, it.title, it.description, it.publishedAt)
                        }
                        .let { _data.postValue(it) }
                }
            })
    }
}

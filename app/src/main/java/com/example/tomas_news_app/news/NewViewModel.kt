package com.example.tomas_news_app.news

import android.util.Log
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
        //Log.d("TEST2", "${sourceId!!}")
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
                    //Log.d("TEST2", "${response.body()!!.articles!!}")
                    response.body()!!.articles!!
                        .map {
                            NewsItem(it.author, it.title, it.description, it.url, it.urlToImage, it.publishedAt)
                        }
                        .let { _data.postValue(it) }
                }
            })
    }

    fun onAllTimeArticlesSelected() {
        //Log.d("TEST2", "${sourceId!!}")
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
                    //Log.d("TEST2", "${response.body()!!.articles!!}")
                    response.body()!!.articles
                        ?.map {
                            NewsItem(it.author, it.title, it.description, it.url, it.urlToImage, it.publishedAt)
                        }
                        .let { _data.postValue(it) }
                }
            })
    }

    fun onNewestArticlesSelected() {
        //Log.d("TEST2", "${sourceId!!}")
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
                    //Log.d("TEST2", "${response.body()!!.articles!!}")
                    response.body()!!.articles
                        ?.map {
                            NewsItem(it.author, it.title, it.description, it.url, it.urlToImage, it.publishedAt)
                        }
                        .let { _data.postValue(it) }
                }
            })
    }
}

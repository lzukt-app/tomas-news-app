package com.example.tomas_news_app.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewViewModel (
    val service: NewsService

) : ViewModel() {
    private val _data = MutableLiveData<List<NewsItem>>()
    val data: LiveData<List<NewsItem>> get() = _data
    init {
        service.getSources().enqueue(object : Callback<NewsListResponse> {
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
                        NewsItem(0, it.title, it.description, it.publishedAt)
                    }
                    .let { _data.postValue(it) }
            }

        })
    }
}

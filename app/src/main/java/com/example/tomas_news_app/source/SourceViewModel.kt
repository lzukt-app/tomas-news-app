package com.example.tomas_news_app.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourceViewModel(
    private val service: SourceService

) : ViewModel() {
    private val _data = MutableLiveData<List<SourceItem>>()
    val data: LiveData<List<SourceItem>> get() = _data

    fun onCreate() {
        service.getSources().enqueue(object : Callback<SourceListResponse> {
            override fun onFailure(call: Call<SourceListResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<SourceListResponse>,
                response: Response<SourceListResponse>
            ) {
                response.body()!!.sources
                    .map { SourceItem(it.id, it.name, it.description) }
                    .let { _data.postValue(it.sortedBy(SourceItem::title)) }
            }

        })
    }

    private var sort = false

    fun sortSourceList() {
        sort = !sort
        _data.postValue(
            when (sort) {
                false ->  (_data.value ?: listOf()).sortedBy { it.title }
                else -> (_data.value ?: listOf()).sortedByDescending { it.title }
            }
        )
    }
}
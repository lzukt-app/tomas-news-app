package com.example.tomas_news_app.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomas_news_app.utils.database.SourceDao
import com.example.tomas_news_app.utils.database.SourceEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class SourceViewModel(
    private val service: SourceService,
    private val sourceDao: SourceDao

) : ViewModel() {
    private val _data = MutableLiveData<List<SourceItem>>()
    val data: LiveData<List<SourceItem>> get() = _data

    fun onCreate() {
        thread {
            sourceDao.query()
                .map { SourceItem(it.id, it.title, it.description) }
                .let { _data.postValue(it) }
        }
        service
            .getSources()
            .enqueue(object : Callback<SourceListResponse> {
                override fun onFailure(call: Call<SourceListResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<SourceListResponse>,
                    response: Response<SourceListResponse>
                ) {
                    thread {
                        response.body()!!.sources
                            .map { SourceItem(it.id, it.name, it.description) }
                            .map { SourceEntity(it.id, it.title, it.description) }
                            .also { sourceDao.insert(it) }
                            .let { sourceDao.query() }
                            .map { SourceItem(it.id, it.title, it.description) }
                            .let { _data.postValue(it) }
                    }
                }

            })
    }

    private var sort = false

    fun sortSourceList() {
        sort = !sort
        _data.postValue(
            when (sort) {
                false -> (_data.value ?: listOf()).sortedBy { it.title }
                else -> (_data.value ?: listOf()).sortedByDescending { it.title }
            }
        )
    }
}
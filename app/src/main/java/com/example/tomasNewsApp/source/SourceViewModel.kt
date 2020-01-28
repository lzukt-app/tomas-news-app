package com.example.tomasNewsApp.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomasNewsApp.utils.database.SourceDao
import com.example.tomasNewsApp.utils.database.SourceEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class SourceViewModel(
    private val service: SourceService,
    private val sourceDao: SourceDao

) : ViewModel() {

    var sortID = MutableLiveData(true)
    val sortid: LiveData<Boolean> get() = sortID

    private val _data = MutableLiveData<List<SourceItem>>()
    val data: LiveData<List<SourceItem>> get() = _data

    fun onCreate() {
        thread {
            if (sortid.value == true) {
                sourceDao.query()
                    .map { SourceItem(it.id, it.title, it.description) }
                    .let { _data.postValue(it) }
            } else {
                sourceDao.queryDESC()
                    .map { SourceItem(it.id, it.title, it.description) }
                    .let { _data.postValue(it) }
            }
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
                        (if (sortID.value == true) {
                            this.let { sourceDao.query() }
                        } else {
                            this.let { sourceDao.queryDESC() }
                        })
                            .map { SourceItem(it.id, it.title, it.description) }
                            .let { _data.postValue(it) }
                    }
                }
            })
    }

    fun sortSourceList() {
        _data.postValue(
            if (sortid.value == true) (_data.value ?: listOf()).sortedBy { it.title }
            else (_data.value ?: listOf()).sortedByDescending { it.title }
        )
    }
}
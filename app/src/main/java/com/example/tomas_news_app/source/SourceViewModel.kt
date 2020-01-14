package com.example.tomas_news_app.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SourceViewModel : ViewModel() {
    private val _data = MutableLiveData<List<SourceItem>>()
    val data: LiveData<List<SourceItem>> get() = _data

    init {
        _data.postValue(
            listOf(
                SourceItem(
                    "title 1",
                    "description"
                ),
                SourceItem(
                    "title 2",
                    "description"
                ),
                SourceItem(
                    "title 3",
                    "description"
                ),
                SourceItem(
                    "title 4",
                    "description"
                ),
                SourceItem(
                    "title 5",
                    "description"
                )
            )
        )
    }
}
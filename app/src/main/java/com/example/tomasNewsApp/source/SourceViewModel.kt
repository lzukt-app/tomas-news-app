package com.example.tomasNewsApp.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomasNewsApp.utils.database.SourceDao
import com.example.tomasNewsApp.utils.database.SourceEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

const val DEBOUNCE_VALUE: Long = 200

class SourceViewModel(
    private val service: SourceService,
    private val sourceDao: SourceDao

) : ViewModel() {
    private val disposables = CompositeDisposable()

    var sortID = MutableLiveData(true)
    val sortid: LiveData<Boolean> get() = sortID

    private val _data = MutableLiveData<List<SourceItem>>()
    val data: LiveData<List<SourceItem>> get() = _data

    val publishSubject = PublishSubject.create<String>()

    fun onCreate() {
        val disposable = service.getSources()
            .map { response -> response.sources }
            .map { sources -> sources.map(::toItem) }
            .map { items -> items.map { this.toEntity(it) } }
            .flatMapCompletable { entities ->
                sourceDao.insert(entities)
            }
            .andThen(
                (if (sortID.value == true) {
                    this.let { sourceDao.query() }
                } else {
                    this.let { sourceDao.queryDESC() }
                })
            )
            .map { entities -> entities.map(::toItem) }
            .subscribe(
                { items -> _data.postValue(items) },
                { it.printStackTrace() }
            )

        val publishDisposable = publishSubject.filter { it.length > 2 }
            .debounce(DEBOUNCE_VALUE, TimeUnit.MILLISECONDS)
            .flatMapSingle { query ->
                sourceDao.getSourcesBySearch(query)
                    .subscribeOn(Schedulers.io())
            }
            .subscribe { it ->
                postArticleListToData(it.map { SourceItem(it.id, it.title, it.description) })
            }
        disposables.add(disposable)
        disposables.add(publishDisposable)
    }

    fun onSearch(searchText: String) {
        // The first way to search
//        val disposable =sourceDao.getSourcesBySearch(searchText)
//            .subscribeOn(Schedulers.io())
//            .subscribe { it ->
//                postArticleListToData(it.map { SourceItem(it.id, it.title, it.description) })
//            }
//            disposables.add(disposable)
        // The second way to search
        publishSubject.onNext(searchText)
    }

    private fun toItem(it: SourceEntity): SourceItem {
        return SourceItem(
            it.id,
            it.title,
            it.description
        )
    }

    private fun toItem(it: SourceResponse): SourceItem {
        return SourceItem(
            it.id,
            it.name,
            it.description
        )
    }

    private fun toEntity(it: SourceItem): SourceEntity {
        return SourceEntity(
            it.id, it.title, it.description
        )
    }

    fun sortSourceList() {
        _data.postValue(
            if (sortid.value == true) (_data.value ?: listOf()).sortedBy { it.title }
            else (_data.value ?: listOf()).sortedByDescending { it.title }
        )
    }

    private fun postArticleListToData(sourceList: List<SourceItem>) {
        _data.postValue(sourceList)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
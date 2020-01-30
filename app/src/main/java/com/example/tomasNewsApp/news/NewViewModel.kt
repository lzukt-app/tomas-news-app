package com.example.tomasNewsApp.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tomasNewsApp.utils.database.ArticleDao
import com.example.tomasNewsApp.utils.database.ArticleEntity
import com.example.tomasNewsApp.utils.formatDate
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
    private val disposables = CompositeDisposable()

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
        val disposable = service.getTopNewsFromSource(sourceId)
            .map { response -> response.articles }
            .map { articles -> articles.map(this::toItem) }
            .map { items -> items.map { this.toEntity(it, POPULAR_TODAY_CHIP_ID) } }
            .flatMapCompletable { entities -> update(entities, POPULAR_TODAY_CHIP_ID) }
            .andThen(articleDao.query(sourceId, POPULAR_TODAY_CHIP_ID))
            .map { entities -> entities.map(this::toItem) }
            .subscribe(
                { items -> _data.postValue(items) },
                { it.printStackTrace() }
            )
        disposables.add(disposable)
    }

    fun onAllTimeArticlesSelected() {
        getArticlesFromDB(POPULAR_ALL_TIME_CHIP_ID)
        val disposable = service.getPopularTodayFromSource(
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
            .map { response -> response.articles }
            .map { articles -> articles.map(this::toItem) }
            .map { items -> items.map { this.toEntity(it, POPULAR_ALL_TIME_CHIP_ID) } }
            .flatMapCompletable { entities -> update(entities, POPULAR_ALL_TIME_CHIP_ID) }
            .andThen(articleDao.query(sourceId, POPULAR_ALL_TIME_CHIP_ID))
            .map { entities -> entities.map(this::toItem) }
            .subscribe(
                { items -> _data.postValue(items) },
                { it.printStackTrace() }
            )
        disposables.add(disposable)
    }

    fun onNewestArticlesSelected() {
        getArticlesFromDB(NEWEST_ARTICLE_CHIP_ID)
        val disposable = service.getNewestFromSource(
            sourceId,
            formatDate(Date())
        )
            .map { response -> response.articles }
            .map { articles -> articles.map(this::toItem) }
            .map { items -> items.map { this.toEntity(it, NEWEST_ARTICLE_CHIP_ID) } }
            .flatMapCompletable { entities -> update(entities, NEWEST_ARTICLE_CHIP_ID) }
            .andThen(articleDao.query(sourceId, NEWEST_ARTICLE_CHIP_ID))
            .map { entities -> entities.map(this::toItem) }
            .subscribe(
                { items -> _data.postValue(items) },
                { it.printStackTrace() }
            )
        disposables.add(disposable)
    }

    private fun getArticlesFromDB(chipId: Int) {
        val disposable = articleDao.query(sourceId, chipId).subscribeOn(Schedulers.io())
            .map { list -> toList(list) }
            .subscribe { list -> _data.postValue(list) }
        disposables.add(disposable)
    }

    private fun update(
        articles: List<ArticleEntity>,
        chipId: Int
    ): Completable =
        articleDao.deleteAll(sourceId, chipId)
            .andThen(articleDao.insert(articles))

    private fun toList(it: List<ArticleEntity>): List<NewsItem> {
        return it.map {
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
    }

    private fun toItem(it: ArticleEntity): NewsItem {
        return NewsItem(
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

    private fun toItem(it: ArticleResponse): NewsItem {
        return NewsItem(
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

    private fun toEntity(it: NewsItem, chipId: Int): ArticleEntity {
        return ArticleEntity(
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

    fun changeArticleFavoriteStatus(article: NewsItem) {
        thread {
            articleDao.changeFavoriteStatus(article.url)
            getArticlesFromDB(chipid.value!!)
        }
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}

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
        val disposable = service
            .getTopNewsFromSource(sourceId)
            .map { response -> response.articles }
            .map { articles -> articles.map(this::toItem) }
            .map { items -> items.map(this::toEntity) }
            .flatMapCompletable { entities -> update(entities, POPULAR_TODAY_CHIP_ID) }
            .andThen(articleDao.query(sourceId, POPULAR_TODAY_CHIP_ID))
            .map { entities -> entities.map(this::toItem) }
            .subscribe(
                { items -> _data.postValue(items) },
                { it.printStackTrace() }
            )
        disposables.add(disposable)
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

    private fun toEntity(it: NewsItem): ArticleEntity {
        return ArticleEntity(
            sourceId = sourceId,
            chipId = POPULAR_TODAY_CHIP_ID,
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            favorite = it.favorite
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

    private fun update(
        articles: List<ArticleEntity>,
        chipId: Int
    ): Completable =
        articleDao.deleteAll(sourceId, chipId)
            .andThen(articleDao.insert(articles))

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
            .subscribe({ response ->
                updateArticleData(response, POPULAR_ALL_TIME_CHIP_ID)
            })
    }

    fun onNewestArticlesSelected() {
        getArticlesFromDB(NEWEST_ARTICLE_CHIP_ID)
        service
            .getNewestFromSource(
                sourceId,
                formatDate(Date())
            ).subscribe { response -> updateArticleData(response, NEWEST_ARTICLE_CHIP_ID) }
    }

    private fun getArticlesFromDB(chipId: Int) {
        articleDao.query(sourceId, chipId).subscribeOn(Schedulers.io())
            .map { list ->
                list.map {
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
            .subscribe { list -> _data.postValue(list) }
    }

    private fun updateArticleData(response: NewsListResponse, chipId: Int) {
        response.articles!!

//                .map {
//                    NewsItem(
//                        it.author,
//                        it.title,
//                        it.description,
//                        it.url,
//                        it.urlToImage,(
//                        it.publishedAt,
//                        it.favorite,
//                        it.sourceId
//                    )
//                }
//                .let { _data.postValue(it) }
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

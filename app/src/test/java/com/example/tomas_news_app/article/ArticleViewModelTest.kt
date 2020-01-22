package com.example.tomas_news_app.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.tomas_news_app.news.NewsItem
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class ArticleViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun init_firstTime_showArticle() {
        val article = NewsItem(
            "test",
            "test",
            "test",
            "test",
            "test",
            "publishedAt"
        )
        val viewModel = ArticleViewModel(article)

        viewModel.onCreate()

        val observer = mock<Observer<in NewsItem>>()
        viewModel.data.observeForever(observer)
        verify(observer).onChanged(article)
        //==
        Assert.assertEquals(
            article,
            viewModel.data.value
        )
    }
}
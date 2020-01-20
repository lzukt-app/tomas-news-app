package com.example.tomas_news_app.article

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArticleViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun init_firstTime_showArticle() {

    }
}
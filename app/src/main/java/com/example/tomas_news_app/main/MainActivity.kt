package com.example.tomas_news_app.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tomas_news_app.R
import com.example.tomas_news_app.about.AboutFragment
import com.example.tomas_news_app.article.ArticleFragment
import com.example.tomas_news_app.favorite.FavoriteFragment
import com.example.tomas_news_app.news.NewsItem
import com.example.tomas_news_app.news.NewsListFragment
import com.example.tomas_news_app.source.SourceItem
import com.example.tomas_news_app.source.SourceListFragment
import com.example.tomas_news_app.tutorial.TutorialActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener { onBottomNavigationEvent(it) }

        viewModel = ViewModelProviders.of(
            this,
            MainViewModelFactory(application)
        )
            .get(MainViewModel::class.java)

        viewModel.showTutorial.observe(this, Observer { newData ->
            this.showTutorial()
        })

        if (savedInstanceState == null) {
            this.showSource()
        }
    }

    private fun onBottomNavigationEvent(it: MenuItem): Boolean {
        return when (it.itemId) {
            R.id.bottom_navigation_source -> {
                this.showSource()
                true
            }
            R.id.bottom_navigation_favorite -> {
                this.showFavorite()
                true
            }
            else -> {
                this.showAbout()
                true
            }
        }
    }

    private fun showTutorial() {
        startActivity(Intent(this, TutorialActivity::class.java))
    }

    private fun showSource() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, SourceListFragment.newInstance())
            .commit()
    }

    fun showNews(source: SourceItem) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, NewsListFragment.newInstance(source))
            .commit()
    }

    fun showArticle(article: NewsItem) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.container, ArticleFragment.newInstance(article)
            )
            .commit()
    }

    fun showFavorite() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, FavoriteFragment.newInstance())
            .commit()
    }

    private fun showAbout() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, AboutFragment.newInstance())
            .commit()
    }


}

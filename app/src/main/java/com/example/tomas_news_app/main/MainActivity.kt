package com.example.tomas_news_app.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tomas_news_app.R
import com.example.tomas_news_app.about.AboutFragment
import com.example.tomas_news_app.news.NewsListFragment
import com.example.tomas_news_app.source.SourceItem
import com.example.tomas_news_app.source.SourceListFragment
import com.example.tomas_news_app.tutorial.TutorialActivity

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProviders.of(this,
            MainViewModelFactory(application)
        )
            .get(MainViewModel::class.java)

        viewModel.showTutorial.observe(this, Observer { newData ->
            this.showTutorial()
        })


        this.showSource()
        //this.showAbout()
    }

    private fun showTutorial() {
        startActivity(Intent(this, TutorialActivity::class.java))
    }

    private fun showSource() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SourceListFragment.newInstance())
            .commit()
    }

    fun showNews(source: SourceItem) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, NewsListFragment.newInstance())
            .commit()
    }

    private fun showAbout() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AboutFragment.newInstance())
            .commit()
    }


}
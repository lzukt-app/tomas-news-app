package com.example.tomas_news_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                TutorialItemFragment.newInstance(getString(R.string.tutorial_first_page_label), 0)
            )
            .commit()
    }

    fun showNext(page: Int) {
        if (page == 10) {
            finish()
            return
        }
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                TutorialItemFragment.newInstance("second tutorial text, not key", page + 1)
            )
            .commit()
    }
}

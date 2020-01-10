package com.example.tomas_news_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val adapter = TutorialPagerAdapter(supportFragmentManager, resources)
        viewpager.adapter = adapter

    }

    fun showNext(pagePosition: Int) {
        if (pagePosition > LAST_PAGE_INDEX) {
            finish()
            return
        } else {
            viewpager.setCurrentItem(pagePosition)
        }
    }

    companion object {
        const val LAST_PAGE_INDEX: Int = 2
    }
}

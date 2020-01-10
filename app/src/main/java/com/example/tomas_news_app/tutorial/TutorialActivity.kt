package com.example.tomas_news_app.tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tomas_news_app.R
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val adapter = TutorialPagerAdapter(
            supportFragmentManager,
            resources
        )
        viewpager.adapter = adapter

    }

    fun showNext() {
        if (viewpager.currentItem >= viewpager.adapter!!.count - 1) {
            finish()
            return
        } else {
            viewpager.setCurrentItem(viewpager.currentItem + 1)
        }
    }

}

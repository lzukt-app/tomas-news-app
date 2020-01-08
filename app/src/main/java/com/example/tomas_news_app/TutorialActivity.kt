package com.example.tomas_news_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, TutorialItemFragment())
            .commit()
    }

    fun showNext() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, TutorialItemFragment2())
            .commit()
    }
}

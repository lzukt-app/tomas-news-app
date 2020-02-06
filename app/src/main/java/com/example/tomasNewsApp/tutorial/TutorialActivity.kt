package com.example.tomasNewsApp.tutorial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tomasNewsApp.R
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

    companion object {
        fun createIntent(context: Context) = Intent(context, TutorialActivity::class.java)
    }
}
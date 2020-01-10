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
                TutorialItemFragment.newInstance(
                    R.drawable.news_img_0,
                    getString(R.string.tutorial_first_page_label),
                    1
                )
            )
            .commit()
    }

    fun showNext(currentPage: Int) {
        if (currentPage >= LAST_PAGE) {
            finish()
            return
        }

        val tutorialImgList =
            mutableListOf(R.drawable.news_img_0, R.drawable.news_img_1, R.drawable.news_img_2)

        val fragment = TutorialItemFragment.newInstance(
            tutorialImgList[currentPage],
            getString(R.string.tutorial_second_page_label),
            currentPage + 1
        )

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container,
                fragment
            )
            .commit()
    }

    companion object {
        const val LAST_PAGE: Int = 3
    }
}

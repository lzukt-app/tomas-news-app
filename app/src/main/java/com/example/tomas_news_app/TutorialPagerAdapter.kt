package com.example.tomas_news_app

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TutorialPagerAdapter(fragmentManager: FragmentManager, private val resources: Resources): FragmentPagerAdapter(fragmentManager) {
    private val tutorialImgList =
        listOf(R.drawable.news_img_0, R.drawable.news_img_1, R.drawable.news_img_2)

    private val tutorialTxtList =
        listOf(resources.getString(R.string.tutorial_first_page_label), resources.getString(R.string.tutorial_second_page_label), resources.getString(R.string.tutorial_last_page_label))

    private val tutorialBtnList =
        listOf(resources.getString(R.string.tutorial_button), resources.getString(R.string.tutorial_button), resources.getString(R.string.tutorial_button_last))

    override fun getItem(position: Int): Fragment {

        val config = TutorialScreenConfig(
            tutorialImage = tutorialImgList[position],
            tutorialText = tutorialTxtList[position],
            tutorialButton = tutorialBtnList[position],
            page = position
        )

        return TutorialItemFragment.newInstance(
            config
        )
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    companion object {
        const val PAGE_COUNT: Int = 3
    }
}
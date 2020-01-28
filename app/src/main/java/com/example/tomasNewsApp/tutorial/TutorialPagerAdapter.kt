package com.example.tomasNewsApp.tutorial

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tomasNewsApp.R

class TutorialPagerAdapter(
    fragmentManager: FragmentManager,
    resources: Resources
) : FragmentPagerAdapter(fragmentManager) {
    private val configs = listOf(
        TutorialScreenConfig(
            R.mipmap.news_img_0,
            resources.getString(R.string.tutorial_first_page_label),
            resources.getString(R.string.tutorial_button)
        ),
        TutorialScreenConfig(
            R.mipmap.news_img_1,
            resources.getString(R.string.tutorial_second_page_label),
            resources.getString(R.string.tutorial_button)
        ),
        TutorialScreenConfig(
            R.mipmap.news_img_2,
            resources.getString(R.string.tutorial_last_page_label),
            resources.getString(R.string.tutorial_button_last)
        )
    )

    override fun getItem(position: Int): Fragment {

        return TutorialItemFragment.newInstance(
            configs[position]
        )
    }

    override fun getCount(): Int = configs.size
}
package com.example.tomas_news_app

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class TutorialScreenConfig(
    val tutorialImage: Int,
    val tutorialText: String,
    val tutorialButton: String
) :Parcelable
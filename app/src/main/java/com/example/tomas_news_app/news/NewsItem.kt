package com.example.tomas_news_app.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class NewsItem (
    //val urlToImage: String?,
    //val title: String?,
    //val description: String?,
    //val datetime: String
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
) : Parcelable
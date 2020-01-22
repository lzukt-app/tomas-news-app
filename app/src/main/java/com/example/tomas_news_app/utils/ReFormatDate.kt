package com.example.tomas_news_app.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun reFormatDate(dateTime: String, outputFormat: String): String {
    val parseDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    val displayDateFormatter = SimpleDateFormat(outputFormat, Locale.US)
    val date = parseDateFormatter.parse(dateTime.substring(0, 19))
    //Log.d("TEST2", "${dateTime.substring(0,19)!!}")
    return displayDateFormatter.format(date)
}


fun formatDate(date: Date): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
}
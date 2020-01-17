package com.example.tomas_news_app.utils

import java.text.SimpleDateFormat
import java.util.*

fun reFormatDate(dateTime: String): String {
    val parseDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    val displayDateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    val date = parseDateFormatter.parse(dateTime.substring(1, 19))
    return displayDateFormatter.format(date)
}


fun formatDate(date: Date): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
}
package com.example.tomasNewsApp.utils

import java.text.SimpleDateFormat
import java.util.*

const val SUBSTRING_START_INDEX = 0
const val SUBSTRING_END_INDEX = 19

fun reFormatDate(dateTime: String, outputFormat: String): String {
    val parseDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    val displayDateFormatter = SimpleDateFormat(outputFormat, Locale.US)
    val date =
        parseDateFormatter.parse(dateTime.substring(SUBSTRING_START_INDEX, SUBSTRING_END_INDEX))
    // Log.d("TEST2", "${dateTime.substring(0,19)!!}")
    return displayDateFormatter.format(date)
}

fun formatDate(date: Date): String = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
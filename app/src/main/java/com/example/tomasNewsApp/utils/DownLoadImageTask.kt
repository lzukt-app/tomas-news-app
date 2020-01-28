package com.example.tomasNewsApp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import java.io.IOException
import java.net.URL

@Deprecated("Old example do not use")
class DownLoadImageTask(internal val imageView: ImageView) :
    AsyncTask<String, Void, Bitmap?>() {

    override fun doInBackground(vararg urls: String): Bitmap? {
        val urlOfImage = urls[0]
        return try {
            val inputStream = URL(urlOfImage).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (cause: IOException) { // Catch the download exception
            Log.d("error", cause.message.orEmpty())
            // cause.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        if (result != null) {
            Toast.makeText(imageView.context, "download success", Toast.LENGTH_SHORT).show()
            imageView.setImageBitmap(result)
        } else {
            Toast.makeText(imageView.context, "Error downloading", Toast.LENGTH_SHORT).show()
        }
    }
}
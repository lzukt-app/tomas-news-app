package com.example.tomasNewsApp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tomasNewsApp.main.MainActivity
import com.example.tomasNewsApp.tutorial.TutorialActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

private const val MY_CHANEL_CODE = 4

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (NotificationManagerCompat.from(this).getNotificationChannel(getString(R.string.myChannel)) == null) {
                NotificationManagerCompat.from(this).createNotificationChannel(
                    NotificationChannel(
                        "myChannel",
                        getString(R.string.common_notification_channel_general),
                        NotificationManager.IMPORTANCE_HIGH
                    )
                )
            }
        }
        val disposable = Observable.timer(2, TimeUnit.SECONDS).subscribe {
            val notification =
                NotificationCompat.Builder(this, getString(R.string.myChannel)).apply {
                    setContentTitle("It's a notification")
                    setContentText("text!!!")
                    setSmallIcon(R.drawable.ic_launcher_foreground)
                    // Add button
                    addAction(
                        R.drawable.ic_launcher_foreground, "SHOW", PendingIntent.getActivity(
                            this@NewsApplication,
                            MY_CHANEL_CODE,
                            MainActivity.createIntent(this@NewsApplication),
                            0
                        )
                    )
                    // Add action
                    setContentIntent(
                        PendingIntent.getActivity(
                            this@NewsApplication,
                            MY_CHANEL_CODE,
                            TutorialActivity.createIntent(this@NewsApplication),
                            0
                        )
                    )
                }.build()
            NotificationManagerCompat.from(this).notify(1, notification)
        }
    }
}
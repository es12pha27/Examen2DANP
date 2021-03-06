package com.example.notificationexamen2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    companion object {
        private const val TAG = "FCM Notification"
        const val DEFAULT_NOTIFICATION_ID = 0
    }

    override fun onNewToken(token: String) {
        Log.e(TAG, "token: $token")
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initNotificationChannel(notificationManager)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        var title = message.notification?.title
        var body = message.notification?.body
        val ctx: Context = this

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        var notificationBuilder = if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            NotificationCompat.Builder(applicationContext, "1")
        } else {
            NotificationCompat.Builder(applicationContext)
        }

        /* Custom layout */
        val smallLayout = RemoteViews(ctx.packageName, R.layout.notification_small)
        smallLayout.setTextViewText(R.id.notification_title, title)
        smallLayout.setTextViewText(R.id.notification_body, body)

        notificationBuilder = notificationBuilder
            .setCustomContentView(smallLayout)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
        initNotificationChannel(notificationManager)
        notificationManager.notify(DEFAULT_NOTIFICATION_ID, notificationBuilder.build())

    }


    private fun initNotificationChannel(notificationManager: NotificationManager){
        if(Build.VERSION_CODES.O <= Build.VERSION.SDK_INT){
            var channel = notificationManager.getNotificationChannel("1")
            if(channel == null){
                channel = NotificationChannel("1", "Default", NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
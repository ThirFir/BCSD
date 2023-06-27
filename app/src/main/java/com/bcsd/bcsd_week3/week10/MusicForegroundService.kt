package com.bcsd.bcsd_week3.week10

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bcsd.bcsd_week3.R

class MusicForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MusicForegroundService", "onStartCommand")
        start(intent?.getStringExtra("title"), intent?.getStringExtra("artist"))
        return START_STICKY
    }

    private fun start(title: String?, artist: String?) {
        try {
            startForeground(NOTIFICATION_ID, generateForegroundNotification(title, artist))
        } catch (e: SecurityException) {

        }
    }

    private fun generateForegroundNotification(title: String?, artist: String?): Notification {
        val mainIntent = Intent(this, Week10Activity::class.java)
        val mainPendingIntent = PendingIntent
            .getActivity(
                this,
                NOTIFICATION_ID,
                mainIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val channelId = getString(R.string.channel_id)

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_music_video_48)
            .setContentTitle(title)
            .setContentText(artist)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(mainPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .also {
                val notificationManagerCompat = NotificationManagerCompat.from(this)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channelName = getString(R.string.channel_name)
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(channelId, channelName, importance)
                    notificationManagerCompat.createNotificationChannel(channel)
                }   // if SDK version >= Oreo(26), create notification channel
            }.build()
    }
}

private const val NOTIFICATION_ID = 1
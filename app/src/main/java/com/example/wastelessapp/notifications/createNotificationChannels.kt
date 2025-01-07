package com.example.wastelessapp.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.wastelessapp.MainActivity
import com.example.wastelessapp.database.preferences.SettingsManager


fun createNotificationChannels(context: Context) {
    val notificationSoundsList = NotificationSounds.list
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    for (soundName in notificationSoundsList) {

        val channelId = "notification_channel_$soundName"


        val channelName = "$soundName Notifications"
        val channelDescription = "Notifications with $soundName sound"

        println("android.resource://${context.packageName}/raw/${soundName.lowercase()}")
        val soundUri = Uri.parse("android.resource://${context.packageName}/raw/${soundName.lowercase()}")


        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = channelDescription
            setSound(soundUri, audioAttributes)
        }

        notificationManager.createNotificationChannel(channel)
        println("Created channel: $channelId")
    }
}

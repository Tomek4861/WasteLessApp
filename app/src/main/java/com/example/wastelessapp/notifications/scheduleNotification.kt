package com.example.wastelessapp.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

fun scheduleNotification(
    context: Context,
    hour: Int,
    minute: Int,
    dayOffset: Int = 0
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        add(Calendar.DAY_OF_YEAR, dayOffset)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }

    if (dayOffset == 0 && calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("title", "Scheduled Notification")
        putExtra("message", "This is your notification for the scheduled time.")
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        calendar.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

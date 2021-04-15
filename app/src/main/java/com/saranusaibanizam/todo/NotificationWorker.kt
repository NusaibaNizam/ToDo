package com.saranusaibanizam.todo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


class NotificationWorker(val context: Context, val param: WorkerParameters):Worker(context, param) {
    override fun doWork(): Result {
        val name=inputData.getString("name")
        sendNotification(context, name)

        return Result.success()
    }

    private fun sendNotification(context: Context, name: String?) {
        val pattern = longArrayOf(0, 200, 60, 200)
        val alarmSound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.notification)
        var CHANNEL_ID="todo_Channel"
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notify)
            .setColor(Color.MAGENTA)
            .setContentTitle("To Do Alert!")
            .setContentText(name)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_MAX)
        val manager:NotificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            val name = "Todo_channel"
            val descriptionText = "Task alert Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableLights(true)
                setShowBadge(true)
                setLockscreenVisibility(Notification.VISIBILITY_PUBLIC)
                enableVibration(true)

            }
            manager.createNotificationChannel(channel)
        }

        manager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

}
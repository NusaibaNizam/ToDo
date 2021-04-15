package com.saranusaibanizam.todo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
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
        var CHANNEL_ID="todo_Channel"
        var vibrate:LongArray= longArrayOf(1000,1000, 1000, 1000, 1000)
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notify)
            .setColor(Color.MAGENTA)
            .setContentTitle("To Do Alert!")
            .setVibrate(vibrate)
            .setContentText(name)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val manager:NotificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Todo_channel"
            val descriptionText = "Task alert Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            manager.createNotificationChannel(channel)
        }

        manager.notify(System.currentTimeMillis().toInt(),builder.build())
    }

}
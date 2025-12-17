package com.example.daymate.todo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.daymate.R

class ReminderWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val taskTitle = inputData.getString("task_title") ?: return Result.failure()

        showNotification(taskTitle)

        return Result.success()
    }

    private fun showNotification(taskTitle: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "task_reminders"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Task Reminders", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Task Reminder")
            .setContentText(taskTitle)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

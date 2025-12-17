package com.example.daymate.todo

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class ReminderScheduler(private val context: Context) {

    fun scheduleReminder(task: Task) {
        val workManager = WorkManager.getInstance(context)

        val data = Data.Builder()
            .putString("task_title", task.title)
            .build()

        val delay = task.reminderTime - System.currentTimeMillis()

        if (delay > 0) {
            val reminderWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .addTag(task.id.toString())
                .build()

            workManager.enqueue(reminderWorkRequest)
        }
    }

    fun cancelReminder(taskId: Int) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(taskId.toString())
    }
}

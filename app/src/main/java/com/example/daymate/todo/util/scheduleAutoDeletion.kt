package com.example.daymate.todo.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleAutoDeletion(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<AutoDeleteWorker>(1, TimeUnit.DAYS).build()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "AutoDeleteWorker",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )
}
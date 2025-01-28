package com.example.daymate.todo.util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.daymate.todo.MainApplication

import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class AutoDeleteWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val database = MainApplication.toDoDatabase
        val dao = database.getToDoDao()

        val threeDaysAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)

        // Use a coroutine to call suspend functions
        kotlin.runCatching {
            runBlocking {
                dao.deleteOldData(threeDaysAgo)
            }
        }

        return Result.success()
    }
}

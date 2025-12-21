package com.example.daymate.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    private val reminderScheduler: ReminderScheduler
    val allTasks: Flow<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
        reminderScheduler = ReminderScheduler(application)
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
        reminderScheduler.scheduleReminder(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
        reminderScheduler.cancelReminder(task.id)
        reminderScheduler.scheduleReminder(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
        reminderScheduler.cancelReminder(task.id)
    }
}

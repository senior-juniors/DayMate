package com.example.daymate.todo

import android.app.Application
import androidx.room.Room
import com.example.daymate.todo.database.data.ToDoDatabase
import com.example.daymate.todo.util.scheduleAutoDeletion

class MainApplication : Application() {

    companion object {
        lateinit var toDoDatabase: ToDoDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize the database and assign it to the companion object
        toDoDatabase = Room.databaseBuilder(
            applicationContext,
            ToDoDatabase::class.java,
            ToDoDatabase.Name
        ).fallbackToDestructiveMigration() // Use cautiously in production
            .build()

        // Schedule auto-deletion task
        scheduleAutoDeletion(this)
    }
}

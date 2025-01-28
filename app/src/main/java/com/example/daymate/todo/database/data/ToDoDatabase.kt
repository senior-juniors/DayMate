package com.example.daymate.todo.database.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.daymate.todo.util.Converter

@Database(entities = [ToDo::class], version = 1)
@TypeConverters(Converter::class)
abstract class ToDoDatabase:RoomDatabase() {

    companion object{
        const val Name = "ToDo_db"
    }

    abstract fun getToDoDao() : ToDoDao
}
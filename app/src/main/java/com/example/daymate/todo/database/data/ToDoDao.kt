package com.example.daymate.todo.database.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ToDoDao {
    @Query("select * from ToDo")
    fun getAllToDo () : LiveData<List<ToDo>>

    @Insert()
    fun addToDo(toDo: ToDo)

    @Query("Delete from ToDo where id = :id")
    fun deleteToDo(id : Int )

    @Query("DELETE FROM ToDo WHERE timestamp < :timeLimit")
    suspend fun deleteOldData(timeLimit: Long)
}
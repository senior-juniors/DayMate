package com.example.daymate.todo.viewmodel

import android.annotation.SuppressLint

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daymate.todo.MainApplication
import com.example.daymate.todo.database.data.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class ToDoViewModel():ViewModel() {

    val todoDao = MainApplication.toDoDatabase.getToDoDao()
  val todoList : LiveData<List<ToDo>> = todoDao.getAllToDo()


    @SuppressLint("NewApi")
    fun addTodo(title : String, desc : String){
        viewModelScope.launch ( Dispatchers.IO ){
            todoDao.addToDo(ToDo(title = title, desc = desc, createDate = Date.from(Instant.now())))
        }
    }

    fun deleteToDo(id : Int){
        viewModelScope.launch(Dispatchers.IO){
            todoDao.deleteToDo(id = id)
        }
    }

}
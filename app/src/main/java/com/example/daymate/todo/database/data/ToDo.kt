package com.example.daymate.todo.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title : String,
    val desc : String, //Description
    val createDate: Date,
    val timestamp: Long = System.currentTimeMillis() // store the creation time in milliseconds
)

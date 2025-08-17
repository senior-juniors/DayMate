package com.example.daymate.event

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val organizer: String = "",
    val imageUrl: String = ""
)
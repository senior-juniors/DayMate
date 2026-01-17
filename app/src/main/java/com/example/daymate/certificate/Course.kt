package com.example.daymate.certificate
import com.google.firebase.Timestamp


data class Course(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val provider: String = "",
    val link: String = "",
    val duration: String = "",
    val certificateType: String = "",
    val createdAt: Long = 0
)

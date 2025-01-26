package com.example.daymate.Class_Schedule

data class ClassData(
    val semester: String,
    val branch:String,
    val section:String,
    val timetable:List<SubjectDetails>
)

data class SubjectsDetails(
    val name: String,
    val time: String,
    val venue:String
)
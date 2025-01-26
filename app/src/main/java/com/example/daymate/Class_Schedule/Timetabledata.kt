package com.example.daymate.Class_Schedule

data class Timetabledata(
    val day: String,
    val schedule: List<SubjectDetails>
)

data class SubjectDetails(
    val subject: String,
    val time: String,
    val venue: String
)

val timetableData = mapOf(
    "A1" to listOf(
        Timetabledata(
            day = "Monday",
            schedule = listOf(
                SubjectDetails("CST302 (AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST304 (VK)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("HST302 (RS)", "3:00 PM - 4:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "4:00 PM - 5:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Tuesday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST302 (AN)", "11:00 AM - 12:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "12:00 PM - 1:00 PM", "LT-6"),
                SubjectDetails("CSP302 (AN +SS)", "3:00 PM - 5:00 PM", "LAB-138")
            )
        ),
        Timetabledata(
            day = "Wednesday",
            schedule = listOf(
                SubjectDetails("HST302 (RS)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        ),
        Timetabledata(
            day = "Thursday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CSP304 (VK)", "11:00 AM - 1:00 PM", "CC"),
                SubjectDetails("CST306 (GSY)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("CST302 (AN)", "3:00 PM - 4:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Friday",
            schedule = listOf(
                SubjectDetails("HSP302 (RS)", "9:00 AM - 11:00 AM", "LAB-305"),
                SubjectDetails("CST302 (TUTE)(AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        )
    ),
    "A2" to listOf(
        Timetabledata(
            day = "Monday",
            schedule = listOf(
                SubjectDetails("CST302 (AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CSP302 (AN +SS)", "11:00 AM - 1:00 PM", "LAB-137"),
                SubjectDetails("CST304 (VK)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("HST302 (RS)", "3:00 PM - 4:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "4:00 PM - 5:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Tuesday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST302 (AN)", "11:00 AM - 12:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "12:00 PM - 1:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Wednesday",
            schedule = listOf(
                SubjectDetails("HST302 (RS)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CSP304 (VK)", "11:00 AM - 1:00 PM", "CC"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        ),
        Timetabledata(
            day = "Thursday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("HSP302 (RS)", "11:00 AM - 1:00 PM", "LAB-305"),
                SubjectDetails("CST306 (GSY)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("CST302 (AN)", "3:00 PM - 4:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Friday",
            schedule = listOf(
                SubjectDetails("CST302 (TUTE)(AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        )
    ),
    "A3" to listOf(
        Timetabledata(
            day = "Monday",
            schedule = listOf(
                SubjectDetails("CST302 (AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST304 (VK)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("HST302 (RS)", "3:00 PM - 4:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "4:00 PM - 5:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Tuesday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST302 (AN)", "11:00 AM - 12:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "12:00 PM - 1:00 PM", "LT-6"),
                SubjectDetails("HSP302 (RS)", "3:00 PM - 5:00 PM", "LAB-305")
            )
        ),
        Timetabledata(
            day = "Wednesday",
            schedule = listOf(
                SubjectDetails("HST302 (RS)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CSP302 (AN +SS)", "11:00 AM - 1:00 PM", "LAB-137"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        ),
        Timetabledata(
            day = "Thursday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CSP304 (VK)", "11:00 AM - 1:00 PM", "CC"),
                SubjectDetails("CST306 (GSY)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("CST302 (AN)", "3:00 PM - 4:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Friday",
            schedule = listOf(
                SubjectDetails("CST302 (TUTE)(AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        )
    ),
    "A4" to listOf(
        Timetabledata(
            day = "Monday",
            schedule = listOf(
                SubjectDetails("CST302 (AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("HSP302 (MD)", "11:00 AM - 1:00 PM", "RM-243"),
                SubjectDetails("CST304 (VK)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("HST302 (RS)", "3:00 PM - 4:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "4:00 PM - 5:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Tuesday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST302 (AN)", "11:00 AM - 12:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "12:00 PM - 1:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Wednesday",
            schedule = listOf(
                SubjectDetails("HST302 (RS)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CSP304 (VK)", "11:00 AM - 1:00 PM", "CC"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        ),
        Timetabledata(
            day = "Thursday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("CST302 (AN)", "3:00 PM - 4:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Friday",
            schedule = listOf(
                SubjectDetails("CSP302 (AN +SS)", "10:00 AM - 11:00 AM", "LAB-137"),
                SubjectDetails("CST302 (TUTE)(AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        )
    ),
    "A5" to listOf(
        Timetabledata(
            day = "Monday",
            schedule = listOf(
                SubjectDetails("CST302 (AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST304 (VK)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("HST302 (RS)", "3:00 PM - 4:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "4:00 PM - 5:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Tuesday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CST302 (AN)", "11:00 AM - 12:00 PM", "LT-6"),
                SubjectDetails("CST306 (GSY)", "12:00 PM - 1:00 PM", "LT-6"),
                SubjectDetails("HSP302 (RS)", "3:00 PM - 5:00 PM", "LAB-305")
            )
        ),
        Timetabledata(
            day = "Wednesday",
            schedule = listOf(
                SubjectDetails("HST302 (RS)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("HSP302 (MD)", "11:00 AM - 1:00 PM", "RM243"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        ),
        Timetabledata(
            day = "Thursday",
            schedule = listOf(
                SubjectDetails("CST304 (VK)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("CSP302 (AN +SS)", "11:00 AM - 1:00 PM", "LAB-137"),
                SubjectDetails("CST306 (GSY)", "2:00 PM - 3:00 PM", "LT-6"),
                SubjectDetails("CST302 (AN)", "3:00 PM - 4:00 PM", "LT-6")
            )
        ),
        Timetabledata(
            day = "Friday",
            schedule = listOf(
                SubjectDetails("CSP304 (VK)", "9:00 AM - 11:00 AM", "LAB-19"),
                SubjectDetails("CST302 (TUTE)(AN)", "10:00 AM - 11:00 AM", "LT-6"),
                SubjectDetails("PROJECT CSD302", "2:00 PM - 4:00 PM", "--")
            )
        )
    )
)


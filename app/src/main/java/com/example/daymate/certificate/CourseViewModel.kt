package com.example.daymate.certificate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow

class CourseViewModel : ViewModel() {
    private val repository = CourseRepository()
    val courses = repository.courses

    fun addCourse(
        title: String, description: String, provider: String, link: String, duration: String, certificateType: String
    ) {
        println("CourseViewModel: addCourse called for course: $title")
        viewModelScope.launch {
            try {
                val courseData = Course(
                    title = title, description = description, provider = provider, link = link,
                    duration = duration, certificateType = certificateType, createdAt = System.currentTimeMillis()
                )
                println("CourseViewModel: Calling repository.addCourse")
                val result = repository.addCourse(courseData)
                println("CourseViewModel: Repository returned result: $result")
                println("CourseViewModel: Course added successfully")
            } catch (e: Exception) {
                println("CourseViewModel: Exception caught - ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun deleteCourse(courseId: String) {
        viewModelScope.launch {
            try {
                repository.deleteCourse(courseId)
                println("Course deleted successfully")

            } catch (e: Exception) {
                println("Error deleting course: ${e.message}")
            }
        }
    }
}

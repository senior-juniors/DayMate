package com.example.daymate.certificate

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class CourseRepository {
    private val data = Firebase.database("https://daymate-e74c9-default-rtdb.firebaseio.com/").reference.child("courses")
 
    // Use a private MutableStateFlow and expose it as a public StateFlow
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    init {
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val courseList = mutableListOf<Course>()
                for (courseSnapshot in snapshot.children) {
                    val course = Course(
                        id = courseSnapshot.key ?: "",
                        title = courseSnapshot.child("title").getValue(String::class.java) ?: "",
                        description = courseSnapshot.child("description").getValue(String::class.java) ?: "",
                        provider = courseSnapshot.child("provider").getValue(String::class.java) ?: "",
                        link = courseSnapshot.child("link").getValue(String::class.java) ?: "",
                        duration = courseSnapshot.child("duration").getValue(String::class.java) ?: "",
                        certificateType = courseSnapshot.child("certificateType").getValue(String::class.java) ?: "",
                        createdAt = courseSnapshot.child("createdAt").getValue(Long::class.java) ?: 0L
                    )
                    courseList.add(course)
                }
                // Sort by createdAt in descending order (newest first)
                _courses.value = courseList.sortedByDescending { it.createdAt }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
                println("Firebase Error: ${error.message}")
            }
        })
    }

    // Asynchronous function to add a course
    suspend fun addCourse(course: Course): String {
        return try {
            val courseRef = data.push()
            courseRef.setValue(course.copy(id = courseRef.key ?: "")).await()
            courseRef.key ?: ""
        } catch (e: Exception) {
            println("Error adding Course: ${e.message}")
            ""
        }
    }

    // Asynchronous function to delete a course
    suspend fun deleteCourse(courseId: String): Boolean {
        return try {
            data.child(courseId).removeValue().await()
            true
        } catch (e: Exception) {
            println("Error deleting Course: ${e.message}")
            false
        }
    }
}

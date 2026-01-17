package com.example.daymate.certificate

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class CourseRepository {
    private val data: DatabaseReference

    // Use a private MutableStateFlow and expose it as a public StateFlow
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    init {
        // Get database instance and enable persistence
        val firebaseDatabase = Firebase.database("https://daymate-e74c9-default-rtdb.firebaseio.com/")
        try {
            firebaseDatabase.setPersistenceEnabled(true)
            println("CourseRepository: Firebase persistence enabled")
        } catch (e: Exception) {
            println("CourseRepository: Persistence already enabled or error: ${e.message}")
        }

        data = firebaseDatabase.reference.child("courses")

        println("CourseRepository: Initializing - Database reference: ${data.path}")
        println("CourseRepository: Database URL: https://daymate-e74c9-default-rtdb.firebaseio.com/courses")

        // Monitor connection status
        val connectedRef = Firebase.database("https://daymate-e74c9-default-rtdb.firebaseio.com/").getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                println("CourseRepository: Firebase connected: $connected")
            }
            override fun onCancelled(error: DatabaseError) {
                println("CourseRepository: Connection check cancelled: ${error.message}")
            }
        })

        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val courseList = mutableListOf<Course>()
                println("Firebase Courses: onDataChange triggered, snapshot exists: ${snapshot.exists()}, children count: ${snapshot.childrenCount}")
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
                    println("Firebase Courses: Loaded course - ${course.title}")
                }
                // Sort by createdAt in descending order (newest first)
                _courses.value = courseList.sortedByDescending { it.createdAt }
                println("Firebase Courses: Total courses loaded: ${courseList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
                println("Firebase Courses Error: ${error.message}")
            }
        })
    }

    // Asynchronous function to add a course
    suspend fun addCourse(course: Course): String {
        return try {
            println("Firebase Courses: Attempting to add course - ${course.title}")
            val courseRef = data.push()
            val courseId = courseRef.key ?: ""
            println("Firebase Courses: Generated course ID: $courseId")
            courseRef.setValue(course.copy(id = courseId)).await()
            println("Firebase Courses: Successfully added course to Firebase")
            courseId
        } catch (e: Exception) {
            println("Firebase Courses Error adding course: ${e.message}")
            e.printStackTrace()
            ""
        }
    }

    // Asynchronous function to delete a course
    suspend fun deleteCourse(courseId: String): Boolean {
        return try {
            println("Firebase Courses: Attempting to delete course ID: $courseId")
            data.child(courseId).removeValue().await()
            println("Firebase Courses: Successfully deleted course")
            true
        } catch (e: Exception) {
            println("Firebase Courses Error deleting course: ${e.message}")
            e.printStackTrace()
            false
        }
    }
}

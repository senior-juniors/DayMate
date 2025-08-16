package com.example.daymate.classroom
//
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
//import com.google.api.client.http.javanet.NetHttpTransport
//import com.google.api.client.json.gson.GsonFactory
//import com.google.api.services.classroom.Classroom
//import com.google.api.services.classroom.model.Course
//import com.google.api.services.classroom.model.CourseWork
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class ClassroomViewModel : ViewModel() {
//
//    // StateFlow to hold the list of courses
//    private val _courses = MutableStateFlow<List<Course>>(emptyList())
//    val courses = _courses.asStateFlow()
//
//    // StateFlow to hold the list of assignments
//    private val _assignments = MutableStateFlow<List<CourseWork>>(emptyList())
//    val assignments = _assignments.asStateFlow()
//
//    // StateFlow for loading state
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading = _isLoading.asStateFlow()
//
//    // StateFlow for error messages
//    private val _error = MutableStateFlow<String?>(null)
//    val error = _error.asStateFlow()
//
//    /**
//     * Fetches courses and assignments from the Google Classroom API.
//     * It uses the already signed-in Google account.
//     * @param context The application context.
//     */
//    fun fetchClassroomData(context: Context) {
//        // Start loading
//        _isLoading.value = true
//        _error.value = null
//
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                // Get the last signed-in account. Since the user is already logged in,
//                // we don't need to ask them to sign in again.
//                val account = GoogleSignIn.getLastSignedInAccount(context)
//                if (account == null) {
//                    _error.value = "User is not signed in with Google."
//                    _isLoading.value = false
//                    return@launch
//                }
//
//                // Create credentials from the signed-in account
//                val credential = GoogleAccountCredential.usingOAuth2(
//                    context,
//                    listOf(
//                        "https://www.googleapis.com/auth/classroom.courses.readonly",
//                        "https://www.googleapis.com/auth/classroom.coursework.me.readonly"
//                    )
//                ).setSelectedAccount(account.account)
//
//                // Build the Classroom API service
//                val classroom = Classroom.Builder(
//                    NetHttpTransport(),
//                    GsonFactory.getDefaultInstance(),
//                    credential
//                ).setApplicationName("DayMate") // Use your app name
//                    .build()
//
//                // Fetch the list of courses
//                val courseResponse = classroom.courses().list().setPageSize(20).execute()
//                val coursesList = courseResponse.courses ?: emptyList()
//                _courses.value = coursesList
//
//                // Fetch assignments for each course
//                val allAssignments = mutableListOf<CourseWork>()
//                coursesList.forEach { course ->
//                    val courseworkResponse = classroom.courses().courseWork()
//                        .list(course.id)
//                        // *** THIS IS THE FIX: Pass a list to setCourseWorkStates ***
//                        .setCourseWorkStates(listOf("PUBLISHED"))
//                        .execute()
//                    allAssignments.addAll(courseworkResponse.courseWork ?: emptyList())
//                }
//
//                // Filter for assignments that have a due date
//                _assignments.value = allAssignments.filter { it.dueDate != null }
//
//            } catch (e: Exception) {
//                Log.e("ClassroomViewModel", "Error fetching data", e)
//                _error.value = "Failed to fetch classroom data: ${e.message}"
//            } finally {
//                // Stop loading
//                _isLoading.value = false
//            }
//        }
//    }
//}


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.classroom.Classroom
import com.google.api.services.classroom.model.Course
import com.google.api.services.classroom.model.CourseWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ClassroomViewModel : ViewModel() {

    // StateFlow to hold the list of courses
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    // StateFlow to hold the list of assignments
    private val _assignments = MutableStateFlow<List<CourseWork>>(emptyList())
    val assignments = _assignments.asStateFlow()

    // StateFlow for loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // StateFlow for error messages
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    /**
     * Fetches courses and assignments from the Google Classroom API.
     * It uses the already signed-in Google account.
     * @param context The application context.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchClassroomData(context: Context) {
        // Start loading
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Get the last signed-in account.
                val account = GoogleSignIn.getLastSignedInAccount(context)
                if (account == null) {
                    _error.value = "User is not signed in with Google."
                    _isLoading.value = false
                    return@launch
                }

                // Create credentials from the signed-in account
                val credential = GoogleAccountCredential.usingOAuth2(
                    context,
                    listOf(
                        "https://www.googleapis.com/auth/classroom.courses.readonly",
                        "https://www.googleapis.com/auth/classroom.coursework.me.readonly"
                    )
                ).setSelectedAccount(account.account)

                // Build the Classroom API service
                val classroom = Classroom.Builder(
                    NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    credential
                ).setApplicationName("DayMate")
                    .build()

                // --- RELIABLE FETCHING & FILTERING ---

                // Define the date to filter by
                val filterDate = ZonedDateTime.parse("2025-01-01T00:00:00Z")

                // 1. Fetch the list of all courses first
                val courseResponse = classroom.courses().list().setPageSize(20).execute()
                val allCourses = courseResponse.courses ?: emptyList()

                // 2. Filter the courses by date
                val filteredCourses = allCourses.filter { course ->
                    val creationTime = ZonedDateTime.parse(course.creationTime, DateTimeFormatter.ISO_DATE_TIME)
                    creationTime.isAfter(filterDate)
                }
                _courses.value = filteredCourses

                // 3. Fetch assignments for each of the filtered courses
                val allAssignments = mutableListOf<CourseWork>()
                filteredCourses.forEach { course ->
                    val courseworkResponse = classroom.courses().courseWork()
                        .list(course.id)
                        .setCourseWorkStates(listOf("PUBLISHED"))
                        .execute()
                    allAssignments.addAll(courseworkResponse.courseWork ?: emptyList())
                }

                // 4. Filter the assignments by date
                _assignments.value = allAssignments.filter { assignment ->
                    val creationTime = ZonedDateTime.parse(assignment.creationTime, DateTimeFormatter.ISO_DATE_TIME)
                    assignment.dueDate != null && creationTime.isAfter(filterDate)
                }

            } catch (e: Exception) {
                Log.e("ClassroomViewModel", "Error fetching data", e)
                _error.value = "Failed to fetch classroom data: ${e.message}"
            } finally {
                // Stop loading
                _isLoading.value = false
            }
        }
    }
}
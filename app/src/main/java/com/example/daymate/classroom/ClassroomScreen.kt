package com.example.daymate.classroom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.api.services.classroom.model.Course
import com.google.api.services.classroom.model.CourseWork


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassroomScreen(
    navController: NavController,
    classroomViewModel: ClassroomViewModel = viewModel()
) {
    val context = LocalContext.current
    val courses by classroomViewModel.courses.collectAsState()
    val assignments by classroomViewModel.assignments.collectAsState()
    val isLoading by classroomViewModel.isLoading.collectAsState()
    val error by classroomViewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        classroomViewModel.fetchClassroomData(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Google Classroom") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (error != null) {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Text(
                            "Enrolled Courses",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    if (courses.isEmpty()) {
                        item { Text("No courses found.") }
                    } else {
                        items(courses) { course ->
                            CourseItem(course)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Pending Assignments",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    if (assignments.isEmpty()) {
                        item { Text("No pending assignments found.") }
                    } else {
                        items(assignments) { assignment ->
                            AssignmentItem(assignment)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseItem(course: Course) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(course.name, fontWeight = FontWeight.Bold)
            course.section?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun AssignmentItem(assignment: CourseWork) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(assignment.title, fontWeight = FontWeight.Bold)
            assignment.dueDate?.let {
                Text(
                    "Due: ${it.day}/${it.month}/${it.year}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


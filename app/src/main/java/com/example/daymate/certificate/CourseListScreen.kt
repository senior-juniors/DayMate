package com.example.daymate.certificate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.daymate.admin.AdminManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseListScreen(viewModel: CourseViewModel, navController: NavController) {
    val courses by viewModel.courses.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isAdmin = AdminManager.isAdmin(context)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Free Certifications", fontWeight = FontWeight.Bold) }) },
        floatingActionButton = {
            // Show FAB only for admin users
            if (isAdmin) {
                FloatingActionButton(onClick = { showAddDialog = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Course")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        if (courses.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                Text("No courses available. Click + to add one!", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(courses) { course ->
                    CourseCard(
                        course = course,
                        isAdmin = isAdmin,
                        onClick = {
                            navController.navigate("course_detail/${course.id}")
                        },
                        onDelete = {
                            viewModel.deleteCourse(course.id)
                        }
                    )
                }
            }
        }

        if (showAddDialog) {
            AddCourseDialog(
                onDismiss = { showAddDialog = false },
                onAddCourse = { title, description, provider, link, duration, certificateType ->
                    viewModel.addCourse(title, description, provider, link, duration, certificateType)
                    android.widget.Toast.makeText(context, "Course added successfully!", android.widget.Toast.LENGTH_SHORT).show()
                    showAddDialog = false
                }
            )
        }
    }
}

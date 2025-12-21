package com.example.daymate.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.daymate.Screens.standardQuadFromTo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreen(taskViewModel: TaskViewModel = viewModel()) {
    val tasks by taskViewModel.allTasks.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF06154C)))
    {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            with(this) {
                val width = constraints.maxWidth.toFloat()
                val height = constraints.maxHeight.toFloat()

                val mediumColoredPath = Path().apply {
                    val mediumColoredPoint1 = Offset(0f, height * 0.8f)
                    val mediumColoredPoint2 = Offset(width * 0.25f, height * 0.9f)
                    val mediumColoredPoint3 = Offset(width * 0.5f, height * 0.75f)
                    val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.95f)
                    val mediumColoredPoint5 = Offset(width * 1.4f, height * 0.6f)

                    moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
                    standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
                    standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
                    standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
                    standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
                    lineTo(width + 100f, height + 100f)
                    lineTo(-100f, height + 100f)
                    close()
                }

                val lightColoredPath = Path().apply {

                    val lightPoint1 = Offset(0f, height * 0.85f)
                    val lightPoint2 = Offset(width * 0.2f, height * 0.95f)
                    val lightPoint3 = Offset(width * 0.5f, height * 0.85f)
                    val lightPoint4 = Offset(width * 0.8f, height * 1.05f)
                    val lightPoint5 = Offset(width * 1.4f, height * 0.7f)

                    moveTo(lightPoint1.x, lightPoint1.y)
                    standardQuadFromTo(lightPoint1, lightPoint2)
                    standardQuadFromTo(lightPoint2, lightPoint3)
                    standardQuadFromTo(lightPoint3, lightPoint4)
                    standardQuadFromTo(lightPoint4, lightPoint5)
                    lineTo(width + 100f, height + 100f)
                    lineTo(-100f, height + 100f)
                    close()
                }

                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawPath(path = mediumColoredPath, color = Color(0xFF494E8A))
                    drawPath(path = lightColoredPath, color = Color(0xFFCACFFF))
                }
            }
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("To-Do List", color = Color.White) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        taskToEdit = null
                        showDialog = true
                    },
                    containerColor = Color(0xFF8CA8DA)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                tasks.forEach { task ->
                    TaskItem(
                        task = task,
                        onEdit = {
                            taskToEdit = it
                            showDialog = true
                        },
                        onDelete = {
                            taskViewModel.delete(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (showDialog) {
                AddEditTaskDialog(
                    task = taskToEdit,
                    onDismiss = { showDialog = false },
                    onSave = { title, description, reminderTime ->
                        if (taskToEdit == null) {
                            taskViewModel.insert(Task(title = title, description = description, reminderTime = reminderTime))
                        } else {
                            taskViewModel.update(taskToEdit!!.copy(title = title, description = description, reminderTime = reminderTime))
                        }
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onEdit: (Task) -> Unit, onDelete: (Task) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = task.description, style = MaterialTheme.typography.bodyMedium, color = Color.LightGray)
                Text(
                    text = "Reminder: ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(task.reminderTime))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
            }
            Row {
                IconButton(onClick = { onEdit(task) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Task", tint = Color.White)
                }
                IconButton(onClick = { onDelete(task) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Task", tint = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskDialog(
    task: Task?,
    onDismiss: () -> Unit,
    onSave: (String, String, Long) -> Unit
) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    val calendar = Calendar.getInstance()
    if (task != null) {
        calendar.timeInMillis = task.reminderTime
    }
    val context = LocalContext.current

    val (year, month, day) = Triple(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    val (hour, minute) = Pair(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))

    var reminderTime by remember { mutableStateOf(calendar.timeInMillis) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            calendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
            reminderTime = calendar.timeInMillis
        },
        year, month, day
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendar.set(Calendar.MINUTE, selectedMinute)
            reminderTime = calendar.timeInMillis
        },
        hour, minute, true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (task == null) "Add Task" else "Edit Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Reminder Time: ")
                    Text(
                        text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(reminderTime)),
                        modifier = Modifier.clickable { datePickerDialog.show() }
                    )
                }
                Button(onClick = { datePickerDialog.show() }) {
                    Text("Select Date")
                }
                Button(onClick = { timePickerDialog.show() }) {
                    Text("Select Time")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(title, description, reminderTime) },
                enabled = title.isNotBlank() && description.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

package com.example.daymate.Class_Schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimetableScreen() {
    var selectedbatch by remember { mutableStateOf("") }
    BatchSelectorWithTimetable(
        selectedBatch = selectedbatch,
        onBatchSelected = { selectedbatch = it })

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchSelectorWithTimetable(selectedBatch: String, onBatchSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val batches = timetableData.keys.toList()
    val currentDay = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Select Batch",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedBatch.ifBlank { "Select Batch" },
                    onValueChange = {},
                    label = { Text("Batch") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    batches.forEach { batch ->
                        DropdownMenuItem(
                            onClick = {
                                onBatchSelected(batch)
                                expanded = false
                            },
                            text = { Text(text = "Batch $batch") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Today's Timetable: $currentDay",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            val schedule = timetableData[selectedBatch]?.find { it.day == currentDay }?.schedule
                ?: listOf(SubjectDetails(subject = "No classes scheduled", time = "", venue = ""))
            schedule.forEach { subject ->
                Text(
                    text = "${subject.subject} - ${subject.time} - ${subject.venue}",
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


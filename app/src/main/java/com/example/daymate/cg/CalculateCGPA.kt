package com.example.daymate.cg

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// DataStore Setup
val Context.dataStore by preferencesDataStore("cgpa_store")

suspend fun saveCGPA(context: Context, cgpa: Double) {
    context.dataStore.edit {
        it[doublePreferencesKey("cgpa")] = cgpa
    }
}

data class Subject(val credit: String = "", val grade: String = "")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CGPACalculator(navController: NavController) {
    val context = LocalContext.current
    var subjects by remember { mutableStateOf(listOf(Subject())) }
    var cgpa by remember { mutableStateOf<Double?>(null) }
    var error by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CGPA Calculator") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // ... rest of your code { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            // Using LazyColumn correctly with itemsIndexed for scrollable content
            LazyColumn(modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(subjects.size) { index ->
                    val subject = subjects[index]
                    SubjectCard(
                        subject = subject,
                        onCreditChange = { newVal ->
                            val newList = subjects.toMutableList()
                            newList[index] = newList[index].copy(credit = newVal)
                            subjects = newList
                        },
                        onGradeChange = { newVal ->
                            val newList = subjects.toMutableList()
                            newList[index] = newList[index].copy(grade = newVal)
                            subjects = newList
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }

            Button(
                onClick = { subjects = subjects + Subject() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Subject")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    var totalCredits = 0
                    var totalPoints = 0

                    for (s in subjects) {
                        val credit = s.credit.toIntOrNull()
                        val point = gradePoints[s.grade]

                        if (credit == null || point == null) {
                            error = "⚠ Please enter valid credits and grade"
                            return@Button
                        }

                        totalCredits += credit
                        totalPoints += credit * point
                    }

                    if (totalCredits > 0) {
                        cgpa = totalPoints.toDouble() / totalCredits
                        error = ""
                        CoroutineScope(Dispatchers.IO).launch {
                            saveCGPA(context, cgpa!!)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calculate CGPA")
            }

            if (error.isNotEmpty()) {
                Text(error, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            cgpa?.let {
                Text(
                    text = "Your CGPA: %.2f".format(it),
                    fontSize = 22.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectCard(
    subject: Subject,
    onCreditChange: (String) -> Unit,
    onGradeChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            OutlinedTextField(
                value = subject.credit,
                onValueChange = onCreditChange,
                label = { Text("Credits") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = subject.grade,
                    onValueChange = {},
                    label = { Text("Grade") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    gradePoints.keys.forEach { grade ->
                        DropdownMenuItem(
                            text = { Text(grade) },
                            onClick = {
                                onGradeChange(grade)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
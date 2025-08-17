package com.example.daymate.event

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.daymate.Screens.standardQuadFromTo
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs


fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(viewModel: EventViewModel, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var organizer by remember { mutableStateOf("") }

    // Date Picker State
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    // Time Picker State
    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    // Indian Timezone
    val indianZone = ZoneId.of("Asia/Kolkata")
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(indianZone)
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(indianZone)

    val darkBlue = Color(0xFF06154C)
    val mediumBlue = Color(0xFF494E8A)
    val lightBlue = Color(0xFFCACFFF)
    val buttonBlue = Color(0xFF8CA8DA)

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(

        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        disabledTextColor = Color.White,
        cursorColor = lightBlue,


        focusedBorderColor = lightBlue,
        unfocusedBorderColor = Color.LightGray,
        disabledBorderColor = Color.LightGray,


        focusedLabelColor = lightBlue,
        unfocusedLabelColor = Color.LightGray,
        disabledLabelColor = Color.LightGray,


        focusedTrailingIconColor = lightBlue,
        unfocusedTrailingIconColor = lightBlue,
        disabledTrailingIconColor = lightBlue
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBlue)
    ) {
        // 1. Custom Background Canvas (copied from ProfileScreen)
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
                    drawPath(path = mediumColoredPath, color = mediumBlue)
                    drawPath(path = lightColoredPath, color = lightBlue)
                }
            }

            // 2. Content Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()), // Make content scrollable
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                // 3. Styled Screen Title
                Text(
                    text = "Add New Event",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))

                // 4. Styled Text Fields
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Event Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { },
                    label = { Text("Date") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true, // Important for icon button to work
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Pick Date",
                                tint = Color.White)
                        }
                    },
                    colors = textFieldColors
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = time,
                    onValueChange = {},
                    label = { Text("Time") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true, // Important for icon button to work
                    trailingIcon = {
                        IconButton(onClick = { showTimePicker = true }) {
                            Icon(Icons.Default.Schedule, contentDescription = "Pick Time",
                                tint = Color.White)
                        }
                    },
                    colors = textFieldColors
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = organizer,
                    onValueChange = { organizer = it },
                    label = { Text("Organizer") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    singleLine = true
                )

                Spacer(modifier = Modifier.weight(1f)) // Pushes button to bottom if space allows
                Spacer(modifier = Modifier.height(32.dp))

                // 5. Styled Button
                Button(
                    onClick = {
                        val event = Event(
                            title = title,
                            description = description,
                            date = date,
                            time = time,
                            location = location,
                            organizer = organizer
                        )
                        viewModel.addEvent(
                            event = event,
                            onSuccess = { navController.popBackStack() },
                            onError = { /* TODO: Handle error, e.g., show a Snackbar */ }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = title.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonBlue,
                        contentColor = Color.White,
                        disabledContainerColor = darkBlue,
                        disabledContentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Add Event", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }


        // Date Picker Dialog (Styled to match)
        if (showDatePicker) {

            DatePickerDialog(

                onDismissRequest = { showDatePicker = false },

                confirmButton = {

                    Button(

                        onClick = {

                            datePickerState.selectedDateMillis?.let {

                                date = Instant.ofEpochMilli(it)

                                    .atZone(indianZone)

                                    .format(dateFormatter)

                            }

                            showDatePicker = false

                        }

                    ) { Text("OK") }

                }

            ) {

                DatePicker(state = datePickerState)

            }

        }



        // Time Picker Dialog (Wrapped in a styled dialog)
        if (showTimePicker) {
            Dialog(onDismissRequest = { showTimePicker = false }) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = darkBlue)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Select Time",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        TimePicker(
                            state = timePickerState,
                            colors = TimePickerDefaults.colors(

                                selectorColor = buttonBlue,
                                clockDialColor = mediumBlue.copy(alpha = 0.8f),
                                clockDialSelectedContentColor = Color.White, // Changed for better contrast
                                clockDialUnselectedContentColor = Color.White,


                                periodSelectorBorderColor = buttonBlue,
                                periodSelectorSelectedContainerColor = buttonBlue,
                                periodSelectorSelectedContentColor = Color.White,
                                periodSelectorUnselectedContainerColor = Color.Transparent, // FIX: Was White
                                periodSelectorUnselectedContentColor = Color.White,


                                timeSelectorSelectedContainerColor = mediumBlue,
                                timeSelectorSelectedContentColor = Color.White,
                                timeSelectorUnselectedContainerColor = Color.Transparent, // FIX: Was White
                                timeSelectorUnselectedContentColor = Color.White
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { showTimePicker = false },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                            ) { Text("Cancel", color = buttonBlue) }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    val now = ZonedDateTime.now(indianZone)
                                    val selectedDateTime = now
                                        .withHour(timePickerState.hour)
                                        .withMinute(timePickerState.minute)
                                    time = selectedDateTime.format(timeFormatter)
                                    showTimePicker = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = buttonBlue)
                            ) { Text("OK") }
                        }
                    }
                }
            }
        }
    }
}
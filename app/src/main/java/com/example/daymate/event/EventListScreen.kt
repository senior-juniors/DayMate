package com.example.daymate.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EventListScreen(viewModel: EventViewModel, navController: NavController) {
    val events by viewModel.events.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var eventToDelete by remember { mutableStateOf<Event?>(null) }

    // Define colors from your theme for consistency
    val darkBlue = Color(0xFF06154C)
    val mediumBlue = Color(0xFF494E8A)
    val lightBlue = Color(0xFFCACFFF)
    val buttonBlue = Color(0xFF8CA8DA)



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBlue)
    ) {
        Column {
            Text(
                text = "Upcoming Events",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (events.isEmpty()) {
                    item {
                        Text(
                            text = "No events yet.\nTap the '+' button to add one!",
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(32.dp)
                        )
                    }
                }

                items(events) { event ->
                    EventCard(
                        event = event,
                        onClick = {
                            navController.navigate("eventDetails/${event.id}")
                        },
                        onDelete = {
                            eventToDelete = event
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
            FloatingActionButton(
                onClick = { navController.navigate("addEvent") },
                containerColor = buttonBlue,
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                containerColor = darkBlue,
                title = {
                    Text("Delete Event", color = Color.White)
                },
                text = {
                    Text(
                        "Are you sure you want to delete '${eventToDelete?.title}'?",
                        color = Color.LightGray
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            eventToDelete?.let { viewModel.deleteEvent(it.id) }
                            showDeleteDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f))
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDeleteDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = mediumBlue)
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
    //    { padding ->
//        LazyColumn(modifier = Modifier.padding(padding)) {
//            if (events.isEmpty()) {
//                item {
//                    Text(
//                        text = "No events available",
//                        modifier = Modifier.padding(16.dp))
//                }
//            }
//
//            items(events) { event ->
//                EventCard(
//                    event = event,
//                    viewModel = viewModel, // Pass the ViewModel
//                    onClick = {
//                        navController.navigate("eventDetails/${event.id}")
//                    },
//                    onDelete = {
//                        // Show confirmation dialog before deleting
//                        viewModel.deleteEvent(event.id)
//                    }
//                )
//            }
//        }
//    }
//}
    @Composable
    fun EventCard(
        event: Event,
       // viewModel: EventViewModel, // Add ViewModel parameter
        onClick: () -> Unit,
        onDelete: () -> Unit // Add delete callback
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
           )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onClick)
                ) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleLarge, // M3 typography
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${event.date} • ${event.time}",
                        style = MaterialTheme.typography.bodyMedium, // M3 typography
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Organized by: ${event.organizer}",
                        style = MaterialTheme.typography.bodyMedium, // M3 typography
                        color = Color.LightGray
                    )
                }

                // Delete button in top-right corner
                IconButton(
                    onClick = onDelete,
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Event",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }

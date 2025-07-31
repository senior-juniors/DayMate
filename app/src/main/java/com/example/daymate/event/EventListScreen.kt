package com.example.daymate.event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EventListScreen(viewModel: EventViewModel, navController: NavController) {
    val events by viewModel.events.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addEvent") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Event")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            if (events.isEmpty()) {
                item {
                    Text(
                        text = "No events available",
                        modifier = Modifier.padding(16.dp))
                }
            }

            items(events) { event ->
                EventCard(
                    event = event,
                    viewModel = viewModel, // Pass the ViewModel
                    onClick = {
                        navController.navigate("eventDetails/${event.id}")
                    },
                    onDelete = {
                        // Show confirmation dialog before deleting
                        viewModel.deleteEvent(event.id)
                    }
                )
            }
        }
    }
}
@Composable
fun EventCard(
    event: Event,
    viewModel: EventViewModel, // Add ViewModel parameter
    onClick: () -> Unit,
    onDelete: () -> Unit // Add delete callback
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Box {
            // Main content column
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(onClick = onClick)
            ) {
                Text(text = event.title, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${event.date} • ${event.time}")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Organized by: ${event.organizer}")
            }

            // Delete button in top-right corner
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Event",
                    tint = MaterialTheme.colors.error
                )
            }
        }
    }
}
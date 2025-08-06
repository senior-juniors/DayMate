package com.example.daymate.event
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun EventDetailsScreen(event: Event) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = event.title, style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Date & Time: ${event.date} at ${event.time}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Location: ${event.location}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Organized by: ${event.organizer}")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = event.description, style = MaterialTheme.typography.body1)
    }
}
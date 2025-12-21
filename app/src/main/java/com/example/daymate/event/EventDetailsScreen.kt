package com.example.daymate.event
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//@Composable
//fun EventDetailsScreen(event: Event) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = event.title, style = MaterialTheme.typography.titleMedium)
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(text = "Date & Time: ${event.date} at ${event.time}")
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = "Location: ${event.location}")
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(text = "Organized by: ${event.organizer}")
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(text = event.description, style = MaterialTheme.typography.titleMedium)
//    }
//}


@Composable
fun EventDetailsScreen(event: Event) {
    val darkBlue = Color(0xFF06154C)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBlue)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = event.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Details Card
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Use a helper for consistent rows
                    EventDetailRow(
                        icon = Icons.Default.CalendarToday,
                        label = "Date & Time",
                        value = "${event.date} at ${event.time}"
                    )
                    EventDetailRow(
                        icon = Icons.Default.LocationOn,
                        label = "Location",
                        value = event.location
                    )
                    EventDetailRow(
                        icon = Icons.Default.Person,
                        label = "Organizer",
                        value = event.organizer
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    )

                    // Event Description
                    EventDetailRow(
                        icon = Icons.Default.Info,
                        label = "About this event",
                        value = event.description
                    )
                }
            }
        }
    }
}

@Composable
fun EventDetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label icon",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                color = Color.LightGray,
                fontSize = 12.sp
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
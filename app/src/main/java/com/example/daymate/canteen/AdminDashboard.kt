package com.example.daymate.canteen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.util.CollectionUtils.listOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pending Orders") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(listOf("Order #1001", "Order #1002", "Order #1003")) { order ->
                OrderCard(orderId = order)
            }
        }
    }
}

@Composable
fun OrderCard(orderId: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = orderId,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Sample order items
            Text("2 × Samosa (₹30)")
            Text("1 × Tea (₹10)")
            Text("1 × Sandwich (₹30)")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { /* Decline action */ }
                ) {
                    Text("Decline")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { /* Accept action */ }
                ) {
                    Text("Accept")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CanteenLoginScPreview() {
    MaterialTheme {
        AdminDashboardScreen()
    }
}
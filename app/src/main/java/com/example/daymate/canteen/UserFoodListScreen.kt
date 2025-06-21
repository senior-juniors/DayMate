package com.example.canteen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.daymate.canteen.FoodItemsScreen
import com.google.android.gms.common.util.CollectionUtils.listOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFoodListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Menu",modifier = Modifier.padding(start = 140.dp),
                style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)})
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Do nothing */ },
                icon = { Icon(Icons.Default.ShoppingCart, "Place Order") },
                text = { Text("Place Order") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(getSampleFoodItems()) { item ->
                FoodItemCard(item)
            }
        }
    }
}

@Composable
fun FoodItemCard(item: FoodItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text("₹${item.price}", style = MaterialTheme.typography.bodyLarge)
            }
            QuantitySelector()
        }
    }
}

@Composable
fun QuantitySelector() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { /* Do nothing */ }) {
            Icon(Icons.Default.Remove, "Decrease")
        }
        Text("0", modifier = Modifier.padding(horizontal = 8.dp))
        IconButton(onClick = { /* Do nothing */ }) {
            Icon(Icons.Default.Add, "Increase")
        }
    }
}

// Sample data model
data class FoodItem(
    val name: String,
    val price: Double
)

// Sample data
fun getSampleFoodItems() = listOf(
    FoodItem("Samosa", 15.0),
    FoodItem("Tea", 10.0),
    FoodItem("Sandwich", 30.0),
    FoodItem("Maggi", 25.0),
    FoodItem("Cold Drink", 20.0)
)

@Preview(showBackground = true)
@Composable
fun CanteenLoginScreenPreview() {
    MaterialTheme {
       UserFoodListScreen()
    }
}
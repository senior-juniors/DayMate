package com.example.daymate.canteen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastMap
import androidx.core.graphics.toColorInt
import com.google.android.gms.common.util.CollectionUtils.listOf
import java.util.UUID

data class FoodItem(
    val name: String,
    val price: Double,
    val id: String = UUID.randomUUID().toString()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodItemsScreen() {
    // Sample food items data
    var foodItems by remember { mutableStateOf(
        listOf(
            FoodItem("Samosa", 15.00, 1.toString()),
            FoodItem("Tea", 10.00,2.toString()),
            FoodItem("Sandwich", 30.00,3.toString()),
            FoodItem("Maggi", 25.00,4.toString()),
            FoodItem("Cold Drink", 20.00,6.toString()))
        )

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    modifier = Modifier.padding(start = 40.dp),
                    text = "Canteen Food Items",
                    color = Color.Black,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                ) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Add new item logic would go here
                  //  foodItems = foodItems.plus(FoodItem("New Item", 0))
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(foodItems) { item ->
                FoodItemCard(
                    item = item,
                    onItemClick = { /* Handle item click */ },
                    onPriceChange = { newPrice ->
                        foodItems = foodItems.fastMap {
                            if (it.id == item.id) it.copy(price = newPrice.toDouble()) else it
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FoodItemCard(
    item: com.example.daymate.canteen.FoodItem,
    onItemClick: () -> Unit,
    onPriceChange: (Int) -> Unit
) {
    var editable by remember { mutableStateOf(false) }
    var tempPrice by remember { mutableStateOf(item.price.toString()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (editable) {
                OutlinedTextField(
                    value = tempPrice,
                    onValueChange = { tempPrice = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        onPriceChange(tempPrice.toColorInt() ?: 0)
                        editable = false
                    },
                   /// modifier = Modifier.align(LineHeightStyle.Alignment.End)
                ) {
                    Text("Save")
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "₹${item.price}",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    TextButton(
                        onClick = { editable = true }
                    ) {
                        Text("Edit Price")
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CanteenLoginScreenPreview() {
    MaterialTheme {
        FoodItemsScreen()
    }
}
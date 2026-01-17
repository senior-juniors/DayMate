package com.example.daymate.admin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.daymate.R

@Preview(showBackground = true, backgroundColor = 0xFF0A1A41)
@Composable
fun AdminLoginScreenPreview() {
    val navController = rememberNavController()
    AdminLoginScreen(navController)
}

@Composable
fun AdminLoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Fixed credentials
    val ADMIN_EMAIL = "daymate@iiitkota.ac.in"
    val ADMIN_PASSWORD = "Daymate@468"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1A41)) // Dark Blue Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Image(
                painter = painterResource(R.drawable.logo_img),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Admin Login 🔐\nWelcome to DayMate",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 36.dp)
            )

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter Email", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter Password", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            // Login Button
            Button(
                onClick = {
                    if (email == ADMIN_EMAIL && password == ADMIN_PASSWORD) {
                        AdminManager.setAdminStatus(context, true)
                        Toast.makeText(context, "Admin Login Successful!", Toast.LENGTH_SHORT).show()
                        navController.navigate("dashboard") {
                            popUpTo("admin_login") { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Admin Login", fontSize = 16.sp)
            }
        }
    }
}

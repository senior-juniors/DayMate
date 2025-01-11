package com.example.daymate.Authentication

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.test.services.storage.file.PropertyFile.Column

@Composable
fun SignInScreen(navController: NavController,loginViewmodel: LoginViewmodel= viewModel()){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val signInResult = loginViewmodel.signInResult

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Sign In", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                loginViewmodel.signIn(email, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }
        // Display Result
        if (signInResult != null) {
            LaunchedEffect(signInResult) {
                if (signInResult.contains("Successful")) {
                    val role = signInResult.split(":")[1].trim()
                    when (role) {
                        "Super Admin" -> navController.navigate("super_admin_dashboard")
                        "Admin" -> navController.navigate("admin_dashboard")
                        "Canteen Admin" -> navController.navigate("canteen_admin_dashboard")
                        else -> navController.navigate("user_dashboard")
                    }
                } else {
                    Toast.makeText(navController.context, signInResult, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
package com.example.daymate.Authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUp_Screen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                signUpUser(email, password) {
                    isLoading = false
                    if (it) {
                        navController.navigate("home")
                    } else {
//                            Toast.makeText(LocalContext.current,
//                                "Sign Up failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Sign Up")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Login")
        }
    }
}

fun signUpUser(email: String, password: String, onResult: (Boolean) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            onResult(task.isSuccessful)
        }
}



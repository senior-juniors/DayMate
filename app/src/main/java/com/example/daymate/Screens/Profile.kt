package com.example.daymate.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.daymate.R
import com.example.daymate.auth.UserViewmodel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val userViewModel: UserViewmodel = viewModel()
    Column (modifier = Modifier.fillMaxSize()
        .padding(50.dp)) {
        val user = userViewModel.user_Data

        // Fetch data once when the screen loads
        LaunchedEffect(Unit) {
            userViewModel.fetchUserData()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (user != null) {
                Text(text = "Name: ${user.name}", style = MaterialTheme.typography.body1)
                Text(text = "Email: ${user.email}", style = MaterialTheme.typography.body1)
                Text(
                    text = "Semester: ${user.semester}",
                    style = MaterialTheme.typography.body1
                )
                Text(text = "Branch: ${user.branch}", style = MaterialTheme.typography.body1)
                Text(text = "Batch: ${user.batch}", style = MaterialTheme.typography.body1)
                Text(text = "Club: ${user.club}", style = MaterialTheme.typography.body1)
            } else {
                CircularProgressIndicator()
            }

            Button(onClick = {
                userViewModel.signOut()

                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                googleSignInClient.signOut().addOnCompleteListener {
                    // Navigate to the FirstScreen (Google login screen)
                    navController.navigate("FirstScreen") {
                        popUpTo(0) { inclusive = true } // Clear entire backstack
                        launchSingleTop = true
                    }
                }
            }) {
                Text("Sign Out")
            }
        }
    }
}


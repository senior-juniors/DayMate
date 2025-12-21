package com.example.daymate.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun rememberGoogleAuthLauncher(
    navController: NavController,
    authViewModel: AuthViewmodel = hiltViewModel(),
    userViewModel: UserViewmodel = viewModel()
): () -> Unit {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val email = account?.email
            
            // Validate college email domain or admin email
            val isCollegeEmail = email?.endsWith("@iiitkota.ac.in") == true
            val isAdminEmail = email == "hrmeenam636@gmail.com"
            
            if (email == null || (!isCollegeEmail && !isAdminEmail)) {
                Toast.makeText(
                    context, 
                    "Please use your college email (@iiitkota.ac.in)", 
                    Toast.LENGTH_LONG
                ).show()
                return@rememberLauncherForActivityResult
            }
            
            account.idToken?.let { token ->
                authViewModel.signInWithGoogle(token) { success ->
                    if (success) {
                        userViewModel.checkUserDataExists { exists ->
                            navController.navigate(
                                if (exists) "dashboard" else "userinfoScreen"
                            ) {
                                popUpTo("FirstScreen") { inclusive = true }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    return remember {
        {
            val client = GoogleAuthManager.getClient(context)
            launcher.launch(client.signInIntent)
        }
    }
}

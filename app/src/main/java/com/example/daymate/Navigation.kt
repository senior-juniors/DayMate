package com.example.daymate


import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.daymate.screens.ClubConfirmScreen
import com.example.daymate.screens.ClubScreen
import com.example.daymate.screens.DashboardScreen
import com.example.daymate.screens.DayMateFirstScreen
import com.example.daymate.screens.LoginScreen
import com.example.daymate.screens.ProfileScreen
import com.example.daymate.screens.SemesterSelectionScreen
import com.example.daymate.screens.SignUpScreen
import com.example.daymate.auth.AuthViewmodel
import com.example.daymate.auth.UserViewmodel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splashScreen"
    ) {
        composable("FirstScreen") {
            val context = LocalContext.current
            val authViewModel: AuthViewmodel = hiltViewModel()
            val userViewModel: UserViewmodel = viewModel()

            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        account?.idToken?.let { token ->
                            authViewModel.signInWithGoogle(token) { success ->
                                if (success) {
                                    // ✅ Always go to userinfo screen after sign in
//                                    navController.navigate("userinfo") {
//                                        popUpTo("FirstScreen") { inclusive = true }
//                                    }

                                    // If you want to check if user data exists before navigating, use this instead:
                                userViewModel.checkUserDataExists { exists ->
                                    if (exists) {
                                        navController.navigate("dashboard") {
                                            popUpTo("FirstScreen") { inclusive = true }
                                        }
                                    } else {
                                        navController.navigate("userinfoScreen") {
                                            popUpTo("FirstScreen") { inclusive = true }
                                        }
                                    }
                                }

                                }
                            }
                        }
                    } catch (e: ApiException) {
                        Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                    }
                }

            val launchGoogleSignIn = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                val client = GoogleSignIn.getClient(context, gso)
                launcher.launch(client.signInIntent)
            }

            DayMateFirstScreen(
                navController = navController,
                onGoogleSignInClick = launchGoogleSignIn
            )
        }


        composable("login") {
            LoginScreen(navController)
        }
        composable("signup") {
            SignUpScreen(navController)
        }

        composable("clubscreen") {
            ClubScreen(navController, userViewmodel = UserViewmodel())
        }
        composable("clubconfirm") {
            ClubConfirmScreen(navController)
        }
        composable("dashboard") {
            DashboardScreen(navController, userViewModel = UserViewmodel())
        }
        composable("userinfoScreen") {
            SemesterSelectionScreen(navController, userViewmodel = UserViewmodel())
        }
        composable("splashScreen") {
            SplashScreen(navController)
        }
        composable("profileScreen") {
            ProfileScreen(navController)
        }


    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    // Check if the user is already logged in
    LaunchedEffect(Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // If the user is logged in, navigate directly to the Dashboard
            navController.navigate("dashboard") {
                popUpTo("splashScreen") { inclusive = true } // Remove Splash from the backstack
            }
        } else {
            // If the user is not logged in, navigate to the signup screen
            navController.navigate("FirstScreen")
        }
    }

    // Show loading or splash screen while checking login status
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}



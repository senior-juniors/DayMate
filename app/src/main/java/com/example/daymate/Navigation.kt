package com.example.daymate


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.daymate.Screens.ClubConfirmScreen
import com.example.daymate.Screens.ClubScreen
import com.example.daymate.Screens.DashboardScreen
import com.example.daymate.Screens.DayMateFirstScreen
import com.example.daymate.Screens.LoginScreen
import com.example.daymate.Screens.ProfileScreen
import com.example.daymate.Screens.SemesterSelectionScreen
import com.example.daymate.Screens.SignUpScreen
import com.example.daymate.Screens.StudyMaterial
import com.example.daymate.auth.UserViewmodel
import com.example.daymate.auth.rememberGoogleAuthLauncher
import com.example.daymate.classroom.ClassroomScreen
import com.example.daymate.event.AddEventScreen
import com.example.daymate.event.EventDetailsScreen
import com.example.daymate.event.EventListScreen
import com.example.daymate.event.EventViewModel
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel = EventViewModel()
    val events by viewModel.events.collectAsState()
    NavHost(
        navController = navController,
        startDestination = "splashScreen"
    ) {
//        composable("FirstScreen") {
//            val context = LocalContext.current
//            val authViewModel: AuthViewmodel = hiltViewModel()
//            val userViewModel: UserViewmodel = viewModel()
//
//            val launcher =
//                rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                    try {
//                        val account = task.getResult(ApiException::class.java)
//                        account?.idToken?.let { token ->
//                            authViewModel.signInWithGoogle(token) { success ->
//                                if (success) {
//                                    // ✅ Always go to userinfo screen after sign in
////                                    navController.navigate("userinfo") {
////                                        popUpTo("FirstScreen") { inclusive = true }
////                                    }
//
//                                    // If you want to check if user data exists before navigating, use this instead:
//                                userViewModel.checkUserDataExists { exists ->
//                                    if (exists) {
//                                        navController.navigate("dashboard") {
//                                            popUpTo("FirstScreen") { inclusive = true }
//                                        }
//                                    } else {
//                                        navController.navigate("userinfoScreen") {
//                                            popUpTo("FirstScreen") { inclusive = true }
//                                        }
//                                    }
//                                }
//
//                                }
//                            }
//                        }
//                    } catch (e: ApiException) {
//                        Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            val launchGoogleSignIn = {
//                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken(context.getString(R.string.default_web_client_id))
//                    .requestEmail()
//                    .build()
//                val client = GoogleSignIn.getClient(context, gso)
//                launcher.launch(client.signInIntent)
//            }
//
//            DayMateFirstScreen(
//                navController = navController,
//                onGoogleSignInClick = launchGoogleSignIn
//            )
//        }
        composable("FirstScreen") {
            val launchGoogleSignIn = rememberGoogleAuthLauncher(navController)
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

        composable("classroom") {
            ClassroomScreen(navController)
        }
        composable("events") {
            EventListScreen(viewModel = viewModel, navController = navController)
        }
        composable("semester") {
            StudyMaterial(  navController)
        }
        composable("addEvent") {
            AddEventScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            route = "eventDetails/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            val event = events.find { it.id == eventId }

            if (event != null) {
                EventDetailsScreen(event = event)
            } else {
                Text("Event not found")
            }
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



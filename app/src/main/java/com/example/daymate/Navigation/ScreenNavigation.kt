package com.example.daymate.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.daymate.Authentication.SignInScreen
import com.example.daymate.Authentication.SignUpScreen

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.SignUpScreen.name
    ) {
        composable(Screens.SignUpScreen.name) { SignUpScreen(navController = navController) }
        composable(Screens.SignInScreen.name) { SignInScreen(navController=navController) }

        // composable("super_admin_dashboard") {  }
    }

}
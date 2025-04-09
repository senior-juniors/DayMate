package com.example.daymate


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.daymate.Screens.ClubConfirmScreen
import com.example.daymate.Screens.ClubScreen
import com.example.daymate.Screens.DashboardScreen
import com.example.daymate.Screens.DayMateFirstScreen
import com.example.daymate.Screens.LoginScreen
import com.example.daymate.Screens.SemesterSelectionScreen
import com.example.daymate.Screens.SignUpScreen

//@Composable
//fun AppNavGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        startDestination = "first"
//    ) {
//        composable("first") {
//           // DayMateFirstScreen(navController)
//        }
//
//        composable("login") {
//          //  LoginScreen(navController)
//        }
//        composable("signup") {
//          //  SignUpScreen(navController)
//        }

//        composable("clubs") {
//            ClubScreen(
//                navController = navController,
//
//                )
//        }

//        composable("semester_select") {
//            SemesterSelectionScreen(navController)
//        }
//        composable("dashboard") {
//            DashboardScreen(navController)
//        }
//    }
//}
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "FirstScreen"
    ){
        composable("login") {
            LoginScreen(navController)
        }
        composable("signup") {
            SignUpScreen(navController)
        }
        composable("FirstScreen") {
            DayMateFirstScreen(navController)
        }
        composable("clubscreen") {
            ClubScreen(navController)
        }
        composable("clubconfirm") {
            ClubConfirmScreen(navController)
        }
        composable("dashboard") {
            DashboardScreen(navController)
        }
        composable("secondscreen") {
            SemesterSelectionScreen (navController)
        }

    }
}
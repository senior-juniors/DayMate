package com.example.daymate.Navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.example.daymate.Authentication.SignIn_Screen
    import com.example.daymate.Authentication.SignUp_Screen
    import com.example.daymate.User.User_homepage

@Composable
fun ScreenNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { SignIn_Screen(navController) }
        composable("signup") { SignUp_Screen(navController) }
//        composable("home") { Admin_homepage(navController)  }
//        composable(Screens.Canteen_admin_homepage.name) { Canteen_admin_homepage(navController)  }
//        composable(Screens.Super_admin_homepage.name) { Super_admin_homepage(navController) }
        composable("home") { User_homepage(navController)  }

    }

}
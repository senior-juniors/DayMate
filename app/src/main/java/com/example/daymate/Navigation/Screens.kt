package com.example.daymate.Navigation

enum class Screens {
    SignUp_Screen,
    SignIn_Screen,
    User_homepage,
    Admin_homepage,
    Super_admin_homepage,
    Canteen_admin_homepage;

    companion object {
        fun fromeRoute(route: String): Screens = when (route?.substringBefore("/")) {
            SignUp_Screen.name->SignUp_Screen
            SignIn_Screen.name->SignIn_Screen
            User_homepage.name->User_homepage
            Admin_homepage.name-> Admin_homepage
            Super_admin_homepage.name->Super_admin_homepage
            Canteen_admin_homepage.name->Canteen_admin_homepage
            null -> SignIn_Screen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}
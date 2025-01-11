package com.example.daymate.Navigation

enum class Screens {
    SignInScreen,
    SignUpScreen;

    companion object {
        fun fromeRoute(route: String): Screens = when (route?.substringBefore("/")) {
            SignInScreen.name -> SignInScreen
            SignUpScreen.name -> SignUpScreen
            null -> SignUpScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}
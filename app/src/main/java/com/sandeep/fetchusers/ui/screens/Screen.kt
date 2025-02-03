package com.sandeep.fetchusers.ui.screens

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object UserList: Screen("user_list")
    object UserDetail: Screen("user_details")
}
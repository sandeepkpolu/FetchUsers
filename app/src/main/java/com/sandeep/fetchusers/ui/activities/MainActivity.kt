package com.sandeep.fetchusers.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sandeep.fetchusers.ui.screens.Screen
import com.sandeep.fetchusers.ui.screens.SplashScreen
import com.sandeep.fetchusers.ui.screens.UserDetailsScreen
import com.sandeep.fetchusers.ui.screens.UserListScreen
import com.sandeep.fetchusers.ui.theme.FetchUsersTheme
import com.sandeep.fetchusers.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchUsersTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
                    composable(route = Screen.SplashScreen.route) {
                        SplashScreen(navController)
                    }
                    composable(route = Screen.UserList.route) {
                        UserListScreen(navController, viewModel)
                    }
                    composable(route = Screen.UserDetail.route + "?userId={userId}&userName={userName}",
                        arguments = listOf(
                            navArgument("userId") {
                                type = NavType.IntType
                                nullable = false
                            },
                            navArgument("userName") {
                                type = NavType.StringType
                                nullable = false
                            }
                            )) {
                        UserDetailsScreen(it.arguments?.getInt("userId") ?: -1,
                            it.arguments?.getString("userName") ?: "",
                            navController)
                    }
                }
            }
        }
    }
}
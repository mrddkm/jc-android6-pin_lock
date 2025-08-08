package com.arkhe.pinlock.presentation.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arkhe.pinlock.domain.model.PinState
import com.arkhe.pinlock.presentation.screens.create.CreatePinScreen
import com.arkhe.pinlock.presentation.screens.main.MainScreen
import com.arkhe.pinlock.presentation.screens.signin.SignInScreen

sealed class Screen(val route: String) {
    object CreatePin : Screen("create_pin")
    object SignIn : Screen("sign_in")
    object Main : Screen("main")
}

@Composable
fun PinNavigation(
    navController: NavHostController,
    pinState: PinState
) {
    val startDestination = when {
        !pinState.isCreated || pinState.pinCode.isEmpty() -> Screen.CreatePin.route
        pinState.pinCode.isNotEmpty() && !pinState.isSignedIn -> Screen.SignIn.route
        else -> Screen.Main.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.CreatePin.route) {
            CreatePinScreen(
                onNavigateToSignIn = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.CreatePin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.SignIn.route) {
            SignInScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SignIn.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
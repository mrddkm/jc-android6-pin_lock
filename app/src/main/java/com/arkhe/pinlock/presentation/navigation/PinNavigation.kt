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
    /*Improved navigation logic for security*/
    val startDestination = when {
        /*If PIN not created or empty -> Create PIN*/
        !pinState.isCreated || pinState.pinCode.isEmpty() -> Screen.CreatePin.route
        /*If PIN created but not signed in -> Sign In (SECURITY: Always require sign in)*/
        pinState.pinCode.isNotEmpty() && !pinState.isSignedIn -> Screen.SignIn.route
        /*If everything is valid -> Main Screen*/
        pinState.pinCode.isNotEmpty() && pinState.isSignedIn -> Screen.Main.route
        /*Default fallback -> Sign In*/
        else -> Screen.SignIn.route
    }

    /*Navigate to correct screen when state changes*/
    LaunchedEffect(pinState) {
        val currentRoute = navController.currentDestination?.route

        /*Auto navigate to SignIn when signed out (security feature)*/
        if (pinState.isCreated && pinState.pinCode.isNotEmpty() &&
            !pinState.isSignedIn && currentRoute == Screen.Main.route) {
            navController.navigate(Screen.SignIn.route) {
                popUpTo(Screen.Main.route) { inclusive = true }
            }
        }
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
package com.arkhe.pinlock.presentation.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arkhe.pinlock.domain.model.PinState
import com.arkhe.pinlock.presentation.screens.create.CreatePinScreen
import com.arkhe.pinlock.presentation.screens.main.MainScreen
import com.arkhe.pinlock.presentation.screens.lockpin.LockPinScreen

sealed class Screen(val route: String) {
    object CreatePin : Screen("create_pin")
    object LockPin : Screen("lock_pin")
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
        /*If PIN created but not locked in -> LockIn (SECURITY: Always require lock in)*/
        pinState.pinCode.isNotEmpty() && !pinState.isLockedIn -> Screen.LockPin.route
        /*If everything is valid -> Main Screen*/
        pinState.pinCode.isNotEmpty() && pinState.isLockedIn -> Screen.Main.route
        /*Default fallback -> Sign In*/
        else -> Screen.LockPin.route
    }

    /*Navigate to correct screen when state changes*/
    LaunchedEffect(pinState) {
        val currentRoute = navController.currentDestination?.route

        /*Auto navigate to LockIn when locked out (security feature)*/
        if (pinState.isCreated && pinState.pinCode.isNotEmpty() &&
            !pinState.isLockedIn && currentRoute == Screen.Main.route) {
            navController.navigate(Screen.LockPin.route) {
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
                onNavigateToLockIn = {
                    navController.navigate(Screen.LockPin.route) {
                        popUpTo(Screen.CreatePin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.LockPin.route) {
            LockPinScreen(
                onNavigateToMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.LockPin.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
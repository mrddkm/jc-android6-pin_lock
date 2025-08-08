package com.arkhe.pinlock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.arkhe.pinlock.di.appModule
import com.arkhe.pinlock.domain.model.PinState
import com.arkhe.pinlock.domain.usecase.GetPinStateUseCase
import com.arkhe.pinlock.presentation.navigation.PinNavigation
import com.arkhe.pinlock.presentation.theme.PinPadTheme
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    private val getPinStateUseCase: GetPinStateUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        setContent {
            PinPadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val pinState by getPinStateUseCase().collectAsState(
                        initial = PinState()
                    )

                    PinNavigation(
                        navController = navController,
                        pinState = pinState
                    )
                }
            }
        }
    }
}
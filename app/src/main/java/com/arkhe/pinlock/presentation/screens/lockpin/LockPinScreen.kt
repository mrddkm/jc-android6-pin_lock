package com.arkhe.pinlock.presentation.screens.lockpin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkhe.pinlock.presentation.components.PinIndicator
import com.arkhe.pinlock.presentation.components.PinPadComponent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockPinScreen(
    onNavigateToMain: () -> Unit,
    onNavigateToCreatePin: () -> Unit = {},
    viewModel: LockPinViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateToMain()
        }
    }

    LaunchedEffect(uiState.shouldNavigateToCreatePin) {
        if (uiState.shouldNavigateToCreatePin) {
            onNavigateToCreatePin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Input PIN",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Input 6-digit PIN",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        PinIndicator(
            pinLength = uiState.currentPin.length,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(bottom = 24.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        PinPadComponent(
            onNumberClick = { digit ->
                if (!uiState.isLoading) {
                    viewModel.onPinDigitEntered(digit)
                }
            },
            onBackspaceClick = {
                if (!uiState.isLoading) {
                    viewModel.onBackspace()
                }
            },
            onClearClick = {
                if (!uiState.isLoading) {
                    viewModel.onClearPin()
                }
            }
        )

        TextButton(
            onClick = {
                if (!uiState.isLoading) {
                    viewModel.onForgotPin()
                }
            },
            enabled = !uiState.isLoading
        ) {
            Text(
                text = "Forgot PIN?",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            )
        }

        if (uiState.error != null) {
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
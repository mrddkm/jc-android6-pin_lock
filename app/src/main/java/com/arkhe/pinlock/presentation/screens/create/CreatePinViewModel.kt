package com.arkhe.pinlock.presentation.screens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkhe.pinlock.domain.usecase.CreatePinUseCase
import kotlinx.coroutines.launch

class CreatePinViewModel(
    private val createPinUseCase: CreatePinUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(CreatePinUiState())
        private set

    fun onPinDigitEntered(digit: String) {
        if (uiState.currentPin.length < 6) {
            val newPin = uiState.currentPin + digit
            uiState = uiState.copy(currentPin = newPin)

            if (newPin.length == 6) {
                handlePinComplete(newPin)
            }
        }
    }

    fun onBackspace() {
        if (uiState.currentPin.isNotEmpty()) {
            uiState = uiState.copy(
                currentPin = uiState.currentPin.dropLast(1)
            )
        }
    }

    private fun handlePinComplete(pin: String) {
        if (!uiState.isConfirmation) {
            uiState = uiState.copy(
                isConfirmation = true,
                firstPin = pin,
                currentPin = "",
                title = "PIN Confirmation"
            )
        } else {
            if (pin == uiState.firstPin) {
                viewModelScope.launch {
                    try {
                        createPinUseCase(pin)
                        uiState = uiState.copy(
                            isSuccess = true,
                            error = null
                        )
                    } catch (e: Exception) {
                        uiState = uiState.copy(
                            error = e.message ?: "An error occurred",
                        )
                    }
                }
            } else {
                uiState = uiState.copy(
                    isConfirmation = false,
                    firstPin = "",
                    currentPin = "",
                    title = "Create PIN",
                    error = "PIN not matched, try again."
                )
            }
        }
    }
}

data class CreatePinUiState(
    val currentPin: String = "",
    val firstPin: String = "",
    val isConfirmation: Boolean = false,
    val isSuccess: Boolean = false,
    val title: String = "Create PIN",
    val error: String? = null
)
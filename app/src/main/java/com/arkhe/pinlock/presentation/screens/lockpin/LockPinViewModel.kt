package com.arkhe.pinlock.presentation.screens.lockpin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkhe.pinlock.domain.repository.PinRepository
import com.arkhe.pinlock.domain.usecase.ValidatePinUseCase
import kotlinx.coroutines.launch

class LockPinViewModel(
    private val validatePinUseCase: ValidatePinUseCase,
    private val repository: PinRepository,
) : ViewModel() {

    var uiState by mutableStateOf(LockPinUiState())
        private set

    fun onPinDigitEntered(digit: String) {
        if (uiState.currentPin.length < 6) {
            val newPin = uiState.currentPin + digit
            uiState = uiState.copy(
                currentPin = newPin,
                error = null
            )

            if (newPin.length == 6) {
                validatePin(newPin)
            }
        }
    }

    fun onBackspace() {
        if (uiState.currentPin.isNotEmpty()) {
            uiState = uiState.copy(
                currentPin = uiState.currentPin.dropLast(1),
                error = null
            )
        }
    }

    private fun validatePin(pin: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val isValid = validatePinUseCase(pin)
                if (isValid) {
                    repository.lockIn()
                    uiState = uiState.copy(
                        isSuccess = true,
                        isLoading = false,
                        error = null
                    )
                } else {
                    uiState = uiState.copy(
                        currentPin = "",
                        isLoading = false,
                        error = "Wrong PIN, please try again."
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    currentPin = "",
                    isLoading = false,
                    error = "An error occurred: ${e.message}"
                )
            }
        }
    }
}

data class LockPinUiState(
    val currentPin: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
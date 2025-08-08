package com.arkhe.pinlock.domain.usecase

import android.util.Log
import com.arkhe.pinlock.domain.repository.PinRepository

class ValidatePinUseCase(
    private val repository: PinRepository
) {
    suspend operator fun invoke(pinCode: String): Boolean {
        Log.d("ValidatePinUseCase", "Attempting to validate PIN: ${pinCode.length} digits")

        if (pinCode.length != 6 || !pinCode.all { it.isDigit() }) {
            Log.d(
                "ValidatePinUseCase",
                "PIN format invalid: length=${pinCode.length}, isDigit=${pinCode.all { it.isDigit() }}"
            )
            return false
        }

        val result = repository.validatePin(pinCode)
        Log.d("ValidatePinUseCase", "Validation result: $result")

        return result
    }
}
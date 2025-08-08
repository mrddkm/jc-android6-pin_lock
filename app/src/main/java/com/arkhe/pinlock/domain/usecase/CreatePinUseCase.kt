package com.arkhe.pinlock.domain.usecase

import com.arkhe.pinlock.domain.repository.PinRepository

class CreatePinUseCase(
    private val repository: PinRepository
) {
    suspend operator fun invoke(pinCode: String) {
        if (pinCode.length != 6 || !pinCode.all { it.isDigit() }) {
            throw IllegalArgumentException("PIN use 6-digit Numeric")
        }
        repository.createPin(pinCode)
    }
}
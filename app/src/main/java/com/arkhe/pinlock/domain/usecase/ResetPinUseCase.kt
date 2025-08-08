package com.arkhe.pinlock.domain.usecase

import com.arkhe.pinlock.domain.repository.PinRepository

class ResetPinUseCase(
    private val repository: PinRepository
) {
    suspend operator fun invoke() {
        repository.resetPin()
    }
}
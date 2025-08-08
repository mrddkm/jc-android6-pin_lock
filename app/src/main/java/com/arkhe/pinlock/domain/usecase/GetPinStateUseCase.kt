package com.arkhe.pinlock.domain.usecase

import com.arkhe.pinlock.domain.model.PinState
import com.arkhe.pinlock.domain.repository.PinRepository
import kotlinx.coroutines.flow.Flow

class GetPinStateUseCase(
    private val repository: PinRepository
) {
    operator fun invoke(): Flow<PinState> {
        return repository.getPinState()
    }
}
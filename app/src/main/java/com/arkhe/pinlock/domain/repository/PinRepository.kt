package com.arkhe.pinlock.domain.repository

import com.arkhe.pinlock.domain.model.PinState
import kotlinx.coroutines.flow.Flow

interface PinRepository {
    suspend fun createPin(pinCode: String)
    suspend fun validatePin(pinCode: String): Boolean
    suspend fun lockIn()
    suspend fun lockOut()
    fun getPinState(): Flow<PinState>
}
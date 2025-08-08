package com.arkhe.pinlock.data.repository

import com.arkhe.pinlock.data.local.datastore.PinDataStore
import com.arkhe.pinlock.domain.model.PinState
import com.arkhe.pinlock.domain.repository.PinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class PinRepositoryImpl(
    private val dataStore: PinDataStore
) : PinRepository {

    override suspend fun createPin(pinCode: String) {
        dataStore.savePinCode(pinCode)
        dataStore.savePinCreated(true)
    }

    override suspend fun validatePin(pinCode: String): Boolean {
        val storedPin = dataStore.pinCodeFlow.first()
        return storedPin == pinCode
    }

    override suspend fun signIn() {
        dataStore.saveSignInStatus(true)
    }

    override suspend fun signOut() {
        dataStore.saveSignInStatus(false)
    }

    override fun getPinState(): Flow<PinState> {
        return combine(
            dataStore.pinCreatedFlow,
            dataStore.pinCodeFlow,
            dataStore.signInStatusFlow
        ) { isCreated, pinCode, isSignedIn ->
            PinState(
                isCreated = isCreated,
                pinCode = pinCode,
                isSignedIn = isSignedIn
            )
        }
    }
}
package com.arkhe.pinlock.domain.model

data class PinState(
    val isCreated: Boolean = false,
    val pinCode: String = "",
    val isLockedIn: Boolean = false
)
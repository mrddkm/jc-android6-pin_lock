package com.arkhe.pinlock.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkhe.pinlock.domain.model.PinState
import com.arkhe.pinlock.domain.repository.PinRepository
import com.arkhe.pinlock.domain.usecase.GetPinStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPinStateUseCase: GetPinStateUseCase,
    private val pinRepository: PinRepository
) : ViewModel() {

    private val _pinState = MutableStateFlow(PinState())
    val pinState: StateFlow<PinState> = _pinState.asStateFlow()

    init {
        observePinState()
    }

    private fun observePinState() {
        viewModelScope.launch {
            getPinStateUseCase().collect { state ->
                _pinState.value = state
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            pinRepository.signOut()
        }
    }
}
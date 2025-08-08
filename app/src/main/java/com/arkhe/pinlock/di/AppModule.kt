package com.arkhe.pinlock.di

import com.arkhe.pinlock.data.local.datastore.PinDataStore
import com.arkhe.pinlock.data.repository.PinRepositoryImpl
import com.arkhe.pinlock.domain.repository.PinRepository
import com.arkhe.pinlock.domain.usecase.CreatePinUseCase
import com.arkhe.pinlock.domain.usecase.GetPinStateUseCase
import com.arkhe.pinlock.domain.usecase.ResetPinUseCase
import com.arkhe.pinlock.domain.usecase.ValidatePinUseCase
import com.arkhe.pinlock.presentation.screens.create.CreatePinViewModel
import com.arkhe.pinlock.presentation.screens.lockpin.LockPinViewModel
import com.arkhe.pinlock.presentation.screens.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Suppress("DEPRECATION")
val appModule = module {

    /*DataStore*/
    single { PinDataStore(androidContext()) }

    /*Repository*/
    single<PinRepository> { PinRepositoryImpl(get()) }

    /*Use Cases*/
    single { CreatePinUseCase(get()) }
    single { ValidatePinUseCase(get()) }
    single { GetPinStateUseCase(get()) }
    single { ResetPinUseCase(get()) }

    /*ViewModels*/
    viewModel { CreatePinViewModel(get()) }
    viewModel { LockPinViewModel(get(), get(), get()) }
    viewModel { MainViewModel(get(), get()) }
}
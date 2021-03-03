package com.appsynthassignment.di

import com.appsynthassignment.presentation.profile.ProfileInformationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProfileInformationViewModel(get()) }
}

val applicationModule = listOf(
    networkModule, dataBaseModule,
    useCaseModule, repositoryModule,
    viewModelModule
)
package com.appsynthassignment.di

import com.appsynthassignment.data.domain.GetProfileAndNotificationUseCase
import com.appsynthassignment.data.domain.GetProfileAndNotificationUseCaseImp
import org.koin.dsl.module

val useCaseModule = module {
    single<GetProfileAndNotificationUseCase> { GetProfileAndNotificationUseCaseImp(get()) }
}
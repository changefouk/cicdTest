package com.appsynthassignment.di

import com.appsynthassignment.data.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<RemoteDataSource> { RemoteDataSourceImp(get()) }
    single<LocalDataSource> { LocalDataSourceImp(get()) }
    single<DataRepository> { DataRepositoryImp(get(),get()) }
}
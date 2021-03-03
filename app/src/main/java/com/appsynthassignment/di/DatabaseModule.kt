package com.appsynthassignment.di

import android.app.Application
import androidx.room.Room
import com.appsynthassignment.data.database.AppsynthDatabase
import com.appsynthassignment.data.database.NotificationDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private fun provideDatabase(application: Application): AppsynthDatabase {
    return Room.databaseBuilder(application, AppsynthDatabase::class.java, "appsynth_database")
        .fallbackToDestructiveMigration()
        .build()
}

private fun provideCountriesDao(database: AppsynthDatabase): NotificationDao {
    return database.notificationDao
}

val dataBaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }
}
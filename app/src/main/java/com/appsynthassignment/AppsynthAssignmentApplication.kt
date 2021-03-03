package com.appsynthassignment

import android.app.Application
import com.appsynthassignment.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppsynthAssignmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppsynthAssignmentApplication)
            modules(applicationModule)
        }
    }
}
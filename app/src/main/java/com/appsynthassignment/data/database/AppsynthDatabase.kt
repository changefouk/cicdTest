package com.appsynthassignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appsynthassignment.data.database.model.Notification

@Database(
    entities = [Notification::class],
    version = 1,
    exportSchema = false
)
abstract class AppsynthDatabase : RoomDatabase() {
    abstract val notificationDao: NotificationDao
}
package com.appsynthassignment.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification")
data class Notification(
    val userId: String,
    val created: String,
    val text: String
) {
    @PrimaryKey(autoGenerate = true)
    var notificationId: Int = 0
}
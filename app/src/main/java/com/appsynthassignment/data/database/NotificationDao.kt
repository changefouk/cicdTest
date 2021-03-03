package com.appsynthassignment.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appsynthassignment.data.database.model.Notification

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification WHERE userId = :userId")
    fun getListNotification(userId: String): List<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Notification>)
}
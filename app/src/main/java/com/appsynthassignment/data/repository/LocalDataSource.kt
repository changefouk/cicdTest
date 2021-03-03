package com.appsynthassignment.data.repository

import com.appsynthassignment.data.database.NotificationDao
import com.appsynthassignment.data.database.model.Notification
import com.appsynthassignment.data.model.response.NotificationResponseModel
import com.appsynthassignment.util.ResultResponse

interface LocalDataSource {
    fun getListNotification(userId: String): ResultResponse<List<NotificationResponseModel>>
    suspend fun insertAll(notification: List<Notification>)
}

class LocalDataSourceImp(
    private val notificationDao: NotificationDao
) : LocalDataSource {

    override fun getListNotification(userId: String): ResultResponse<List<NotificationResponseModel>> {
        val result = notificationDao.getListNotification(userId)
        return if (result.isNotEmpty()) {
            ResultResponse.success(result.map {
                NotificationResponseModel(
                    created = it.created,
                    text = it.text
                )
            })
        } else {
            ResultResponse.error(Exception("No data in database"))
        }
    }


    override suspend fun insertAll(notification: List<Notification>) =
        notificationDao.insertAll(notification)

}
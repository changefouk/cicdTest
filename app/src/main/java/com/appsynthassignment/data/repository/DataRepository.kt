package com.appsynthassignment.data.repository

import com.appsynthassignment.data.database.model.Notification
import com.appsynthassignment.data.model.response.NotificationResponseModel
import com.appsynthassignment.data.model.response.ProfileResponseModel
import com.appsynthassignment.util.ResultResponse

interface DataRepository {
    suspend fun getProfile(): ResultResponse<ProfileResponseModel>
    suspend fun getNotification(userId: String): ResultResponse<List<NotificationResponseModel>>
}

class DataRepositoryImp(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : DataRepository {

    override suspend fun getProfile(): ResultResponse<ProfileResponseModel> =
        remoteDataSource.getProfile()

    override suspend fun getNotification(userId: String)
            : ResultResponse<List<NotificationResponseModel>> {
        val result = localDataSource.getListNotification(userId)
        if (result.isSuccess) {
            return result
        }
        val responseResult = remoteDataSource.getNotification(userId)
        if (responseResult.isSuccess) {
            responseResult.data?.let { listNotification ->
                localDataSource.insertAll(listNotification.map {
                    Notification(
                        userId = userId,
                        created = it.created,
                        text = it.text
                    )
                })
            }
        }
        return responseResult
    }
}